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

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;

public class SentenceSplitter extends JCasAnnotator_ImplBase {
  private AnalysisEngine mEngine;
  private String         mViewName;

  @Override
  public void initialize(UimaContext context) throws ResourceInitializationException{
    AnalysisEngineDescription desc 
          = createEngineDescription(createEngineDescription(StanfordSegmenter.class));
    
    mViewName = (String)context.getConfigParameterValue("ViewName");
    
    mEngine = createEngine(desc);
  }
  
  @Override
  public void process(JCas origJCas) throws AnalysisEngineProcessException {
    JCas jcas = null;
    try {
      jcas = mViewName == null ? origJCas : origJCas.getView(mViewName);
    } catch (CASException e) {
      e.printStackTrace();
      throw new AnalysisEngineProcessException(e);
    }
    
    mEngine.process(jcas);
  }  
}
