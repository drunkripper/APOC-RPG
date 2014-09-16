/*    */ package com.modcrafting.diablodrops.builders;
/*    */ 
/*    */ import com.modcrafting.diablodrops.DiabloDrops;
/*    */ import com.modcrafting.diablodrops.sets.ArmorSet;
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ 
/*    */ public class ArmorSetBuilder
/*    */ {
/*    */   DiabloDrops plugin;
/*    */ 
/*    */   public ArmorSetBuilder(DiabloDrops plugin)
/*    */   {
/* 19 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */   public void build()
/*    */   {
/* 27 */     this.plugin.armorSets.clear();
/* 28 */     FileConfiguration cs = new YamlConfiguration();
/* 29 */     File f = new File(this.plugin.getDataFolder(), "set.yml");
/* 30 */     if (f.exists())
/*    */     {
/*    */       try
/*    */       {
/* 34 */         cs.load(f);
/*    */       }
/*    */       catch (Exception e)
/*    */       {
/* 38 */         if (this.plugin.getDebug())
/* 39 */           this.plugin.log.warning(e.getMessage());
/*    */       }
/*    */     }
/* 42 */     for (String name : cs.getKeys(false))
/*    */     {
/* 44 */       List<String> bonuses = cs.getStringList(name + ".Bonuses");
/* 45 */       if (bonuses == null)
/* 46 */         bonuses = new ArrayList<String>();
/* 47 */       this.plugin.armorSets.add(new ArmorSet(name, bonuses));
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.builders.ArmorSetBuilder
 * JD-Core Version:    0.6.2
 */