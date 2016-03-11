package br.ufes.inf.nemo.mltplugin.model;

import com.vp.plugin.model.IModelElement;

public class ModelElement {
	
	private final IModelElement sourceElement;
	
	ModelElement(IModelElement source){
		sourceElement = source;
	}
	
	public IModelElement getSourceElement(){
		return sourceElement;
	}
	
	public String report(){
		return "Model Element(NAME="
			+sourceElement.getName()
			+", ID="
			+sourceElement.getId()
			+")";
	}

}
