/*    */ package com.modcrafting.diablodrops.events;
/*    */ 
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class IdentifyItemEvent extends Event
/*    */   implements Cancellable
/*    */ {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/*    */ 
/* 18 */   private boolean isCancelled = false;
/*    */   private final ItemStack tool;
/*    */ 
/*    */   public static HandlerList getHandlerList()
/*    */   {
/* 15 */     return handlers;
/*    */   }
/*    */ 
/*    */   public IdentifyItemEvent(ItemStack tool)
/*    */   {
/* 24 */     this.tool = tool;
/*    */   }
/*    */ 
/*    */   public HandlerList getHandlers()
/*    */   {
/* 30 */     return handlers;
/*    */   }
/*    */ 
/*    */   public ItemStack getItemStack()
/*    */   {
/* 35 */     return this.tool;
/*    */   }
/*    */ 
/*    */   public boolean isCancelled()
/*    */   {
/* 41 */     return this.isCancelled;
/*    */   }
/*    */ 
/*    */   public void setCancelled(boolean bln)
/*    */   {
/* 47 */     this.isCancelled = bln;
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.events.IdentifyItemEvent
 * JD-Core Version:    0.6.2
 */