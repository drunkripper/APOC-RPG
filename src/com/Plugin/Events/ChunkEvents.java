package com.Plugin.Events;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
	private boolean isBlockValid(World world, int x, int i, int z) {
		//Add a config element for size of spawn flat
		
		int m = 5; // Step size. Diameter is 4n.
		
		//Valid materials are set here.
		
		ArrayList<Material> valid = new ArrayList<Material>();
		valid.add(Material.DIRT);
		valid.add(Material.SAND);
		valid.add(Material.GRASS);
		valid.add(Material.STONE);
		
		
		// Logic below. Don't change this unless you want to break things.
		if(valid.contains(world.getBlockAt(x, i, z))
				&&valid.contains(world.getBlockAt(x+2*m, i, z+2*m))
				&&valid.contains(world.getBlockAt(x-2*m, i, z-2*m))
				&&valid.contains(world.getBlockAt(x+2*m, i, z))
				&&valid.contains(world.getBlockAt(x, i, z+2*m))
				&&valid.contains(world.getBlockAt(x-2*m, i, z))
				&&valid.contains(world.getBlockAt(x, i, z-2*m))
				&&valid.contains(world.getBlockAt(x+2*m, i, z-2*m))
				&&valid.contains(world.getBlockAt(x-2*m, i, z+2*m))
				&&valid.contains(world.getBlockAt(x+m, i, z))
				&&valid.contains(world.getBlockAt(x, i, z+m))
				&&valid.contains(world.getBlockAt(x-m, i, z))
				&&valid.contains(world.getBlockAt(x, i, z-m))
				&&valid.contains(world.getBlockAt(x+m, i, z+m))
				&&valid.contains(world.getBlockAt(x-m, i, z-m))
				&&valid.contains(world.getBlockAt(x+m, i, z-m))
				&&valid.contains(world.getBlockAt(x-m, i, z+m)))
		return true;
		else 
			return false;
	}
	@EventHandler
	public void onChunkLoaded(ChunkPopulateEvent event) {
		if (Plugin.Random.nextInt(100) <= Plugin.Settings.getInt("Dungeons.Chance")) {
			World World = event.getWorld();
			Chunk Chunk = event.getChunk();
			
			int x = 16*Chunk.getX()*Plugin.Random.nextInt(15);
			int y=0;
			int z=16*Chunk.getZ()*Plugin.Random.nextInt(15);
			
			for (int i=World.getMaxHeight()-1; i>0; i--) {
				//if (World.getBlockAt(x, i, z).getType() != Material.AIR) 
				if(isBlockValid(World,x, i, z))
				{
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
