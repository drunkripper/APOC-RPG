/*    */ package com.modcrafting.diablodrops.events;
/*    */ 
/*    */ import org.bukkit.Chunk;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class RuinGenerateEvent extends Event
/*    */   implements Cancellable
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private Block chest;
/*    */   private final Chunk chunk;
/* 22 */   private boolean isCancelled = false;
/*    */ 
/*    */   public static HandlerList getHandlerList()
/*    */   {
/* 16 */     return handlers;
/*    */   }
/*    */ 
/*    */   public RuinGenerateEvent(Chunk chunk, Block block)
/*    */   {
/* 26 */     this.chunk = chunk;
/* 27 */     this.chest = block;
/*    */   }
/*    */ 
/*    */   public Block getChest()
/*    */   {
/* 32 */     return this.chest;
/*    */   }
/*    */ 
/*    */   public Chunk getChunk()
/*    */   {
/* 37 */     return this.chunk;
/*    */   }
/*    */ 
/*    */   public HandlerList getHandlers()
/*    */   {
/* 43 */     return handlers;
/*    */   }
/*    */ 
/*    */   public boolean isCancelled()
/*    */   {
/* 49 */     return this.isCancelled;
/*    */   }
/*    */ 
/*    */   public void setCancelled(boolean bln)
/*    */   {
/* 55 */     this.isCancelled = bln;
/*    */   }
/*    */ 
/*    */   public void setChest(Block chest)
/*    */   {
/* 60 */     this.chest = chest;
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.events.RuinGenerateEvent
 * JD-Core Version:    0.6.2
 */