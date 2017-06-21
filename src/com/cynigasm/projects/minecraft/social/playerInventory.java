/**
 * 
 * Class Notes Section
 *
 */
package com.cynigasm.projects.minecraft.social;

import com.cynigasm.projects.minecraft.empire.Empire;
import com.cynigasm.projects.minecraft.empire.EmpireHandler;
import com.cynigasm.projects.minecraft.oPlayer;
import com.cynigasm.projects.minecraft.utility.ItemBuilder;
import com.cynigasm.projects.minecraft.utility.MessageUtils;
import com.cynigasm.projects.minecraft.utility.randomUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * @author Cynigasm 
 * @website https://trello.com/b/Yx15qVRx
 * Jun 11, 2017
 */
public class playerInventory {
	
	public static String inv_name = MessageUtils.format("&lPlayer Statistics");
	private Inventory inventory;
	
	public playerInventory(oPlayer player, boolean friend, boolean viewerCanInviteToClan) {
		Empire empire = EmpireHandler.getEmpire(player.getId());
		
		inventory = Bukkit.getServer().createInventory(null, 27, inv_name);
		
		ItemBuilder ib = new ItemBuilder(Material.SKULL);
		ib.setItemStack(randomUtils.Skull(player.getPlayer().getName(), 1));
		ib.setName(player.getPlayer().getDisplayName());
		ib.setLore(Arrays.asList(
				"&7Ingame Username |&e&l " + player.getPlayer().getName(),
				"&7Prefix | " + player.getPrefix(),
				"&7Clan | " + (empire != null ? empire.getName() : "None"),
				"&7Balance | &e&l" + player.getBalance(),
				"&7Power | &e&l" + player.getPower(),
				"&7Death's Touch | "));
		
		this.inventory.setItem(inventory.getSize() - 23, ib.getItem());
		this.inventory.setItem(inventory.getSize() - 9, socialMenu.getBackItem());
		
		ItemStack friendItem = friend ? socialMenu.getDeleteItem() : socialMenu.getAddItem();
		if (empire == null && viewerCanInviteToClan) {
			inventory.setItem(inventory.getSize() - 6, friendItem);
			inventory.setItem(inventory.getSize() - 4, getInviteToClanItem());
		} else {
			inventory.setItem(inventory.getSize() - 5, friendItem);
		}
	}
	
	public Inventory getInventory() {
		return this.inventory;
	}
	
	public static ItemStack getInviteToClanItem() {
		return new ItemBuilder(Material.IRON_CHESTPLATE).addEnchantment(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).setName("&a&lInvite to clan").getItem();
	}
}