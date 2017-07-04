package com.cynigasm.projects.minecraft.empire;

import com.cynigasm.projects.minecraft.Project;
import com.cynigasm.projects.minecraft.oPlayer;
import com.cynigasm.projects.minecraft.utility.MessageUtils;
import com.cynigasm.projects.minecraft.utility.YMLConfig;
import com.cynigasm.projects.minecraft.utility.randomUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Empire {
	public Empire(String name, UUID owner) {
		File file = new File(Project.getPlugin().getDataFolder() + File.separator + "empires" + File.separator + name + File.separator + "empire.yml");
		file.getParentFile().mkdirs();
		config = new YMLConfig(file);
		this.name = name;
		level = 1;
		this.owner = owner;
		admin = null;
		members = new HashSet<>();
		members.add(owner);
		gold = 0;
		allies = new HashSet<>();
		enemies = new HashSet<>();
	}
	
	public Empire(String name) {
		config = new YMLConfig(Project.getPlugin(), "empires" + File.separator + name + File.separator + "empire.yml");
		config.load();
		this.name = config.getString("name");
		level = config.getInt("level");
		owner = config.getUUID("owner");
		admin = config.getUUID("admin");
		members = config.getUUIDSet("members");
		gold = config.getDouble("gold");
		allies = config.getStringSet("allies");
		enemies = config.getStringSet("enemies");
	}
	
	private final YMLConfig config;
	private final String name;
	private int level;
	private UUID owner;
	private UUID admin;
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
	
	public boolean increaseLevel() {
		if (level < 10) { //This check is probably not needed
			level++;
			return true;
		} else {
			return false;
		}
	}
	
	
	
	public UUID getOwner() {
		return owner;
	}
	
	public boolean isOwner(UUID player) {
		return this.owner.equals(player);
	}
	
	public boolean makeOwner(UUID member) {
		if (isMember(member) && !isOwner(member)) {
			owner = member;
			return true;
		} else {
			return false;
		}
	}
	
	
	
	public UUID getAdmin() {
		return admin;
	}
	
	public boolean isAdmin(UUID player) {
		return this.admin != null && this.admin.equals(player);
	}
	
	public boolean makeAdmin(UUID member) {
		if (isMember(member) && !isAdmin(member) && !isOwner(member)) {
			admin = member;
			return true;
		} else {
			return false;
		}
	}
	
	
	
	public Set<UUID> getMembers() {
		return new HashSet<>(members);
	}
	
	public boolean isMember(UUID player) {
		return members.contains(player);
	}
	
	public boolean addMember(UUID player) {
		if (getMemberCount() < getMaxMemberCount() && EmpireHandler.getEmpire(player) == null) {
			if (members.add(player)) {
				broadcastToMembers(randomUtils.getPlayerName(Bukkit.getOfflinePlayer(player), "") + "&e has joined the clan!");
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean removeMember(UUID member) {
		if (members.remove(member)) {
			broadcastToMembers(randomUtils.getPlayerName(Bukkit.getOfflinePlayer(member), "") + "&c has left the clan!");
			randomUtils.NotifyPlayer("&cYou have left your clan!", Bukkit.getOfflinePlayer(member));
			return true;
		} else {
			return false;
		}
	}
	
	public int getMemberCount() {
		return members.size();
	}
	
	public int getMaxMemberCount() {
		return level * 5;
	}
	
	public void broadcastToMembers(String message) {
		message = MessageUtils.format(message);
		for (UUID id : members) {
			OfflinePlayer player = Bukkit.getOfflinePlayer(id);
			if (player.isOnline()) {
				player.getPlayer().sendMessage(message);
			}
		}
	}
	
	public String getRank(UUID member, boolean colored) {
		if (isMember(member)) {
			if (isOwner(member)) {
				return colored ? ChatColor.RED + "Owner" : "Owner";
			} else if (isAdmin(member)) {
				return colored ? ChatColor.YELLOW + "Admin" : "Admin";
			} else {
				return colored ? ChatColor.BLUE + "Member" : "Member";
			}
		} else {
			return colored ? ChatColor.GRAY + "Outsider" : "Outsider";
		}
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
	
	
	
	public Set<String> getAllies() {
		return new HashSet<>(allies);
	}
	
	public Set<String> getEnemies() {
		return new HashSet<>(enemies);
	}
	
	public boolean isAlly(String empire) {
		return allies.contains(empire);
	}
	
	public boolean isEnemy(String empire) {
		return enemies.contains(empire);
	}
	
	public boolean addAlly(Empire empire) {
		if (allies.add(empire.getName())) {
			empire.addAlly(name);
			broadcastToMembers("&2Your clan is now the ally of " + empire.getName());
			return true;
		} else {
			return false;
		}
	}
	
	private void addAlly(String empire) {
		allies.add(empire);
		broadcastToMembers("&2Your clan is now the ally of " + empire);
	}
	
	public boolean addEnemy(Empire empire) {
		if (enemies.add(empire.getName())) {
			empire.addEnemy(name);
			broadcastToMembers("&cYour clan is now the enemy of " + empire.getName());
			return true;
		} else {
			return false;
		}
	}
	
	private void addEnemy(String empire) {
		enemies.add(empire);
		broadcastToMembers("&cYour clan is now the enemy of " + empire);
	}
	
	public boolean removeAlly(Empire empire) {
		if (allies.remove(empire.getName())) {
			empire.removeAlly(name);
			broadcastToMembers("&cYour clan is no longer the ally of " + empire.getName());
			return true;
		} else {
			return false;
		}
	}
	
	private void removeAlly(String empire) {
		allies.remove(empire);
		broadcastToMembers("&cYour clan is no longer the ally of " + empire);
	}
	
	public boolean removeEnemy(Empire empire) {
		if (enemies.remove(empire.getName())) {
			empire.removeEnemy(name);
			broadcastToMembers("&2Your clan is no longer the enemy of " + empire.getName());
			return true;
		} else {
			return false;
		}
	}
	
	private void removeEnemy(String empire) {
		enemies.remove(empire);
		broadcastToMembers("&2Your clan is no longer the enemy of " + empire);
	}
	
	
	
	public void disband() {
		//members.clear();
		for (String name : allies) {
			EmpireHandler.getEmpire(name).removeAlly(name);
		}
		for (String name : enemies) {
			EmpireHandler.getEmpire(name).removeEnemy(name);
		}
	}
	
	
	
	public void save () {
		config.set("name", name);
		config.set("level", level);
		config.set("owner", owner);
		config.set("admin", admin);
		config.setUUIDSet("members", members);
		config.set("gold", gold);
		config.set("allies", allies);
		config.set("enemies", enemies);
		config.save();
	}
}