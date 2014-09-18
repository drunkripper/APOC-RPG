package com.Plugin.Items;

import com.Plugin.Main.Plugin;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

public class ItemAPI {
	
	private static Material[] Materials = { Material.WOOD_AXE, Material.WOOD_HOE, Material.WOOD_PICKAXE, Material.WOOD_SPADE, Material.WOOD_SWORD, Material.STONE_AXE, Material.STONE_HOE, Material.STONE_PICKAXE, Material.STONE_SPADE, Material.STONE_SWORD, Material.IRON_AXE, Material.IRON_HOE, Material.IRON_PICKAXE, Material.IRON_SPADE, Material.IRON_SWORD, Material.DIAMOND_AXE, Material.DIAMOND_HOE, Material.DIAMOND_PICKAXE, Material.DIAMOND_SPADE, Material.DIAMOND_SWORD, Material.GOLD_AXE, Material.GOLD_HOE, Material.GOLD_PICKAXE, Material.GOLD_SPADE, Material.GOLD_SWORD };

	public static ItemStack createItem() {
		Material Material = Materials[Plugin.Random.nextInt(Materials.length)];
		ItemStack Item = new ItemStack(Material);
		Item.setDurability((short)Plugin.Random.nextInt(120));

		//I assume we want to put the enchantment subsystem here? 
//Enchantments will be 1-10 vanilla enchants, or ridiculous other types too? 

return Item;
	}

	public static void fillChest(Block block) {
		if (block.getType() == Material.CHEST) {
			Chest Chest = (Chest)block.getState();

			Chest.getInventory().clear();
			int Amount = Plugin.Random.nextInt(6) + 2;
			for (int i = 0; i < Amount; i++)
				Chest.getInventory().addItem(createItem());
    }
  }
}