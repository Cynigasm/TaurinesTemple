package com.cynigasm.projects.minecraft.schematics;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings ("deprecation")
public class SerializableBlock {
	public SerializableBlock(Block block, Location origin) {
		x = block.getX() - origin.getBlockX();
		y = block.getY() - origin.getBlockY();
		z = block.getZ() - origin.getBlockZ();
		material = block.getType();
		data = block.getData();
	}
	
	public SerializableBlock(Map<String, Object> serialized) {
		x = (Integer)serialized.get("x");
		y = (Integer)serialized.get("y");
		z = (Integer)serialized.get("z");
		material = Material.getMaterial((Integer)serialized.get("material"));
		data = (Byte)serialized.get("data");
	}
	
	private final int x;
	private final int y;
	private final int z;
	private final Material material;
	private final byte data;
	
	
	
	public void paste(Location relative) {
		Block block = relative.clone().add(x, y, z).getBlock();
		block.setType(material, false);
		block.setData(data, false);
	}
	
	public Map<String, Object> serialize() {
		Map<String, Object> serialized = new HashMap<>();
		serialized.put("x", x);
		serialized.put("y", y);
		serialized.put("z", z);
		serialized.put("material", material.getId());
		serialized.put("data", data);
		return serialized;
	}
}