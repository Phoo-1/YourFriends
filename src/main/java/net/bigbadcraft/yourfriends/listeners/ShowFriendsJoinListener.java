package main.java.net.bigbadcraft.yourfriends.listeners;

import main.java.net.bigbadcraft.yourfriends.YourFriends;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ShowFriendsJoinListener implements Listener{
	
	private YourFriends plugin;
	public ShowFriendsJoinListener(YourFriends plugin){
		this.plugin = plugin;
	}
	
	@EventHandler(priority=EventPriority.HIGH,ignoreCancelled=true)
	public void displayFriends(PlayerJoinEvent e){
		if (plugin.show_friends == false) return;
		plugin.friend_manager.showFriends(e.getPlayer());
	}
}
