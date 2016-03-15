package br.ufes.inf.nemo.mltplugin.model;

import br.ufes.inf.nemo.mltplugin.LogUtilitary;

import com.vp.plugin.model.IAssociation;
import com.vp.plugin.model.IAssociationEnd;
import com.vp.plugin.model.IModelElement;

public class AssociationWrapper extends ModelElementWrapper {
	
	public static final String INSTANTIATION_STR = "instantiation";
	
	public AssociationWrapper(IAssociation source) {
		super(source);
	}
	
	@Override
	public IAssociation getSourceEntity(){
		return (IAssociation) super.getSourceEntity();
	}

	public String getName() {
		return getSourceEntity().getName();
	}

	public String getTargetElementId() {
		final IModelElement ret = getSourceEntity().getTo();
		if(ret!=null){
			return ret.getId();
		}
		return null;
	}

	public String getTargetEndCardinality() {
		final IAssociationEnd ret = (IAssociationEnd) getSourceEntity().getToEnd();
		//LogUtilitary.log("getTargetEndCardinality "+ret.getMultiplicity());
		return ret.getMultiplicity();
	}

	public String getSourceElementId() {
		final IModelElement ret = getSourceEntity().getFrom();
		if (ret!=null) {
			return ret.getId();
		}
		return null;
	}

	public String getSourceEndCardinality() {
		final IAssociationEnd ret = (IAssociationEnd) getSourceEntity().getFromEnd();
		if (ret!=null) {
			return ret.getMultiplicity();
		}
		return null;
	}

	public String[] getStereotypeList() {
		return getSourceEntity().toStereotypeArray();
	}
	
	public boolean isInstantiation(){
		for (String stereotype : getStereotypeList()) {
//			LogUtilitary.log("isInstantion " + getId() + " STR " + stereotype);
			if (INSTANTIATION_STR.equals(stereotype)) {
//				report();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String report(){
		return "ASSOCIATION, NAME="+getName()
				+", ID="+getId()
				+", N_STR="+getStereotypeList().length
				+", TARGET_ID="+getTargetElementId()
				+", SOURCE_ID="+getSourceElementId();
	}
	
	@Override
	public void validate() {
//		LogUtilitary.log("validate");
		checkPowerTypeTarget();
//		checkDifferentOrderOnInstantiationRelation();
	}

//	private void checkDifferentOrderOnInstantiationRelation() {
//		if(!(getTargetElement().getOrder() == getSourceElement().getOrder()+1)){
//			LogUtilitary.log(
//					"ERROR: '"+getTargetElement().getName()
//					);
//		}
//	}

	/*
	 * Working just fine
	 */
	private void checkPowerTypeTarget() {
//		LogUtilitary.log("checkPowerTypeTarget");
		if(
			isInstantiation() &&
			getTargetElement().isPowertype() &&
			!IAssociationEnd.MULTIPLICITY_ONE_TO_MANY.equals(getTargetEndCardinality())
		) {
			LogUtilitary.log(
				"ERROR: At the instantion relation '"
				+getName()
				+"' the target multiplicity must be [1..*] because it conects to a power type ("
				+getTargetElement().getName()+")"
			);
		}
	}

	public ClassWrapper getTargetElement() {
		return (ClassWrapper) ModelManager.getModelElementWrapper(getTargetElementId());
	}

	public ClassWrapper getSourceElement() {
		return (ClassWrapper) ModelManager.getModelElementWrapper(getSourceElementId());
	}
	
}
