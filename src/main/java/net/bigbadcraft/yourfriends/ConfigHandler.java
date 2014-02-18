package main.java.net.bigbadcraft.yourfriends;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigHandler {
	
	private YourFriends plugin;
	public ConfigHandler(YourFriends plugin){
		this.plugin = plugin;
	}

	public void reloadFriendsConf() {
		if (plugin.friends_file == null) 
			plugin.friends_file = new File(plugin.getDataFolder(), "friends.yml");
		
		plugin.friends_conf = YamlConfiguration.loadConfiguration(plugin.friends_file);
		
		InputStream stream = plugin.getResource("friends.yml");
		if (stream != null) {
			YamlConfiguration streamConf = YamlConfiguration.loadConfiguration(stream);
			plugin.friends_conf.setDefaults(streamConf);
		}
			
	}
	
	public void saveFriendsConf(){
		if (plugin.friends_conf == null || plugin.friends_file == null)
			return;
		
		try {
			plugin.friends_conf.save(plugin.friends_file);
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Could not save credits config to " + plugin.friends_file, e);
		}
	}
	
	public void reloadPendingConf(){
		if (plugin.pending_friends_file == null) 
			plugin.pending_friends_file = new File(plugin.getDataFolder(), "pendingfriends.yml");
		
		plugin.pending_friends_conf = YamlConfiguration.loadConfiguration(plugin.pending_friends_file);
		
		InputStream stream = plugin.getResource("pendingfriends.yml");
		if (stream != null) {
			YamlConfiguration streamConf = YamlConfiguration.loadConfiguration(stream);
			plugin.pending_friends_conf.setDefaults(streamConf);
		}
			
	}
	
	public void savePendingConf(){
		if (plugin.pending_friends_conf == null || plugin.pending_friends_file == null)
			return;
		
		try {
			plugin.pending_friends_conf.save(plugin.pending_friends_file);
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Could not save credits config to " + plugin.pending_friends_file, e);
		}
	}
}
