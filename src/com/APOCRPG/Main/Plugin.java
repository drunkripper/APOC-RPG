package com.APOCRPG.Main;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.APOCRPG.Commands.ApocRPGCommand;
import com.APOCRPG.Events.ChunkEvents;
import com.APOCRPG.Events.CombatEvents;
import com.APOCRPG.Events.EffectPollingEvent;
import com.APOCRPG.Events.EntityEvents;
import com.APOCRPG.Events.PollingEventListener;
import com.APOCRPG.Events.ProjectileEvents;
import com.APOCRPG.Events.SocketEvents;
import com.APOCRPG.SkillPoints.DBApi;
import com.APOCRPG.SkillPoints.InSkill;
import com.APOCRPG.SkillPoints.SkillGet;

public class Plugin extends JavaPlugin {
	public static Random Random = new Random();
	public static Plugin instance = null;
	public static Settings Settings = null;
	public static File LandRuins = null;
	public static PollingEventListener PollListener = new PollingEventListener();
	public static ChunkEvents ChunkListener = new ChunkEvents();
	public static EntityEvents EntityListener = new EntityEvents();
	public static CombatEvents CombatListener = new CombatEvents();
	public static ProjectileEvents ProjectileListener = new ProjectileEvents();
	public static SkillGet SkillListener = new SkillGet();
	public static SocketEvents SocketListener = null;
	public static InSkill SpendSkillListener = new InSkill();
	// global constants - general
	public static String APOCRPG_ERROR = ChatColor.RED + "[APOC-RPG] ";
	public static String APOCRPG_ERROR_EMPTY_HAND = APOCRPG_ERROR + "You have nothing in your hand!";
	public static String APOCRPG_ERROR_NO_PERMISSION = APOCRPG_ERROR + "You do not have permission for that command!";
	public static String APOCRPG_ERROR_NO_MONEY = APOCRPG_ERROR + "Not enough money!";
	public static String APOCRPG_ERROR_SOCKET = APOCRPG_ERROR + "You can not socket that gem to this item!";
	public static String DISPLAY_NAME_GEM = ChatColor.GREEN + "Socket Gem";
	public static String DISPLAY_NAME_TOME = ChatColor.GREEN + "Tome of Identify";
	public static String DISPLAY_NAME_UNIDENTIFIED_ITEM = ChatColor.WHITE + "Unidentified Item";
	public static String LORE_GEM_OF = ChatColor.LIGHT_PURPLE + "Gem of ";
	public static String LORE_ITEM_SOCKET = ChatColor.LIGHT_PURPLE + "(Socket)";
	public static String LORE_PLAYER_BOUND = ChatColor.WHITE + "Player Bound:";
	public static String LORE_REPAIRED = ChatColor.DARK_GRAY + "Repaired";
	public static String LORE_TOME = "Identify the Unknown";
	public static String LORE_UNKNOWN_ITEM = "Unidentified Item";
	// global constants - dungeon chests
	public static boolean CHEST_FILL_RPG = true;
	public static boolean CHEST_LOCKABLE = true;
	public static int CHEST_MAX_RANDOM = 100;
	public static int CHEST_MIN_ITEMS = 1;
	public static int CHEST_MAX_ITEMS = 5;
	public static int CHEST_MAX_CHANCE_GEM = 10;
	public static int CHEST_MAX_CHANCE_TOME = 20;
	public static int CHEST_MAX_CHANCE_UNKNOWN = 35;
	public static int CHEST_MAX_CHANCE_TIER_LEGENDARY = 40;
	public static int CHEST_MAX_CHANCE_TIER_SET = 45;
	public static int CHEST_MAX_CHANCE_TIER_UNIQUE = 55;
	public static int CHEST_MAX_CHANCE_TIER_RARE = 70;
	public static int CHEST_MAX_CHANCE_TIER_UNCOMMON = 85;
	public static int CHEST_MAX_CHANCE_TIER_COMMON = 100;
	public static SortedMap<Integer, String> chestItems = new TreeMap<Integer, String>();

	// global constants - config settings
	public static double COST_BUY_GEAR = 750;
	public static double COST_BUY_GEM = 500;
	public static double COST_BUY_LORE = 150;
	public static double COST_BUY_NAME = 100;
	public static double COST_BUY_TOME = 500;
	public static double COST_DISENCHANT = 0.25;
	public static double COST_ENCHANT_LVL_1 = 500;
	public static double COST_ENCHANT_LVL_2 = 750;
	public static double COST_ENCHANT_LVL_3 = 1000;
	public static double COST_ENCHANT_LVL_4 = 1250;
	public static double COST_ENCHANT_LVL_5 = 1500;
	public static double COST_ENCHANT_LVL_6 = 1750;
	public static double COST_ENCHANT_LVL_7 = 2000;
	public static double COST_ENCHANT_LVL_8 = 2250;
	public static double COST_ENCHANT_LVL_9 = 2500;
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

	public static double GEAR_CHAIN_RATE = 0;
	public static double GEAR_GOLD_RATE = 0;
	public static double GEAR_IRON_RATE = 0;
	public static double GEAR_DIAMOND_RATE = 0;
	public static double GEAR_FORGE_RATE = 0;
	public static double GEAR_LEATHER_RATE = 0;
	public static boolean GEAR_NO_SALE_ON_REPAIR = true;
	public static int GEAR_SOCKETS_MAX_BUY = 3;
	public static double GEAR_STONE_RATE = 0;
	public static double GEAR_WOOD_RATE = 0;

	public static int TIER_COMMON = 0;
	public static boolean TIER_COMMON_ENCHANTS_ALLOW = false;
	public static int TIER_COMMON_ENCHANTS_MIN = 0;
	public static int TIER_COMMON_ENCHANTS_MAX = 0;
	public static int TIER_COMMON_ENCHANTS_MAX_LVL = 3;
	public static int TIER_COMMON_MAX_CHANCE = 100;
	public static String TIER_COMMON_NAMES_COLOR = "WHITE";
	public static boolean TIER_COMMON_NAMING = false;
	public static boolean TIER_COMMON_SOCKETS_ALLOW = false;
	public static int TIER_COMMON_SOCKETS_MAX = 1;

	public static int TIER_UNCOMMON = 1;
	public static boolean TIER_UNCOMMON_ENCHANTS_ALLOW = false;
	public static int TIER_UNCOMMON_ENCHANTS_MIN = 0;
	public static int TIER_UNCOMMON_ENCHANTS_MAX = 0;
	public static int TIER_UNCOMMON_ENCHANTS_MAX_LVL = 5;
	public static int TIER_UNCOMMON_MAX_CHANCE = 50;
	public static boolean TIER_UNCOMMON_SOCKETS_ALLOW = false;
	public static int TIER_UNCOMMON_SOCKETS_MAX = 1;
	public static boolean TIER_UNCOMMON_NAMING = false;
	public static String TIER_UNCOMMON_NAMES_COLOR = "BLUE";

	public static int TIER_RARE = 2;
	public static boolean TIER_RARE_ENCHANTS_ALLOW = false;
	public static int TIER_RARE_ENCHANTS_MIN = 0;
	public static int TIER_RARE_ENCHANTS_MAX = 0;
	public static int TIER_RARE_ENCHANTS_MAX_LVL = 6;
	public static int TIER_RARE_MAX_CHANCE = 25;
	public static boolean TIER_RARE_SOCKETS_ALLOW = false;
	public static int TIER_RARE_SOCKETS_MAX = 1;
	public static boolean TIER_RARE_NAMING = false;
	public static String TIER_RARE_NAMES_COLOR = "YELLOW";

	public static int TIER_UNIQUE = 3;
	public static boolean TIER_UNIQUE_ENCHANTS_ALLOW = false;
	public static int TIER_UNIQUE_ENCHANTS_MIN = 0;
	public static int TIER_UNIQUE_ENCHANTS_MAX = 0;
	public static int TIER_UNIQUE_ENCHANTS_MAX_LVL = 7;
	public static int TIER_UNIQUE_MAX_CHANCE = 15;
	public static boolean TIER_UNIQUE_SOCKETS_ALLOW = false;
	public static int TIER_UNIQUE_SOCKETS_MAX = 1;
	public static boolean TIER_UNIQUE_NAMING = false;
	public static String TIER_UNIQUE_NAMES_COLOR = "GOLD";

	public static int TIER_SET = 4;
	public static boolean TIER_SET_ENCHANTS_ALLOW = false;
	public static int TIER_SET_ENCHANTS_MIN = 0;
	public static int TIER_SET_ENCHANTS_MAX = 0;
	public static int TIER_SET_ENCHANTS_MAX_LVL = 7;
	public static int TIER_SET_MAX_CHANCE = 10;
	public static boolean TIER_SET_SOCKETS_ALLOW = false;
	public static int TIER_SET_SOCKETS_MAX = 1;
	public static boolean TIER_SET_NAMING = false;
	public static String TIER_SET_NAMES_COLOR = "GREEN";

	public static int TIER_LEGENDARY = 5;
	public static boolean TIER_LEGENDARY_ENCHANTS_ALLOW = false;
	public static int TIER_LEGENDARY_ENCHANTS_MIN = 0;
	public static int TIER_LEGENDARY_ENCHANTS_MAX = 0;
	public static int TIER_LEGENDARY_ENCHANTS_MAX_LVL = 10;
	public static int TIER_LEGENDARY_MAX_CHANCE = 5;
	public static boolean TIER_LEGENDARY_SOCKETS_ALLOW = false;
	public static int TIER_LEGENDARY_SOCKETS_MAX = 1;
	public static boolean TIER_LEGENDARY_NAMING = false;
	public static String TIER_LEGENDARY_NAMES_COLOR = "DARK_RED";

	public static Logger logger = null;

	/*
	 * boolean allowEnchant = false; int maxEnchants = 0; int minEnchants = 0;
	 * boolean allowSocket = false; int maxSockets = 0; int minSockets = 0;
	 * boolean usePrefix = false; boolean useSuffix = false; ChatColor nameColor
	 * = ChatColor.WHITE;
	 */

	public static boolean PERMISSION_BUY = false;
	public static boolean PERMISSION_BUY_ENCHANT = false;
	public static boolean PERMISSION_BUY_GEM = false;
	public static boolean PERMISSION_BUY_ITEM = false;
	public static boolean PERMISSION_BUY_NAME = false;
	public static boolean PERMISSION_BUY_SOCKET = false;
	public static boolean PERMISSION_BUY_TOME = false;
	public static boolean PERMISSION_BUY_UNKNOWN = false;
	public static boolean PERMISSION_DISENCHANT = false;
	public static boolean PERMISSION_DISENCHANT_ALL = false;
	public static boolean PERMISSION_OP_BYPASS = false;
	public static boolean PERMISSION_RELOAD = false;
	public static boolean PERMISSION_REMOVE_GEM = false;
	public static boolean PERMISSION_REMOVE_SOCKET = false;
	public static boolean PERMISSION_REPAIR = false;
	public static boolean PERMISSION_REPAIR_ALL = false;
	public static boolean PERMISSION_SALVAGE = false;
	public static boolean PERMISSION_SALVAGE_ALL = false;
	public static boolean PERMISSION_SELL = false;
	public static boolean PERMISSION_SELL_ALL = false;
	public static boolean PERMISSION_VERSION = false;

	public static boolean MOBS_ENABLED = true;
	public static boolean MOBS_SPAWN_COMMON = true;
	public static boolean MOBS_SPAWN_UNCOMMON = true;
	public static boolean MOBS_SPAWN_RARE = true;
	public static boolean MOBS_SPAWN_UNIQUE = true;
	public static boolean MOBS_SPAWN_SET = true;
	public static boolean MOBS_SPAWN_LEGENDARY = true;
	public static boolean MOBS_SPAWN_GEMS = true;
	public static boolean MOBS_SPAWN_TOMES = true;
	public static boolean MOBS_SPAWN_FORGE = true;
	public static boolean MOBS_SPAWN_WEAPON = true;
	public static boolean MOBS_ITEM_DROP_DEATH = true;
	public static int MOBS_ITEM_DROP_CHANCE = 10;
	public static int MOBS_ITEM_HELD_MAX = 4;
	public static int MOBS_ITEM_DROP_MAX = 2;
	public static boolean MOBS_SPAWN_ENCHANTED = true;
	public static int MOBS_SPAWN_MAX_ENCHANT_LEVEL = 10;
	public static double MOBS_BONUS_HP_PCT = 100;

	public void onEnable() {
		instance = this;
		logger = getLogger();
		debug("Starting APOC-RPG Plugin.onEnable()");

		SocketListener = new SocketEvents();
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				Collection<? extends Player> ps = instance.getServer().getOnlinePlayers();
				for (Player p : ps) {
					EffectPollingEvent event = new EffectPollingEvent(p);
					instance.getServer().getPluginManager().callEvent(event);
					// Plugin.getServer().broadcastMessage(event.getMessage());
				}
			}
		}, 0l, 600l);

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
		getServer().getPluginManager().registerEvents(ProjectileListener, this);
		getServer().getPluginManager().registerEvents(SkillListener, this);
		getServer().getPluginManager().registerEvents(SpendSkillListener, this);
		getCommand("apocrpg").setExecutor(new ApocRPGCommand());
		debug("Completing APOC-RPG Plugin.onEnable()");
		
		String databaseHost = getConfig().getString("server_ip");
    	int port = getConfig().getInt("server_port");
    	String username = getConfig().getString("server_user");
    	String password = getConfig().getString("server_password");
    	
        Connection conn;
        String url = "jdbc:mysql://" + databaseHost + ":" + port;
       
        //Attempt to connect
        try{
          //Connection succeeded
          conn = DriverManager.getConnection(url, username, password);
          ResultSet rs = conn.getMetaData().getTables(null, null, "Skill", null);
          if(!rs.next()) {
        	  String sql = "CREATE TABLE Skill " +
                      "(player_name VARCHAR(17) not NULL, " +
                      " skill_points INTEGER DEFAULT 0, "   + 
                      " xp_roof INTEGER DEFAULT 100, "      +  
                      " current_xp INTEGER DEFAULT 0,"      +
                      " recovery INTEGER DEFAULT 0,"        +
                      " evasion INTEGER DEFAULT 0,"         +
                      " agility INTEGER DEFAULT 0,"         +
                      " luck INTEGER DEFAULT 0,"            +
                      " armor INTEGER DEFAULT 0,"          +
                      " PRIMARY KEY ( player ))";
        	  DBApi.executeQuery(sql);
        	  sql = "CREATE TABLE Logs "                    +
                      "(player_name VARCHAR(17), "          +
                      " killed VARCHAR(100), "              +  
                      " time_killed TIMESTAMP, "            +
                      " PRIMARY KEY ( player ))";
        	  DBApi.executeQuery(sql);
        	  getLogger().info("Tables not found. Created!");
          } else {
        	  getLogger().info("Tables Found!");
          }
        } catch(Exception e) {
        	
        }
	}

	public void onDisable() {

	}

	/**
	 * This method will append a single line of text to the provided List
	 * <String> by concatenating the two provided Strings with a whitespace ' '
	 * between them if the second String is not null.
	 * 
	 * @param lore
	 *            - List<String> to have record appended
	 * @param s1
	 *            - String 1 to append to List with String 2
	 * @param s2
	 *            - String 2 to append to List with String 1
	 * @return List<String>
	 */
	public static List<String> addLoreText(List<String> lore, String s1, String s2) {
		if (s1 == null) {
			return lore;
		}

		if (lore == null || lore.isEmpty()) {
			lore = new ArrayList<String>();
		}

		lore.add((s1 == null ? "" : s1.trim()) + (s2 != null && !s2.trim().equals("") ? (" " + s2.trim()) : ""));
		return lore;
	}

	/**
	 * This method will append a single line of text to the provided List
	 * <String>.
	 * 
	 * @param lore
	 *            - List<String> to have record appended.
	 * @param s1
	 *            - String 1 to append to List.
	 * @return List<String>
	 */
	public static List<String> addLoreText(List<String> lore, String s1) {
		return addLoreText(lore, s1, null);
	}

	/**
	 * This method will append a single line of text to the provided ItemMeta's
	 * lore by concatenating the two provided Strings with a whitespace ' '
	 * between them if the second String is not null.
	 * 
	 * @param meta
	 *            - ItemMeta to have record appended to its lore
	 * @param s1
	 *            - String 1 to append to lore with String 2
	 * @param s2
	 *            - String 2 to append to lore with String 1
	 * @return ItemMeta
	 */
	public static ItemMeta addLoreText(ItemMeta meta, String s1, String s2) {
		if (meta != null && !containsLoreText(meta, s1)) {
			List<String> lore = (List<String>) meta.getLore();
			meta.setLore(addLoreText(lore, s1, s2));
		}
		return meta;
	}

	/**
	 * This method will append a single line of text to the provided ItemMeta's
	 * lore.
	 * 
	 * @param meta
	 *            - ItemMeta to have record appended to its lore
	 * @param s1
	 *            - String 1 to append to lore
	 * @return ItemMeta
	 */
	public static ItemMeta addLoreText(ItemMeta meta, String s1) {
		return addLoreText(meta, s1, null);
	}

	/**
	 * This method will append a single line of text to the provided ItemStack's
	 * lore by concatenating the two provided Strings with a whitespace ' '
	 * between them if the second String is not null.
	 * 
	 * @param meta
	 *            - ItemStack to have record appended to its lore
	 * @param s1
	 *            - String 1 to append to lore with String 2
	 * @param s2
	 *            - String 2 to append to lore with String 1
	 * @return ItemStack
	 */
	public static void addLoreText(ItemStack item, String s1, String s2) {
		if (item != null && !item.getType().equals(Material.AIR)) {
			item.setItemMeta(addLoreText(item.getItemMeta(), s1, s2));
		}
	}

	/**
	 * This method will append a single line of text to the provided ItemStack's
	 * lore.
	 * 
	 * @param meta
	 *            - ItemStack to have record appended to its lore
	 * @param s1
	 *            - String 1 to append to lore.
	 * @return ItemStack
	 */
	public static void addLoreText(ItemStack item, String s1) {
		addLoreText(item, s1, null);
	}

	/**
	 * This method will clear the ItemStack's existing lore.
	 * 
	 * @param item
	 */
	public static void clearLore(ItemStack item) {
		if (item != null && !item.getType().equals(Material.AIR)) {
			ItemMeta meta = item.getItemMeta();
			if (meta != null) {
				List<String> lore = new ArrayList<String>();
				lore.add("");
				meta.setLore(lore);
				item.setItemMeta(meta);
			}
		}
	}

	/**
	 * Check a lore List<String> for record starting with the provided parameter
	 * 
	 * @param lore
	 *            -- List<String> to be searched for particular String
	 * @param str
	 *            -- search String
	 * @return retval -- true/false
	 */
	public static boolean containsLoreText(List<String> lore, String str) {
		boolean retval = false;
		if (lore != null && !lore.isEmpty()) {
			for (String l : lore) {
				if (l.startsWith(str)) {
					return true;
				}
			}
		}
		return retval;
	}

	/**
	 * Check an ItemMeta's lore for record starting with the provided parameter
	 * 
	 * @param meta
	 *            -- ItemMeta to be searched for particular String
	 * @param str
	 *            -- search String
	 * @return retval -- true/false
	 */
	public static boolean containsLoreText(ItemMeta meta, String str) {
		boolean retval = false;
		if (meta != null && meta.hasLore() && !meta.getLore().isEmpty()) {
			retval = containsLoreText(meta.getLore(), str);
		}
		return retval;
	}

	/**
	 * Check a ItemStack's lore for record starting with the provided parameter
	 * 
	 * @param item
	 *            -- ItemStack to be searched for particular lore String
	 * @param str
	 *            -- search String
	 * @return retval -- true/false
	 */
	public static boolean containsLoreText(ItemStack item, String s) {
		boolean retval = false;
		if (item != null && !item.getType().equals(Material.AIR)) {
			ItemMeta meta = item.getItemMeta();
			retval = containsLoreText(meta, s);
		}
		return retval;
	}

	/**
	 * Send a message to the server console if debugging is allowed
	 * 
	 * @param s
	 *            - String message to be sent to console
	 */
	public static void debugConsole(String s) {
		if (DEBUG) {
			System.out.println(s);
		}
	}

	/**
	 * Send a message to the plugin log if debugging is allowed
	 * 
	 * @param s
	 *            - String message to be sent to the log
	 */
	public static void debug(String s) {
		if (DEBUG) {
			logger.info("[DEBUG] " + s);
		} else {
		}
	}

	/**
	 * Send a message to the player if debugging is allowed
	 * 
	 * @param player
	 *            - (CommandSender) player to be sent message
	 * @param s
	 *            - String message to be sent to the player
	 */
	public static void debugPlayerMsg(CommandSender player, String msg) {
		if (DEBUG) {
			player.sendMessage(msg);
		}
	}

	/**
	 * Send a message to the player if debugging is allowed
	 * 
	 * @param player
	 *            - (Player) player to be sent message
	 * @param s
	 *            - String message to be sent to the player
	 */
	public static void debugPlayerMsg(Player player, String msg) {
		if (DEBUG) {
			player.sendMessage(msg);
		}
	}

	/**
	 * This method will return a List of available Enchanments for this
	 * ItemStack.
	 * 
	 * @param item
	 * @return
	 */
	public static List<Enchantment> getEnchantmentsFor(ItemStack item) {
		List<Enchantment> retval = new ArrayList<Enchantment>();
		Enchantment[] allEnchants = Enchantment.values();
		for (Enchantment e : allEnchants) {
			if (e.canEnchantItem(item)) {
				retval.add(e);
			}
		}

		return retval;
	}

	/**
	 * Return a List<String> of an ItemStack's lore records that start with the
	 * provided search String.
	 * 
	 * @param item
	 *            -- ItemStack to be searched for particular lore String
	 * @param str
	 *            -- search String
	 * @return List<String> all Strings starting with the search String.
	 */
	public static List<String> getLoreContaining(ItemStack item, String s) {
		List<String> retval = new ArrayList<String>();
		if (hasLore(item)) {
			List<String> lore = (List<String>) item.getItemMeta().getLore();
			for (String l : lore) {
				if (l.startsWith(s)) {
					retval.add(l);
				}
			}
		}
		return retval;
	}

	/**
	 * This method returns true if the provided ItemStack contains lore.
	 * 
	 * @param item
	 * @return
	 */
	public static boolean hasLore(ItemStack item) {
		boolean retval = false;
		if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
			retval = true;
		}
		return retval;
	}

	/**
	 * This method will return true if the player has the permission to issue
	 * the command. This will check agains
	 * 
	 * @param player
	 * @param args
	 * @return
	 */
	public static boolean hasPermission(Player player, String[] args) {
		boolean retval = false;
		/*
		 * if ( PERMISSION_OP_BYPASS && isOp ){ debugConsole(
		 * "op bypassing permissions"); return true; }
		 */
		String arg1 = (args.length > 0 ? args[0] : null);
		String arg2 = (args.length > 1 ? args[1] : null);

		String command = arg1.toLowerCase() + (arg2 == null ? "" : "-" + arg2.toLowerCase());
		String permission = "apocrpg." + command;
		switch (command) {
		case "buy":
			retval = (PERMISSION_BUY || player.hasPermission(permission));
			break;
		case "buy-enchant":
			retval = (PERMISSION_BUY_ENCHANT || player.hasPermission(permission));
			break;
		case "buy-gem":
			retval = (PERMISSION_BUY_GEM || player.hasPermission(permission));
			break;
		case "buy-item":
			retval = (PERMISSION_BUY_ITEM || player.hasPermission(permission));
			break;
		case "buy-name":
			retval = (PERMISSION_BUY_NAME || player.hasPermission(permission));
			break;
		case "buy-socket":
			retval = (PERMISSION_BUY_SOCKET || player.hasPermission(permission));
			break;
		case "buy-tome":
			retval = (PERMISSION_BUY_TOME || player.hasPermission(permission));
			break;
		case "buy-unknown":
			retval = (PERMISSION_BUY_UNKNOWN || player.hasPermission(permission));
			break;
		case "disenchant":
			retval = (PERMISSION_DISENCHANT || player.hasPermission(permission));
			break;
		case "disenchant-all":
			retval = (PERMISSION_DISENCHANT_ALL || player.hasPermission(permission));
			break;
		case "reload":
			retval = (PERMISSION_RELOAD || player.hasPermission(permission));
			break;
		case "remove-gem":
			retval = (PERMISSION_REMOVE_GEM || player.hasPermission(permission));
			break;
		case "remove-socket":
			retval = (PERMISSION_REMOVE_SOCKET || player.hasPermission(permission));
			break;
		case "repair":
			retval = (PERMISSION_REPAIR || player.hasPermission(permission));
			break;
		case "repair-all":
			retval = (PERMISSION_REPAIR_ALL || player.hasPermission(permission));
			break;
		case "sell":
			retval = (PERMISSION_SELL || player.hasPermission(permission));
			break;
		case "sell-all":
			retval = (PERMISSION_SELL_ALL || player.hasPermission(permission));
			break;
		case "salvage":
			retval = (PERMISSION_SALVAGE || player.hasPermission(permission));
			break;
		case "salvage-all":
			retval = (PERMISSION_SALVAGE_ALL || player.hasPermission(permission));
			break;
		case "spawn":
			retval = player.hasPermission(permission);
			break;
		case "spawn-item":
			retval = player.hasPermission(permission);
			break;
		case "spawn-chest":
			retval = player.hasPermission(permission);
			break;
		case "version":
			retval = (PERMISSION_VERSION || player.hasPermission(permission));
			break;
		}
		return retval;
	}

	/**
	 * This method will set internal variables to stored configuration values.
	 */
	public static void loadConfig() {

		CHEST_FILL_RPG = Settings.getBoolean("Dungeons.chest-fill-rpg");
		CHEST_LOCKABLE = Settings.getBoolean("Dungeons.chest-lockable");
		CHEST_MAX_RANDOM = Settings.getInt("Dungeons.chest-max-random");
		CHEST_MIN_ITEMS = Settings.getInt("Dungeons.chest-min-items");
		CHEST_MAX_ITEMS = Settings.getInt("Dungeons.chest-max-items");
		CHEST_MAX_CHANCE_GEM = Settings.getInt("Dungeons.chest-max-chance-gem");
		CHEST_MAX_CHANCE_TOME = Settings.getInt("Dungeons.chest-max-chance-tome");
		CHEST_MAX_CHANCE_UNKNOWN = Settings.getInt("Dungeons.chest-max-chance-unknown");
		CHEST_MAX_CHANCE_TIER_LEGENDARY = Settings.getInt("Dungeons.chest-max-chance-tier-legendary");
		CHEST_MAX_CHANCE_TIER_SET = Settings.getInt("Dungeons.chest-max-chance-tier-set");
		CHEST_MAX_CHANCE_TIER_UNIQUE = Settings.getInt("Dungeons.chest-max-chance-tier-unique");
		CHEST_MAX_CHANCE_TIER_RARE = Settings.getInt("Dungeons.chest-max-chance-tier-rare");
		CHEST_MAX_CHANCE_TIER_UNCOMMON = Settings.getInt("Dungeons.chest-max-chance-tier-uncommon");
		CHEST_MAX_CHANCE_TIER_COMMON = Settings.getInt("Dungeons.chest-max-chance-tier-common");

		chestItems = new TreeMap<Integer, String>();
		if (CHEST_MAX_CHANCE_GEM > 0) {
			chestItems.put(Integer.valueOf(Plugin.CHEST_MAX_CHANCE_GEM), "GEM");
		}
		if (CHEST_MAX_CHANCE_TOME > 0) {
			chestItems.put(Integer.valueOf(Plugin.CHEST_MAX_CHANCE_TOME), "TOME");
		}
		if (CHEST_MAX_CHANCE_UNKNOWN > 0) {
			chestItems.put(Integer.valueOf(Plugin.CHEST_MAX_CHANCE_UNKNOWN), "UNKNOWN");
		}
		if (CHEST_MAX_CHANCE_TIER_COMMON > 0) {
			chestItems.put(Integer.valueOf(Plugin.CHEST_MAX_CHANCE_TIER_COMMON), "COMMON");
		}
		if (CHEST_MAX_CHANCE_TIER_UNCOMMON > 0) {
			chestItems.put(Integer.valueOf(Plugin.CHEST_MAX_CHANCE_TIER_UNCOMMON), "UNCOMMON");
		}
		if (CHEST_MAX_CHANCE_TIER_RARE > 0) {
			chestItems.put(Integer.valueOf(Plugin.CHEST_MAX_CHANCE_TIER_RARE), "RARE");
		}
		if (CHEST_MAX_CHANCE_TIER_UNIQUE > 0) {
			chestItems.put(Integer.valueOf(Plugin.CHEST_MAX_CHANCE_TIER_UNIQUE), "UNIQUE");
		}
		if (CHEST_MAX_CHANCE_TIER_SET > 0) {
			chestItems.put(Integer.valueOf(Plugin.CHEST_MAX_CHANCE_TIER_SET), "SET");
		}
		if (CHEST_MAX_CHANCE_TIER_LEGENDARY > 0) {
			chestItems.put(Integer.valueOf(Plugin.CHEST_MAX_CHANCE_TIER_LEGENDARY), "LEGENDARY");
		}

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
		COST_ENCHANT_LVL_10 = Settings.getDouble("Command-Settings.cost-to-enchant-10");
		COST_REMOVE_GEM = Settings.getDouble("Command-Settings.cost-to-degem");
		COST_REMOVE_SOCKET = Settings.getDouble("Command-Settings.cost-to-desocket");
		COST_REPAIR = Settings.getDouble("Command-Settings.cost-to-repair");
		COST_SALVAGE = Settings.getDouble("Command-Settings.cost-to-salvage");
		COST_SOCKET_1 = Settings.getDouble("Command-Settings.cost-to-socket-1");
		COST_SOCKET_2 = Settings.getDouble("Command-Settings.cost-to-socket-2");
		COST_SOCKET_3 = Settings.getDouble("Command-Settings.cost-to-socket-3");

		DEBUG = Settings.getBoolean("Plugin.debug");

		EXP_DISENCHANT = Settings.getDouble("Command-Settings.disenchant-exp");

		GEAR_SOCKETS_MAX_BUY = Settings.getInt("Tiers.sockets-max-buy");
		GEAR_NO_SALE_ON_REPAIR = Settings.getBoolean("Tiers.no-sell-on-repair");
		GEAR_LEATHER_RATE = Settings.getDouble("Command-Settings.leather-rate");
		GEAR_WOOD_RATE = Settings.getDouble("Command-Settings.wood-rate");
		GEAR_STONE_RATE = Settings.getDouble("Command-Settings.stone-rate");
		GEAR_GOLD_RATE = Settings.getDouble("Command-Settings.gold-rate");
		GEAR_CHAIN_RATE = Settings.getDouble("Command-Settings.chain-rate");
		GEAR_IRON_RATE = Settings.getDouble("Command-Settings.iron-rate");
		GEAR_DIAMOND_RATE = Settings.getDouble("Command-Settings.diamond-rate");
		GEAR_FORGE_RATE = Settings.getDouble("Command-Settings.forge-rate");

		TIER_COMMON_ENCHANTS_ALLOW = Settings.getBoolean("Tiers.enchant-allow-common");
		TIER_COMMON_ENCHANTS_MIN = Settings.getInt("Tiers.enchant-min-common");
		TIER_COMMON_ENCHANTS_MAX = Settings.getInt("Tiers.enchant-max-common");
		TIER_COMMON_ENCHANTS_MAX_LVL = Settings.getInt("Tiers.enchant-max-level-common");
		TIER_COMMON_SOCKETS_ALLOW = Settings.getBoolean("Tiers.allow-sockets-common");
		TIER_COMMON_MAX_CHANCE = Settings.getInt("Tiers.max-chance-common");
		TIER_COMMON_SOCKETS_MAX = Settings.getInt("Tiers.sockets-max-common");
		TIER_COMMON_NAMING = Settings.getBoolean("Tiers.name-random-common");
		TIER_COMMON_NAMES_COLOR = Settings.getString("Tiers.name-color-common");

		TIER_UNCOMMON_ENCHANTS_ALLOW = Settings.getBoolean("Tiers.enchant-allow-uncommon");
		TIER_UNCOMMON_ENCHANTS_MIN = Settings.getInt("Tiers.enchant-min-uncommon");
		TIER_UNCOMMON_ENCHANTS_MAX = Settings.getInt("Tiers.enchant-max-uncommon");
		TIER_UNCOMMON_ENCHANTS_MAX_LVL = Settings.getInt("Tiers.enchant-max-level-uncommon");
		TIER_UNCOMMON_MAX_CHANCE = Settings.getInt("Tiers.max-chance-uncommon");
		TIER_UNCOMMON_SOCKETS_ALLOW = Settings.getBoolean("Tiers.allow-sockets-uncommon");
		TIER_UNCOMMON_SOCKETS_MAX = Settings.getInt("Tiers.sockets-max-uncommon");
		TIER_UNCOMMON_NAMING = Settings.getBoolean("Tiers.name-random-uncommon");
		TIER_UNCOMMON_NAMES_COLOR = Settings.getString("Tiers.name-color-uncommon");

		TIER_RARE_ENCHANTS_ALLOW = Settings.getBoolean("Tiers.enchant-allow-rare");
		TIER_RARE_ENCHANTS_MIN = Settings.getInt("Tiers.enchant-min-rare");
		TIER_RARE_ENCHANTS_MAX = Settings.getInt("Tiers.enchant-max-rare");
		TIER_RARE_ENCHANTS_MAX_LVL = Settings.getInt("Tiers.enchant-max-level-rare");
		TIER_RARE_MAX_CHANCE = Settings.getInt("Tiers.max-chance-rare");
		TIER_RARE_SOCKETS_ALLOW = Settings.getBoolean("Tiers.allow-sockets-rare");
		TIER_RARE_SOCKETS_MAX = Settings.getInt("Tiers.sockets-max-rare");
		TIER_RARE_NAMING = Settings.getBoolean("Tiers.name-random-rare");
		TIER_RARE_NAMES_COLOR = Settings.getString("Tiers.name-color-rare");

		TIER_UNIQUE_ENCHANTS_ALLOW = Settings.getBoolean("Tiers.enchant-allow-unique");
		TIER_UNIQUE_ENCHANTS_MIN = Settings.getInt("Tiers.enchant-min-unique");
		TIER_UNIQUE_ENCHANTS_MAX = Settings.getInt("Tiers.enchant-max-unique");
		TIER_UNIQUE_ENCHANTS_MAX_LVL = Settings.getInt("Tiers.enchant-max-level-unique");
		TIER_UNIQUE_MAX_CHANCE = Settings.getInt("Tiers.max-chance-unique");
		TIER_UNIQUE_SOCKETS_ALLOW = Settings.getBoolean("Tiers.allow-sockets-unique");
		TIER_UNIQUE_SOCKETS_MAX = Settings.getInt("Tiers.sockets-max-unique");
		TIER_UNIQUE_NAMING = Settings.getBoolean("Tiers.name-random-unique");
		TIER_UNIQUE_NAMES_COLOR = Settings.getString("Tiers.name-color-unique");

		TIER_SET_ENCHANTS_ALLOW = Settings.getBoolean("Tiers.enchant-allow-set");
		TIER_SET_ENCHANTS_MIN = Settings.getInt("Tiers.enchant-min-set");
		TIER_SET_ENCHANTS_MAX = Settings.getInt("Tiers.enchant-max-set");
		TIER_SET_ENCHANTS_MAX_LVL = Settings.getInt("Tiers.enchant-max-level-set");
		TIER_SET_MAX_CHANCE = Settings.getInt("Tiers.max-chance-set");
		TIER_SET_SOCKETS_ALLOW = Settings.getBoolean("Tiers.allow-sockets-set");
		TIER_SET_SOCKETS_MAX = Settings.getInt("Tiers.sockets-max-set");
		TIER_SET_NAMING = Settings.getBoolean("Tiers.name-random-set");
		TIER_SET_NAMES_COLOR = Settings.getString("Tiers.name-color-set");

		TIER_LEGENDARY_ENCHANTS_ALLOW = Settings.getBoolean("Tiers.enchant-allow-legendary");
		TIER_LEGENDARY_ENCHANTS_MIN = Settings.getInt("Tiers.enchant-min-legendary");
		TIER_LEGENDARY_ENCHANTS_MAX = Settings.getInt("Tiers.enchant-max-legendary");
		TIER_LEGENDARY_ENCHANTS_MAX_LVL = Settings.getInt("Tiers.enchant-max-level-legendary");
		TIER_LEGENDARY_MAX_CHANCE = Settings.getInt("Tiers.max-chance-legendary");
		TIER_LEGENDARY_SOCKETS_ALLOW = Settings.getBoolean("Tiers.allow-sockets-legendary");
		TIER_LEGENDARY_SOCKETS_MAX = Settings.getInt("Tiers.sockets-max-legendary");
		TIER_LEGENDARY_NAMING = Settings.getBoolean("Tiers.name-random-legendary");
		TIER_LEGENDARY_NAMES_COLOR = Settings.getString("Tiers.name-color-legendary");

		PERMISSION_BUY = Settings.getBoolean("Permissions.buy");
		PERMISSION_BUY_ENCHANT = Settings.getBoolean("Permissions.buy-enchant");
		PERMISSION_BUY_GEM = Settings.getBoolean("Permissions.buy-gem");
		PERMISSION_BUY_ITEM = Settings.getBoolean("Permissions.buy-item");
		PERMISSION_BUY_NAME = Settings.getBoolean("Permissions.buy-name");
		PERMISSION_BUY_SOCKET = Settings.getBoolean("Permissions.buy-socket");
		PERMISSION_BUY_TOME = Settings.getBoolean("Permissions.buy-tome");
		PERMISSION_BUY_UNKNOWN = Settings.getBoolean("Permissions.buy-unknown");
		PERMISSION_DISENCHANT = Settings.getBoolean("Permissions.disenchant");
		PERMISSION_DISENCHANT_ALL = Settings.getBoolean("Permissions.disenchant-all");
		PERMISSION_OP_BYPASS = Settings.getBoolean("Plugin.op-bypass-permissions");
		PERMISSION_REMOVE_GEM = Settings.getBoolean("Permissions.remove-gem");
		PERMISSION_REMOVE_SOCKET = Settings.getBoolean("Permissions.remove-socket");
		PERMISSION_RELOAD = Settings.getBoolean("Permissions.reload");
		PERMISSION_REPAIR = Settings.getBoolean("Permissions.repair");
		PERMISSION_REPAIR_ALL = Settings.getBoolean("Permissions.repair-all");
		PERMISSION_SALVAGE = Settings.getBoolean("Permissions.salvage");
		PERMISSION_SALVAGE_ALL = Settings.getBoolean("Permissions.salvage-all");
		PERMISSION_SELL = Settings.getBoolean("Permissions.sell");
		PERMISSION_SELL_ALL = Settings.getBoolean("Permissions.sell-all");
		PERMISSION_VERSION = Settings.getBoolean("Permissions.version");
		VERSION = Settings.getString("Plugin.version");

		MOBS_ENABLED = Settings.getBoolean("Mobs.enabled");
		MOBS_SPAWN_COMMON = Settings.getBoolean("Mobs.spawn-common");
		MOBS_SPAWN_UNCOMMON = Settings.getBoolean("Mobs.spawn-uncommond");
		MOBS_SPAWN_RARE = Settings.getBoolean("Mobs.spawn-rare");
		MOBS_SPAWN_UNIQUE = Settings.getBoolean("Mobs.spawn-unique");
		MOBS_SPAWN_SET = Settings.getBoolean("Mobs.spawn-set");
		MOBS_SPAWN_LEGENDARY = Settings.getBoolean("Mobs.spawn-legendary");
		MOBS_SPAWN_GEMS = Settings.getBoolean("Mobs.spawn-gems");
		MOBS_SPAWN_TOMES = Settings.getBoolean("Mobs.spawn-tomes");
		MOBS_SPAWN_FORGE = Settings.getBoolean("Mobs.spawn-forge");
		MOBS_SPAWN_WEAPON = Settings.getBoolean("Mobs.spawn-weapon");
		MOBS_ITEM_DROP_DEATH = Settings.getBoolean("Mobs.item-drop-death");
		MOBS_ITEM_DROP_CHANCE = Settings.getInt("Mobs.item-drop-chance");
		MOBS_ITEM_HELD_MAX = Settings.getInt("Mobs.item-held-max");
		MOBS_ITEM_DROP_MAX = Settings.getInt("Mobs.item-drop-max");
		MOBS_SPAWN_ENCHANTED = Settings.getBoolean("Mobs.spawn-enchanted");
		MOBS_SPAWN_MAX_ENCHANT_LEVEL = Settings.getInt("Mobs.spawn-max-enchant-level");
		MOBS_BONUS_HP_PCT = Settings.getDouble("Mobs.bonus-hp-pct");
	}

	/**
	 * This method will convert a String roman numeral to an int.
	 * 
	 * @param roman
	 *            - Roman numeral to be converted to int.
	 * @return int
	 */
	public static int romanToInt(String roman) {
		int retval = -1;
		switch (roman.toUpperCase()) {
		case "I":
			retval = 1;
			break;
		case "II":
			retval = 2;
			break;
		case "III":
			retval = 3;
			break;
		case "IV":
			retval = 4;
			break;
		case "V":
			retval = 5;
			break;
		case "VI":
			retval = 6;
			break;
		case "VII":
			retval = 7;
			break;
		case "VIII":
			retval = 8;
			break;
		case "IX":
			retval = 9;
			break;
		case "X":
			retval = 10;
			break;

		case "XI":
			retval = 11;
			break;
		case "XII":
			retval = 12;
			break;
		case "XIII":
			retval = 13;
			break;
		case "XIV":
			retval = 14;
			break;
		case "XV":
			retval = 15;
			break;
		case "XVI":
			retval = 16;
			break;
		case "XVII":
			retval = 17;
			break;
		case "XVIII":
			retval = 18;
			break;
		case "XIX":
			retval = 19;
			break;
		case "XX":
			retval = 20;
			break;

		case "XXI":
			retval = 21;
			break;
		case "XXII":
			retval = 22;
			break;
		case "XXIII":
			retval = 23;
			break;
		case "XXIV":
			retval = 24;
			break;
		case "XXV":
			retval = 25;
			break;
		case "XXVI":
			retval = 26;
			break;
		case "XXVII":
			retval = 27;
			break;
		case "XXVIII":
			retval = 28;
			break;
		case "XXIX":
			retval = 29;
			break;
		case "XXX":
			retval = 30;
			break;

		case "XXXI":
			retval = 31;
			break;
		case "XXXII":
			retval = 32;
			break;
		case "XXXIII":
			retval = 33;
			break;
		case "XXXIV":
			retval = 34;
			break;
		case "XXXV":
			retval = 35;
			break;
		case "XXXVI":
			retval = 36;
			break;
		case "XXXVII":
			retval = 37;
			break;
		case "XXXVIII":
			retval = 38;
			break;
		case "XXXIX":
			retval = 39;
			break;
		case "XL":
			retval = 40;
			break;

		case "XLI":
			retval = 41;
			break;
		case "XLII":
			retval = 42;
			break;
		case "XLIII":
			retval = 43;
			break;
		case "XLIV":
			retval = 44;
			break;
		case "XLV":
			retval = 45;
			break;
		case "XLVI":
			retval = 46;
			break;
		case "XLVII":
			retval = 47;
			break;
		case "XLVIII":
			retval = 48;
			break;
		case "XLIX":
			retval = 49;
			break;
		case "L":
			retval = 50;
			break;

		case "LI":
			retval = 51;
			break;
		case "LII":
			retval = 52;
			break;
		case "LIII":
			retval = 53;
			break;
		case "LIV":
			retval = 54;
			break;
		case "LV":
			retval = 55;
			break;
		case "LVI":
			retval = 56;
			break;
		case "LVII":
			retval = 57;
			break;
		case "LVIII":
			retval = 58;
			break;
		case "LIX":
			retval = 59;
			break;
		case "LX":
			retval = 60;
			break;

		case "LXI":
			retval = 61;
			break;
		case "LXII":
			retval = 62;
			break;
		case "LXIII":
			retval = 63;
			break;
		case "LXIV":
			retval = 64;
			break;
		case "LXV":
			retval = 65;
			break;
		case "LXVI":
			retval = 66;
			break;
		case "LXVII":
			retval = 67;
			break;
		case "LXVIII":
			retval = 68;
			break;
		case "LXIX":
			retval = 69;
			break;
		case "LXX":
			retval = 70;
			break;

		case "LXXI":
			retval = 71;
			break;
		case "LXXII":
			retval = 72;
			break;
		case "LXXIII":
			retval = 73;
			break;
		case "LXXIV":
			retval = 74;
			break;
		case "LXXV":
			retval = 75;
			break;
		case "LXXVI":
			retval = 76;
			break;
		case "LXXVII":
			retval = 77;
			break;
		case "LXXVIII":
			retval = 78;
			break;
		case "LXXIX":
			retval = 79;
			break;
		case "LXXX":
			retval = 80;
			break;

		case "LXXXI":
			retval = 81;
			break;
		case "LXXXII":
			retval = 82;
			break;
		case "LXXXIII":
			retval = 83;
			break;
		case "LXXXIV":
			retval = 84;
			break;
		case "LXXXV":
			retval = 85;
			break;
		case "LXXXVI":
			retval = 86;
			break;
		case "LXXXVII":
			retval = 87;
			break;
		case "LXXXVIII":
			retval = 88;
			break;
		case "LXXXIX":
			retval = 89;
			break;
		case "XC":
			retval = 90;
			break;

		case "XCI":
			retval = 91;
			break;
		case "XCII":
			retval = 92;
			break;
		case "XCIII":
			retval = 93;
			break;
		case "XCIV":
			retval = 94;
			break;
		case "XCV":
			retval = 95;
			break;
		case "XCVI":
			retval = 96;
			break;
		case "XCVII":
			retval = 97;
			break;
		case "XCVIII":
			retval = 98;
			break;
		case "XCIX":
			retval = 99;
			break;
		case "C":
			retval = 100;
			break;

		}
		return retval;
	}

	/**
	 * This method will convert an int to a String roman numeral.
	 * 
	 * @param roman
	 *            - int to be converted to roman numeral.
	 * @return String roman numeral
	 */
	public static String intToRoman(int nbr) {
		String retval = "";
		switch (nbr) {
		case 1:
			retval = "I";
			break;
		case 2:
			retval = "II";
			break;
		case 3:
			retval = "III";
			break;
		case 4:
			retval = "IV";
			break;
		case 5:
			retval = "V";
			break;
		case 6:
			retval = "VI";
			break;
		case 7:
			retval = "VII";
			break;
		case 8:
			retval = "VIII";
			break;
		case 9:
			retval = "IX";
			break;
		case 10:
			retval = "X";
			break;

		case 11:
			retval = "XI";
			break;
		case 12:
			retval = "XII";
			break;
		case 13:
			retval = "XIII";
			break;
		case 14:
			retval = "XIV";
			break;
		case 15:
			retval = "XV";
			break;
		case 16:
			retval = "XVI";
			break;
		case 17:
			retval = "XVII";
			break;
		case 18:
			retval = "XVIII";
			break;
		case 19:
			retval = "XIX";
			break;
		case 20:
			retval = "XX";
			break;

		case 21:
			retval = "XXI";
			break;
		case 22:
			retval = "XXII";
			break;
		case 23:
			retval = "XXIII";
			break;
		case 24:
			retval = "XXIV";
			break;
		case 25:
			retval = "XXV";
			break;
		case 26:
			retval = "XXVI";
			break;
		case 27:
			retval = "XXVII";
			break;
		case 28:
			retval = "XXVIII";
			break;
		case 29:
			retval = "XXIX";
			break;
		case 30:
			retval = "XXX";
			break;

		case 31:
			retval = "XXXI";
			break;
		case 32:
			retval = "XXXII";
			break;
		case 33:
			retval = "XXXIII";
			break;
		case 34:
			retval = "XXXIV";
			break;
		case 35:
			retval = "XXXV";
			break;
		case 36:
			retval = "XXXVI";
			break;
		case 37:
			retval = "XXXVII";
			break;
		case 38:
			retval = "XXXVIII";
			break;
		case 39:
			retval = "XXXIX";
			break;
		case 40:
			retval = "XL";
			break;

		case 41:
			retval = "XLI";
			break;
		case 42:
			retval = "XLII";
			break;
		case 43:
			retval = "XLIII";
			break;
		case 44:
			retval = "XLIV";
			break;
		case 45:
			retval = "XLV";
			break;
		case 46:
			retval = "XLVI";
			break;
		case 47:
			retval = "XLVII";
			break;
		case 48:
			retval = "XLVIII";
			break;
		case 49:
			retval = "XLIX";
			break;
		case 50:
			retval = "L";
			break;

		case 51:
			retval = "LI";
			break;
		case 52:
			retval = "LII";
			break;
		case 53:
			retval = "LIII";
			break;
		case 54:
			retval = "LIV";
			break;
		case 55:
			retval = "LV";
			break;
		case 56:
			retval = "LVI";
			break;
		case 57:
			retval = "LVII";
			break;
		case 58:
			retval = "LVIII";
			break;
		case 59:
			retval = "LIX";
			break;
		case 60:
			retval = "LX";
			break;

		case 61:
			retval = "LXI";
			break;
		case 62:
			retval = "LXII";
			break;
		case 63:
			retval = "LXIII";
			break;
		case 64:
			retval = "LXIV";
			break;
		case 65:
			retval = "LXV";
			break;
		case 66:
			retval = "LXVI";
			break;
		case 67:
			retval = "LXVII";
			break;
		case 68:
			retval = "LXVIII";
			break;
		case 69:
			retval = "LXIX";
			break;
		case 70:
			retval = "LXX";
			break;

		case 71:
			retval = "LXXI";
			break;
		case 72:
			retval = "LXXII";
			break;
		case 73:
			retval = "LXXIII";
			break;
		case 74:
			retval = "LXXIV";
			break;
		case 75:
			retval = "LXXV";
			break;
		case 76:
			retval = "LXXVI";
			break;
		case 77:
			retval = "LXXVII";
			break;
		case 78:
			retval = "LXXVIII";
			break;
		case 79:
			retval = "LXXIX";
			break;
		case 80:
			retval = "LXXX";
			break;

		case 81:
			retval = "LXXXI";
			break;
		case 82:
			retval = "LXXXII";
			break;
		case 83:
			retval = "LXXXIII";
			break;
		case 84:
			retval = "LXXXIV";
			break;
		case 85:
			retval = "LXXXV";
			break;
		case 86:
			retval = "LXXXVI";
			break;
		case 87:
			retval = "LXXXVII";
			break;
		case 88:
			retval = "LXXXVIII";
			break;
		case 89:
			retval = "LXXXIX";
			break;
		case 90:
			retval = "XC";
			break;

		case 91:
			retval = "XCI";
			break;
		case 92:
			retval = "XCII";
			break;
		case 93:
			retval = "XCIII";
			break;
		case 94:
			retval = "XCIV";
			break;
		case 95:
			retval = "XCV";
			break;
		case 96:
			retval = "XCVI";
			break;
		case 97:
			retval = "XCVII";
			break;
		case 98:
			retval = "XCVIII";
			break;
		case 99:
			retval = "XCIX";
			break;
		case 100:
			retval = "C";
			break;

		}
		return retval;
	}

}