
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
 * Updated by JCasGen Sat Aug 01 23:01:04 EDT 2015
 * @generated */
public class EntityMeshId_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (EntityMeshId_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = EntityMeshId_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new EntityMeshId(addr, EntityMeshId_Type.this);
  			   EntityMeshId_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new EntityMeshId(addr, EntityMeshId_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = EntityMeshId.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.lti.oaqa.bio.index.medline.annotated.types.EntityMeshId");
 
  /** @generated */
  final Feature casFeat_parentId;
  /** @generated */
  final int     casFeatCode_parentId;
  /** @generated */ 
  public String getParentId(int addr) {
        if (featOkTst && casFeat_parentId == null)
      jcas.throwFeatMissing("parentId", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.EntityMeshId");
    return ll_cas.ll_getStringValue(addr, casFeatCode_parentId);
  }
  /** @generated */    
  public void setParentId(int addr, String v) {
        if (featOkTst && casFeat_parentId == null)
      jcas.throwFeatMissing("parentId", "edu.cmu.lti.oaqa.bio.index.medline.annotated.types.EntityMeshId");
    ll_cas.ll_setStringValue(addr, casFeatCode_parentId, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public EntityMeshId_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_parentId = jcas.getRequiredFeatureDE(casType, "parentId", "uima.cas.String", featOkTst);
    casFeatCode_parentId  = (null == casFeat_parentId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_parentId).getCode();

  }
}



    