package com.cynigasm.projects.minecraft.empire;

import com.cynigasm.projects.minecraft.Project;
import com.cynigasm.projects.minecraft.utility.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EmpireCommands implements CommandExecutor {
	public EmpireCommands() {
		Project.getPlugin().getCommand("clan").setExecutor(this);
	}
	
	//
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch (command.getName()) {
			case "clan":
				return commandClan(sender, args);
			default:
				return false;
		}
	}
	
	
	
	private boolean commandClan(CommandSender sender, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player player = (Player)sender;
				Empire empire = EmpireHandler.getEmpire(player.getUniqueId());
				if (empire != null) {
					new EmpireMenu(player, empire, 1);
				} else {
					MessageUtils.sendFormatted(sender, "&cYou aren't part of a clan!");
					return false;
				}
			} else {
				MessageUtils.sendFormatted(sender, "&cOnly players can use this command!");
			}
			return true;
		} else {
			switch (args[0].toLowerCase()) {
				case "create":
					return commandCreate(sender, args);
				default:
					return false;
			}
		}
	}
	
	
	
	private boolean commandCreate(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			if (args.length == 2) {
				Player player = (Player)sender;
				Empire empire = EmpireHandler.getEmpire(player.getUniqueId());
				if (empire == null) {
					empire = EmpireHandler.getEmpire(args[1]);
					if (empire == null) {
						EmpireHandler.addEmpire(args[1], player.getUniqueId());
						MessageUtils.sendFormatted(sender, "&eYou have successfully created a clan named: &7" + args[1]);
					} else {
						MessageUtils.sendFormatted(sender, "&cA clan already exists with that name!");
					}
				} else {
					MessageUtils.sendFormatted(sender, "&cYou are already a member of a clan!");
				}
			} else {
				return false;
			}
		} else {
			MessageUtils.sendFormatted(sender, "&cOnly players can use this command!");
		}
		return true;
	}
}