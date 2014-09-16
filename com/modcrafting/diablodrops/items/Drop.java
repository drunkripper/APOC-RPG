/*    */ package com.modcrafting.diablodrops.items;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ public class Drop extends ItemStack
/*    */ {
/*    */   public Drop(Material mat)
/*    */   {
/* 17 */     super(mat);
/*    */     ItemMeta meta;
/* 19 */     if (hasItemMeta())
/* 20 */       meta = getItemMeta();
/*    */     else
/* 22 */       meta = Bukkit.getItemFactory().getItemMeta(mat);
/* 23 */     setItemMeta(meta);
/*    */   }
/*    */ 
/*    */   public Drop(Material mat, ChatColor color, String name)
/*    */   {
/* 28 */     super(mat);
/*    */     ItemMeta meta;
/* 30 */     if (hasItemMeta())
/* 31 */       meta = getItemMeta();
/*    */     else
/* 33 */       meta = Bukkit.getItemFactory().getItemMeta(mat);
/* 34 */     meta.setDisplayName(color + name + ChatColor.STRIKETHROUGH);
/* 35 */     setItemMeta(meta);
/*    */   }
/*    */ 
/*    */   public Drop(Material mat, ChatColor color, String name, short durability)
/*    */   {
/* 40 */     super(mat);
/*    */     ItemMeta meta;
/* 42 */     if (hasItemMeta())
/* 43 */       meta = getItemMeta();
/*    */     else
/* 45 */       meta = Bukkit.getItemFactory().getItemMeta(mat);
/* 46 */     meta.setDisplayName(color + name + ChatColor.STRIKETHROUGH);
/* 47 */     setItemMeta(meta);
/* 48 */     setDurability(durability);
/*    */   }
/*    */ 
/*    */   public Drop(Material mat, ChatColor color, String name, short durability, String[] lore)
/*    */   {
/* 54 */     super(mat);
/*    */     ItemMeta meta;
/* 56 */     if (hasItemMeta())
/* 57 */       meta = getItemMeta();
/*    */     else
/* 59 */       meta = Bukkit.getItemFactory().getItemMeta(mat);
/* 60 */     meta.setDisplayName(color + name + ChatColor.STRIKETHROUGH);
/* 61 */     List<String> list = new ArrayList<String>();
/* 62 */     for (String e : lore)
/*    */     {
/* 64 */       list.add(e);
/*    */     }
/* 66 */     meta.setLore(list);
/* 67 */     setItemMeta(meta);
/* 68 */     setDurability(durability);
/*    */   }
/*    */ 
/*    */   public Drop(Material mat, ChatColor color, String name, String[] lore)
/*    */   {
/* 73 */     super(mat);
/*    */     ItemMeta meta;
/* 75 */     if (hasItemMeta())
/* 76 */       meta = getItemMeta();
/*    */     else
/* 78 */       meta = Bukkit.getItemFactory().getItemMeta(mat);
/* 79 */     meta.setDisplayName(color + name + ChatColor.STRIKETHROUGH);
/* 80 */     List<String> list = new ArrayList<String>();
/* 81 */     for (String e : lore)
/*    */     {
/* 83 */       list.add(e);
/*    */     }
/* 85 */     meta.setLore(list);
/* 86 */     setItemMeta(meta);
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.items.Drop
 * JD-Core Version:    0.6.2
 */