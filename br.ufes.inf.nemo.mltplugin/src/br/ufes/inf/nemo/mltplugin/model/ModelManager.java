package br.ufes.inf.nemo.mltplugin.model;

import java.util.HashMap;
import java.util.Iterator;

import br.ufes.inf.nemo.mltplugin.LogUtilitary;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.model.IModelElement;

public class ModelManager {
	
	private static HashMap<String, ModelElementWrapper> modelCopyReference;
	
	public static void populateModel(){
		final Iterator<?> modelElementList = ApplicationManager
			.instance().getProjectManager().getProject().allLevelModelElementIterator();
		while (modelElementList.hasNext()) {
			final ModelElementWrapper wrappedElement = ModelElementWrapper
					.wrapThis((IModelElement) modelElementList.next());
			if(wrappedElement!=null){
				getModelCopy().put(wrappedElement.getId(), wrappedElement);
			}
		}
		for (ModelElementWrapper element : getModelCopy().values()) {
			if(element instanceof ClassWrapper){
				((ClassWrapper) element).loadOrder();
//				LogUtilitary.log(((ClassWrapper) element).smallReportC());
			} else {
//				LogUtilitary.log(element.smallReport());
			}
		}
		for (ModelElementWrapper element : getModelCopy().values()) {
			LogUtilitary.log(element.smallReport());
			element.validate();
		}
	}
	
	public static HashMap<String, ModelElementWrapper> getModelCopy(){
		if(modelCopyReference == null){
			modelCopyReference = new HashMap<String, ModelElementWrapper>();
		}
		return modelCopyReference;
	}
	
	public static ModelElementWrapper getModelElementWrapper(String id) {
//		LogUtilitary.log("getModelElementWrapper " + getModelCopy().get(id).getId());
		return getModelCopy().get(id);
	}
	
	public static void resetModelCopy() {
		getModelCopy().clear();
	}
	
}
