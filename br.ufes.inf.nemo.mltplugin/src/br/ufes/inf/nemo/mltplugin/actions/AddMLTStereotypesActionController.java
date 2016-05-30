package br.ufes.inf.nemo.mltplugin.actions;

import java.util.Iterator;

import br.ufes.inf.nemo.mltplugin.model.AssociationWrapper;
import br.ufes.inf.nemo.mltplugin.model.ClassWrapper;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import com.vp.plugin.model.IStereotype;
import com.vp.plugin.model.factory.IModelElementFactory;

public class AddMLTStereotypesActionController implements VPActionController {

	@Override
	public void performAction(VPAction arg0) {
		Iterator<?> projectStereotypes =
				ApplicationManager.instance().getProjectManager().getProject()
				.allLevelModelElementIterator(IModelElementFactory.MODEL_TYPE_STEREOTYPE);
		
		boolean hasInstantiationStr = false;
		boolean hasPowerTypeStr = false;
		while(projectStereotypes.hasNext()){
			switch (((IStereotype) projectStereotypes.next()).getName()) {
			case ClassWrapper.POWERTYPE_STR:
				System.out.println("Project has already the stereotype <<"
						+ClassWrapper.POWERTYPE_STR+">>.");
				hasPowerTypeStr = true;
				break;
			case AssociationWrapper.INSTANTIATION_STR:
				System.out.println("Project has already the stereotype <<"
						+AssociationWrapper.INSTANTIATION_STR+">>.");
				hasInstantiationStr = true;
				break;
			default:
				break;
			}
		}
		
		if (!hasPowerTypeStr) {
			final IStereotype str = IModelElementFactory.instance().createStereotype();
			str.setName(ClassWrapper.POWERTYPE_STR);
			str.setBaseType(IModelElementFactory.MODEL_TYPE_CLASS);
			System.out.println("The stereotype <<"
					+ClassWrapper.POWERTYPE_STR+">> was added to the project.");
		}
		if (!hasInstantiationStr) {
			final IStereotype str = IModelElementFactory.instance().createStereotype();
			str.setName(AssociationWrapper.INSTANTIATION_STR);
			str.setBaseType(IModelElementFactory.MODEL_TYPE_ASSOCIATION);
			System.out.println("The stereotype <<"
					+AssociationWrapper.INSTANTIATION_STR+">> was added to the project.");
		}
	}

	@Override
	public void update(VPAction action) {}

}
