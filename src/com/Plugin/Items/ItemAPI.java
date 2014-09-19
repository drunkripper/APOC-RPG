package com.Plugin.Items;

import com.Plugin.Main.Plugin;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ItemAPI {
	
	private static Material[] Materials = {  Material.BOW, Material.WOOD_AXE, Material.WOOD_HOE, Material.WOOD_PICKAXE, Material.WOOD_SPADE, Material.WOOD_SWORD, Material.STONE_AXE, Material.STONE_HOE, Material.STONE_PICKAXE, Material.STONE_SPADE, Material.STONE_SWORD, Material.IRON_AXE, Material.IRON_HOE, Material.IRON_PICKAXE, Material.IRON_SPADE, Material.IRON_SWORD, Material.DIAMOND_AXE, Material.DIAMOND_HOE, Material.DIAMOND_PICKAXE, Material.DIAMOND_SPADE, Material.DIAMOND_SWORD, Material.GOLD_AXE, Material.GOLD_HOE, Material.GOLD_PICKAXE, Material.GOLD_SPADE, Material.GOLD_SWORD, Material.LEATHER_HELMET,Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS,Material.LEATHER_CHESTPLATE,Material.IRON_HELMET, Material.IRON_LEGGINGS,Material.IRON_BOOTS,Material.IRON_CHESTPLATE,Material.GOLD_HELMET,Material.GOLD_LEGGINGS, Material.GOLD_BOOTS, Material.GOLD_CHESTPLATE,Material.DIAMOND_HELMET,Material.DIAMOND_LEGGINGS,Material.DIAMOND_BOOTS, Material.DIAMOND_CHESTPLATE,Material.CHAINMAIL_BOOTS,Material.CHAINMAIL_CHESTPLATE,Material.CHAINMAIL_HELMET,Material.CHAINMAIL_LEGGINGS };
	private static Material[][] Armor = {{Material.LEATHER_HELMET, Material.IRON_HELMET, Material.GOLD_HELMET, Material.DIAMOND_HELMET,Material.CHAINMAIL_HELMET},{Material.LEATHER_CHESTPLATE, Material.IRON_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.DIAMOND_CHESTPLATE,Material.CHAINMAIL_CHESTPLATE},{Material.LEATHER_LEGGINGS, Material.IRON_LEGGINGS, Material.GOLD_LEGGINGS, Material.DIAMOND_LEGGINGS,Material.CHAINMAIL_LEGGINGS},{Material.LEATHER_BOOTS, Material.IRON_BOOTS, Material.GOLD_BOOTS, Material.DIAMOND_BOOTS,Material.CHAINMAIL_BOOTS}, { Material.BOW, Material.WOOD_AXE, Material.WOOD_HOE, Material.WOOD_PICKAXE, Material.WOOD_SPADE, Material.WOOD_SWORD, Material.STONE_AXE, Material.STONE_HOE, Material.STONE_PICKAXE, Material.STONE_SPADE, Material.STONE_SWORD, Material.IRON_AXE, Material.IRON_HOE, Material.IRON_PICKAXE, Material.IRON_SPADE, Material.IRON_SWORD, Material.DIAMOND_AXE, Material.DIAMOND_HOE, Material.DIAMOND_PICKAXE, Material.DIAMOND_SPADE, Material.DIAMOND_SWORD, Material.GOLD_AXE, Material.GOLD_HOE, Material.GOLD_PICKAXE, Material.GOLD_SPADE, Material.GOLD_SWORD}};
	/**
	 * Create Armor                         
	 * <p>
	 * Creates an armor item based on a passed in parameter, with random enchants and type.
	 * 
	 * @param  type 0 = Helmet, 1 = Chestplate, 2 = Leggings, 3 = Boots, 4 = Held weapons
	 * @return The generated Item
	 */
	public static ItemStack createArmor(int type) {
		Material Material = Armor[type][Plugin.Random.nextInt(Armor[type].length)];
		ItemStack Item = new ItemStack(Material);
		return diablofy(Item);
	}
/**
* Diablofy wrapper:
* <p>
* this will diablofy the item that is passed to it in the classic way. This is a wrapper to preserve method signature.
*/
public static ItemStack diablofy(ItemStack Item) {
return diablofy(Item, 0);
}
	/**
	 * Diablofy
	 * <p>
	 * Takes an item and adds random ass enchantments to it.
	 * @param Item
* @param type  an Int that determines how to diablofy- 0=ignored (Normal) 1=armor (Useful) 2=weapon (Useful) 3=tool (Useful) 4=bow (useful)
	 * @return Funky Item
	 */
	public static ItemStack diablofy(ItemStack Item, int type) {

		Item.setDurability((short)Plugin.Random.nextInt(120));
		////////////////Set max enchants to this value///////////////
		int maxEnchants = 6;
		/////////////////
		int[] enchantsTable = {0, 1, 2, 3,4 ,5, 6, 7, 16, 17,18, 19, 20, 21,32,33,34,35,48,49,50,51};
int[] armorTable ={0,1,2,3,4,5,6,7,34};// 9
int[] toolTable = {32,33,34,35}; //4
int[] weaponTable = {16,17,18,19,20,21,34}; //7
int[] bowTable = {48,49,50,51}; //4
		int hasEnchants = Plugin.Random.nextInt(maxEnchants);
		for(int i = 0; i<hasEnchants;i++)
		{
			
		if(type==0)	Item.addUnsafeEnchantment(Enchantment.getById(enchantsTable[Plugin.Random.nextInt(22)]), Plugin.Random.nextInt(10)+1);
else if(type==1)
	//Armor	Item.addUnsafeEnchantment(Enchantment.getById(armorTable[Plugin.Random.nextInt(9)]), Plugin.Random.nextInt(10)+1);
else if(type==2)
//Weapon
Item.addUnsafeEnchantment(Enchantment.getById(weaponTable[Plugin.Random.nextInt(7)]), Plugin.Random.nextInt(10)+1);
else if(type==3)
//Tool
Item.addUnsafeEnchantment(Enchantment.getById(toolTable[Plugin.Random.nextInt(4)]), Plugin.Random.nextInt(10)+1);
else if(type==4)
//Bow
Item.addUnsafeEnchantment(Enchantment.getById(bowTable[Plugin.Random.nextInt(4)]), Plugin.Random.nextInt(10)+1);

		}
		

return Item;
	}
	public static ItemStack createItem() {
		Material Material = Materials[Plugin.Random.nextInt(Materials.length)];
		ItemStack Item = new ItemStack(Material);
		return diablofy(Item);
		
		
	}

public static ItemStack generateUsefulItem(){

return createItem();
//placeholder

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