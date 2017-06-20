/**
 * 
 * Class Notes Section
 *
 */
package com.cynigasm.projects.minecraft.utility;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

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
	
    public static String format(String message){
    	return ChatColor.translateAlternateColorCodes('&', message);
    }
    
    public static String[] format(String[] message) {
    	for (int i = 0; i < message.length; i++) {
    		if (message[i] != null) {
			    message[i] = format(message[i]);
		    }
	    }
	    return message;
    }
    
    public static void sendFormatted(CommandSender recipient, String message) {
    	recipient.sendMessage(format(message));
    }
	
	public static void sendFormatted(CommandSender recipient, String[] message) {
		recipient.sendMessage(format(message));
	}
}