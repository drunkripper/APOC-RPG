/*     */ package com.modcrafting.diablodrops.commands;
/*     */ 
/*     */ import com.modcrafting.diablodrops.DiabloDrops;
/*     */ import com.modcrafting.diablodrops.items.IdentifyTome;
/*     */ import com.modcrafting.diablodrops.items.Socket;
/*     */ import com.modcrafting.diablodrops.items.SockettedItem;
/*     */ import com.modcrafting.diablodrops.sets.ArmorSet;
/*     */ import com.modcrafting.diablodrops.tier.Tier;

/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;

/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ public class DiabloDropCommand
/*     */   implements CommandExecutor
/*     */ {
/*     */   private final DiabloDrops plugin;
/*     */ 
/*     */   public DiabloDropCommand(DiabloDrops plugin)
/*     */   {
/*  34 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */   public String combineSplit(int startIndex, String[] string, String seperator)
/*     */   {
/*  40 */     StringBuilder builder = new StringBuilder();
/*  41 */     if (string.length >= 1)
/*     */     {
/*  43 */       for (int i = startIndex; i < string.length; i++)
/*     */       {
/*  45 */         builder.append(string[i]);
/*  46 */         builder.append(seperator);
/*     */       }
/*  48 */       if (builder.length() > seperator.length())
/*     */       {
/*  50 */         builder.deleteCharAt(builder.length() - seperator.length());
/*  51 */         return builder.toString();
/*     */       }
/*     */     }
/*  54 */     return "";
/*     */   }
/*     */ 
/*     */   @SuppressWarnings("deprecation")
public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
/*     */   {
/*  62 */     if (!sender.hasPermission(command.getPermission()))
/*     */     {
/*  64 */       sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("You cannot run this command.").toString());
/*  65 */       return true;
/*     */     }
/*  67 */     switch (args.length)
/*     */     {
/*     */     case 0:
/*  70 */       if (!(sender instanceof Player))
/*     */       {
/*  72 */         sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("You cannot run this command.").toString());
/*     */ 
/*  74 */         return true;
/*     */       }
/*  76 */       ItemStack ci = this.plugin.getDropAPI().getItem();
/*  77 */       while (ci == null)
/*     */       {
/*  79 */         ci = this.plugin.getDropAPI().getItem();
/*     */       }
/*  81 */       ((Player)sender).getInventory().addItem(new ItemStack[] { ci });
/*  82 */       ((Player)sender).updateInventory();
/*  83 */       ((Player)sender).sendMessage(new StringBuilder().append(ChatColor.GREEN).append("You have been given a DiabloDrops item.").toString());
/*     */ 
/*  85 */       return true;
/*     */     case 1:
/*  87 */       if ((args[0].equalsIgnoreCase("tome")) || (args[0].equalsIgnoreCase("book")))
/*     */       {
/*  90 */         if (!(sender instanceof Player))
/*     */         {
/*  92 */           sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("You cannot run this command.").toString());
/*     */         }
/*     */         else
/*     */         {
/*  97 */           ((Player)sender).getInventory().addItem(new ItemStack[] { new IdentifyTome() });
/*     */ 
/*  99 */           ((Player)sender).sendMessage(new StringBuilder().append(ChatColor.GREEN).append("You have been given an Identify Tome.").toString());
/*     */ 
/* 101 */           ((Player)sender).updateInventory();
/*     */         }
/*     */       }
/* 104 */       else if ((args[0].equalsIgnoreCase("socket")) || (args[0].equalsIgnoreCase("socketitem")))
/*     */       {
/* 107 */         if (!(sender instanceof Player))
/*     */         {
/* 109 */           sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("You cannot run this command.").toString());
/*     */         }
/*     */         else
/*     */         {
/* 114 */           List<String> l = this.plugin.getConfig().getStringList("SocketItem.Items");
/*     */ 
/* 116 */           ((Player)sender).getInventory().addItem(new ItemStack[] { new Socket(Material.valueOf(((String)l.get(this.plugin.getSingleRandom().nextInt(l.size()))).toUpperCase())) });
/*     */ 
/* 120 */           ((Player)sender).updateInventory();
/* 121 */           ((Player)sender).sendMessage(new StringBuilder().append(ChatColor.GREEN).append("You have been given a Socket Enhancement.").toString());
/*     */         }
/*     */ 
/*     */       }
/* 125 */       else if ((args[0].equalsIgnoreCase("socketted")) || (args[0].equalsIgnoreCase("socketteditem")))
/*     */       {
/* 128 */         if (!(sender instanceof Player))
/*     */         {
/* 130 */           sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("You cannot run this command.").toString());
/*     */         }
/*     */         else
/*     */         {
/* 135 */           ((Player)sender).getInventory().addItem(new ItemStack[] { new SockettedItem(this.plugin.getDropAPI().dropPicker()) });
/*     */ 
/* 138 */           ((Player)sender).updateInventory();
/* 139 */           ((Player)sender).sendMessage(new StringBuilder().append(ChatColor.GREEN).append("You have been given a Socketted Item.").toString());
/*     */         }
/*     */ 
/*     */       }
/* 143 */       else if (args[0].equalsIgnoreCase("custom"))
/*     */       {
/* 145 */         if (!(sender instanceof Player))
/*     */         {
/* 147 */           sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("You cannot run this command.").toString());
/*     */         }
/*     */         else
/*     */         {
/* 152 */           if (this.plugin.custom.size() > 0)
/*     */           {
/* 154 */             ((Player)sender).getInventory().addItem(new ItemStack[] { (ItemStack)this.plugin.custom.get(this.plugin.getSingleRandom().nextInt(this.plugin.custom.size())) });
/*     */           }
/*     */           else
/*     */           {
/* 160 */             ItemStack pis = this.plugin.getDropAPI().getItem();
/* 161 */             while (pis == null)
/* 162 */               pis = this.plugin.getDropAPI().getItem();
/* 163 */             ((Player)sender).getInventory().addItem(new ItemStack[] { pis });
/*     */           }
/* 165 */           ((Player)sender).updateInventory();
/* 166 */           ((Player)sender).sendMessage(new StringBuilder().append(ChatColor.GREEN).append("You have been given a DiabloDrops item.").toString());
/*     */         }
/*     */ 
/*     */       }
/* 170 */       else if (args[0].equalsIgnoreCase("repair"))
/*     */       {
/* 172 */         if (!(sender instanceof Player))
/*     */         {
/* 174 */           sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("You are unable to run this command right now.").toString());
/*     */         }
/* 179 */         else if (this.plugin.getDropAPI().canBeItem(((Player)sender).getItemInHand().getType()))
/*     */         {
/* 182 */           ((Player)sender).getItemInHand().setDurability((short)0);
/*     */ 
/* 184 */           ((Player)sender).sendMessage(new StringBuilder().append(ChatColor.GREEN).append("Item repaired.").toString());
/*     */ 
/* 186 */           return true;
/*     */         }
/*     */ 
/* 189 */         ((Player)sender).sendMessage("Unable to repair item.");
/*     */       }
/* 191 */       else if ((args[0].equalsIgnoreCase("reload")) && (sender.hasPermission("diablodrops.reload")))
/*     */       {
/* 194 */         this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
/* 195 */         this.plugin.getServer().getPluginManager().enablePlugin(this.plugin);
/* 196 */         this.plugin.reloadConfig();
/* 197 */         this.plugin.getLogger().info("Reloaded");
/* 198 */         sender.sendMessage(new StringBuilder().append(ChatColor.GREEN).append("DiabloDrops Reloaded").toString());
/*     */       }
/* 200 */       else if (args[0].equalsIgnoreCase("debug"))
/*     */       {
/* 202 */         int customsize = this.plugin.custom.size();
/* 203 */         this.plugin.getLogger().info(new StringBuilder().append(customsize).append("]: Custom Items Loaded.").toString());
/*     */ 
/* 205 */         sender.sendMessage(new StringBuilder().append(customsize).append("]: Custom Items Loaded.").toString());
/* 206 */         int armorsets = this.plugin.armorSets.size();
/* 207 */         this.plugin.getLogger().info(new StringBuilder().append(armorsets).append("]: ArmorSets Loaded.").toString());
/* 208 */         sender.sendMessage(new StringBuilder().append(armorsets).append("]: ArmorSets Loaded.").toString());
/* 209 */         int tier = this.plugin.tiers.size();
/* 210 */         this.plugin.getLogger().info(new StringBuilder().append(tier).append("]: Tiers Loaded.").toString());
/* 211 */         sender.sendMessage(new StringBuilder().append(tier).append("]: Tiers Loaded.").toString());
/* 212 */         int defaultP = this.plugin.prefix.size();
/* 213 */         this.plugin.getLogger().info(new StringBuilder().append(defaultP).append("]: Default Prefixes Loaded.").toString());
/*     */ 
/* 215 */         sender.sendMessage(new StringBuilder().append(defaultP).append("]: Default Prefixes Loaded.").toString());
/* 216 */         int defaultS = this.plugin.suffix.size();
/* 217 */         this.plugin.getLogger().info(new StringBuilder().append(defaultS).append("]: Default Suffixes Loaded.").toString());
/*     */ 
/* 219 */         sender.sendMessage(new StringBuilder().append(defaultS).append("]: Default Suffixes Loaded.").toString());
/* 220 */         int customP = this.plugin.hmprefix.size();
/* 221 */         this.plugin.getLogger().info(new StringBuilder().append(customP).append("]: Custom Prefixes Loaded.").toString());
/*     */ 
/* 223 */         sender.sendMessage(new StringBuilder().append(customP).append("]: Custom Prefixes Loaded.").toString());
/* 224 */         int customS = this.plugin.hmsuffix.size();
/* 225 */         this.plugin.getLogger().info(new StringBuilder().append(customS).append("]: Custom Suffixes Loaded.").toString());
/*     */ 
/* 227 */         sender.sendMessage(new StringBuilder().append(customS).append("]: Custom Suffixes Loaded.").toString());
/* 228 */         int dlore = this.plugin.defenselore.size();
/* 229 */         this.plugin.getLogger().info(new StringBuilder().append(dlore).append("]: Defense Lore Loaded.").toString());
/* 230 */         sender.sendMessage(new StringBuilder().append(dlore).append("]: Defense Lore Loaded.").toString());
/* 231 */         int olore = this.plugin.offenselore.size();
/* 232 */         this.plugin.getLogger().info(new StringBuilder().append(olore).append("]: Offense Lore Loaded.").toString());
/* 233 */         sender.sendMessage(new StringBuilder().append(olore).append("]: Offense Lore Loaded.").toString());
/* 234 */         int w = this.plugin.worlds.size();
/* 235 */         this.plugin.getLogger().info(new StringBuilder().append(w).append("]: Worlds allowing Loaded.").toString());
/* 236 */         sender.sendMessage(new StringBuilder().append(w).append("]: Worlds allowing Loaded.").toString());
/* 237 */         if (args.length > 1)
/*     */         {
/* 239 */           if (args[1].equalsIgnoreCase("detailed"))
/*     */           {
/* 241 */             StringBuilder sb = new StringBuilder();
/* 242 */             sb.append("\n");
/* 243 */             sb.append("-----Custom-----");
/* 244 */             sb.append("\n");
/* 245 */             for (ItemStack tool : this.plugin.custom)
/*     */             {
/* 247 */               sb.append(new StringBuilder().append(tool.getItemMeta().getDisplayName()).append(" ").toString());
/*     */             }
/*     */ 
/* 250 */             sb.append("\n");
/* 251 */             sb.append("-----ArmorSet-----");
/* 252 */             sb.append("\n");
/* 253 */             for (ArmorSet a : this.plugin.armorSets)
/*     */             {
/* 255 */               sb.append(new StringBuilder().append(a.getName()).append(" ").toString());
/*     */             }
/* 257 */             sb.append("\n");
/* 258 */             sb.append("-----Tiers-----");
/* 259 */             sb.append("\n");
/* 260 */             for (Tier a : this.plugin.tiers)
/*     */             {
/* 262 */               sb.append(new StringBuilder().append(a.getName()).append(" ").toString());
/*     */             }
/* 264 */             sb.append("\n");
/* 265 */             sb.append("-----DefaultPrefix-----");
/* 266 */             sb.append("\n");
/* 267 */             for (String s : this.plugin.prefix)
/*     */             {
/* 269 */               sb.append(new StringBuilder().append(s).append(" ").toString());
/*     */             }
/* 271 */             sb.append("\n");
/* 272 */             sb.append("-----DefaultSuffix-----");
/* 273 */             sb.append("\n");
/* 274 */             for (String s : this.plugin.suffix)
/*     */             {
/* 276 */               sb.append(new StringBuilder().append(s).append(" ").toString());
/*     */             }
/* 278 */             sb.append("\n");
/* 279 */             sb.append("-----CustomPrefix-----");
/* 280 */             sb.append("\n");
/* 281 */             for (Material m : this.plugin.hmprefix.keySet())
/*     */             {
/* 283 */               sb.append(new StringBuilder().append(m.toString()).append("\n").toString());
/* 284 */               for (String p : this.plugin.hmprefix.get(m))
/*     */               {
/* 286 */                 sb.append(new StringBuilder().append(p).append(" ").toString());
/*     */               }
/*     */             }
/* 289 */             sb.append("\n");
/* 290 */             sb.append("-----CustomSuffix-----");
/* 291 */             sb.append("\n");
/* 292 */             for (Material m : this.plugin.hmsuffix.keySet())
/*     */             {
/* 294 */               sb.append(new StringBuilder().append(m.toString()).append("\n").toString());
/* 295 */               for (String p : this.plugin.hmsuffix.get(m))
/*     */               {
/* 297 */                 sb.append(new StringBuilder().append(p).append(" ").toString());
/*     */               }
/*     */             }
/* 300 */             sb.append("\n");
/* 301 */             sb.append("-----Defense Lore-----");
/* 302 */             sb.append("\n");
/* 303 */             for (String s : this.plugin.defenselore)
/*     */             {
/* 305 */               sb.append(new StringBuilder().append(s).append(" ").toString());
/*     */             }
/* 307 */             sb.append("\n");
/* 308 */             sb.append("-----Offense Lore-----");
/* 309 */             sb.append("\n");
/* 310 */             for (String s : this.plugin.offenselore)
/*     */             {
/* 312 */               sb.append(new StringBuilder().append(s).append(" ").toString());
/*     */             }
/* 314 */             this.plugin.getLogger().info(sb.toString());
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 320 */         sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("That is not a valid command.").toString());
/*     */       }
/*     */ 
/* 323 */       return true;
/*     */     }
/* 325 */     if (args[0].equalsIgnoreCase("custom"))
/*     */     {
/* 327 */       String name = "";
/* 328 */       Player p = null;
/* 329 */       for (String s : args)
/*     */       {
/* 331 */         if (StringUtils.containsIgnoreCase(s, "p:"))
/*     */         {
/* 333 */           s = s.replace("p:", "");
/* 334 */           p = Bukkit.getPlayer(s);
/*     */         }
/* 337 */         else if (!s.equals(args[0])) {
/* 338 */           if (s.equals(""))
/* 339 */             name = s;
/*     */           else
/* 341 */             name = new StringBuilder().append(name).append(" ").append(s).toString(); 
/*     */         }
/*     */       }
/* 343 */       ItemStack customItem = null;
/* 344 */       if (this.plugin.custom.size() > 0)
/*     */       {
/* 346 */         for (ItemStack is : this.plugin.custom)
/*     */         {
/* 348 */           if (ChatColor.stripColor(this.plugin.getItemAPI().getName(is)).equals(name))
/*     */           {
/* 352 */             customItem = is;
/* 353 */             break;
/*     */           }
/*     */         }
/* 356 */         if (customItem == null) {
/* 357 */           customItem = (ItemStack)this.plugin.custom.get(this.plugin.getSingleRandom().nextInt(this.plugin.custom.size()));
/*     */         }
/*     */       }
/*     */ 
/* 361 */       if ((customItem != null) && (p != null))
/*     */       {
/* 363 */         p.getInventory().addItem(new ItemStack[] { customItem });
/* 364 */         p.updateInventory();
/* 365 */         p.sendMessage(new StringBuilder().append(ChatColor.GREEN).append("You have been given a DiabloDrops item.").toString());
/*     */ 
/* 367 */         sender.sendMessage(new StringBuilder().append(ChatColor.WHITE).append(p.getName()).append(ChatColor.GREEN).append(" has been given a DiabloDrops item.").toString());
/*     */       }
/* 371 */       else if ((customItem != null) && (p == null) && ((sender instanceof Player)))
/*     */       {
/* 374 */         ((Player)sender).getInventory().addItem(new ItemStack[] { customItem });
/* 375 */         ((Player)sender).updateInventory();
/* 376 */         ((Player)sender).sendMessage(new StringBuilder().append(ChatColor.GREEN).append("You have been given a DiabloDrops item.").toString());
/*     */       }
/*     */       else
/*     */       {
/* 381 */         sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("Either that is not a valid item or you are unable to run this command.").toString());
/*     */       }
/*     */ 
/* 384 */       return true;
/*     */     }
/* 386 */     if (args[0].equalsIgnoreCase("give"))
/*     */     {
/* 388 */       if (args.length < 2)
/*     */       {
/* 390 */         sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("Not enough arguments.").toString());
/*     */ 
/* 392 */         return true;
/*     */       }
/* 394 */       List<String> stringList = new ArrayList<String>();
/* 395 */       for (String s : args)
/*     */       {
/* 397 */         if (!s.equals(args[0]))
/*     */         {
/* 399 */           Player p = Bukkit.getPlayer(s);
/* 400 */           ItemStack giveItem = this.plugin.getDropAPI().getItem();
/* 401 */           while (giveItem == null)
/*     */           {
/* 403 */             giveItem = this.plugin.getDropAPI().getItem();
/*     */           }
/* 405 */           stringList.add(p.getName());
/* 406 */           p.getInventory().addItem(new ItemStack[] { giveItem });
/* 407 */           p.sendMessage(new StringBuilder().append(ChatColor.GREEN).append("You have been given a DiabloDrops item.").toString());
/*     */         }
/*     */       }
/* 410 */       sender.sendMessage(new StringBuilder().append(ChatColor.GREEN).append("You gave items to ").append(ChatColor.WHITE).append(stringList.toString().replace("[", "").replace("]", "")).append(ChatColor.GREEN).append(".").toString());
/*     */ 
/* 415 */       return true;
/*     */     }
/* 417 */     if (args[0].equalsIgnoreCase("modify"))
/*     */     {
/* 419 */       if (!(sender instanceof Player))
/*     */       {
/* 421 */         sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("You cannot run this command.").toString());
/*     */       }
/*     */       else
/*     */       {
/* 426 */         if ((args.length < 2) || (((Player)sender).getItemInHand() == null) || (((Player)sender).getItemInHand().getType().equals(Material.AIR)))
/*     */         {
/* 430 */           return true;
/* 431 */         }ItemStack tool = ((Player)sender).getItemInHand();
/* 432 */         ItemMeta meta = tool.getItemMeta();
/* 433 */         if (args[1].equalsIgnoreCase("lore"))
/*     */         {
/* 435 */           if (args[2].equalsIgnoreCase("clear"))
/*     */           {
/* 437 */             meta.setLore(null);
/* 438 */             tool.setItemMeta(meta);
/* 439 */             ((Player)sender).sendMessage(new StringBuilder().append(ChatColor.GREEN).append("Cleared the lore for the item!").toString());
/*     */ 
/* 441 */             return true;
/*     */           }
/* 443 */           String lore = combineSplit(2, args, " ");
/* 444 */           lore = ChatColor.translateAlternateColorCodes("&".toCharArray()[0], lore);
/*     */ 
/* 446 */           meta.setLore(Arrays.asList(lore.split(",")));
/* 447 */           tool.setItemMeta(meta);
/* 448 */           ((Player)sender).sendMessage(new StringBuilder().append(ChatColor.GREEN).append("Set the lore for the item!").toString());
/*     */ 
/* 450 */           return true;
/*     */         }
/* 452 */         if (args[1].equalsIgnoreCase("name"))
/*     */         {
/* 454 */           if (args[2].equalsIgnoreCase("clear"))
/*     */           {
/* 456 */             tool.getItemMeta().setDisplayName(null);
/* 457 */             ((Player)sender).sendMessage(new StringBuilder().append(ChatColor.GREEN).append("Cleared the name for the item!").toString());
/*     */ 
/* 459 */             return true;
/*     */           }
/* 461 */           String name = combineSplit(2, args, " ");
/* 462 */           name = ChatColor.translateAlternateColorCodes("&".toCharArray()[0], name);
/*     */ 
/* 465 */           meta.setDisplayName(name);
/* 466 */           tool.setItemMeta(meta);
/* 467 */           ((Player)sender).sendMessage(new StringBuilder().append(ChatColor.GREEN).append("Set the name for the item!").toString());
/*     */ 
/* 469 */           return true;
/*     */         }
/* 471 */         if (args[1].equalsIgnoreCase("enchant"))
/*     */         {
/* 473 */           if (args.length < 4)
/*     */           {
/* 475 */             if ((args.length == 3) && (args[2].equalsIgnoreCase("clear")))
/*     */             {
/* 478 */               for (Enchantment e : Enchantment.values())
/* 479 */                 tool.getItemMeta().removeEnchant(e);
/* 480 */               ((Player)sender).sendMessage(new StringBuilder().append(ChatColor.GREEN).append("Cleared the enchantments for the item!").toString());
/*     */ 
/* 483 */               return true;
/*     */             }
/* 485 */             ((Player)sender).sendMessage(new StringBuilder().append(ChatColor.RED).append("Correct usage: /dd modify enchant").append(" [enchantment name] [enchantment level]").toString());
/*     */ 
/* 489 */             return true;
/*     */           }
/* 491 */           if (args[2].equalsIgnoreCase("add"))
/*     */           {
/* 493 */             if (args.length < 5)
/* 494 */               return true;
/* 495 */             int i = 1;
/*     */             try
/*     */             {
/* 498 */               i = Integer.parseInt(args[4]);
/*     */             }
/*     */             catch (NumberFormatException nfe)
/*     */             {
/* 502 */               if (this.plugin.getDebug())
/*     */               {
/* 504 */                 this.plugin.log.warning(nfe.getMessage());
/*     */               }
/*     */             }
/* 507 */             Enchantment ech = Enchantment.getByName(args[3].toUpperCase());
/*     */ 
/* 509 */             if (ech != null)
/*     */             {
/* 511 */               ((Player)sender).getItemInHand().addUnsafeEnchantment(ech, i);
/*     */ 
/* 513 */               ((Player)sender).sendMessage(new StringBuilder().append(ChatColor.GREEN).append("Added enchantment.").toString());
/*     */             }
/*     */             else
/*     */             {
/* 519 */               ((Player)sender).sendMessage(new StringBuilder().append(ChatColor.RED).append(args[3]).append(" :enchantment does not exist!").toString());
/*     */             }
/*     */ 
/* 523 */             return true;
/*     */           }
/* 525 */           if (args[2].equalsIgnoreCase("remove"))
/*     */           {
/* 527 */             ItemStack is = ((Player)sender).getItemInHand();
/*     */ 
/* 529 */             Map<Enchantment, Integer> hm = new HashMap<Enchantment, Integer>();
/* 530 */             for (Enchantment e1 : is.getEnchantments().keySet())
/*     */             {
/* 533 */               if (!e1.getName().equalsIgnoreCase(args[3]))
/*     */               {
/* 535 */                 hm.put(e1, Integer.valueOf(is.getEnchantmentLevel(e1)));
/*     */               }
/*     */             }
/* 538 */             is.addUnsafeEnchantments(hm);
/* 539 */             ((Player)sender).sendMessage(new StringBuilder().append(ChatColor.GREEN).append("Removed enchantment.").toString());
/*     */ 
/* 541 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 546 */       return true;
/*     */     }
/* 548 */     if (args[0].equalsIgnoreCase("tier"))
/*     */     {
/* 550 */       String name = "";
/* 551 */       Player p = null;
/* 552 */       for (String s : args)
/*     */       {
/* 554 */         if (StringUtils.containsIgnoreCase(s, "p:"))
/*     */         {
/* 556 */           s = s.replace("p:", "");
/* 557 */           p = Bukkit.getPlayer(s);
/*     */         }
/* 560 */         else if (!s.equals(args[0])) {
/* 561 */           if (s.equals(""))
/* 562 */             name = s;
/*     */           else
/* 564 */             name = new StringBuilder().append(name).append(" ").append(s).toString(); 
/*     */         }
/*     */       }
/* 566 */       Tier tier = this.plugin.getDropAPI().getTier(name);
/* 567 */       ItemStack customItem = this.plugin.getDropAPI().getItem(tier);
/* 568 */       if ((customItem != null) && (p != null))
/*     */       {
/* 570 */         p.getInventory().addItem(new ItemStack[] { customItem });
/* 571 */         p.updateInventory();
/* 572 */         p.sendMessage(new StringBuilder().append(ChatColor.GREEN).append("You have been given a DiabloDrops item.").toString());
/*     */ 
/* 574 */         sender.sendMessage(new StringBuilder().append(ChatColor.WHITE).append(p.getName()).append(ChatColor.GREEN).append(" has been given a DiabloDrops item.").toString());
/*     */       }
/* 578 */       else if ((customItem != null) && (p == null) && ((sender instanceof Player)))
/*     */       {
/* 581 */         ((Player)sender).getInventory().addItem(new ItemStack[] { customItem });
/* 582 */         ((Player)sender).updateInventory();
/* 583 */         ((Player)sender).sendMessage(new StringBuilder().append(ChatColor.GREEN).append("You have been given a DiabloDrops item.").toString());
/*     */       }
/*     */       else
/*     */       {
/* 588 */         sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("Either that is not a valid tier or you are unable to run this command.").toString());
/*     */       }
/*     */ 
/* 591 */       return true;
/*     */     }
/* 593 */     if (!(sender instanceof Player))
/*     */     {
/* 595 */       sender.sendMessage("You cannot run this command right now.");
/* 596 */       return true;
/*     */     }
/*     */ 
/* 600 */     ItemStack ci2 = this.plugin.getDropAPI().getItem();
/* 601 */     while (ci2 == null)
/*     */     {
/* 603 */       ci2 = this.plugin.getDropAPI().getItem();
/*     */     }
/* 605 */     ((Player)sender).getInventory().addItem(new ItemStack[] { ci2 });
/* 606 */     ((Player)sender).updateInventory();
/* 607 */     ((Player)sender).sendMessage(new StringBuilder().append(ChatColor.GREEN).append("You have been given a DiabloDrops item.").toString());
/*     */ 
/* 609 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.commands.DiabloDropCommand
 * JD-Core Version:    0.6.2
 */