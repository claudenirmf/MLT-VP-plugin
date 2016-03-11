package br.ufes.inf.nemo.vp_ontouml_plugin.ontouml.entities;

import com.vp.plugin.model.IClass;

import br.ufes.inf.nemo.vp_ontouml_plugin.ufo_a.QualityClass;
import br.ufes.inf.nemo.vp_ontouml_plugin.ufo_a.SemiRigidMixinClass;

public class QualityMixin extends OntoUMLClassEntity implements QualityClass,
		SemiRigidMixinClass {

	public QualityMixin(IClass modelElement) {
		super(modelElement);
		// TODO Auto-generated constructor stub
	}

}
