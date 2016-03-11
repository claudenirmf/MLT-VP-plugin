package br.ufes.inf.nemo.mltplugin.actions

import com.vp.plugin.action.VPActionController
import com.vp.plugin.action.VPAction
import com.vp.plugin.ApplicationManager
import br.ufes.inf.nemo.mltplugin.MLTPlugin

class ReloadMLTPluginActionController implements VPActionController {
	
	override performAction(VPAction action) {
		ApplicationManager.instance.viewManager.showMessage(
			"Reloading " + MLTPlugin.PLUGIN_ID + " ...",
			"MLT Plugin"
		)
		ApplicationManager.instance.reloadPluginClasses(MLTPlugin.PLUGIN_ID)
		ApplicationManager.instance.viewManager.showMessage(
			"Reloading complete.", "MLT Plugin"
		)
	}
	
	override update(VPAction action) {
	}
	
}