package br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.ufes.inf.nemo.vp_ontouml_plugin.OntoUMLPlugin;
import br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.ModelValidator;
import br.ufes.inf.nemo.vp_ontouml_plugin.ufo_a.AntiRigidClass;
import br.ufes.inf.nemo.vp_ontouml_plugin.ufo_a.ModeClass;
import br.ufes.inf.nemo.vp_ontouml_plugin.ufo_a.QualityClass;
import br.ufes.inf.nemo.vp_ontouml_plugin.ufo_a.RelatorClass;
import br.ufes.inf.nemo.vp_ontouml_plugin.ufo_a.SortalClass;
import br.ufes.inf.nemo.vp_ontouml_plugin.ufo_a.SubstantialClass;
import br.ufes.inf.nemo.vp_ontouml_plugin.ufo_a.UltimateSortalClass;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IGeneralization;
import com.vp.plugin.model.ISimpleRelationship;

public class OntoUMLClassEntity {

	private IClass classEntity=null;
	private List<OntoUMLClassEntity> supertypes;
	private List<OntoUMLClassEntity> subtypes;
	
	public OntoUMLClassEntity(IClass modelEntity) {
		classEntity = modelEntity;
		supertypes = null;
		subtypes = null;
	}
	
	public void setSupertypes(){
		supertypes = new ArrayList<OntoUMLClassEntity>();
		
		for (ISimpleRelationship relationship : classEntity.toToRelationshipArray()) {
			if (relationship instanceof IGeneralization) {
				IClass father = getSupertype((IGeneralization) relationship);
				supertypes
						.add(ModelValidator
								.getOntoUMLClassEntity(father.getName()));
			}
		}
	}

	public void setSubtypes(){
		subtypes = new ArrayList<OntoUMLClassEntity>();
		
		for (ISimpleRelationship relationship : classEntity.toFromRelationshipArray()) {
			if (relationship instanceof IGeneralization) {
				IClass son = getSubtype((IGeneralization) relationship);
				subtypes
						.add(ModelValidator
								.getOntoUMLClassEntity(son.getName()));
			}
		}
	}

	public IClass getClassEntity() {
		return classEntity;
	}
	
	private static IClass getSupertype(IGeneralization g){
		return (IClass) g.getFrom();
	}
	
	public void printSupertypes(){
		Iterator<OntoUMLClassEntity> iter = supertypes.iterator();
		while(iter.hasNext()){
			OntoUMLClassEntity ontoUMLClassEntity = iter.next();
			if (ontoUMLClassEntity != null) {
				OntoUMLPlugin.print(
						ontoUMLClassEntity.getClassEntity().getName() + " is my supertype."
						);
			} else {
				OntoUMLPlugin.print(
						"I have a null supertype."
						);
			}
		}
	}
	
	private IClass getSubtype(IGeneralization g) {
		return (IClass) g.getTo();
	}

	public boolean hasSortalSupertype(){
		Iterator<OntoUMLClassEntity> iter = supertypes.iterator();
		while(iter.hasNext()){
			OntoUMLClassEntity ontoUMLClassEntity = iter.next();
			if (ontoUMLClassEntity != null) {
				if (ontoUMLClassEntity instanceof SortalClass
						|| ontoUMLClassEntity.hasSortalSupertype()){
					return true;
				}
			}
		}
		return false;
	}
	
	public String getSortalSupertypes() {
		String msg="";
		if(this instanceof SortalClass){
			msg = classEntity.getName() + " " + msg;
		}
		for (OntoUMLClassEntity ontoUMLClassEntity : supertypes) {
			ApplicationManager
			.instance()
			.getViewManager()
			.showMessage(
					"Beep" + supertypes.size(),"OntoUML");
			if(ontoUMLClassEntity!=null){
				msg = msg + ontoUMLClassEntity.getSortalSupertypes();
			}
		}
		return msg;
	}

	public boolean hasAntiRigidSupertype() {
		Iterator<OntoUMLClassEntity> iter = supertypes.iterator();
		while(iter.hasNext()){
			OntoUMLClassEntity ontoUMLClassEntity = iter.next();
			if (ontoUMLClassEntity != null) {
				if (ontoUMLClassEntity instanceof AntiRigidClass
						|| ontoUMLClassEntity.hasSortalSupertype()){
					return true;
				}
			}
		}
		return false;
	}

	public int identityGiversCount() {
		int nIdentityGivers=0;
		if(this instanceof UltimateSortalClass){
			nIdentityGivers++;
		}
		Iterator<OntoUMLClassEntity> iter = supertypes.iterator();
		while(iter.hasNext()){
			OntoUMLClassEntity ontoUMLClassEntity = iter.next();
			if (ontoUMLClassEntity != null) {
				nIdentityGivers += ontoUMLClassEntity.identityGiversCount();
			}
		}
		return nIdentityGivers;
	}

	public boolean hasNonSubstantialSupertype() {
		Iterator<OntoUMLClassEntity> iter = supertypes.iterator();
		while(iter.hasNext()){
			OntoUMLClassEntity ontoUMLClassEntity = iter.next();
			if (ontoUMLClassEntity != null) {
				if (!(ontoUMLClassEntity instanceof SubstantialClass)
						|| ontoUMLClassEntity.hasNonSubstantialSupertype()){
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasNonRelatorSupertype() {
		Iterator<OntoUMLClassEntity> iter = supertypes.iterator();
		while(iter.hasNext()){
			OntoUMLClassEntity ontoUMLClassEntity = iter.next();
			if (ontoUMLClassEntity != null) {
				if (!(ontoUMLClassEntity instanceof RelatorClass)
						|| ontoUMLClassEntity.hasNonRelatorSupertype()){
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasNonModeSupertype() {
		Iterator<OntoUMLClassEntity> iter = supertypes.iterator();
		while(iter.hasNext()){
			OntoUMLClassEntity ontoUMLClassEntity = iter.next();
			if (ontoUMLClassEntity != null) {
				if (!(ontoUMLClassEntity instanceof ModeClass)
						|| ontoUMLClassEntity.hasNonModeSupertype()){
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasNonQualitySupertype() {
		Iterator<OntoUMLClassEntity> iter = supertypes.iterator();
		while(iter.hasNext()){
			OntoUMLClassEntity ontoUMLClassEntity = iter.next();
			if (ontoUMLClassEntity != null) {
				if (!(ontoUMLClassEntity instanceof QualityClass)
						|| ontoUMLClassEntity.hasNonQualitySupertype()){
					return true;
				}
			}
		}
		return false;
	}
	
}
