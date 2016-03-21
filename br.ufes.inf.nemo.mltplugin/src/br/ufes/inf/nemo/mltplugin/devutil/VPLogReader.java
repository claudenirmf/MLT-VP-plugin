package br.ufes.inf.nemo.mltplugin.devutil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class VPLogReader {

	public static void main(String[] args) throws IOException {
		final String logFilePath = args[0];
		final BufferedReader vpLogBuffer = 
				new BufferedReader(new FileReader(logFilePath));
		
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
