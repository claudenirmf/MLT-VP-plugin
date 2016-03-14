package br.ufes.inf.nemo.mltplugin.model;

import java.util.ArrayList;
import java.util.List;

import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IRelationshipEnd;

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
	
	public int getorder(){
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
			if (stereotype == POWERTYPE_STR) {
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
	
	public String[] getOutcommingAssociationsId(){
		final List<String> ret = new ArrayList<String>();
		for (IRelationshipEnd fromEnd : getSourceEntity().toFromRelationshipEndArray()) {
			ret.add(fromEnd.getId());
		}
		return ret.toArray(new String[0]);
	}
	
	public String[] getIncommingAssociationsId(){
		final List<String> ret = new ArrayList<String>();
		for (IRelationshipEnd toEnd : getSourceEntity().toToRelationshipEndArray()) {
			ret.add(toEnd.getId());
		}
		return ret.toArray(new String[0]);
	}
	
}
