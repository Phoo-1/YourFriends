package main.java.net.bigbadcraft.yourfriends;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.google.common.base.Joiner;

public class FriendManager{
	
	private YourFriends plugin;
	public FriendManager(YourFriends plugin){
		this.plugin = plugin;
	}
	
	public void showFriendRequests(Player player){
		String requests = Joiner.on(", ").join(plugin.pending_friends_conf.getStringList(player.getName())) + ".";
		makeMessage(player, "Friend Requests: " + requests); 
	}
	
	public void showFriends(Player player){
		StringBuilder sb = new StringBuilder();
		List<String> list = plugin.friends_conf.getStringList(player.getName());
		for (String players:list){
			ChatColor color = Bukkit.getPlayer(players) != null ? ChatColor.GREEN : ChatColor.RED;
			sb.append(list.indexOf(players) == list.size()-1 ? color + players +ChatColor.WHITE+ "." : color + players +ChatColor.WHITE+ ", ");
		}
		makeMessage(player, "Friends (" + list.size() + "): " + sb.toString());
	}
	
	public void deleteFriend(Player player, String to_delete){
		plugin.conf_handler.reloadFriendsConf();
		List<String> list = plugin.friends_conf.getStringList(player.getName());
		list.remove(to_delete);
		plugin.friends_conf.set(player.getName(), list);
		List<String> list_two = plugin.friends_conf.getStringList(to_delete);
		list_two.remove(player.getName());
		plugin.friends_conf.set(to_delete, list_two);
		plugin.conf_handler.saveFriendsConf();
	}
	
	public boolean hasFriends(Player player){
		return plugin.friends_conf.getStringList(player.getName()).size() > 0;
	}
	
	public boolean areFriends(Player friend_one, String friend_two){
		return plugin.friends_conf.getStringList(friend_one.getName()).contains(friend_two)
				&& plugin.friends_conf.getStringList(friend_two).contains(friend_one.getName());
	}
	
	public void acceptRequest(Player player, String to_accept){
		plugin.conf_handler.reloadPendingConf();
		List<String> list = plugin.pending_friends_conf.getStringList(player.getName());
		list.remove(to_accept);
		plugin.pending_friends_conf.set(player.getName(), list);
		plugin.conf_handler.savePendingConf();
		plugin.conf_handler.reloadFriendsConf();
		List<String> list_two = plugin.friends_conf.getStringList(player.getName());
		list_two.add(to_accept);
		plugin.friends_conf.set(player.getName(), list_two);
		List<String> list_three = plugin.friends_conf.getStringList(to_accept);
		list_three.add(player.getName());
		plugin.friends_conf.set(to_accept, list_three);
		plugin.conf_handler.saveFriendsConf();
	}
	
	public void rejectRequest(Player player, String to_reject){
		
	}
	
	public void sendRequest(Player player, Player target){
		plugin.conf_handler.reloadPendingConf();
		List<String> list = plugin.pending_friends_conf.getStringList(target.getName());
		list.add(player.getName());
		plugin.pending_friends_conf.set(target.getName(), list);
		plugin.conf_handler.savePendingConf();
	}
	
	public boolean hasTheirRequest(Player viewer, String requester){
		return plugin.pending_friends_conf.getStringList(viewer.getName()).contains(requester);
	}
	
	public boolean isValidRequest(Player player, String requester){
		return plugin.pending_friends_conf.getStringList(player.getName()).contains(requester);
	}
	
	public boolean hasFriendRequests(Player player){
		return plugin.pending_friends_conf.getStringList(player.getName()).size() > 0;
	}
	
	// Display online and offline status.
	
	public String getHelpMenu(){
		return plugin.TEAL + "----------(" + plugin.CYAN + "YourFriends" + plugin.TEAL + ")----------\n"
				+ plugin.CYAN + "Shortcut: " + plugin.TEAL + "/yf\n"
				+ plugin.CYAN + "-/yourfriends add <player>" + plugin.TEAL + " - Adds specified player.\n"
				+ plugin.CYAN + "-/yourfriends delete <player>" + plugin.TEAL + " - Removes player from your friends list.\n"
				+ plugin.CYAN + "-/yourfriends accept <player>" + plugin.TEAL + " - Accept player's friend request.\n"
				+ plugin.CYAN + "-/yourfriends reject <player>" + plugin.TEAL + " - Reject player's friend request.\n"
				+ plugin.CYAN + "-/yourfriends requests" + plugin.TEAL + " - List your current friend requests.\n"
				+ plugin.CYAN + "-/yourfriends list" + plugin.TEAL + " - List your current friends.\n"
				+ plugin.CYAN + "-/yourfriends nudge <player>" + plugin.TEAL + " - Nudge your friend for attention.\n"
				+ plugin.TEAL + "---------------------------------";
	}
	
	public void notificationPing(Player player){
		player.playSound(player.getLocation(), Sound.valueOf(plugin.notification_sound.toUpperCase()), 1F, 1F);
	}
	
	public void makeMessage(Player player, String error){
		player.sendMessage(plugin.PREFIX + plugin.CYAN + error);
	}
	
	public void noPermission(Player player){
		player.sendMessage(plugin.PREFIX + plugin.CYAN + "Insufficient permission.");
	}
	
	public void promptSyntax(Player player, String cmd_syntax){
		player.sendMessage(plugin.PREFIX + plugin.CYAN + "Incorrect syntax, usage: " + cmd_syntax);
	}
	
}
