package com.cynigasm.projects.minecraft.schematics;

import com.cynigasm.projects.minecraft.Project;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;

public class ProtectedSchematicListener implements Listener {
	public ProtectedSchematicListener() {
		Bukkit.getPluginManager().registerEvents(this, Project.getPlugin());
	}
	
	//
	
	
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	private void onBlockBreak(BlockBreakEvent event) {
		if (!event.getPlayer().isOp() && ProtectedSchematic.isProtected(event.getBlock())) {
			event.setCancelled(true);
		}
	}
	
	
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	private void onBlockPistonExtend(BlockPistonExtendEvent event) {
		for (Block block : event.getBlocks()) {
			if (ProtectedSchematic.isProtected(block)) {
				event.setCancelled(true);
				break;
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	private void onBlockPistonRetract(BlockPistonRetractEvent event) {
		for (Block block : event.getBlocks()) {
			if (ProtectedSchematic.isProtected(block)) {
				event.setCancelled(true);
				break;
			}
		}
	}
	
	
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	private void onBlockExplode(BlockExplodeEvent event) {
		event.blockList().removeIf(ProtectedSchematic::isProtected);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	private void onEntityExplode(EntityExplodeEvent event) {
		event.blockList().removeIf(ProtectedSchematic::isProtected);
	}
	
	
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	private void onHangingBreak(HangingBreakEvent event) {
		if (ProtectedSchematic.isProtected(event.getEntity()) && (!(event instanceof HangingBreakByEntityEvent) || !((HangingBreakByEntityEvent)event).getRemover().isOp())) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	private void onEntityDamage(EntityDamageEvent event) {
		if (ProtectedSchematic.isProtected(event.getEntity()) && (!(event instanceof EntityDamageByEntityEvent) || !((EntityDamageByEntityEvent)event).getDamager().isOp())) {
			event.setCancelled(true);
		}
	}
}