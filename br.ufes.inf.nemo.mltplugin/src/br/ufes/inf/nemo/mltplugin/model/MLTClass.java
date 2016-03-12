package br.ufes.inf.nemo.mltplugin.model;

import com.vp.plugin.model.IClass;

public class MLTClass extends ModelElement {

	private static final String POWERTYPE_STR = "powertype";
	
	private int order = 0;
	
	MLTClass(IClass source){
		super(source);
	}
	
	@Override
	public IClass getSourceEntity(){
		return (IClass) getSourceEntity();
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
}
