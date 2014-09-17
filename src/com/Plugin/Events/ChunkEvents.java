package com.Plugin.Events;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;

import com.Plugin.Generation.Schematic;
import com.Plugin.Main.Plugin;

public class ChunkEvents implements Listener {
	
	@EventHandler
	public void onChunkLoaded(ChunkPopulateEvent event) {
		if (Plugin.Random.nextInt(100) <= Plugin.Settings.getInt("Dungeons.Chance")) {
			World World = event.getWorld();
			Chunk Chunk = event.getChunk();
			
			int x = 16*Chunk.getX()*Plugin.Random.nextInt(15);
			int y=0;
			int z=16*Chunk.getZ()*Plugin.Random.nextInt(15);
			
			for (int i=World.getMaxHeight()-1; i>0; i--) {
				if (World.getBlockAt(x, i, z).getType() != Material.AIR) {
					y = i;
					break;
				}
			}
			
			String[] List = Plugin.LandRuins.list();
			if (List.length > 0) {
				File File = new File(Plugin.LandRuins + "/" + List[Plugin.Random.nextInt(List.length)]);
				try {
					Schematic.pasteSchematic(World, new Location(World, x, y, z), Schematic.loadSchematic(File));
					System.out.println("Schematic Pasted at " + x + ", " + y + ", " + z);
				} catch (IOException e) {
					System.err.println(ChatColor.RED + "APOC-RPG Error!");
					e.printStackTrace();
				}
			}
		}
	}
	
}
