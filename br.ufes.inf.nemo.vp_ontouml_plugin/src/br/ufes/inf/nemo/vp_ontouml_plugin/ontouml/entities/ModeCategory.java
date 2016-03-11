package br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities;

import com.vp.plugin.model.IClass;

import br.ufes.inf.nemo.vp_ontouml_plugin.ufo_a.ModeClass;
import br.ufes.inf.nemo.vp_ontouml_plugin.ufo_a.RigidMixinClass;

public class ModeCategory extends OntoUMLClassEntity implements ModeClass,
		RigidMixinClass {

	public ModeCategory(IClass modelElement) {
		super(modelElement);
	}

}
