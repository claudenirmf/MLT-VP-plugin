package br.ufes.inf.nemo.vp_ontouml_plugin;

import com.vp.plugin.*;

public class OntoUMLPlugin implements VPPlugin {
	
	public void loaded(VPPluginInfo aArg0) {
	}
	
	public void unloaded() {
	}

	public static void print(String msg) {
		ApplicationManager.instance().getViewManager()
				.showMessage(msg, "OntoUML");
	}
}
