
/* First created by JCasGen Sat Aug 01 23:01:04 EDT 2015 */
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
 * Updated by JCasGen Sun Aug 02 04:02:09 EDT 2015
 * @generated */
public class Entity_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Entity_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Entity_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Entity(addr, Entity_Type.this);
  			   Entity_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Entity(addr, Entity_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Entity.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.lti.oaqa.bio.index.medline.annotated.types.Entity");
 
  /** @generated */
  final Feature casFeat_id;
  /** @generated */
  final int     casFeatCode_id;
  /** @generated */ 
  public int getId(int addr) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.Entity");
    return ll_cas.ll_getIntValue(addr, casFeatCode_id);
  }
  /** @generated */    
  public void setId(int addr, int v) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.Entity");
    ll_cas.ll_setIntValue(addr, casFeatCode_id, v);}
    
  
 
  /** @generated */
  final Feature casFeat_bioConcept;
  /** @generated */
  final int     casFeatCode_bioConcept;
  /** @generated */ 
  public String getBioConcept(int addr) {
        if (featOkTst && casFeat_bioConcept == null)
      jcas.throwFeatMissing("bioConcept", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.Entity");
    return ll_cas.ll_getStringValue(addr, casFeatCode_bioConcept);
  }
  /** @generated */    
  public void setBioConcept(int addr, String v) {
        if (featOkTst && casFeat_bioConcept == null)
      jcas.throwFeatMissing("bioConcept", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.Entity");
    ll_cas.ll_setStringValue(addr, casFeatCode_bioConcept, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Entity_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_id = jcas.getRequiredFeatureDE(casType, "id", "uima.cas.Integer", featOkTst);
    casFeatCode_id  = (null == casFeat_id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_id).getCode();

 
    casFeat_bioConcept = jcas.getRequiredFeatureDE(casType, "bioConcept", "uima.cas.String", featOkTst);
    casFeatCode_bioConcept  = (null == casFeat_bioConcept) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_bioConcept).getCode();

  }
}



    