/**
 * 
 * Class Notes Section
 *
 */

package com.cynigasm.projects.minecraft;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.cynigasm.projects.minecraft.handlers.PlayerHandler;
import com.cynigasm.projects.minecraft.listeners.onPlayerJoin;
import com.cynigasm.projects.minecraft.listeners.onPlayerQuit;

/**
 * @author Cynigasm 
 * @website https://trello.com/b/Yx15qVRx
 * 1 Jun 2017 
 */

public class Project extends JavaPlugin {
	
	private static Plugin plugin;
	private File file_configuration;
	public static PlayerHandler playerhandler;
	
	public void onEnable() {
		setPlugin(this);
		
		startup();
		Project.playerhandler = new PlayerHandler();
		registerEvents();
		
		if(!file_configuration.exists()) {
			this.saveDefaultConfig();
		}
	}
	
	public void onDisable() {
		setPlugin(null);
	}
	
	public void startup() {
		this.file_configuration = new File(this.getDataFolder() + File.separator + "config.yml");
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

	public static Plugin getPlugin() {
		return plugin;
	}

	public static void setPlugin(Plugin plugin) {
		Project.plugin = plugin;
	}
}
