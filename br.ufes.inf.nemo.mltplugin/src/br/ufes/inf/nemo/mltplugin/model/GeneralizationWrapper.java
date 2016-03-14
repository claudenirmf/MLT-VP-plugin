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
	
	public String getSuperTypeId() {
		return getSourceEntity().getFrom().getId();
	}
	
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

}
