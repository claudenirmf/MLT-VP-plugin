package br.ufes.inf.nemo.mltplugin.actions;

import br.ufes.inf.nemo.mltplugin.LogUtilitary;
import br.ufes.inf.nemo.mltplugin.MLTPlugin;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;

public class ReloadMLTPluginActionController implements VPActionController {

	public void performAction(VPAction arg0) {
		LogUtilitary.log("Reloading "+MLTPlugin.PLUG_ID+"...");
		ApplicationManager.instance().reloadPluginClasses(MLTPlugin.PLUG_ID);
		LogUtilitary.log("Reloading complete!");
	}

	public void update(VPAction arg0) {}

}
