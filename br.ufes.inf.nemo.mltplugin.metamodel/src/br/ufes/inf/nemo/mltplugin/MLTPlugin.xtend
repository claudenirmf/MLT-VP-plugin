package br.ufes.inf.nemo.mltplugin

import com.vp.plugin.VPPlugin
import com.vp.plugin.VPPluginInfo
import com.vp.plugin.ApplicationManager
import java.util.Date

class MLTPlugin implements VPPlugin {
	
	public static val PLUGIN_ID = "br.ufes.inf.nemo.mltplugin"
	
	override loaded(VPPluginInfo pluginInfo) {
		ApplicationManager.instance.viewManager.showMessage("MLT Plugin loaded ("+new Date+")", "MLT Plugin")
	}
	
	override unloaded() {
	}
}