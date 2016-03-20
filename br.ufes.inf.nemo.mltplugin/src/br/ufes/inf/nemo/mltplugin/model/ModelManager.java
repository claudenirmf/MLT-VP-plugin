package br.ufes.inf.nemo.mltplugin.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import br.ufes.inf.nemo.mltplugin.LogUtilitary;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.model.IAssociationEnd;
import com.vp.plugin.model.IModelElement;

public class ModelManager {
	
	private static ConcurrentHashMap<String, ModelElementWrapper> modelCopyReference;
	
	public static void populateModel(){
		final Iterator<?> modelElementList = ApplicationManager
			.instance().getProjectManager().getProject().allLevelModelElementIterator();
		while (modelElementList.hasNext()) {
			final ModelElementWrapper wrappedElement = ModelElementWrapper
					.wrapThis((IModelElement) modelElementList.next());
			if(wrappedElement!=null){
				registerModelElementWrapper(wrappedElement.getId(), wrappedElement);
			}
		}
		for (ModelElementWrapper element : getModelCopy().values()) {
//			LogUtilitary.log(element.smallReport());
			System.out.println(element.smallReport());
			element.validate();
		}
	}
	
	private static ConcurrentHashMap<String, ModelElementWrapper> getModelCopy(){
		if(modelCopyReference == null){
			modelCopyReference = new ConcurrentHashMap<String, ModelElementWrapper>();
		}
		return modelCopyReference;
	}
	
	public synchronized static ModelElementWrapper getModelElementWrapper(String id) {
		return getModelCopy().get(id);
	}
	
	public synchronized static ModelElementWrapper registerModelElementWrapper(String id, ModelElementWrapper element) {
		return getModelCopy().put(id, element);
	}
	
	public synchronized static ModelElementWrapper unregisterModelElementWrapper(String id){
		return getModelCopy().remove(id);
	}
	
	public synchronized static void resetModelCopy() {
		getModelCopy().clear();
	}
	
	public static void main(String[] args) {
		System.out.println("ONE "+IAssociationEnd.MULTIPLICITY_ONE);
		System.out.println("ONE_TO_MANY "+IAssociationEnd.MULTIPLICITY_ZERO_TO_MANY);
		System.out.println("UNSPECIFIED "+IAssociationEnd.MULTIPLICITY_UNSPECIFIED);
		System.out.println("Z_TO_MANY "+IAssociationEnd.MULTIPLICITY_ZERO_TO_MANY);
	}
	
}
