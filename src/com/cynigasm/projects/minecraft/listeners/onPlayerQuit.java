package com.cynigasm.projects.minecraft.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.cynigasm.projects.minecraft.Project;

public class onPlayerQuit implements Listener {
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) {
		if(Project.playerIsOnline(event.getPlayer().getUniqueId())) {
			Project.getPlayer(event.getPlayer().getUniqueId()).save();
			Project.removePlayer(event.getPlayer().getUniqueId());
		}
	}
}