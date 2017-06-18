/**
 * 
 * Class Notes Section
 *
 */
package com.cynigasm.projects.minecraft.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * @author Cynigasm 
 * @website https://trello.com/b/Yx15qVRx
 * Jun 11, 2017
 */
public class onDropEvent implements Listener {
	
	public static boolean disable;
	
	@EventHandler
	public void onDropItemEvent(PlayerDropItemEvent event) {
		if(disable == true) {
			event.getItemDrop().setItemStack(null);
			onDropEvent.disable = false;
		}
	}
}