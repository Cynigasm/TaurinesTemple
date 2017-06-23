package com.cynigasm.projects.minecraft.schematics;

import com.cynigasm.projects.minecraft.Project;
import com.cynigasm.projects.minecraft.utility.MessageUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SchematicCommands implements CommandExecutor {
	public SchematicCommands() {
		Project.getPlugin().getCommand("schematics").setExecutor(this);
		schematics = new HashMap<>();
	}
	
	Map<String, Schematic> schematics;
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			if (sender.isOp()) {
				Player player = (Player)sender;
				if (args.length > 0) {
					switch (args[0]) {
						case "save":
						case "copy":
							return commandSave(player, args);
						case "load":
						case "paste":
							return commandLoad(player, args);
						default:
							return false;
					}
				} else {
					return false;
				}
			} else {
				MessageUtils.sendFormatted(sender, "&cYou don't have permission to use this command!");
				return true;
			}
		} else {
			MessageUtils.sendFormatted(sender, "&cOnly players can use this command!");
			return true;
		}
	}
	
	
	
	
	private boolean commandSave(Player player, String[] args) {
		if (args.length == 8) {
			for (int i = 2; i < 8; i++) {
				if (!args[i].matches("-?[1-9][0-9]*") && !args[i].equals("0")) {
					MessageUtils.sendFormatted(player, "&cInvalid coordinate: " + args[i]);
					return true;
				}
			}
			schematics.put(args[1], new Schematic(new Location(player.getWorld(), Integer.valueOf(args[2]), Integer.valueOf(args[3]), Integer.valueOf(args[4])),
			                                      new Location(player.getWorld(), Integer.valueOf(args[5]), Integer.valueOf(args[6]), Integer.valueOf(args[7])),
			                                      player.getLocation()));
			MessageUtils.sendFormatted(player, "&eThe schematic has been saved.");
			return true;
		} else {
			return false;
		}
	}
	
	private boolean commandLoad(Player player, String[] args) {
		if (args.length == 2) {
			Schematic schematic = schematics.get(args[1]);
			if (schematic != null) {
				schematic.paste(player.getLocation());
				MessageUtils.sendFormatted(player, "&eThe schematic has been loaded.");
			} else {
				MessageUtils.sendFormatted(player, "&cNo schematic exists with that name!");
			}
			return true;
		} else {
			return false;
		}
	}
}