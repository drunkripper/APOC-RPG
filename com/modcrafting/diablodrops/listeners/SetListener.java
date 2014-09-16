/*     */ package com.modcrafting.diablodrops.listeners;
/*     */ 
/*     */ import com.modcrafting.diablodrops.DiabloDrops;
/*     */ import com.modcrafting.diablodrops.effects.EffectsAPI;
/*     */ import com.modcrafting.diablodrops.sets.ArmorSet;
/*     */ import java.util.List;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Monster;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ 
/*     */ public class SetListener
/*     */   implements Listener
/*     */ {
/*     */   DiabloDrops plugin;
/*     */ 
/*     */   public SetListener(DiabloDrops instance)
/*     */   {
/*  24 */     this.plugin = instance;
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.LOW)
/*     */   public void onDiabloMonsterDamageByEntityEvent(EntityDamageByEntityEvent event)
/*     */   {
/*  31 */     if (((event.getDamager() instanceof Monster)) && ((event.getEntity() instanceof LivingEntity)))
/*     */     {
/*  34 */       if (this.plugin.getSetAPI().wearingSet((LivingEntity)event.getDamager()))
/*     */       {
/*  37 */         String sName = this.plugin.getSetAPI().getNameOfSet((LivingEntity)event.getDamager());
/*     */ 
/*  39 */         ArmorSet aSet = this.plugin.getSetAPI().getArmorSet(sName);
/*  40 */         if (aSet != null)
/*     */         {
/*  42 */           List<String> effects = aSet.getBonuses();
/*  43 */           for (String s : effects)
/*     */           {
/*  45 */             EffectsAPI.addEffect((LivingEntity)event.getEntity(), (LivingEntity)event.getDamager(), s, event);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*  51 */     else if (((event.getDamager() instanceof Player)) && ((event.getEntity() instanceof LivingEntity)))
/*     */     {
/*  54 */       if (this.plugin.getSetAPI().wearingSet((Player)event.getDamager()))
/*     */       {
/*  56 */         String sName = this.plugin.getSetAPI().getNameOfSet((Player)event.getDamager());
/*     */ 
/*  58 */         ArmorSet aSet = this.plugin.getSetAPI().getArmorSet(sName);
/*  59 */         if (aSet != null)
/*     */         {
/*  61 */           List<String> effects = aSet.getBonuses();
/*  62 */           for (String s : effects)
/*     */           {
/*  64 */             EffectsAPI.addEffect((LivingEntity)event.getEntity(), (LivingEntity)event.getDamager(), s, event);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  70 */     if (((event.getEntity() instanceof Monster)) && ((event.getDamager() instanceof LivingEntity)))
/*     */     {
/*  73 */       if (this.plugin.getSetAPI().wearingSet((LivingEntity)event.getEntity()))
/*     */       {
/*  75 */         String sName = this.plugin.getSetAPI().getNameOfSet((LivingEntity)event.getEntity());
/*     */ 
/*  77 */         ArmorSet aSet = this.plugin.getSetAPI().getArmorSet(sName);
/*  78 */         if (aSet != null)
/*     */         {
/*  80 */           List<String> effects = aSet.getBonuses();
/*  81 */           for (String s : effects)
/*     */           {
/*  83 */             EffectsAPI.addEffect((LivingEntity)event.getDamager(), (LivingEntity)event.getEntity(), s, event);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*  90 */     else if (((event.getEntity() instanceof Player)) && ((event.getDamager() instanceof LivingEntity)))
/*     */     {
/*  93 */       if (this.plugin.getSetAPI().wearingSet((Player)event.getEntity()))
/*     */       {
/*  95 */         String sName = this.plugin.getSetAPI().getNameOfSet((Player)event.getEntity());
/*     */ 
/*  97 */         ArmorSet aSet = this.plugin.getSetAPI().getArmorSet(sName);
/*  98 */         if (aSet != null)
/*     */         {
/* 100 */           List<String> effects = aSet.getBonuses();
/* 101 */           for (String s : effects)
/*     */           {
/* 103 */             EffectsAPI.addEffect((LivingEntity)event.getDamager(), (Player)event.getEntity(), s, event);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.LOW)
/*     */   public void onDiabloMonsterDamageEvent(EntityDamageEvent event)
/*     */   {
/* 115 */     if ((event.getEntity() instanceof Monster))
/*     */     {
/* 117 */       if (this.plugin.getSetAPI().wearingSet((LivingEntity)event.getEntity()))
/*     */       {
/* 119 */         String sName = this.plugin.getSetAPI().getNameOfSet((LivingEntity)event.getEntity());
/*     */ 
/* 121 */         ArmorSet aSet = this.plugin.getSetAPI().getArmorSet(sName);
/* 122 */         if (aSet != null)
/*     */         {
/* 124 */           List<String> effects = aSet.getBonuses();
/* 125 */           for (String s : effects)
/*     */           {
/* 127 */             EffectsAPI.addEffect((LivingEntity)event.getEntity(), null, s, event);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/* 134 */     else if ((event.getEntity() instanceof Player))
/*     */     {
/* 136 */       if (this.plugin.getSetAPI().wearingSet((Player)event.getEntity()))
/*     */       {
/* 138 */         String sName = this.plugin.getSetAPI().getNameOfSet((Player)event.getEntity());
/*     */ 
/* 140 */         ArmorSet aSet = this.plugin.getSetAPI().getArmorSet(sName);
/* 141 */         if (aSet != null)
/*     */         {
/* 143 */           List<String> effects = aSet.getBonuses();
/* 144 */           for (String s : effects)
/*     */           {
/* 146 */             EffectsAPI.addEffect((Player)event.getEntity(), null, s, event);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.listeners.SetListener
 * JD-Core Version:    0.6.2
 */