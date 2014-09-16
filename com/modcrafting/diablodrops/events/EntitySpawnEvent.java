/*    */ package com.modcrafting.diablodrops.events;
/*    */ 
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class EntitySpawnEvent extends Event
/*    */ {
/* 10 */   private static final HandlerList handlers = new HandlerList();
/*    */   private int chance;
/*    */   private final LivingEntity entity;
/*    */ 
/*    */   public static HandlerList getHandlerList()
/*    */   {
/* 14 */     return handlers;
/*    */   }
/*    */ 
/*    */   public EntitySpawnEvent(LivingEntity entity, int chance)
/*    */   {
/* 22 */     this.entity = entity;
/* 23 */     this.chance = chance;
/*    */   }
/*    */ 
/*    */   public int getChance()
/*    */   {
/* 33 */     return this.chance;
/*    */   }
/*    */ 
/*    */   public LivingEntity getEntity()
/*    */   {
/* 43 */     return this.entity;
/*    */   }
/*    */ 
/*    */   public HandlerList getHandlers()
/*    */   {
/* 49 */     return handlers;
/*    */   }
/*    */ 
/*    */   public void setChance(int chance)
/*    */   {
/* 60 */     this.chance = chance;
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.events.EntitySpawnEvent
 * JD-Core Version:    0.6.2
 */