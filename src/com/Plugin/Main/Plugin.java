package com.Plugin.Main;

import com.Plugin.Commands.ApocRPGCommand;
import com.Plugin.Events.ChunkEvents;
import com.Plugin.Events.CombatEvents;
import com.Plugin.Events.EffectPollingEvent;
import com.Plugin.Events.EntityEvents;
import com.Plugin.Events.PollingEventListener;
import com.Plugin.Events.SocketEvents;

import java.io.File;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {
	
	public static Random Random = new Random();
	public static Plugin Plugin = null;
	public static Settings Settings = null;
	public static File LandRuins = null;
	public static PollingEventListener PollListener = new PollingEventListener();
	public static ChunkEvents ChunkListener = new ChunkEvents();
	public static EntityEvents EntityListener = new EntityEvents();
	public static CombatEvents CombatListener = new CombatEvents();
	public static SocketEvents SocketListener = null;
	public void onEnable() {
		Plugin = this;
		SocketListener = new SocketEvents();
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				Player[] ps = Plugin.getServer().getOnlinePlayers();
				for(Player p: ps) {
					EffectPollingEvent event = new EffectPollingEvent(p);
					Plugin.getServer().getPluginManager().callEvent(event);
					//Plugin.getServer().broadcastMessage(event.getMessage());
				}}}, 0l, 600l);
			
		saveDefaultConfig();
		Settings = new Settings(getConfig());
		LandRuins = new File(getDataFolder(), "/LandRuins");
		if (!LandRuins.exists()) {
			LandRuins.mkdir();
		}
		
		getServer().getPluginManager().registerEvents(ChunkListener, this);
		getServer().getPluginManager().registerEvents(EntityListener, this);
		getServer().getPluginManager().registerEvents(CombatListener, this);
		getServer().getPluginManager().registerEvents(SocketListener, this);
		getServer().getPluginManager().registerEvents(PollListener, this);
		getCommand("apocrpg").setExecutor(new ApocRPGCommand());
	}

	public void onDisable() {
		
	}
	
}