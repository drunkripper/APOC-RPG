/*     */ package com.modcrafting.diablodrops.listeners;
/*     */ 
/*     */ import java.io.File;
import java.io.IOException;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.Schematic;

/*     */ import org.bukkit.Chunk;
import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.world.ChunkPopulateEvent;
/*     */ 
/*     */ public class ChunkListener implements Listener
/*     */ {
/*     */   private final DiabloDrops plugin;
/*     */ 
/*     */   public ChunkListener(DiabloDrops plugin)
/*     */   {
/*  29 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */   
/*     */ 
/*     */   @EventHandler
/*     */   public void onChunkPopulate(ChunkPopulateEvent event)
/*     */   {
/* 618 */     if (!this.plugin.getConfig().getBoolean("Ruins.Enabled", true))
/* 619 */       return;
/* 620 */     if ((!this.plugin.worlds.contains(event.getWorld().getName())) && (this.plugin.getConfig().getBoolean("Worlds.Enabled", false)))
/*     */     {
/* 622 */       return;
/* 623 */     }
			  Chunk chunk = event.getChunk();
/* 624 */     int genInt = this.plugin.getSingleRandom().nextInt(100) + 1;
/* 625 */     int confChance = this.plugin.getConfig().getInt("Ruins.Chance", 1);
/* 626 */     if (genInt > confChance)
/* 627 */       return;
/* 628 */     int realX = chunk.getX() * 16 + this.plugin.getSingleRandom().nextInt(15);
/* 629 */     int realZ = chunk.getZ() * 16 + this.plugin.getSingleRandom().nextInt(15);
/* 630 */     int y = 0;
/* 631 */     for (int yp = 0; yp < chunk.getWorld().getMaxHeight() - 1; yp++) {
				boolean Good = true;
				for (int xp = 0; xp < realX+5; xp++) {
					for (int zp=0; zp < realZ+5; xp++) {
		/* 633 */       if ((chunk.getWorld().getBlockAt(realX+xp, yp, realZ+zp).getType() == Material.AIR) && (chunk.getWorld().getBlockAt(realX+xp, yp + 1, realZ+zp).getType() == Material.AIR) && (chunk.getWorld().getBlockAt(realX+xp, yp + 2, realZ+zp).getType() == Material.AIR) && (chunk.getWorld().getBlockAt(realX+xp, yp + 4, realZ+zp).getType() == Material.AIR) && (chunk.getWorld().getBlockAt(realX+xp, yp + 5, realZ+zp).getType() == Material.AIR)) { } else {
							Good = false;
							break;
						}
					}
					if (!Good) break;
				}
				if (Good) {
					y = yp;
					break;
				}
/*     */     }
/* 647 */     Block block = chunk.getWorld().getBlockAt(realX, y, realZ);
			  Material Under = block.getRelative(BlockFace.DOWN).getType();
			  String Type = "Land";
			  if (Under == Material.LAVA || Under == Material.STATIONARY_LAVA) {
				  Type = "Lava";
			  } else if (Under == Material.WATER || Under == Material.STATIONARY_WATER) {
				  Type = "Water";
			  }
/* 648 */     if (Under == Material.AIR || Under == Material.LEAVES || Under == Material.LOG || Under == Material.WOOD_STAIRS || Under == Material.VINE || Under == Material.SANDSTONE || Under == Material.SANDSTONE_STAIRS || Under == Material.GLASS || Under == Material.LONG_GRASS || Under == Material.BEDROCK)
/*     */     {
/* 653 */       return;
/* 654 */     }
			  if (Under == Material.SNOW) {
				  realX--;
			  }
/*     */
			  String[] Schematics = null;
			  if (Type.equals("Land")) {
				  Schematics = plugin.LandSchematics.list();
			  } else if (Type.equals("Lava")) {
				  Schematics = plugin.LavaSchematics.list();
			  } else if (Type.equals("Water")) {
				  Schematics = plugin.WaterSchematics.list();
			  }
			  if (Schematics.length > 0) {
				  int Schem = plugin.getSingleRandom().nextInt(Schematics.length);
				  Schematic SchematicLoaded = null;
				  try {
					  if (Type.equals("Land")) {
						  SchematicLoaded = Schematic.loadSchematic(new File(plugin.LandSchematics.getAbsolutePath() + "/" + Schematics[Schem]));
					  } else if (Type.equals("Lava")) {
						  SchematicLoaded = Schematic.loadSchematic(new File(plugin.LavaSchematics.getAbsolutePath() + "/" + Schematics[Schem]));
					  } else if (Type.equals("Water")) {
						  SchematicLoaded = Schematic.loadSchematic(new File(plugin.WaterSchematics.getAbsolutePath() + "/" + Schematics[Schem]));
					  }
				  } catch (IOException e) {
					e.printStackTrace();
					return;
				  }
				  Schematic.pasteSchematic(chunk.getWorld(), new Location(chunk.getWorld(), realX, y, realZ), SchematicLoaded, plugin);
			  }
/*     */   }
/*     */ }