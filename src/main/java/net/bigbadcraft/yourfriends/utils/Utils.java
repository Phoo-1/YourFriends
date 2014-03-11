package main.java.net.bigbadcraft.yourfriends.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;

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
	
	public static void logConsole(Level level, String message){
		Bukkit.getLogger().log(level, message);
	}
}
