package com.cynigasm.projects.minecraft.empire;

import com.cynigasm.projects.minecraft.menus.MenuInventoryHolder;
import com.cynigasm.projects.minecraft.utility.ItemBuilder;
import com.cynigasm.projects.minecraft.utility.MessageUtils;
import com.cynigasm.projects.minecraft.utility.randomUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MemberMenu extends MenuInventoryHolder {
	MemberMenu(Player player, OfflinePlayer target, Empire empire, int page) {
		this.target = target;
		this.empire = empire;
		this.page = page;
		
		Inventory inventory = Bukkit.createInventory(this, 27, MessageUtils.format("&lMember: " + randomUtils.getPlayerName(target, "")));
		inventory.setItem(18, EmpireMenu.getBackItem());
		inventory.setItem(4, new ItemBuilder(randomUtils.getSkull(target)).setLore("&7Rank: " + empire.getRank(target.getUniqueId(), true)).getItem());
		inventory.setItem(22, new ItemBuilder(Material.TNT).setName("&cKick member").getItem());
		
		player.openInventory(inventory);
	}
	
	private final OfflinePlayer target;
	private final Empire empire;
	private final int page;
	
	
	
	@Override
	public void onClick(Player player, int slot, boolean menuWasClicked, InventoryClickEvent event) {
		event.setCancelled(true);
		ItemStack item = event.getCurrentItem();
		if (menuWasClicked && item != null && item.getType() != Material.AIR) {
			switch (slot) {
				case 18:
					new EmpireMenu(player, empire, page);
					break;
				case 22:
					empire.removeMember(target.getUniqueId());
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