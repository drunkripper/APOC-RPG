package com.APOCRPG.Generation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import com.APOCRPG.API.ItemAPI;
import com.APOCRPG.Main.Plugin;
import com.APOCRPG.jnbt.ByteArrayTag;
import com.APOCRPG.jnbt.CompoundTag;
import com.APOCRPG.jnbt.NBTInputStream;
import com.APOCRPG.jnbt.ShortTag;
import com.APOCRPG.jnbt.StringTag;
import com.APOCRPG.jnbt.Tag;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.CreatureType;

// imports above;



@SuppressWarnings("deprecation")
public class Schematic {
 
    private byte[] blocks;
    private byte[] data;
    private short width;
    private short length;
    private short height;
 //Constructor for schematic objects 
    public Schematic(byte[] blocks, byte[] data, short width, short length, short height) {
        this.blocks = blocks;
        this.data = data;
        this.width = width;
        this.length = length;
        this.height = height;
    }
    //Getters and setters 
    public byte[] getBlocks() {
        return blocks;
    }
 
    public byte[] getData() {
        return data;
    }
 
    public short getWidth() {
        return width;
    }
 
    public short getLength(){
        return length;
    }
 
    public short getHeight() {
        return height;
    }
    
    //File IO load method
public static Schematic loadSchematic(File file) throws IOException {
        FileInputStream stream = new FileInputStream(file);
        NBTInputStream nbtStream = new NBTInputStream(new GZIPInputStream(stream));
 
        CompoundTag schematicTag = (CompoundTag) nbtStream.readTag();
        if (!schematicTag.getName().equals("Schematic")) {
        	nbtStream.close();
            throw new IllegalArgumentException("Tag \"Schematic\" does not exist or is not first");
        }
 
        Map<String, Tag> schematic = schematicTag.getValue();
        if (!schematic.containsKey("Blocks")) {
        	nbtStream.close();
            throw new IllegalArgumentException("Schematic file is missing a \"Blocks\" tag");
        }
 
        short width = getChildTag(schematic, "Width", ShortTag.class).getValue();
        short length = getChildTag(schematic, "Length", ShortTag.class).getValue();
        short height = getChildTag(schematic, "Height", ShortTag.class).getValue();
 
        String materials = getChildTag(schematic, "Materials", StringTag.class).getValue();
        if (!materials.equals("Alpha")) {
        	nbtStream.close();
            throw new IllegalArgumentException("Schematic file is not an Alpha schematic");
        }
 
        byte[] blocks = getChildTag(schematic, "Blocks", ByteArrayTag.class).getValue();
        byte[] blockData = getChildTag(schematic, "Data", ByteArrayTag.class).getValue();
        nbtStream.close();
        return new Schematic(blocks, blockData, width, length, height);
    }
    //Puts the schematic into the world
    public static void pasteSchematic(World world, Location loc, Schematic schematic) {
        byte[] blocks = schematic.getBlocks();
        byte[] blockData = schematic.getData();
 
        short length = schematic.getLength();
        short width = schematic.getWidth();
        short height = schematic.getHeight();
 
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                for (int z = 0; z < length; ++z) {
                    int index = y * width * length + z * width + x;
                    Block block = new Location(world, x + loc.getX(), y + loc.getY(), z + loc.getZ()).getBlock();
                    block.setTypeIdAndData(blocks[index], blockData[index], true);
                    if (block.getType() == Material.CHEST) {
                    	ItemAPI.fillChest(block);
                    } else if (block.getType() == Material.MOB_SPAWNER) {
                    	CreatureSpawner Spawner = (CreatureSpawner) block.getState();
                    	if (Plugin.Random.nextInt(100) <= 50) {
                    		Spawner.setCreatureType(CreatureType.ZOMBIE);
                    	} else {
                    		Spawner.setCreatureType(CreatureType.SKELETON);
                    	}
                    }
                }
            }
        }
    }
 
    private static <T extends Tag> T getChildTag(Map<String, Tag> items, String key, Class<T> expected) throws IllegalArgumentException {
        if (!items.containsKey(key)) {
            throw new IllegalArgumentException("Schematic file is missing a \"" + key + "\" tag");
        }
        Tag tag = items.get(key);
        if (!expected.isInstance(tag)) {
            throw new IllegalArgumentException(key + " tag is not of tag type " + expected.getName());
        }
        return expected.cast(tag);
    }
}