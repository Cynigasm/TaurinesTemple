package com.cynigasm.projects.minecraft.empire;

import com.cynigasm.projects.minecraft.Project;
import com.cynigasm.projects.minecraft.oPlayer;
import com.cynigasm.projects.minecraft.utility.YMLConfig;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Empire {
	public Empire(String name, UUID leader) {
		config = new YMLConfig(Project.getPlugin(), "empires" + File.separator + name + ".yml");
		this.name = name;
		level = 1;
		this.leader = leader;
		members = new HashSet<>();
		gold = 0;
		allies = new HashSet<>();
		enemies = new HashSet<>();
	}
	
	private final YMLConfig config;
	private final String name;
	private int level;
	private UUID leader;
	private final Set<UUID> members;
	private double gold;
	private final Set<String> allies;
	private final Set<String> enemies;
	
	
	
	
	public String getName() {
		return name;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void increaseLevel() {
		level++;
	}
	
	
	
	public UUID getLeader() {
		return leader;
	}
	
	public boolean isLeader(UUID leader) {
		return this.leader.equals(leader);
	}
	
	public boolean makeLeader(UUID member) {
		if (members.remove(member)) {
			members.add(leader);
			leader = member;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isMember(UUID memberOrLeader) {
		return members.contains(memberOrLeader) || isLeader(memberOrLeader);
	}
	
	public boolean addMember(UUID member) {
		return members.add(member);
	}
	
	public boolean removeMember(UUID member) {
		return members.remove(member);
	}
	
	public int getMemberCount() {
		return members.size() + 1;
	}
	
	
	
	public double getGold() {
		return gold;
	}
	
	public void setGold(double amount) {
		gold = amount;
	}
	
	public boolean hasGold(double amount) {
		return gold >= amount;
	}
	
	public boolean depositGold(oPlayer player, double amount) {
		if (player.hasBalance(amount)) {
			player.removeBalance(amount);
			gold += amount;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean withdrawGold(oPlayer player, double amount) {
		if (hasGold(amount)) {
			gold -= amount;
			player.addBalance(amount);
			return true;
		} else {
			return false;
		}
	}
	
	
	
	public boolean isAlly(String empire) {
		return allies.contains(empire);
	}
	
	public boolean isEnemy(String empire) {
		return enemies.contains(empire);
	}
	
	public boolean addAlly(String empire) {
		return allies.add(empire);
	}
	
	public boolean addEnemy(String empire) {
		return enemies.add(empire);
	}
	
	public boolean removeAlly(String empire) {
		return allies.remove(empire);
	}
	
	public boolean removeEnemy(String empire) {
		return enemies.remove(empire);
	}
	
	
	
	public void save () {
		config.set("name", name);
		config.set("level", level);
		config.set("leader", leader);
		config.setUUIDSet("members", members);
		config.set("gold", gold);
		config.set("allies", allies);
		config.set("enemies", enemies);
		config.save();
	}
	
	public Empire(String name) {
		config = new YMLConfig(Project.getPlugin(), "empires" + File.separator + name + ".yml");
		config.load();
		this.name = config.getString("name");
		level = config.getInt("level");
		leader = config.getUUID("leader");
		members = config.getUUIDSet("members");
		gold = config.getDouble("gold");
		allies = config.getStringSet("allies");
		enemies = config.getStringSet("enemies");
	}
}