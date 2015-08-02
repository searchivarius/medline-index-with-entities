
/* First created by JCasGen Sun Aug 02 04:02:07 EDT 2015 */
package edu.cmu.lti.oaqa.bio.index.medline.annotated.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Sun Aug 02 04:33:09 EDT 2015
 * @generated */
public class EntityConceptId_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (EntityConceptId_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = EntityConceptId_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new EntityConceptId(addr, EntityConceptId_Type.this);
  			   EntityConceptId_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new EntityConceptId(addr, EntityConceptId_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = EntityConceptId.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.lti.oaqa.bio.index.medline.annotated.types.EntityConceptId");
 
  /** @generated */
  final Feature casFeat_parent;
  /** @generated */
  final int     casFeatCode_parent;
  /** @generated */ 
  public int getParent(int addr) {
        if (featOkTst && casFeat_parent == null)
      jcas.throwFeatMissing("parent", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.EntityConceptId");
    return ll_cas.ll_getRefValue(addr, casFeatCode_parent);
  }
  /** @generated */    
  public void setParent(int addr, int v) {
        if (featOkTst && casFeat_parent == null)
      jcas.throwFeatMissing("parent", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.EntityConceptId");
    ll_cas.ll_setRefValue(addr, casFeatCode_parent, v);}
    
  
 
  /** @generated */
  final Feature casFeat_bioConceptID;
  /** @generated */
  final int     casFeatCode_bioConceptID;
  /** @generated */ 
  public String getBioConceptID(int addr) {
        if (featOkTst && casFeat_bioConceptID == null)
      jcas.throwFeatMissing("bioConceptID", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.EntityConceptId");
    return ll_cas.ll_getStringValue(addr, casFeatCode_bioConceptID);
  }
  /** @generated */    
  public void setBioConceptID(int addr, String v) {
        if (featOkTst && casFeat_bioConceptID == null)
      jcas.throwFeatMissing("bioConceptID", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.EntityConceptId");
    ll_cas.ll_setStringValue(addr, casFeatCode_bioConceptID, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public EntityConceptId_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_parent = jcas.getRequiredFeatureDE(casType, "parent", "uima.tcas.Annotation", featOkTst);
    casFeatCode_parent  = (null == casFeat_parent) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_parent).getCode();

 
    casFeat_bioConceptID = jcas.getRequiredFeatureDE(casType, "bioConceptID", "uima.cas.String", featOkTst);
    casFeatCode_bioConceptID  = (null == casFeat_bioConceptID) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_bioConceptID).getCode();

  }
}



    