package edu.cmu.lti.oaqa.bio.index.medline.annotated.utils;

import edu.cmu.lti.oaqa.annographix.solr.UtilConst;

public class UtilConstMedline {
  public static final String PMID_FIELD = "pmid";
  public static final String ARTICLE_TITLE_FIELD      = "articleTitle";
  public static final String ABSTRACT_TEXT_FIELD      = "abstractText";
  public static final String ENTITIES_DESC_FIELD      = "entityDesc";
  
  public static final String ANNOT_VIEW_NAME        = "annot";
  public static final String ANNOT_DESC_VIEW_NAME   = "annot_desc"; 
  
  public static final String CONCEPT_INDEX_PREFIX   = "concept";
  public static final String CONCEPTID_INDEX_PREFIX = "conceptid";
  
  public static final String TEXT4ANNOT_FIELD = UtilConst.DEFAULT_TEXT4ANNOT_FIELD;
  public static final String ANNOT_FIELD = UtilConst.DEFAULT_ANNOT_FIELD;
}
