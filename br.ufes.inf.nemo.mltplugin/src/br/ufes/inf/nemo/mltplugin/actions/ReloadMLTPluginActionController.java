package br.ufes.inf.nemo.mltplugin.actions;

import java.util.Date;

import br.ufes.inf.nemo.mltplugin.LogUtilitary;
import br.ufes.inf.nemo.mltplugin.MLTPlugin;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;

public class ReloadMLTPluginActionController implements VPActionController {

	public void performAction(VPAction arg0) {
		LogUtilitary.log("Reloading again "+MLTPlugin.PLUGIN_ID+"...");
		ApplicationManager.instance().reloadPluginClasses(MLTPlugin.PLUGIN_ID);
		LogUtilitary.clearLog();
		LogUtilitary.log("Reloading complete! ["+new Date()+"]");
	}

	public void update(VPAction arg0) {}

}
