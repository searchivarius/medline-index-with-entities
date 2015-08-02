

/* First created by JCasGen Sat Aug 01 23:01:04 EDT 2015 */
package edu.cmu.lti.oaqa.bio.index.medline.annotated.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sat Aug 01 23:01:04 EDT 2015
 * XML source: /home/leo/SourceTreeGit/medline-index-with-entities/src/main/resources/types/typeSystemDescriptor.xml
 * @generated */
public class EntityMeshId extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(EntityMeshId.class);
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
  protected EntityMeshId() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public EntityMeshId(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public EntityMeshId(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public EntityMeshId(JCas jcas, int begin, int end) {
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
  //* Feature: parentId

  /** getter for parentId - gets An id of respective entity
   * @generated */
  public String getParentId() {
    if (EntityMeshId_Type.featOkTst && ((EntityMeshId_Type)jcasType).casFeat_parentId == null)
      jcasType.jcas.throwFeatMissing("parentId", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.EntityMeshId");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EntityMeshId_Type)jcasType).casFeatCode_parentId);}
    
  /** setter for parentId - sets An id of respective entity 
   * @generated */
  public void setParentId(String v) {
    if (EntityMeshId_Type.featOkTst && ((EntityMeshId_Type)jcasType).casFeat_parentId == null)
      jcasType.jcas.throwFeatMissing("parentId", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.EntityMeshId");
    jcasType.ll_cas.ll_setStringValue(addr, ((EntityMeshId_Type)jcasType).casFeatCode_parentId, v);}    
  }

    