package com.cynigasm.projects.minecraft;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class oPlayer {
	
	private String prefix;
	private ArrayList<String> prefixes;
	private UUID id;
	private File playerfile;
	private YamlConfiguration configuration;
	private double balance;
	private int power;
	private Player player;
	private ArrayList<UUID> friends;
	private ArrayList<UUID> friend_requests;
	private ArrayList<UUID> blocklist;
	
	public oPlayer(UUID id) {
		this.id = id;
		this.prefix = Project.getPlugin().getConfig().getString("players.default_prefix");
		this.player = Bukkit.getPlayer(id);
		this.prefixes = new ArrayList<String>();
		this.playerfile = new File(Project.getPlugin().getDataFolder() + File.separator + "players" + File.separator + id.toString() + ".yml");
		this.configuration = YamlConfiguration.loadConfiguration(playerfile);
		this.balance = Project.getPlugin().getConfig().getDouble("players.default_bal");
		this.power = Project.getPlugin().getConfig().getInt("players.default_power");
		this.friends = new ArrayList<UUID>();
		this.friend_requests = new ArrayList<UUID>();
		this.blocklist = new ArrayList<UUID>();
		if(playerfile.exists()) {
			load();
		}
	}
	
	public void load() {
		this.prefix = configuration.getString("player.prefix");
		this.power = configuration.getInt("player.power");
		
		for(String prefix : configuration.getStringList("player.prefixes")) {
			if(!this.prefixes.contains(prefix)) {
				this.prefixes.add(prefix);
			}
		}
		
		for(String string_uuid : configuration.getStringList("player.friends")) {
			UUID uuid = UUID.fromString(string_uuid);
			if(!friends.contains(uuid)) {
				friends.add(uuid);
			}
		}
		
		for(String string_requests : configuration.getStringList("player.friend_requests")) {
			UUID uuid = UUID.fromString(string_requests);
			if(!friend_requests.contains(uuid)) {
				friend_requests.add(uuid);
			}
		}
		
		for(String string_blocked : configuration.getStringList("player.blocked")) {
			UUID uuid = UUID.fromString(string_blocked);
			if(!blocklist.contains(uuid)) {
				blocklist.add(uuid);
			}
		}
		
		this.balance = configuration.getDouble("player.balance");
	}
	
	public void save() {
		configuration.set("player.prefix", this.prefix);
		configuration.set("player.prefixes", this.prefixes);
		
		ArrayList<String> friends = new ArrayList<String>();
		for(UUID uuid : this.friends) {
			friends.add(uuid.toString());
		}
		configuration.set("player.friends", friends);
		ArrayList<String> friendrequests = new ArrayList<String>();
		for(UUID uuid : this.friend_requests) {
			friendrequests.add(uuid.toString());
		}
		configuration.set("player.friendrequests", friendrequests);
		ArrayList<String> blocked = new ArrayList<String>();
		for(UUID uuid : this.blocklist) {
			blocked.add(uuid.toString());
		}
		configuration.set("player.blocked", blocked);
		configuration.set("player.balance", this.balance);
		configuration.set("player.power", this.power);
		try {
			configuration.save(playerfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public ArrayList<String> getPrefixes() {
		return prefixes;
	}

	public void setPrefixes(ArrayList<String> prefixes) {
		this.prefixes = prefixes;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public File getPlayerfile() {
		return playerfile;
	}

	public void setPlayerfile(File playerfile) {
		this.playerfile = playerfile;
	}

	public YamlConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(YamlConfiguration configuration) {
		this.configuration = configuration;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double amoune) {
		this.balance = amoune;
	}
	
	public void addBalance(double amount) {
		this.balance = this.balance + amount;
	}
	
	public boolean hasBalance(double amount) {
		if(this.balance >= amount) {
			return true;
		} else {
			return false;
		}
	}
	
	public void removeBalance(double amount) {
		this.balance = this.balance - amount;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void addFriend(UUID uuid) {
		this.friends.add(uuid);
	}
	
	public boolean hasFriend(UUID uuid) {
		if(this.friends.contains(uuid)) {
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<UUID> getFriends() {
		return this.friends;
	}
	
	public int getOnlineFriends() {
		int i = 0;
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(this.friends.contains(p.getUniqueId())) {
				i= i+1;
			}
		}
		return i;
	}
	
	public void removeFriend(UUID uuid) {
		this.friends.remove(uuid);
	}
	
	public void addFriendRequest(UUID uuid) {
		this.friend_requests.add(uuid);
	}
	
	public ArrayList<UUID> getFriendRequests() {
		return this.friend_requests;
	}
	
	public boolean hasFriendRequest(UUID uuid) {
		if(this.friend_requests.contains(uuid)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void removeFriendRequest(UUID uuid) {
		if(hasFriendRequest(uuid)) {
			this.friend_requests.remove(uuid);
		}
	}
	
	public void acceptFriendRequest(UUID uuid) {
		if(this.hasFriendRequest(uuid)) {
			this.friend_requests.remove(uuid);
			this.friends.add(uuid);
		}
	}

	public ArrayList<UUID> getBlocklist() {
		return blocklist;
	}

	public void setBlocklist(ArrayList<UUID> blocklist) {
		this.blocklist = blocklist;
	}
	
	public void blockPlayer(UUID uuid) {
		this.blocklist.add(uuid);
	}
	
	public boolean hasBlockedPlayer(UUID uuid) {
		if(this.blocklist.contains(uuid)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void unblockPlayer(UUID uuid) {
		if(hasBlockedPlayer(uuid)) {
			this.blocklist.remove(uuid);
		}
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}
}