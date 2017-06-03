/**
 * 
 * Class Notes Section
 *
 */

package com.cynigasm.projects.minecraft;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.cynigasm.projects.minecraft.listeners.onPlayerQuit;
import com.cynigasm.projects.minecraft.listeners.onPlayerJoin;

/**
 * @author Cynigasm 
 * @website https://trello.com/b/Yx15qVRx
 * 1 Jun 2017 
 */

public class Project extends JavaPlugin {
	
	private static Plugin plugin;
	private File file_configuration;
	private static ArrayList<oPlayer> onlineplayers;
	
	public void onEnable() {
		setPlugin(this);
		
		startup();
		registerEvents();
		playerSavingScheduler();
		
		if(!file_configuration.exists()) {
			this.saveDefaultConfig();
		}
	}
	
	public void onDisable() {
		setPlugin(null);
	}
	
	public void startup() {
		this.file_configuration = new File(this.getDataFolder() + File.separator + "config.yml");
		Project.onlineplayers = new ArrayList<oPlayer>();
	}
	
	public void registerEvents() {
		PluginManager plugin_manager = Bukkit.getServer().getPluginManager();
		plugin_manager.registerEvents(new onPlayerJoin(), this);
		plugin_manager.registerEvents(new onPlayerQuit(), this);
	}
	
	public File getConfigurationFile() {
		return this.file_configuration;
	}
	
	public void setConfigurationFile(File file) {
		this.file_configuration = file;
	}

	public static ArrayList<oPlayer> getOnlineplayers() {
		return onlineplayers;
	}

	public static void setOnlineplayers(ArrayList<oPlayer> onlineplayers) {
		Project.onlineplayers = onlineplayers;
	}
	
	public static void addPlayer(UUID uuid) {
		oPlayer player = new oPlayer(uuid);
		if(!playerIsOnline(uuid)) {
			Project.onlineplayers.add(player);
		}
	}
	
	public static void removePlayer(UUID uuid) {
		oPlayer player = new oPlayer(uuid);
		if(playerIsOnline(uuid)) {
			Project.onlineplayers.remove(player);
		}
	}
	
	public static oPlayer getPlayer(UUID uuid) {
		oPlayer result = null;
		for(oPlayer oplayer : Project.onlineplayers) {
			if(oplayer.getId().equals(uuid)) {
				result = oplayer;
			}
		}
		return result;
	}
	
	public static boolean playerIsOnline(UUID uuid) {
		boolean online = false;
		for(oPlayer player : Project.onlineplayers) {
			if(player.getId().equals(uuid)) {
				online = true;
			}
		}
		return online;
	}
	
	public void playerSavingScheduler() {
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	if(!onlineplayers.isEmpty()) {
                    for(oPlayer player : getOnlineplayers()) {
                    	player.save();
                    }
            	}
            }
        }, 0L, 20L);
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public static void setPlugin(Plugin plugin) {
		Project.plugin = plugin;
	}
}
