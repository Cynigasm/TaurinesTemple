package com.cynigasm.projects.minecraft.schematics;

import com.cynigasm.projects.minecraft.Project;
import com.cynigasm.projects.minecraft.utility.YMLConfig;

import java.io.File;

public class SchematicHandler {
	public SchematicHandler() {
		//instance = this;
		
		//schematics = new HashMap<>();
		File folder = new File(Project.getPlugin().getDataFolder() + File.separator + "schematics");
		if (folder.exists()) {
			/*File[] files = folder.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.getName().endsWith(".yml")) {
						schematics.put(file.getName().substring(file.getName().length() - 4), new Schematic());
					}
				}
			}*/
		} else {
			folder.mkdirs();
		}
	}
	
	//private static SchematicHandler instance = null;
	//private final Map<String, Schematic> schematics;
	
	
	
	public static Schematic getSchematic(String name) {
		//return instance.schematics.get(name);
		File file = new File(Project.getPlugin().getDataFolder() + File.separator + "schematics" + File.separator + name + ".yml");
		if (file.exists()) {
			YMLConfig config = new YMLConfig(file);
			config.load();
			return new Schematic(config.getValues(false));
		} else {
			return null;
		}
	}
	
	public static void setSchematic(String name, Schematic schematic) {
		//instance.schematics.put(name, schematic);
		YMLConfig config = new YMLConfig(Project.getPlugin(), "schematics" + File.separator + name + ".yml");
		schematic.serialize().entrySet().forEach((entry) -> config.set(entry.getKey(), entry.getValue()));
		config.save();
	}
}