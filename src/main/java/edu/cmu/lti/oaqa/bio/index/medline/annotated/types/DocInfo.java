

/* First created by JCasGen Sun Aug 02 04:24:21 EDT 2015 */
package edu.cmu.lti.oaqa.bio.index.medline.annotated.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sun Aug 02 04:33:09 EDT 2015
 * XML source: /home/leo/SourceTreeGit/medline-index-with-entities/src/main/resources/types/typeSystemDescriptor.xml
 * @generated */
public class DocInfo extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DocInfo.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected DocInfo() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public DocInfo(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public DocInfo(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public DocInfo(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: pmid

  /** getter for pmid - gets document ID
   * @generated */
  public String getPmid() {
    if (DocInfo_Type.featOkTst && ((DocInfo_Type)jcasType).casFeat_pmid == null)
      jcasType.jcas.throwFeatMissing("pmid", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.DocInfo");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DocInfo_Type)jcasType).casFeatCode_pmid);}
    
  /** setter for pmid - sets document ID 
   * @generated */
  public void setPmid(String v) {
    if (DocInfo_Type.featOkTst && ((DocInfo_Type)jcasType).casFeat_pmid == null)
      jcasType.jcas.throwFeatMissing("pmid", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.DocInfo");
    jcasType.ll_cas.ll_setStringValue(addr, ((DocInfo_Type)jcasType).casFeatCode_pmid, v);}    
  }

    