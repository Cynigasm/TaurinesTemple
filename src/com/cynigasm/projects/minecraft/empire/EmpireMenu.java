package com.cynigasm.projects.minecraft.empire;

import com.cynigasm.projects.minecraft.menus.ConfirmationMenu;
import com.cynigasm.projects.minecraft.menus.MenuInventoryHolder;
import com.cynigasm.projects.minecraft.utility.ItemBuilder;
import com.cynigasm.projects.minecraft.utility.MessageUtils;
import com.cynigasm.projects.minecraft.utility.randomUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionType;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EmpireMenu extends MenuInventoryHolder {
	public EmpireMenu(Player player, Empire empire, int page) {
		this.empire = empire;
		this.page = page;
		Inventory inventory = Bukkit.createInventory(this, 54, MessageUtils.format("&lCLAN MANAGEMENT"));
		
		inventory.setItem(0, new ItemBuilder(Material.GOLD_BLOCK).setName("&lTREASURY").getItem());
		inventory.setItem(8, new ItemBuilder(Material.DIAMOND_BLOCK).setName("&lUPGRADES").getItem());
		
		ItemStack informationItem = new ItemBuilder(Material.BOOK_AND_QUILL)
				.setName("&lINFORMATION")
				.setLore("&eName: &7" + empire.getName(),
				         "&eLevel: &7" + empire.getLevel(),
				         "&eLeader: &7" + Bukkit.getOfflinePlayer(empire.getOwner()).getName(),
				         "&eMember count: &7" + empire.getMemberCount(),
				         "&eGold balance: &7" + empire.getGold())
				.addEnchantment(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).getItem();
		if (empire.isOwner(player.getUniqueId())) {
			inventory.setItem(3, informationItem);
			inventory.setItem(5, new ItemBuilder(Material.TNT).setName("&lDISBAND").setLore("&cDisband the clan. This action cannot be undone.").getItem());
		} else {
			inventory.setItem(4, informationItem);
		}
		
		ItemStack fillerItem = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability(15).setName("").getItem();
		for (int i = 0; i < 18; i++) {
			if (inventory.getItem(i) == null) {
				inventory.setItem(i, fillerItem);
			}
		}
		for (int i = 36; i < inventory.getSize(); i++) {
			if (inventory.getItem(i) == null) {
				inventory.setItem(i, fillerItem);
			}
		}
		
		Comparator<ItemStack> comparator = Comparator.comparing((item) -> item.getItemMeta().getDisplayName().toLowerCase());
		List<ItemStack> skulls = empire.getMembers().stream().map((id) -> randomUtils.getSkull(Bukkit.getOfflinePlayer(id))).sorted(comparator).collect(Collectors.toList());
		
		int maxPage;
		if (skulls.size() > 36) {
			maxPage = 3;
		} else if (skulls.size() > 18) {
			maxPage = 2;
		} else {
			maxPage = 1;
		}
		
		if (page > maxPage) {
			page = maxPage;
		}
		if (page > 1) {
			inventory.setItem(45, new ItemBuilder(Material.TIPPED_ARROW).setName("&lPREVIOUS PAGE").setPotionData(PotionType.INSTANT_HEAL).getItem());
		}
		if (page < maxPage) {
			inventory.setItem(53, new ItemBuilder(Material.TIPPED_ARROW).setName("&lNEXT PAGE").setPotionData(PotionType.JUMP).getItem());
		}
		
		ItemStack skeletonItem = new ItemBuilder(Material.SKULL_ITEM).setDurability(0).setName("&lFREE SPACE").getItem();
		ItemStack barrierItem = new ItemBuilder(Material.BARRIER).setName("&lUPGRADE CLAN").getItem();
		int slot = 18;
		for (int i = (page - 1) * 18; ; i++) {
			ItemStack item;
			if (i < skulls.size()) {
				item = skulls.get(i);
			} else if (i < empire.getMaxMemberCount()) {
				item = skeletonItem;
			} else if (empire.getLevel() != 10) {
				item = barrierItem;
			} else {
				item = fillerItem;
			}
			inventory.setItem(slot, item);
			slot++;
			if (slot > 35) {
				break;
			}
		}
		
		player.openInventory(inventory);
	}
	
	private final Empire empire;
	private final int page;
	
	
	
	@Override
	public void onClick(Player player, int slot, boolean menuWasClicked, InventoryClickEvent event) {
		event.setCancelled(true);
		ItemStack item = event.getCurrentItem();
		if (menuWasClicked && item != null && item.getType() != Material.STAINED_GLASS_PANE) {
			switch (slot) {
				case 0:
					new GoldStorageMenu(player, empire);
					return;
				case 5:
					onDisbandClick(player);
					return;
				case 45:
					new EmpireMenu(player, empire, page - 1);
					return;
				case 53:
					new EmpireMenu(player, empire, page + 1);
					return;
			}
			if (item.getType() == Material.SKULL_ITEM && item.getDurability() == 3) {
				OfflinePlayer target = randomUtils.getOfflinePlayer(((SkullMeta)item.getItemMeta()).getOwner());
				if (target != null) {
					new MemberMenu(player, target, empire, page);
				}
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
	
	
	
	private void onDisbandClick(Player player) {
		Consumer<Boolean> consumer = (confirmed) -> {
			if (confirmed) {
				EmpireHandler.removeEmpire(empire);
				player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
			} else {
				player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
			}
			player.closeInventory();
		};
		new ConfirmationMenu(player, "&lDISBAND CLAN", "&lCONFIRM", Collections.singletonList("&cDisband the clan."), "&lCANCEL", Collections.singletonList("&2Keep the clan."), consumer);
	}
}