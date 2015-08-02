/*
 *  Copyright 2015 Carnegie Mellon University
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package edu.cmu.lti.oaqa.bio.index.medline.annotated.uima;

import java.util.*;
import java.io.*;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import edu.cmu.lti.oaqa.annographix.solr.UtilConst;

import edu.cmu.lti.oaqa.bio.index.medline.annotated.types.DocInfo;
import edu.cmu.lti.oaqa.bio.index.medline.annotated.types.Entity;
import edu.cmu.lti.oaqa.bio.index.medline.annotated.types.EntityConceptId;
import edu.cmu.lti.oaqa.bio.index.medline.annotated.types.Sentence;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;

import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;



import org.jdom2.JDOMException;

import com.google.common.base.Splitter;
import com.google.common.collect.*;

import edu.cmu.lti.oaqa.bio.index.medline.*;
import edu.cmu.lti.oaqa.bio.index.medline.annotated.utils.CompressUtils;
import edu.cmu.lti.oaqa.bio.index.medline.annotated.utils.UtilConstMedline;
import edu.cmu.lti.oaqa.annographix.util.XmlHelper;
import edu.cmu.lti.oaqa.annographix.solr.UtilConst;

/**
 * A collection reader for Medline/Pubmed files.
 * 
 * @author Leonid Boytsov, 
 *          reusing some bits of the code by Zi Yang from https://github.com/ziy/medline-indexer.git
 *          
 *
 */
class SuppportedExtensionFilter implements FilenameFilter {
  public static Set<String> supportedExtensions = Sets.newHashSet(".xml.gz", ".xml");
  
  @Override
  public boolean accept(File dir, String name) {
    for (String extension : supportedExtensions) {
      if (name.endsWith(extension)) {
        return true;
      }
    }
    return false;
  }
}

public class MedlineCollectionReader extends CollectionReader_ImplBase {
  public static final String PARAM_INPUT_DIR = "InputDir";
  
  /**
   * Name of the configuration parameter specifying the maximum number of abstracts to process.
   */
  public static final String PARAM_MAX_QTY = "MaxQty";
  
  // TODO Can it be in other languages?
  private static final String DOCUMENT_LANGUAGE = "en";

  // The directory storing the previously created Lucene index for annotations.
  private static final String PARAM_LUCENE_DIR = "AnnotLuceneDir";

  private static final int BATCH_QTY = 1000;

  private static final int APPROX_SHIFT = 2;
  
  private ArrayList<File>   mInpFiles;
  private int                       mNextInpFileIndex = 0;
  private MedlineCitationSetReader  mCurrReader = null;
  private int                       mEntryQty = 0;
  private int                       mMaxEntryQty = Integer.MAX_VALUE;
  
  private IndexSearcher             mSearcher;
  private QueryParser               mQueryParser;
  
  private StanfordCoreNLP           mPipeline = null;
  private static Splitter           mSplitOnTAB = Splitter.on('\t');
  private static Splitter           mSplitOnNL  = Splitter.on('\n');  
  
  private XmlHelper mXmlHelper = new XmlHelper();

  /**
   * @see org.apache.uima.collection.CollectionReader_ImplBase#initialize()
   */
  public void initialize() throws ResourceInitializationException {
    String inputDirName = (String) getConfigParameterValue(PARAM_INPUT_DIR);
   
    File inputDir = new File(inputDirName);
    mInpFiles = Lists.newArrayList(inputDir.listFiles(new SuppportedExtensionFilter()));
    
    System.out.println("I will process the following input files: ");
    
    for (File f: mInpFiles) {
      System.out.println(f.getAbsolutePath());
    }
    
    Integer maxQty = (Integer) getConfigParameterValue(PARAM_MAX_QTY);
    if (maxQty != null) {
      mMaxEntryQty = maxQty;
      System.out.println("Procesing at most " + mMaxEntryQty + " entries");
    } else {
      System.out.println("Processing the whole collection.");
    }
    
    try {
      String luceneDir = (String) getConfigParameterValue(PARAM_LUCENE_DIR);
      
      File indexDir = new File(luceneDir);

      if (!indexDir.exists()) {
        throw new Exception(String.format("Directory '%s' doesn't exist", luceneDir));
      }

      mSearcher = new IndexSearcher(DirectoryReader.open(FSDirectory.open(indexDir)));

      mQueryParser = new QueryParser(UtilConstMedline.PMID_FIELD, new WhitespaceAnalyzer());
      mQueryParser.setDefaultOperator(QueryParser.OR_OPERATOR);
    } catch (Exception e) {
      e.printStackTrace();
      throw new ResourceInitializationException(e);
    }

    Properties props = new Properties();
    props.setProperty("annotators", "tokenize, ssplit");
    mPipeline = new StanfordCoreNLP(props);        
  }  
  
  @Override
  public void getNext(CAS aCAS) throws IOException, CollectionException {
    if (mEntryQty < mMaxEntryQty) {
      if (mCurrReader != null && mCurrReader.hasNext()) {
        MedlineCitation entry = mCurrReader.next();
        ++mEntryQty;
        HashMap<String, String>  fields = new HashMap<String, String>();
        
        int pmid = entry.getPmid();
        fields.put(UtilConst.TAG_DOCNO, pmid + ""); 
                
        String entityDesc = "";
        String abstractText = entry.getAbstractText();
        String articleTitle = entry.getArticleTitle();
        
        String query = pmid + "";
        try {
          Query  queryParsed = mQueryParser.parse(query);
          
          TopDocs     hits = mSearcher.search(queryParsed, 1);
          ScoreDoc[]  scoreDocs = hits.scoreDocs;

          for (ScoreDoc oneHit: scoreDocs) {
            Document doc = mSearcher.doc(oneHit.doc);
            entityDesc = doc.get(UtilConstMedline.ENTITIES_DESC_FIELD);
            /*
             *  Use the values provided in the annotation file:
             *  who knows, it might be different from the data
             *  in the Medline source XML.
             */
            abstractText = doc.get(UtilConstMedline.ABSTRACT_TEXT_FIELD);
            articleTitle = doc.get(UtilConstMedline.ARTICLE_TITLE_FIELD);
            
            break;
          }
        } catch (Exception e) {
          e.printStackTrace();
          throw new CollectionException(e);
        }
        
        fields.put(UtilConstMedline.ABSTRACT_TEXT_FIELD, abstractText);        
        fields.put(UtilConstMedline.ARTICLE_TITLE_FIELD, articleTitle);
        
        /*
         *  We insert an extract space: I think all offsets were counted
         *  assuming that such an extra space existed, though this
         *  space isn't explicitly represented in the annotation data.
         */
        String titlePlusText = articleTitle + " " + abstractText;
        fields.put(UtilConstMedline.TITLE_PLUS_TEXT_FIELD, titlePlusText);
        
        JCas jcas;
        
        try {
          jcas = aCAS.getJCas();
        } catch (CASException e) {
          e.printStackTrace();
          throw new CollectionException(e);        }
        
          jcas.setDocumentLanguage(DOCUMENT_LANGUAGE);
        try {
          jcas.setDocumentText(mXmlHelper.genXMLIndexEntry(fields));
          
          DocInfo info = 
              new DocInfo(jcas, 0, jcas.getDocumentText().length());
          
          info.setPmid(pmid + "");
          info.addToIndexes();
          
          JCas annotView = jcas.createView(UtilConstMedline.ANNOT_VIEW_NAME);
          annotView.setDocumentText(titlePlusText);
          
          /*
           *  Let's create ALL annotations here: 
           *  this may not be THE best style. 
           *  However, this way, we don't have to create additional
           *  annotations and their respective descriptors.
           */          
          // 1. sentence annotations
          splitSentences(annotView);
          // 2. entity annotations
          addEntities(annotView, entityDesc);
        } catch (Exception e) {
          e.printStackTrace();
          throw new CollectionException(e);
        }
        
        if (mEntryQty % BATCH_QTY == 0)
          System.out.println("Processed " + mEntryQty + " abstracts");
        
        return;
      }
    }
    
    throw new CollectionException(
          new Exception("Bug: getNext() called, but no next entry is available."));
  }
  
  private void addEntities(JCas annotView, String entityDesc) {
    if (entityDesc.isEmpty()) return; // Ignore empty descriptions
    
    String text = annotView.getDocumentText();
    
//    System.out.println("Processing entities!");
    
    for (String line : mSplitOnNL.splitToList(entityDesc)) {
      if (line.isEmpty()) continue;
      List<String> parts = mSplitOnTAB.splitToList(line);
      if (parts.size() != 6) {
        System.err.println(
            "The entity line is expected to have six TAB-separated fields, but it has "
                + parts.size() + " line: '" + line.replaceAll("\\t", " <TAB> ") + "', ignoring invalid line");
      } else {
        int startApprox = Integer.parseInt(parts.get(1));
        
        // Offsets in the annotation file can be +/- by one or two. Let's compute them more precisely:
        String coveredText = parts.get(3);
        
        int start = -1;
        
        for (int k = Math.max(startApprox - APPROX_SHIFT, 0);
             k <= Math.min(startApprox + APPROX_SHIFT + 1, text.length() - coveredText.length()); ++k) {
          // The end index of the substring() should be <= text.length
          // This seems to be guaranteed if k <= text.length() - coveredText.length()
          if (text.substring(k, k + coveredText.length()).equals(coveredText)) {
            start = k;
            break;
          }
        }
        
        if (start >= 0) {
          int end = start + coveredText.length();
          
          String type     = parts.get(4);
          String typeID   = parts.get(5);
          
          Entity a1 = new Entity(annotView, start, end);
          a1.addToIndexes();
          a1.setBioConcept(type);
          EntityConceptId a2 = new EntityConceptId(annotView, start, end);
          a2.setParent(a1);
          a2.addToIndexes();
          a2.setBioConceptID(typeID);
        }
      }
    }    
  }

  private void splitSentences(JCas jcas) {
    String text = jcas.getDocumentText();
    
    Annotation doc = new Annotation(text);
    mPipeline.annotate(doc);

    for(CoreMap oneSent: doc.get(SentencesAnnotation.class)) {
      int start = Integer.MAX_VALUE, end = -1;

      for (CoreLabel token: oneSent.get(TokensAnnotation.class)) {
        start = Math.min(start, token.beginPosition());
        end = Math.max(end, token.endPosition());
      }
      if (start <= end) {
        Sentence annot = new Sentence(jcas, start, end);
        annot.addToIndexes();
      }
    }    
  }

  @Override
  public boolean hasNext() throws IOException, CollectionException {
    if (mEntryQty >= mMaxEntryQty) return false;
      
    if (mCurrReader!= null) {
      if (mCurrReader.hasNext()) return true;
      mCurrReader = null;
    }
    if (mNextInpFileIndex < mInpFiles.size()) {
      try {
        System.out.println("Starting to process " + mInpFiles.get(mNextInpFileIndex).getAbsolutePath());
        mCurrReader = new MedlineCitationSetReader(
            CompressUtils.createInputStream(mInpFiles.get(mNextInpFileIndex).getAbsolutePath()));
        mNextInpFileIndex++;
      } catch (JDOMException e) {
        throw new CollectionException(e); 
      }
      return mCurrReader.hasNext();
    }
    return false;
  }
  
  /**
   * Actually this function doesn't properly report progress (we don't
   * care a bit about the progress anyways), it exists only because 
   * we have to override a parent class method.
   * 
   */
  @Override
  public Progress[] getProgress() {
    return new Progress[] { new ProgressImpl(0, 0, Progress.BYTES) };
  }
  
  @Override
  public void close() throws IOException {
    System.out.println("Processed " + mEntryQty + " abstracts");    
  }
}
