package br.ufes.inf.nemo.mltplugin.live;

import br.ufes.inf.nemo.mltplugin.LogUtilitary;

import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IProject;
import com.vp.plugin.model.IProjectModelListener;

public class ProjectModelListener implements IProjectModelListener {

	@Override
	public void modelAdded(IProject project, IModelElement modelElement) {
		LogUtilitary.log("Element '"+modelElement.getName()
				+"' added to project'"+project.getName()+"'.");
	}

	@Override
	public void modelRemoved(IProject project, IModelElement modelElement) {
		LogUtilitary.log("Element '"+modelElement.getName()
				+"' removed from project'"+project.getName()+"'.");
	}

}
