package com.cynigasm.projects.minecraft.menus;

import com.cynigasm.projects.minecraft.Project;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class MenuEventListener implements Listener {
	public MenuEventListener() {
		Project.getPlugin().getServer().getPluginManager().registerEvents(this, Project.getPlugin());
	}
	
	//
	
	
	
	@EventHandler (ignoreCancelled = true)
	private void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getHolder() instanceof MenuInventoryHolder) {
			((MenuInventoryHolder)event.getInventory().getHolder()).onClick((Player)event.getWhoClicked(), event.getSlot(), event.getInventory() == event.getClickedInventory(), event);
		}
	}
	
	@EventHandler (ignoreCancelled = true)
	private void onInventoryDrag(InventoryDragEvent event) {
		if (event.getInventory().getHolder() instanceof MenuInventoryHolder) {
			((MenuInventoryHolder)event.getInventory().getHolder()).onDrag((Player)event.getWhoClicked(), event);
		}
	}
	
	@EventHandler (ignoreCancelled = true)
	private void onInventoryClose(InventoryCloseEvent event) {
		if (event.getInventory().getHolder() instanceof MenuInventoryHolder) {
			((MenuInventoryHolder)event.getInventory().getHolder()).onClose((Player)event.getPlayer(), event);
		}
	}
}