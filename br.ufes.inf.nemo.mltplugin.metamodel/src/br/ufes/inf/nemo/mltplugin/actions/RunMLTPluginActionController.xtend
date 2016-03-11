package br.ufes.inf.nemo.mltplugin.actions

import br.ufes.inf.nemo.mltplugin.ModelManager
import com.vp.plugin.action.VPAction
import com.vp.plugin.action.VPActionController

class RunMLTPluginActionController implements VPActionController {
	
	override performAction(VPAction action) {
		ModelManager.populateModel
	}
	
	override update(VPAction action) {
	}
	
}