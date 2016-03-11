package br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities;

import com.vp.plugin.model.IClass;

import br.ufes.inf.nemo.vp_ontouml_plugin.ufo_a.CollectiveClass;
import br.ufes.inf.nemo.vp_ontouml_plugin.ufo_a.UltimateSortalClass;

public class CollectiveKind extends OntoUMLClassEntity implements
		CollectiveClass, UltimateSortalClass {

	public CollectiveKind(IClass modelEntity) {
		super(modelEntity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isExtensinable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setIsExtensinable(boolean value) {
		// TODO Auto-generated method stub
		
	}

}
