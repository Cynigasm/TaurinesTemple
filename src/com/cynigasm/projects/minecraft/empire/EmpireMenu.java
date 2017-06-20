package com.cynigasm.projects.minecraft.empire;

import com.cynigasm.projects.minecraft.menus.MenuInventoryHolder;
import com.cynigasm.projects.minecraft.utility.ItemBuilder;
import com.cynigasm.projects.minecraft.utility.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class EmpireMenu extends MenuInventoryHolder {
	public EmpireMenu(Player player, Empire empire) {
		this.empire = empire;
		Inventory inventory = Bukkit.createInventory(this, 27, MessageUtils.format("&lCLAN MANAGEMENT"));
		
		inventory.setItem(4, new ItemBuilder(Material.BOOK_AND_QUILL)
				.setName("&lINFORMATION")
				.setLore("&eName: &7" + empire.getName(),
				         "&eLevel: &7" + empire.getLevel(),
				         "&eLeader: &7" + Bukkit.getOfflinePlayer(empire.getLeader()).getName(),
				         "&eMember count: &7" + empire.getMemberCount(),
				         "&eGold balance: &7" + empire.getGold())
				.addEnchantment(Enchantment.DURABILITY, 1)
				.addItemFlags(ItemFlag.HIDE_ENCHANTS)
				.getItem());
		
		inventory.setItem(10, new ItemBuilder(Material.GOLD_BLOCK).setName("&lGOLD STORAGE").getItem());
		inventory.setItem(16, new ItemBuilder(Material.SKULL_ITEM).setDurability(3).setName("&lMEMBERS").getItem());
		//TODO disband -> delete empire file as well, delete from other empires' allies, enemies
		
		player.openInventory(inventory);
	}
	
	private final Empire empire;
	
	
	
	@Override
	public void onClick(Player player, int slot, boolean menuWasClicked, InventoryClickEvent event) {
		event.setCancelled(true);
		if (menuWasClicked) {
			switch (slot) {
				case 10:
					new GoldStorageMenu(player, empire);
					break;
				case 16:
					new MembersMenu(player, empire);
					break;
			}
		}
	}
	
	@Override
	public void onDrag(Player player, InventoryDragEvent event) {
		event.setCancelled(true);
	}
	
	public static ItemStack getBackItem() {
		return new ItemBuilder(Material.WOOD_DOOR).setName("&lBACK").getItem();
	}
}