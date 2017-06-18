/**
 * 
 * Class Notes Section
 *
 */
package com.cynigasm.projects.minecraft.utility;

import org.bukkit.ChatColor;

/**
 * @author Cynigasm 
 * @website https://trello.com/b/Yx15qVRx
 * Jun 13, 2017
 */
public class MessageUtils {
	
	public static String targetIsBlocked(String name) {
		String message = "";
		if(name != null) {
			message = MessageUtils.format("&cYou must remove &f&l" + name + " &c from your blocklist before adding them!");
		} else {
			randomUtils.Error("test");
		}
		return message;
	}
	
	public static String targetHasBlocked(String name) {
		String message = "";
		if(name != null) {
			message = MessageUtils.format("&f&l" + name + " &c does not exist!");
		} else {
			randomUtils.Error("test");
		}
		return message;
	}
	
    public static String format(String format){
    	return ChatColor.translateAlternateColorCodes('&', format);
    }
}