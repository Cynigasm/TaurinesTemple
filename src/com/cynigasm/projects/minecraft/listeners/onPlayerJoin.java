package com.cynigasm.projects.minecraft.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.cynigasm.projects.minecraft.Project;

public class onPlayerJoin implements Listener {
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		if(!Project.playerhandler.containsPlayer(event.getPlayer().getUniqueId())) {
			Project.playerhandler.addPlayer(event.getPlayer().getUniqueId());
			
		}
	}
}