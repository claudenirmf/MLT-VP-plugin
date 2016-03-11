package com.vp.plugin.sample.reloadclasses.actions;

import java.io.*;

import com.vp.plugin.*;
import com.vp.plugin.action.*;

public class ReloadClassesActionController implements VPActionController {

	@Override
	public void performAction(VPAction aAction) {
		
		// e.g. C:\Program Files\Eclipse\Workspace\MyProject\classes
		File lSourceClassesFolder = new File("{Source Classes Folder}");
		// e.g. C:\Program Files\Visual Paradigm for UML\plugins\com.vp.plugin.sample.reloadclasses\classes
		File lPluginClassesFolder = new File("{Plugin Directory's Classes Folder}"); 
		
		copy(lSourceClassesFolder, lPluginClassesFolder);
		
		// e.g. com.vp.plugin.sample.reloadclasses
		ApplicationManager.instance().reloadPluginClasses("{Plugin ID}");
	}

	@Override
	public void update(VPAction aAction) {
	}
	
	
	private void copy(File aFrom, File aTo) {
		if (aFrom.isDirectory()) {
			if (! aTo.exists()) {
				aTo.mkdir();
			}
			
			File[] lFiles = aFrom.listFiles();
			if (lFiles != null) {
				for (File lFile : lFiles) {
					copy(lFile, new File(aTo, lFile.getName()));
				}
			}
		}
		else {
			
			try {
				InputStream lIs = new FileInputStream(aFrom);
				try {
					OutputStream lOs = new FileOutputStream(aTo);
					try {
						byte[] lData = new byte[10240];
						int lDataLength = lIs.read(lData);
						while (lDataLength > -1) {
							lOs.write(lData, 0, lDataLength);
							
							lDataLength = lIs.read(lData);
						}
					}
					finally {
						lOs.close();
					}
				}
				finally {
					lIs.close();
				}
			}
			catch (Exception lE) {
			}
			
		}
		
	}

}
