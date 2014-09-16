/*      */ package com.modcrafting.diablodrops.drops;
/*      */ 
/*      */ import com.modcrafting.diablodrops.DiabloDrops;
/*      */ import com.modcrafting.diablodrops.items.Drop;
/*      */ import com.modcrafting.diablodrops.items.IdentifyTome;
/*      */ import com.modcrafting.diablodrops.items.Socket;
import com.modcrafting.diablodrops.items.SocketGemUtil;
import com.modcrafting.diablodrops.items.SocketItem;
/*      */ import com.modcrafting.diablodrops.items.SockettedItem;
/*      */ import com.modcrafting.diablodrops.tier.Tier;

/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;

/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.ChatColor;
/*      */ import org.bukkit.Color;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.block.Block;
/*      */ import org.bukkit.block.Chest;
/*      */ import org.bukkit.enchantments.Enchantment;
/*      */ import org.bukkit.inventory.Inventory;
/*      */ import org.bukkit.inventory.ItemStack;
/*      */ import org.bukkit.inventory.meta.ItemMeta;
/*      */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*      */ 
/*      */ public class DropsAPI
/*      */ {
/*      */   private final DiabloDrops plugin;
/*      */ 
/*      */   public DropsAPI(DiabloDrops instance)
/*      */   {
/*   33 */     this.plugin = instance;
/*      */   }
/*      */ 
/*      */   public boolean canBeItem(Material material)
/*      */   {
/*   45 */     if ((this.plugin.getItemAPI().isArmor(material)) || (this.plugin.getItemAPI().isTool(material)))
/*      */     {
/*   47 */       return true;
/*   48 */     }return false;
/*      */   }
/*      */ 
/*      */   public boolean canBeTier(Material mat, Tier tier)
/*      */   {
/*   62 */     if ((tier.getMaterials() == null) || (tier.getMaterials().isEmpty()) || (tier.getMaterials().contains(mat)))
/*      */     {
/*   64 */       return true;
/*   65 */     }return false;
/*      */   }
/*      */ 
/*      */   public ChatColor colorPicker()
/*      */   {
/*   75 */     return this.plugin.getSettings().getSocketColors()[this.plugin.getSingleRandom().nextInt(this.plugin.getSettings().getSocketColors().length)];
/*      */   }
/*      */ 
/*      */   public boolean containsIgnoreCase(List<String> l, String s)
/*      */   {
/*   90 */     Iterator<String> it = l.iterator();
/*   91 */     while (it.hasNext())
/*   92 */       if (((String)it.next()).equalsIgnoreCase(s))
/*   93 */         return true;
/*   94 */     return false;
/*      */   }
/*      */ 
/*      */   public short damageItemStack(Material material)
/*      */   {
/*  106 */     short dur = material.getMaxDurability();
/*      */     try
/*      */     {
/*  109 */       int newDur = this.plugin.getSingleRandom().nextInt(dur);
/*  110 */       return (short)newDur;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  114 */       dur = 0;
/*      */     }
/*  116 */     return dur;
/*      */   }
/*      */ 
/*      */   public Material dropPicker()
/*      */   {
/*  126 */     int next = this.plugin.getSingleRandom().nextInt(10);
/*  127 */     switch (next)
/*      */     {
/*      */     case 1:
/*  130 */       return this.plugin.getItemAPI().getHelmet();
/*      */     case 2:
/*  132 */       return this.plugin.getItemAPI().getChestPlate();
/*      */     case 3:
/*  134 */       return this.plugin.getItemAPI().getLeggings();
/*      */     case 4:
/*  136 */       return this.plugin.getItemAPI().getBoots();
/*      */     case 5:
/*  138 */       return this.plugin.getItemAPI().getHoe();
/*      */     case 6:
/*  140 */       return this.plugin.getItemAPI().getPickaxe();
/*      */     case 7:
/*  142 */       return this.plugin.getItemAPI().getAxe();
/*      */     case 8:
/*  144 */       return this.plugin.getItemAPI().getSpade();
/*      */     case 9:
/*  146 */       return Material.BOW;
/*      */     }
/*  148 */     return this.plugin.getItemAPI().getSword();
/*      */   }
/*      */ 
/*      */   public void fillChest(Block block)
/*      */   {
/*      */     try
/*      */     {
/*  163 */       if (!(block.getState() instanceof Chest))
/*  164 */         return;
/*  165 */       Chest chestB = (Chest)block.getState();
/*  166 */       Inventory chest = chestB.getBlockInventory();
/*  167 */       for (int i = 0; i < this.plugin.getSingleRandom().nextInt(chest.getSize()); 
/*  168 */         i++)
/*      */       {
/*  170 */         ItemStack cis = getItem();
/*  171 */         while (cis == null)
/*      */         {
/*  173 */           cis = getItem();
/*      */         }
/*  175 */         chest.setItem(i, cis);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void fillChest(Block block, int size)
/*      */   {
/*      */     try
/*      */     {
/*  195 */       if (!(block.getState() instanceof Chest))
/*  196 */         return;
/*  197 */       Chest chestB = (Chest)block.getState();
/*  198 */       Inventory chest = chestB.getBlockInventory();
/*  199 */       for (int i = 0; i < size; i++)
/*      */       {
/*  201 */         ItemStack cis = getItem();
/*  202 */         while (cis == null)
/*      */         {
/*  204 */           cis = getItem();
/*      */         }
/*  206 */         chest.setItem(i, cis);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public ItemStack getCustomItem(String name)
/*      */   {
/*  223 */     for (ItemStack is : this.plugin.custom)
/*      */     {
/*  225 */       if (ChatColor.stripColor(this.plugin.getItemAPI().getName(is)).equals(name))
/*      */       {
/*  228 */         return is;
/*      */       }
/*      */     }
/*  231 */     return null;
/*      */   }
/*      */ 
/*      */   public List<Enchantment> getEnchantStack(ItemStack ci)
/*      */   {
/*  243 */     List<Enchantment> set = new ArrayList<Enchantment>();
/*  244 */     for (Enchantment e : Enchantment.values())
/*  245 */       if (e.canEnchantItem(ci))
/*      */       {
/*  247 */         set.add(e);
/*      */       }
/*  249 */     return set;
/*      */   }
/*      */ 
/*      */   public ItemStack getIdItem(Material mat, String name)
/*      */   {
/*  263 */     while (mat == null)
/*      */     {
/*  265 */       mat = dropPicker();
/*      */     }
/*  267 */     Material material = mat;
/*  268 */     ItemStack ci = null;
/*  269 */     Tier tier = getTier();
/*  270 */     while (tier == null)
/*      */     {
/*  272 */       tier = getTier();
/*      */     }
/*  274 */     if ((tier.getMaterials().size() > 0) && (!tier.getMaterials().contains(material)))
/*      */     {
/*  277 */       material = (Material)tier.getMaterials().get(this.plugin.getSingleRandom().nextInt(tier.getMaterials().size()));
/*      */     }
/*      */ 
/*  281 */     int e = tier.getAmount().intValue();
/*  282 */     int l = tier.getLevels().intValue();
/*  283 */     short damage = 0;
/*  284 */     if (this.plugin.getConfig().getBoolean("DropFix.Damage", true))
/*      */     {
/*  286 */       damage = damageItemStack(mat);
/*      */     }
/*  288 */     if ((this.plugin.getConfig().getBoolean("Display.TierName", true)) && (!tier.getColor().equals(ChatColor.MAGIC)))
/*      */     {
/*  291 */       ci = new Drop(material, tier.getColor(), ChatColor.stripColor(name(mat)), damage, new String[] { tier.getColor() + "Tier: " + tier.getDisplayName() });
/*      */     }
/*  295 */     else if ((this.plugin.getConfig().getBoolean("Display.TierName", true)) && (tier.getColor().equals(ChatColor.MAGIC)))
/*      */     {
/*  298 */       ci = new Drop(material, tier.getColor(), ChatColor.stripColor(name(mat)), damage, new String[] { ChatColor.WHITE + "Tier: " + tier.getDisplayName() });
/*      */     }
/*      */     else
/*      */     {
/*  304 */       ci = new Drop(material, tier.getColor(), ChatColor.stripColor(name(mat)), damage);
/*      */     }
/*      */ 
/*  307 */     if (tier.getColor().equals(ChatColor.MAGIC))
/*  308 */       return ci;
/*  309 */     List<Enchantment> eStack = Arrays.asList(Enchantment.values());
/*  310 */     boolean safe = this.plugin.getConfig().getBoolean("SafeEnchant.Enabled", true);
/*      */ 
/*  312 */     if (safe)
/*      */     {
/*  314 */       eStack = getEnchantStack(ci);
/*      */     }
/*  316 */     for (; e > 0; e--)
/*      */     {
/*  318 */       int lvl = this.plugin.getSingleRandom().nextInt(l + 1);
/*  319 */       int size = eStack.size();
/*  320 */       if (size >= 1)
/*      */       {
/*  324 */         Enchantment ench = (Enchantment)eStack.get(this.plugin.getSingleRandom().nextInt(size));
/*      */ 
/*  326 */         if ((!ci.containsEnchantment(ench)) && 
/*  327 */           (lvl != 0) && (ench != null) && (!tier.getColor().equals(ChatColor.MAGIC)))
/*      */         {
/*  329 */           if (safe)
/*      */           {
/*  331 */             if ((lvl >= ench.getStartLevel()) && (lvl <= ench.getMaxLevel()))
/*      */             {
/*      */               try
/*      */               {
/*  336 */                 ci.addEnchantment(ench, lvl);
/*      */               }
/*      */               catch (Exception e1)
/*      */               {
/*  340 */                 if (this.plugin.getDebug())
/*      */                 {
/*  342 */                   this.plugin.log.warning(e1.getMessage());
/*      */                 }
/*  344 */                 e++;
/*      */               }
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  350 */             ci.addUnsafeEnchantment(ench, lvl);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     ItemMeta tool;
/*  354 */     if (ci.hasItemMeta())
/*  355 */       tool = ci.getItemMeta();
/*      */     else
/*  357 */       tool = Bukkit.getItemFactory().getItemMeta(ci.getType());
/*  358 */     tool.setLore(tier.getLore());
/*  359 */     List<String> list = new ArrayList<String>();
/*  360 */     if ((this.plugin.getConfig().getBoolean("Lore.Enabled", true)) && (this.plugin.getSingleRandom().nextInt(10000) <= this.plugin.getSettings().getLoreChance()))
/*      */     {
/*  364 */       for (int i = 0; i < this.plugin.getConfig().getInt("Lore.EnhanceAmount", 2); 
/*  365 */         i++) {
/*  366 */         if (this.plugin.getItemAPI().isArmor(mat))
/*      */         {
/*  368 */           list.add(this.plugin.defenselore.get(this.plugin.getSingleRandom().nextInt(this.plugin.defenselore.size())));
/*      */         }
/*  372 */         else if (this.plugin.getItemAPI().isTool(mat))
/*      */         {
/*  374 */           list.add(this.plugin.offenselore.get(this.plugin.getSingleRandom().nextInt(this.plugin.offenselore.size())));
/*      */         }
/*      */       }
/*      */     }
/*  378 */     tool.setLore(list);
/*  379 */     ci.setItemMeta(tool);
/*  380 */     return ci;
/*      */   }
/*      */ 
/*      */   public ItemStack getItem()
/*      */   {
/*  390 */     if ((this.plugin.getSingleRandom().nextBoolean()) && (this.plugin.getConfig().getBoolean("IdentifyTome.Enabled", true)) && (this.plugin.getSingleRandom().nextInt(10000) <= this.plugin.getSettings().getTomeChance()))
/*      */     {
/*  394 */       return new IdentifyTome();
/*  395 */     }if ((this.plugin.getSingleRandom().nextBoolean()) && (this.plugin.getConfig().getBoolean("SocketItem.Enabled", true)) && (this.plugin.getSingleRandom().nextInt(10000) <= this.plugin.getSettings().getSocketChance()))
/*      */     {
/*  400 */       //This should really return a socket. This is temporary
/*  402 */       return new SocketItem(SocketGemUtil.getRandomSocketGemMaterial(), SocketGemUtil.getRandomSocketGemWithChance());
/*      */     }
/*      */ 
/*  405 */     if ((this.plugin.getConfig().getBoolean("SockettedItem.Enabled", true)) && (this.plugin.getSingleRandom().nextInt(10000) <= this.plugin.getSettings().getSocketedChance()))
/*      */     {
/*  408 */       return new SocketItem(dropPicker(), SocketGemUtil.getRandomSocketGemWithChance());
/*  409 */     }if ((this.plugin.getSingleRandom().nextBoolean()) && (this.plugin.getConfig().getBoolean("Custom.Enabled", true)) && (this.plugin.getSingleRandom().nextInt(10000) <= this.plugin.getSettings().getCustomChance()) && (this.plugin.custom.size() > 0))
/*      */     {
/*  414 */       return (ItemStack)this.plugin.custom.get(this.plugin.getSingleRandom().nextInt(this.plugin.custom.size()));
/*      */     }
/*  416 */     return getItem(dropPicker());
/*      */   }
/*      */ 
/*      */   public ItemStack getItem(ItemStack oldTool)
/*      */   {
/*  428 */     ItemStack tool = oldTool;
/*  429 */     short oldDam = tool.getDurability();
/*  430 */     tool = new ItemStack(tool.getType());
/*  431 */     tool.setDurability(oldDam);
/*  432 */     Tier tier = getTier();
/*  433 */     while ((tier == null) || (tier.getColor().equals(ChatColor.MAGIC)))
/*      */     {
/*  435 */       tier = getTier();
/*      */     }
/*  437 */     int e = tier.getAmount().intValue();
/*  438 */     int l = tier.getLevels().intValue();
/*  439 */     List<String> list = new ArrayList<String>();
/*  440 */     if (this.plugin.getSettings().isColorBlindCompat())
/*      */     {
/*  442 */       list.add("Material: " + getPrettyMaterialName(tool.getType()));
/*      */     }
/*  444 */     if (this.plugin.getConfig().getBoolean("Display.TierName", true))
/*      */     {
/*  446 */       list.add(tier.getColor() + "Tier: " + tier.getDisplayName());
/*      */     }
/*  448 */     for (String s : tier.getLore())
/*  449 */       if (s != null)
/*      */       {
/*  451 */         list.add(s);
/*      */       }
/*  453 */     List<Enchantment> eStack = Arrays.asList(Enchantment.values());
/*  454 */     boolean safe = this.plugin.getConfig().getBoolean("SafeEnchant.Enabled", true);
/*      */ 
/*  456 */     if (safe)
/*      */     {
/*  458 */       eStack = getEnchantStack(tool);
/*      */     }
/*  460 */     for (; e > 0; e--)
/*      */     {
/*  462 */       int lvl = this.plugin.getSingleRandom().nextInt(l + 1);
/*  463 */       int size = eStack.size();
/*  464 */       if (size >= 1)
/*      */       {
/*  468 */         Enchantment ench = (Enchantment)eStack.get(this.plugin.getSingleRandom().nextInt(size));
/*      */ 
/*  470 */         if ((!tool.containsEnchantment(ench)) && 
/*  471 */           (lvl != 0) && (ench != null) && (!tier.getColor().equals(ChatColor.MAGIC)))
/*      */         {
/*  473 */           if (safe)
/*      */           {
/*  475 */             if ((lvl >= ench.getStartLevel()) && (lvl <= ench.getMaxLevel()))
/*      */             {
/*      */               try
/*      */               {
/*  480 */                 tool.addEnchantment(ench, lvl);
/*      */               }
/*      */               catch (Exception e1)
/*      */               {
/*  484 */                 if (this.plugin.getDebug())
/*      */                 {
/*  486 */                   this.plugin.log.warning(e1.getMessage());
/*      */                 }
/*  488 */                 e++;
/*      */               }
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  494 */             tool.addUnsafeEnchantment(ench, lvl);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     ItemMeta meta;
/*  498 */     if (tool.hasItemMeta())
/*  499 */       meta = tool.getItemMeta();
/*      */     else
/*  501 */       meta = Bukkit.getItemFactory().getItemMeta(tool.getType());
/*  502 */     meta.setDisplayName(tier.getColor() + name(tool.getType()));
/*  503 */     if ((this.plugin.getConfig().getBoolean("Lore.Enabled", true)) && (this.plugin.getSingleRandom().nextInt(100) <= this.plugin.getSettings().getLoreChance()) && (!tier.getColor().equals(ChatColor.MAGIC)))
/*      */     {
/*  508 */       for (int i = 0; i < this.plugin.getConfig().getInt("Lore.EnhanceAmount", 2); 
/*  509 */         i++) {
/*  510 */         if (this.plugin.getItemAPI().isArmor(tool.getType()))
/*      */         {
/*  512 */           list.add(colorPicker() + (String)this.plugin.defenselore.get(this.plugin.getSingleRandom().nextInt(this.plugin.defenselore.size())));
/*      */         }
/*  517 */         else if (this.plugin.getItemAPI().isTool(tool.getType()))
/*      */         {
/*  519 */           list.add(colorPicker() + (String)this.plugin.offenselore.get(this.plugin.getSingleRandom().nextInt(this.plugin.offenselore.size())));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  524 */     meta.setLore(list);
/*  525 */     tool.setItemMeta(meta);
/*  526 */     return tool;
/*      */   }
/*      */ 
/*      */   public ItemStack getItem(Material mat)
/*      */   {
/*  538 */     while (mat == null)
/*      */     {
/*  540 */       mat = dropPicker();
/*      */     }
/*  542 */     ItemStack ci = null;
/*  543 */     Tier tier = getTier();
/*  544 */     while (tier == null)
/*      */     {
/*  546 */       tier = getTier();
/*      */     }
/*  548 */     if ((tier.getMaterials().size() > 0) && (!tier.getMaterials().contains(mat)))
/*      */     {
/*  551 */       mat = (Material)tier.getMaterials().get(this.plugin.getSingleRandom().nextInt(tier.getMaterials().size()));
/*      */     }
/*      */ 
/*  555 */     int e = tier.getAmount().intValue();
/*  556 */     int l = tier.getLevels().intValue();
/*  557 */     short damage = 0;
/*  558 */     if (this.plugin.getConfig().getBoolean("DropFix.Damage", true))
/*      */     {
/*  560 */       damage = damageItemStack(mat);
/*      */     }
/*  562 */     List<String> startList = new ArrayList<String>();
/*  563 */     if (this.plugin.getSettings().isColorBlindCompat())
/*      */     {
/*  565 */       startList.add("Material: " + getPrettyMaterialName(mat));
/*      */     }
/*  567 */     if ((this.plugin.getConfig().getBoolean("Display.TierName", true)) && (!tier.getColor().equals(ChatColor.MAGIC)))
/*      */     {
/*  570 */       startList.add(tier.getColor() + "Tier: " + tier.getDisplayName());
/*      */     }
/*  572 */     else if ((this.plugin.getConfig().getBoolean("Display.TierName", true)) && (tier.getColor().equals(ChatColor.MAGIC)))
/*      */     {
/*  575 */       startList.add(ChatColor.WHITE + "Tier: " + tier.getDisplayName());
/*      */     }
/*  577 */     ci = new Drop(mat, tier.getColor(), ChatColor.stripColor(name(mat)), damage, (String[])startList.toArray(new String[0]));
/*      */ 
/*  579 */     if (tier.getColor().equals(ChatColor.MAGIC))
/*  580 */       return ci;
/*  581 */     ItemStack tool = new ItemStack(ci);
/*  582 */     List<Enchantment> eStack = Arrays.asList(Enchantment.values());
/*  583 */     List<String> list = new ArrayList<String>();
/*  584 */     for (String s : tier.getLore()) {
/*  585 */       if (s != null)
/*      */       {
/*  587 */         list.add(s);
/*      */       }
/*      */     }
/*  590 */     boolean safe = this.plugin.getConfig().getBoolean("SafeEnchant.Enabled", true);
/*      */ 
/*  592 */     if (safe)
/*      */     {
/*  594 */       eStack = getEnchantStack(tool);
/*      */     }
/*  596 */     for (; e > 0; e--)
/*      */     {
/*  598 */       int lvl = this.plugin.getSingleRandom().nextInt(l + 1);
/*  599 */       int size = eStack.size();
/*  600 */       if (size >= 1)
/*      */       {
/*  604 */         Enchantment ench = (Enchantment)eStack.get(this.plugin.getSingleRandom().nextInt(size));
/*      */ 
/*  606 */         if ((!tool.containsEnchantment(ench)) && 
/*  607 */           (lvl != 0) && (ench != null) && (!tier.getColor().equals(ChatColor.MAGIC)))
/*      */         {
/*  609 */           if (safe)
/*      */           {
/*  611 */             if ((lvl >= ench.getStartLevel()) && (lvl <= ench.getMaxLevel()))
/*      */             {
/*      */               try
/*      */               {
/*  616 */                 tool.addEnchantment(ench, lvl);
/*      */               }
/*      */               catch (Exception e1)
/*      */               {
/*  620 */                 if (this.plugin.getDebug())
/*      */                 {
/*  622 */                   this.plugin.log.warning(e1.getMessage());
/*      */                 }
/*  624 */                 e++;
/*      */               }
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  630 */             tool.addUnsafeEnchantment(ench, lvl);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     ItemMeta meta;
/*  634 */     if (tool.hasItemMeta())
/*  635 */       meta = tool.getItemMeta();
/*      */     else
/*  637 */       meta = Bukkit.getItemFactory().getItemMeta(tool.getType());
/*  638 */     if ((this.plugin.getConfig().getBoolean("Lore.Enabled", true)) && (this.plugin.getSingleRandom().nextInt(10000) <= this.plugin.getSettings().getLoreChance()) && (!tier.getColor().equals(ChatColor.MAGIC)))
/*      */     {
/*  643 */       for (int i = 0; i < this.plugin.getConfig().getInt("Lore.EnhanceAmount", 2); 
/*  644 */         i++) {
/*  645 */         if (this.plugin.getItemAPI().isArmor(mat))
/*      */         {
/*  647 */           list.add(colorPicker() + (String)this.plugin.defenselore.get(this.plugin.getSingleRandom().nextInt(this.plugin.defenselore.size())));
/*      */         }
/*  651 */         else if (this.plugin.getItemAPI().isTool(mat))
/*      */         {
/*  653 */           list.add(colorPicker() + (String)this.plugin.offenselore.get(this.plugin.getSingleRandom().nextInt(this.plugin.offenselore.size())));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  658 */     meta.setLore(list);
/*  659 */     tool.setItemMeta(meta);
/*  660 */     if (this.plugin.getItemAPI().isLeather(tool.getType()))
/*      */     {
/*  662 */       LeatherArmorMeta lam = (LeatherArmorMeta)tool.getItemMeta();
/*  663 */       lam.setColor(Color.fromRGB(this.plugin.getSingleRandom().nextInt(255), this.plugin.getSingleRandom().nextInt(255), this.plugin.getSingleRandom().nextInt(255)));
/*      */ 
/*  666 */       tool.setItemMeta(lam);
/*      */     }
/*  668 */     return tool;
/*      */   }
/*      */ 
/*      */   public ItemStack getItem(Material passMat, Tier passTier)
/*      */   {
/*  682 */     Material mat = passMat;
/*  683 */     Tier tier = passTier;
/*  684 */     while (mat == null)
/*      */     {
/*  686 */       mat = dropPicker();
/*      */     }
/*  688 */     ItemStack ci = null;
/*  689 */     while (tier == null)
/*      */     {
/*  691 */       tier = getTier();
/*      */     }
/*  693 */     if ((tier.getMaterials().size() > 0) && (!tier.getMaterials().contains(mat)))
/*      */     {
/*  696 */       mat = (Material)tier.getMaterials().get(this.plugin.getSingleRandom().nextInt(tier.getMaterials().size()));
/*      */     }
/*      */ 
/*  700 */     int e = tier.getAmount().intValue();
/*  701 */     int l = tier.getLevels().intValue();
/*  702 */     short damage = 0;
/*  703 */     if (this.plugin.getConfig().getBoolean("DropFix.Damage", true))
/*      */     {
/*  705 */       damage = damageItemStack(mat);
/*      */     }
/*  707 */     List<String> startList = new ArrayList<String>();
/*  708 */     if (this.plugin.getSettings().isColorBlindCompat())
/*      */     {
/*  710 */       startList.add("Material: " + getPrettyMaterialName(mat));
/*      */     }
/*  712 */     if ((this.plugin.getConfig().getBoolean("Display.TierName", true)) && (!tier.getColor().equals(ChatColor.MAGIC)))
/*      */     {
/*  715 */       startList.add(tier.getColor() + "Tier: " + tier.getDisplayName());
/*      */     }
/*  717 */     else if ((this.plugin.getConfig().getBoolean("Display.TierName", true)) && (tier.getColor().equals(ChatColor.MAGIC)))
/*      */     {
/*  720 */       startList.add(ChatColor.WHITE + "Tier: " + tier.getDisplayName());
/*      */     }
/*  722 */     ci = new Drop(mat, tier.getColor(), ChatColor.stripColor(name(mat)), damage, (String[])startList.toArray(new String[0]));
/*      */ 
/*  724 */     if (tier.getColor().equals(ChatColor.MAGIC))
/*  725 */       return ci;
/*  726 */     ItemStack tool = new ItemStack(ci);
/*  727 */     List<Enchantment> eStack = Arrays.asList(Enchantment.values());
/*  728 */     List<String> list = new ArrayList<String>();
/*  729 */     for (String s : tier.getLore())
/*  730 */       if (s != null)
/*      */       {
/*  732 */         list.add(s);
/*      */       }
/*  734 */     boolean safe = this.plugin.getConfig().getBoolean("SafeEnchant.Enabled", true);
/*      */ 
/*  736 */     if (safe)
/*      */     {
/*  738 */       eStack = getEnchantStack(tool);
/*      */     }
/*  740 */     for (; e > 0; e--)
/*      */     {
/*  742 */       int lvl = this.plugin.getSingleRandom().nextInt(l + 1);
/*  743 */       int size = eStack.size();
/*  744 */       if (size >= 1)
/*      */       {
/*  748 */         Enchantment ench = (Enchantment)eStack.get(this.plugin.getSingleRandom().nextInt(size));
/*      */ 
/*  750 */         if ((!tool.containsEnchantment(ench)) && 
/*  751 */           (lvl != 0) && (ench != null) && (!tier.getColor().equals(ChatColor.MAGIC)))
/*      */         {
/*  753 */           if (safe)
/*      */           {
/*  755 */             if ((lvl >= ench.getStartLevel()) && (lvl <= ench.getMaxLevel()))
/*      */             {
/*      */               try
/*      */               {
/*  760 */                 tool.addEnchantment(ench, lvl);
/*      */               }
/*      */               catch (Exception e1)
/*      */               {
/*  764 */                 if (this.plugin.getDebug())
/*      */                 {
/*  766 */                   this.plugin.log.warning(e1.getMessage());
/*      */                 }
/*  768 */                 e++;
/*      */               }
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  774 */             tool.addUnsafeEnchantment(ench, lvl);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     ItemMeta meta;
/*  778 */     if (tool.hasItemMeta())
/*  779 */       meta = tool.getItemMeta();
/*      */     else
/*  781 */       meta = Bukkit.getItemFactory().getItemMeta(tool.getType());
/*  782 */     if ((this.plugin.getConfig().getBoolean("Lore.Enabled", true)) && (this.plugin.getSingleRandom().nextInt(10000) <= this.plugin.getSettings().getLoreChance()) && (!tier.getColor().equals(ChatColor.MAGIC)))
/*      */     {
/*  787 */       for (int i = 0; i < this.plugin.getConfig().getInt("Lore.EnhanceAmount", 2); 
/*  788 */         i++) {
/*  789 */         if (this.plugin.getItemAPI().isArmor(mat))
/*      */         {
/*  791 */           list.add(colorPicker() + (String)this.plugin.defenselore.get(this.plugin.getSingleRandom().nextInt(this.plugin.defenselore.size())));
/*      */         }
/*  796 */         else if (this.plugin.getItemAPI().isTool(mat))
/*      */         {
/*  798 */           list.add(colorPicker() + (String)this.plugin.offenselore.get(this.plugin.getSingleRandom().nextInt(this.plugin.offenselore.size())));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  803 */     meta.setLore(list);
/*  804 */     tool.setItemMeta(meta);
/*  805 */     if (this.plugin.getItemAPI().isLeather(tool.getType()))
/*      */     {
/*  807 */       LeatherArmorMeta lam = (LeatherArmorMeta)tool.getItemMeta();
/*  808 */       lam.setColor(Color.fromRGB(this.plugin.getSingleRandom().nextInt(255), this.plugin.getSingleRandom().nextInt(255), this.plugin.getSingleRandom().nextInt(255)));
/*      */ 
/*  811 */       tool.setItemMeta(lam);
/*      */     }
/*  813 */     return tool;
/*      */   }
/*      */ 
/*      */   public ItemStack getItem(Tier tier)
/*      */   {
/*  825 */     Material mat = dropPicker();
/*  826 */     while (mat == null)
/*      */     {
/*  828 */       mat = dropPicker();
/*      */     }
/*  830 */     while (tier == null)
/*      */     {
/*  832 */       tier = getTier();
/*      */     }
/*  834 */     ItemStack ci = null;
/*  835 */     if ((tier.getMaterials().size() > 0) && (!tier.getMaterials().contains(mat)))
/*      */     {
/*  838 */       mat = (Material)tier.getMaterials().get(this.plugin.getSingleRandom().nextInt(tier.getMaterials().size()));
/*      */     }
/*      */ 
/*  842 */     int e = tier.getAmount().intValue();
/*  843 */     int l = tier.getLevels().intValue();
/*  844 */     short damage = 0;
/*  845 */     if (this.plugin.getConfig().getBoolean("DropFix.Damage", true))
/*      */     {
/*  847 */       damage = damageItemStack(mat);
/*      */     }
/*  849 */     List<String> startList = new ArrayList<String>();
/*  850 */     if (this.plugin.getSettings().isColorBlindCompat())
/*      */     {
/*  852 */       startList.add("Material: " + getPrettyMaterialName(mat));
/*      */     }
/*  854 */     if ((this.plugin.getConfig().getBoolean("Display.TierName", true)) && (!tier.getColor().equals(ChatColor.MAGIC)))
/*      */     {
/*  857 */       startList.add(tier.getColor() + "Tier: " + tier.getDisplayName());
/*      */     }
/*  859 */     else if ((this.plugin.getConfig().getBoolean("Display.TierName", true)) && (tier.getColor().equals(ChatColor.MAGIC)))
/*      */     {
/*  862 */       startList.add(ChatColor.WHITE + "Tier: " + tier.getDisplayName());
/*      */     }
/*  864 */     ci = new Drop(mat, tier.getColor(), ChatColor.stripColor(name(mat)), damage, (String[])startList.toArray(new String[0]));
/*      */ 
/*  866 */     if (tier.getColor().equals(ChatColor.MAGIC))
/*  867 */       return ci;
/*  868 */     ItemStack tool = new ItemStack(ci);
/*  869 */     List<String> list = new ArrayList<String>();
/*  870 */     for (String s : tier.getLore())
/*  871 */       if (s != null)
/*      */       {
/*  873 */         list.add(s);
/*      */       }
/*  875 */     List<Enchantment> eStack = Arrays.asList(Enchantment.values());
/*  876 */     boolean safe = this.plugin.getConfig().getBoolean("SafeEnchant.Enabled", true);
/*      */ 
/*  878 */     if (safe)
/*      */     {
/*  880 */       eStack = getEnchantStack(ci);
/*      */     }
/*  882 */     for (; e > 0; e--)
/*      */     {
/*  884 */       int lvl = this.plugin.getSingleRandom().nextInt(l + 1);
/*  885 */       int size = eStack.size();
/*  886 */       if (size >= 1)
/*      */       {
/*  890 */         Enchantment ench = (Enchantment)eStack.get(this.plugin.getSingleRandom().nextInt(size));
/*      */ 
/*  892 */         if ((!tool.containsEnchantment(ench)) && 
/*  893 */           (lvl != 0) && (ench != null) && (!tier.getColor().equals(ChatColor.MAGIC)))
/*      */         {
/*  895 */           if (safe)
/*      */           {
/*  897 */             if ((lvl >= ench.getStartLevel()) && (lvl <= ench.getMaxLevel()))
/*      */             {
/*      */               try
/*      */               {
/*  902 */                 tool.addEnchantment(ench, lvl);
/*      */               }
/*      */               catch (Exception e1)
/*      */               {
/*  906 */                 if (this.plugin.getDebug())
/*      */                 {
/*  908 */                   this.plugin.log.warning(e1.getMessage());
/*      */                 }
/*  910 */                 e++;
/*      */               }
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  916 */             tool.addUnsafeEnchantment(ench, lvl);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  919 */     ItemMeta meta = tool.getItemMeta();
/*  920 */     if ((this.plugin.getConfig().getBoolean("Lore.Enabled", true)) && (this.plugin.getSingleRandom().nextInt(10000) <= this.plugin.getSettings().getLoreChance()) && (!tier.getColor().equals(ChatColor.MAGIC)))
/*      */     {
/*  925 */       for (int i = 0; i < this.plugin.getConfig().getInt("Lore.EnhanceAmount", 2); 
/*  926 */         i++) {
/*  927 */         if (this.plugin.getItemAPI().isArmor(tool.getType()))
/*      */         {
/*  929 */           list.add(this.plugin.defenselore.get(this.plugin.getSingleRandom().nextInt(this.plugin.defenselore.size())));
/*      */         }
/*  933 */         else if (this.plugin.getItemAPI().isTool(tool.getType()))
/*      */         {
/*  935 */           list.add(this.plugin.offenselore.get(this.plugin.getSingleRandom().nextInt(this.plugin.offenselore.size())));
/*      */         }
/*      */       }
/*      */     }
/*  939 */     meta.setLore(list);
/*  940 */     tool.setItemMeta(meta);
/*  941 */     if (this.plugin.getItemAPI().isLeather(tool.getType()))
/*      */     {
/*  943 */       LeatherArmorMeta lam = (LeatherArmorMeta)tool.getItemMeta();
/*  944 */       lam.setColor(Color.fromRGB(this.plugin.getSingleRandom().nextInt(255), this.plugin.getSingleRandom().nextInt(255), this.plugin.getSingleRandom().nextInt(255)));
/*      */ 
/*  947 */       tool.setItemMeta(lam);
/*      */     }
/*  949 */     return tool;
/*      */   }
/*      */ 
/*      */   public String getPrettyMaterialName(Material material)
/*      */   {
/*  961 */     String prettyMaterialName = "";
/*  962 */     String matName = material.name();
/*  963 */     String[] split = matName.split("_");
/*  964 */     for (String s : split)
/*      */     {
/*  966 */       prettyMaterialName = prettyMaterialName + s.substring(0, 1).toUpperCase() + s.substring(1, s.length()).toLowerCase() + " ";
/*      */     }
/*      */ 
/*  970 */     return prettyMaterialName;
/*      */   }
/*      */ 
/*      */   public Tier getTier()
/*      */   {
/*  980 */     for (Tier tier : this.plugin.tiers)
/*  981 */       if (this.plugin.getSingleRandom().nextInt(10000) <= tier.getChance().intValue())
/*  982 */         return tier;
/*  983 */     return null;
/*      */   }
/*      */ 
/*      */   public Tier getTier(ItemStack item)
/*      */   {
/*  995 */     if ((!item.hasItemMeta()) || (!item.getItemMeta().hasLore()))
/*  996 */       return null;
/*  997 */     for (String s : item.getItemMeta().getLore())
/*      */     {
/*  999 */       Tier tier = getTier(s);
/* 1000 */       if (tier != null)
/* 1001 */         return tier;
/*      */     }
/* 1003 */     return null;
/*      */   }
/*      */ 
/*      */   public Tier getTier(String name)
/*      */   {
/* 1015 */     for (Tier tier : this.plugin.tiers)
/* 1016 */       if ((tier.getDisplayName().equalsIgnoreCase(name)) || (tier.getName().equalsIgnoreCase(name)))
/*      */       {
/* 1018 */         return tier;
/*      */       }
/* 1019 */     return null;
/*      */   }
/*      */ 
/*      */   public boolean matchesTier(String type)
/*      */   {
/* 1031 */     for (Tier tier : this.plugin.tiers)
/* 1032 */       if ((tier.getDisplayName().equalsIgnoreCase(type)) || (tier.getName().equalsIgnoreCase(type)))
/*      */       {
/* 1034 */         return true;
/*      */       }
/* 1035 */     return false;
/*      */   }
/*      */ 
/*      */   public String name(Material material)
/*      */   {
/* 1047 */     String template = this.plugin.getConfig().getString("Display.ItemNameFormat", "%randprefix% %randsuffix%");
/*      */ 
/* 1049 */     String prefix = "";
/* 1050 */     String suffix = "";
/* 1051 */     String matName = material.name();
/* 1052 */     if (this.plugin.hmprefix.containsKey(material))
/*      */     {
/* 1054 */       List<String> l = this.plugin.hmprefix.get(material);
/* 1055 */       prefix = l.get(this.plugin.getSingleRandom().nextInt(l.size()));
/*      */     }
/* 1057 */     if (this.plugin.hmsuffix.containsKey(material))
/*      */     {
/* 1059 */       List<String> l = this.plugin.hmsuffix.get(material);
/* 1060 */       suffix = l.get(this.plugin.getSingleRandom().nextInt(l.size()));
/*      */     }
/* 1062 */     boolean t = this.plugin.getConfig().getBoolean("Display.ItemMaterialExtras", false);
/*      */ 
/* 1064 */     if ((prefix.length() < 1) || (!t))
/*      */     {
/* 1066 */       prefix = (String)this.plugin.prefix.get(this.plugin.getSingleRandom().nextInt(this.plugin.prefix.size()));
/*      */     }
/*      */ 
/* 1069 */     if ((suffix.length() < 1) || (!t))
/*      */     {
/* 1071 */       suffix = (String)this.plugin.suffix.get(this.plugin.getSingleRandom().nextInt(this.plugin.suffix.size()));
/*      */     }
/*      */ 
/* 1074 */     if (template == null)
/* 1075 */       return null;
/* 1076 */     return template.replace("%randprefix%", prefix).replace("%randsuffix%", suffix).replace("%matname%", matName);
/*      */   }
/*      */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.drops.DropsAPI
 * JD-Core Version:    0.6.2
 */