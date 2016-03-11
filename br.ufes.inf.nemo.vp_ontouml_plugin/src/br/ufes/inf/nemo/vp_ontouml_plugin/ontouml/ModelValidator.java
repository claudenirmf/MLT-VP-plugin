package br.ufes.inf.nemo.vp_ontouml_plugin.ontouml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.ufes.inf.nemo.vp_ontouml_plugin.OntoUMLPlugin;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.Category;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.CollectiveKind;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.Kind;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.Mixin;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.ModeCategory;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.ModeKind;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.ModeMixin;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.ModePhaseMixin;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.ModeRoleMixin;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.OntoUMLClassEntity;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.Phase;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.PhaseMixin;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.QualityCategory;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.QualityKind;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.QualityMixin;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.QualityPhaseMixin;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.QualityRoleMixin;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.QuantityKind;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.RelatorCategory;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.RelatorKind;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.RelatorMixin;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.RelatorPhaseMixin;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.RelatorRoleMixin;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.Role;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.RoleMixin;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities.Subkind;
import br.ufes.inf.nemo.vp_ontouml_plugin.ufo_a.MixinClass;
import br.ufes.inf.nemo.vp_ontouml_plugin.ufo_a.ModeClass;
import br.ufes.inf.nemo.vp_ontouml_plugin.ufo_a.QualityClass;
import br.ufes.inf.nemo.vp_ontouml_plugin.ufo_a.RelatorClass;
import br.ufes.inf.nemo.vp_ontouml_plugin.ufo_a.RigidClass;
import br.ufes.inf.nemo.vp_ontouml_plugin.ufo_a.SemiRigidClass;
import br.ufes.inf.nemo.vp_ontouml_plugin.ufo_a.SortalClass;
import br.ufes.inf.nemo.vp_ontouml_plugin.ufo_a.SubstantialClass;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IProject;

public class ModelValidator {
	
	static public final String ONTOUML_STEREOTYPE_KIND = "kind";
	static public final String ONTOUML_STEREOTYPE_COLLECTIVE_KIND = "collectiveKind";
	static public final String ONTOUML_STEREOTYPE_QUANTITY_KIND = "quantityKind";
	static public final String ONTOUML_STEREOTYPE_RELATOR_KIND = "relatorKind";
	static public final String ONTOUML_STEREOTYPE_MODE_KIND = "modeKind";
	static public final String ONTOUML_STEREOTYPE_QUALITY_KIND = "qualityKind";
	static public final String ONTOUML_STEREOTYPE_SUBKIND = "subkind";
	static public final String ONTOUML_STEREOTYPE_PHASE = "phase";
	static public final String ONTOUML_STEREOTYPE_ROLE = "role";
	static public final String ONTOUML_STEREOTYPE_CATEGORY = "category";
	static public final String ONTOUML_STEREOTYPE_MIXIN = "mixin";
	static public final String ONTOUML_STEREOTYPE_PHASE_MIXIN = "phaseMixin";
	static public final String ONTOUML_STEREOTYPE_ROLE_MIXIN = "roleMixin";
	static public final String ONTOUML_STEREOTYPE_RELATOR_CATEGORY = "relatorCategory";
	static public final String ONTOUML_STEREOTYPE_RELATOR_MIXIN = "relatorMixin";
	static public final String ONTOUML_STEREOTYPE_RELATOR_PHASE_MIXIN = "relatorPhaseMixin";
	static public final String ONTOUML_STEREOTYPE_RELATOR_ROLE_MIXIN = "relatorRoleMixin";
	static public final String ONTOUML_STEREOTYPE_MODE_CATEGORY = "modeCategory";
	static public final String ONTOUML_STEREOTYPE_MODE_MIXIN = "modeMixin";
	static public final String ONTOUML_STEREOTYPE_MODE_PHASE_MIXIN = "modePhaseMixin";
	static public final String ONTOUML_STEREOTYPE_MODE_ROLE_MIXIN = "modeRoleMixin";
	static public final String ONTOUML_STEREOTYPE_QUALITY_CATEGORY = "qualityCategory";
	static public final String ONTOUML_STEREOTYPE_QUALITY_MIXIN = "qualityMixin";
	static public final String ONTOUML_STEREOTYPE_QUALITY_PHASE_MIXIN = "qualityPhaseMixin";
	static public final String ONTOUML_STEREOTYPE_QUALITY_ROLE_MIXIN = "qualityRoleMixin";
	
	static private HashMap<String, OntoUMLClassEntity> classEntitiesTable;
	
	public static void validate() {
		classEntitiesTable = new HashMap<String, OntoUMLClassEntity>();
		generateOntoUMLClassEntitiesTable();
		setSupertypes();
		setSubtypes();
		for(OntoUMLClassEntity entity : classEntitiesTable.values()){
			// Check for rules.
			checkMixinForSortalSupertype(entity);
			checkMixinForAbstractProperty(entity);
			checkRigidForAntiRigidSupertype(entity);
			checkSemiRigidForAntiRigidSupertype(entity);
			checkSortalForUniqueUltimateSortalSupertype(entity);
			checkSubstantialForNonSubstantialSupertype(entity);
			checkRelatorForNonRelatorSupertype(entity);
			checkModeForNonModeSupertype(entity);
			checkQualityForNonQualitySupertype(entity);
			//checkHasNoSortalSpecialization(entity);		//Is this a problem?
		}
	}
	
	private static void checkQualityForNonQualitySupertype(
			OntoUMLClassEntity entity) {
		if(!(entity instanceof QualityClass)){
			return ;
		} else {
			if (entity.hasNonQualitySupertype()) {
				OntoUMLPlugin.print("Error010: quality entity "
						+ entity.getClassEntity().getName()
						+ " has supertypes which are not qualities.");
			}
		}
	}

	private static void checkModeForNonModeSupertype(OntoUMLClassEntity entity) {
		if(!(entity instanceof ModeClass)){
			return ;
		} else {
			if (entity.hasNonModeSupertype()) {
				OntoUMLPlugin.print("Error009: mode entity "
						+ entity.getClassEntity().getName()
						+ " has supertypes which are not modes.");
			}
		}
	}

	private static void checkRelatorForNonRelatorSupertype(
			OntoUMLClassEntity entity) {
		if(!(entity instanceof RelatorClass)){
			return ;
		} else {
			if (entity.hasNonRelatorSupertype()) {
				OntoUMLPlugin.print("Error008: relator entity "
						+ entity.getClassEntity().getName()
						+ " has supertypes which are not relators.");
			}
		}
	}

	private static void checkSubstantialForNonSubstantialSupertype(
			OntoUMLClassEntity entity) {
		if(!(entity instanceof SubstantialClass)){
			return ;
		} else {
			if (entity.hasNonSubstantialSupertype()) {
				OntoUMLPlugin.print("Error007: substantial entity "
						+ entity.getClassEntity().getName()
						+ " has supertypes which are not substantials.");
			}
		}
	}

	private static void checkSortalForUniqueUltimateSortalSupertype(
			OntoUMLClassEntity entity) {
		if(!(entity instanceof SortalClass)){
			return ;
		} else {
			int nIdentityGivers = entity.identityGiversCount();
			
			if (nIdentityGivers == 0) {
				OntoUMLPlugin.print("Error005: sortal entity "
						+ entity.getClassEntity().getName()
						+ " has no identity principle.");
			} else if(nIdentityGivers > 1){
				OntoUMLPlugin.print("Error006: sortal entity "
						+ entity.getClassEntity().getName()
						+ " has multiple identity principles.");
			}
		}
	}

	private static void checkSemiRigidForAntiRigidSupertype(
			OntoUMLClassEntity entity) {
		if(!(entity instanceof SemiRigidClass)){
			return ;
		} else {
			if (entity.hasAntiRigidSupertype()) {
				OntoUMLPlugin.print("Error004: semi-rigid entity "
						+ entity.getClassEntity().getName()
						+ " has anti-rigid supertypes.");
			}
		}
	}
	
	private static void checkRigidForAntiRigidSupertype(
			OntoUMLClassEntity entity) {
		if(!(entity instanceof RigidClass)){
			return ;
		} else {
			if (entity.hasAntiRigidSupertype()) {
				OntoUMLPlugin.print("Error003: rigid entity "
						+ entity.getClassEntity().getName()
						+ " has anti-rigid supertypes.");
			}
		}
	}

	private static void checkMixinForAbstractProperty(OntoUMLClassEntity entity) {
		if(!(entity instanceof MixinClass)){
			return ;
		} else {
			if(!entity.getClassEntity().isAbstract()){
				OntoUMLPlugin.print("Error002: mixim entity "
						+ entity.getClassEntity().getName()
						+ " must have isAbstract = true.");
			}
		}
	}

	private static void checkMixinForSortalSupertype(OntoUMLClassEntity entity) {
		if(!(entity instanceof MixinClass)){
			return ;
		} else {
			if (entity.hasSortalSupertype()) {
				OntoUMLPlugin.print("Error001: mixim entity "
						+ entity.getClassEntity().getName()
						+ " has sortal supertypes.");
			}
		}
	}

	private static void setSubtypes() {
		for (OntoUMLClassEntity iterable_element : classEntitiesTable.values()) {
			iterable_element.setSubtypes();
		}
	}

	private static void setSupertypes() {
		for (OntoUMLClassEntity iterable_element : classEntitiesTable.values()) {
			iterable_element.setSupertypes();
		}
	}

	private static void generateOntoUMLClassEntitiesTable() {
		String[] stereotypes;
		OntoUMLClassEntity ontoUMLClassEntity;
		List<IClass> nonOntoUMLValidEntities = new ArrayList<IClass>();
		IProject project = ApplicationManager.instance().getProjectManager()
				.getProject();
		@SuppressWarnings("unchecked")
		Iterator<IModelElement> iter = project.allLevelModelElementIterator();
		
		while (iter.hasNext()) {
			IModelElement modelElement = (IModelElement) iter.next();
			if (modelElement instanceof IClass) {
				stereotypes = ((IClass) modelElement).toStereotypeArray();
				ontoUMLClassEntity = null;
				
				if(stereotypes.length == 1){					
					if(stereotypes[0].equals(ONTOUML_STEREOTYPE_CATEGORY)){
						ontoUMLClassEntity = new Category((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_COLLECTIVE_KIND)){
						ontoUMLClassEntity = new CollectiveKind((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_KIND)){
						ontoUMLClassEntity = new Kind((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_MIXIN)){
						ontoUMLClassEntity = new Mixin((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_MODE_CATEGORY)){
						ontoUMLClassEntity = new ModeCategory((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_MODE_KIND)){
						ontoUMLClassEntity = new ModeKind((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_MODE_MIXIN)){
						ontoUMLClassEntity = new ModeMixin((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_MODE_PHASE_MIXIN)){
						ontoUMLClassEntity = new ModePhaseMixin((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_MODE_ROLE_MIXIN)){
						ontoUMLClassEntity = new ModeRoleMixin((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_PHASE)){
						ontoUMLClassEntity = new Phase((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_PHASE_MIXIN)){
						ontoUMLClassEntity = new PhaseMixin((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_QUALITY_CATEGORY)){
						ontoUMLClassEntity = new QualityCategory((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_QUALITY_KIND)){
						ontoUMLClassEntity = new QualityKind((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_QUALITY_MIXIN)){
						ontoUMLClassEntity = new QualityMixin((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_QUALITY_PHASE_MIXIN)){
						ontoUMLClassEntity = new QualityPhaseMixin((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_QUALITY_ROLE_MIXIN)){
						ontoUMLClassEntity = new QualityRoleMixin((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_QUANTITY_KIND)){
						ontoUMLClassEntity = new QuantityKind((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_RELATOR_CATEGORY)){
						ontoUMLClassEntity = new RelatorCategory((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_RELATOR_KIND)){
						ontoUMLClassEntity = new RelatorKind((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_RELATOR_MIXIN)){
						ontoUMLClassEntity = new RelatorMixin((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_RELATOR_PHASE_MIXIN)){
						ontoUMLClassEntity = new RelatorPhaseMixin((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_RELATOR_ROLE_MIXIN)){
						ontoUMLClassEntity = new RelatorRoleMixin((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_ROLE)){
						ontoUMLClassEntity = new Role((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_ROLE_MIXIN)){
						ontoUMLClassEntity = new RoleMixin((IClass) modelElement);
					} else if(stereotypes[0].equals(ONTOUML_STEREOTYPE_SUBKIND)){
						ontoUMLClassEntity = new Subkind((IClass) modelElement);
					}
				}
				if(ontoUMLClassEntity!=null){
					classEntitiesTable.put(ontoUMLClassEntity.getClassEntity()
							.getName(), ontoUMLClassEntity);
				} else {
					nonOntoUMLValidEntities.add((IClass) modelElement);
				}
			}
		}
		msgNonValidEntities(nonOntoUMLValidEntities);
	}

	private static void msgNonValidEntities(List<IClass> nonOntoUMLValidEntities) {
		if(nonOntoUMLValidEntities.size() > 0){
			String msg="";
			for (IClass iClass : nonOntoUMLValidEntities) {
				msg = msg + iClass.getName() + " ";
			}
			ApplicationManager
					.instance()
					.getViewManager()
					.showMessage(
							"OntoUML Classes must have one, and only one, stereotype from its set.",
							"OntoUML");
			ApplicationManager
					.instance()
					.getViewManager()
					.showMessage(
							"The following classes are invalid according to OntoUML: " + msg,
							"OntoUML");
		}
	}

	public static OntoUMLClassEntity getOntoUMLClassEntity(String name){
		return classEntitiesTable.get(name);
	}

	public static List<String> getOntoUMLClassStereotypes() {
		List<String> list = new ArrayList<String>();
		list.add(ONTOUML_STEREOTYPE_CATEGORY);
		list.add(ONTOUML_STEREOTYPE_COLLECTIVE_KIND);
		list.add(ONTOUML_STEREOTYPE_KIND);
		list.add(ONTOUML_STEREOTYPE_MIXIN);
		list.add(ONTOUML_STEREOTYPE_MODE_CATEGORY);
		list.add(ONTOUML_STEREOTYPE_MODE_KIND);
		list.add(ONTOUML_STEREOTYPE_MODE_MIXIN);
		list.add(ONTOUML_STEREOTYPE_MODE_PHASE_MIXIN);
		list.add(ONTOUML_STEREOTYPE_MODE_ROLE_MIXIN);
		list.add(ONTOUML_STEREOTYPE_PHASE);
		list.add(ONTOUML_STEREOTYPE_PHASE_MIXIN);
		list.add(ONTOUML_STEREOTYPE_QUALITY_CATEGORY);
		list.add(ONTOUML_STEREOTYPE_QUALITY_KIND);
		list.add(ONTOUML_STEREOTYPE_QUALITY_MIXIN);
		list.add(ONTOUML_STEREOTYPE_QUALITY_PHASE_MIXIN);
		list.add(ONTOUML_STEREOTYPE_QUALITY_ROLE_MIXIN);
		list.add(ONTOUML_STEREOTYPE_QUANTITY_KIND);
		list.add(ONTOUML_STEREOTYPE_RELATOR_CATEGORY);
		list.add(ONTOUML_STEREOTYPE_RELATOR_KIND);
		list.add(ONTOUML_STEREOTYPE_RELATOR_MIXIN);
		list.add(ONTOUML_STEREOTYPE_RELATOR_PHASE_MIXIN);
		list.add(ONTOUML_STEREOTYPE_RELATOR_ROLE_MIXIN);
		list.add(ONTOUML_STEREOTYPE_ROLE);
		list.add(ONTOUML_STEREOTYPE_ROLE_MIXIN);
		list.add(ONTOUML_STEREOTYPE_SUBKIND);
		return list;
	}

}
