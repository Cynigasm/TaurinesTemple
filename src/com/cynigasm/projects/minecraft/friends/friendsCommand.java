/**
 * 
 * Class Notes Section
 *
 * Temporary filler, introducing player requests based on online oPlayer's
 *
 */
package com.cynigasm.projects.minecraft.friends;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.cynigasm.projects.minecraft.Project;
import com.cynigasm.projects.minecraft.oPlayer;

/**
 * @author Cynigasm 
 * @website https://trello.com/b/Yx15qVRx
 * Jun 5, 2017
 */
public class friendsCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(args.length == 3) {
				if(args[0].equalsIgnoreCase("friends")) {
					oPlayer oplayer = Project.playerhandler.getPlayer(player.getUniqueId());
					if(args[1].equalsIgnoreCase("add")) {
						if(player.hasPermission("friends.add")) {
							if(args[2] != null && oplayer != null) {
								UUID id = UUID.fromString(args[2]);
								OfflinePlayer offlineplayer = Bukkit.getOfflinePlayer(id);
								if(offlineplayer != null) {
									if(offlineplayer.hasPlayedBefore()) {
										oplayer.addFriend(args[2].toLowerCase());
									}
								}
							}	
						}
					} else if(args[1].equalsIgnoreCase("remove")) {
						if(player.hasPermission("friends.remove")) {
							if(args[2] != null && oplayer != null) {
								if(oplayer.hasFriend(args[2].toLowerCase())) {
									oplayer.removeFriend(args[2].toLowerCase());
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
}