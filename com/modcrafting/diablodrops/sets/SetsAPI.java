/*     */ package com.modcrafting.diablodrops.sets;
/*     */ 
/*     */ import com.modcrafting.diablodrops.DiabloDrops;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ public class SetsAPI
/*     */ {
/*     */   private final DiabloDrops plugin;
/*     */ 
/*     */   public SetsAPI(DiabloDrops instance)
/*     */   {
/*  20 */     this.plugin = instance;
/*     */   }
/*     */ 
/*     */   public ArmorSet getArmorSet(String name)
/*     */   {
/*  32 */     for (ArmorSet as : this.plugin.armorSets)
/*     */     {
/*  34 */       if (as.getName().equalsIgnoreCase(name))
/*  35 */         return as;
/*     */     }
/*  37 */     return null;
/*     */   }
/*     */ 
/*     */   public String getNameOfSet(LivingEntity entity)
/*     */   {
/*  49 */     ItemStack his = entity.getEquipment().getHelmet();
/*  50 */     if (his == null)
/*  51 */       return null;
/*  52 */     if (his.hasItemMeta())
/*     */     {
/*  54 */       ItemMeta meta = his.getItemMeta();
/*  55 */       if (meta.getDisplayName() != null)
/*     */       {
/*  57 */         String[] splits = ChatColor.stripColor(meta.getDisplayName()).split(" ");
/*     */ 
/*  59 */         return splits[0];
/*     */       }
/*     */     }
/*  62 */     return null;
/*     */   }
/*     */ 
/*     */   public String getNameOfSet(Player player)
/*     */   {
/*  74 */     ItemStack his = player.getInventory().getHelmet();
/*  75 */     if (his == null)
/*  76 */       return null;
/*  77 */     if (his.hasItemMeta())
/*     */     {
/*  79 */       ItemMeta meta = his.getItemMeta();
/*  80 */       if (meta.getDisplayName() != null)
/*     */       {
/*  82 */         String[] splits = ChatColor.stripColor(meta.getDisplayName()).split(" ");
/*     */ 
/*  84 */         return splits[0];
/*     */       }
/*     */     }
/*  87 */     return null;
/*     */   }
/*     */ 
/*     */   public DiabloDrops getPlugin()
/*     */   {
/*  92 */     return this.plugin;
/*     */   }
/*     */ 
/*     */   public boolean wearingSet(LivingEntity entity)
/*     */   {
/*  97 */     ItemStack his = entity.getEquipment().getHelmet();
/*  98 */     ItemStack cis = entity.getEquipment().getChestplate();
/*  99 */     ItemStack lis = entity.getEquipment().getLeggings();
/* 100 */     ItemStack bis = entity.getEquipment().getBoots();
/* 101 */     if ((his == null) || (cis == null) || (lis == null) || (bis == null))
/* 102 */       return false;
/* 103 */     Set<ItemStack> sis = new HashSet<ItemStack>();
/* 104 */     sis.add(cis);
/* 105 */     sis.add(lis);
/* 106 */     sis.add(bis);
/*     */     String potentialSet;
/* 107 */     if (his.hasItemMeta())
/*     */     {
/* 109 */       ItemMeta meta = his.getItemMeta();
/* 110 */       potentialSet = "";
/* 111 */       if (meta.getDisplayName() != null)
/*     */       {
/* 113 */         String[] splits = ChatColor.stripColor(meta.getDisplayName()).split(" ");
/*     */ 
/* 115 */         potentialSet = splits[0];
/* 116 */         for (ItemStack is : sis)
/*     */         {
/* 118 */           ItemMeta ism = is.getItemMeta();
/* 119 */           if (ism != null)
/*     */           {
/* 121 */             if (ism.getDisplayName() != null)
/*     */             {
/* 123 */               String[] splits1 = ChatColor.stripColor(ism.getDisplayName()).split(" ");
/*     */ 
/* 125 */               if (!splits1[0].equalsIgnoreCase(potentialSet))
/* 126 */                 return false;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 132 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean wearingSet(Player player)
/*     */   {
/* 143 */     ItemStack his = player.getInventory().getHelmet();
/* 144 */     ItemStack cis = player.getInventory().getChestplate();
/* 145 */     ItemStack lis = player.getInventory().getLeggings();
/* 146 */     ItemStack bis = player.getInventory().getBoots();
/* 147 */     if ((his == null) || (cis == null) || (lis == null) || (bis == null))
/* 148 */       return false;
/* 149 */     Set<ItemStack> sis = new HashSet<ItemStack>();
/* 150 */     sis.add(cis);
/* 151 */     sis.add(lis);
/* 152 */     sis.add(bis);
/*     */     String potentialSet;
/* 153 */     if (his.hasItemMeta())
/*     */     {
/* 155 */       ItemMeta meta = his.getItemMeta();
/* 156 */       potentialSet = "";
/* 157 */       if (meta.getDisplayName() != null)
/*     */       {
/* 159 */         String[] splits = ChatColor.stripColor(meta.getDisplayName()).split(" ");
/*     */ 
/* 161 */         potentialSet = splits[0];
/* 162 */         for (ItemStack is : sis)
/*     */         {
/* 164 */           ItemMeta ism = is.getItemMeta();
/* 165 */           if (ism != null)
/*     */           {
/* 167 */             if (ism.getDisplayName() != null)
/*     */             {
/* 169 */               String[] splits1 = ChatColor.stripColor(ism.getDisplayName()).split(" ");
/*     */ 
/* 171 */               if (!splits1[0].equalsIgnoreCase(potentialSet))
/* 172 */                 return false;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 178 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.sets.SetsAPI
 * JD-Core Version:    0.6.2
 */