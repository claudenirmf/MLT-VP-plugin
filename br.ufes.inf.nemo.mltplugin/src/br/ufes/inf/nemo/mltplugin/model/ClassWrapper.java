package br.ufes.inf.nemo.mltplugin.model;

import java.util.ArrayList;
import java.util.List;

import br.ufes.inf.nemo.mltplugin.LogUtilitary;

import com.vp.plugin.model.IAssociationEnd;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IGeneralization;
import com.vp.plugin.model.IMultiplicity;
import com.vp.plugin.model.IRelationshipEnd;
import com.vp.plugin.model.ISimpleRelationship;

public class ClassWrapper extends ModelElementWrapper {

	private static final String POWERTYPE_STR = "powertype";
	
	private int order = 0;
	
	ClassWrapper(IClass source){
		super(source);
	}
	
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
	
	public String getName(){
		return getSourceEntity().getName();
	}
	
	public String[] getStereotypeList(){
		return getSourceEntity().toStereotypeArray();
	}
	
	public boolean isPowertype(){
		for (String stereotype : getStereotypeList()) {
//			LogUtilitary.log("isPowertype "+getName()+" stereotype "+stereotype);
			if (POWERTYPE_STR.equals(stereotype)) {
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
	 * This method is null-safe, at most returns a String[] of length 0.
	 */
	public String[] getIncommingAssociationsId(){
		final List<String> ret = new ArrayList<String>();
		for (IRelationshipEnd toEnd : getSourceEntity().toToRelationshipEndArray()) {
//			LogUtilitary.log("getOutcommingAssociationsId");
			ret.add(toEnd.getEndRelationship().getId());
		}
		return ret.toArray(new String[0]);
	}

	public AssociationWrapper[] getOutcommingAssociations() {
		final List<AssociationWrapper> outcommingRelations = new ArrayList<AssociationWrapper>();
		for (String associationId : getOutcommingAssociationsId()) {
//			LogUtilitary.log("getOutcommingAssociations "+associationId);
			AssociationWrapper tmp = (AssociationWrapper) ModelManager.getModelElementWrapper(associationId);
			if (tmp != null) {
				outcommingRelations.add(tmp);
			}
		}
		return outcommingRelations.toArray(new AssociationWrapper[0]);
	}

	public AssociationWrapper[] getIncommingAssociations() {
		final List<AssociationWrapper> incommingRelations = new ArrayList<AssociationWrapper>();
		for (String associationId : getIncommingAssociationsId()) {
			final AssociationWrapper tmp = (AssociationWrapper) ModelManager.getModelCopy().get(associationId);
			if (tmp != null) {
				incommingRelations.add(tmp);
			}
		}
		return incommingRelations.toArray(new AssociationWrapper[0]);
	}

	public boolean isBaseTypeof(String characterizerTypeId) {
//		LogUtilitary.log("isBaseTypeof");
		for (AssociationWrapper association : getOutcommingAssociations()) {
			if(association.isInstantiation() && association.getTargetElementId()==characterizerTypeId){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void validate() {
		checkPowerType();
		checkUniqueinstantiationRelation();
		checkSameOrderSpecialization();
	}

	private void checkSameOrderSpecialization() {
		for (ISimpleRelationship relationship : getSourceEntity().toToRelationshipArray()) {
			if(relationship instanceof IGeneralization){
				final ClassWrapper superType = (ClassWrapper) ModelManager.getModelElementWrapper(relationship.getFrom().getId());
				if(getOrder() != superType.getOrder()){
					LogUtilitary.log(
							"ERROR: '"+getName()+"' (order="+getOrder()
							+") cannot specialize an entity of a different order, '"
							+superType.getName()+"' (order="+superType.getOrder()+").");
				}
			}
		}
	}

	private void checkUniqueinstantiationRelation() {
		int count = 0;
		for (AssociationWrapper association : getIncommingAssociations()) {
			if(association.isInstantiation()){
				count++;
			}
		}
		if(count>1){
			LogUtilitary.log("ERROR: '"+getName()+"' is target of "+count
				+" instantiation relations. The maximum is one. This error may lead to wrong order assignment.");
		}
	}

	private void checkPowerType() {
		if(isPowertype()){
			int n_instatiations = 0;
			for (AssociationWrapper association : getIncommingAssociations()) {
				if(association.isInstantiation()){
					n_instatiations++;
				}
			}
			if (n_instatiations == 0) {
				LogUtilitary.log(
					"ERRO: the power type '"+getName()+"' must be target of at least one instantiation relation."
					);
			} else if (n_instatiations > 1) {
				LogUtilitary.log(
					"ERRO: the power type '"+getName()+"' must be target of at most one instantiation relation."
					);
			}
		}
	}

	public void loadOrder() {
//		LogUtilitary.log("loadOrder");
		final List<String> visitedIds = new ArrayList<String>();
		ClassWrapper tmp = this;
		while(tmp != null){
//			LogUtilitary.log("loadOrder loop "+tmp.getName());
			if(visitedIds.contains(tmp.getId())){
//				LogUtilitary.log("ERRO: instantiation cycle detected in class '"+tmp.getName()+"' ID="+tmp.getId());
				setOrder(0);
				return ;
			} else {
				visitedIds.add(getId());
			}
			tmp = tmp.getBaseType();
		}
		setOrder(visitedIds.size());
	}
//		try {
//			getHouDependencies(visitedIds);
//			setOrder(visitedIds.size());
//		} catch (InstantiationCycleException e) {
//			LogUtilitary.log(
//				"ERRO: instantiation cycle detected for class '"+e.getName()+"' ID="+e.getId()
//			);
//			setOrder(0);
//		}

	public ClassWrapper getBaseType() {
//		LogUtilitary.log("getBaseType");
		for (AssociationWrapper association : getIncommingAssociations()) {
//			LogUtilitary.log("getBaseType association "+association.getName());
			if(association.isInstantiation()){
//				LogUtilitary.log("getBaseType isInstantion"+association.getName());
				return (ClassWrapper) ModelManager.getModelElementWrapper(association.getSourceElementId());
			}
		}
		return null;
	}

	private void getHouDependencies(List<String> visitedIds) throws InstantiationCycleException {
		if(visitedIds.contains(getId())){
			throw new InstantiationCycleException(this);
		}
		visitedIds.add(getId());
		for (AssociationWrapper association : getIncommingAssociations()) {
			if(association.isInstantiation()){
				association.getSourceElement().getHouDependencies(visitedIds);
				return ;
			}
		}
	}

	public String smallReportC() {
		return getSourceEntity().getName()+", ID: "+getId()+" ORDER: "+getOrder();
	}
	
}
