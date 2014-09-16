/*    */ package com.modcrafting.diablodrops.listeners;
/*    */ 
/*    */ import com.modcrafting.diablodrops.DiabloDrops;
/*    */ import com.modcrafting.diablodrops.events.EntitySpawnEvent;
/*    */ import com.modcrafting.diablodrops.events.EntitySpawnWithItemEvent;
/*    */ import com.modcrafting.diablodrops.tier.Tier;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.entity.Monster;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class MobListener
/*    */   implements Listener
/*    */ {
/*    */   private final DiabloDrops plugin;
/*    */ 
/*    */   public MobListener(DiabloDrops instance)
/*    */   {
/* 27 */     this.plugin = instance;
/*    */   }
/*    */ 
/*    */   @EventHandler(priority=EventPriority.LOW)
/*    */   public void onSpawn(CreatureSpawnEvent event)
/*    */   {
/* 33 */     LivingEntity entity = event.getEntity();
/* 34 */     if (!(entity instanceof Monster))
/* 35 */       return;
/* 36 */     if ((this.plugin.worlds.size() > 0) && (this.plugin.getConfig().getBoolean("Worlds.Enabled", false)) && (!this.plugin.worlds.contains(entity.getLocation().getWorld().getName().toLowerCase())))
/*    */     {
/* 40 */       return;
/* 41 */     }if ((this.plugin.getConfig().getBoolean("Reason.Spawner", true)) && (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER)))
/*    */     {
/* 43 */       return;
/* 44 */     }if ((this.plugin.getConfig().getBoolean("Reason.Egg", true)) && ((event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.EGG)) || (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG))))
/*    */     {
/* 47 */       return;
/* 48 */     }EntitySpawnEvent ese = new EntitySpawnEvent(entity, this.plugin.getSingleRandom().nextInt(100) + 1);
/*    */ 
/* 50 */     this.plugin.getServer().getPluginManager().callEvent(ese);
/* 51 */     if (((entity instanceof Monster)) && (this.plugin.getConfig().getInt("Percentages.ChancePerSpawn", 3) >= ese.getChance()))
/*    */     {
/* 55 */       List<ItemStack> items = new ArrayList<ItemStack>();
/* 56 */       for (int i = 0; i < this.plugin.getSingleRandom().nextInt(5) + 1; i++)
/*    */       {
/* 58 */         ItemStack ci = this.plugin.getDropAPI().getItem();
/* 59 */         while (ci == null)
/*    */         {
/* 61 */           ci = this.plugin.getDropAPI().getItem();
/*    */         }
/* 63 */         if ((this.plugin.getConfig().getBoolean("Custom.Only", false)) && (this.plugin.getConfig().getBoolean("Custom.Enabled", true)))
/*    */         {
/* 67 */           ci = (ItemStack)this.plugin.custom.get(this.plugin.getSingleRandom().nextInt(this.plugin.custom.size()));
/*    */         }
/*    */ 
/* 70 */         if (ci != null)
/*    */         {
/* 72 */           items.add(ci);
/*    */         }
/*    */       }
/* 75 */       EntitySpawnWithItemEvent eswi = new EntitySpawnWithItemEvent(entity, items);
/*    */ 
/* 77 */       this.plugin.getServer().getPluginManager().callEvent(eswi);
/* 78 */       if (eswi.isCancelled()) {
/* 79 */         return;
/*    */       }
/* 81 */       for (ItemStack cis : eswi.getItems())
/*    */       {
/* 83 */         if (cis != null)
/*    */         {
/* 85 */           float dropChance = 2.0F;
/*    */ 
/* 88 */           Tier tier = this.plugin.getDropAPI().getTier(cis);
/* 89 */           if (tier != null) {
/* 90 */             dropChance = tier.getDropChance() * 0.01F;
/*    */           }
/* 92 */           if ((this.plugin.getItemAPI().isHelmet(cis.getType())) || (cis.getType().equals(Material.SKULL_ITEM)))
/*    */           {
/* 95 */             entity.getEquipment().setHelmet(cis);
/* 96 */             entity.getEquipment().setHelmetDropChance(dropChance);
/*    */           }
/* 98 */           else if (this.plugin.getItemAPI().isChestPlate(cis.getType()))
/*    */           {
/* 100 */             entity.getEquipment().setChestplate(cis);
/* 101 */             entity.getEquipment().setChestplateDropChance(dropChance);
/*    */           }
/* 104 */           else if (this.plugin.getItemAPI().isLeggings(cis.getType()))
/*    */           {
/* 106 */             entity.getEquipment().setLeggings(cis);
/* 107 */             entity.getEquipment().setLeggingsDropChance(dropChance);
/*    */           }
/* 109 */           else if (this.plugin.getItemAPI().isBoots(cis.getType()))
/*    */           {
/* 111 */             entity.getEquipment().setBoots(cis);
/* 112 */             entity.getEquipment().setBootsDropChance(dropChance);
/*    */           }
/*    */           else
/*    */           {
/* 116 */             entity.getEquipment().setItemInHand(cis);
/* 117 */             entity.getEquipment().setItemInHandDropChance(dropChance);
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.listeners.MobListener
 * JD-Core Version:    0.6.2
 */