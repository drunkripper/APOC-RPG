package com.Plugin.Items;

import java.util.ArrayList;
import java.util.List;

import com.Plugin.Main.Plugin;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
		ItemMeta Meta = Item.getItemMeta();
		Meta.setDisplayName(Plugin.Settings.getRandomPrefix() + " " + Plugin.Settings.getRandomSuffix());
	
		////////////////Set max enchants to this value///////////////
		int maxEnchants = 6;
		int minEnchants = 1;
		/////////////////
		Enchantment[] enchantsTable = {Enchantment.PROTECTION_ENVIRONMENTAL
				,Enchantment.PROTECTION_FIRE
				,Enchantment.PROTECTION_FALL
				,Enchantment.PROTECTION_EXPLOSIONS
				,Enchantment.PROTECTION_PROJECTILE
				,Enchantment.OXYGEN
				,Enchantment.WATER_WORKER
				,Enchantment.THORNS
				,Enchantment.DAMAGE_ALL
				,Enchantment.DAMAGE_UNDEAD
				,Enchantment.DAMAGE_ARTHROPODS
				,Enchantment.KNOCKBACK
				,Enchantment.FIRE_ASPECT
				,Enchantment.LOOT_BONUS_MOBS
				,Enchantment.DIG_SPEED
				,Enchantment.SILK_TOUCH
				,Enchantment.DURABILITY
				,Enchantment.LOOT_BONUS_BLOCKS
				,Enchantment.ARROW_DAMAGE
				,Enchantment.ARROW_KNOCKBACK
				,Enchantment.ARROW_FIRE
				,Enchantment.ARROW_INFINITE
				};

// 9
Enchantment[] armorTable = {
		Enchantment.PROTECTION_ENVIRONMENTAL
		,Enchantment.PROTECTION_FIRE
		,Enchantment.PROTECTION_FALL
		,Enchantment.PROTECTION_EXPLOSIONS
		,Enchantment.PROTECTION_PROJECTILE
		,Enchantment.OXYGEN
		,Enchantment.WATER_WORKER
		,Enchantment.THORNS
		,Enchantment.DURABILITY
};
//4
Enchantment[] toolTable = {Enchantment.DIG_SPEED
		,Enchantment.SILK_TOUCH
		,Enchantment.DURABILITY
		,Enchantment.LOOT_BONUS_BLOCKS}; //7
Enchantment[] weaponTable = {
		Enchantment.DAMAGE_ALL
		,Enchantment.DAMAGE_UNDEAD
		,Enchantment.DAMAGE_ARTHROPODS
		,Enchantment.KNOCKBACK
		,Enchantment.FIRE_ASPECT
		,Enchantment.LOOT_BONUS_MOBS
		,Enchantment.DURABILITY
};
//4
Enchantment[] bowTable = {
		Enchantment.ARROW_DAMAGE
		,Enchantment.ARROW_KNOCKBACK
		,Enchantment.ARROW_FIRE
		,Enchantment.ARROW_INFINITE
};
		int hasEnchants = Plugin.Random.nextInt(maxEnchants-minEnchants)+minEnchants;
		for(int i = 0; i<hasEnchants;i++)
		{
			
		if(type==0)	Meta.addEnchant(enchantsTable[Plugin.Random.nextInt(22)], Plugin.Random.nextInt(10)+1,true);
else if(type==1)
	//Armor
	Meta.addEnchant(armorTable[Plugin.Random.nextInt(9)], Plugin.Random.nextInt(10)+1,true);
else if(type==2)
//Weapon
Meta.addEnchant(weaponTable[Plugin.Random.nextInt(7)], Plugin.Random.nextInt(10)+1,true);
else if(type==3)
//Tool
Meta.addEnchant(toolTable[Plugin.Random.nextInt(4)], Plugin.Random.nextInt(10)+1,true);
else if(type==4)
//Bow
Meta.addEnchant(bowTable[Plugin.Random.nextInt(4)], Plugin.Random.nextInt(10)+1,true);
if(Plugin.Random.nextGaussian()<0.1)
{
	List<String> lore = new ArrayList<String>();
	lore.add("(Socket)");
	Meta.setLore(lore);
}
		}
		
		
		Item.setItemMeta(Meta);

return Item;
	}
	public static ItemStack createItem() {
		Material Material = Materials[Plugin.Random.nextInt(Materials.length)];
		ItemStack Item = new ItemStack(Material);
		return diablofy(Item);
		
		
	}
	public static ItemStack createSocket() {
		//Effects with -ing are targeted on the enemy. Otherwise targeted on the holder/wearer.
		ItemStack socket = new ItemStack(Material.EMERALD);
		String[] Effects = { "Speed",
						"Slowing",
						"Haste",
						"Fatiguing",
						"Strength",
						"Sanctifying",
						"Desecrating",
						"Jumpfulness",
						"Nauseating",
						"Regeneration",
						"Resistance",
						"Fire Resistance",
						"Scuba",
						"Invisibility",
						"Blinding",
						"Night Vision",
						"Hungering",
						"Weakening",
						"Poisoning",
						"Withering",
						"Health",
						"Absorption",
						"Saturation"}; //All these effects are helpful for the user.
										//Unpleasant effects can be added as well.
		ItemMeta Meta = socket.getItemMeta();

		String Effect = Effects[Plugin.Random.nextInt(Effects.length)];
		int effectLevel = Plugin.Random.nextInt(3) + 1;
		int minDur = Plugin.Random.nextInt(2);
		int secDur = Plugin.Random.nextInt(50)+10;
		List<String> Lore = new ArrayList<String>();
		Lore.add("(Socket)");
		Lore.add("------");
		Lore.add(Effect);
		Lore.add("Level "+ effectLevel);
		Lore.add(minDur + ":" + secDur);
		Meta.setLore(Lore);
		socket.setItemMeta(Meta);
		return socket;
	}

public static ItemStack generateUsefulItem(){

return generateUsefulItem(0);
//placeholder to create generic signature

}

public static ItemStack generateUsefulItem(int variety){
int[] toolMaterials = { 1,2,3,4,6,7,8,9,11,12,13,14,16,17,18,19,21,22,23,24};
int[] weapMaterials = { 1,5,6,10,11,15,16,20,21,25};
//armor materials is 26 +r20
if(variety==0)
variety+=Plugin.Random.nextInt(4) +1;
if(variety==1) {
//armor
Material Material = Materials[26+Plugin.Random.nextInt(20)];
		ItemStack Item = new ItemStack(Material);
		return diablofy(Item, variety);
		
}
else if (variety ==2) {
//weapon
Material Material = Materials[weapMaterials[Plugin.Random.nextInt(weapMaterials.length)]];
		ItemStack Item = new ItemStack(Material);
		return diablofy(Item, variety);
		
}
else if(variety ==3) {
//tool
Material Material = Materials[toolMaterials[Plugin.Random.nextInt(toolMaterials.length)]];
		ItemStack Item = new ItemStack(Material);
		return diablofy(Item, variety);
		
}
else if(variety ==4){
//bow
Material Material = Materials[0];
		ItemStack Item = new ItemStack(Material);
		return diablofy(Item, variety);
		
}
else return generateUsefulItem();
}
	
public static void fillChest(Block block) {
		if (block.getType() == Material.CHEST) {
			Chest Chest = (Chest)block.getState();

			Chest.getInventory().clear();
			int Amount = Plugin.Random.nextInt(6) + 2;
			for (int i = 0; i < Amount; i++) {
				if (Plugin.Random.nextInt(100) <= 10) {
					Chest.getInventory().addItem(createSocket());
				} else {
					Chest.getInventory().addItem(createItem());
				}
			}
    }
  }
}