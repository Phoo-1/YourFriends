package main.java.net.bigbadcraft.yourfriends;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class YourFriendsCommand implements CommandExecutor{
	
	private List<String> one_args;
	
	private YourFriends plugin;
	private FriendManager friend_manager;
	public YourFriendsCommand(YourFriends plugin){
		this.plugin = plugin;
		friend_manager = plugin.friend_manager;
		
		one_args = new ArrayList<String>();
		one_args.add("invite");
		one_args.add("delete");
		one_args.add("accept");
		one_args.add("reject");
		one_args.add("nudge");
	}
	
	public boolean onCommand(CommandSender sender, Command cmdObj, String label, String[] strings){
		if (!(sender instanceof Player)){
			// Not a player
			return true;
		}
		Player player = (Player) sender;
		if (cmdObj.getName().equalsIgnoreCase("yourfriends")){
			if (!player.hasPermission("yourfriends.use")){
				friend_manager.noPermission(player);
				return true;
			}
			if (strings.length == 0){
				player.sendMessage(friend_manager.getHelpMenu());
			}
			else if (strings.length == 1){
				if (strings[0].equalsIgnoreCase("list")){
					if (friend_manager.hasFriends(player)){
						friend_manager.showFriends(player);
					} else {
						friend_manager.makeMessage(player, "現在フレンドはいません");
					}
					return true;
				}
				else if (strings[0].equalsIgnoreCase("requests")){
					if (friend_manager.hasFriendRequests(player)){
						friend_manager.showFriendRequests(player);
					} else {
						friend_manager.makeMessage(player, "フレンド申請はありません");
					}
				}
				if (one_args.contains(strings[0].toLowerCase())) {
					friend_manager.promptSyntax(player, "/friends " + strings[0] + (strings[0].equals("nudge") ? " <friend>" : " <player>")); 
				}
			}
			else if (strings.length == 2){
				if (strings[0].equalsIgnoreCase("invite")){
					Player target = Bukkit.getPlayer(strings[1]);
					if (target != null){
						if (!player.getName().equals(target.getName())){
							if (plugin.friends_conf.getStringList(player.getName()).size() >= plugin.friend_limit){
								friend_manager.makeMessage(player, "You can only have " + plugin.friend_limit + " friends."); 
								return true;
							}
							if (plugin.friends_conf.getStringList(player.getName()).contains(target.getName())){
								friend_manager.makeMessage(player, "既に " + target.getName() + " はフレンドです"); 
								return true;
							}
							if (!friend_manager.hasTheirRequest(target, player.getName())){
								friend_manager.sendRequest(player, target);
								friend_manager.notificationPing(target);
								friend_manager.makeMessage(target, player.getName() + " からフレンド申請が届きました");
								friend_manager.makeMessage(player, target.getName() + " へフレンド申請を送信しました");
							} else {
								friend_manager.makeMessage(player, "既に " + target.getName() + " へフレンド申請済みです"); 
							}
						} else {
							friend_manager.makeMessage(player, "自分へ申請することは出来ません");
						}
					} else {
						friend_manager.makeMessage(player, strings[1] + "はオフラインです");
					}
				}
				else if (strings[0].equalsIgnoreCase("delete")){
					String target = strings[1];
					if (friend_manager.hasFriends(player)){
						if (friend_manager.areFriends(player, target)){
							friend_manager.deleteFriend(player, target);
							friend_manager.makeMessage(player, target + " をフレンドから削除しました"); 
						} else {
							friend_manager.makeMessage(player, target + " はフレンドから削除されました");
						}
					} else {
						friend_manager.makeMessage(player, "削除できるフレンドがいません");
					}
				}
				else if (strings[0].equalsIgnoreCase("accept")){
					String target = strings[1];
					if (friend_manager.hasFriendRequests(player)){
						if (friend_manager.isValidRequest(player, target)){
							friend_manager.acceptRequest(player, target);
							friend_manager.makeMessage(player, target + "のフレンド申請を承認しました");
						} else {
							friend_manager.makeMessage(player, target + " はフレンド申請を送信していません");
						}
					} else {
						friend_manager.makeMessage(player, "承認できるフレンド申請はありません");
					}
				}
				else if (strings[0].equalsIgnoreCase("reject")){
					String target = strings[1];
					if (friend_manager.hasFriendRequests(player)){
						if (friend_manager.isValidRequest(player, target)){
							friend_manager.rejectRequest(player, target);
							friend_manager.makeMessage(player, target + "のフレンド申請を拒否しま");
						} else {
							friend_manager.makeMessage(player, target + " はフレンド申請を送信していません");
						}
					} else {
						friend_manager.makeMessage(player, "拒否できるフレンド申請はありません");
					}
				}
				else if (strings[0].equalsIgnoreCase("nudge")){
					Player target = Bukkit.getPlayer(strings[1]);
					if (target != null){
						if (friend_manager.hasFriends(player)){
							if (friend_manager.areFriends(player, target.getName())){
								friend_manager.notificationPing(target);
								friend_manager.makeMessage(target, player.getName() + " has nudged you.");
								friend_manager.makeMessage(player, "Successfully nudged " + target.getName());
							} else {
								friend_manager.makeMessage(player, "You cannot nudge " + target.getName() + ".");
							}
						} else {
							friend_manager.makeMessage(player, "You currently have no friends to nudge.");
						}
					} else {
						friend_manager.makeMessage(player, "Cannot nudge offline user " + strings[1] + "."); 
					}
				}
			}
		}
		return true;
	}

}
