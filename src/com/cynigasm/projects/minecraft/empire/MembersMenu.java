package com.cynigasm.projects.minecraft.empire;

import com.cynigasm.projects.minecraft.menus.MenuInventoryHolder;
import com.cynigasm.projects.minecraft.utility.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

public class MembersMenu extends MenuInventoryHolder {
	public MembersMenu(Player player, Empire empire) {
		this.empire = empire;
		Inventory inventory = Bukkit.createInventory(this, 27, MessageUtils.format("&lMEMBERS"));
		
		inventory.setItem(18, EmpireMenu.getBackItem());
		
		player.openInventory(inventory);
	}
	
	private final Empire empire;
	
	
	
	@Override
	public void onClick(Player player, int slot, boolean menuWasClicked, InventoryClickEvent event) {
		event.setCancelled(true);
		if (menuWasClicked) {
			switch (slot) {
				case 18:
					new EmpireMenu(player, empire);
					break;
			}
		}
	}
	
	@Override
	public void onDrag(Player player, InventoryDragEvent event) {
		event.setCancelled(true);
	}
}
