/**
 * 
 * Class Notes Section
 *
 */

package com.cynigasm.projects.minecraft;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Cynigasm 
 * @website https://trello.com/b/Yx15qVRx
 * 1 Jun 2017 
 */

public class Project extends JavaPlugin {
	
	File file_configuration = new File(this.getDataFolder() + File.separator + "config.yml");
	
	public void onEnable() {
		
		if(!file_configuration.exists()) {
			this.saveDefaultConfig();
		}
	}
	
	public void onDisable() {
		
	}
}
