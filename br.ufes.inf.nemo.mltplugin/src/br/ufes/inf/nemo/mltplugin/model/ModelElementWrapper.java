package br.ufes.inf.nemo.mltplugin.model;

import com.vp.plugin.model.IAssociation;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IGeneralization;
import com.vp.plugin.model.IGeneralizationSet;
import com.vp.plugin.model.IModelElement;

public class ModelElementWrapper {
	
	private final IModelElement sourceEntity;
	
	public static ModelElementWrapper wrapThis(IModelElement sourceEntity){
		if(sourceEntity instanceof IClass){
			return new ClassWrapper((IClass) sourceEntity);
		} else if(sourceEntity instanceof IAssociation){
			return new AssociationWrapper((IAssociation) sourceEntity);
		} else if(sourceEntity instanceof IGeneralization){
			return new GeneralizationWrapper((IGeneralization) sourceEntity);
		}  else if(sourceEntity instanceof IGeneralizationSet){
			return new GeneralizationSetWrapper((IGeneralizationSet) sourceEntity);
		} else {
			return null;
		}
	}
	
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
	
	public String smallReport(){
		return getSourceEntity().getName()+", ID: "+getId();
	}
	
	public String getId(){
		return getSourceEntity().getId();
	}
	
	public void validate() {
	}

}
