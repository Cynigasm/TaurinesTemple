package com.cynigasm.projects.minecraft.menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;

import java.util.function.Consumer;

public class AnvilInputMenu extends MenuInventoryHolder { //TODO test this class
	public AnvilInputMenu(Player player, Consumer<String> resultConsumer) {
		AnvilInventory inventory = (AnvilInventory)Bukkit.createInventory(this, InventoryType.ANVIL); //TODO title?
		//TODO default text
		player.openInventory(inventory);
		this.resultConsumer = resultConsumer;
	}
	
	private final Consumer<String> resultConsumer;
	
	
	
	@Override
	public void onClose(Player player, InventoryCloseEvent event) {
		resultConsumer.accept(((AnvilInventory)event.getInventory()).getRenameText());
	}
	
	@Override
	public void onClick(Player player, int slot, boolean menuWasClicked, InventoryClickEvent event) {
		event.setCancelled(true);
	}
	
	@Override
	public void onDrag(Player player, InventoryDragEvent event) {
		event.setCancelled(true);
	}
}