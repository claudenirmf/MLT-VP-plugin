package br.ufes.inf.nemo.mltplugin.model;

import java.util.ArrayList;
import java.util.List;

import com.vp.plugin.model.IGeneralization;
import com.vp.plugin.model.IGeneralizationSet;

public class GeneralizationSetWrapper extends ModelElementWrapper {

	GeneralizationSetWrapper(IGeneralizationSet source) {
		super(source);
	}
	
	@Override
	public IGeneralizationSet getSourceEntity(){
		return (IGeneralizationSet) super.getSourceEntity();
	}
	
	public String getPowerTypeId(){
		return getSourceEntity().getPowerType().getId();
	}
	
	public String getSuperTypeId(){
		return getSourceEntity().toGeneralizationArray()[0].getFrom().getId();
	}
	
	public String[] getSubTypesId(){
		final List<String> ret = new ArrayList<String>();
		for (IGeneralization generalization : getSourceEntity().toGeneralizationArray()) {
			ret.add(generalization.getTo().getId());
		}
		return ret.toArray(new String[0]);
	}
	
	public boolean isDisjoint(){
		return getSourceEntity().isDisjoint();
	}
	
	public boolean isCovering(){
		return getSourceEntity().isCovering();
	}
	
	@Override
	public String report() {
		return "GENERALIZATION, ID="+getId()
				+", SUPER_ID="+getSuperTypeId()
				+", N_SUBTYPES="+getSubTypesId().length
				+", POWERTTYPE="+getPowerTypeId();
	}

}
