package com.APOCRPG.Main;

import com.APOCRPG.Commands.ApocRPGCommand;
import com.APOCRPG.Events.ChunkEvents;
import com.APOCRPG.Events.CombatEvents;
import com.APOCRPG.Events.EffectPollingEvent;
import com.APOCRPG.Events.EntityEvents;
import com.APOCRPG.Events.PollingEventListener;
import com.APOCRPG.Events.SocketEvents;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.server.v1_7_R3.Material;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
	public static String DISPLAY_NAME_TOME = "Identify Tome";
	public static String LORE_ITEM_SOCKET = "(Socket)";
	public static String LORE_PLAYER_BOUND = ChatColor.WHITE+"Player Bound:";
	public static String LORE_REPAIRED = ChatColor.DARK_GRAY+"Repaired";
	public static String LORE_TOME = "Identify the Unknown";
	
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
	
	public static boolean containsLoreText(ItemMeta meta, String s) {
		boolean retval = false;
		if ( meta != null && meta.hasLore()){
			ArrayList<String> lore = (ArrayList<String>)meta.getLore();
			for (String l:lore){
				if ( l.startsWith(s) ) {
					return true;
				}
			}
		}
		return retval;
	}
	
	public static boolean containsLoreText(ItemStack item, String s) {
		boolean retval = false;
		if ( item != null && !item.getType().equals(Material.AIR) ) {
			ItemMeta meta = item.getItemMeta();
			retval = containsLoreText(meta, s);
		}
		return retval;
	}
	
	public static void addLoreText(ArrayList<String> lore, String s1, String s2 ){
		if ( s1 == null ) { return; } //{ return lore; }
		
		if ( lore == null ){ lore = new ArrayList<String>(); }
		
		lore.add((s1 == null ? "" : s1.trim() ) + (s2 != null && !s2.trim().equals("")? (" "+s2.trim()) : "" ));
		//return lore;
	}
	
	public static void addLoreText(ArrayList<String> lore, String s1){
		addLoreText( lore, s1, null);
	}
	
	public static ItemMeta addLoreText(ItemMeta meta, String s1, String s2){
		if ( meta != null && !containsLoreText(meta, s1)){
			ArrayList<String> lore = (ArrayList<String>)meta.getLore();
			addLoreText( lore, s1, s2);
			meta.setLore(lore);
		}
		return meta;
	}
	
	public static ItemMeta addLoreText(ItemMeta meta, String s1){
		return addLoreText( meta, s1, null );
	}
	
	public static void addLoreText(ItemStack item, String s1, String s2){
		if ( item != null && !item.getType().equals(Material.AIR) ) { 
			item.setItemMeta(addLoreText( item.getItemMeta(), s1, s2 ));
		}
	}
	
	public static void addLoreText(ItemStack item, String s1 ){
		addLoreText( item, s1, null );
	}
	
}