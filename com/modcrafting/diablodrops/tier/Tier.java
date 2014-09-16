/*     */ package com.modcrafting.diablodrops.tier;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ 
/*     */ public class Tier
/*     */ {
/*     */   private final int amt;
/*     */   private final int chance;
/*     */   private final ChatColor color;
/*     */   private final String displayName;
/*     */   private final float dropChance;
/*     */   private final List<Material> l;
/*     */   private final List<String> lore;
/*     */   private final int lvl;
/*     */   private final String name;
/*     */ 
/*     */   public Tier(String name, ChatColor color, int amt, int lvl, int chance, List<Material> l, List<String> lore, String displayName, float dropChance)
/*     */   {
/*  25 */     this.name = name;
/*  26 */     this.color = color;
/*  27 */     this.amt = amt;
/*  28 */     this.lvl = lvl;
/*  29 */     this.chance = chance;
/*  30 */     this.l = l;
/*  31 */     this.lore = lore;
/*  32 */     if (displayName != null)
/*  33 */       this.displayName = displayName;
/*     */     else
/*  35 */       this.displayName = name;
/*  36 */     this.dropChance = dropChance;
/*     */   }
/*     */ 
/*     */   public Integer getAmount()
/*     */   {
/*  46 */     return Integer.valueOf(this.amt);
/*     */   }
/*     */ 
/*     */   public Integer getChance()
/*     */   {
/*  56 */     return Integer.valueOf(this.chance);
/*     */   }
/*     */ 
/*     */   public ChatColor getColor()
/*     */   {
/*  66 */     return this.color;
/*     */   }
/*     */ 
/*     */   public String getDisplayName()
/*     */   {
/*  71 */     if (this.displayName != null)
/*  72 */       return this.displayName;
/*  73 */     return this.name;
/*     */   }
/*     */ 
/*     */   public float getDropChance()
/*     */   {
/*  78 */     return this.dropChance;
/*     */   }
/*     */ 
/*     */   public Integer getLevels()
/*     */   {
/*  88 */     return Integer.valueOf(this.lvl);
/*     */   }
/*     */ 
/*     */   public List<String> getLore()
/*     */   {
/*  98 */     return this.lore;
/*     */   }
/*     */ 
/*     */   public List<Material> getMaterials()
/*     */   {
/* 108 */     return this.l;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 118 */     return this.name;
/*     */   }
/*     */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.tier.Tier
 * JD-Core Version:    0.6.2
 */