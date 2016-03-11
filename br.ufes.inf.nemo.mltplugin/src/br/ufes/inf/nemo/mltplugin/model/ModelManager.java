package br.ufes.inf.nemo.mltplugin.model;

import java.util.Iterator;

import br.ufes.inf.nemo.mltplugin.LogUtilitary;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.model.IClass;

public class ModelManager {

	public static void populateModel(){
		final Iterator<?> modelElementList = ApplicationManager
			.instance().getProjectManager().getProject().allLevelModelElementIterator();
		while(modelElementList.hasNext()){
			final Object modelElement = modelElementList.next();
			if(modelElement instanceof IClass){
				final MLTClass classElement = new MLTClass((IClass) modelElement);
				LogUtilitary.log(classElement.report());
			}
		}
	}
}
