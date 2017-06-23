/**
 * 
 * Class Notes Section
 *
 */
package com.cynigasm.projects.minecraft.utility;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * @author Cynigasm 
 * @website https://trello.com/b/Yx15qVRx
 * Jun 10, 2017
 */
public class randomUtils {
	
	public randomUtils() {
		
	}
	
	public static ItemStack Skull(String name, int amount) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, amount, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        
        Player player = Bukkit.getPlayerExact(name);
        meta.setOwner(player.getName());
        meta.setDisplayName(player.getDisplayName());
        skull.setItemMeta(meta);
       
        return skull;
	}
	
	public static ItemStack getSkull(OfflinePlayer player) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
		SkullMeta meta = (SkullMeta)item.getItemMeta();
		meta.setOwner(player.getName());
		meta.setDisplayName(getPlayerName(player, ChatColor.DARK_GRAY.toString()));
		item.setItemMeta(meta);
		return item;
	}
	
	public static void Error(String message) {
		Bukkit.getLogger().severe(MessageUtils.format(message));
	}
	
	public static void NotifyPlayer(String message, OfflinePlayer player) {
		if (player.isOnline()) {
			player.getPlayer().sendMessage(MessageUtils.format(message));
		}
	}
    
	public static String getPlayerName(OfflinePlayer player, String offlinePrefix) {
		if (player.isOnline()) {
			return player.getPlayer().getDisplayName();
		} else {
			return offlinePrefix + player.getName();
		}
	}
	
	public static OfflinePlayer getOfflinePlayer(String name) {
		if (name == null) {
			return null;
		}
		
		Player online = Bukkit.getPlayerExact(name);
		if (online != null) {
			return online;
		}
		
		for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
			if (player.getName().equalsIgnoreCase(name)) {
				return player;
			}
		}
		return null;
	}
}
