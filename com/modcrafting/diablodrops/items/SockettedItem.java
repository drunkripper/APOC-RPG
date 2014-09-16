/*     */ package com.modcrafting.diablodrops.items;
/*     */ 
/*     */ import com.modcrafting.diablodrops.DiabloDrops;
/*     */ import com.modcrafting.diablodrops.tier.Tier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ 
/*     */ public class SockettedItem extends Drop
/*     */ {
/*     */   private Tier tier;
/*     */ 
/*     */   private static ChatColor color()
/*     */   {
/*  21 */     return DiabloDrops.getInstance().getDropAPI().colorPicker();
/*     */   }
/*     */ 
/*     */   private static String name(Material mat)
/*     */   {
/*  26 */     return DiabloDrops.getInstance().getDropAPI().name(mat);
/*     */   }
/*     */ 
/*     */   private static String[] sockets()
/*     */   {
/*  31 */     List<String> list = new ArrayList<String>();
/*  32 */     int enhance = DiabloDrops.getInstance().getSettings().getMinSockets() + DiabloDrops.getInstance().getSingleRandom().nextInt(DiabloDrops.getInstance().getSettings().getMaxSockets());
/*     */ 
/*  39 */     for (int i = 0; i < enhance; i++)
/*     */     {
/*  41 */       list.add(color() + "(Socket)");
/*     */     }
/*  43 */     return (String[])list.toArray(new String[0]);
/*     */   }
/*     */ 
/*     */   public SockettedItem(Material mat)
/*     */   {
/*  50 */     super(mat);
/*  51 */     this.tier = DiabloDrops.getInstance().getDropAPI().getTier();
/*  52 */     while (this.tier == null)
/*  53 */       this.tier = DiabloDrops.getInstance().getDropAPI().getTier();
/*  54 */     Material material = mat;
/*  55 */     if ((this.tier.getMaterials().size() > 0) && (!this.tier.getMaterials().contains(mat)))
/*     */     {
/*  58 */       material = (Material)this.tier.getMaterials().get(DiabloDrops.getInstance().getSingleRandom().nextInt(this.tier.getMaterials().size()));
/*     */     }
/*     */ 
/*  62 */     setType(material);
/*  63 */     short damage = 0;
/*  64 */     if (DiabloDrops.getInstance().getConfig().getBoolean("DropFix.Damage", true))
/*     */     {
/*  67 */       damage = DiabloDrops.getInstance().getDropAPI().damageItemStack(mat);
/*     */     }
/*     */ 
/*  70 */     setDurability(damage);
/*  71 */     DiabloDrops.getInstance().getItemAPI().setName(this, this.tier.getColor() + name(mat));
/*     */ 
/*  73 */     int e = this.tier.getAmount().intValue();
/*  74 */     int l = this.tier.getLevels().intValue();
/*  75 */     List<Enchantment> eStack = Arrays.asList(Enchantment.values());
/*  76 */     boolean safe = DiabloDrops.getInstance().getConfig().getBoolean("SafeEnchant.Enabled", true);
/*     */ 
/*  78 */     if (safe)
/*     */     {
/*  80 */       eStack = DiabloDrops.getInstance().getDropAPI().getEnchantStack(this);
/*     */     }
/*     */ 
/*  83 */     for (; e > 0; e--)
/*     */     {
/*  85 */       int lvl = DiabloDrops.getInstance().getSingleRandom().nextInt(l + 1);
/*     */ 
/*  87 */       int size = eStack.size();
/*  88 */       if (size >= 1)
/*     */       {
/*  92 */         Enchantment ench = (Enchantment)eStack.get(DiabloDrops.getInstance().getSingleRandom().nextInt(size));
/*     */ 
/*  94 */         if ((lvl != 0) && (ench != null) && (!this.tier.getColor().equals(ChatColor.MAGIC)))
/*     */         {
/*  96 */           if (safe)
/*     */           {
/*  98 */             if ((lvl >= ench.getStartLevel()) && (lvl <= ench.getMaxLevel()))
/*     */             {
/*     */               try
/*     */               {
/* 103 */                 addEnchantment(ench, lvl);
/*     */               }
/*     */               catch (Exception e1)
/*     */               {
/* 107 */                 if (DiabloDrops.getInstance().getDebug())
/*     */                 {
/* 109 */                   DiabloDrops.getInstance().log.warning(e1.getMessage());
/*     */                 }
/*     */ 
/* 112 */                 e++;
/*     */               }
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 118 */             addUnsafeEnchantment(ench, lvl);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 121 */     if (DiabloDrops.getInstance().getSettings().isColorBlindCompat())
/*     */     {
/* 123 */       DiabloDrops.getInstance().getItemAPI().addLore(this, DiabloDrops.getInstance().getDropAPI().getPrettyMaterialName(material));
/*     */     }
/*     */ 
/* 131 */     if ((DiabloDrops.getInstance().getConfig().getBoolean("Display.TierName", true)) && (!this.tier.getColor().equals(ChatColor.MAGIC)))
/*     */     {
/* 135 */       DiabloDrops.getInstance().getItemAPI().addLore(this, this.tier.getColor() + this.tier.getDisplayName());
/*     */     }
/*     */ 
/* 138 */     for (String s : sockets())
/*     */     {
/* 140 */       DiabloDrops.getInstance().getItemAPI().addLore(this, s);
/*     */     }
/* 142 */     if (DiabloDrops.getInstance().getItemAPI().isLeather(getType()))
/*     */     {
/* 144 */       LeatherArmorMeta lam = (LeatherArmorMeta)getItemMeta();
/* 145 */       lam.setColor(Color.fromRGB(DiabloDrops.getInstance().getSingleRandom().nextInt(255), DiabloDrops.getInstance().getSingleRandom().nextInt(255), DiabloDrops.getInstance().getSingleRandom().nextInt(255)));
/*     */ 
/* 149 */       setItemMeta(lam);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Tier getTier()
/*     */   {
/* 158 */     return this.tier;
/*     */   }
/*     */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.items.SockettedItem
 * JD-Core Version:    0.6.2
 */