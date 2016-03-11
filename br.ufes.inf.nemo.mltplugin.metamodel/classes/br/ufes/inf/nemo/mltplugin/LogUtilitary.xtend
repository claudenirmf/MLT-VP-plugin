package br.ufes.inf.nemo.mltplugin

import com.vp.plugin.ApplicationManager

class LogUtilitary {
	
	public static val LOG_ID = "MLT Plugin"
	
	def static void log(String msg){
		ApplicationManager.instance.viewManager.showMessage(msg,LOG_ID)
	}
	
}