/*    */ package com.modcrafting.diablodrops.builders;
/*    */ 
/*    */ import com.modcrafting.diablodrops.DiabloDrops;
/*    */ import com.modcrafting.diablodrops.tier.Tier;
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ 
/*    */ public class TierBuilder
/*    */ {
/*    */   DiabloDrops plugin;
/*    */ 
/*    */   public TierBuilder(DiabloDrops plugin)
/*    */   {
/* 21 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */   public void build()
/*    */   {
/* 29 */     this.plugin.tiers.clear();
/* 30 */     FileConfiguration cs = new YamlConfiguration();
/* 31 */     File f = new File(this.plugin.getDataFolder(), "tier.yml");
/* 32 */     if (f.exists())
/*    */       try
/*    */       {
/* 35 */         cs.load(f);
/*    */       }
/*    */       catch (Exception e)
/*    */       {
/* 39 */         if (this.plugin.getDebug())
/* 40 */           this.plugin.log.warning(e.getMessage());
/*    */       }
/* 42 */     for (String name : cs.getKeys(false))
/*    */     {
/* 44 */       int amt = cs.getInt(name + ".Enchantments.Amt");
/* 45 */       int lvl = cs.getInt(name + ".Enchantments.Levels");
/* 46 */       double chance = cs.getDouble(name + ".Chance");
/* 47 */       String color = cs.getString(name + ".Color");
/* 48 */       List<Material> l = new ArrayList<Material>();
/* 49 */       for (String s : cs.getStringList(name + ".Materials"))
/*    */       {
/* 51 */         Material mat = Material.getMaterial(s.toUpperCase());
/* 52 */         if (mat != null)
/* 53 */           l.add(mat);
/*    */       }
/* 55 */       for (String s : cs.getStringList(name + ".Material"))
/*    */       {
/* 57 */         Material mat = Material.getMaterial(s.toUpperCase());
/* 58 */         if (mat != null)
/* 59 */           l.add(mat);
/*    */       }
/* 61 */       List<String> lore = new ArrayList<String>();
/* 62 */       for (String s : cs.getStringList(name + ".Lore"))
/* 63 */         if (s != null)
/* 64 */           lore.add(ChatColor.translateAlternateColorCodes('&', s));
/* 65 */       this.plugin.tiers.add(new Tier(name, ChatColor.valueOf(color.toUpperCase()), Math.abs(amt), Math.abs(lvl), Math.abs((int)(chance * 100.0D)), l, lore, cs.getString(name + ".DisplayName", name), (float)cs.getDouble(name + ".DropChance", 100.0D) / 100.0F));
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.builders.TierBuilder
 * JD-Core Version:    0.6.2
 */