package br.ufes.inf.nemo.mltplugin.model;

import com.vp.plugin.model.IModelElement;

public class ModelElementWrapper {
	
	private final IModelElement sourceEntity;
	
	ModelElementWrapper(IModelElement source){
		sourceEntity = source;
	}
	
	public IModelElement getSourceEntity(){
		return sourceEntity;
	}
	
	public String report(){
		return "Model Element(NAME="
			+getSourceEntity().getName()
			+", ID="
			+getSourceEntity().getId()
			+")";
	}
	
	public String getId(){
		return getSourceEntity().getId();
	}

}
