/*    */ package com.modcrafting.diablodrops.listeners;
/*    */ 
/*    */ import com.modcrafting.diablodrops.DiabloDrops;
/*    */ import com.modcrafting.diablodrops.events.IdentifyItemEvent;

/*    */ import java.util.Iterator;

/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
import org.bukkit.event.Event;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.block.Action;
/*    */ import org.bukkit.event.player.PlayerInteractEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.PlayerInventory;
/*    */ import org.bukkit.inventory.meta.BookMeta;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ public class TomeListener
/*    */   implements Listener
/*    */ {
/*    */   private final DiabloDrops plugin;
/*    */ 
/*    */   public TomeListener(DiabloDrops plugin)
/*    */   {
/* 29 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */   public ChatColor findColor(String s)
/*    */   {
/* 34 */     char[] c = s.toCharArray();
/* 35 */     for (int i = 0; i < c.length; i++)
/* 36 */       if ((c[i] == new Character('Â').charValue()) && (i + 1 < c.length))
/* 37 */         return ChatColor.getByChar(c[(i + 1)]);
/* 38 */     return null;
/*    */   }
/*    */ 
@SuppressWarnings("deprecation")
/*    */   @EventHandler(priority=EventPriority.LOWEST)
/*    */   public void onRightClick(PlayerInteractEvent e)
/*    */   {
/* 45 */     if (((e.getAction().equals(Action.RIGHT_CLICK_AIR)) || (e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) && (e.getPlayer().getItemInHand().getType().equals(Material.WRITTEN_BOOK)))
/*    */     {
/* 50 */       ItemStack inh = e.getPlayer().getItemInHand();
/* 51 */       BookMeta b = (BookMeta)inh.getItemMeta();
/* 52 */       if (b == null)
/* 53 */         return;
/* 54 */       if ((!b.hasTitle()) || (!b.hasAuthor()))
/* 55 */         return;
/* 56 */       if ((b.getTitle().contains("Identity Tome")) && (findColor(b.getAuthor()).equals(ChatColor.MAGIC)))
/*    */       {
/* 59 */         Player p = e.getPlayer();
/* 60 */         PlayerInventory pi = p.getInventory();
/* 61 */         p.updateInventory();
/* 62 */         Iterator<ItemStack> itis = pi.iterator();
/* 63 */         while (itis.hasNext())
/*    */         {
/* 65 */           ItemStack tool = (ItemStack)itis.next();
/* 66 */           if ((tool != null) && (this.plugin.getDropAPI().canBeItem(tool.getType())))
/*    */           {
/*    */             ItemMeta meta;
/* 72 */             if (tool.hasItemMeta())
/* 73 */               meta = tool.getItemMeta();
/*    */             else
/* 75 */               meta = this.plugin.getServer().getItemFactory().getItemMeta(tool.getType());
/*    */             String name;
/* 78 */             if (meta.hasDisplayName())
/* 79 */               name = meta.getDisplayName();
/*    */             else
/* 81 */               name = tool.getType().name();
/* 82 */             if ((ChatColor.getLastColors(name) != null) && ((ChatColor.getLastColors(name).equalsIgnoreCase(ChatColor.MAGIC.name())) || (ChatColor.getLastColors(name).equalsIgnoreCase(ChatColor.MAGIC.toString())) || (name.contains(ChatColor.MAGIC.name())) || (name.contains(ChatColor.MAGIC.toString()))))
/*    */             {
/* 92 */               IdentifyItemEvent iie = new IdentifyItemEvent(tool);
/* 93 */               this.plugin.getServer().getPluginManager().callEvent(iie);
/* 94 */               if (iie.isCancelled())
/*    */               {
/* 96 */                 p.sendMessage(ChatColor.RED + "You are unable to identify right now.");
/*    */ 
/* 98 */                 p.closeInventory();
/* 99 */                 e.setUseItemInHand(org.bukkit.event.Event.Result.DENY);
/* 100 */                 e.setCancelled(true);
/* 101 */                 return;
/*    */               }
/* 103 */               pi.setItemInHand(null);
/* 104 */               ItemStack item = this.plugin.getDropAPI().getItem(tool);
/*    */ 
/* 108 */               while ((item == null) || (!item.hasItemMeta()) || (!item.getItemMeta().hasDisplayName()) || (item.getItemMeta().getDisplayName().contains(ChatColor.MAGIC.toString())))
/*    */               {
/* 111 */                 item = this.plugin.getDropAPI().getItem(tool);
/*    */               }
/* 113 */               pi.removeItem(new ItemStack[] { tool });
/* 114 */               pi.addItem(new ItemStack[] { item });
/* 115 */               p.sendMessage(ChatColor.GREEN + "You have identified an item!");
/*    */ 
/* 117 */               p.updateInventory();
/* 118 */               e.setUseItemInHand(Event.Result.DENY);
/* 119 */               e.setCancelled(true);
/* 120 */               p.closeInventory();
/* 121 */               return;
/*    */             }
/*    */           }
/*    */         }
/* 123 */         p.sendMessage(ChatColor.RED + "You have no items to identify.");
/* 124 */         p.closeInventory();
/* 125 */         e.setUseItemInHand(Event.Result.DENY);
/* 126 */         e.setCancelled(true);
/* 127 */         return;
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.listeners.TomeListener
 * JD-Core Version:    0.6.2
 */