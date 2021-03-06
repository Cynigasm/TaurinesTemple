package com.cynigasm.projects.minecraft.handlers;

import java.util.ArrayList;
import java.util.UUID;

import com.cynigasm.projects.minecraft.oPlayer;

public class PlayerHandler {
	
	private ArrayList<oPlayer> onlineplayers;
	
	public PlayerHandler() {
		this.setOnlinePlayers(new ArrayList<oPlayer>());
	}
	
	public boolean containsPlayer(oPlayer player) {
		boolean answer = false;
		for(oPlayer p : this.onlineplayers) {
			if(p.getId() == player.getId()) {
				answer = true;
			}
		}
		return answer;
	}
	
	public boolean containsPlayer(UUID uuid) {
		boolean answer = false;
		for(oPlayer p : this.onlineplayers) {
			if(p.getId() == uuid) {
				answer = true;
			}
		}
		return answer;
	}
	
	public void addPlayer(oPlayer player) {
		if(!this.containsPlayer(player)) {
			this.onlineplayers.add(player);
		}
	}
	
	public void addPlayer(UUID uuid) {
		oPlayer player = new oPlayer(uuid);
		if(player != null) {
			if(!containsPlayer(player)) {
				this.onlineplayers.add(player);
			}
		}
	}
	
	public void removePlayer(oPlayer player) {
		if(this.containsPlayer(player)) {
			this.onlineplayers.remove(player);
		}
	}
	
	public void removePlayer(UUID uuid) {
		oPlayer player = new oPlayer(uuid);
		if(player != null) {
			if(containsPlayer(player)) {
				this.onlineplayers.remove(this.getPlayer(uuid));
			}
		}
	}
	
	public void setOnlinePlayers(ArrayList<oPlayer> players) {
		this.onlineplayers = players;
	}
	
	public ArrayList<oPlayer> getOnlinePlayers() {
		return this.onlineplayers;
	}
	
	public oPlayer getPlayer(UUID uuid) {
		oPlayer p = null;
		for(oPlayer player : this.onlineplayers) {
			if(player.getId().equals(uuid)) {
				p = player;
			}
		}
		return p;
	}
	
	public void savePlayers() {
		for(oPlayer player : this.onlineplayers) {
			player.save();
		}
	}
	
}