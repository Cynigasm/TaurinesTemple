/**
 * 
 * Class Notes Section
 *
 */
package com.cynigasm.projects.minecraft.social;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.cynigasm.projects.minecraft.oPlayer;
import com.cynigasm.projects.minecraft.utility.MessageUtils;
import com.cynigasm.projects.minecraft.utility.randomUtils;

/**
 * @author Cynigasm 
 * @website https://trello.com/b/Yx15qVRx
 * Jun 11, 2017
 */
public class requestsInventory {
	
	private oPlayer player;
	private ArrayList<ItemStack> offlineplayers;
	private ArrayList<ItemStack> onlineplayers;
	
	private Inventory inventory;
	public static String inv_name = MessageUtils.format("&e&lFRIEND REQUESTS");
	
	public requestsInventory(oPlayer player) {
		this.player = player;
		offlineplayers = new ArrayList<ItemStack>();
		onlineplayers = new ArrayList<ItemStack>();
		inventory = Bukkit.createInventory(null,  generateInventorySize(), inv_name);
	}
	
	public Inventory getInventory() {
		int slot_backitem = this.inventory.getSize() - 9;
		this.inventory.setItem(slot_backitem, socialMenu.getBackItem());
		this.inventory.setItem(slot_backitem + 2, socialMenu.getAddItem());
		
		for(int i = 0; i < this.inventory.getSize(); i++) {
			switch(i) {
			default : {
					if(!this.onlineplayers.isEmpty()) {
						this.inventory.setItem(i, onlineplayers.get(1));
					} else {
						if(!this.offlineplayers.isEmpty()) {
							this.inventory.setItem(i, offlineplayers.get(1));
						}
					}
				break;
			}
			}
		}
		
		return this.inventory;
	}
	
	@SuppressWarnings("deprecation")
	public void organiseFriendList() {
		ArrayList<String> friendslist;
		friendslist = new ArrayList<String>();
		for(UUID id : player.getFriendRequests()) {
			OfflinePlayer p = Bukkit.getOfflinePlayer(id);
			friendslist.add(p.getName());
		}
		Collections.sort(friendslist, String.CASE_INSENSITIVE_ORDER);
		
		for(String string : friendslist) {
			OfflinePlayer p = Bukkit.getOfflinePlayer(string);
			if(p.isOnline()) {
				onlineplayers.add(randomUtils.Skull(p.getName(), 1));
			} else {
				offlineplayers.add(randomUtils.Skull("Wither", 1));
			}
		}
	}
	
	public int generateInventorySize() {
		int i = 18;
		if(!player.getFriendRequests().isEmpty()) {
			for(int x = 9; x <= player.getFriendRequests().size(); x=x+9) {
				i = i+x;
			}
		} else {
			i = i + 9;
		}
		return i;
	}
}