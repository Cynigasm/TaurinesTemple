/**
 * 
 * Class Notes Section
 *
 */
package com.cynigasm.projects.minecraft.social;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.cynigasm.projects.minecraft.utility.ItemBuilder;
import com.cynigasm.projects.minecraft.utility.MessageUtils;

import net.minecraft.server.v1_11_R1.ChatMessage;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.PacketPlayOutOpenWindow;

/**
 * @author Lewysryan
 * @website https://bukkit.org/threads/1-8-open-an-anvil-inventory-not-much-code.328178/
 * Jun 10, 2017
 */
public class Anvil {
	
	public static ItemStack item_additem;
	private static ItemBuilder ib;

    public static void openAnvilInventory(final Player player) {
   
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        FakeAnvil fakeAnvil = new FakeAnvil(entityPlayer);
        int containerId = entityPlayer.nextContainerCounter();
   
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutOpenWindow(containerId, "minecraft:anvil", new ChatMessage("Repairing", new Object[]{}), 0));
   
        entityPlayer.activeContainer = fakeAnvil;
        entityPlayer.activeContainer.windowId = containerId;
        entityPlayer.activeContainer.addSlotListener(entityPlayer);
        entityPlayer.activeContainer = fakeAnvil;
        entityPlayer.activeContainer.windowId = containerId;
   
        Inventory inv = fakeAnvil.getBukkitView().getTopInventory();
        
        ib = new ItemBuilder(Material.SKULL_ITEM);
        ib.setAmount((short) 1);
        ib.setName(MessageUtils.format("Enter Name Here"));
        ib.setLore(MessageUtils.format("&aMore friends, Yay!"));
        item_additem = ib.getItem();
        inv.setItem(0, item_additem);
   
        }
}