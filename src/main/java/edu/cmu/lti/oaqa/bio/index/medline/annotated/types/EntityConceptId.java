

/* First created by JCasGen Sun Aug 02 04:02:07 EDT 2015 */
package edu.cmu.lti.oaqa.bio.index.medline.annotated.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sun Aug 02 04:33:09 EDT 2015
 * XML source: /home/leo/SourceTreeGit/medline-index-with-entities/src/main/resources/types/typeSystemDescriptor.xml
 * @generated */
public class EntityConceptId extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(EntityConceptId.class);
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
  protected EntityConceptId() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public EntityConceptId(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public EntityConceptId(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public EntityConceptId(JCas jcas, int begin, int end) {
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
  //* Feature: parent

  /** getter for parent - gets An id of respective entity
   * @generated */
  public Annotation getParent() {
    if (EntityConceptId_Type.featOkTst && ((EntityConceptId_Type)jcasType).casFeat_parent == null)
      jcasType.jcas.throwFeatMissing("parent", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.EntityConceptId");
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((EntityConceptId_Type)jcasType).casFeatCode_parent)));}
    
  /** setter for parent - sets An id of respective entity 
   * @generated */
  public void setParent(Annotation v) {
    if (EntityConceptId_Type.featOkTst && ((EntityConceptId_Type)jcasType).casFeat_parent == null)
      jcasType.jcas.throwFeatMissing("parent", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.EntityConceptId");
    jcasType.ll_cas.ll_setRefValue(addr, ((EntityConceptId_Type)jcasType).casFeatCode_parent, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: bioConceptID

  /** getter for bioConceptID - gets 
   * @generated */
  public String getBioConceptID() {
    if (EntityConceptId_Type.featOkTst && ((EntityConceptId_Type)jcasType).casFeat_bioConceptID == null)
      jcasType.jcas.throwFeatMissing("bioConceptID", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.EntityConceptId");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EntityConceptId_Type)jcasType).casFeatCode_bioConceptID);}
    
  /** setter for bioConceptID - sets  
   * @generated */
  public void setBioConceptID(String v) {
    if (EntityConceptId_Type.featOkTst && ((EntityConceptId_Type)jcasType).casFeat_bioConceptID == null)
      jcasType.jcas.throwFeatMissing("bioConceptID", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.EntityConceptId");
    jcasType.ll_cas.ll_setStringValue(addr, ((EntityConceptId_Type)jcasType).casFeatCode_bioConceptID, v);}    
  }

    