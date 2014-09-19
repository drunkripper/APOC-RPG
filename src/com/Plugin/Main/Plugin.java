package com.Plugin.Main;

import com.Plugin.Events.ChunkEvents;
import com.Plugin.Events.EntityEvents;

import java.io.File;
import java.util.Random;

import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {
	
	public static Random Random = new Random();
	public static Plugin Plugin = null;
	public static Settings Settings = null;
	public static File LandRuins = null;
	
	public static ChunkEvents ChunkListener = new ChunkEvents();
	public static EntityEvents EntityListener = new EntityEvents();

	public void onEnable() {
		Plugin = this;
		saveDefaultConfig();
		Settings = new Settings(getConfig());
		LandRuins = new File(getDataFolder(), "/LandRuins");
		if (!LandRuins.exists()) {
			LandRuins.mkdir();
		}
		
		getServer().getPluginManager().registerEvents(ChunkListener, this);
		getServer().getPluginManager().registerEvents(EntityListener, this);
	}

	public void onDisable() {
		
	}
	
}