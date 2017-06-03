package com.cynigasm.projects.minecraft;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class oPlayer {
	
	private String prefix;
	private ArrayList<String> prefixes;
	private UUID id;
	private File playerfile;
	private YamlConfiguration configuration;
	private double balance;
	private Player player;
	
	public oPlayer(UUID id) {
		this.prefix = Project.getPlugin().getConfig().getString("players.default_prefix");
		this.player = Bukkit.getPlayer(id);
		this.prefixes = new ArrayList<String>();
		this.playerfile = new File(Project.getPlugin().getDataFolder() + File.separator + "players" + File.separator + id.toString() + ".yml");
		this.configuration = YamlConfiguration.loadConfiguration(playerfile);
		this.balance = 0D;
		if(player.hasPlayedBefore()) {
			load();
		}
	}
	
	public void load() {
		this.prefix = configuration.getString("player.prefix");
		
		for(String prefix : configuration.getStringList("player.prefixes")) {
			if(!this.prefixes.contains(prefix)) {
				this.prefixes.add(prefix);
			}
		}
		
		this.balance = configuration.getDouble("player.balance");
	}
	
	public void save() {
		configuration.set("player.prefix", this.prefix);
		configuration.set("player.prefixes", this.prefixes);
		configuration.set("player.balance", this.balance);
		try {
			configuration.save(playerfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public ArrayList<String> getPrefixes() {
		return prefixes;
	}

	public void setPrefixes(ArrayList<String> prefixes) {
		this.prefixes = prefixes;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public File getPlayerfile() {
		return playerfile;
	}

	public void setPlayerfile(File playerfile) {
		this.playerfile = playerfile;
	}

	public YamlConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(YamlConfiguration configuration) {
		this.configuration = configuration;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}