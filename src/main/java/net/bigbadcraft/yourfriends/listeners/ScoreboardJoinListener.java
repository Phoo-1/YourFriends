package main.java.net.bigbadcraft.yourfriends.listeners;

import main.java.net.bigbadcraft.yourfriends.YourFriends;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardJoinListener implements Listener{
	
	private Scoreboard board;
	private String[] values;
	
	private YourFriends plugin;
	public ScoreboardJoinListener(YourFriends plugin){
		this.plugin = plugin;
		values = this.plugin.show_scoreboard.split(":");
		if (values[1].equals("1")){
			board = Bukkit.getScoreboardManager().getNewScoreboard();
			board.registerNewObjective("friendsscoreboard", "dummy");
			Objective objective = board.getObjective("friendsscoreboard");
			objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
			objective.setDisplayName(ChatColor.valueOf(values[0].toUpperCase()) + "Friend");
		}
	}
	
	@EventHandler(priority=EventPriority.HIGH,ignoreCancelled=true)
	public void showScoreboard(PlayerJoinEvent e){
		Player player = e.getPlayer();
		if (values[1].equals("0") || (!values[1].equals("1"))) return;
		for (Player players:Bukkit.getOnlinePlayers()){
			if (plugin.friend_manager.areFriends(players, player.getName())){
				players.setScoreboard(board);
				player.setScoreboard(board);
			}
		}
	}
}
