/**
 * 
 * Class Notes Section
 *
 */
package com.cynigasm.projects.minecraft.utility;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
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
	
	public static void Error(String message) {
		Bukkit.getLogger().severe(MessageUtils.format(message));
	}
	
	public static void NotifyPlayer(String message, OfflinePlayer player) {
		if (player.isOnline()) {
			player.getPlayer().sendMessage(MessageUtils.format(message));
		}
	}
    
}
