package br.ufes.inf.nemo.mltplugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class MoveFiles {

	public static void main(String[] args){
		String origin = "C:\\Users\\Claudenir\\Documents\\Workspace\\MLTPlugin\\br.ufes.inf.nemo.mltplugin\\classes";
		String destiny = "C:\\Users\\Claudenir\\Programs\\Visual Paradigm CE 12.0\\plugins\\br.ufes.inf.nemo.mltplugin\\classes";
		File org_folder = new File(origin);
		File des_folder = new File(destiny);
		if(des_folder.exists()){
			des_folder.delete();
		}
		try {
			Files.copy(org_folder.toPath(), des_folder.toPath(), StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Everything is fine");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
