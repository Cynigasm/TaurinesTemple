package com.cynigasm.projects.minecraft.schematics;

import com.cynigasm.projects.minecraft.Project;
import com.cynigasm.projects.minecraft.utility.YMLConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.Metadatable;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ProtectedSchematic {
	public static void protect(Metadatable metadatable) {
		metadatable.setMetadata("empireProtected", new FixedMetadataValue(Project.getPlugin(), true));
	}
	
	public static boolean isProtected(Metadatable metadatable) {
		return metadatable.hasMetadata("empireProtected");
	}
	
	public static void create(String empire, String schematicName, World world, List<Block> blocks, List<Entity> entities) {
		YMLConfig config = new YMLConfig(Project.getPlugin(), "empires" + File.separator + empire + File.separator + schematicName + ".ps.yml");
		config.set("world", world.getName());
		
		List<Map<String, Integer>> serializedBlocks = new ArrayList<>();
		for (Block block : blocks) {
			Map<String, Integer> map = new HashMap<>();
			map.put("x", block.getX());
			map.put("y", block.getY());
			map.put("z", block.getZ());
			serializedBlocks.add(map);
		}
		config.set("blocks", serializedBlocks);
		
		config.set("entities", entities.stream().map((entity) -> entity.getUniqueId().toString()).collect(Collectors.toList()));
		config.save();
	}
	
	@SuppressWarnings("unchecked")
	public static void load(File file) {
		YMLConfig config = new YMLConfig(file);
		config.load();
		
		World world = Bukkit.getWorld(config.getString("world"));
		if (world == null) {
			return;
		}
		
		//for (Map<String, Object> map : (List<Map<String, Object>>)config.getList("blocks")) {
			//Block block = world.getBlockAt((Integer)map.get("x"), (Integer)map.get("y"), (Integer)map.get("z"));
		for (Map<String, Integer> map : (List<Map<String, Integer>>)config.getList("blocks")) {
			Block block = world.getBlockAt(map.get("x"), map.get("y"), map.get("z"));
			if (block.getType() != Material.AIR) {
				protect(block);
			}
		}
		
		for (String string : config.getStringList("entities")) {
			Entity entity = Bukkit.getEntity(UUID.fromString(string));
			if (entity != null) {
				protect(entity);
			}
		}
	}
}