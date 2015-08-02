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

import java.util.Map;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;

import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.oaqa.annographix.solr.UtilConst;
import edu.cmu.lti.oaqa.annographix.util.XmlHelper;

public class CreateView4AnnotText extends JCasAnnotator_ImplBase {
  private String    viewName      = null;
  private String    textFieldName = null;
  
  @Override
  public void initialize(UimaContext aContext)
  throws ResourceInitializationException {
    super.initialize(aContext);
    
    viewName = (String) aContext.getConfigParameterValue(UtilConst.CONFIG_VIEW_NAME);
    
    if (viewName == null)
      throw new ResourceInitializationException(
                    new Exception("Missing parameter: " + UtilConst.CONFIG_VIEW_NAME));
    
    textFieldName = (String) aContext.getConfigParameterValue(UtilConst.CONFIG_TEXT4ANNOT_FIELD);
    
    if (textFieldName == null)
      throw new ResourceInitializationException(
                    new Exception("Missing parameter: " + UtilConst.CONFIG_TEXT4ANNOT_FIELD));
  }
  
  @Override
  public void process(JCas oldJCas) throws AnalysisEngineProcessException {
    try {
      // Let's parse the document and extract the XML
      String oldText = oldJCas.getDocumentText();
      
      Map<String, String> doc = XmlHelper.parseXMLIndexEntry(oldText);

      JCas newJCas = oldJCas.createView(viewName);
      String docText = doc.get(textFieldName);
      if (docText == null) {
        throw new AnalysisEngineProcessException(
              new Exception("No text field '" + textFieldName + "' is found in input XML.")
            );
      }
      newJCas.setDocumentText(docText);
    } catch (Exception e) {
      throw new AnalysisEngineProcessException(e);
    }   
  }

}
