package main.java.net.bigbadcraft.yourfriends.listeners;

import main.java.net.bigbadcraft.yourfriends.YourFriends;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ShowFriendsJoinListener implements Listener{
	
	private YourFriends plugin;
	public ShowFriendsJoinListener(YourFriends plugin){
		this.plugin = plugin;
	}
	
	@EventHandler(priority=EventPriority.HIGH,ignoreCancelled=true)
	public void displayFriends(final PlayerJoinEvent e){
		if (plugin.show_friends == false) return;
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new BukkitRunnable(){
			public void run(){
				plugin.friend_manager.showFriends(e.getPlayer());
			}
		}, 20 * 3);
	}
}
