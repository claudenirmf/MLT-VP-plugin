package br.ufes.inf.nemo.mltplugin.model;

import java.util.ArrayList;
import java.util.List;

import br.ufes.inf.nemo.mltplugin.LogUtilitary;

import com.vp.plugin.model.IAssociation;
import com.vp.plugin.model.IAssociationEnd;
import com.vp.plugin.model.IGeneralization;
import com.vp.plugin.model.IGeneralizationSet;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IRelationshipEnd;

public class GeneralizationSetWrapper extends ModelElementWrapper {

	GeneralizationSetWrapper(IGeneralizationSet source) {
		super(source);
	}
	
	@Override
	public IGeneralizationSet getSourceEntity(){
		return (IGeneralizationSet) super.getSourceEntity();
	}
	
	public String getPowerTypeId(){
		final IModelElement powerType = getSourceEntity().getPowerType();
		if (powerType!=null) {
			return powerType.getId();
		}
		return null;
	}
	
	/*
	 * The method is null safe, because a generalization set will have at least one generalization
	 */
	public String getSuperTypeId(){
		return getSourceEntity().toGeneralizationArray()[0].getFrom().getId();
	}
	
	/*
	 * The method is null safe, because a generalization set will have at least one generalization
	 */
	public String[] getSubTypesId(){
		final List<String> ret = new ArrayList<String>();
		for (IGeneralization generalization : getSourceEntity().toGeneralizationArray()) {
			ret.add(generalization.getTo().getId());
		}
		return ret.toArray(new String[0]);
	}
	
	public boolean isDisjoint(){
		return getSourceEntity().isDisjoint();
	}
	
	public boolean isCovering(){
		return getSourceEntity().isCovering();
	}
	
	@Override
	public String report() {
		return "SET, ID="+getId()
				+", SUPER_ID="+getSuperTypeId()
				+", N_SUBTYPES="+getSubTypesId().length
				+", POWERTTYPE="+getPowerTypeId();
	}
	
	@Override
	public void validate() {
		checkMatchingUMLPowerTypeMLTCharacterizer();
		checkGeneralizationSetMetapropertiesByMultiplicity();
	}

	private void checkGeneralizationSetMetapropertiesByMultiplicity() {
		final ClassWrapper powerType = getPowerType();
		if(powerType!=null){
			AssociationWrapper association = getSuperType().getInstantiationRelationTo(powerType);
			if(association!=null){
				if (
					association.getTargetEndCardinality().endsWith("1") &&
					!isDisjoint()
				) {
					LogUtilitary.validationLog("ERROR: the generalization set '"
						+getName()+"' cannot be overlapping since the association end of '"
						+association.getName()+"'.upperbound == 1.");
				}
				if (
					association.getTargetEndCardinality().startsWith("0") &&
					isCovering()
				) {
					LogUtilitary.validationLog("ERROR: the generalization set '"
						+getName()+"' cannot be complete since the association end of '"
						+association.getName()+"'.lowerbound == 0.");
				}
			}
		}
	}

	public String getName() {
		return getSourceEntity().getName();
	}

	/*
	 * Working just fine
	 * 
	 * If the GS has a power type, checks if there is a <<instantion>>
	 * relation between the super type and the power type.
	 */
	private void checkMatchingUMLPowerTypeMLTCharacterizer() {
//		LogUtilitary.log("another");
		if(getPowerType().isPowertype()){
			LogUtilitary.validationLog("ERROR: '"+getPowerType().getName()
					+"' cannot classify a generalization set since it's a power type."
					);
		} else if(!getSuperType().isBaseTypeof(getPowerTypeId())){
			LogUtilitary.validationLog("ERROR: Missing <<instantitation>> between characterizer type ("
				+(getPowerType()==null ? "" : getPowerType().getName())+") and  base type ("
				+getSuperType().getName()+") in set("+getSourceEntity().getName()
				+" ID="+getId()+")");
		}
	}

	public ClassWrapper getSuperType() {
		return (ClassWrapper) ModelManager.getModelElementWrapper(getSuperTypeId());
	}

	public ClassWrapper getPowerType() {
		return (ClassWrapper) ModelManager.getModelElementWrapper(getPowerTypeId());
	}

}
