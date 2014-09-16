/*    */ package com.modcrafting.diablodrops.items;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.UUID;

/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.BookMeta;
/*    */ 
/*    */ public class IdentifyTome extends ItemStack
/*    */ {
/*    */   public IdentifyTome()
/*    */   {
/* 16 */     super(Material.WRITTEN_BOOK);
/* 17 */     BookMeta meta = (BookMeta)getItemMeta();
/* 18 */     meta.setTitle(ChatColor.DARK_AQUA + "Identity Tome");
/* 19 */     String author = UUID.randomUUID().toString();
/* 20 */     if (author.length() > 16)
/* 21 */       author = author.substring(0, 15);
/* 22 */     meta.setAuthor(ChatColor.MAGIC + author);
/* 23 */     List<String> pages = new ArrayList<String>();
/* 24 */     pages.add(0, ChatColor.MAGIC + author);
/* 25 */     meta.setPages(pages);
/* 26 */     setItemMeta(meta);
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.items.IdentifyTome
 * JD-Core Version:    0.6.2
 */