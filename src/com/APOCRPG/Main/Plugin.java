package com.APOCRPG.Main;

import com.APOCRPG.API.Database;
import com.APOCRPG.Commands.ApocRPGCommand;
import com.APOCRPG.Entities.APlayer;
import com.APOCRPG.Enums.Files;
import com.APOCRPG.Enums.Folders;
import com.APOCRPG.Events.*;
import com.APOCRPG.SkillPoints.DBApi;
import com.APOCRPG.SkillPoints.InSkill;
import com.APOCRPG.SkillPoints.SkillGet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.*;
import java.util.logging.Logger;

public class Plugin extends JavaPlugin {

	//General
	public static Random Random = new Random();
	public static Plugin instance = null;
	public static Logger logger = null;

	//Config
	private static boolean DEBUG;

	//Listeners
	public static PollingEventListener PollingEventListener = new PollingEventListener();
	public static ChunkEventsListener ChunkEventsListener = new ChunkEventsListener();
	public static PlayerEventsListener PlayerEventsListener = new PlayerEventsListener();
	public static CombatEventsListener CombatEventsListener = new CombatEventsListener();
	public static ProjectileEventsListener ProjectileEventsListener = new ProjectileEventsListener();
	public static SocketEventsListener SocketEventsListener = new SocketEventsListener();

	//Deprecated Listeners
	public static SkillGet SkillListener = new SkillGet();
	public static InSkill SpendSkillListener = new InSkill();

	//Lists, arrays, hash-maps
	public static Map<APlayer, Integer> playersInCombat = new HashMap<>();
	public static SortedMap<Integer, String> chestItems = new TreeMap<>();
	public static List<Location> dungeonChestLocations;


	public void onEnable() {

		instance = this;
		logger = getLogger();
		DEBUG = Settings.Cfg.DEBUG.getBoolean();

		debug("Starting APOC-RPG");

		debug("Initializing database checks"); //TODO
		Database db = new Database();
		db.initSetup();

		//Runnable to remove players from combat list that have exceeded their 30sec timer
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				for (APlayer p : playersInCombat.keySet()) {
					if (playersInCombat.get(p) <= 1) {
						playersInCombat.remove(p);
					} else if (playersInCombat.get(p) > 1) {
						playersInCombat.put(p, playersInCombat.get(p)-1);
					}
				}
			}
		}, 0L, 20L); //Runs forever every second (20 ticks)

		//Custom polling event
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				Player[] ps = instance.getServer().getOnlinePlayers();
				for (Player p : ps) {
					EffectPollingEvent event = new EffectPollingEvent(p);
					instance.getServer().getPluginManager().callEvent(event);
					// Plugin.getServer().broadcastMessage(event.getMessage());
				}
			}
		}, 0L, 600L);

		debug("Creating the default config");
		saveDefaultConfig();

		debug("Checking for and creating folders");
		//Folder hierarchy checks
		for(Folders f : Folders.values()) {
			if (!f.getValue().exists()) f.getValue().mkdirs();
			if (DEBUG) debug("Created a new folder: " + f.getValue().toString());
		}

		debug("Checking for and creating needed dafault configs");
		for(Files cfg : Files.values()) {
			if (!cfg.getValue().exists()) cfg.getValue().mkdirs();
			if (DEBUG) debug("Created a new config file: " + cfg.getValue().toString());
		}

		//Reading all the dungeon chests's location from the file
		//If there's different chest types, we'll start using custom block and material classes
		FileConfiguration dungeonChests = YamlConfiguration.loadConfiguration(Files.DungeonChestLocations.getValue());
		List<String> tempLocations = dungeonChests.getStringList("Locations");
		for (int x = 0; x <= tempLocations.size(); x++) {
			String[] arg = tempLocations.get(x).split(","); //<-- TODO: need to confirm that if it works
			double[] parsed = new double[3];
			for (int a = 0; a < 3; a++) {
				parsed[a] = Double.parseDouble(arg[a+1]);
			}

			Location location = new Location (Bukkit.getWorld(arg[0]), parsed[0], parsed[1], parsed[2]);
			dungeonChestLocations.add(location);
		}

		//Registering all the event listeners
		getServer().getPluginManager().registerEvents(ChunkEventsListener, this);
		getServer().getPluginManager().registerEvents(PlayerEventsListener, this);
		getServer().getPluginManager().registerEvents(CombatEventsListener, this);
		getServer().getPluginManager().registerEvents(SocketEventsListener, this);
		getServer().getPluginManager().registerEvents(PollingEventListener, this);
		getServer().getPluginManager().registerEvents(ProjectileEventsListener, this);
		getServer().getPluginManager().registerEvents(SkillListener, this);
		getServer().getPluginManager().registerEvents(SpendSkillListener, this);
		//getCommand("apocrpg").setExecutor(new CommandManager());
		getCommand("apocrpg").setExecutor(new ApocRPGCommand());
		
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
		debug("Finished setting up APOC-RPG");
		debug("Cheers");
	}

	public void onDisable() {

		//Saving dungeon chests locations to file
		FileConfiguration dungeonChests = YamlConfiguration.loadConfiguration(Files.DungeonChestLocations.getValue());
		dungeonChests.set("Locations", null);
		dungeonChests.set("Locations", dungeonChestLocations); //TODO: In theory it should work

	}

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

	public static List<String> addLoreText(List<String> lore, String s1) {
		return addLoreText(lore, s1, null);
	}

	public static ItemMeta addLoreText(ItemMeta meta, String s1, String s2) {
		if (meta != null && !containsLoreText(meta, s1)) {
			List<String> lore = (List<String>) meta.getLore();
			meta.setLore(addLoreText(lore, s1, s2));
		}
		return meta;
	}

	public static ItemMeta addLoreText(ItemMeta meta, String s1) {
		return addLoreText(meta, s1, null);
	}

	public static void addLoreText(ItemStack item, String s1, String s2) {
		if (item != null && !item.getType().equals(Material.AIR)) {
			item.setItemMeta(addLoreText(item.getItemMeta(), s1, s2));
		}
	}

	public static void addLoreText(ItemStack item, String s1) {
		addLoreText(item, s1, null);
	}

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

	public static boolean containsLoreText(ItemMeta meta, String str) {
		boolean retval = false;
		if (meta != null && meta.hasLore() && !meta.getLore().isEmpty()) {
			retval = containsLoreText(meta.getLore(), str);
		}
		return retval;
	}

	public static boolean containsLoreText(ItemStack item, String s) {
		boolean retval = false;
		if (item != null && !item.getType().equals(Material.AIR)) {
			ItemMeta meta = item.getItemMeta();
			retval = containsLoreText(meta, s);
		}
		return retval;
	}

	public static void debugConsole(String s) {
		if (DEBUG) {
			System.out.println(s);
		}
	}

	public static void debug(String s) {
		if (DEBUG) {
			logger.info("[DEBUG] " + s);
		} else {
		}
	}

	public static void debug(CommandSender player, String msg) {
		if (DEBUG) {
			player.sendMessage(msg);
		}
	}

	public static void debug(Player player, String msg) {
		if (DEBUG) {
			player.sendMessage(msg);
		}
	}

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

	public static boolean hasLore(ItemStack item) {
		boolean retval = false;
		if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
			retval = true;
		}
		return retval;
	}

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
			retval = (Settings.Cfg.PERMISSION_BUY.getBoolean() || player.hasPermission(permission));
			break;
		case "buy-enchant":
			retval = (Settings.Cfg.PERMISSION_BUY_ENCHANT.getBoolean() || player.hasPermission(permission));
			break;
		case "buy-gem":
			retval = (Settings.Cfg.PERMISSION_BUY_GEM.getBoolean() || player.hasPermission(permission));
			break;
		case "buy-item":
			retval = (Settings.Cfg.PERMISSION_BUY_ITEM.getBoolean() || player.hasPermission(permission));
			break;
		case "buy-name":
			retval = (Settings.Cfg.PERMISSION_BUY_NAME.getBoolean() || player.hasPermission(permission));
			break;
		case "buy-socket":
			retval = (Settings.Cfg.PERMISSION_BUY_SOCKET.getBoolean() || player.hasPermission(permission));
			break;
		case "buy-tome":
			retval = (Settings.Cfg.PERMISSION_BUY_TOME.getBoolean() || player.hasPermission(permission));
			break;
		case "buy-unknown":
			retval = (Settings.Cfg.PERMISSION_BUY_UNKNOWN.getBoolean() || player.hasPermission(permission));
			break;
		case "disenchant":
			retval = (Settings.Cfg.PERMISSION_DISENCHANT.getBoolean()|| player.hasPermission(permission));
			break;
		case "disenchant-all":
			retval = (Settings.Cfg.PERMISSION_DISENCHANT_ALL.getBoolean() || player.hasPermission(permission));
			break;
		case "reload":
			retval = (Settings.Cfg.PERMISSION_RELOAD.getBoolean() || player.hasPermission(permission));
			break;
		case "remove-gem":
			retval = (Settings.Cfg.PERMISSION_REMOVE_GEM.getBoolean() || player.hasPermission(permission));
			break;
		case "remove-socket":
			retval = (Settings.Cfg.PERMISSION_REMOVE_SOCKET.getBoolean() || player.hasPermission(permission));
			break;
		case "repair":
			retval = (Settings.Cfg.PERMISSION_REPAIR.getBoolean() || player.hasPermission(permission));
			break;
		case "repair-all":
			retval = (Settings.Cfg.PERMISSION_REPAIR_ALL.getBoolean() || player.hasPermission(permission));
			break;
		case "sell":
			retval = (Settings.Cfg.PERMISSION_SELL.getBoolean() || player.hasPermission(permission));
			break;
		case "sell-all":
			retval = (Settings.Cfg.PERMISSION_SELL_ALL.getBoolean() || player.hasPermission(permission));
			break;
		case "salvage":
			retval = (Settings.Cfg.PERMISSION_SALVAGE.getBoolean() || player.hasPermission(permission));
			break;
		case "salvage-all":
			retval = (Settings.Cfg.PERMISSION_SALVAGE_ALL.getBoolean() || player.hasPermission(permission));
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
			retval = (Settings.Cfg.PERMISSION_VERSION.getBoolean() || player.hasPermission(permission));
			break;
		}
		return retval;
	}

	public static int romanToInt(String romanNumber) {
		Hashtable<Character, Integer> romanNumbers = new Hashtable<>();
		romanNumbers.put('I', 1);	romanNumbers.put('V', 5);
		romanNumbers.put('X', 10);	romanNumbers.put('L', 50);
		romanNumbers.put('C', 100);	romanNumbers.put('D', 500);
		romanNumbers.put('M', 1000);

		int decNum = 0;
		int prevNum = 0;

		for (int x = romanNumber.length()-1; x >= 0; x--) {
			int temp = romanNumbers.get(romanNumber.toUpperCase().charAt(x));
			if (temp < prevNum) {
				decNum -= temp;
			} else {
				decNum += temp;
			}
			prevNum = temp;
		}
		return decNum;
	}

	public static String intToRoman(int nbr) {

		// I'm still coming up with a smaller algorithm here && lower priority for now
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