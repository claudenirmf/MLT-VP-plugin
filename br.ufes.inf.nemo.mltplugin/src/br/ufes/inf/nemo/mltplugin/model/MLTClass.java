package br.ufes.inf.nemo.mltplugin.model;

import java.util.ArrayList;
import java.util.List;

import com.vp.plugin.model.IClass;

public class MLTClass extends ModelElement {

	private int order = 0;
	private String name;
	private List<String> stereotypeList = new ArrayList<String>();
	
	MLTClass(IClass source){
		super(source);
		name = source.getName();
		String[] sourceStereotypes = source.toStereotypeArray();
		for (String stereotype : sourceStereotypes) {
			stereotypeList.add(stereotype);
		}
	}
	
	public IClass getSourceElement(){
		return (IClass) getSourceElement();
	}
	
	public int getorder(){
		return order;
	}
	
	public void setOrder(int order){
		this.order = order;
	}
	
	public String getName(){
		return name;
	}
	
	public List<String> getStereotypeList(){
		return stereotypeList;
	}
	
}
