package com.cynigasm.projects.minecraft.schematics;

import com.cynigasm.projects.minecraft.utility.BukkitObjectBase64;
import org.bukkit.Art;
import org.bukkit.Location;
import org.bukkit.Rotation;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings ("deprecation")
public class SerializableEntity {
	public SerializableEntity(Entity entity, Location origin) {
		x = entity.getLocation().getX() - origin.getX();
		y = entity.getLocation().getY() - origin.getY();
		z = entity.getLocation().getZ() - origin.getZ();
		pitch = entity.getLocation().getPitch();
		yaw = entity.getLocation().getYaw();
		type = entity.getType();
		
		data = new HashMap<>();
		switch (entity.getType()) {
			case ARMOR_STAND:
				ArmorStand armorStand = (ArmorStand)entity;
				data.put("boots", BukkitObjectBase64.toBase64(armorStand.getBoots()));
				data.put("leggings", BukkitObjectBase64.toBase64(armorStand.getLeggings()));
				data.put("chestplate", BukkitObjectBase64.toBase64(armorStand.getChestplate()));
				data.put("helmet", BukkitObjectBase64.toBase64(armorStand.getHelmet()));
				break;
			case ITEM_FRAME:
				ItemFrame itemFrame = (ItemFrame)entity;
				data.put("item", BukkitObjectBase64.toBase64(itemFrame.getItem()));
				data.put("rotation", itemFrame.getRotation().name());
				data.put("facing", itemFrame.getFacing().name());
				break;
			case PAINTING:
				Painting painting = (Painting)entity;
				data.put("art", painting.getArt().getId());
				data.put("facing", painting.getFacing().name());
				break;
		}
	}
	
	@SuppressWarnings ("unchecked")
	public SerializableEntity(Map<String, Object> serialized) {
		x = (Double)serialized.get("x");
		y = (Double)serialized.get("y");
		z = (Double)serialized.get("z");
		pitch = ((Double)serialized.get("pitch")).floatValue();
		yaw = ((Double)serialized.get("yaw")).floatValue();
		type = EntityType.fromId((Integer)serialized.get("type"));
		data = (Map<String, Object>)serialized.get("data");
	}
	
	private final double x;
	private final double y;
	private final double z;
	private final float pitch;
	private final float yaw;
	private final EntityType type;
	private final Map<String, Object> data;
	
	
	
	public Entity paste(Location origin) {
		Entity entity;
		
		if (type == EntityType.ITEM_FRAME || type == EntityType.PAINTING) {
			Location clone = origin.clone().add(x, y, z).getBlock().getLocation();
			clone.setPitch(pitch);
			clone.setYaw(yaw);
			entity = origin.getWorld().spawnEntity(clone, type);
			
			switch (type) {
				case ITEM_FRAME:
					ItemFrame itemFrame = (ItemFrame)entity;
					itemFrame.setItem(BukkitObjectBase64.fromBase64((String)data.get("item")));
					itemFrame.setRotation(Rotation.valueOf((String)data.get("rotation")));
					itemFrame.setFacingDirection(BlockFace.valueOf((String)data.get("facing")), false);
					break;
				case PAINTING:
					Painting painting = ((Painting)entity);
					Art art = Art.getById((Integer)data.get("art"));
					BlockFace facing = BlockFace.valueOf((String)data.get("facing"));
					painting.teleport(calculatePaintingLocation(art, facing, clone));
					painting.setArt(art, false);
					painting.setFacingDirection(facing, false);
					break;
			}
		} else {
			Location clone = origin.clone().add(x, y, z);
			clone.setPitch(pitch);
			clone.setYaw(yaw);
			entity = origin.getWorld().spawnEntity(clone, type);
			
			switch (type) {
				case ARMOR_STAND:
					ArmorStand armorStand = (ArmorStand)entity;
					armorStand.setBoots(BukkitObjectBase64.fromBase64((String)data.get("boots")));
					armorStand.setLeggings(BukkitObjectBase64.fromBase64((String)data.get("leggings")));
					armorStand.setChestplate(BukkitObjectBase64.fromBase64((String)data.get("chestplate")));
					armorStand.setHelmet(BukkitObjectBase64.fromBase64((String)data.get("helmet")));
					break;
			}
		}
		
		return entity;
	}
	
	public Map<String, Object> serialize() {
		Map<String, Object> serialized = new HashMap<>();
		serialized.put("x", x);
		serialized.put("y", y);
		serialized.put("z", z);
		serialized.put("pitch", pitch);
		serialized.put("yaw", yaw);
		serialized.put("type", type.getTypeId());
		serialized.put("data", data);
		return serialized;
	}
	
	
	
	private Location calculatePaintingLocation(Art art, BlockFace facing, Location location) {
		switch(art) {
			case ALBAN: // 1x1
			case AZTEC:
			case AZTEC2:
			case BOMB:
			case KEBAB:
			case PLANT:
			case WASTELAND:
				return location; // No calculation needed.
			case GRAHAM: // 1x2
			case WANDERER:
				return location.add(0, -1, 0);
			case CREEBET: // 2x1
			case COURBET:
			case POOL:
			case SEA:
			case SUNSET:
			case DONKEYKONG: // 4x3
			case SKELETON:
				if (facing == BlockFace.WEST) {
					return location.add(0, 0, -1);
				} else if (facing == BlockFace.SOUTH) {
					return location.add(-1, 0, 0);
				} else {
					return location;
				}
			case BUST: // 2x2
			case MATCH:
			case SKULL_AND_ROSES:
			case STAGE:
			case VOID:
			case WITHER:
			case FIGHTERS: // 4x2
			case BURNINGSKULL: // 4x4
			case PIGSCENE:
			case POINTER:
				if (facing == BlockFace.WEST) {
					return location.add(0, -1, -1);
				} else if (facing == BlockFace.SOUTH) {
					return location.add(-1, -1, 0);
				} else {
					return location.add(0, -1, 0);
				}
			default:
				return location;
		}
	}
}