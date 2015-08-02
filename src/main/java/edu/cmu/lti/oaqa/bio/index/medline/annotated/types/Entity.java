

/* First created by JCasGen Sat Aug 01 23:01:03 EDT 2015 */
package edu.cmu.lti.oaqa.bio.index.medline.annotated.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sat Aug 01 23:01:03 EDT 2015
 * XML source: /home/leo/SourceTreeGit/medline-index-with-entities/src/main/resources/types/typeSystemDescriptor.xml
 * @generated */
public class Entity extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Entity.class);
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
  protected Entity() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Entity(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Entity(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Entity(JCas jcas, int begin, int end) {
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
  //* Feature: id

  /** getter for id - gets Entity ID
   * @generated */
  public String getId() {
    if (Entity_Type.featOkTst && ((Entity_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.Entity");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Entity_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets Entity ID 
   * @generated */
  public void setId(String v) {
    if (Entity_Type.featOkTst && ((Entity_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.Entity");
    jcasType.ll_cas.ll_setStringValue(addr, ((Entity_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: bioConcept

  /** getter for bioConcept - gets Entity type
   * @generated */
  public String getBioConcept() {
    if (Entity_Type.featOkTst && ((Entity_Type)jcasType).casFeat_bioConcept == null)
      jcasType.jcas.throwFeatMissing("bioConcept", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.Entity");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Entity_Type)jcasType).casFeatCode_bioConcept);}
    
  /** setter for bioConcept - sets Entity type 
   * @generated */
  public void setBioConcept(String v) {
    if (Entity_Type.featOkTst && ((Entity_Type)jcasType).casFeat_bioConcept == null)
      jcasType.jcas.throwFeatMissing("bioConcept", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.Entity");
    jcasType.ll_cas.ll_setStringValue(addr, ((Entity_Type)jcasType).casFeatCode_bioConcept, v);}    
  }

    