package com.cynigasm.projects.minecraft.menus;

import com.cynigasm.projects.minecraft.utility.ItemBuilder;
import com.cynigasm.projects.minecraft.utility.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.function.Consumer;

public class ConfirmationMenu extends MenuInventoryHolder {
	public ConfirmationMenu(Player player, String title, String confirmName, List<String> confirmLore, String cancelName, List<String> cancelLore, Consumer<Boolean> resultConsumer) {
		this.resultConsumer = resultConsumer;
		finished = false;
		
		Inventory inventory = Bukkit.createInventory(this, 27, MessageUtils.format(title));
		inventory.setItem(11, new ItemBuilder(Material.WOOL).setDurability(13).setName(confirmName).setLore(confirmLore).getItem());
		inventory.setItem(15, new ItemBuilder(Material.WOOL).setDurability(14).setName(cancelName).setLore(cancelLore).getItem());
		player.openInventory(inventory);
	}
	
	private final Consumer<Boolean> resultConsumer;
	private boolean finished;
	
	
	@Override
	public void onClick(Player player, int slot, boolean menuWasClicked, InventoryClickEvent event) {
		event.setCancelled(true);
		if (menuWasClicked) {
			switch (slot) {
				case 11:
					finished = true;
					resultConsumer.accept(true);
					break;
				case 15:
					resultConsumer.accept(false);
					finished = true;
					break;
			}
		}
	}
	
	@Override
	public void onDrag(Player player, InventoryDragEvent event) {
		event.setCancelled(true);
	}
	
	@Override
	public void onClose(Player player, InventoryCloseEvent event) {
		if (!finished) {
			finished = true;
			resultConsumer.accept(false);
		}
	}
}
