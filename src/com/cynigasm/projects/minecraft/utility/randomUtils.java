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
	
	public static ItemStack Skull(String name, int i) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, i, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        
        Player player = Bukkit.getPlayerExact(name);
        meta.setOwner(player.getName());
        meta.setDisplayName(player.getDisplayName());
        skull.setItemMeta(meta);
       
        return skull;
	}
	
	public static void Error(String Message) {
		Bukkit.getLogger().info(MessageUtils.format(Message));
	}
	
	public static void NotifyPlayer(String message, Player player) {
		if(Bukkit.getOnlinePlayers().contains(player)) {
			player.sendMessage(MessageUtils.format(message));
		}
	}
    
}
