package br.ufes.inf.nemo.mltplugin;

import java.util.Date;

import com.vp.plugin.ApplicationManager;

public class LogUtilitary {

	public static final String VALIDATION_TAB = "MLT Validation";
	
	public static void log(String msg) {
		ApplicationManager
			.instance()
			.getViewManager()
			.showMessage("["+new Date()+"] "+msg);
	}
	
	public static void validationLog(String msg){
		ApplicationManager
			.instance()
			.getViewManager()
			.showMessage(msg, VALIDATION_TAB);
	}

	public static void clearValidationLog() {
		ApplicationManager
			.instance()
			.getViewManager()
			.clearMessages(VALIDATION_TAB);
	}
	
}
