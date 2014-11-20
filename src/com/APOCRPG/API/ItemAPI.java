package com.APOCRPG.API;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;

import com.APOCRPG.Main.Plugin;
import com.APOCRPG.items.IdentifyTome;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemAPI {
	
	private static Material[] Materials = {  Material.BOW, Material.WOOD_AXE, Material.WOOD_HOE, Material.WOOD_PICKAXE, Material.WOOD_SPADE, Material.WOOD_SWORD, Material.STONE_AXE, Material.STONE_HOE, Material.STONE_PICKAXE, Material.STONE_SPADE, Material.STONE_SWORD, Material.IRON_AXE, Material.IRON_HOE, Material.IRON_PICKAXE, Material.IRON_SPADE, Material.IRON_SWORD, Material.DIAMOND_AXE, Material.DIAMOND_HOE, Material.DIAMOND_PICKAXE, Material.DIAMOND_SPADE, Material.DIAMOND_SWORD, Material.GOLD_AXE, Material.GOLD_HOE, Material.GOLD_PICKAXE, Material.GOLD_SPADE, Material.GOLD_SWORD, Material.LEATHER_HELMET,Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS,Material.LEATHER_CHESTPLATE,Material.IRON_HELMET, Material.IRON_LEGGINGS,Material.IRON_BOOTS,Material.IRON_CHESTPLATE,Material.GOLD_HELMET,Material.GOLD_LEGGINGS, Material.GOLD_BOOTS, Material.GOLD_CHESTPLATE,Material.DIAMOND_HELMET,Material.DIAMOND_LEGGINGS,Material.DIAMOND_BOOTS, Material.DIAMOND_CHESTPLATE,Material.CHAINMAIL_BOOTS,Material.CHAINMAIL_CHESTPLATE,Material.CHAINMAIL_HELMET,Material.CHAINMAIL_LEGGINGS };
	private static Material[][] Armor = {{Material.LEATHER_HELMET, Material.IRON_HELMET, Material.GOLD_HELMET, Material.DIAMOND_HELMET,Material.CHAINMAIL_HELMET}
										,{Material.LEATHER_CHESTPLATE, Material.IRON_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.DIAMOND_CHESTPLATE,Material.CHAINMAIL_CHESTPLATE}
										,{Material.LEATHER_LEGGINGS, Material.IRON_LEGGINGS, Material.GOLD_LEGGINGS, Material.DIAMOND_LEGGINGS,Material.CHAINMAIL_LEGGINGS}
										,{Material.LEATHER_BOOTS, Material.IRON_BOOTS, Material.GOLD_BOOTS, Material.DIAMOND_BOOTS,Material.CHAINMAIL_BOOTS}
										,{Material.BOW, Material.WOOD_AXE, Material.WOOD_HOE, Material.WOOD_PICKAXE, Material.WOOD_SPADE, Material.WOOD_SWORD, Material.STONE_AXE, Material.STONE_HOE, Material.STONE_PICKAXE, Material.STONE_SPADE, Material.STONE_SWORD, Material.IRON_AXE, Material.IRON_HOE, Material.IRON_PICKAXE, Material.IRON_SPADE, Material.IRON_SWORD, Material.DIAMOND_AXE, Material.DIAMOND_HOE, Material.DIAMOND_PICKAXE, Material.DIAMOND_SPADE, Material.DIAMOND_SWORD, Material.GOLD_AXE, Material.GOLD_HOE, Material.GOLD_PICKAXE, Material.GOLD_SPADE, Material.GOLD_SWORD}
										};
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
		return diablofy(Item, Plugin.Random.nextInt(5));
	}
	
	public static ItemStack createTome(){
		IdentifyTome tome = new IdentifyTome();
		Plugin.addLoreText(tome, Plugin.LORE_TOME);
		return tome;
	}
	
	/**
	* Diablofy wrapper:
	* <p>
	* this will diablofy the item that is passed to it in the classic way. This is a wrapper to preserve method signature.
	*/
	public static ItemStack diablofy(ItemStack Item, int tier) {
		return diablofy(Item, 0, tier);
	}
	/**
	 * Diablofy
	 * <p>
	 * Takes an item and adds random ass enchantments to it.
	 * @param Item
	 * @param type  an Int that determines how to diablofy- 0=ignored (Normal) 1=armor (Useful) 2=weapon (Useful) 3=tool (Useful) 4=bow (useful)
	 * @return Funky Item
	 */
	public static ItemStack diablofy(ItemStack item, int type, int tier) {
		boolean allowEnchant = false;
		int maxEnchants = 0;
		int minEnchants = 0;
		int maxEnchLvl  = 1;
		int nbrEnchants = 0;
		int maxSockets  = 0;
		boolean tierNaming = false;
		ChatColor nameColor = ChatColor.WHITE;
		
		// local variables based upon tier value
		if ( tier == Plugin.TIER_COMMON ) {
			allowEnchant = Plugin.TIER_COMMON_ENCHANTS_ALLOW;
			maxEnchants = Plugin.TIER_COMMON_ENCHANTS_MAX;
			minEnchants = Plugin.TIER_COMMON_ENCHANTS_MIN;
			maxEnchLvl = Plugin.TIER_COMMON_ENCHANTS_MAX_LVL;
			maxSockets = Plugin.TIER_COMMON_SOCKETS_MAX;
			tierNaming = Plugin.TIER_COMMON_NAMING;
			nameColor = ChatColor.valueOf(Plugin.TIER_COMMON_NAMES_COLOR);
			
		} else if ( tier == Plugin.TIER_UNCOMMON ) {
			allowEnchant = Plugin.TIER_UNCOMMON_ENCHANTS_ALLOW;
			maxEnchants = Plugin.TIER_UNCOMMON_ENCHANTS_MAX;
			minEnchants = Plugin.TIER_UNCOMMON_ENCHANTS_MIN;
			maxEnchLvl = Plugin.TIER_UNCOMMON_ENCHANTS_MAX_LVL;
			maxSockets = Plugin.TIER_UNCOMMON_SOCKETS_MAX;
			tierNaming = Plugin.TIER_UNCOMMON_NAMING;
			nameColor = ChatColor.valueOf(Plugin.TIER_UNCOMMON_NAMES_COLOR);
		} else if ( tier == Plugin.TIER_RARE ) {
			allowEnchant = Plugin.TIER_RARE_ENCHANTS_ALLOW;
			maxEnchants = Plugin.TIER_RARE_ENCHANTS_MAX;
			minEnchants = Plugin.TIER_RARE_ENCHANTS_MIN;
			maxEnchLvl = Plugin.TIER_RARE_ENCHANTS_MAX_LVL;
			maxSockets = Plugin.TIER_RARE_SOCKETS_MAX;
			tierNaming = Plugin.TIER_RARE_NAMING;
			nameColor = ChatColor.valueOf(Plugin.TIER_RARE_NAMES_COLOR);
		} else if ( tier == Plugin.TIER_UNIQUE ) {
			allowEnchant = Plugin.TIER_UNIQUE_ENCHANTS_ALLOW;
			maxEnchants = Plugin.TIER_UNIQUE_ENCHANTS_MAX;
			minEnchants = Plugin.TIER_UNIQUE_ENCHANTS_MIN;
			maxEnchLvl = Plugin.TIER_UNIQUE_ENCHANTS_MAX_LVL;
			maxSockets = Plugin.TIER_UNIQUE_SOCKETS_MAX;
			tierNaming = Plugin.TIER_UNIQUE_NAMING;
			nameColor = ChatColor.valueOf(Plugin.TIER_UNIQUE_NAMES_COLOR);
		} else if ( tier == Plugin.TIER_SET ) {
			allowEnchant = Plugin.TIER_SET_ENCHANTS_ALLOW;
			maxEnchants = Plugin.TIER_SET_ENCHANTS_MAX;
			minEnchants = Plugin.TIER_SET_ENCHANTS_MIN;
			maxEnchLvl = Plugin.TIER_SET_ENCHANTS_MAX_LVL;
			maxSockets = Plugin.TIER_SET_SOCKETS_MAX;
			tierNaming = Plugin.TIER_SET_NAMING;
			nameColor = ChatColor.valueOf(Plugin.TIER_SET_NAMES_COLOR);
		} else if ( tier == Plugin.TIER_LEGENDARY ) {
			allowEnchant = Plugin.TIER_LEGENDARY_ENCHANTS_ALLOW;
			maxEnchants = Plugin.TIER_LEGENDARY_ENCHANTS_MAX;
			minEnchants = Plugin.TIER_LEGENDARY_ENCHANTS_MIN;
			maxEnchLvl = Plugin.TIER_LEGENDARY_ENCHANTS_MAX_LVL;
			maxSockets = Plugin.TIER_LEGENDARY_SOCKETS_MAX;
			tierNaming = Plugin.TIER_LEGENDARY_NAMING;
			nameColor = ChatColor.valueOf(Plugin.TIER_LEGENDARY_NAMES_COLOR);
		}
		if ( allowEnchant && maxEnchants > 0 ) {
			// nbrEnchants = minEnchants + random difference between Max and Min values
			nbrEnchants = minEnchants + Plugin.Random.nextInt( maxEnchants - minEnchants );
			
			// get available enchantments for item.
			ArrayList<Enchantment> enchList = (ArrayList<Enchantment>)Plugin.getEnchantmentsFor(item);
			if ( enchList != null && !enchList.isEmpty()) {
				int enchApplied = 0;
				// add enchants
				for ( int i = 0; i < nbrEnchants && enchApplied < enchList.size(); i++) {
					Enchantment ench = null;
					do {
						ench = enchList.get(Plugin.Random.nextInt(enchList.size()));
					} while (item.getItemMeta().hasEnchant(ench));
					// add echantment with a random power level determined by the tier's max enchant level
					item.addUnsafeEnchantment(ench, Plugin.Random.nextInt(maxEnchLvl)+1);
					enchApplied++;
				}
			}
		}
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		if ( lore == null ) { lore = new ArrayList<String>();}
		
		// add sockets
		if ( maxSockets > 0 ){
			int nbrSockets = Plugin.Random.nextInt(maxSockets) + 1;
			for ( int s = 0; s < nbrSockets; s++ ){
				lore.add(Plugin.LORE_ITEM_SOCKET);
			}
			meta.setLore(lore);
		}
		
		if ( tierNaming ){
			// if ( tier == set ) set name + prefix + suffix else prefix + suffix 
			meta.setDisplayName(nameColor+ ( tier == Plugin.TIER_SET ? Plugin.Settings.getRandomSet() + " " : "" ) + Plugin.Settings.getRandomPrefix() + " " + Plugin.Settings.getRandomSuffix());
		}
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack createItem() {
		int tier = 0;
		int tierChance = Plugin.Random.nextInt(100) + 1;
		if ( tierChance <= Plugin.TIER_LEGENDARY_MAX_CHANCE ) {
			tier = Plugin.TIER_LEGENDARY;
		} else if ( tierChance <= Plugin.TIER_SET_MAX_CHANCE ) {
			tier = Plugin.TIER_SET;
		} else if ( tierChance <= Plugin.TIER_UNIQUE_MAX_CHANCE ) {
			tier = Plugin.TIER_UNIQUE;
		} else if ( tierChance <= Plugin.TIER_RARE_MAX_CHANCE ) {
			tier = Plugin.TIER_RARE;
		} else if ( tierChance <= Plugin.TIER_UNCOMMON_MAX_CHANCE ) {
			tier = Plugin.TIER_UNCOMMON;
		} else {
			tier = Plugin.TIER_COMMON;
		}
		return createItem( tier);
	}
	
	public static ItemStack createItem( int tier ) {
		Material Material = Materials[Plugin.Random.nextInt(Materials.length)];
		ItemStack Item = new ItemStack(Material);
		return diablofy(Item, tier);
	}
	
	public static ItemStack createUnidentified() {
		Material material = Materials[Plugin.Random.nextInt(Materials.length)];
		ItemStack item = new ItemStack(material);
		Plugin.clearLore(item);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Plugin.DISPLAY_NAME_UNIDENTIFIED_ITEM);
		meta.setLore(null);
		item.setItemMeta(meta);
		return item;
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
			return diablofy(Item, variety, Plugin.Random.nextInt(5));
				
		}
		else if (variety ==2) {
			//weapon
			Material Material = Materials[weapMaterials[Plugin.Random.nextInt(weapMaterials.length)]];
			ItemStack Item = new ItemStack(Material);
			return diablofy(Item, variety, Plugin.Random.nextInt(5));
					
		}
		else if(variety ==3) {
			//tool
			Material Material = Materials[toolMaterials[Plugin.Random.nextInt(toolMaterials.length)]];
			ItemStack Item = new ItemStack(Material);
			return diablofy(Item, variety, Plugin.Random.nextInt(5));
		}
		else if(variety ==4){
			//bow
			Material Material = Materials[0];
			ItemStack Item = new ItemStack(Material);
			return diablofy(Item, variety, Plugin.Random.nextInt(5));
		}
		else return generateUsefulItem();
	}
	
	public static ItemStack createSocket() {
		//Redundant. Using GemAPI now.
		//return socket;
		return GemAPI.createGem();
	}

	public static void fillChest(Block block) {
		if (block.getType() == Material.CHEST) {
			int minItems = Plugin.CHEST_MIN_ITEMS;
			int maxItems = Plugin.CHEST_MAX_ITEMS;
			if ( maxItems > 0 ) {
				Chest Chest = (Chest)block.getState();
				Chest.getInventory().clear();
				int nbrItems = Plugin.Random.nextInt(maxItems-minItems)+1+minItems;
				SortedMap<Integer, String> items = Plugin.chestItems;
				// create items
				for (int i = 0; i < nbrItems; i++) {
					// if Plugin.CHEST_FILL_RPG != TRUE or chestItems is empty create common items
					if ( !Plugin.CHEST_FILL_RPG || items.size() == 0 ) {
						Chest.getInventory().addItem(createItem(0));
					} else {
						int maxRandom = ((Integer)Plugin.chestItems.lastKey()).intValue();
						int random = Plugin.Random.nextInt(maxRandom) + 1;
						String type = null;
						Iterator<Integer> it = (Iterator<Integer>)items.keySet().iterator();
						// find what type was randomly selected
						while (it.hasNext() && type == null){
							Integer key = (Integer)it.next();
							if ( random < key.intValue() ) {
								type = items.get(key);
							}
						}
						
						if ( type.equalsIgnoreCase("GEM") ) {
							Chest.getInventory().addItem(GemAPI.createGem());
						} else if ( type.equalsIgnoreCase("TOME") ) {
							Chest.getInventory().addItem(createTome());
						} else if ( type.equalsIgnoreCase("UNKNOWN") ) {
							Chest.getInventory().addItem(createUnidentified());
						} else {
							int tier = 0;
							switch ( type.toUpperCase() ) {
								case "COMMON" : tier = 0; break;
								case "UNCOMMON" : tier = 1; break;
								case "RARE" : tier = 2; break;
								case "UNIQUE" : tier = 3; break;
								case "SET" : tier = 4; break;
								case "LEGENDARY" : tier = 0; break;
							}
							Chest.getInventory().addItem(createItem(tier));
						}
					}
				}
			}
		}
	}
}