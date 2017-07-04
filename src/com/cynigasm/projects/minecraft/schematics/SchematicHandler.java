package com.cynigasm.projects.minecraft.schematics;

import com.cynigasm.projects.minecraft.Project;
import com.cynigasm.projects.minecraft.utility.YMLConfig;

import java.io.File;

public class SchematicHandler {
	public SchematicHandler() {
		File folder = new File(Project.getPlugin().getDataFolder() + File.separator + "schematics");
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}
	
	//
	
	
	
	public static Schematic getSchematic(String name) {
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
		YMLConfig config = new YMLConfig(Project.getPlugin(), "schematics" + File.separator + name + ".yml");
		schematic.serialize().entrySet().forEach((entry) -> config.set(entry.getKey(), entry.getValue()));
		config.save();
	}
}