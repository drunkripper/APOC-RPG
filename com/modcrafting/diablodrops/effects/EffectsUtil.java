/*     */ package com.modcrafting.diablodrops.effects;
/*     */ 
/*     */ import com.modcrafting.diablodrops.DiabloDrops;

/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Random;

/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.FireworkEffect;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Chicken;
/*     */ import org.bukkit.entity.Cow;
/*     */ import org.bukkit.entity.Firework;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Ocelot;
/*     */ import org.bukkit.entity.Pig;
/*     */ import org.bukkit.entity.Sheep;
/*     */ import org.bukkit.entity.Villager;
/*     */ import org.bukkit.entity.Wolf;
/*     */ import org.bukkit.entity.Zombie;
/*     */ import org.bukkit.inventory.meta.FireworkMeta;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class EffectsUtil
/*     */ {
/*     */   public static void entomb(final Location loc, final int value)
/*     */   {
/*  45 */     final HashMap<Location, Material> atLoc = new HashMap<Location, Material>();
/*  46 */     Bukkit.getScheduler().scheduleSyncDelayedTask(DiabloDrops.getInstance(), new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/*  52 */         int r = 3;
/*  53 */         World world = loc.getWorld();
/*  54 */         int x = loc.getBlockX();
/*  55 */         int y = loc.getBlockY();
/*  56 */         int z = loc.getBlockZ();
/*  57 */         Location[] vertex = new Location[8];
/*  58 */         int i = 0;
/*  59 */         for (int dx = -1; dx <= 1; dx += 2)
/*     */         {
/*  61 */           for (int dy = -1; dy <= 1; dy += 2)
/*     */           {
/*  63 */             for (int dz = -1; dz <= 1; dz += 2)
/*     */             {
/*  65 */               Location l = new Location(world, x + dx * r, y + dy * r, z + dz * r);
/*     */ 
/*  68 */               vertex[(i++)] = l;
/*     */             }
/*     */           }
/*     */         }
/*  72 */         for (int x_o = vertex[0].getBlockX(); x_o <= vertex[4].getBlockX(); 
/*  73 */           x_o++)
/*     */         {
/*  75 */           for (int z_o = vertex[0].getBlockZ(); z_o <= vertex[1].getBlockZ(); 
/*  76 */             z_o++)
/*     */           {
/*  78 */             Block block1 = world.getBlockAt(x_o, vertex[0].getBlockY(), z_o);
/*  80 */             atLoc.put(block1.getLocation(), block1.getType());
/*     */ 
/*  82 */             EffectsUtil.entombBlockType(block1, value);
/*  83 */             Block block2 = world.getBlockAt(x_o, vertex[2].getBlockY(), z_o);
/*     */ 
/*  85 */             atLoc.put(block2.getLocation(), block2.getType());
/*     */ 
/*  87 */             EffectsUtil.entombBlockType(block2, value);
/*     */           }
/*     */         }
/*  90 */         for (int y_o = vertex[0].getBlockY(); y_o <= vertex[2].getBlockY(); 
/*  91 */           y_o++)
/*     */         {
/*  93 */           for (int z_o = vertex[0].getBlockZ(); z_o <= vertex[1].getBlockZ(); 
/*  94 */             z_o++)
/*     */           {
/*  96 */             Block block1 = world.getBlockAt(vertex[0].getBlockX(), y_o, z_o);
/*     */ 
/*  98 */             atLoc.put(block1.getLocation(), block1.getType());
/*     */ 
/* 100 */             EffectsUtil.entombBlockType(world.getBlockAt(vertex[0].getBlockX(), y_o, z_o), value);
/*     */ 
/* 102 */             Block block2 = world.getBlockAt(vertex[5].getBlockX(), y_o, z_o);
/*     */ 
/* 104 */             atLoc.put(block2.getLocation(), block2.getType());
/*     */ 
/* 106 */             EffectsUtil.entombBlockType(world.getBlockAt(vertex[5].getBlockX(), y_o, z_o), value);
/*     */           }
/*     */         }
/*     */ 
/* 110 */         for (int x_o = vertex[0].getBlockX(); x_o <= vertex[4].getBlockX(); 
/* 111 */           x_o++)
/*     */         {
/* 113 */           for (int y_o = vertex[0].getBlockY(); y_o <= vertex[6].getBlockY(); 
/* 114 */             y_o++)
/*     */           {
/* 116 */             Block block1 = world.getBlockAt(x_o, y_o, vertex[0].getBlockZ());
/*     */ 
/* 118 */             atLoc.put(block1.getLocation(), block1.getType());
/*     */ 
/* 120 */             EffectsUtil.entombBlockType(world.getBlockAt(x_o, y_o, vertex[0].getBlockZ()), value);
/*     */ 
/* 123 */             Block block2 = world.getBlockAt(x_o, y_o, vertex[5].getBlockZ());
/*     */ 
/* 125 */             atLoc.put(block2.getLocation(), block2.getType());
/*     */ 
/* 127 */             EffectsUtil.entombBlockType(world.getBlockAt(x_o, y_o, vertex[5].getBlockZ()), value);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     , 20L);
/*     */ 
/* 134 */     Bukkit.getScheduler().scheduleSyncDelayedTask(DiabloDrops.getInstance(), new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 140 */         for (Location loc : atLoc.keySet())
/*     */         {
/* 142 */           loc.getBlock().setType((Material)atLoc.get(loc));
/*     */         }
/*     */       }
/*     */     }
/*     */     , 200L);
/*     */   }
/*     */ 
/*     */   @SuppressWarnings("deprecation")
private static void entombBlockType(Block block, int value)
/*     */   {
/* 150 */     switch (value)
/*     */     {
/*     */     case 1:
/* 153 */       block.setTypeIdAndData(20, (byte)0, false);
/* 154 */       break;
/*     */     case 2:
/* 156 */       block.setTypeIdAndData(79, (byte)0, false);
/* 157 */       break;
/*     */     case 3:
/* 159 */       block.setTypeIdAndData(3, (byte)0, false);
/* 160 */       break;
/*     */     case 4:
/* 162 */       block.setTypeIdAndData(4, (byte)0, false);
/* 163 */       break;
/*     */     case 5:
/* 165 */       block.setTypeIdAndData(1, (byte)0, false);
/* 166 */       break;
/*     */     case 6:
/* 168 */       block.setTypeIdAndData(45, (byte)0, false);
/* 169 */       break;
/*     */     case 7:
/* 171 */       block.setTypeIdAndData(98, (byte)new Random().nextInt(4), false);
/*     */ 
/* 173 */       break;
/*     */     case 8:
/* 175 */       block.setTypeIdAndData(101, (byte)0, false);
/* 176 */       break;
/*     */     case 9:
/* 178 */       block.setTypeIdAndData(130, (byte)0, false);
/* 179 */       break;
/*     */     case 10:
/* 181 */       block.setTypeIdAndData(49, (byte)0, false);
/* 182 */       break;
/*     */     default:
/* 184 */       block.setTypeIdAndData(1, (byte)0, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static Method getMethod(Class<?> cl, String method)
/*     */   {
/* 191 */     for (Method m : cl.getMethods())
/* 192 */       if (m.getName().equals(method))
/* 193 */         return m;
/* 194 */     return null;
/*     */   }
/*     */ 
/*     */   public static void launchEntity(LivingEntity entity, int value)
/*     */   {
/* 207 */     entity.setVelocity(new Vector(0, 2 * value, 0));
/*     */   }
/*     */ 
/*     */   public static void makeBaby(LivingEntity e)
/*     */   {
/* 218 */     if ((e instanceof Zombie))
/*     */     {
/* 220 */       Zombie z = (Zombie)e;
/* 221 */       if (!z.isBaby())
/*     */       {
/* 223 */         z.setBaby(true);
/*     */       }
/*     */     }
/* 226 */     else if ((e instanceof Villager))
/*     */     {
/* 228 */       if (((Villager)e).isAdult())
/*     */       {
/* 230 */         ((Villager)e).setBaby();
/*     */       }
/*     */     }
/* 233 */     else if ((e instanceof Pig))
/*     */     {
/* 235 */       if (((Pig)e).isAdult())
/*     */       {
/* 237 */         ((Pig)e).setBaby();
/*     */       }
/*     */     }
/* 240 */     else if ((e instanceof Cow))
/*     */     {
/* 242 */       if (((Cow)e).isAdult())
/*     */       {
/* 244 */         ((Cow)e).setBaby();
/*     */       }
/*     */     }
/* 247 */     else if ((e instanceof Chicken))
/*     */     {
/* 249 */       if (((Chicken)e).isAdult())
/*     */       {
/* 251 */         ((Chicken)e).setBaby();
/*     */       }
/*     */     }
/* 254 */     else if ((e instanceof Wolf))
/*     */     {
/* 256 */       if (((Wolf)e).isAdult())
/*     */       {
/* 258 */         ((Wolf)e).setBaby();
/*     */       }
/*     */     }
/* 261 */     else if ((e instanceof Ocelot))
/*     */     {
/* 263 */       if (((Ocelot)e).isAdult())
/*     */       {
/* 265 */         ((Ocelot)e).setBaby();
/*     */       }
/*     */     }
/* 268 */     else if ((e instanceof Sheep))
/*     */     {
/* 270 */       if (((Sheep)e).isAdult())
/*     */       {
/* 272 */         ((Sheep)e).setBaby();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void playFirework(Location loc)
/*     */   {
/* 285 */     Random gen = DiabloDrops.getInstance().getSingleRandom();
/*     */     try
/*     */     {
/* 288 */       Firework fw = (Firework)loc.getWorld().spawn(loc, Firework.class);
/* 289 */       Method d0 = getMethod(loc.getWorld().getClass(), "getHandle");
/* 290 */       Method d2 = getMethod(fw.getClass(), "getHandle");
/* 291 */       Object o3 = d0.invoke(loc.getWorld(), (Object[])null);
/* 292 */       Object o4 = d2.invoke(fw, (Object[])null);
/* 293 */       Method d1 = getMethod(o3.getClass(), "broadcastEntityEffect");
/* 294 */       FireworkMeta data = fw.getFireworkMeta();
/* 295 */       data.addEffect(FireworkEffect.builder().with(org.bukkit.FireworkEffect.Type.values()[gen.nextInt(org.bukkit.FireworkEffect.Type.values().length)]).flicker(gen.nextBoolean()).trail(gen.nextBoolean()).withColor(Color.fromRGB(gen.nextInt(255), gen.nextInt(255), gen.nextInt(255))).withFade(Color.fromRGB(gen.nextInt(255), gen.nextInt(255), gen.nextInt(255))).build());
/*     */ 
/* 307 */       fw.setFireworkMeta(data);
/* 308 */       d1.invoke(o3, new Object[] { o4, Byte.valueOf("17") });
/* 309 */       fw.remove();
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void potionEffect(LivingEntity e, PotionEffectType ef, int dur)
/*     */   {
/* 330 */     e.addPotionEffect(new PotionEffect(ef, dur, 2));
/*     */   }
/*     */ 
/*     */   public static void setOnFire(LivingEntity entity, int value)
/*     */   {
/* 343 */     entity.setFireTicks(60 * Math.abs(value));
/*     */   }
/*     */ 
/*     */   public static void strikeLightning(final LivingEntity entity, int times)
/*     */   {
/* 357 */     entity.getWorld().strikeLightning(entity.getLocation());
/* 358 */     int amt = times - 1;
/* 359 */     for (int x = 1; x <= amt; x++)
/*     */     {
/* 361 */       Bukkit.getScheduler().scheduleSyncDelayedTask(DiabloDrops.getInstance(), new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 367 */           entity.getWorld().strikeLightning(entity.getLocation());
/*     */         }
/*     */       }
/*     */       , 20L * x);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.effects.EffectsUtil
 * JD-Core Version:    0.6.2
 */