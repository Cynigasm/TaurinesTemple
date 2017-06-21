/**
 * 
 * Class Notes Section
 *
 */
package com.cynigasm.projects.minecraft.social;

import com.cynigasm.projects.minecraft.oPlayer;
import com.cynigasm.projects.minecraft.utility.ItemBuilder;
import com.cynigasm.projects.minecraft.utility.MessageUtils;
import com.cynigasm.projects.minecraft.utility.randomUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * @author Cynigasm 
 * @website https://trello.com/b/Yx15qVRx
 * Jun 10, 2017
 */
public class socialMenu {
	
	private Inventory inv;
	private ItemStack item_friends;
	private ItemStack item_requests;
	private ItemStack item_blocked;
	private oPlayer player;
	private ArrayList<ItemStack> onlineplayers;
	public static String inv_name = MessageUtils.format("&lSOCIAL MANAGEMENT");
	
	public socialMenu(oPlayer player) {
		onlineplayers = new ArrayList<>();
		this.setPlayer(player);
		this.inv = Bukkit.createInventory(null, generateInventorySize(), inv_name);
		organiseFriendList();
		
		ItemBuilder ib = new ItemBuilder(Material.INK_SACK);
		ib.setDurability((short)2);
		ib.setName(MessageUtils.format("&a&lFRIENDS LIST"));
		ib.setLore(MessageUtils.format("&fYou currently have &a&l" + player.getOnlineFriends() + "&f friends online."));
		this.item_friends = ib.getItem();
		
		ib = new ItemBuilder(Material.INK_SACK);
		ib.setDurability((short)11);
		ib.setName(MessageUtils.format("&e&lFRIEND REQUESTS"));
		ib.setLore(MessageUtils.format("&fYou currently have &e&l" + player.getFriendRequests().size() + "&f friend requests."));
		this.item_requests = ib.getItem();
		
		ib = new ItemBuilder(Material.INK_SACK);
		ib.setDurability((short)1);
		ib.setName(MessageUtils.format("&c&lBLOCK LIST"));
		ib.setLore(MessageUtils.format("&fYou have blocked &c&l" + player.getBlocklist().size() + "&f players."));
		this.item_blocked = ib.getItem();
	}
	
	public Inventory getInventory() {
		for(int i = 0; i < this.inv.getSize(); i++) {
			switch(i) {
			case 2 :  {
				this.inv.setItem(i, this.item_friends);
				break;
			}
			case 4 :  {
				this.inv.setItem(i, this.item_requests);
				break;
			}
			case 6 :  {
				this.inv.setItem(i, this.item_blocked);
				break;
			}
			default : {
				break;
			}
			}
		}
		int x = 18;
		for(ItemStack item : this.onlineplayers) {
			this.inv.setItem(x, item);
			x++;
		}
		return this.inv;	
	}
	
	public void organiseFriendList() {
		ArrayList<String> friendslist;
		friendslist = new ArrayList<>();
		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			if(!player.getName().equals(this.player.getPlayer().getName())) {
				friendslist.add(player.getName());
			}
		}
		friendslist.sort(String.CASE_INSENSITIVE_ORDER);
		
		
		
		for(String string : friendslist) {
			Player p = Bukkit.getServer().getPlayerExact(string);
			if(p.isOnline()) {
				onlineplayers.add(randomUtils.Skull(p.getName(), 1));
			}
		}
	}
	
	public int generateInventorySize() {
		int i = 27;
		for(int x = 9; x <= Bukkit.getServer().getOnlinePlayers().size() -1; x += 9) {
			i += x;
		}
		return i;
	}

	public oPlayer getPlayer() {
		return player;
	}

	public void setPlayer(oPlayer player) {
		this.player = player;
	}
	
	public static ItemStack getBackItem() {
		ItemBuilder ib = new ItemBuilder(Material.WOOD_DOOR);
		ib.setName(MessageUtils.format("&f&lBACK"));
		return ib.getItem();
	}
	
	public static ItemStack getAddItem() {
		ItemBuilder ib = new ItemBuilder(Material.SLIME_BALL);
		ib.setName(MessageUtils.format("&a&lSend friend request"));
		ib.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
		ib.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		return ib.getItem();
	}
	
	public static ItemStack getDeleteItem() {
		ItemBuilder ib = new ItemBuilder(Material.TNT);
		ib.setName(MessageUtils.format("&c&lUnfriend this player"));
		ib.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
		ib.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		return ib.getItem();
	}
	
	public static ItemStack getReaper() {
		ItemBuilder ib = new ItemBuilder(Material.POTATO_ITEM);
		ib.setName(MessageUtils.format("&e&lGOD'S CROP"));
		ib.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_ENVIRONMENTAL.getMaxLevel());
		ib.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		return ib.getItem();
	}
}
