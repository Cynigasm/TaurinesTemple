package com.cynigasm.projects.minecraft.empire;

import com.cynigasm.projects.minecraft.Project;
import com.cynigasm.projects.minecraft.menus.MenuInventoryHolder;
import com.cynigasm.projects.minecraft.oPlayer;
import com.cynigasm.projects.minecraft.utility.ItemBuilder;
import com.cynigasm.projects.minecraft.utility.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class GoldStorageMenu extends MenuInventoryHolder {
	public GoldStorageMenu(Player player, Empire empire) {
		this.empire = empire;
		Inventory inventory = Bukkit.createInventory(this, 27, MessageUtils.format("&lGOLD STORAGE"));
		inventory.setContents(ITEMS);
		setBalanceItem(inventory);
		player.openInventory(inventory);
	}
	
	private final Empire empire;
	private final static ItemStack[] ITEMS;
	
	static {
		ITEMS = new ItemStack[27];
		ITEMS[18] = EmpireMenu.getBackItem();
		
		ITEMS[2] = getItem(Material.GOLD_BLOCK, 1, true, 10000);
		ITEMS[3] = getItem(Material.GOLD_BLOCK, 10, true, 100000);
		ITEMS[4] = getItem(Material.GOLD_BLOCK, 50, true, 500000);
		
		ITEMS[6] = getItem(Material.IRON_BLOCK, 1, false, 10000);
		ITEMS[7] = getItem(Material.IRON_BLOCK, 10, false, 100000);
		ITEMS[8] = getItem(Material.IRON_BLOCK, 50, false, 500000);
		
		ITEMS[11] = getItem(Material.GOLD_INGOT, 1, true, 100);
		ITEMS[12] = getItem(Material.GOLD_INGOT, 10, true, 1000);
		ITEMS[13] = getItem(Material.GOLD_INGOT, 50, true, 5000);
		
		ITEMS[15] = getItem(Material.IRON_INGOT, 1, false, 100);
		ITEMS[16] = getItem(Material.IRON_INGOT, 10, false, 1000);
		ITEMS[17] = getItem(Material.IRON_INGOT, 50, false, 5000);
		
		ITEMS[20] = getItem(Material.GOLD_NUGGET, 1, true, 1);
		ITEMS[21] = getItem(Material.GOLD_NUGGET, 10, true, 10);
		ITEMS[22] = getItem(Material.GOLD_NUGGET, 50, true, 50);
		
		ITEMS[24] = getItem(Material.IRON_NUGGET, 1, false, 1);
		ITEMS[25] = getItem(Material.IRON_NUGGET, 10, false, 10);
		ITEMS[26] = getItem(Material.IRON_NUGGET, 50, false, 50);
	}
	
	
	
	@Override
	public void onClick(Player player, int slot, boolean menuWasClicked, InventoryClickEvent event) {
		event.setCancelled(true);
		if (menuWasClicked) {
			if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
				if (event.getCurrentItem().getType() == Material.WOOD_DOOR) {
					new EmpireMenu(player, empire);
				} else if (event.getCurrentItem().getType() != Material.CHEST) {
					int value = getValue(event.getCurrentItem());
					oPlayer oPlayer = Project.playerhandler.getPlayer(player.getUniqueId());
					if (value < 0) {
						if (empire.withdrawGold(oPlayer, value * -1)) {
							player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
							setBalanceItem(event.getInventory());
						} else {
							player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
						}
					} else {
						if (empire.depositGold(oPlayer, value)) {
							player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
							setBalanceItem(event.getInventory());
						} else {
							player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
						}
					}
				}
			}
		}
	}
	
	@Override
	public void onDrag(Player player, InventoryDragEvent event) {
		event.setCancelled(true);
	}
	
	
	
	private void setBalanceItem(Inventory inventory) {
		inventory.setItem(0, new ItemBuilder(Material.CHEST)
				.setName("&lBALANCE").setLore("&eBalance: &7" + empire.getGold())
				.addEnchantment(Enchantment.DURABILITY, 1)
				.addItemFlags(ItemFlag.HIDE_ENCHANTS)
				.getItem());
	}
	
	private static ItemStack getItem(Material material, int amount, boolean withdraw, int value) {
		return new ItemBuilder(material).setAmount(amount).setName(withdraw ? "&lWITHDRAW" : "&lDEPOSIT").setLore("&eAmount: &7" + value).getItem();
	}
	
	private static int getValue(ItemStack item) {
		int value = item.getAmount();
		switch (item.getType()) {
			case GOLD_BLOCK:
				value *= -1;
			case IRON_BLOCK:
				value *= 10000;
				break;
			case GOLD_INGOT:
				value *= -1;
			case IRON_INGOT:
				value *= 100;
				break;
			case GOLD_NUGGET:
				value *= -1;
		}
		return value;
	}
}