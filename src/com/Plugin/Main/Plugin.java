package com.Plugin.Main;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Random;

import com.Plugin.Events.ChunkEvents;

public class Plugin extends JavaPlugin {
	
	public static Random Random = new Random();
	public static Plugin Plugin = null;
	public static Settings Settings = null;
	public static File LandRuins = null;
	
	public static ChunkEvents ChunkListener = new ChunkEvents();
	
	public void onEnable() {
		Plugin = this;
		saveDefaultConfig();
		Settings = new Settings(getConfig());
		LandRuins = new File(getDataFolder(), "/LandRuins");
		if (!LandRuins.exists()) {
			LandRuins.mkdir();
		}
		
		getServer().getPluginManager().registerEvents(ChunkListener, this);
	}
	
	public void onDisable() {
		
	}
	
}
