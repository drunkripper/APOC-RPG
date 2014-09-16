/*    */ package com.modcrafting.diablodrops.events;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class EntitySpawnWithItemEvent extends Event
/*    */   implements Cancellable
/*    */ {
/* 15 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final LivingEntity entity;
/* 23 */   private boolean isCancelled = false;
/*    */   private List<ItemStack> items;
/*    */ 
/*    */   public static HandlerList getHandlerList()
/*    */   {
/* 19 */     return handlers;
/*    */   }
/*    */ 
/*    */   public EntitySpawnWithItemEvent(LivingEntity entity)
/*    */   {
/* 29 */     this.entity = entity;
/* 30 */     setItems(new ArrayList<ItemStack>());
/*    */   }
/*    */ 
/*    */   public EntitySpawnWithItemEvent(LivingEntity entity, List<ItemStack> items)
/*    */   {
/* 35 */     this.entity = entity;
/* 36 */     setItems(items);
/*    */   }
/*    */ 
/*    */   public LivingEntity getEntity()
/*    */   {
/* 41 */     return this.entity;
/*    */   }
/*    */ 
/*    */   public HandlerList getHandlers()
/*    */   {
/* 47 */     return handlers;
/*    */   }
/*    */ 
/*    */   public List<ItemStack> getItems()
/*    */   {
/* 52 */     return this.items;
/*    */   }
/*    */ 
/*    */   public boolean isCancelled()
/*    */   {
/* 58 */     return this.isCancelled;
/*    */   }
/*    */ 
/*    */   public void setCancelled(boolean bln)
/*    */   {
/* 64 */     this.isCancelled = bln;
/*    */   }
/*    */ 
/*    */   public void setItems(List<ItemStack> items)
/*    */   {
/* 69 */     this.items = items;
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.events.EntitySpawnWithItemEvent
 * JD-Core Version:    0.6.2
 */