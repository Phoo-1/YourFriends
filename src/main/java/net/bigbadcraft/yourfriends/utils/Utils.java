package main.java.net.bigbadcraft.yourfriends.utils;

import java.io.File;
import java.io.IOException;

public class Utils{

	public static void makeFile(File file){
		if (!file.exists()){
			try{
				file.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}
