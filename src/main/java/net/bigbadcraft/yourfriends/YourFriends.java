package main.java.net.bigbadcraft.yourfriends;

import java.io.File;

import main.java.net.bigbadcraft.yourfriends.listeners.NotifyFriendJoinListener;
import main.java.net.bigbadcraft.yourfriends.listeners.NotifyFriendLeaveListener;
import main.java.net.bigbadcraft.yourfriends.listeners.PlayerJoinRegisterNameListener;
import main.java.net.bigbadcraft.yourfriends.utils.ConfigHandler;
import main.java.net.bigbadcraft.yourfriends.utils.ConfigPath;
import main.java.net.bigbadcraft.yourfriends.utils.Utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class YourFriends extends JavaPlugin{
	
	public final ChatColor TEAL = ChatColor.DARK_AQUA;
	public final ChatColor CYAN = ChatColor.AQUA;
	
	public final String PREFIX = TEAL + "[YourFriends] ";

	/* Configurations */
	public File friends_file;
	public FileConfiguration friends_conf;
	
	public File pending_friends_file;
	public FileConfiguration pending_friends_conf;
	
	public FriendManager friend_manager;
	public ConfigHandler conf_handler;
	
	/* Configuration settings */
	public String notification_sound;
	public boolean notify_on_join;
	public boolean notify_on_leave;
	
	public void onEnable(){
		saveDefaultConfig();
		notification_sound = getConfig().getString(ConfigPath.NOTIFICATION_SOUND);
		notify_on_join = getConfig().getBoolean(ConfigPath.NOTIFY_JOIN);
		notify_on_leave = getConfig().getBoolean(ConfigPath.NOTIFY_LEAVE);
		friends_file = new File(getDataFolder(), "friends.yml");
		pending_friends_file = new File(getDataFolder(), "pendingfriends.yml");
		conf_handler = new ConfigHandler(this);
		Utils.makeFile(friends_file);
		friends_conf = YamlConfiguration.loadConfiguration(friends_file);
		conf_handler.reloadFriendsConf();
		Utils.makeFile(pending_friends_file);
		pending_friends_conf = YamlConfiguration.loadConfiguration(pending_friends_file);
		conf_handler.reloadPendingConf();
		friend_manager = new FriendManager(this);
		getServer().getPluginManager().registerEvents(new PlayerJoinRegisterNameListener(this), this);
		getServer().getPluginManager().registerEvents(new NotifyFriendJoinListener(this), this);
		getServer().getPluginManager().registerEvents(new NotifyFriendLeaveListener(this), this);
		getCommand("yourfriends").setExecutor(new YourFriendsCommand(this));
	}
}
