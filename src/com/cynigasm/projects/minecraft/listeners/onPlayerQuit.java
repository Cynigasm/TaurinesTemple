package com.cynigasm.projects.minecraft.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.cynigasm.projects.minecraft.Project;
import com.cynigasm.projects.minecraft.social.socialMenu;

public class onPlayerQuit implements Listener {
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) {
		if(Project.playerhandler.containsPlayer(event.getPlayer().getUniqueId())) {
			Project.playerhandler.getPlayer(event.getPlayer().getUniqueId()).save();
			Project.playerhandler.removePlayer(event.getPlayer().getUniqueId());
		}
		event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), socialMenu.getReaper());
	}
}