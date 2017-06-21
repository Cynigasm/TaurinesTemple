package com.cynigasm.projects.minecraft.utility;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class YMLConfig extends YamlConfiguration {
	public YMLConfig(JavaPlugin plugin, String fileName) {
		file = new File(plugin.getDataFolder() + File.separator + fileName);
		if (!file.exists()) {
			//noinspection ResultOfMethodCallIgnored
			file.getParentFile().mkdirs();
		}
	}
	
	private File file;
	
	
	
	public void reload() {
		if (file.exists()) {
			try {
				load(file);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void save() {
		try {
			save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void load() {
		if (file.exists()) {
			try {
				load(file);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void setUUIDSet(String path, Set<UUID> set) {
		set(path, set.stream().map(UUID::toString).collect(Collectors.toList()));
	}
	
	public void set(String path, Set<?> set) {
		set(path, new ArrayList<>(set));
	}
	
	public void set(String path, UUID id) {
		if (id != null) {
			set(path, id.toString());
		} else {
			set(path, (Object)null);
		}
	}
	
	public Set<UUID> getUUIDSet(String path) {
		return getStringList(path).stream().map(UUID::fromString).collect(Collectors.toSet());
	}
	
	public Set<String> getStringSet(String path) {
		return new HashSet<>(getStringList(path));
	}
	
	public UUID getUUID(String path) {
		String id = getString(path);
		if (id != null) {
			return UUID.fromString(id);
		} else {
			return null;
		}
	}
}