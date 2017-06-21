package com.cynigasm.projects.minecraft.empire;

import com.cynigasm.projects.minecraft.menus.MenuInventoryHolder;
import com.cynigasm.projects.minecraft.utility.MessageUtils;
import com.cynigasm.projects.minecraft.utility.randomUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

public class MemberMenu extends MenuInventoryHolder {
	MemberMenu(Player player, OfflinePlayer target, Empire empire, int page) {
		this.target = target;
		this.empire = empire;
		this.page = page;
		
		Inventory inventory = Bukkit.createInventory(this, 27, MessageUtils.format("&lMember: &7&l" + randomUtils.getPlayerName(target, "")));
		
		inventory.setItem(18, EmpireMenu.getBackItem());
		
		player.openInventory(inventory);
	}
	
	private final OfflinePlayer target;
	private final Empire empire;
	private final int page;
	
	
	
	@Override
	public void onClick(Player player, int slot, boolean menuWasClicked, InventoryClickEvent event) {
		event.setCancelled(true);
		if (menuWasClicked) {
			switch (slot) {
				case 18:
					new EmpireMenu(player, empire, page);
					break;
			}
		}
	}
	
	@Override
	public void onDrag(Player player, InventoryDragEvent event) {
		event.setCancelled(true);
	}
}