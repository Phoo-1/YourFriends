package main.java.net.bigbadcraft.yourfriends.listeners;

import java.util.List;

import main.java.net.bigbadcraft.yourfriends.YourFriends;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class NotifyFriendLeaveListener implements Listener {
	
	private YourFriends plugin;
	public NotifyFriendLeaveListener(YourFriends plugin){
		this.plugin = plugin;
	}
	
	@EventHandler(priority=EventPriority.HIGH,ignoreCancelled=true)
	public void onFriendLeave(PlayerQuitEvent e){
		if (plugin.notify_on_leave == false) return;
		Player player = e.getPlayer();
		for (Player players:Bukkit.getOnlinePlayers()){
			List<String> list = plugin.friends_conf.getStringList(players.getName());
			if (list.contains(player.getName())){
				plugin.friend_manager.notificationPing(players);
				plugin.friend_manager.makeMessage(players, "friend " + player.getName() + " がログアウトしました");
			}
		}
	}
}
