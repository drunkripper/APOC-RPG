/*    */ package com.modcrafting.diablodrops.events;
/*    */ 
/*    */ import org.bukkit.block.Furnace;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class PreSocketEnhancementEvent extends Event
/*    */   implements Cancellable
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final ItemStack fuel;
/*    */   private final Furnace furnace;
/*    */   private final ItemStack input;
/* 23 */   private boolean isCancelled = false;
/*    */ 
/*    */   public static HandlerList getHandlerList()
/*    */   {
/* 16 */     return handlers;
/*    */   }
/*    */ 
/*    */   public PreSocketEnhancementEvent(ItemStack input, ItemStack fuel, Furnace furnace)
/*    */   {
/* 28 */     this.input = input;
/* 29 */     this.fuel = fuel;
/* 30 */     this.furnace = furnace;
/*    */   }
/*    */ 
/*    */   public ItemStack getFuel()
/*    */   {
/* 35 */     return this.fuel;
/*    */   }
/*    */ 
/*    */   public Furnace getFurnace()
/*    */   {
/* 40 */     return this.furnace;
/*    */   }
/*    */ 
/*    */   public HandlerList getHandlers()
/*    */   {
/* 46 */     return handlers;
/*    */   }
/*    */ 
/*    */   public ItemStack getInput()
/*    */   {
/* 51 */     return this.input;
/*    */   }
/*    */ 
/*    */   public boolean isCancelled()
/*    */   {
/* 57 */     return this.isCancelled;
/*    */   }
/*    */ 
/*    */   public void setCancelled(boolean bln)
/*    */   {
/* 63 */     this.isCancelled = bln;
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.events.PreSocketEnhancementEvent
 * JD-Core Version:    0.6.2
 */