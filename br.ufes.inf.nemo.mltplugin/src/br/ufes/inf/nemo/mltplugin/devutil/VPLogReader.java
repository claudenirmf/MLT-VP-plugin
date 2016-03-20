package br.ufes.inf.nemo.mltplugin.devutil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class VPLogReader {
	final static String VP_LOG_FILE_NAME = 
			"C:\\Users\\Claudenir\\AppData\\Roaming\\VisualParadigm\\vp.log";

	public static void main(String[] args) throws IOException {
		final BufferedReader vpLogBuffer = 
				new BufferedReader(new FileReader(VP_LOG_FILE_NAME));
//		Runtime.getRuntime().addShutdownHook(new Thread() {
//			vpLog
//		});
		
		try{
			String line;
			while(true){
				line = vpLogBuffer.readLine();
				if(line!=null){
					System.out.println(line);
				} else {
					Thread.sleep(10*1000);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			vpLogBuffer.close();
		}
	}
	
}
