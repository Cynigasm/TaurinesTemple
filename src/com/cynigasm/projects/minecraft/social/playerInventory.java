/**
 * 
 * Class Notes Section
 *
 */
package com.cynigasm.projects.minecraft.social;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import com.cynigasm.projects.minecraft.oPlayer;
import com.cynigasm.projects.minecraft.utility.ItemBuilder;
import com.cynigasm.projects.minecraft.utility.MessageUtils;
import com.cynigasm.projects.minecraft.utility.randomUtils;

/**
 * @author Cynigasm 
 * @website https://trello.com/b/Yx15qVRx
 * Jun 11, 2017
 */
public class playerInventory {
	
	private oPlayer player;
	public static String inv_name = MessageUtils.format("&lPlayer Statistics");
	private Inventory inventory;
	ItemBuilder ib;
	
	public playerInventory(oPlayer player, boolean answer) {
		this.setPlayer(player);
		ib = new ItemBuilder(Material.SKULL);
		ib.setItemStack(randomUtils.Skull(player.getPlayer().getName(), 1));
		
		inventory = Bukkit.getServer().createInventory(null, 27, inv_name);
		
		if(answer == true) {
			friendsInventory();
		} else {
			strangerInventory();
		}
	}
	
	public Inventory getInventory() {
		return this.inventory;
	}
	
	public void friendsInventory() {
		List<String> ls = Arrays.asList(
				MessageUtils.format("&7Ingame Username |&e&l " + player.getPlayer().getName()),
				MessageUtils.format("&7Prefix | " + player.getPrefix()),
				MessageUtils.format("&7Clan | "),
				MessageUtils.format("&7Balance | &e&l" + player.getBalance()),
				MessageUtils.format("&7Power | &e&l" + player.getPower()),
				MessageUtils.format("&7Death's Touch | "));

		ib.setName(MessageUtils.format(player.getPlayer().getDisplayName()));
		ib.setLore(ls);
		
		this.inventory.setItem(inventory.getSize() - 23, ib.getItem());
		this.inventory.setItem(inventory.getSize() - 9, socialMenu.getBackItem());
		this.inventory.setItem(inventory.getSize() - 5, socialMenu.getDeleteItem());
	}
	
	public void strangerInventory() {
		
		List<String> ls = Arrays.asList(
				MessageUtils.format("&7Ingame Username |&e&l " + player.getPlayer().getName()),
				MessageUtils.format("&7Prefix | " + player.getPrefix()),
				MessageUtils.format("&7Clan | "),
				MessageUtils.format("&7Balance | &e&l" + player.getBalance()),
				MessageUtils.format("&7Power | &e&l" + player.getPower()),
				MessageUtils.format("&7Death's Touch | "));

		ib.setName(MessageUtils.format(player.getPlayer().getDisplayName()));
		ib.setLore(ls);
		
		this.inventory.setItem(inventory.getSize() - 23, ib.getItem());
		this.inventory.setItem(inventory.getSize() - 9, socialMenu.getBackItem());
		this.inventory.setItem(inventory.getSize() - 5, socialMenu.getAddItem());
	}

	public oPlayer getPlayer() {
		return player;
	}

	public void setPlayer(oPlayer player) {
		this.player = player;
	}
}