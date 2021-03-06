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
package edu.cmu.lti.oaqa.bio.index.medline.annotated.auxindex;

import java.io.*;
import java.util.*;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.*;

import com.google.common.base.Splitter;

import edu.cmu.lti.oaqa.bio.index.medline.annotated.utils.CompressUtils;
import edu.cmu.lti.oaqa.bio.index.medline.annotated.utils.UtilConstMedline;

class TextLine {
  static Splitter splitOnPipe = Splitter.on('|');
  
  TextLine(String line) throws Exception {
    List<String> parts = splitOnPipe.splitToList(line);
    if (parts.size() != 3)
      throw new Exception("Wrong format of the line: '" + line + "'"
                          + " expected 3 '|'-separated parts, but got: " + parts.size());
    mID   = parts.get(0);
    mType = parts.get(1);
    mText = parts.get(2);
  }
  
  String mID, mType, mText;
}

/**
 * Store (mostly unparsed) BioConcept annotations in the form
 * a Lucene index, so that they can be retrieved by document ID.
 * 
 * @author Leonid Boytsov
 *
 */
public class CreateBioConceptLuceneIndex {
  private static Splitter mSplitOnTAB = Splitter.on('\t');
  private static final int BATCH_QTY = 50000;

  public static void Usage(String err) {
    System.err.println(err);
    System.err.println("Usage: <bioconcept annotation file> <Lucene index directory>");
    System.exit(1);
  }
  
  public static void main(String args[]) {
    
    if (args.length != 2) {
      Usage("Wrong number of arguments");
    }
    String inpFile = args[0];
    String luceneDirName = args[1];
   
    BufferedReader inp = null;
    
    int ln = 0;
    
    String line = "";

    File outputDir = new File(luceneDirName);
    if (!outputDir.exists()) {
      if (!outputDir.mkdirs()) {
        System.out.println("couldn't create " + outputDir.getAbsolutePath());
        System.exit(1);
      }
    }
    if (!outputDir.isDirectory()) {
      System.out.println(outputDir.getAbsolutePath() + " is not a directory!");
      System.exit(1);
    }
    if (!outputDir.canWrite()) {
      System.out.println("Can't write to " + outputDir.getAbsolutePath());
      System.exit(1);
    }

    
    try {
      Analyzer          analyzer = new WhitespaceAnalyzer();
      FSDirectory       indexDir    = FSDirectory.open(outputDir);
      IndexWriterConfig indexConf   = new IndexWriterConfig(analyzer.getVersion(), analyzer);
      
      System.out.println("Creating a new Lucene index.");
      indexConf.setOpenMode(OpenMode.CREATE);
      
      IndexWriter       indexWriter = new IndexWriter(indexDir, indexConf);
      
      inp = new BufferedReader(
                              new InputStreamReader(
                                  CompressUtils.createInputStream(inpFile)));
      
      int docQty = 0, docAddedQty = 0;
      
      while (true) {
        String titleText = inp.readLine(); ++ln;
        if (null == titleText) break;
        if (titleText.isEmpty()) continue;
        
/*
        if (null == titleText || titleText.isEmpty()) 
          throw new Exception(
                      String.format("Expected non-empty title line in line %d", ln));
*/
  
        TextLine titleParsed = null, abstractParsed = null;
        
        try {
          titleParsed = new TextLine(titleText);
          if (!titleParsed.mType.equals("t"))
            throw new Exception("The type of the title line ("+titleParsed.mType+") isn't 't'!");
          
          String docID = titleParsed.mID;
          
          String abstractText = inp.readLine(); ++ln;
          if (null == abstractText || abstractText.isEmpty())
            throw new Exception(
                String.format("Expected non-empty title line"));
  
          abstractParsed = new TextLine(abstractText);
          if (!abstractParsed.mType.equals("a"))
            throw new Exception("The type of the abstract line  ("+abstractParsed.mType+") isn't 'a'!");
          
          if (!abstractParsed.mID.equals(docID))
            throw new Exception("Ids of title and asbtract differ");
          
          StringBuffer sbEntities = new StringBuffer();
          
          line = inp.readLine();++ln;
          
          while (line != null && !line.isEmpty()) {
            List<String> parts = mSplitOnTAB.splitToList(line);
            if (parts.size() != 6) {
              System.err.println(
                  "The entity line is expected to have six TAB-separated fields, but it has "
                      + parts.size() + " line: '" + line.replaceAll("\\t", " <TAB> ") + "', ignoring invalid line # " + ln);
            } else {
              sbEntities.append(line);
              sbEntities.append('\n');
            }
            String tmpId = parts.get(0);
            if (!tmpId.equals(docID)) {
              System.err.println(("Different Id (" + tmpId + ") in the entity description, line # " + ln +
                                  " (expected the same Id=" + docID +" as in title&abstract," +
                                  "I am going to skip this garbled input till the next empty line is encountered."));
              
              line = inp.readLine();++ln;              
              while (line != null && !line.isEmpty()) {
                line = inp.readLine(); ++ln; 
              }
              break;              
            }
            line = inp.readLine(); ++ln;
          }
          
          { // Creating a Lucene document for non-empty annotation records
            Document luceneDoc = new Document();
            
            String entText = sbEntities.toString();
            
            if (entText.isEmpty()) {
//              System.out.println("Skipping empty entry for id=" + docID);
            } else {            
              luceneDoc.add(new StringField(UtilConstMedline.PMID_FIELD, docID, Field.Store.YES));
              luceneDoc.add(new StringField(UtilConstMedline.ARTICLE_TITLE_FIELD, titleParsed.mText, Field.Store.YES));
              luceneDoc.add(new StringField(UtilConstMedline.ABSTRACT_TEXT_FIELD, abstractParsed.mText, Field.Store.YES));
              luceneDoc.add(new StringField(UtilConstMedline.ENTITIES_DESC_FIELD, entText, Field.Store.YES));
              indexWriter.addDocument(luceneDoc);
              ++docAddedQty;
      
              if (docAddedQty % BATCH_QTY == 0) {
                indexWriter.commit();
                System.out.println(String.format("Added %d entries out of %d", docAddedQty, docQty));
              }
              
            }
            
            ++docQty;
          }
          
          if (line == null) break;
        } catch (Exception e) {
          throw new Exception("Error in line " + ln + ": " + e);
        }
      }
      
      indexWriter.commit();
      indexWriter.close();
      
      System.out.println(String.format("Added %d entries out of %d", docAddedQty, docQty));

      
      inp.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    } 
  }
}
