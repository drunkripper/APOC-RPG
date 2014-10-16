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
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
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
	// global constants - general
	public static String APOCRPG_ERROR = ChatColor.RED+"[APOC-RPG] ";
	public static String APOCRPG_ERROR_EMPTY_HAND = APOCRPG_ERROR+"You have nothing in your hand!";
	public static String APOCRPG_ERROR_NO_PERMISSION = APOCRPG_ERROR+"You do not have permission for that command!";
	public static String APOCRPG_ERROR_NO_MONEY = APOCRPG_ERROR+"Not enough money!";
	public static String APOCRPG_ERROR_SOCKET = APOCRPG_ERROR+"You can not socket that gem to this item!";
	public static String DISPLAY_NAME_TOME = ChatColor.MAGIC+"Identify Tome";
	public static String DISPLAY_NAME_UNIDENTIFIED_ITEM = "Unidentified Item";
	public static String LORE_GEM_OF = ChatColor.LIGHT_PURPLE+"Gem of ";
	public static String LORE_ITEM_SOCKET = ChatColor.LIGHT_PURPLE+"(Socket)";
	public static String LORE_PLAYER_BOUND = ChatColor.WHITE+"Player Bound:";
	public static String LORE_REPAIRED = ChatColor.DARK_GRAY+"Repaired";
	public static String LORE_TOME = "Identify the Unknown";
	public static String LORE_UNKNOWN_ITEM = "Unidentified Item";
	// global constants - config settings
	public static double COST_BUY_GEAR = 750;
	public static double COST_BUY_GEM = 500;
	public static double COST_BUY_LORE = 150;
	public static double COST_BUY_NAME = 100;
	public static double COST_BUY_TOME = 500;
	public static double COST_DISENCHANT = 0.25;
	public static double COST_ENCHANT_LVL_1  = 500;
	public static double COST_ENCHANT_LVL_2  = 750;
	public static double COST_ENCHANT_LVL_3  = 1000;
	public static double COST_ENCHANT_LVL_4  = 1250;
	public static double COST_ENCHANT_LVL_5  = 1500;
	public static double COST_ENCHANT_LVL_6  = 1750;
	public static double COST_ENCHANT_LVL_7  = 2000;
	public static double COST_ENCHANT_LVL_8  = 2250;
	public static double COST_ENCHANT_LVL_9  = 2500;
	public static double COST_ENCHANT_LVL_10 = 2750;
	public static double COST_REMOVE_GEM = 500;
	public static double COST_REMOVE_SOCKET = 1000;
	public static double COST_REPAIR = 1000;
	public static double COST_SALVAGE = 25;
	public static double COST_SOCKET_1 = 1500;
	public static double COST_SOCKET_2 = 3000;
	public static double COST_SOCKET_3 = 4500;
	public static Boolean DEBUG = false;
	public static double EXP_DISENCHANT = 5;
	public static String VERSION = "";
	
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
		loadConfig();
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
	
	public static boolean containsLoreText(List<String> lore, String s) {
		boolean retval = false;
		if ( lore != null && !lore.isEmpty()){
			for (String l:lore){
				if ( l.startsWith(s) ) {
					return true;
				}
			}
		}
		return retval;
	}
	
	public static boolean containsLoreText(ItemMeta meta, String s) {
		boolean retval = false;
		if ( meta != null && meta.hasLore() && !meta.getLore().isEmpty()){
			containsLoreText( meta.getLore(), s);
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
	
	
	public static List<String> getLoreContaining(ItemStack item, String s){
		List<String> retval = new ArrayList<String>();
		if ( containsLoreText(item, s)) {
			List<String> lore = (List<String>)item.getItemMeta().getLore();
			for ( String l:lore ){
				if (l.startsWith(s)){
					retval.add(l);
				}
			}
		}
		return retval;
	}
	
	public static List<String> addLoreText(List<String> lore, String s1, String s2 ){
		if ( s1 == null ) { 
			return lore;
		} 
		
		if ( lore == null || lore.isEmpty() ){ lore = new ArrayList<String>(); }
		
		lore.add((s1 == null ? "" : s1.trim() ) + (s2 != null && !s2.trim().equals("")? (" "+s2.trim()) : "" ));
		debugConsole("lore added: "+s1+" "+s2);
		return lore;
	}
	
	public static List<String> addLoreText(List<String> lore, String s1){
		return addLoreText( lore, s1, null);
	}
	
	public static ItemMeta addLoreText(ItemMeta meta, String s1, String s2){
		if ( meta != null && !containsLoreText(meta, s1)){
			List<String> lore = (List<String>)meta.getLore();
			meta.setLore(addLoreText( lore, s1, s2));
		} else {
			debugConsole("addLoreText: ItemMeta is null OR equals AIR");
		} 
		return meta;
	}
	
	public static ItemMeta addLoreText(ItemMeta meta, String s1){
		return addLoreText( meta, s1, null );
	}
	
	public static void addLoreText(ItemStack item, String s1, String s2){
		if ( item != null && !item.getType().equals(Material.AIR) ) { 
			item.setItemMeta(addLoreText( item.getItemMeta(), s1, s2 ));
		} else {
			debugConsole("addLoreText: ItemStack is null OR equals AIR");
		}
	}
	
	public static void addLoreText(ItemStack item, String s1 ){
		addLoreText( item, s1, null );
	}
	
	public static void clearLore( ItemStack item ){
		if ( item != null && !item.getType().equals(Material.AIR) ) { 
			ItemMeta meta = item.getItemMeta();
			if ( meta != null ){
				List<String> lore = new ArrayList<String>();
				lore.add("");
				meta.setLore(lore);
				item.setItemMeta(meta);
			}
		}
	}
	
	public static void loadConfig(){
		COST_BUY_GEAR = Settings.getDouble("Command-Settings.cost-for-gear");
		COST_BUY_GEM = Settings.getDouble("Command-Settings.cost-for-gem");
		COST_BUY_LORE = Settings.getDouble("Command-Settings.cost-for-lore");
		COST_BUY_NAME = Settings.getDouble("Command-Settings.cost-for-name");
		COST_BUY_TOME = Settings.getDouble("Command-Settings.cost-for-tome");
		COST_DISENCHANT = Settings.getDouble("Command-Settings.cost-to-disenchant");
		COST_ENCHANT_LVL_1 = Settings.getDouble("Command-Settings.cost-to-enchant-1");
		COST_ENCHANT_LVL_2 = Settings.getDouble("Command-Settings.cost-to-enchant-2");
		COST_ENCHANT_LVL_3 = Settings.getDouble("Command-Settings.cost-to-enchant-3");
		COST_ENCHANT_LVL_4 = Settings.getDouble("Command-Settings.cost-to-enchant-4");
		COST_ENCHANT_LVL_5 = Settings.getDouble("Command-Settings.cost-to-enchant-5");
		COST_ENCHANT_LVL_6 = Settings.getDouble("Command-Settings.cost-to-enchant-6");
		COST_ENCHANT_LVL_7 = Settings.getDouble("Command-Settings.cost-to-enchant-7");
		COST_ENCHANT_LVL_8 = Settings.getDouble("Command-Settings.cost-to-enchant-8");
		COST_ENCHANT_LVL_9 = Settings.getDouble("Command-Settings.cost-to-enchant-9");
		COST_ENCHANT_LVL_10= Settings.getDouble("Command-Settings.cost-to-enchant-10");
		COST_REMOVE_GEM = Settings.getDouble("Command-Settings.cost-to-degem");
		COST_REMOVE_SOCKET = Settings.getDouble("Command-Settings.cost-to-desocket");
		COST_REPAIR = Settings.getDouble("Command-Settings.cost-to-repair");
		COST_SALVAGE = Settings.getDouble("Command-Settings.cost-to-salvage");
		COST_SOCKET_1 = Settings.getDouble("Command-Settings.cost-to-socket-1");
		COST_SOCKET_2 = Settings.getDouble("Command-Settings.cost-to-socket-2");
		COST_SOCKET_3 = Settings.getDouble("Command-Settings.cost-to-socket-3");
		DEBUG =  Settings.getBoolean("Plugin.debug");
		EXP_DISENCHANT = Settings.getDouble("Command-Settings.disenchant-exp");
		VERSION = Settings.getString("Plugin.version");
	}
	
	public static void debugConsole(String s){
		if ( DEBUG ){
			System.out.println(s);
		}
	}
	
	public static void debugLog(String s ){
		if ( DEBUG ){
			// put debug logging here
		}
	}
	
	public static void debugPlayerMsg(CommandSender player, String msg){
		if ( DEBUG ) {
			player.sendMessage(msg);
		}
	}
	
	public static void debugPlayerMsg(Player player, String msg){
		if ( DEBUG ) {
			player.sendMessage(msg);
		}
	}
	
	public static int romanToInt ( String roman )  {
		int retval = -1;
		switch ( roman ){
			case "I" : retval = 1; break;
			case "II" : retval = 2; break;
			case "III" : retval = 3; break;
			case "IV" : retval = 4; break;
			case "V" : retval = 5; break;
			case "VI" : retval = 6; break;
			case "VII" : retval = 7; break;
			case "VIII" : retval = 8; break;
			case "IX" : retval = 9; break;
			case "X" : retval = 10; break;
		}
		return retval;
	}
	
	public static String intToRoman ( int nbr )  {
		String retval = "";
		switch ( nbr ){
			case 1: retval = "I"; break;
			case 2: retval = "II"; break;
			case 3: retval = "III"; break;
			case 4: retval = "IV"; break;
			case 5: retval = "V"; break;
			case 6: retval = "VI"; break;
			case 7: retval = "VII"; break;
			case 8: retval = "VIII"; break;
			case 9: retval = "IX"; break;
			case 10: retval = "X"; break;
		}
		return retval;
	}
	
}