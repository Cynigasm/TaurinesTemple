/**
 * 
 * Class Notes Section
 *
 */
package com.cynigasm.projects.minecraft.utility;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Trigary
 * @website https://github.com/Trigary/TGUtils/blob/master/src/main/java/hu/trigary/tgutils/ItemBuilder.java
 * Jun 10, 2017
 */

public class ItemBuilder {
	
	public ItemBuilder(Material material) {
		item = new ItemStack(material);
		meta = item.getItemMeta();
	}
	
	private ItemStack item;
	private ItemMeta meta;
	
	public ItemStack getItem() {
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemBuilder setName(String name) {
		meta.setDisplayName(MessageUtils.format(name));
		return this;
	}
	
	public ItemBuilder setLore(List<String> lore) {
		meta.setLore(lore.stream().map(MessageUtils::format).collect(Collectors.toList()));
		return this;
	}
	
	
	public ItemBuilder setLore(String... lore) {
		meta.setLore(Arrays.stream(lore).map(MessageUtils::format).collect(Collectors.toList()));
		return this;
	}
	
	public ItemBuilder setAmount(int amount) {
		item.setAmount(amount);
		return this;
	}
	
	public ItemBuilder setDurability(int durability) {
		item.setDurability((short)durability);
		return this;
	}
	
	public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
		meta.addEnchant(enchantment, level, true);
		return this;
	}
	
	public ItemBuilder setItemStack(ItemStack itemstack) {
		item = itemstack;
		meta = itemstack.getItemMeta();
		return this;
	}
	
	public ItemBuilder addItemFlags(ItemFlag... flags) {
		meta.addItemFlags(flags);
		return this;
	}
	
	
	
	public ItemBuilder setPotionData(PotionType type) {
		PotionMeta potionMeta = (PotionMeta)meta;
		potionMeta.setBasePotionData(new PotionData(type));
		return this;
	}
	
	public ItemBuilder setPotionData(PotionType type, boolean extended, boolean upgraded) {
		PotionMeta potionMeta = (PotionMeta)meta;
		potionMeta.setBasePotionData(new PotionData(type, extended, upgraded));
		return this;
	}
	
	public ItemBuilder setPotionColor(Color color) {
		PotionMeta potionMeta = (PotionMeta)meta;
		potionMeta.setColor(color);
		return this;
	}
}