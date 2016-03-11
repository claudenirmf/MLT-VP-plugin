package br.ufes.inf.nemo.mltplugin

import br.ufes.inf.nemo.mltplugin.metamodel.MetamodelFactory
import com.vp.plugin.ApplicationManager
import com.vp.plugin.model.IClass

class ModelManager {
	
	def static void populateModel(){
		val modelElementsList = ApplicationManager.instance.projectManager.project.allLevelModelElementIterator
		modelElementsList.filter[ it instanceof IClass ].forEach[
			val classElement = MetamodelFactory.eINSTANCE.createClassElement
			classElement.sourceElementFromVP = it
			classElement.name = (it as IClass).name
			classElement.stereotypes.addAll((it as IClass).stereotypeIterator.toList)
			
			LogUtilitary.log('''
			Class «classElement.name» was cretead. Order=«classElement.order» NStereotypes=«classElement.stereotypes.size»
			''')
		]
	}
	
}