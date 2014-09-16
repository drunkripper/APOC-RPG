/*     */ package com.modcrafting.diablodrops;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ 
/*     */ public class Settings
/*     */ {
/*     */   private final boolean colorBlindCompat;
/*     */   private final ChatColor[] colorList;
/*     */   private final double custom;
/*     */   private final double lore;
/*     */   private final double socket;
/*     */   private final double socketed;
/*     */   private final int minSockets;
/*     */   private final int maxSockets;
/*     */   private final double standard;
/*     */   private final double tome;
/*     */ 
/*     */   public Settings(FileConfiguration fc)
/*     */   {
/*  24 */     this.socket = fc.getDouble("SocketItem.Chance", 5.0D);
/*  25 */     this.socketed = fc.getDouble("SockettedItem.Chance", 20.0D);
/*  26 */     this.minSockets = fc.getInt("SockettedItem.MinimumSockets", 1);
/*  27 */     this.maxSockets = fc.getInt("SockettedItem.MaximumSockets", 5);
/*  28 */     this.tome = fc.getDouble("IdentifyTome.Chance", 2.0D);
/*  29 */     this.standard = fc.getDouble("Percentages.ChancePerSpawn", 2.0D);
/*  30 */     this.lore = fc.getDouble("Lore.Chance", 2.0D);
/*  31 */     this.custom = fc.getDouble("Custom.Chance", 2.0D);
/*  32 */     this.colorBlindCompat = fc.getBoolean("Display.ColorBlind", false);
/*  33 */     this.colorList = setupSocketColors(fc);
/*     */   }
/*     */ 
/*     */   public int getCustomChance()
/*     */   {
/*  38 */     return (int)(this.custom * 100.0D);
/*     */   }
/*     */ 
/*     */   public int getLoreChance()
/*     */   {
/*  43 */     return (int)(this.lore * 100.0D);
/*     */   }
/*     */ 
/*     */   public int getMaxSockets()
/*     */   {
/*  51 */     return this.maxSockets;
/*     */   }
/*     */ 
/*     */   public int getMinSockets()
/*     */   {
/*  59 */     return this.minSockets;
/*     */   }
/*     */ 
/*     */   public int getSocketChance()
/*     */   {
/*  64 */     return (int)(this.socket * 100.0D);
/*     */   }
/*     */ 
/*     */   public ChatColor[] getSocketColors()
/*     */   {
/*  69 */     return this.colorList;
/*     */   }
/*     */ 
/*     */   public int getSocketedChance()
/*     */   {
/*  74 */     return (int)(this.socketed * 100.0D);
/*     */   }
/*     */ 
/*     */   public int getStandardChance()
/*     */   {
/*  79 */     return (int)(this.standard * 100.0D);
/*     */   }
/*     */ 
/*     */   public int getTomeChance()
/*     */   {
/*  84 */     return (int)(this.tome * 100.0D);
/*     */   }
/*     */ 
/*     */   public boolean isColorBlindCompat()
/*     */   {
/*  89 */     return this.colorBlindCompat;
/*     */   }
/*     */ 
/*     */   private ChatColor[] setupSocketColors(FileConfiguration fc)
/*     */   {
/*  94 */     List<String> colorStringList = fc.getStringList("SocketItem.Colors");
/*  95 */     if (colorStringList == null) {
/*  96 */       colorStringList = Arrays.asList(new String[] { "GREEN", "BLUE", "RED" });
/*     */     }
/*  98 */     ChatColor[] colorList = new ChatColor[colorStringList.size()];
/*  99 */     for (int i = 0; i < colorStringList.size(); i++)
/*     */     {
/* 101 */       String string = (String)colorStringList.get(i);
/* 102 */       ChatColor cc = null;
/*     */       try
/*     */       {
/* 105 */         cc = ChatColor.valueOf(string.toUpperCase());
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 109 */         continue;
/*     */       }
/* 111 */       if (cc != null)
/* 112 */         colorList[i] = cc;
/*     */     }
/* 114 */     return colorList;
/*     */   }
/*     */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.Settings
 * JD-Core Version:    0.6.2
 */