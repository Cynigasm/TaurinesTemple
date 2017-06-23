package com.cynigasm.projects.minecraft.schematics;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Schematic {
	public Schematic(Location firstCorner, Location secondCorner, Location origin) {
		Vector smallest = new Vector();
		Vector biggest = new Vector();
		if (firstCorner.getBlockX() < secondCorner.getBlockX()) {
			smallest.setX(firstCorner.getBlockX());
			biggest.setX(secondCorner.getBlockX());
		} else {
			smallest.setX(secondCorner.getBlockX());
			biggest.setX(firstCorner.getBlockX());
		}
		if (firstCorner.getBlockY() < secondCorner.getBlockY()) {
			smallest.setY(firstCorner.getBlockY());
			biggest.setY(secondCorner.getBlockY());
		} else {
			smallest.setY(secondCorner.getBlockY());
			biggest.setY(firstCorner.getBlockY());
		}
		if (firstCorner.getBlockZ() < secondCorner.getBlockZ()) {
			smallest.setZ(firstCorner.getBlockZ());
			biggest.setZ(secondCorner.getBlockZ());
		} else {
			smallest.setZ(secondCorner.getBlockZ());
			biggest.setZ(firstCorner.getBlockZ());
		}
		
		blocks = new ArrayList<>();
		for (int x = smallest.getBlockX(); x <= biggest.getBlockX(); x++) {
			for (int y = smallest.getBlockY(); y <= biggest.getBlockY(); y++) {
				for (int z = smallest.getBlockZ(); z <= biggest.getBlockZ(); z++) {
					blocks.add(new SerializableBlock(origin.getWorld().getBlockAt(x, y, z), origin));
				}
			}
		}
		
		entities = new ArrayList<>();
		
	}
	
	@SuppressWarnings ("unchecked")
	public Schematic(Map<String, Object> serialized) {
		blocks = ((List<Map<String, Object>>)serialized.get("blocks")).stream().map(SerializableBlock::new).collect(Collectors.toList());
		entities = ((List<Map<String, Object>>)serialized.get("entities")).stream().map(SerializableEntity::new).collect(Collectors.toList());
	}
	
	private final List<SerializableBlock> blocks;
	private final List<SerializableEntity> entities;
	
	
	
	public void paste(Location origin) {
		for (SerializableBlock block : blocks) {
			block.paste(origin);
		}
		for (SerializableEntity entity : entities) {
			entity.paste(origin);
		}
	}
	
	public Map<String, Object> serialize() {
		Map<String, Object> serialized = new HashMap<>();
		serialized.put("blocks", blocks.stream().map(SerializableBlock::serialize).collect(Collectors.toList()));
		serialized.put("entities", entities.stream().map(SerializableEntity::serialize).collect(Collectors.toList()));
		return serialized;
	}
	
	
	
	/*public static Set<Chunk> getChunks(Chunk firstChunk, Chunk secondChunk) {
		int minChunkX;
		int maxChunkX;
		if (firstChunk.getX() < secondChunk.getX()) {
			minChunkX = firstChunk.getX();
			maxChunkX = secondChunk.getX();
		} else {
			minChunkX = secondChunk.getX();
			maxChunkX = firstChunk.getX();
		}
		
		int minChunkZ;
		int maxChunkZ;
		if (firstChunk.getZ() < secondChunk.getZ()) {
			minChunkZ = firstChunk.getZ();
			maxChunkZ = secondChunk.getZ();
		} else {
			minChunkZ = secondChunk.getZ();
			maxChunkZ = firstChunk.getZ();
		}
		
		Set<Chunk> chunks = new HashSet<>();
		for (int x = minChunkX; x < maxChunkX; x++) {
			for (int z = minChunkZ; z < maxChunkZ; z++) {
				chunks.add(firstChunk.getWorld().getChunkAt(x, z));
			}
		}
		return chunks;
	}*/
}