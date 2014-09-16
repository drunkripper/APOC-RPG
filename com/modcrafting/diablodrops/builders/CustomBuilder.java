/*    */ package com.modcrafting.diablodrops.builders;
/*    */ 
/*    */ import com.modcrafting.diablodrops.DiabloDrops;
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.configuration.ConfigurationSection;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.enchantments.Enchantment;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ public class CustomBuilder
/*    */ {
/*    */   DiabloDrops plugin;
/*    */ 
/*    */   public CustomBuilder(DiabloDrops instance)
/*    */   {
/* 24 */     this.plugin = instance;
/*    */   }
/*    */ 
/*    */   public void build()
/*    */   {
/* 32 */     FileConfiguration fc = new YamlConfiguration();
/* 33 */     File f = new File(this.plugin.getDataFolder(), "custom.yml");
/* 34 */     if (f.exists())
/*    */     {
/*    */       try
/*    */       {
/* 38 */         fc.load(f);
/*    */       }
/*    */       catch (Exception e)
/*    */       {
/* 42 */         if (this.plugin.getDebug())
/* 43 */           this.plugin.log.warning(e.getMessage());
/*    */       }
/*    */     }
/* 46 */     for (String name : fc.getKeys(false))
/*    */     {
/* 48 */       ConfigurationSection cs = fc.getConfigurationSection(name);
/* 49 */       Material mat = Material.matchMaterial(cs.getString("Material"));
/* 50 */       ChatColor color = ChatColor.valueOf(cs.getString("Color").toUpperCase());
/*    */ 
/* 52 */       List<String> lore = cs.getStringList("Lore");
/* 53 */       ItemStack tool = new ItemStack(mat);
/* 54 */       List<String> list = new ArrayList<String>();
/* 55 */       for (String s : lore)
/*    */       {
/* 57 */         list.add(ChatColor.translateAlternateColorCodes("&".toCharArray()[0], s));
/*    */       }
/*    */ 
/* 60 */       ConfigurationSection cs1 = cs.getConfigurationSection("Enchantments");
/*    */ 
/* 62 */       if (cs1 != null)
/*    */       {
/* 64 */         for (String ench : cs1.getKeys(false))
/*    */         {
/* 66 */           Enchantment encha = Enchantment.getByName(ench.toUpperCase());
/*    */ 
/* 68 */           if (encha != null)
/*    */           {
/* 70 */             tool.addUnsafeEnchantment(encha, cs1.getInt(ench));
/*    */           }
/*    */         }
/*    */       }
/* 73 */       ItemMeta meta = tool.getItemMeta();
/* 74 */       meta.setDisplayName(color + name);
/* 75 */       meta.setLore(list);
/* 76 */       tool.setItemMeta(meta);
/* 77 */       this.plugin.custom.add(tool);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.builders.CustomBuilder
 * JD-Core Version:    0.6.2
 */