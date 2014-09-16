/*     */ package com.modcrafting.diablodrops.items;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ public class ItemAPI
/*     */ {
/*  15 */   public Random gen = new Random();
/*     */ 
/*     */   public ItemStack addLore(ItemStack itemStack, String lore)
/*     */   {
/*  28 */     ItemStack ret = itemStack;
/*  29 */     List<String> loreList = getLore(ret);
/*  30 */     loreList.add(lore);
/*  31 */     return setLore(ret, loreList);
/*     */   }
/*     */ 
/*     */   public Material[] allItems()
/*     */   {
/*  41 */     return new Material[] { Material.DIAMOND_SWORD, Material.DIAMOND_PICKAXE, Material.DIAMOND_SPADE, Material.DIAMOND_AXE, Material.DIAMOND_HOE, Material.IRON_SWORD, Material.IRON_PICKAXE, Material.IRON_SPADE, Material.IRON_AXE, Material.IRON_HOE, Material.GOLD_SWORD, Material.GOLD_PICKAXE, Material.GOLD_SPADE, Material.GOLD_AXE, Material.GOLD_HOE, Material.STONE_SWORD, Material.STONE_PICKAXE, Material.STONE_SPADE, Material.STONE_AXE, Material.STONE_HOE, Material.WOOD_SWORD, Material.WOOD_PICKAXE, Material.WOOD_SPADE, Material.WOOD_AXE, Material.WOOD_HOE, Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS, Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS, Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS, Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS, Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS, Material.BOW };
/*     */   }
/*     */ 
/*     */   public Material[] armorPicker()
/*     */   {
/*  72 */     switch (this.gen.nextInt(5))
/*     */     {
/*     */     case 0:
/*  75 */       return diamondArmor();
/*     */     case 1:
/*  77 */       return ironArmor();
/*     */     case 2:
/*  79 */       return chainmailArmor();
/*     */     case 3:
/*  81 */       return goldArmor();
/*     */     case 4:
/*  83 */       return leatherArmor();
/*     */     }
/*  85 */     return null;
/*     */   }
/*     */ 
/*     */   public Material[] chainmailArmor()
/*     */   {
/*  96 */     return new Material[] { Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS };
/*     */   }
/*     */ 
/*     */   public Material[] diamondArmor()
/*     */   {
/* 108 */     return new Material[] { Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS };
/*     */   }
/*     */ 
/*     */   public Material diamondWeapon(int num)
/*     */   {
/* 123 */     switch (num)
/*     */     {
/*     */     case 1:
/* 126 */       return Material.DIAMOND_SWORD;
/*     */     case 2:
/* 128 */       return Material.DIAMOND_PICKAXE;
/*     */     case 3:
/* 130 */       return Material.DIAMOND_SPADE;
/*     */     case 4:
/* 132 */       return Material.DIAMOND_AXE;
/*     */     case 5:
/* 134 */       return Material.DIAMOND_HOE;
/*     */     }
/* 136 */     return null;
/*     */   }
/*     */ 
/*     */   public Material dropPicker()
/*     */   {
/* 147 */     int next = this.gen.nextInt(10);
/* 148 */     switch (next)
/*     */     {
/*     */     case 1:
/* 151 */       return getHelmet();
/*     */     case 2:
/* 153 */       return getChestPlate();
/*     */     case 3:
/* 155 */       return getLeggings();
/*     */     case 4:
/* 157 */       return getBoots();
/*     */     case 5:
/* 159 */       return getHoe();
/*     */     case 6:
/* 161 */       return getPickaxe();
/*     */     case 7:
/* 163 */       return getAxe();
/*     */     case 8:
/* 165 */       return getSpade();
/*     */     case 9:
/* 167 */       return Material.BOW;
/*     */     }
/* 169 */     return getSword();
/*     */   }
/*     */ 
/*     */   public Material getAxe()
/*     */   {
/* 181 */     switch (this.gen.nextInt(5))
/*     */     {
/*     */     case 0:
/* 184 */       return Material.WOOD_AXE;
/*     */     case 1:
/* 186 */       return Material.STONE_AXE;
/*     */     case 2:
/* 188 */       return Material.GOLD_AXE;
/*     */     case 3:
/* 190 */       return Material.IRON_AXE;
/*     */     case 4:
/* 192 */       return Material.DIAMOND_AXE;
/*     */     }
/* 194 */     return null;
/*     */   }
/*     */ 
/*     */   public Material getBoots()
/*     */   {
/* 205 */     switch (this.gen.nextInt(5))
/*     */     {
/*     */     case 0:
/* 208 */       return Material.LEATHER_BOOTS;
/*     */     case 1:
/* 210 */       return Material.GOLD_BOOTS;
/*     */     case 2:
/* 212 */       return Material.CHAINMAIL_BOOTS;
/*     */     case 3:
/* 214 */       return Material.IRON_BOOTS;
/*     */     case 4:
/* 216 */       return Material.DIAMOND_BOOTS;
/*     */     }
/* 218 */     return null;
/*     */   }
/*     */ 
/*     */   public Material getChestPlate()
/*     */   {
/* 229 */     switch (this.gen.nextInt(5))
/*     */     {
/*     */     case 0:
/* 232 */       return Material.LEATHER_CHESTPLATE;
/*     */     case 1:
/* 234 */       return Material.GOLD_CHESTPLATE;
/*     */     case 2:
/* 236 */       return Material.CHAINMAIL_CHESTPLATE;
/*     */     case 3:
/* 238 */       return Material.IRON_CHESTPLATE;
/*     */     case 4:
/* 240 */       return Material.DIAMOND_CHESTPLATE;
/*     */     }
/* 242 */     return null;
/*     */   }
/*     */ 
/*     */   public Material getHelmet()
/*     */   {
/* 253 */     switch (this.gen.nextInt(5))
/*     */     {
/*     */     case 0:
/* 256 */       return Material.LEATHER_HELMET;
/*     */     case 1:
/* 258 */       return Material.GOLD_HELMET;
/*     */     case 2:
/* 260 */       return Material.CHAINMAIL_HELMET;
/*     */     case 3:
/* 262 */       return Material.IRON_HELMET;
/*     */     case 4:
/* 264 */       return Material.DIAMOND_HELMET;
/*     */     }
/* 266 */     return null;
/*     */   }
/*     */ 
/*     */   public Material getHoe()
/*     */   {
/* 277 */     switch (this.gen.nextInt(5))
/*     */     {
/*     */     case 0:
/* 280 */       return Material.WOOD_HOE;
/*     */     case 1:
/* 282 */       return Material.STONE_HOE;
/*     */     case 2:
/* 284 */       return Material.GOLD_HOE;
/*     */     case 3:
/* 286 */       return Material.IRON_HOE;
/*     */     case 4:
/* 288 */       return Material.DIAMOND_HOE;
/*     */     }
/* 290 */     return null;
/*     */   }
/*     */ 
/*     */   public Material getLeggings()
/*     */   {
/* 301 */     switch (this.gen.nextInt(5))
/*     */     {
/*     */     case 0:
/* 304 */       return Material.LEATHER_LEGGINGS;
/*     */     case 1:
/* 306 */       return Material.GOLD_LEGGINGS;
/*     */     case 2:
/* 308 */       return Material.CHAINMAIL_LEGGINGS;
/*     */     case 3:
/* 310 */       return Material.IRON_LEGGINGS;
/*     */     case 4:
/* 312 */       return Material.DIAMOND_LEGGINGS;
/*     */     }
/* 314 */     return null;
/*     */   }
/*     */ 
/*     */   public List<String> getLore(ItemStack itemStack)
/*     */   {
/* 327 */     ItemStack ret = itemStack;
/*     */     ItemMeta itemMeta;
/* 329 */     if (ret.hasItemMeta())
/*     */     {
/* 331 */       itemMeta = ret.getItemMeta();
/*     */     }
/*     */     else
/*     */     {
/* 335 */       itemMeta = Bukkit.getItemFactory().getItemMeta(ret.getType());
/*     */     }
/* 337 */     if (itemMeta.hasLore())
/* 338 */       return itemMeta.getLore();
/* 339 */     return new ArrayList<String>();
/*     */   }
/*     */ 
/*     */   public String getName(ItemStack itemStack)
/*     */   {
/* 351 */     ItemStack ret = itemStack;
/*     */     ItemMeta itemMeta;
/* 353 */     if (ret.hasItemMeta())
/*     */     {
/* 355 */       itemMeta = ret.getItemMeta();
/*     */     }
/*     */     else
/*     */     {
/* 359 */       itemMeta = Bukkit.getItemFactory().getItemMeta(ret.getType());
/*     */     }
/* 361 */     if (itemMeta.hasDisplayName())
/* 362 */       return itemMeta.getDisplayName();
/* 363 */     String unfName = ret.getType().name();
/* 364 */     String[] split = unfName.split("_");
/* 365 */     String fName = new String();
/* 366 */     for (String s : split)
/*     */     {
/* 368 */       String firstLetter = s.substring(0, 1);
/* 369 */       String restOfWord = s.substring(1, s.length());
/* 370 */       String newName = firstLetter.toUpperCase() + restOfWord.toLowerCase();
/*     */ 
/* 372 */       fName = fName + newName + " ";
/*     */     }
/* 374 */     return fName;
/*     */   }
/*     */ 
/*     */   public Material getPickaxe()
/*     */   {
/* 384 */     switch (this.gen.nextInt(5))
/*     */     {
/*     */     case 0:
/* 387 */       return Material.WOOD_PICKAXE;
/*     */     case 1:
/* 389 */       return Material.STONE_PICKAXE;
/*     */     case 2:
/* 391 */       return Material.GOLD_PICKAXE;
/*     */     case 3:
/* 393 */       return Material.IRON_PICKAXE;
/*     */     case 4:
/* 395 */       return Material.DIAMOND_PICKAXE;
/*     */     }
/* 397 */     return null;
/*     */   }
/*     */ 
/*     */   public Material getSpade()
/*     */   {
/* 408 */     switch (this.gen.nextInt(5))
/*     */     {
/*     */     case 0:
/* 411 */       return Material.WOOD_SPADE;
/*     */     case 1:
/* 413 */       return Material.STONE_SPADE;
/*     */     case 2:
/* 415 */       return Material.GOLD_SPADE;
/*     */     case 3:
/* 417 */       return Material.IRON_SPADE;
/*     */     case 4:
/* 419 */       return Material.DIAMOND_SPADE;
/*     */     }
/* 421 */     return null;
/*     */   }
/*     */ 
/*     */   public Material getSword()
/*     */   {
/* 432 */     switch (this.gen.nextInt(5))
/*     */     {
/*     */     case 0:
/* 435 */       return Material.WOOD_SWORD;
/*     */     case 1:
/* 437 */       return Material.STONE_SWORD;
/*     */     case 2:
/* 439 */       return Material.GOLD_SWORD;
/*     */     case 3:
/* 441 */       return Material.IRON_SWORD;
/*     */     case 4:
/* 443 */       return Material.DIAMOND_SWORD;
/*     */     }
/* 445 */     return null;
/*     */   }
/*     */ 
/*     */   public Material[] goldArmor()
/*     */   {
/* 456 */     return new Material[] { Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS };
/*     */   }
/*     */ 
/*     */   public Material goldWeapon(int num)
/*     */   {
/* 470 */     switch (num)
/*     */     {
/*     */     case 1:
/* 473 */       return Material.GOLD_SWORD;
/*     */     case 2:
/* 475 */       return Material.GOLD_PICKAXE;
/*     */     case 3:
/* 477 */       return Material.GOLD_SPADE;
/*     */     case 4:
/* 479 */       return Material.GOLD_AXE;
/*     */     case 5:
/* 481 */       return Material.GOLD_HOE;
/*     */     }
/* 483 */     return null;
/*     */   }
/*     */ 
/*     */   public Material[] ironArmor()
/*     */   {
/* 494 */     return new Material[] { Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS };
/*     */   }
/*     */ 
/*     */   public Material ironWeapon(int num)
/*     */   {
/* 508 */     switch (num)
/*     */     {
/*     */     case 1:
/* 511 */       return Material.IRON_SWORD;
/*     */     case 2:
/* 513 */       return Material.IRON_PICKAXE;
/*     */     case 3:
/* 515 */       return Material.IRON_SPADE;
/*     */     case 4:
/* 517 */       return Material.IRON_AXE;
/*     */     case 5:
/* 519 */       return Material.IRON_HOE;
/*     */     }
/* 521 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean isArmor(Material mat)
/*     */   {
/* 534 */     if ((isHelmet(mat)) || (isBoots(mat)) || (isChestPlate(mat)) || (isLeggings(mat)))
/*     */     {
/* 536 */       return true;
/* 537 */     }return false;
/*     */   }
/*     */ 
/*     */   public boolean isAxe(Material mat)
/*     */   {
/* 549 */     if ((mat.equals(Material.WOOD_AXE)) || (mat.equals(Material.STONE_AXE)) || (mat.equals(Material.GOLD_AXE)) || (mat.equals(Material.IRON_AXE)) || (mat.equals(Material.DIAMOND_AXE)))
/*     */     {
/* 553 */       return true;
/* 554 */     }return false;
/*     */   }
/*     */ 
/*     */   public boolean isBoots(Material mat)
/*     */   {
/* 566 */     if ((mat.equals(Material.LEATHER_BOOTS)) || (mat.equals(Material.GOLD_BOOTS)) || (mat.equals(Material.CHAINMAIL_BOOTS)) || (mat.equals(Material.IRON_BOOTS)) || (mat.equals(Material.DIAMOND_BOOTS)))
/*     */     {
/* 571 */       return true;
/* 572 */     }return false;
/*     */   }
/*     */ 
/*     */   public boolean isChestPlate(Material mat)
/*     */   {
/* 584 */     if ((mat.equals(Material.LEATHER_CHESTPLATE)) || (mat.equals(Material.GOLD_CHESTPLATE)) || (mat.equals(Material.CHAINMAIL_CHESTPLATE)) || (mat.equals(Material.IRON_CHESTPLATE)) || (mat.equals(Material.DIAMOND_CHESTPLATE)))
/*     */     {
/* 589 */       return true;
/* 590 */     }return false;
/*     */   }
/*     */ 
/*     */   public boolean isHelmet(Material mat)
/*     */   {
/* 602 */     if ((mat.equals(Material.LEATHER_HELMET)) || (mat.equals(Material.GOLD_HELMET)) || (mat.equals(Material.CHAINMAIL_HELMET)) || (mat.equals(Material.IRON_HELMET)) || (mat.equals(Material.DIAMOND_HELMET)))
/*     */     {
/* 607 */       return true;
/* 608 */     }return false;
/*     */   }
/*     */ 
/*     */   public boolean isHoe(Material mat)
/*     */   {
/* 620 */     if ((mat.equals(Material.WOOD_HOE)) || (mat.equals(Material.STONE_HOE)) || (mat.equals(Material.GOLD_HOE)) || (mat.equals(Material.IRON_HOE)) || (mat.equals(Material.DIAMOND_HOE)))
/*     */     {
/* 624 */       return true;
/* 625 */     }return false;
/*     */   }
/*     */ 
/*     */   public boolean isLeather(Material mat)
/*     */   {
/* 630 */     if ((mat.equals(Material.LEATHER_HELMET)) || (mat.equals(Material.LEATHER_CHESTPLATE)) || (mat.equals(Material.LEATHER_LEGGINGS)) || (mat.equals(Material.LEATHER_BOOTS)))
/*     */     {
/* 634 */       return true;
/* 635 */     }return false;
/*     */   }
/*     */ 
/*     */   public boolean isLeggings(Material mat)
/*     */   {
/* 647 */     if ((mat.equals(Material.LEATHER_LEGGINGS)) || (mat.equals(Material.GOLD_LEGGINGS)) || (mat.equals(Material.CHAINMAIL_LEGGINGS)) || (mat.equals(Material.IRON_LEGGINGS)) || (mat.equals(Material.DIAMOND_LEGGINGS)))
/*     */     {
/* 652 */       return true;
/* 653 */     }return false;
/*     */   }
/*     */ 
/*     */   public boolean isPickaxe(Material mat)
/*     */   {
/* 665 */     if ((mat.equals(Material.WOOD_PICKAXE)) || (mat.equals(Material.STONE_PICKAXE)) || (mat.equals(Material.GOLD_PICKAXE)) || (mat.equals(Material.IRON_PICKAXE)) || (mat.equals(Material.DIAMOND_PICKAXE)))
/*     */     {
/* 670 */       return true;
/* 671 */     }return false;
/*     */   }
/*     */ 
/*     */   public boolean isSpade(Material mat)
/*     */   {
/* 683 */     if ((mat.equals(Material.WOOD_SPADE)) || (mat.equals(Material.STONE_SPADE)) || (mat.equals(Material.GOLD_SPADE)) || (mat.equals(Material.IRON_SPADE)) || (mat.equals(Material.DIAMOND_SPADE)))
/*     */     {
/* 687 */       return true;
/* 688 */     }return false;
/*     */   }
/*     */ 
/*     */   public boolean isSword(Material mat)
/*     */   {
/* 700 */     if ((mat.equals(Material.WOOD_SWORD)) || (mat.equals(Material.STONE_SWORD)) || (mat.equals(Material.GOLD_SWORD)) || (mat.equals(Material.IRON_SWORD)) || (mat.equals(Material.DIAMOND_SWORD)))
/*     */     {
/* 704 */       return true;
/* 705 */     }return false;
/*     */   }
/*     */ 
/*     */   public boolean isTool(Material mat)
/*     */   {
/* 717 */     if ((isSword(mat)) || (isSpade(mat)) || (isAxe(mat)) || (isPickaxe(mat)) || (isHoe(mat)) || (mat.equals(Material.BOW)))
/*     */     {
/* 719 */       return true;
/* 720 */     }return false;
/*     */   }
/*     */ 
/*     */   public Material[] leatherArmor()
/*     */   {
/* 730 */     return new Material[] { Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS };
/*     */   }
/*     */ 
/*     */   public ItemStack replaceLore(ItemStack tool, String toReplace, String replaceWith)
/*     */   {
/* 749 */     ItemMeta meta = tool.getItemMeta();
/* 750 */     List<String> loreList = meta.getLore();
/* 751 */     if ((loreList == null) || (loreList.isEmpty()))
/* 752 */       return tool;
/* 753 */     for (String s : meta.getLore())
/* 754 */       if (s.equals(toReplace))
/*     */       {
/* 756 */         loreList.remove(s);
/* 757 */         loreList.add(replaceWith);
/*     */       }
/* 759 */     meta.setLore(loreList);
/* 760 */     tool.setItemMeta(meta);
/* 761 */     return tool;
/*     */   }
/*     */ 
/*     */   public ItemStack setLore(ItemStack itemStack, List<String> lore)
/*     */   {
/* 775 */     ItemStack ret = itemStack;
/*     */     ItemMeta itemMeta;
/* 777 */     if (ret.hasItemMeta())
/*     */     {
/* 779 */       itemMeta = ret.getItemMeta();
/*     */     }
/*     */     else
/*     */     {
/* 783 */       itemMeta = Bukkit.getItemFactory().getItemMeta(ret.getType());
/*     */     }
/* 785 */     itemMeta.setLore(lore);
/* 786 */     ret.setItemMeta(itemMeta);
/* 787 */     return ret;
/*     */   }
/*     */ 
/*     */   public ItemStack setLore(ItemStack itemStack, String[] lore)
/*     */   {
/* 801 */     ItemStack ret = itemStack;
/*     */     ItemMeta itemMeta;
/* 803 */     if (ret.hasItemMeta())
/*     */     {
/* 805 */       itemMeta = ret.getItemMeta();
/*     */     }
/*     */     else
/*     */     {
/* 809 */       itemMeta = Bukkit.getItemFactory().getItemMeta(ret.getType());
/*     */     }
/* 811 */     itemMeta.setLore(Arrays.asList(lore));
/* 812 */     ret.setItemMeta(itemMeta);
/* 813 */     return ret;
/*     */   }
/*     */ 
/*     */   public ItemStack setName(ItemStack itemStack, String name)
/*     */   {
/* 827 */     ItemStack ret = itemStack;
/*     */     ItemMeta itemMeta;
/* 829 */     if (ret.hasItemMeta())
/*     */     {
/* 831 */       itemMeta = ret.getItemMeta();
/*     */     }
/*     */     else
/*     */     {
/* 835 */       itemMeta = Bukkit.getItemFactory().getItemMeta(ret.getType());
/*     */     }
/* 837 */     itemMeta.setDisplayName(name);
/* 838 */     ret.setItemMeta(itemMeta);
/* 839 */     return ret;
/*     */   }
/*     */ 
/*     */   public Material stoneWeapon(int num)
/*     */   {
/* 852 */     switch (num)
/*     */     {
/*     */     case 1:
/* 855 */       return Material.STONE_SWORD;
/*     */     case 2:
/* 857 */       return Material.STONE_PICKAXE;
/*     */     case 3:
/* 859 */       return Material.STONE_SPADE;
/*     */     case 4:
/* 861 */       return Material.STONE_AXE;
/*     */     case 5:
/* 863 */       return Material.STONE_HOE;
/*     */     }
/* 865 */     return null;
/*     */   }
/*     */ 
/*     */   public Material toolPicker()
/*     */   {
/* 876 */     int next = this.gen.nextInt(10);
/* 877 */     switch (next)
/*     */     {
/*     */     case 1:
/* 880 */       return getHelmet();
/*     */     case 2:
/* 882 */       return getChestPlate();
/*     */     case 3:
/* 884 */       return getLeggings();
/*     */     case 4:
/* 886 */       return getBoots();
/*     */     case 5:
/* 888 */       return getHoe();
/*     */     case 6:
/* 890 */       return getPickaxe();
/*     */     case 7:
/* 892 */       return getAxe();
/*     */     case 8:
/* 894 */       return getSpade();
/*     */     case 9:
/* 896 */       return Material.BOW;
/*     */     }
/* 898 */     return getSword();
/*     */   }
/*     */ 
/*     */   public Material weaponPicker()
/*     */   {
/* 910 */     switch (this.gen.nextInt(6))
/*     */     {
/*     */     case 0:
/* 913 */       return diamondWeapon(this.gen.nextInt(5));
/*     */     case 1:
/* 915 */       return ironWeapon(this.gen.nextInt(5));
/*     */     case 2:
/* 917 */       return goldWeapon(this.gen.nextInt(5));
/*     */     case 3:
/* 919 */       return stoneWeapon(this.gen.nextInt(5));
/*     */     case 4:
/* 921 */       return woodWeapon(this.gen.nextInt(5));
/*     */     case 5:
/* 923 */       return Material.BOW;
/*     */     }
/* 925 */     return null;
/*     */   }
/*     */ 
/*     */   public Material woodWeapon(int num)
/*     */   {
/* 939 */     switch (num)
/*     */     {
/*     */     case 1:
/* 942 */       return Material.WOOD_SWORD;
/*     */     case 2:
/* 944 */       return Material.WOOD_PICKAXE;
/*     */     case 3:
/* 946 */       return Material.WOOD_SPADE;
/*     */     case 4:
/* 948 */       return Material.WOOD_AXE;
/*     */     case 5:
/* 950 */       return Material.WOOD_HOE;
/*     */     }
/* 952 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.items.ItemAPI
 * JD-Core Version:    0.6.2
 */