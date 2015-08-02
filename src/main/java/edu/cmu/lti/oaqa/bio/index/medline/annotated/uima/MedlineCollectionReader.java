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

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;
import org.jdom2.JDOMException;

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
  
  private ArrayList<File>   mInpFiles;
  int                       mNextInpFileIndex = 0;
  MedlineCitationSetReader  mCurrReader = null;
  int                       mEntryQty = 0;
  int                       mMaxEntryQty = Integer.MAX_VALUE;

  private XmlHelper mXmlHelper = new XmlHelper();

  /**
   * @see org.apache.uima.collection.CollectionReader_ImplBase#initialize()
   */
  public void initialize() throws ResourceInitializationException {
    String inputDirName = (String) getConfigParameterValue(PARAM_INPUT_DIR);
   
    File inputDir = new File(inputDirName);
    mInpFiles = Lists.newArrayList(inputDir.listFiles(new SuppportedExtensionFilter()));
    
    Integer maxQty = (Integer) getConfigParameterValue(PARAM_MAX_QTY);
    if (maxQty != null) mMaxEntryQty = maxQty;
  }  
  
  @Override
  public void getNext(CAS aCAS) throws IOException, CollectionException {
    if (mEntryQty < mMaxEntryQty) {
      if (mCurrReader != null && mCurrReader.hasNext()) {
        MedlineCitation entry = mCurrReader.next();
        ++mEntryQty;
        HashMap<String, String>  fields = new HashMap<String, String>();
        
        fields.put(UtilConst.TAG_DOCNO, entry.getPmid() + ""); 
        String abstractText = entry.getAbstractText();
        String articleTitle = entry.getArticleTitle();
        
        fields.put(UtilConstMedline.ABSTRACT_TEXT_FIELD, abstractText);        
        fields.put(UtilConstMedline.ARTICLE_TITLE_FIELD, articleTitle);
        // We insert an extract space: thus entity positions will need to be updated
        fields.put(UtilConstMedline.ENTITIES_DESC_FIELD, abstractText + " " + articleTitle);
        
        JCas jcas;
        
        try {
          jcas = aCAS.getJCas();
        } catch (CASException e) {
          e.printStackTrace();
          throw new CollectionException(e);
        }
        
        jcas.setDocumentLanguage(DOCUMENT_LANGUAGE);
        try {
          jcas.setDocumentText(mXmlHelper.genXMLIndexEntry(fields));
        } catch (Exception e) {
          e.printStackTrace();
          throw new CollectionException(e);
        }
      }
    }
    
    throw new CollectionException(
          new Exception("Bug: getNext() called, but no next entry is available."));
  }
  @Override
  public boolean hasNext() throws IOException, CollectionException {
    if (mEntryQty >= mMaxEntryQty) return false;
      
    if (mCurrReader!= null) {
      return mCurrReader.hasNext();
    }
    if (mNextInpFileIndex < mInpFiles.size()) {
      try {
        mCurrReader = new MedlineCitationSetReader(
            CompressUtils.createInputStream(mInpFiles.get(mNextInpFileIndex++).getAbsolutePath()));
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
    // TODO Auto-generated method stub
    
  }
}
