package br.ufes.inf.nemo.mltplugin;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.VPPlugin;
import com.vp.plugin.VPPluginInfo;

public class MLTPlugin implements VPPlugin {

	public static final String PLUG_ID = "br.ufes.inf.nemo.mltplugin";
	
	public void loaded(VPPluginInfo arg0) {
		ApplicationManager
			.instance()
			.getViewManager()
			.showMessage("MLT Plugin loaded");
	}

	public void unloaded() {}

}
