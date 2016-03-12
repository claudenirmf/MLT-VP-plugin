package br.ufes.inf.nemo.mltplugin.model;

import java.util.HashMap;
import java.util.Iterator;

import br.ufes.inf.nemo.mltplugin.LogUtilitary;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.model.IAssociation;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IGeneralizationSet;

public class ModelManager {
	
	private static HashMap<String, ModelElementWrapper> modelCopyReference;
	
	public static void populateModel(){
		final Iterator<?> modelElementList = ApplicationManager
			.instance().getProjectManager().getProject().allLevelModelElementIterator();
		while (modelElementList.hasNext()) {
			final Object modelElement = modelElementList.next();
			if (modelElement instanceof IClass) {
				final ClassWrapper classElement = new ClassWrapper(
						(IClass) modelElement);
				getModelCopy().put(((IClass) modelElement).getId(),
						classElement);
				//LogUtilitary.log(classElement.report());
			} else if (modelElement instanceof IAssociation) {
				final AssociationWrapper associationElement = new AssociationWrapper(
						(IAssociation) modelElement);
				getModelCopy().put(((IAssociation) modelElement).getId(),
						associationElement);
				//LogUtilitary.log(associationElement.report());
			} else if (modelElement instanceof IGeneralizationSet) {
				final GeneralizationSetWrapper genSetElement = new GeneralizationSetWrapper(
						(IGeneralizationSet) modelElement);
				getModelCopy().put(((IGeneralizationSet) modelElement).getId(),
						genSetElement);
				//LogUtilitary.log(genSetElement.report());
			}
		}
		for (ModelElementWrapper element : getModelCopy().values()) {
			LogUtilitary.log(element.report());
		}
	}
	
	public static HashMap<String, ModelElementWrapper> getModelCopy(){
		if(modelCopyReference == null){
			modelCopyReference = new HashMap<String, ModelElementWrapper>();
		}
		return modelCopyReference;
	}
	
}
