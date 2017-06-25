package com.cynigasm.projects.minecraft.empire;

import com.cynigasm.projects.minecraft.Project;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EmpireHandler {
	public EmpireHandler() {
		instance = this;
		
		empires = new HashSet<>();
		File folder = new File(Project.getPlugin().getDataFolder() + File.separator + "empires");
		if (folder.exists()) {
			File[] files = folder.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.getName().endsWith(".yml")) {
						empires.add(new Empire(file.getName().substring(0, file.getName().length() - 4)));
					}
				}
			}
		} else {
			folder.mkdirs();
		}
		
		Bukkit.getScheduler().runTaskTimer(Project.getPlugin(), () -> {
			for (Empire empire : empires) {
				empire.save();
			}
		}, 12, 20); //12: random delay, so not every scheduled task gets executed on the same tick
	}
	
	private static EmpireHandler instance = null;
	private final Set<Empire> empires;
	
	
	
	public static Empire getEmpire(String name) {
		for (Empire empire : instance.empires) {
			if (empire.getName().equalsIgnoreCase(name)) {
				return empire;
			}
		}
		return null;
	}
	
	public static Empire getEmpire(UUID member) {
		for (Empire empire : instance.empires) {
			if (empire.isMember(member)) {
				return empire;
			}
		}
		return null;
	}
	
	
	
	public static void addEmpire(String name, UUID leader) {
		instance.empires.add(new Empire(name, leader));
	}
	
	public static void removeEmpire(Empire empire) {
		instance.empires.remove(empire);
		File file = new File(Project.getPlugin().getDataFolder() + File.separator + "empires" + File.separator + empire.getName() + ".yml");
		if (file.exists()) {
			//noinspection ResultOfMethodCallIgnored
			file.delete();
		}
		empire.disband();
	}
}