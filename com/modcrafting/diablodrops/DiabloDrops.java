/*     */ package com.modcrafting.diablodrops;
/*     */ 
/*     */ import com.modcrafting.diablodrops.builders.ArmorSetBuilder;
/*     */ import com.modcrafting.diablodrops.builders.CustomBuilder;
/*     */ import com.modcrafting.diablodrops.builders.SocketBuilder;
/*     */ import com.modcrafting.diablodrops.builders.TierBuilder;
/*     */ import com.modcrafting.diablodrops.commands.DiabloDropCommand;
/*     */ import com.modcrafting.diablodrops.drops.DropsAPI;
/*     */ import com.modcrafting.diablodrops.items.ItemAPI;
/*     */ import com.modcrafting.diablodrops.listeners.ChunkListener;
/*     */ import com.modcrafting.diablodrops.listeners.EffectsListener;
/*     */ import com.modcrafting.diablodrops.listeners.MobListener;
/*     */ import com.modcrafting.diablodrops.listeners.SetListener;
import com.modcrafting.diablodrops.listeners.SockettingListener;
/*     */ import com.modcrafting.diablodrops.listeners.TomeListener;
/*     */ import com.modcrafting.diablodrops.name.NamesLoader;
/*     */ import com.modcrafting.diablodrops.sets.ArmorSet;
/*     */ import com.modcrafting.diablodrops.sets.SetsAPI;
/*     */ import com.modcrafting.diablodrops.tier.Tier;

/*     */ import java.io.File;
import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.logging.Logger;

import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ public class DiabloDrops extends JavaPlugin
/*     */ {
/*     */   private static DiabloDrops instance;
/*  59 */   public HashSet<ArmorSet> armorSets = new HashSet<ArmorSet>();
/*  61 */   public List<ItemStack> custom = new ArrayList<ItemStack>();
/*     */   private boolean debug;
/*  63 */   public List<String> defenselore = new ArrayList<String>();
/*     */   private ItemAPI drop;
/*     */   private DropsAPI dropsAPI;
/*  66 */   public HashMap<Block, ItemStack> furnanceMap = new HashMap<Block, ItemStack>();
/*  67 */   private final Random gen = new Random();
/*     */ 
/*  69 */   public HashMap<Material, List<String>> hmprefix = new HashMap<Material, List<String>>();
/*  70 */   public HashMap<Material, List<String>> hmsuffix = new HashMap<Material, List<String>>();
/*     */   public Logger log;
/*     */   private NamesLoader nameLoader;
/*  74 */   public List<String> offenselore = new ArrayList<String>();
/*  75 */   public List<String> prefix = new ArrayList<String>();
/*     */   private SetsAPI setsAPI;
/*     */   private Settings settings;
/*  80 */   public List<String> suffix = new ArrayList<String>();
			public File LandSchematics;
			public File WaterSchematics;
			public File LavaSchematics;
			public MythicSockettingSettings SockettingSettings = new MythicSockettingSettings();
			public MythicConfigSettings mcs = new MythicConfigSettings();
/*     */ 
/*  82 */   public HashSet<Tier> tiers = new HashSet<Tier>();
/*     */ 
/*  84 */   public List<String> worlds = new ArrayList<String>();
/*     */ 
/*     */   public static DiabloDrops getInstance()
/*     */   {
/*  56 */     return instance;
/*     */   }

		    public SetsAPI getSetAPI() {
		    	return setsAPI;
		    }
/*     */ 
/*     */   public boolean getDebug()
/*     */   {
/*  88 */     return this.debug;
/*     */   }
/*     */ 
/*     */   public DropsAPI getDropAPI()
/*     */   {
/*  93 */     return this.dropsAPI;
/*     */   }
/*     */ 
/*     */   public ItemAPI getItemAPI()
/*     */   {
/*  98 */     return this.drop;
/*     */   }

			public SockettingSettings getSockettingSettings() {
				return SockettingSettings;
			}
			
			public MythicConfigSettings getConfigSettings() {
				return mcs;
			}
			
/*     */ 
/*     */   public Settings getSettings()
/*     */   {
/* 108 */     return this.settings;
/*     */   }
/*     */ 
/*     */   public Random getSingleRandom()
/*     */   {
/* 113 */     return this.gen;
/*     */   }
/*     */ 
/*     */   public void killTasks()
/*     */   {
/* 121 */     getServer().getScheduler().cancelTasks(this);
/*     */   }
/*     */ 
/*     */   public void onDisable()
/*     */   {
/* 127 */     killTasks();
/* 128 */     this.prefix.clear();
/* 129 */     this.suffix.clear();
/* 130 */     this.hmprefix.clear();
/* 131 */     this.hmsuffix.clear();
/* 132 */     this.tiers.clear();
/* 133 */     this.armorSets.clear();
/* 134 */     this.custom.clear();
/* 135 */     this.worlds.clear();
/* 136 */     this.offenselore.clear();
/* 137 */     this.defenselore.clear();
/* 138 */     this.furnanceMap.clear();
/*     */   }
/*     */ 
/*     */   public void onEnable()
/*     */   {
/* 144 */     instance = this;
/* 145 */     getDataFolder().mkdir();
			  LandSchematics = new File(getDataFolder().getAbsolutePath() + "/LandRuins");
			  if (!LandSchematics.exists())
				  LandSchematics.mkdir();
			  WaterSchematics = new File(getDataFolder().getAbsolutePath() + "/WaterRuins");
			  if (!WaterSchematics.exists())
				  WaterSchematics.mkdir();
			  LavaSchematics = new File(getDataFolder().getAbsolutePath() + "/LavaRuins");
			  if (!LavaSchematics.exists())
				  LavaSchematics.mkdir();
/* 146 */     this.log = getLogger();
/* 147 */     this.nameLoader = new NamesLoader(this);
/* 148 */     this.nameLoader.writeDefault("config.yml", false);
/* 149 */     this.nameLoader.writeDefault("custom.yml", false);
/* 150 */     this.nameLoader.writeDefault("tier.yml", false);
/* 151 */     this.nameLoader.writeDefault("set.yml", false);
/* 152 */     this.nameLoader.writeDefault("prefix.txt", false);
/* 153 */     this.nameLoader.writeDefault("suffix.txt", false);
/* 154 */     this.nameLoader.writeDefault("defenselore.txt", false);
/* 155 */     this.nameLoader.writeDefault("offenselore.txt", false);
/* 156 */     FileConfiguration config = getConfig();
/* 157 */     this.settings = new Settings(config);
/* 158 */     if (config.getBoolean("Display.ItemMaterialExtras", false))
/*     */     {
/* 160 */       File loc = new File(getDataFolder(), "/NamesPrefix/");
/* 161 */       if (!loc.exists())
/*     */       {
/* 163 */         loc.mkdir();
/*     */       }
/* 165 */       for (File f : loc.listFiles()) {
/* 166 */         if (f.getName().endsWith(".txt"))
/*     */         {
/* 168 */           getLogger().info("Loading Prefix File:" + f.getName());
/* 169 */           this.nameLoader.loadMaterialFile(this.hmprefix, new File(loc, f.getName()));
/*     */         }
/*     */       }
/* 172 */       File sloc = new File(getDataFolder(), "/NamesSuffix/");
/* 173 */       if (!sloc.exists())
/*     */       {
/* 175 */         sloc.mkdir();
/*     */       }
/* 177 */       for (File f : sloc.listFiles()) {
/* 178 */         if (f.getName().endsWith(".txt"))
/*     */         {
/* 180 */           getLogger().info("Loading Suffix File:" + f.getName());
/* 181 */           this.nameLoader.loadMaterialFile(this.hmsuffix, new File(sloc, f.getName()));
/*     */         }
/*     */       }
/*     */     }
/* 185 */     this.nameLoader.loadFile(this.prefix, "prefix.txt");
/* 186 */     this.nameLoader.loadFile(this.suffix, "suffix.txt");
/* 187 */     this.nameLoader.loadFile(this.defenselore, "defenselore.txt");
/* 188 */     this.nameLoader.loadFile(this.offenselore, "offenselore.txt");
/* 189 */     this.custom = new ArrayList<ItemStack>();
/* 190 */     this.drop = new ItemAPI();
/* 191 */     new CustomBuilder(this).build();
/* 192 */     new SocketBuilder(this).build();
/* 193 */     new TierBuilder(this).build();
/* 194 */     new ArmorSetBuilder(this).build();
/* 195 */     this.dropsAPI = new DropsAPI(this);
/* 196 */     this.setsAPI = new SetsAPI(this);
/* 197 */     if (config.getBoolean("Worlds.Enabled", false))
/*     */     {
/* 199 */       for (String s : config.getStringList("Worlds.Allowed"))
/*     */       {
/* 201 */         this.worlds.add(s.toLowerCase());
/*     */       }
/*     */     }
/* 204 */     this.debug = config.getBoolean("Plugin.Debug", false);
/*     */ 
/* 206 */     PluginManager pm = getServer().getPluginManager();
/* 207 */     pm.registerEvents(new MobListener(this), this);
/* 208 */     pm.registerEvents(new TomeListener(this), this);
/* 209 */     pm.registerEvents(new SockettingListener(this), this);
/* 210 */     pm.registerEvents(new ChunkListener(this), this);
/* 211 */     pm.registerEvents(new EffectsListener(this), this);
/* 212 */     pm.registerEvents(new SetListener(this), this);
/*     */ 
/* 214 */     getCommand("diablodrops").setExecutor(new DiabloDropCommand(this));

			  /*YamlConfiguration sockettingYAML = new YamlConfiguration();
			  File file = new File(getDataFolder().getAbsolutePath() + "/socketting.yml");
			  if (file.exists()) {
				  System.out.println("Socketting doesn't exist! It is now created!");
				  file.mkdir();
			  }
			  try {
				  
				  System.out.println("Loading Conf: " + file);
				sockettingYAML.load(file);
				sockettingYAML.save(file);
				System.out.println("Loaded");
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(ChatColor.YELLOW + "Socketting Config: " + sockettingYAML);*/

			  FileConfiguration c = getConfig();
			  MythicSockettingSettings mss = SockettingSettings;
			mss = new MythicSockettingSettings();
			mss.setCanDropSocketGemsOnItems(c.getBoolean("options.can-drop-socket-gems-on-items", false));
			mss.setUseAttackerItemInHand(c.getBoolean("options.use-attacker-item-in-hand", true));
			mss.setUseAttackerArmorEquipped(c.getBoolean("options.use-attacker-armor-equipped", false));
			mss.setUseDefenderItemInHand(c.getBoolean("options.use-defender-item-in-hand", false));
			mss.setUseDefenderArmorEquipped(c.getBoolean("options.use-defender-armor-equipped", true));
			mss.setPreventMultipleChangesFromSockets(
			        c.getBoolean("options.prevent-multiple-changes-from-sockets", true));
			List<String> socketGemMats = c.getStringList("options.socket-gem-material-ids");
			mss.setSocketGemMaterials(socketGemMats);
			mss.setSocketGemName(c.getString("items.socket-name", "&6Socket Gem - %socketgem%"));
			mss.setSocketGemLore(c.getStringList("items.socket-lore"));
			mss.setSockettedItemString(c.getString("items.socketted-item-socket", "&6(Socket)"));
			mss.setSockettedItemLore(c.getStringList("items.socketted-item-lore"));
/*     */   }
/*     */ }