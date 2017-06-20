package com.cynigasm.projects.minecraft.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class MenuInventoryHolder implements InventoryHolder {
	public abstract void onClick(Player player, int slot, boolean menuWasClicked, InventoryClickEvent event);
	public abstract void onDrag(Player player, InventoryDragEvent event);
	
	@Override
	public Inventory getInventory() {
		return null;
	}
}