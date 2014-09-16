/*     */ package com.modcrafting.diablodrops.items;
/*     */ 
/*     */ import com.modcrafting.diablodrops.DiabloDrops;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.SkullMeta;
/*     */ 
/*     */ public class Socket extends Drop
/*     */ {
/*     */   private static ChatColor color()
/*     */   {
/*  32 */     return DiabloDrops.getInstance().getDropAPI().colorPicker();
/*     */   }
/*     */ 
/*     */   public Socket(Material mat)
/*     */   {
/*  37 */     super(mat, color(), "Socket Enhancement", new String[] { ChatColor.GOLD + "Put in the bottom of a furnace", ChatColor.GOLD + "with another item in the top", ChatColor.GOLD + "to add socket enhancements." });
/*     */ 
/*  41 */     SkullType type = null;
/*  42 */     int numType = DiabloDrops.getInstance().getSingleRandom().nextInt(SkullType.values().length);
/*     */ 
/*  44 */     for (SkullType skullType : SkullType.values())
/*     */     {
/*  46 */       if (skullType.getData() == numType)
/*     */       {
/*  48 */         type = skullType;
/*  49 */         break;
/*     */       }
/*     */     }
/*  52 */     if (type == null)
/*     */     {
/*  54 */       switch (DiabloDrops.getInstance().getSingleRandom().nextInt(5))
/*     */       {
/*     */       case 1:
/*  57 */         type = SkullType.WITHER;
/*  58 */         break;
/*     */       case 2:
/*  60 */         type = SkullType.ZOMBIE;
/*  61 */         break;
/*     */       case 3:
/*  63 */         type = SkullType.PLAYER;
/*  64 */         break;
/*     */       case 4:
/*  66 */         type = SkullType.CREEPER;
/*  67 */         break;
/*     */       default:
/*  69 */         type = SkullType.SKELETON;
/*     */       }
/*     */     }
/*     */ 
/*  73 */     setDurability(type.getData());
/*     */     ItemMeta meta;
/*  75 */     if (hasItemMeta())
/*  76 */       meta = getItemMeta();
/*     */     else
/*  78 */       meta = Bukkit.getItemFactory().getItemMeta(mat);
/*  79 */     if (mat.equals(Material.SKULL_ITEM))
/*     */     {
/*  81 */       SkullMeta sk = (SkullMeta)meta;
/*  82 */       if (type.equals(SkullType.PLAYER))
/*     */       {
/*  84 */         if (Bukkit.getServer().getOfflinePlayers().length > 0)
/*     */         {
/*  86 */           sk.setOwner(Bukkit.getServer().getOfflinePlayers()[DiabloDrops.getInstance().getSingleRandom().nextInt(Bukkit.getServer().getOfflinePlayers().length)].getName());
/*     */         }
				  else if (DiabloDrops.getInstance().getSingleRandom().nextBoolean())
/*     */         {
/*  98 */           sk.setOwner("deathmarine");
/*     */         }
/*     */         else
/*     */         {
/* 102 */           sk.setOwner("ToppleTheNun");
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 107 */     setItemMeta(meta);
/*     */   }
/*     */ 
/*     */   public static enum SkullType
/*     */   {
/*  15 */     CREEPER(4), PLAYER(3), SKELETON(0), WITHER(1), ZOMBIE(2);
/*     */ 
/*     */     public int type;
/*     */ 
/*     */     private SkullType(int i) {
/*  20 */       this.type = i;
/*     */     }
/*     */ 
/*     */     public short getData()
/*     */     {
/*  25 */       return (short)this.type;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.items.Socket
 * JD-Core Version:    0.6.2
 */