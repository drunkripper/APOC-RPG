/*    */ package com.modcrafting.diablodrops.events;
/*    */ 
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class ItemEffectEvent extends Event
/*    */   implements Cancellable
/*    */ {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final LivingEntity damaged;
/*    */   private final LivingEntity damager;
/*    */   private final String effect;
/* 22 */   private boolean isCancelled = false;
/*    */ 
/*    */   public static HandlerList getHandlerList()
/*    */   {
/* 15 */     return handlers;
/*    */   }
/*    */ 
/*    */   public ItemEffectEvent(LivingEntity damaged, LivingEntity damager, String effect)
/*    */   {
/* 27 */     this.damaged = damaged;
/* 28 */     this.damager = damager;
/* 29 */     this.effect = effect;
/*    */   }
/*    */ 
/*    */   public LivingEntity getDamaged()
/*    */   {
/* 34 */     return this.damaged;
/*    */   }
/*    */ 
/*    */   public LivingEntity getDamager()
/*    */   {
/* 39 */     return this.damager;
/*    */   }
/*    */ 
/*    */   public String getEffect()
/*    */   {
/* 44 */     return this.effect;
/*    */   }
/*    */ 
/*    */   public HandlerList getHandlers()
/*    */   {
/* 50 */     return handlers;
/*    */   }
/*    */ 
/*    */   public boolean isCancelled()
/*    */   {
/* 56 */     return this.isCancelled;
/*    */   }
/*    */ 
/*    */   public void setCancelled(boolean bln)
/*    */   {
/* 62 */     this.isCancelled = bln;
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.events.ItemEffectEvent
 * JD-Core Version:    0.6.2
 */