package br.ufes.inf.nemo.mltplugin.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import br.ufes.inf.nemo.mltplugin.LogUtilitary;

import com.vp.plugin.model.IAssociationEnd;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IGeneralization;
import com.vp.plugin.model.IMultiplicity;
import com.vp.plugin.model.IReference;
import com.vp.plugin.model.IRelationshipEnd;
import com.vp.plugin.model.ISimpleRelationship;

public class ClassWrapper extends ModelElementWrapper {

	public static final String POWERTYPE_STR = "powertype";
	
	private int order = 0;
	
	ClassWrapper(IClass source){ super(source); }
	
	@Override
	public IClass getSourceEntity(){
		return (IClass) super.getSourceEntity();
	}
	
	public int getOrder(){
		return order;
	}
	
	public void setOrder(int order){
		this.order = order;
	}
	
	/*
	 * This method must be called for all ClassWrappers before the validation starts.
	 * 
	 * If there is a cycle of instantiation relations, the order will
	 * be set as ZERO.
	 */
	public void loadOrder() {
		final List<String> visitedIds = new ArrayList<String>();
		ClassWrapper tmp = this;
		while(tmp != null){
			if(visitedIds.contains(tmp.getId())){
				setOrder(1);
				return ;
			} else {
				visitedIds.add(tmp.getId());
			}
			tmp = tmp.getBaseType();
		}
		setOrder(visitedIds.size()+1);
	}

	public String getName(){
		return getSourceEntity().getName();
	}
	
	public String[] getStereotypeList(){
		return getSourceEntity().toStereotypeArray();
	}
	
	/*
	 * This method is null-safe, at most returns a String[] of length 0.
	 */
	public String[] getIncomingAssociationsId(){
		final List<String> inAssociationsIds = new ArrayList<String>();
		for (IRelationshipEnd toEnd : getSourceEntity().toToRelationshipEndArray()) {
			inAssociationsIds.add(toEnd.getEndRelationship().getId());
		}
		return inAssociationsIds.toArray(new String[0]);
	}

	/*
	 * This method is null-safe, at most returns a AssociationWrapper[] of length 0.
	 */
	public AssociationWrapper[] getIncomingAssociations() {
		final List<AssociationWrapper> inAssociations = new ArrayList<AssociationWrapper>();
		for (String associationId : getIncomingAssociationsId()) {
			final AssociationWrapper tmp = 
					(AssociationWrapper) ModelManager.getModelElementWrapper(associationId);
			if (tmp != null) {
				inAssociations.add(tmp);
			}
		}
		return inAssociations.toArray(new AssociationWrapper[0]);
	}

	/*
	 * This method is null-safe, at most returns a String[] of length 0.
	 */
	public String[] getOutcommingAssociationsId(){
		final List<String> ret = new ArrayList<String>();
		for (IRelationshipEnd fromEnd : getSourceEntity().toFromRelationshipEndArray()) {
			ret.add(fromEnd.getEndRelationship().getId());
		}
		return ret.toArray(new String[0]);
	}

	/*
	 * This method is null-safe, at most returns a AssociationWrapper[] of length 0.
	 */
	public AssociationWrapper[] getOutgoingAssociations() {
		final List<AssociationWrapper> outgoingRelations = new ArrayList<AssociationWrapper>();
		for (String associationId : getOutcommingAssociationsId()) {
			AssociationWrapper tmp = (AssociationWrapper) ModelManager.getModelElementWrapper(associationId);
			if (tmp != null) {
				outgoingRelations.add(tmp);
			}
		}
		return outgoingRelations.toArray(new AssociationWrapper[0]);
	}

	/*
	 * Returns null if there is no base type.
	 * If there are multiples base types, returns the first it finds.
	 */
	public ClassWrapper getBaseType() {
		for (AssociationWrapper association : getIncomingAssociations()) {
			if(association.isInstantiation()){
				return (ClassWrapper) ModelManager.getModelElementWrapper(association.getSourceElementId());
			}
		}
		return null;
	}

	/*
	 * Returns null if there is no such instantiation relation.
	 */
	public AssociationWrapper getInstantionTo(ClassWrapper powerType) {
		for (AssociationWrapper association : getOutgoingAssociations()) {
			if(
				association.isInstantiation() && 
				association.getTargetElementId() == powerType.getId()
			) {
				return association;
			}
		}
		return null;
	}
	
	public Set<ClassWrapper> getSuperTypes(){
		Set<ClassWrapper> superTypes = new LinkedHashSet<ClassWrapper>();
		if(getSourceEntity().toReferenceArray()==null){ return superTypes; }
		
		for (IReference reference : getSourceEntity().toReferenceArray()) {
			final ModelElementWrapper ele = ModelManager.getModelElementWrapper(reference.getId());
			if(ele instanceof GeneralizationWrapper){
				superTypes.add(((GeneralizationWrapper) ele).getSuperType());
			}
		};
		return superTypes;
	}
	
	
	/**
	 * Returns all super types of a class.
	 * 
	 * @param classHierarchy - an empty set
	 * @return the classHierarchy set with all super types added to it
	 * @author Claudenir Fonseca
	 */
	public Set<ClassWrapper> getClassHierarchy(Set<ClassWrapper> classHierarchy){
		for (ClassWrapper superType : getSuperTypes()) {
			if(!classHierarchy.contains(superType)){
				classHierarchy.add(superType);
				classHierarchy.addAll(superType.getSuperTypes());
			}
		}
		return classHierarchy;
	}

	public boolean isPowertype(){
		for (String stereotype : getStereotypeList()) {
			if (POWERTYPE_STR.equals(stereotype)) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * This method could use getInstantiationTo(powerType) method.
	 */
	public boolean isBaseTypeof(String characterizerTypeId) {
		for (AssociationWrapper association : getOutgoingAssociations()) {
			if(association.isInstantiation() && association.getTargetElementId()==characterizerTypeId){
				return true;
			}
		}
		return false;
	}

	@Override
	public String report(){
		return "CLASS, NAME="+getName()
				+", ID="+getId()
				+", N_STR="+getStereotypeList().length;
	}
	
	@Override
	public void validate() {
		checkPowerType();
		checkUniqueinstantiationRelation();
		checkSameOrderSpecialization();
		checkPowerTypeSpecialization();
	}

	private void checkSameOrderSpecialization() {
		for (ISimpleRelationship relationship : getSourceEntity().toToRelationshipArray()) {
			if(relationship instanceof IGeneralization){
				final ClassWrapper superType = (ClassWrapper) ModelManager.getModelElementWrapper(relationship.getFrom().getId());
				if(getOrder() != superType.getOrder()){
					LogUtilitary.validationLog(
							"ERROR: '"+getName()+"' (order="+getOrder()
							+") cannot specialize an entity of a different order, '"
							+superType.getName()+"' (order="+superType.getOrder()+").");
				}
			}
		}
	}

	private void checkUniqueinstantiationRelation() {
		int count = 0;
		for (AssociationWrapper association : getIncomingAssociations()) {
			if(association.isInstantiation()){
				count++;
			}
		}
		if(count>1){
			LogUtilitary.validationLog("ERROR: '"+getName()+"' is target of "+count
				+" instantiation relations. The maximum is one. This error may lead to wrong order assignment.");
		}
	}

	private void checkPowerType() {
		if(isPowertype()){
			int n_instatiations = 0;
			for (AssociationWrapper association : getIncomingAssociations()) {
				if(association.isInstantiation()){
					n_instatiations++;
				}
			}
			if (n_instatiations == 0) {
				LogUtilitary.validationLog(
					"ERROR: the power type '"+getName()+"' must be target of at least one instantiation relation."
					);
			} else if (n_instatiations > 1) {
				LogUtilitary.validationLog(
					"ERROR: the power type '"+getName()+"' must be target of at most one instantiation relation."
					);
			}
		}
	}
	
	private void checkPowerTypeSpecialization(){
		ClassWrapper baseType = getBaseType();
		if(baseType==null){ return ; }
		
		Set<ClassWrapper> baseTypeHierarchy = baseType.getClassHierarchy(new LinkedHashSet<ClassWrapper>());
		for (ClassWrapper superType : getClassHierarchy(new LinkedHashSet<ClassWrapper>())) {
			if(superType.isPowertype()
					&& superType.getBaseType()!=null
					&& !baseTypeHierarchy.contains(superType.getBaseType())
			){
				LogUtilitary.validationLog(
						"ERROR: '"+baseType.getName()+"' must speciliaze '"+superType.getBaseType().getName()+
						"' (powertype hierarchy of '"+getName()+"').");
			}
		}
	}
	
}
