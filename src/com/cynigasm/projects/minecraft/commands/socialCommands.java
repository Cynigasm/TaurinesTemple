/**
 * 
 * Class Notes Section
 *
 * Temporary filler, introducing player requests based on online oPlayer's
 *
 */
package com.cynigasm.projects.minecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.cynigasm.projects.minecraft.Project;
import com.cynigasm.projects.minecraft.social.socialMenu;

/**
 * @author Cynigasm 
 * @website https://trello.com/b/Yx15qVRx
 * Jun 5, 2017
 */
public class socialCommands implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("social")) {
				player.openInventory(new socialMenu(Project.playerhandler.getPlayer(player.getUniqueId())).getInventory());
			}
		}
		return true;
	}
	
}