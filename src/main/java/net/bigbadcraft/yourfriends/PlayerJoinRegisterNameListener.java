package main.java.net.bigbadcraft.yourfriends;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinRegisterNameListener implements Listener {

	private YourFriends plugin;
	public PlayerJoinRegisterNameListener(YourFriends plugin){
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent e){
		String name = e.getPlayer().getName();
		if (!plugin.friends_conf.contains(name)){
			plugin.conf_handler.reloadFriendsConf();
			plugin.friends_conf.set(name, new ArrayList<String>());
			plugin.conf_handler.saveFriendsConf();
		}
		if (!plugin.pending_friends_conf.contains(name)){
			plugin.conf_handler.reloadPendingConf();
			plugin.pending_friends_conf.set(name, new ArrayList<String>());
			plugin.conf_handler.savePendingConf();
		}
	}
}
