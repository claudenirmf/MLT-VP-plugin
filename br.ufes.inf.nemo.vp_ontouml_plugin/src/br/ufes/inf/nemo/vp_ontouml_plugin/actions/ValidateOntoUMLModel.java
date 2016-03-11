package br.ufes.inf.nemo.vp_ontouml_plugin.actions;

import java.util.Iterator;

import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.ModelValidator;

import com.vp.plugin.*;
import com.vp.plugin.action.*;
import com.vp.plugin.model.*;

public class ValidateOntoUMLModel implements VPActionController {
	
	public void performAction(VPAction aAction) {
		ViewManager viewManager = ApplicationManager.instance().getViewManager();
		viewManager.clearMessages("OntoUML");
		viewManager.showMessage("Validating OntoUML Entities...", "OntoUML");
		ModelValidator.validate();
		viewManager.showMessage("Done.","OntoUML");
	}
	
	@SuppressWarnings("unused")
	private void listModelElements(ViewManager viewManager, String id) {
		IProject project = ApplicationManager.instance().getProjectManager()
				.getProject();
		@SuppressWarnings("unchecked")
		Iterator<IModelElement> iter = project.allLevelModelElementIterator();

		while (iter.hasNext()) {
			IModelElement modelElement = iter.next();
			if (modelElement instanceof IClass) {
				String[] stereotypes = ((IClass) modelElement).toStereotypeArray();
				ISimpleRelationship[] relationsTo = ((IClass) modelElement).toToRelationshipArray();
				ISimpleRelationship[] relationsFrom = ((IClass) modelElement).toFromRelationshipArray();
				String stereotypesMsg="", relationsToMsg="", relationsFromMsg="";
				
				for (int i = 0; i < stereotypes.length; i++) {
					stereotypesMsg = stereotypesMsg + " - " + stereotypes[i];
				}
				for (int i = 0; i < relationsTo.length; i++) {
					relationsToMsg = relationsToMsg + " - " + relationsTo[i].getFrom().getName();
					if(relationsTo[i] instanceof IGeneralization){
						relationsToMsg = relationsToMsg + " (Generalization)";
					}
				}
				for (int i = 0; i < relationsFrom.length; i++) {
					relationsFromMsg = relationsFromMsg + " - " + relationsFrom[i].getTo().getName();
					if(relationsFrom[i] instanceof IGeneralization){
						relationsFromMsg = relationsFromMsg + " (Generalization)";
					}
				}
				
				viewManager.showMessage(modelElement.getName(), id);
				if(stereotypes.length > 0){
					viewManager.showMessage("    > Stereotype " + stereotypesMsg, id);
				}
				if(relationsTo.length > 0){
					viewManager.showMessage("    > Related to " + relationsToMsg, id);
				}
				if(relationsFrom.length > 0){
					viewManager.showMessage("    > Related From " + relationsFromMsg, id);
				}
				
			}
		}
		
	}

	public void update(VPAction aAction) {
	}

}
