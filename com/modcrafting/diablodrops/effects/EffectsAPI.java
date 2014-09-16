/*     */ package com.modcrafting.diablodrops.effects;
/*     */ 
/*     */ import com.modcrafting.diablodrops.DiabloDrops;
/*     */ import com.modcrafting.diablodrops.events.ItemEffectEvent;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ 
/*     */ public class EffectsAPI
/*     */ {
/*  27 */   public static String ATTACK = "attack";
/*  28 */   public static String DEFENSE = "defense";
/*  29 */   public static String ENTOMB = "entomb";
/*  30 */   public static String EXPLODE = "explode";
/*  31 */   public static String FIRE = "fire";
/*  32 */   public static String FREEZE = "freeze";
/*  33 */   public static String LEECH = "leech";
/*  34 */   public static String LIGHTNING = "lightning";
/*  35 */   public static String SHRINK = "shrink";
/*     */ 
/*     */   public static void addEffect(LivingEntity damaged, LivingEntity damager, String s, EntityDamageByEntityEvent event)
/*     */   {
/*  53 */     String[] args = s.replace("+", "").split(" ");
/*  54 */     if (args.length <= 1)
/*  55 */       return;
/*  56 */     Integer level = null;
/*     */     try
/*     */     {
/*  59 */       level = Integer.valueOf(Integer.parseInt(args[0]));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  63 */       level = Integer.valueOf(0);
/*     */     }
/*  65 */     if (level.intValue() == 0)
/*  66 */       return;
/*  67 */     if (args[1].equalsIgnoreCase(ATTACK))
/*     */     {
/*  70 */       double damage = event.getDamage() + level.intValue();
/*  71 */       event.setDamage(Math.max(damage, 0));
/*  72 */       return;
/*     */     }
/*  74 */     if (args[1].equalsIgnoreCase(DEFENSE))
/*     */     {
/*  76 */       double damage = event.getDamage() - level.intValue();
/*  77 */       event.setDamage(Math.max(damage, 0));
/*  78 */       return;
/*     */     }
/*  80 */     if ((args[1].equalsIgnoreCase(SHRINK)) && (damaged != null))
/*     */     {
/*  83 */       EffectsUtil.makeBaby(damaged);
/*  84 */       return;
/*     */     }
/*  86 */     if (args[1].equalsIgnoreCase(LIGHTNING))
/*     */     {
/*  89 */       if ((level.intValue() > 0) && (damaged != null))
/*     */       {
/*  91 */         EffectsUtil.strikeLightning(damaged, Math.abs(level.intValue()));
/*     */       }
/*  93 */       else if ((level.intValue() < 0) && (damager != null))
/*     */       {
/*  95 */         EffectsUtil.strikeLightning(damager, Math.abs(level.intValue()));
/*     */       }
/*  97 */       return;
/*     */     }
/*  99 */     if (args[1].equalsIgnoreCase(FIRE))
/*     */     {
/* 102 */       if ((level.intValue() > 0) && (damaged != null))
/*     */       {
/* 104 */         EffectsUtil.setOnFire(damaged, Math.abs(level.intValue()));
/*     */       }
/* 106 */       else if ((level.intValue() < 0) && (damager != null))
/*     */       {
/* 108 */         EffectsUtil.setOnFire(damager, Math.abs(level.intValue()));
/*     */       }
/* 110 */       return;
/*     */     }
/* 112 */     if (args[1].equalsIgnoreCase(ENTOMB))
/*     */     {
/* 114 */       if ((level.intValue() > 0) && (damaged != null))
/*     */       {
/* 116 */         EffectsUtil.entomb(damaged.getLocation(), Math.abs(level.intValue()));
/*     */       }
/* 118 */       else if ((level.intValue() < 0) && (damager != null))
/*     */       {
/* 120 */         EffectsUtil.entomb(damager.getLocation(), Math.abs(level.intValue()));
/*     */       }
/* 122 */       return;
/*     */     }
/* 124 */     if ((args[1].equalsIgnoreCase(LEECH)) && (damager != null) && (damaged != null))
/*     */     {
/* 127 */       if (level.intValue() > 0)
/*     */       {
/* 129 */         double chng = damaged.getHealth() - Math.abs(level.intValue());
/* 130 */         damaged.setHealth(Math.max(chng, 0));
/* 131 */         chng = level.intValue() + damager.getHealth();
/* 132 */         damager.setHealth(Math.min(chng, damager.getMaxHealth()));
/*     */       }
/* 134 */       else if (level.intValue() < 0)
/*     */       {
/* 136 */         double chng = Math.abs(level.intValue()) + damaged.getHealth();
/* 137 */         damager.setHealth(Math.min(chng, damager.getMaxHealth()));
/* 138 */         chng = damager.getHealth() - Math.abs(level.intValue());
/* 139 */         damaged.setHealth(Math.max(chng, 0));
/*     */       }
/* 141 */       return;
/*     */     }
/* 143 */     if ((args[1].equalsIgnoreCase(EXPLODE)) && (damaged != null))
/*     */     {
/* 145 */       for (int i = Math.abs(level.intValue()); i > 0; i--)
/* 146 */         EffectsUtil.playFirework(damaged.getLocation());
/* 147 */       return;
/*     */     }
/*     */ 
/* 151 */     for (PotionEffectType potionEffect : PotionEffectType.values())
/* 152 */       if ((potionEffect != null) && (potionEffect.getName().equalsIgnoreCase(args[1])))
/*     */       {
/* 154 */         if ((level.intValue() > 0) && (damaged != null))
/*     */         {
/* 156 */           damaged.addPotionEffect(new PotionEffect(potionEffect, Math.abs(level.intValue()) * 100, Math.abs(level.intValue()) - 1));
/*     */         }
/* 159 */         else if ((level.intValue() < 0) && (damager != null))
/*     */         {
/* 161 */           damager.addPotionEffect(new PotionEffect(potionEffect, Math.abs(level.intValue()) * 100, Math.abs(level.intValue()) - 1));
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public static void addEffect(LivingEntity struck, LivingEntity striker, String string, EntityDamageEvent event)
/*     */   {
/* 185 */     String[] args = string.split(" ");
/* 186 */     if (args.length <= 1)
/* 187 */       return;
/* 188 */     Integer level = null;
/*     */     try
/*     */     {
/* 191 */       level = Integer.valueOf(args[0]);
/*     */     }
/*     */     catch (NumberFormatException e)
/*     */     {
/* 195 */       level = Integer.valueOf(0);
/*     */     }
/* 197 */     if (args[1].equalsIgnoreCase(ATTACK))
/*     */     {
/* 200 */       double damage = event.getDamage() + level.intValue();
/* 201 */       if (damage >= 0)
/*     */       {
/* 203 */         event.setDamage(damage);
/*     */       }
/*     */       else
/*     */       {
/* 207 */         event.setDamage(0);
/*     */       }
/* 209 */       return;
/*     */     }
/* 211 */     if (args[1].equalsIgnoreCase(DEFENSE))
/*     */     {
/* 213 */       double damage = event.getDamage() - level.intValue();
/* 214 */       if (damage >= 0)
/*     */       {
/* 216 */         event.setDamage(damage);
/*     */       }
/*     */       else
/*     */       {
/* 220 */         event.setDamage(0);
/*     */       }
/* 222 */       return;
/*     */     }
/* 224 */     if ((args[1].equalsIgnoreCase(SHRINK)) && (struck != null))
/*     */     {
/* 227 */       EffectsUtil.makeBaby(struck);
/* 228 */       return;
/*     */     }
/* 230 */     if (args[1].equalsIgnoreCase(LIGHTNING))
/*     */     {
/* 233 */       if ((level.intValue() > 0) && (struck != null))
/*     */       {
/* 235 */         EffectsUtil.strikeLightning(struck, Math.abs(level.intValue()));
/*     */       }
/* 237 */       else if ((level.intValue() < 0) && (striker != null))
/*     */       {
/* 239 */         EffectsUtil.strikeLightning(striker, Math.abs(level.intValue()));
/*     */       }
/* 241 */       return;
/*     */     }
/* 243 */     if (args[1].equalsIgnoreCase(FIRE))
/*     */     {
/* 246 */       if ((level.intValue() > 0) && (struck != null))
/*     */       {
/* 248 */         EffectsUtil.setOnFire(struck, Math.abs(level.intValue()));
/*     */       }
/* 250 */       else if ((level.intValue() < 0) && (striker != null))
/*     */       {
/* 252 */         EffectsUtil.setOnFire(striker, Math.abs(level.intValue()));
/*     */       }
/* 254 */       return;
/*     */     }
/* 256 */     if (args[1].equalsIgnoreCase(ENTOMB))
/*     */     {
/* 258 */       if ((level.intValue() > 0) && (struck != null))
/*     */       {
/* 260 */         EffectsUtil.entomb(struck.getLocation(), Math.abs(level.intValue()));
/*     */       }
/* 262 */       else if ((level.intValue() < 0) && (striker != null))
/*     */       {
/* 264 */         EffectsUtil.entomb(striker.getLocation(), Math.abs(level.intValue()));
/*     */       }
/* 266 */       return;
/*     */     }
/* 268 */     if ((args[1].equalsIgnoreCase(LEECH)) && (striker != null) && (struck != null))
/*     */     {
/* 271 */       if (level.intValue() > 0)
/*     */       {
/* 273 */         double chng = struck.getHealth() - Math.abs(level.intValue());
/* 274 */         if ((chng < struck.getMaxHealth()) && (chng > 0))
/*     */         {
/* 276 */           struck.setHealth(chng);
/*     */         }
/*     */         else
/*     */         {
/* 280 */           struck.setHealth(0);
/*     */         }
/* 282 */         chng = level.intValue() + striker.getHealth();
/* 283 */         if ((chng < striker.getMaxHealth()) && (chng > 0))
/*     */         {
/* 285 */           striker.setHealth(chng);
/*     */         }
/*     */         else
/*     */         {
/* 289 */           striker.setHealth(striker.getMaxHealth());
/*     */         }
/*     */       }
/* 292 */       else if (level.intValue() < 0)
/*     */       {
/* 294 */         double chng = Math.abs(level.intValue()) + struck.getHealth();
/* 295 */         if ((chng < struck.getMaxHealth()) && (chng > 0))
/*     */         {
/* 297 */           striker.setHealth(chng);
/*     */         }
/*     */         else
/*     */         {
/* 301 */           striker.setHealth(striker.getMaxHealth());
/*     */         }
/* 303 */         chng = striker.getHealth() - Math.abs(level.intValue());
/* 304 */         if ((chng < striker.getMaxHealth()) && (chng > 0))
/*     */         {
/* 306 */           struck.setHealth(chng);
/*     */         }
/*     */         else
/*     */         {
/* 310 */           struck.setHealth(0);
/*     */         }
/*     */       }
/* 313 */       return;
/*     */     }
/* 315 */     if ((args[1].equalsIgnoreCase(EXPLODE)) && (struck != null))
/*     */     {
/* 317 */       EffectsUtil.playFirework(struck.getLocation());
/*     */     }
/*     */     else
/*     */     {
/* 321 */       for (PotionEffectType potionEffect : PotionEffectType.values())
/* 322 */         if ((potionEffect != null) && (potionEffect.getName().equalsIgnoreCase(args[1])))
/*     */         {
/* 324 */           if ((level.intValue() > 0) && (struck != null))
/*     */           {
/* 326 */             struck.addPotionEffect(new PotionEffect(potionEffect, Math.abs(level.intValue()) * 100, Math.abs(level.intValue()) - 1));
/*     */           }
/* 329 */           else if ((level.intValue() < 0) && (striker != null))
/*     */           {
/* 331 */             striker.addPotionEffect(new PotionEffect(potionEffect, Math.abs(level.intValue()) * 100, Math.abs(level.intValue()) - 1));
/*     */           }
/*     */         }
/* 334 */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static ChatColor findColor(String s)
/*     */   {
/* 347 */     char[] c = s.toCharArray();
/* 348 */     for (int i = 0; i < c.length; i++)
/* 349 */       if ((c[i] == new Character('Â').charValue()) && (i + 1 < c.length))
/* 350 */         return ChatColor.getByChar(c[(i + 1)]);
/* 351 */     return null;
/*     */   }
/*     */ 
/*     */   public static void handlePluginEffects(LivingEntity damaged, LivingEntity damager, EntityDamageByEntityEvent event)
/*     */   {
/* 367 */     if ((damager instanceof Player))
/*     */     {
/* 369 */       Player striker = (Player)damager;
/* 370 */       List<ItemStack> strikerEquipment = new ArrayList<ItemStack>();
/* 371 */       strikerEquipment.add(striker.getItemInHand());
/* 372 */       for (String s : listEffects(strikerEquipment))
/*     */       {
/* 374 */         ItemEffectEvent iee = new ItemEffectEvent(damaged, damager, s);
/* 375 */         Bukkit.getPluginManager().callEvent(iee);
/* 376 */         if (!iee.isCancelled()) {
/* 377 */           addEffect(iee.getDamaged(), iee.getDamager(), iee.getEffect(), event);
/*     */         }
/*     */       }
/*     */     }
/* 381 */     if ((damaged instanceof Player))
/*     */     {
/* 383 */       Player struck = (Player)damaged;
/* 384 */       List<ItemStack> struckEquipment = new ArrayList<ItemStack>();
/* 385 */       struckEquipment.addAll(Arrays.asList(struck.getInventory().getArmorContents()));
/*     */ 
/* 387 */       for (String s : listEffects(struckEquipment))
/*     */       {
/* 389 */         ItemEffectEvent iee = new ItemEffectEvent(damaged, damager, s);
/* 390 */         Bukkit.getPluginManager().callEvent(iee);
/* 391 */         if (!iee.isCancelled())
/* 392 */           addEffect(iee.getDamager(), iee.getDamaged(), iee.getEffect(), event);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void handlePluginEffects(LivingEntity damaged, LivingEntity damager, EntityDamageEvent event)
/*     */   {
/* 411 */     if ((damager instanceof Player))
/*     */     {
/* 413 */       Player striker = (Player)damager;
/* 414 */       List<ItemStack> strikerEquipment = new ArrayList<ItemStack>();
/* 415 */       strikerEquipment.add(striker.getItemInHand());
/* 416 */       for (String s : listEffects(strikerEquipment))
/*     */       {
/* 418 */         ItemEffectEvent iee = new ItemEffectEvent(damaged, damager, s);
/* 419 */         Bukkit.getPluginManager().callEvent(iee);
/* 420 */         if (!iee.isCancelled()) {
/* 421 */           addEffect(iee.getDamaged(), iee.getDamager(), iee.getEffect(), event);
/*     */         }
/*     */       }
/*     */     }
/* 425 */     if ((damaged instanceof Player))
/*     */     {
/* 427 */       Player struck = (Player)damaged;
/* 428 */       List<ItemStack> struckEquipment = new ArrayList<ItemStack>();
/* 429 */       struckEquipment.addAll(Arrays.asList(struck.getInventory().getArmorContents()));
/*     */ 
/* 431 */       for (String s : listEffects(struckEquipment))
/*     */       {
/* 433 */         ItemEffectEvent iee = new ItemEffectEvent(damaged, damager, s);
/* 434 */         Bukkit.getPluginManager().callEvent(iee);
/* 435 */         if (!iee.isCancelled())
/* 436 */           addEffect(iee.getDamager(), iee.getDamaged(), iee.getEffect(), event);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static List<String> listEffects(List<ItemStack> equipment)
/*     */   {
/* 452 */     Set<ItemStack> toolSet = new HashSet<ItemStack>();
/* 453 */     for (ItemStack is : equipment)
/* 454 */       if ((is != null) && (!is.getType().equals(Material.AIR)))
/*     */       {
/* 456 */         toolSet.add(new ItemStack(is));
/*     */       }
/* 458 */     List<String> effects = new ArrayList<String>();
/* 459 */     for (ItemStack tool : toolSet)
/*     */     {
/* 461 */       if (tool.hasItemMeta())
/*     */       {
/* 463 */         ItemMeta meta = tool.getItemMeta();
/* 464 */         if ((meta.hasLore()) && (meta.getLore() != null) && (!meta.getLore().isEmpty()))
/*     */         {
/* 467 */           for (String string : meta.getLore())
/*     */           {
/* 469 */             string = ChatColor.stripColor(string).replace("%", "");
/* 470 */             if ((DiabloDrops.getInstance().defenselore.contains(string)) || (DiabloDrops.getInstance().offenselore.contains(string)))
/*     */             {
/* 474 */               effects.add(string);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 477 */     return effects;
/*     */   }
/*     */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.effects.EffectsAPI
 * JD-Core Version:    0.6.2
 */