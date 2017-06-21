/**
 * 
 * Class Notes Section
 *
 *
 * requests to be handled/ignore checkers
 */
package com.cynigasm.projects.minecraft.social;

import com.cynigasm.projects.minecraft.Project;
import com.cynigasm.projects.minecraft.empire.Empire;
import com.cynigasm.projects.minecraft.empire.EmpireHandler;
import com.cynigasm.projects.minecraft.oPlayer;
import com.cynigasm.projects.minecraft.utility.MessageUtils;
import com.cynigasm.projects.minecraft.utility.randomUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Cynigasm 
 * @website https://trello.com/b/Yx15qVRx
 * Jun 10, 2017
 */
public class socialListener implements Listener {
	
	private Inventory inv;
	public static ArrayList<UUID> invis;
	
	@EventHandler
	public void onPlayerUse(PlayerInteractEvent event){
	    Player p = event.getPlayer();
	 
	    if(event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
		    if(p.getInventory().getItemInMainHand().equals(socialMenu.getReaper())){
		    	if(invis.contains(p.getUniqueId())) {
		    		invis.remove(p.getUniqueId());
		    		for(Player player : Bukkit.getOnlinePlayers()) {
		    			player.showPlayer(p);
		    		}
		    		randomUtils.NotifyPlayer("&f&lYOU ARE NOW &c&lVISIBLE", p);
		    	} else {
		    		invis.add(p.getUniqueId());
		    		for(Player player : Bukkit.getOnlinePlayers()) {
		    			player.hidePlayer(p);
		    		}
	    			randomUtils.NotifyPlayer("&f&lYOU ARE NOW &A&LINVISIBLE", p);
		    	}
		    	TextComponent message = new TextComponent( "Click me" );
		    	message.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "http://spigotmc.org" ) );
		    	message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Goto the Spigot website!").create() ) );
		    	event.getPlayer().spigot().sendMessage( message );
		    	event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 10, 1);
		    }
	    }
	}
	
	@EventHandler
	public void onPlayerInteractInventory(InventoryClickEvent event) {
		if(event.getClickedInventory() != null) {
			inv = event.getClickedInventory();
			int clicked = event.getSlot();
			
			if(event.getWhoClicked() instanceof Player) {
				Player clicker = (Player) event.getWhoClicked();
				oPlayer player = Project.playerhandler.getPlayer(clicker.getUniqueId());
				Empire empire = EmpireHandler.getEmpire(clicker.getUniqueId());
				boolean clickerCanInviteToClan = empire != null && empire.isOwner(clicker.getUniqueId());
				
				if(inv.getName().equalsIgnoreCase(socialMenu.inv_name)) {
					event.setCancelled(true);
						
						switch(clicked) {
						case 2 : {
							player.getPlayer().closeInventory();
							player.getPlayer().openInventory(new friendsInventory(player).getInventory());
							break;
						}
						case 4 : {
							player.getPlayer().closeInventory();
							player.getPlayer().openInventory(new requestsInventory(player).getInventory());
							break;
						}
						case 6 : {
							player.getPlayer().closeInventory();
							player.getPlayer().openInventory(new blockedInventory(player).getInventory());
							break;
						}
						default : {
							break;
						}
						}
						if(inv.getItem(clicked) != null) {
							ItemStack item = inv.getItem(clicked);
							if(item.getType().equals(Material.SKULL_ITEM)) {
								Player p = null;
								for(Player target : Bukkit.getOnlinePlayers()) {
									if(target.getDisplayName().equals(item.getItemMeta().getDisplayName())) {
										p = target;
									}
								}
								if(p == null) {
									player.getPlayer().openInventory(new socialMenu(Project.playerhandler.getPlayer(player.getId())).getInventory());
								} else {
									oPlayer target_player = Project.playerhandler.getPlayer(p.getUniqueId());
									player.getPlayer().closeInventory();
									if(player.hasFriend(target_player.getId())) {
										player.getPlayer().openInventory(new playerInventory(target_player, true, clickerCanInviteToClan).getInventory());
									} else {
										player.getPlayer().openInventory(new playerInventory(target_player, false, clickerCanInviteToClan).getInventory());
									}
								}
							}
						}
				} else if(inv.getName().equalsIgnoreCase(requestsInventory.inv_name) ||
						  inv.getName().equalsIgnoreCase(blockedInventory.inv_name) ||
						  inv.getName().equalsIgnoreCase(friendsInventory.inv_name)||
						  inv.getName().equalsIgnoreCase(playerInventory.inv_name)){
					if(inv.getItem(clicked) != null) {
						ItemStack item = inv.getItem(clicked);
						if(item.equals(socialMenu.getBackItem())) {
							player.getPlayer().closeInventory();
							player.getPlayer().openInventory(new socialMenu(Project.playerhandler.getPlayer(player.getId())).getInventory());
						}
						if(inv.getName().equalsIgnoreCase(playerInventory.inv_name)) {
							if(item.equals(socialMenu.getAddItem())) {
								if(inv.getItem(inv.getSize()-23) != null) {
									ItemStack itemstack = inv.getItem(inv.getSize() - 23);
									
									if(itemstack.getType().equals(Material.SKULL_ITEM)) {
										Player target_player;
										target_player = null;
										if(itemstack.getItemMeta().hasDisplayName()) {
											String[] item_lore_name = itemstack.getItemMeta().getLore().get(0).split(" ");
											String sorted_name = ChatColor.stripColor(item_lore_name[3]);
											for(Player o : Bukkit.getOnlinePlayers()) {
												if(o.getName().equalsIgnoreCase(sorted_name)) {
													target_player = o;
												}
											}
											if(target_player != null) {
												UUID target_id = target_player.getUniqueId();
												oPlayer target_oplayer = Project.playerhandler.getPlayer(target_id);
												if(!player.hasBlockedPlayer(target_id)) {
													if(!target_oplayer.hasBlockedPlayer(player.getId())) {
														if(!player.hasFriendRequest(target_id)) {
															if(!target_oplayer.hasFriendRequest(player.getId())) {
																target_oplayer.addFriendRequest(player.getId());
																if(target_oplayer.getPlayer().isOnline()) {
																	randomUtils.NotifyPlayer("&fYou have &e&lRECIEVED&f a friend request from &a&l" + player.getPlayer().getName(), target_oplayer.getPlayer());
																} else {
																	target_oplayer.save();
																	Project.playerhandler.removePlayer(target_id);
																}
																randomUtils.NotifyPlayer("&fYou have &e&lSENT&f a friend request to &a&l" + target_oplayer.getPlayer().getName(), player.getPlayer());
															} else {
																randomUtils.NotifyPlayer("&fYou have already requested to be friends with &l" + target_oplayer.getPlayer().getName(), player.getPlayer());
															}
														} else {
															player.addFriend(target_id);
															target_oplayer.removeFriendRequest(player.getId());
															player.removeFriendRequest(target_id);
															target_oplayer.addFriend(player.getId());
															randomUtils.NotifyPlayer("&fYou are now friends with " + target_oplayer.getPlayer().getDisplayName(), player.getPlayer());
															randomUtils.NotifyPlayer("&fYou are now friends with " + player.getPlayer().getDisplayName(), target_oplayer.getPlayer());
														}
													} else {
														randomUtils.NotifyPlayer(MessageUtils.targetHasBlocked(target_oplayer.getPlayer().getName()), player.getPlayer());
													}
												} else {
													randomUtils.NotifyPlayer(MessageUtils.targetIsBlocked(target_oplayer.getPlayer().getName()), player.getPlayer());
												}
											}
										}
									}
								}
							}  else if(item.equals(socialMenu.getDeleteItem())) {
								if(inv.getItem(inv.getSize()-23) != null) {
									ItemStack itemstack = inv.getItem(inv.getSize() - 23);
									
									if(itemstack.getType().equals(Material.SKULL_ITEM)) {
										Player target_player;
										target_player = null;
										if(itemstack.getItemMeta().hasDisplayName()) {
											String[] item_lore_name = itemstack.getItemMeta().getLore().get(0).split(" ");
											String sorted_name = ChatColor.stripColor(item_lore_name[3]);
											for(Player o : Bukkit.getOnlinePlayers()) {
												if(o.getName().equalsIgnoreCase(sorted_name)) {
													target_player = o;
												}
											}
											if(target_player != null) {
												UUID target_id = target_player.getUniqueId();
												oPlayer target_oplayer = Project.playerhandler.getPlayer(target_id);
												
												target_oplayer.removeFriend(player.getId());
												player.removeFriend(target_oplayer.getId());
												player.getPlayer().closeInventory();
											}
										}
									}
								}
							} else if (item.equals(playerInventory.getInviteToClanItem()) && clickerCanInviteToClan) {
								ItemStack itemstack = inv.getItem(inv.getSize() - 23);
								
								if(itemstack.getType().equals(Material.SKULL_ITEM)) {
									Player target_player;
									target_player = null;
									if(itemstack.getItemMeta().hasDisplayName()) {
										String[] item_lore_name = itemstack.getItemMeta().getLore().get(0).split(" ");
										String sorted_name = ChatColor.stripColor(item_lore_name[3]);
										for(Player o : Bukkit.getOnlinePlayers()) {
											if(o.getName().equalsIgnoreCase(sorted_name)) {
												target_player = o;
											}
										}
										if(target_player != null) {
											if (empire.addMember(target_player.getUniqueId())) {
												oPlayer oPlayer = Project.playerhandler.getPlayer(target_player.getUniqueId());
												player.getPlayer().openInventory(new playerInventory(oPlayer, player.hasFriend(target_player.getUniqueId()), true).getInventory());
											} else {
												randomUtils.NotifyPlayer("Your clan has reached its max. member capacity!", clicker);
											}
										}
									}
								}
							}
						} else if (inv.getName().equalsIgnoreCase(friendsInventory.inv_name)) {
							if(inv.getItem(clicked) != null) {
								if(item.getType().equals(Material.SKULL_ITEM)) {
									Player p = null;
									for(Player target : Bukkit.getOnlinePlayers()) {
										if(target.getDisplayName().equals(item.getItemMeta().getDisplayName())) {
											p = target;
										}
									}
									if(p == null) {
										player.getPlayer().openInventory(new socialMenu(Project.playerhandler.getPlayer(player.getId())).getInventory());
									} else {
										oPlayer target_player = Project.playerhandler.getPlayer(p.getUniqueId());
										player.getPlayer().closeInventory();
										if(player.hasFriend(target_player.getId())) {
											player.getPlayer().openInventory(new playerInventory(target_player, true, clickerCanInviteToClan).getInventory());
										} else {
											player.getPlayer().openInventory(new playerInventory(target_player, false, clickerCanInviteToClan).getInventory());
										}
									}
								}
							}
						}
					}
					event.setCancelled(true);
				} 
			}
		}
	}
}