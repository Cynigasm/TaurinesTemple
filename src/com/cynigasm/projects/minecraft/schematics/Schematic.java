package com.cynigasm.projects.minecraft.schematics;

import com.cynigasm.projects.minecraft.Project;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.Collectors;

public class Schematic {
	private final static EnumSet<Material> dependentMaterials = EnumSet.of(Material.TORCH, Material.REDSTONE_TORCH_ON, Material.REDSTONE_TORCH_OFF, Material.LADDER,
	                                                                       Material.TRAP_DOOR, Material.IRON_TRAPDOOR, Material.ACACIA_DOOR, Material.BIRCH_DOOR,
	                                                                       Material.DARK_OAK_DOOR, Material.JUNGLE_DOOR, Material.SPRUCE_DOOR, Material.WOOD_DOOR,
	                                                                       Material.WOODEN_DOOR, Material.IRON_DOOR_BLOCK, Material.FLOWER_POT, Material.YELLOW_FLOWER,
	                                                                       Material.TRIPWIRE_HOOK, Material.TRIPWIRE, Material.WALL_SIGN, Material.SIGN_POST,
	                                                                       Material.STANDING_BANNER, Material.WALL_BANNER, Material.GOLD_PLATE, Material.IRON_PLATE,
	                                                                       Material.STONE_PLATE, Material.WOOD_PLATE, Material.END_ROD, Material.DEAD_BUSH,
	                                                                       Material.WATER_LILY, Material.LONG_GRASS, Material.CAKE_BLOCK, Material.FIRE, Material.ANVIL,
	                                                                       Material.PUMPKIN_STEM, Material.MELON_STEM, Material.SAPLING, Material.SUGAR_CANE_BLOCK,
	                                                                       Material.CARROT, Material.POTATO, Material.SEEDS, Material.BEETROOT_BLOCK, Material.CROPS,
	                                                                       Material.REDSTONE_WIRE, Material.REDSTONE_COMPARATOR_OFF, Material.REDSTONE_COMPARATOR_ON,
	                                                                       Material.DIODE_BLOCK_ON, Material.DIODE_BLOCK_OFF, Material.VINE, Material.BROWN_MUSHROOM,
	                                                                       Material.RED_MUSHROOM, Material.DOUBLE_PLANT, Material.DRAGON_EGG, Material.STONE_BUTTON,
	                                                                       Material.WOOD_BUTTON, Material.LEVER, Material.RAILS, Material.ACTIVATOR_RAIL,
	                                                                       Material.DETECTOR_RAIL, Material.DETECTOR_RAIL, Material.POWERED_RAIL);
	
	private final static EnumSet<EntityType> whitelistedEntities = EnumSet.of(EntityType.ARMOR_STAND, EntityType.ITEM_FRAME, EntityType.PAINTING);
	
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
		dependentBlocks = new ArrayList<>();
		for (int x = smallest.getBlockX(); x <= biggest.getBlockX(); x++) {
			for (int y = smallest.getBlockY(); y <= biggest.getBlockY(); y++) {
				for (int z = smallest.getBlockZ(); z <= biggest.getBlockZ(); z++) {
					Block block = origin.getWorld().getBlockAt(x, y, z);
					if (dependentMaterials.contains(block.getType())) {
						dependentBlocks.add(new SerializableBlock(block, origin));
					} else {
						blocks.add(new SerializableBlock(block, origin));
					}
				}
			}
		}
		
		entities = new ArrayList<>();
		biggest.add(new Vector(1, 1, 1));
		for (Entity entity : origin.getWorld().getEntities()) {
			if (whitelistedEntities.contains(entity.getType())) {
				Location location = entity.getLocation();
				if (location.getX() >= smallest.getX() && location.getY() >= smallest.getY() && location.getZ() >= smallest.getZ() &&
						location.getX() <= biggest.getX() && location.getY() <= biggest.getY() && location.getZ() <= biggest.getZ()) {
					entities.add(new SerializableEntity(entity, origin));
				}
			}
		}
	}
	
	@SuppressWarnings ("unchecked")
	public Schematic(Map<String, Object> serialized) {
		blocks = ((List<Map<String, Object>>)serialized.get("blocks")).stream().map(SerializableBlock::new).collect(Collectors.toList());
		dependentBlocks = ((List<Map<String, Object>>)serialized.get("dependentBlocks")).stream().map(SerializableBlock::new).collect(Collectors.toList());
		entities = ((List<Map<String, Object>>)serialized.get("entities")).stream().map(SerializableEntity::new).collect(Collectors.toList());
	}
	
	private final List<SerializableBlock> blocks;
	private final List<SerializableBlock> dependentBlocks;
	private final List<SerializableEntity> entities;
	private BukkitTask pastingTask;
	
	
	
	public void paste(Location origin) { //At the moment, pasting air to the location of an air blocks takes one tick as well
		pasteOneByOne(origin, blocks, () -> pasteOneByOne(origin, dependentBlocks, () -> pasteOneByOne(origin, entities, () -> {})));
	}
	
	public Map<String, Object> serialize() {
		Map<String, Object> serialized = new HashMap<>();
		serialized.put("blocks", blocks.stream().map(SerializableBlock::serialize).collect(Collectors.toList()));
		serialized.put("dependentBlocks", dependentBlocks.stream().map(SerializableBlock::serialize).collect(Collectors.toList()));
		serialized.put("entities", entities.stream().map(SerializableEntity::serialize).collect(Collectors.toList()));
		return serialized;
	}
	
	
	
	//This method "destroys" the lists
	private void pasteOneByOne(Location origin, List<? extends Pastable> pastableList, Runnable whenReady) {
		pastingTask = Bukkit.getScheduler().runTaskTimer(Project.getPlugin(), () -> {
			if (pastableList.size() > 0) {
				pastableList.remove(0).paste(origin);
			} else {
				pastingTask.cancel();
				whenReady.run();
			}
		}, 0, 1);
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