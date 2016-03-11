package br.ufes.inf.nemo.vp_ontouml_plugin.actions;

import java.util.ArrayList;
import java.util.List;

import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.ModelValidator;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.ViewManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IProject;
import com.vp.plugin.model.IStereotype;
import com.vp.plugin.model.factory.IModelElementFactory;

public class DeployOntoUMLStereotypes implements VPActionController {

	@Override
	public void performAction(VPAction action) {
		ViewManager viewManager = ApplicationManager.instance()
				.getViewManager();
		viewManager.showMessage("Deploying OntoUML Stereotypes...", "OntoUML");
		findOrCreateStereotype(ModelValidator.getOntoUMLClassStereotypes());
		viewManager.showMessage("Done.", "OntoUML");
	}

	@Override
	public void update(VPAction action) {
		// TODO Auto-generated method stub

	}

	private void findOrCreateStereotype(List<String> stereotypes) {
		IProject lProject = ApplicationManager.instance().getProjectManager()
				.getProject();
		IModelElement[] lStereotypes = lProject
				.toAllLevelModelElementArray(IModelElementFactory.MODEL_TYPE_STEREOTYPE);
		List<String> projectStereotypesNames = new ArrayList<String>();
		int lStereotypeCount = lStereotypes == null ? 0 : lStereotypes.length;

		for (int lStereotypeIndex = 0; lStereotypeIndex < lStereotypeCount; lStereotypeIndex++) {
			IStereotype lStereotype = (IStereotype) lStereotypes[lStereotypeIndex];
			if (IModelElementFactory.MODEL_TYPE_CLASS.equals(lStereotype
					.getBaseType())) {
				projectStereotypesNames.add(lStereotype.getName());
			}
		}

		for (String string : stereotypes) {
			if (projectStereotypesNames.contains(string)) {
				// This project already have this stereotype
				ApplicationManager
						.instance()
						.getViewManager()
						.showMessage(
								"This project already have this stereotype ("
										+ string + ")", "OntoUML");
			} else {
				IStereotype lStereotype = IModelElementFactory.instance()
						.createStereotype();
				lStereotype.setName(string);
				lStereotype.setBaseType(IModelElementFactory.MODEL_TYPE_CLASS);
				// Added stereotype
				ApplicationManager
						.instance()
						.getViewManager()
						.showMessage(
								"Stereotype added (" + lStereotype.getName()
										+ ")", "OntoUML");
			}
		}
	}
}
