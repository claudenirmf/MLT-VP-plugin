package br.ufes.inf.nemo.mltplugin.model;

import com.vp.plugin.model.IGeneralization;

public class GeneralizationWrapper extends ModelElementWrapper {

	GeneralizationWrapper(IGeneralization sourceEntity) {
		super(sourceEntity);
	}
	
	@Override
	public IGeneralization getSourceEntity() {
		return (IGeneralization) super.getSourceEntity();
	}
	
	public String getName() {
		return getSourceEntity().getName();
	}
	
	/*
	 * This method is null-safe because generalizations must have a super type.
	 */
	public String getSuperTypeId() {
		return getSourceEntity().getFrom().getId();
	}
	
	/*
	 * This method is null-safe because generalizations must have a sub type.
	 */
	public String getSubTypeId() {
		return getSourceEntity().getTo().getId();
	}
	
	@Override
	public String report() {
		return "GENERALIZATION, NAME="+getName()
				+", ID="+getId()
				+", SUPER="+getSuperTypeId()
				+", SUB="+getSubTypeId();
	}

	public ClassWrapper getSuperType() {
		return (ClassWrapper) ModelManager.getModelElementWrapper(getSuperTypeId());
	}

}
