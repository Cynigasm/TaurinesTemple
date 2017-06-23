/**
 * 
 * Class Notes Section
 *
 */

package com.cynigasm.projects.minecraft;

import com.cynigasm.projects.minecraft.empire.EmpireCommands;
import com.cynigasm.projects.minecraft.commands.socialCommands;
import com.cynigasm.projects.minecraft.empire.EmpireHandler;
import com.cynigasm.projects.minecraft.handlers.PlayerHandler;
import com.cynigasm.projects.minecraft.listeners.onDropEvent;
import com.cynigasm.projects.minecraft.listeners.onPlayerJoin;
import com.cynigasm.projects.minecraft.listeners.onPlayerQuit;
import com.cynigasm.projects.minecraft.menus.MenuEventListener;
import com.cynigasm.projects.minecraft.schematics.SchematicCommands;
import com.cynigasm.projects.minecraft.social.socialListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Cynigasm 
 * @website https://trello.com/b/Yx15qVRx
 * 1 Jun 2017 
 */

public class Project extends JavaPlugin {
	
	private static JavaPlugin plugin;
	private File file_configuration;
	public static PlayerHandler playerhandler;
	
	public void onEnable() {
		setPlugin(this);
		
		startup();
		Project.playerhandler = new PlayerHandler();
		new EmpireHandler();
		registerEvents();
		
		if(!file_configuration.exists()) {
			this.saveDefaultConfig();
		}
		
		reloadPlayers();
		onDropEvent.disable = false;
		socialListener.invis = new ArrayList<UUID>();
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
		plugin_manager.registerEvents(new onDropEvent(), this);
		plugin_manager.registerEvents(new socialListener(), this);
		
		new MenuEventListener();
		
		this.getCommand("social").setExecutor(new socialCommands());
		new EmpireCommands();
		new SchematicCommands();
	}
	
	public File getConfigurationFile() {
		return this.file_configuration;
	}
	
	public void setConfigurationFile(File file) {
		this.file_configuration = file;
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}

	public static void setPlugin(JavaPlugin plugin) {
		Project.plugin = plugin;
	}
	
	public void reloadPlayers() {
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
            public void run() {
				for(Player player : Bukkit.getOnlinePlayers()) {
					if(!playerhandler.containsPlayer(player.getUniqueId())) {
						playerhandler.addPlayer(player.getUniqueId());
					}
				}
				if(Bukkit.getOnlinePlayers().size() > 0) {
					playerhandler.savePlayers();
				}
            }
        }, 0L, 20L);
	}
}
