package com.Plugin.Main;

import org.bukkit.configuration.file.FileConfiguration;

public class Settings {
	
	private FileConfiguration Config = null;
	
	public Settings(FileConfiguration Config) {
		this.Config = Config;
	}
	
	public boolean getBoolean(String key) {
		return Config.getBoolean(key);
	}
	
	public int getInt(String key) {
		return Config.getInt(key);
	}
	
}
