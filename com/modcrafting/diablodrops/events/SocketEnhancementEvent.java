/*    */ package com.modcrafting.diablodrops.events;
/*    */ 
/*    */ import org.bukkit.block.Furnace;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class SocketEnhancementEvent extends Event
/*    */ {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final ItemStack fuel;
/*    */   private final Furnace furnace;
/*    */   private final ItemStack input;
/*    */   private final ItemStack result;
/*    */ 
/*    */   public static HandlerList getHandlerList()
/*    */   {
/* 15 */     return handlers;
/*    */   }
/*    */ 
/*    */   public SocketEnhancementEvent(ItemStack input, ItemStack fuel, ItemStack result, Furnace furnace)
/*    */   {
/* 27 */     this.input = input;
/* 28 */     this.fuel = fuel;
/* 29 */     this.furnace = furnace;
/* 30 */     this.result = result;
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
/*    */   public ItemStack getResult()
/*    */   {
/* 56 */     return this.result;
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.events.SocketEnhancementEvent
 * JD-Core Version:    0.6.2
 */