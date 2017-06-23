package com.cynigasm.projects.minecraft.schematics;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings ("deprecation")
public class SerializableEntity {
	public SerializableEntity(Entity entity, Location relative) {
		x = entity.getLocation().getX() - relative.getX();
		y = entity.getLocation().getY() - relative.getY();
		z = entity.getLocation().getZ() - relative.getZ();
		pitch = entity.getLocation().getPitch();
		yaw = entity.getLocation().getYaw();
		type = entity.getType();
		
		data = new HashMap<>();
		switch (entity.getType()) {
			case ARMOR_STAND:
				//all items
				//all poses
				break;
			case ITEM_FRAME:
				//item
				//rotation
				//facing direction
				break;
		}
	}
	
	@SuppressWarnings ("unchecked")
	public SerializableEntity(Map<String, Object> serialized) {
		x = (Double)serialized.get("x");
		y = (Double)serialized.get("y");
		z = (Double)serialized.get("z");
		pitch = (Float)serialized.get("pitch");
		yaw = (Float)serialized.get("yaw");
		type = EntityType.fromId((Short)serialized.get("type"));
		data = (Map<String, Object>)serialized.get("data");
	}
	
	private final double x;
	private final double y;
	private final double z;
	private final float pitch;
	private final float yaw;
	private final EntityType type;
	private final Map<String, Object> data;
	
	
	
	
	public void paste(Location origin) {
		Location clone = origin.clone().add(x, y, z);
		clone.setPitch(pitch);
		clone.setYaw(yaw);
		Entity entity = origin.getWorld().spawnEntity(clone, type);
		
		switch (type) {
			case ARMOR_STAND:
				break;
			case ITEM_FRAME:
				break;
		}
	}
	
	public Map<String, Object> serialize() {
		Map<String, Object> serialized = new HashMap<>();
		serialized.put("x", x);
		serialized.put("y", y);
		serialized.put("z", z);
		serialized.put("pitch", pitch);
		serialized.put("yaw", yaw);
		serialized.put("type", type);
		serialized.put("data", data);
		return serialized;
	}
}