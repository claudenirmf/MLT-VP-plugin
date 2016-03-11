package br.ufes.inf.nemo.mltplugin;

import com.vp.plugin.ApplicationManager;

public class LogUtilitary {

	public static final String LOG_ID = "MLT Plugin";
	
	public static void log(String msg){
		ApplicationManager
			.instance()
			.getViewManager()
			.showMessage(msg, LOG_ID);
	}
}
