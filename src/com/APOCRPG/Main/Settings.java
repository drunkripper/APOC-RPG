package com.APOCRPG.Main;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Settings {

    public enum Cfg {

        //Scrap
        MAIN_FOLDER("/plugin/APOC-RPG"),

        // Global constants - Database
        DATABASE_NAME("apoc_db"), //Global Database name for the plugin
        DATABASE_HOST(getSettings().getString("Database.ip")),
        DATABASE_PORT(getSettings().getInt("Database.port")),
        DATABASE_UNAME(getSettings().getString("Database.username")),
        DATABASE_PASSWD(getSettings().getString("Database.password")),

        // Global constants - General
        APOCRPG_ERROR(ChatColor.RED + "[APOC-RPG] "),
        APOCRPG_WARNING(ChatColor.YELLOW + "[APOC-RPG] "),
        APOCRPG_SUCCESS(ChatColor.GREEN + "[APOC-RPG] "),
        APOCRPG_ERROR_EMPTY_HAND(APOCRPG_WARNING.getString() + "You have nothing in your hand!"),
        APOCRPG_ERROR_NO_PERMISSION(APOCRPG_ERROR.getString() + "You're lacking permissions for this action."),
        APOCRPG_ERROR_NO_COMMAND(APOCRPG_WARNING.getString() + "There's no such command"),
        APOCRPG_ERROR_NO_MONEY(APOCRPG_ERROR.getString() + "You don't have enough money!"),
        APOCRPG_ERROR_SOCKET(APOCRPG_WARNING.getString() + "You can not socket that gem to this item!"),
        APOCRPG_ERROR_DATABASE_CONNECTION("Couldn't establish connection to remote DB."),
        DISPLAY_NAME_GEM(ChatColor.GREEN + "Socket Gem"),
        DISPLAY_NAME_TOME(ChatColor.GREEN + "Tome of Identify"),
        DISPLAY_NAME_UNIDENTIFIED_ITEM(ChatColor.WHITE + "Unidentified Item"),
        LORE_GEM_OF(ChatColor.LIGHT_PURPLE + "Gem of "),
        LORE_ITEM_SOCKET(ChatColor.LIGHT_PURPLE + "(Socket)"),
        LORE_PLAYER_BOUND(ChatColor.WHITE + "Player Bound:"),
        LORE_REPAIRED(ChatColor.DARK_GRAY + "Repaired"),
        LORE_TOME("Identify the Unknown"),
        LORE_UNKNOWN_ITEM("Unidentified Item"),

        // Global constants - Config Settings
        CHEST_FILL_RPG(getSettings().getBoolean("Dungeons.chest-fill-rpg")),
        CHEST_LOCKABLE(getSettings().getBoolean("Dungeons.chest-lockable")),
        CHEST_MAX_RANDOM(getSettings().getInt("Dungeons.chest-max-random")),
        CHEST_MIN_ITEMS(getSettings().getInt("Dungeons.chest-min-items")),
        CHEST_MAX_ITEMS(getSettings().getInt("Dungeons.chest-max-items")),
        CHEST_MAX_CHANCE_GEM(getSettings().getInt("Dungeons.chest-max-chance-gem")),
        CHEST_MAX_CHANCE_TOME(getSettings().getInt("Dungeons.chest-max-chance-tome")),
        CHEST_MAX_CHANCE_UNKNOWN(getSettings().getInt("Dungeons.chest-max-chance-unknown")),
        CHEST_MAX_CHANCE_TIER_LEGENDARY(getSettings().getInt("Dungeons.chest-max-chance-tier-legendary")),
        CHEST_MAX_CHANCE_TIER_SET(getSettings().getInt("Dungeons.chest-max-chance-tier-set")),
        CHEST_MAX_CHANCE_TIER_UNIQUE(getSettings().getInt("Dungeons.chest-max-chance-tier-unique")),
        CHEST_MAX_CHANCE_TIER_RARE(getSettings().getInt("Dungeons.chest-max-chance-tier-rare")),
        CHEST_MAX_CHANCE_TIER_UNCOMMON(getSettings().getInt("Dungeons.chest-max-chance-tier-uncommon")),
        CHEST_MAX_CHANCE_TIER_COMMON(getSettings().getInt("Dungeons.chest-max-chance-tier-common")),

        COST_BUY_GEAR(getSettings().getDouble("Command-getSettings().cost-for-gear")),
        COST_BUY_GEM(getSettings().getDouble("Command-getSettings().cost-for-gem")),
        COST_BUY_LORE(getSettings().getDouble("Command-getSettings().cost-for-lore")),
        COST_BUY_NAME(getSettings().getDouble("Command-getSettings().cost-for-name")),
        COST_BUY_TOME(getSettings().getDouble("Command-getSettings().cost-for-tome")),
        COST_DISENCHANT(getSettings().getDouble("Command-getSettings().cost-to-disenchant")),
        COST_ENCHANT_LVL_1(getSettings().getDouble("Command-getSettings().cost-to-enchant-1")),
        COST_ENCHANT_LVL_2(getSettings().getDouble("Command-getSettings().cost-to-enchant-2")),
        COST_ENCHANT_LVL_3(getSettings().getDouble("Command-getSettings().cost-to-enchant-3")),
        COST_ENCHANT_LVL_4(getSettings().getDouble("Command-getSettings().cost-to-enchant-4")),
        COST_ENCHANT_LVL_5(getSettings().getDouble("Command-getSettings().cost-to-enchant-5")),
        COST_ENCHANT_LVL_6(getSettings().getDouble("Command-getSettings().cost-to-enchant-6")),
        COST_ENCHANT_LVL_7(getSettings().getDouble("Command-getSettings().cost-to-enchant-7")),
        COST_ENCHANT_LVL_8(getSettings().getDouble("Command-getSettings().cost-to-enchant-8")),
        COST_ENCHANT_LVL_9(getSettings().getDouble("Command-getSettings().cost-to-enchant-9")),
        COST_ENCHANT_LVL_10(getSettings().getDouble("Command-getSettings().cost-to-enchant-10")),
        COST_REMOVE_GEM(getSettings().getDouble("Command-getSettings().cost-to-degem")),
        COST_REMOVE_SOCKET(getSettings().getDouble("Command-getSettings().cost-to-desocket")),
        COST_REPAIR(getSettings().getDouble("Command-getSettings().cost-to-repair")),
        COST_SALVAGE(getSettings().getDouble("Command-getSettings().cost-to-salvage")),
        COST_SOCKET_1(getSettings().getDouble("Command-getSettings().cost-to-socket-1")),
        COST_SOCKET_2(getSettings().getDouble("Command-getSettings().cost-to-socket-2")),
        COST_SOCKET_3(getSettings().getDouble("Command-getSettings().cost-to-socket-3")),

        DEBUG(getSettings().getBoolean("Plugin.debug")),

        EXP_DISENCHANT(getSettings().getDouble("Command-getSettings().disenchant-exp")),

        GEAR_SOCKETS_MAX_BUY(getSettings().getInt("Tiers.sockets-max-buy")),
        GEAR_NO_SALE_ON_REPAIR(getSettings().getBoolean("Tiers.no-sell-on-repair")),
        GEAR_LEATHER_RATE(getSettings().getDouble("Command-getSettings().leather-rate")),
        GEAR_WOOD_RATE(getSettings().getDouble("Command-getSettings().wood-rate")),
        GEAR_STONE_RATE(getSettings().getDouble("Command-getSettings().stone-rate")),
        GEAR_GOLD_RATE(getSettings().getDouble("Command-getSettings().gold-rate")),
        GEAR_CHAIN_RATE(getSettings().getDouble("Command-getSettings().chain-rate")),
        GEAR_IRON_RATE(getSettings().getDouble("Command-getSettings().iron-rate")),
        GEAR_DIAMOND_RATE(getSettings().getDouble("Command-getSettings().diamond-rate")),
        GEAR_FORGE_RATE(getSettings().getDouble("Command-getSettings().forge-rate")),

        TIER_COMMON(0),
        TIER_COMMON_ENCHANTS_ALLOW(getSettings().getBoolean("Tiers.enchant-allow-common")),
        TIER_COMMON_ENCHANTS_MIN(getSettings().getInt("Tiers.enchant-min-common")),
        TIER_COMMON_ENCHANTS_MAX(getSettings().getInt("Tiers.enchant-max-common")),
        TIER_COMMON_ENCHANTS_MAX_LVL(getSettings().getInt("Tiers.enchant-max-level-common")),
        TIER_COMMON_SOCKETS_ALLOW(getSettings().getBoolean("Tiers.allow-sockets-common")),
        TIER_COMMON_MAX_CHANCE(getSettings().getDouble("Tiers.max-chance-common")),
        TIER_COMMON_SOCKETS_MAX(getSettings().getInt("Tiers.sockets-max-common")),
        TIER_COMMON_NAMING(getSettings().getBoolean("Tiers.name-random-common")),
        TIER_COMMON_NAMES_COLOR(getSettings().getString("Tiers.name-color-common")),

        TIER_UNCOMMON(1),
        TIER_UNCOMMON_ENCHANTS_ALLOW(getSettings().getBoolean("Tiers.enchant-allow-uncommon")),
        TIER_UNCOMMON_ENCHANTS_MIN(getSettings().getInt("Tiers.enchant-min-uncommon")),
        TIER_UNCOMMON_ENCHANTS_MAX(getSettings().getInt("Tiers.enchant-max-uncommon")),
        TIER_UNCOMMON_ENCHANTS_MAX_LVL(getSettings().getInt("Tiers.enchant-max-level-uncommon")),
        TIER_UNCOMMON_MAX_CHANCE(getSettings().getDouble("Tiers.max-chance-uncommon")),
        TIER_UNCOMMON_SOCKETS_ALLOW(getSettings().getBoolean("Tiers.allow-sockets-uncommon")),
        TIER_UNCOMMON_SOCKETS_MAX(getSettings().getInt("Tiers.sockets-max-uncommon")),
        TIER_UNCOMMON_NAMING(getSettings().getBoolean("Tiers.name-random-uncommon")),
        TIER_UNCOMMON_NAMES_COLOR(getSettings().getString("Tiers.name-color-uncommon")),

        TIER_RARE(2),
        TIER_RARE_ENCHANTS_ALLOW(getSettings().getBoolean("Tiers.enchant-allow-rare")),
        TIER_RARE_ENCHANTS_MIN(getSettings().getInt("Tiers.enchant-min-rare")),
        TIER_RARE_ENCHANTS_MAX(getSettings().getInt("Tiers.enchant-max-rare")),
        TIER_RARE_ENCHANTS_MAX_LVL(getSettings().getInt("Tiers.enchant-max-level-rare")),
        TIER_RARE_MAX_CHANCE(getSettings().getDouble("Tiers.max-chance-rare")),
        TIER_RARE_SOCKETS_ALLOW(getSettings().getBoolean("Tiers.allow-sockets-rare")),
        TIER_RARE_SOCKETS_MAX(getSettings().getInt("Tiers.sockets-max-rare")),
        TIER_RARE_NAMING(getSettings().getBoolean("Tiers.name-random-rare")),
        TIER_RARE_NAMES_COLOR(getSettings().getString("Tiers.name-color-rare")),

        TIER_UNIQUE(3),
        TIER_UNIQUE_ENCHANTS_ALLOW(getSettings().getBoolean("Tiers.enchant-allow-unique")),
        TIER_UNIQUE_ENCHANTS_MIN(getSettings().getInt("Tiers.enchant-min-unique")),
        TIER_UNIQUE_ENCHANTS_MAX(getSettings().getInt("Tiers.enchant-max-unique")),
        TIER_UNIQUE_ENCHANTS_MAX_LVL(getSettings().getInt("Tiers.enchant-max-level-unique")),
        TIER_UNIQUE_MAX_CHANCE(getSettings().getDouble("Tiers.max-chance-unique")),
        TIER_UNIQUE_SOCKETS_ALLOW(getSettings().getBoolean("Tiers.allow-sockets-unique")),
        TIER_UNIQUE_SOCKETS_MAX(getSettings().getInt("Tiers.sockets-max-unique")),
        TIER_UNIQUE_NAMING(getSettings().getBoolean("Tiers.name-random-unique")),
        TIER_UNIQUE_NAMES_COLOR(getSettings().getString("Tiers.name-color-unique")),

        TIER_SET(4),
        TIER_SET_ENCHANTS_ALLOW(getSettings().getBoolean("Tiers.enchant-allow-set")),
        TIER_SET_ENCHANTS_MIN(getSettings().getInt("Tiers.enchant-min-set")),
        TIER_SET_ENCHANTS_MAX(getSettings().getInt("Tiers.enchant-max-set")),
        TIER_SET_ENCHANTS_MAX_LVL(getSettings().getInt("Tiers.enchant-max-level-set")),
        TIER_SET_MAX_CHANCE(getSettings().getDouble("Tiers.max-chance-set")),
        TIER_SET_SOCKETS_ALLOW(getSettings().getBoolean("Tiers.allow-sockets-set")),
        TIER_SET_SOCKETS_MAX(getSettings().getInt("Tiers.sockets-max-set")),
        TIER_SET_NAMING(getSettings().getBoolean("Tiers.name-random-set")),
        TIER_SET_NAMES_COLOR(getSettings().getString("Tiers.name-color-set")),

        TIER_LEGENDARY(5),
        TIER_LEGENDARY_ENCHANTS_ALLOW(getSettings().getBoolean("Tiers.enchant-allow-legendary")),
        TIER_LEGENDARY_ENCHANTS_MIN(getSettings().getInt("Tiers.enchant-min-legendary")),
        TIER_LEGENDARY_ENCHANTS_MAX(getSettings().getInt("Tiers.enchant-max-legendary")),
        TIER_LEGENDARY_ENCHANTS_MAX_LVL(getSettings().getInt("Tiers.enchant-max-level-legendary")),
        TIER_LEGENDARY_MAX_CHANCE(getSettings().getDouble("Tiers.max-chance-legendary")),
        TIER_LEGENDARY_SOCKETS_ALLOW(getSettings().getBoolean("Tiers.allow-sockets-legendary")),
        TIER_LEGENDARY_SOCKETS_MAX(getSettings().getInt("Tiers.sockets-max-legendary")),
        TIER_LEGENDARY_NAMING(getSettings().getBoolean("Tiers.name-random-legendary")),
        TIER_LEGENDARY_NAMES_COLOR(getSettings().getString("Tiers.name-color-legendary")),

        PERMISSION_BUY(getSettings().getBoolean("Permissions.buy")),
        PERMISSION_BUY_ENCHANT(getSettings().getBoolean("Permissions.buy-enchant")),
        PERMISSION_BUY_GEM(getSettings().getBoolean("Permissions.buy-gem")),
        PERMISSION_BUY_ITEM(getSettings().getBoolean("Permissions.buy-item")),
        PERMISSION_BUY_NAME(getSettings().getBoolean("Permissions.buy-name")),
        PERMISSION_BUY_SOCKET(getSettings().getBoolean("Permissions.buy-socket")),
        PERMISSION_BUY_TOME(getSettings().getBoolean("Permissions.buy-tome")),
        PERMISSION_BUY_UNKNOWN(getSettings().getBoolean("Permissions.buy-unknown")),
        PERMISSION_DISENCHANT(getSettings().getBoolean("Permissions.disenchant")),
        PERMISSION_DISENCHANT_ALL(getSettings().getBoolean("Permissions.disenchant-all")),
        PERMISSION_OP_BYPASS(getSettings().getBoolean("Plugin.op-bypass-permissions")),
        PERMISSION_REMOVE_GEM(getSettings().getBoolean("Permissions.remove-gem")),
        PERMISSION_REMOVE_SOCKET(getSettings().getBoolean("Permissions.remove-socket")),
        PERMISSION_RELOAD(getSettings().getBoolean("Permissions.reload")),
        PERMISSION_REPAIR(getSettings().getBoolean("Permissions.repair")),
        PERMISSION_REPAIR_ALL(getSettings().getBoolean("Permissions.repair-all")),
        PERMISSION_SALVAGE(getSettings().getBoolean("Permissions.salvage")),
        PERMISSION_SALVAGE_ALL(getSettings().getBoolean("Permissions.salvage-all")),
        PERMISSION_SELL(getSettings().getBoolean("Permissions.sell")),
        PERMISSION_SELL_ALL(getSettings().getBoolean("Permissions.sell-all")),
        PERMISSION_VERSION(getSettings().getBoolean("Permissions.version")),
        VERSION(getSettings().getString("Plugin.version")),

        MOBS_ENABLED(getSettings().getBoolean("Mobs.enabled")),
        MOBS_SPAWN_COMMON(getSettings().getBoolean("Mobs.spawn-common")),
        MOBS_SPAWN_UNCOMMON(getSettings().getBoolean("Mobs.spawn-uncommond")),
        MOBS_SPAWN_RARE(getSettings().getBoolean("Mobs.spawn-rare")),
        MOBS_SPAWN_UNIQUE(getSettings().getBoolean("Mobs.spawn-unique")),
        MOBS_SPAWN_SET(getSettings().getBoolean("Mobs.spawn-set")),
        MOBS_SPAWN_LEGENDARY(getSettings().getBoolean("Mobs.spawn-legendary")),
        MOBS_SPAWN_GEMS(getSettings().getBoolean("Mobs.spawn-gems")),
        MOBS_SPAWN_TOMES(getSettings().getBoolean("Mobs.spawn-tomes")),
        MOBS_SPAWN_FORGE(getSettings().getBoolean("Mobs.spawn-forge")),
        MOBS_SPAWN_WEAPON(getSettings().getBoolean("Mobs.spawn-weapon")),
        MOBS_ITEM_DROP_DEATH(getSettings().getBoolean("Mobs.item-drop-death")),
        MOBS_ITEM_DROP_CHANCE(getSettings().getInt("Mobs.item-drop-chance")),
        MOBS_ITEM_HELD_MAX(getSettings().getInt("Mobs.item-held-max")),
        MOBS_ITEM_DROP_MAX(getSettings().getInt("Mobs.item-drop-max")),
        MOBS_SPAWN_ENCHANTED(getSettings().getBoolean("Mobs.spawn-enchanted")),
        MOBS_SPAWN_MAX_ENCHANT_LEVEL(getSettings().getInt("Mobs.spawn-max-enchant-level")),
        MOBS_BONUS_HP_PCT(getSettings().getDouble("Mobs.bonus-hp-pct")),
        
        //Dungeons
        DUNGEON_SPAWN_RATE(getSettings().getInt("Dungeons.dungeon-spawn-chance"));

        private final Object obj;

        Cfg(Object obj) {
            this.obj = obj;
        }

        private static Settings getSettings() {
            return new Settings();
        }

        public int getInt() {return (int) obj;}

        public boolean getBoolean() {return (boolean) obj;}

        public String getString() {return (String) obj;}

        public Double getDouble() {return (Double) obj;}
    }

    private FileConfiguration Config = null;
    private static List<String> Prefixes = new ArrayList<>();
    private static List<String> Suffixes = new ArrayList<>();
    private static List<String> Sets = new ArrayList<>();
    private static List<String> DisabledWorlds = new ArrayList<>();

    public Settings() {
        this.Config = new Plugin().getConfig();
        Prefixes = Config.getStringList("RPG-Prefix");
        Suffixes = Config.getStringList("RPG-Suffix");
        Sets = Config.getStringList("RPG-Set");
        DisabledWorlds = Config.getStringList("Dungeons.disable-dungeons-on-world");
    }

    private boolean getBoolean(String key) {
        return this.Config.getBoolean(key);
    }

    private int getInt(String key) { return this.Config.getInt(key); }

    private double getDouble(String key) {
        return this.Config.getDouble(key);
    }

    public static String getRandomPrefix() { return Prefixes.get(Plugin.Random.nextInt(Prefixes.size())); }

    public static String getRandomSuffix() {
        return Suffixes.get(Plugin.Random.nextInt(Suffixes.size()));
    }

    public static String getRandomSet() { return Sets.get(Plugin.Random.nextInt(Sets.size())); }

    private String getString(String key) {
        return this.Config.getString(key);
    }

    public static boolean areDungeonsEnabledInWorld(World world) { return !DisabledWorlds.contains(world.getName()); }
}