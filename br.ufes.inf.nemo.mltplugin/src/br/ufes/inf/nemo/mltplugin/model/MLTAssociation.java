package br.ufes.inf.nemo.mltplugin.model;

import com.vp.plugin.model.IAssociation;
import com.vp.plugin.model.IAssociationEnd;

public class MLTAssociation extends ModelElement {
	
	public static final String INSTANTIATION_STR = "instantiation";
	
	public MLTAssociation(IAssociation source) {
		super(source);
	}
	
	@Override
	public IAssociation getSourceEntity(){
		return (IAssociation) super.getSourceEntity();
	}

	public String getName() {
		return getSourceEntity().getName();
	}

	public String getTargetElementId() {
		return getSourceEntity().getTo().getId();
	}

	public String getTargetEndCardinality() {
		return ((IAssociationEnd) getSourceEntity().getToEnd()).getMultiplicity();
	}

	public String getSourceElementId() {
		return getSourceEntity().getFrom().getId();
	}

	public String getSourceEndCardinality() {
		return ((IAssociationEnd) getSourceEntity().getFromEnd()).getMultiplicity();
	}

	public String[] getStereotypeList() {
		return getSourceEntity().toStereotypeArray();
	}
	
	public boolean isInstantion(){
		for (String stereotype : getStereotypeList()) {
			if (stereotype == INSTANTIATION_STR) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String report(){
		return "ASSOCIATION, NAME="+getName()
				+", ID="+getId()
				+", N_STR="+getStereotypeList().length
				+", TARGET_ID="+getTargetElementId()
				+", SOURCE_ID="+getSourceElementId();
	}
	
}
