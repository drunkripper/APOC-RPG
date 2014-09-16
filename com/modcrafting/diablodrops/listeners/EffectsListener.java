/*    */ package com.modcrafting.diablodrops.listeners;
/*    */ 
/*    */ import com.modcrafting.diablodrops.DiabloDrops;
/*    */ import com.modcrafting.diablodrops.effects.EffectsAPI;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.entity.Projectile;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*    */ import org.bukkit.event.entity.EntityDamageEvent;
/*    */ 
/*    */ public class EffectsListener
/*    */   implements Listener
/*    */ {
/*    */   DiabloDrops plugin;
/*    */ 
/*    */   public EffectsListener(DiabloDrops instance)
/*    */   {
/* 21 */     this.plugin = instance;
/*    */   }
/*    */ 
/*    */   @EventHandler(priority=EventPriority.LOW)
/*    */   public void onDiabloMonsterDamageByEntityEvent(EntityDamageByEntityEvent event)
/*    */   {
/* 28 */     if ((this.plugin.worlds.size() > 0) && (this.plugin.getConfig().getBoolean("Worlds.Enabled", false)) && (!this.plugin.worlds.contains(event.getEntity().getLocation().getWorld().getName().toLowerCase())))
/*    */     {
/* 32 */       return;
/* 33 */     }if (!(event.getEntity() instanceof LivingEntity))
/* 34 */       return;
/* 35 */     LivingEntity entity = (LivingEntity)event.getEntity();
/* 36 */     LivingEntity damager = null;
/* 37 */     if ((event.getDamager() instanceof LivingEntity))
/*    */     {
/* 39 */       damager = (LivingEntity)event.getDamager();
/*    */     }
/* 41 */     else if ((event.getDamager() instanceof Projectile))
/*    */     {
/* 43 */       damager = ((Projectile)event.getDamager()).getShooter();
/*    */     }
/* 45 */     if ((entity == null) || (damager == null))
/* 46 */       return;
/* 47 */     if (entity.getWorld() != damager.getWorld())
/* 48 */       return;
/* 49 */     EffectsAPI.handlePluginEffects(entity, damager, event);
/*    */   }
/*    */ 
/*    */   @EventHandler(priority=EventPriority.LOWEST)
/*    */   public void onDiabloMonsterDamageEvent(EntityDamageEvent event)
/*    */   {
/* 55 */     if ((this.plugin.worlds.size() > 0) && (this.plugin.getConfig().getBoolean("Worlds.Enabled", false)) && (!this.plugin.worlds.contains(event.getEntity().getLocation().getWorld().getName().toLowerCase())))
/*    */     {
/* 59 */       return;
/* 60 */     }if ((event.getEntity() instanceof Player))
/*    */     {
/* 62 */       EffectsAPI.handlePluginEffects((LivingEntity)event.getEntity(), null, event);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.listeners.EffectsListener
 * JD-Core Version:    0.6.2
 */