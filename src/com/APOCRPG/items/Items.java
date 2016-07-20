package com.APOCRPG.Items;

import com.APOCRPG.API.EffectAPI;
import com.APOCRPG.Main.Plugin;
import com.APOCRPG.Main.Settings;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

//I'm about to pimp out this class. Just wait, WAIT!

public class Items {

	public static Material[] Materials = { Material.BOW, Material.WOOD_AXE, Material.WOOD_HOE, Material.WOOD_PICKAXE,
			Material.WOOD_SPADE, Material.WOOD_SWORD, Material.STONE_AXE, Material.STONE_HOE, Material.STONE_PICKAXE,
			Material.STONE_SPADE, Material.STONE_SWORD, Material.IRON_AXE, Material.IRON_HOE, Material.IRON_PICKAXE,
			Material.IRON_SPADE, Material.IRON_SWORD, Material.DIAMOND_AXE, Material.DIAMOND_HOE,
			Material.DIAMOND_PICKAXE, Material.DIAMOND_SPADE, Material.DIAMOND_SWORD, Material.GOLD_AXE,
			Material.GOLD_HOE, Material.GOLD_PICKAXE, Material.GOLD_SPADE, Material.GOLD_SWORD, Material.LEATHER_HELMET,
			Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE, Material.IRON_HELMET,
			Material.IRON_LEGGINGS, Material.IRON_BOOTS, Material.IRON_CHESTPLATE, Material.GOLD_HELMET,
			Material.GOLD_LEGGINGS, Material.GOLD_BOOTS, Material.GOLD_CHESTPLATE, Material.DIAMOND_HELMET,
			Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS, Material.DIAMOND_CHESTPLATE, Material.CHAINMAIL_BOOTS,
			Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_HELMET, Material.CHAINMAIL_LEGGINGS };

	public static Material[][] Armor = {
			{ Material.LEATHER_HELMET, Material.IRON_HELMET, Material.GOLD_HELMET, Material.DIAMOND_HELMET,
					Material.CHAINMAIL_HELMET },
			{ Material.LEATHER_CHESTPLATE, Material.IRON_CHESTPLATE, Material.GOLD_CHESTPLATE,
					Material.DIAMOND_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE },
			{ Material.LEATHER_LEGGINGS, Material.IRON_LEGGINGS, Material.GOLD_LEGGINGS, Material.DIAMOND_LEGGINGS,
					Material.CHAINMAIL_LEGGINGS },
			{ Material.LEATHER_BOOTS, Material.IRON_BOOTS, Material.GOLD_BOOTS, Material.DIAMOND_BOOTS,
					Material.CHAINMAIL_BOOTS },
			{ Material.BOW, Material.WOOD_AXE, Material.WOOD_HOE, Material.WOOD_PICKAXE, Material.WOOD_SPADE,
					Material.WOOD_SWORD, Material.STONE_AXE, Material.STONE_HOE, Material.STONE_PICKAXE,
					Material.STONE_SPADE, Material.STONE_SWORD, Material.IRON_AXE, Material.IRON_HOE,
					Material.IRON_PICKAXE, Material.IRON_SPADE, Material.IRON_SWORD, Material.DIAMOND_AXE,
					Material.DIAMOND_HOE, Material.DIAMOND_PICKAXE, Material.DIAMOND_SPADE, Material.DIAMOND_SWORD,
					Material.GOLD_AXE, Material.GOLD_HOE, Material.GOLD_PICKAXE, Material.GOLD_SPADE,
					Material.GOLD_SWORD } };

	public static HashMap<Material, String> GemLookupTable = new HashMap<Material, String>();

	static {
		GemLookupTable.put(Material.WOOD_HOE, EffectAPI.TYPE_TOOL);
		GemLookupTable.put(Material.STONE_HOE, EffectAPI.TYPE_TOOL);
		GemLookupTable.put(Material.IRON_HOE, EffectAPI.TYPE_TOOL);
		GemLookupTable.put(Material.GOLD_HOE, EffectAPI.TYPE_TOOL);
		GemLookupTable.put(Material.DIAMOND_HOE, EffectAPI.TYPE_TOOL);
		GemLookupTable.put(Material.WOOD_SPADE, EffectAPI.TYPE_TOOL);
		GemLookupTable.put(Material.STONE_SPADE, EffectAPI.TYPE_TOOL);
		GemLookupTable.put(Material.IRON_SPADE, EffectAPI.TYPE_TOOL);
		GemLookupTable.put(Material.GOLD_SPADE, EffectAPI.TYPE_TOOL);
		GemLookupTable.put(Material.DIAMOND_SPADE, EffectAPI.TYPE_TOOL);
		GemLookupTable.put(Material.WOOD_PICKAXE, EffectAPI.TYPE_TOOL);
		GemLookupTable.put(Material.STONE_PICKAXE, EffectAPI.TYPE_TOOL);
		GemLookupTable.put(Material.IRON_PICKAXE, EffectAPI.TYPE_TOOL);
		GemLookupTable.put(Material.GOLD_PICKAXE, EffectAPI.TYPE_TOOL);
		GemLookupTable.put(Material.DIAMOND_PICKAXE, EffectAPI.TYPE_TOOL);

		GemLookupTable.put(Material.BOW, EffectAPI.TYPE_BOW);
		GemLookupTable.put(Material.WOOD_AXE, EffectAPI.TYPE_WEAPON);
		GemLookupTable.put(Material.WOOD_SWORD, EffectAPI.TYPE_WEAPON);
		GemLookupTable.put(Material.STONE_AXE, EffectAPI.TYPE_WEAPON);
		GemLookupTable.put(Material.STONE_SWORD, EffectAPI.TYPE_WEAPON);
		GemLookupTable.put(Material.IRON_AXE, EffectAPI.TYPE_WEAPON);
		GemLookupTable.put(Material.IRON_SWORD, EffectAPI.TYPE_WEAPON);
		GemLookupTable.put(Material.GOLD_AXE, EffectAPI.TYPE_WEAPON);
		GemLookupTable.put(Material.GOLD_SWORD, EffectAPI.TYPE_WEAPON);
		GemLookupTable.put(Material.DIAMOND_AXE, EffectAPI.TYPE_WEAPON);
		GemLookupTable.put(Material.DIAMOND_SWORD, EffectAPI.TYPE_WEAPON);
		for (Material m : Armor[0]) {
			GemLookupTable.put(m, EffectAPI.TYPE_ARMOR);
		}
		for (Material m : Armor[1]) {
			GemLookupTable.put(m, EffectAPI.TYPE_ARMOR);
		}
		for (Material m : Armor[2]) {
			GemLookupTable.put(m, EffectAPI.TYPE_ARMOR);
		}
		for (Material m : Armor[3]) {
			GemLookupTable.put(m, EffectAPI.TYPE_ARMOR);
		}
	}

	public static ItemStack createArmor(int type) {
		Material Material = Armor[type][Plugin.Random.nextInt(Armor[type].length)];
		ItemStack Item = new ItemStack(Material);
		return diablofy(Item, Plugin.Random.nextInt(5));
	}

	public static ItemStack createTome() {
		IdentifyTome tome = new IdentifyTome();
		Plugin.addLoreText(tome, Settings.Cfg.LORE_TOME.getString());
		return tome;
	}

	public static ItemStack diablofy(ItemStack Item, int tier) {
		return diablofy(Item, 0, tier);
	}

	public static ItemStack diablofy(ItemStack item, int type, int tier) {
		boolean allowEnchant = false;
		int maxEnchants = 0;
		int minEnchants = 0;
		int maxEnchLvl = 1;
		int nbrEnchants = 0;
		int maxSockets = 0;
		boolean tierNaming = false;
		ChatColor nameColor = ChatColor.WHITE;

		// local variables based upon tier value
		if (tier == Settings.Cfg.TIER_COMMON.getInt()) {
			allowEnchant = Settings.Cfg.TIER_COMMON_ENCHANTS_ALLOW.getBoolean();
			maxEnchants = Settings.Cfg.TIER_COMMON_ENCHANTS_MAX.getInt();
			minEnchants = Settings.Cfg.TIER_COMMON_ENCHANTS_MIN.getInt();
			maxEnchLvl = Settings.Cfg.TIER_COMMON_ENCHANTS_MAX_LVL.getInt();
			maxSockets = Settings.Cfg.TIER_COMMON_SOCKETS_MAX.getInt();
			tierNaming = Settings.Cfg.TIER_COMMON_NAMING.getBoolean();
			nameColor = ChatColor.valueOf(Settings.Cfg.TIER_COMMON_NAMES_COLOR.getString());

		} else if (tier == Settings.Cfg.TIER_UNCOMMON.getInt()) {
			allowEnchant = Settings.Cfg.TIER_UNCOMMON_ENCHANTS_ALLOW.getBoolean();
			maxEnchants = Settings.Cfg.TIER_UNCOMMON_ENCHANTS_MAX.getInt();
			minEnchants = Settings.Cfg.TIER_UNCOMMON_ENCHANTS_MIN.getInt();
			maxEnchLvl = Settings.Cfg.TIER_UNCOMMON_ENCHANTS_MAX_LVL.getInt();
			maxSockets = Settings.Cfg.TIER_UNCOMMON_SOCKETS_MAX.getInt();
			tierNaming = Settings.Cfg.TIER_UNCOMMON_NAMING.getBoolean();
			nameColor = ChatColor.valueOf(Settings.Cfg.TIER_UNCOMMON_NAMES_COLOR.getString());
		} else if (tier == Settings.Cfg.TIER_RARE.getInt()) {
			allowEnchant = Settings.Cfg.TIER_RARE_ENCHANTS_ALLOW.getBoolean();
			maxEnchants = Settings.Cfg.TIER_RARE_ENCHANTS_MAX.getInt();
			minEnchants = Settings.Cfg.TIER_RARE_ENCHANTS_MIN.getInt();
			maxEnchLvl = Settings.Cfg.TIER_RARE_ENCHANTS_MAX_LVL.getInt();
			maxSockets = Settings.Cfg.TIER_RARE_SOCKETS_MAX.getInt();
			tierNaming = Settings.Cfg.TIER_RARE_NAMING.getBoolean();
			nameColor = ChatColor.valueOf(Settings.Cfg.TIER_RARE_NAMES_COLOR.getString());
		} else if (tier == Settings.Cfg.TIER_UNIQUE.getInt()) {
			allowEnchant = Settings.Cfg.TIER_UNIQUE_ENCHANTS_ALLOW.getBoolean();
			maxEnchants = Settings.Cfg.TIER_UNIQUE_ENCHANTS_MAX.getInt();
			minEnchants = Settings.Cfg.TIER_UNIQUE_ENCHANTS_MIN.getInt();
			maxEnchLvl = Settings.Cfg.TIER_UNIQUE_ENCHANTS_MAX_LVL.getInt();
			maxSockets = Settings.Cfg.TIER_UNIQUE_SOCKETS_MAX.getInt();
			tierNaming = Settings.Cfg.TIER_UNIQUE_NAMING.getBoolean();
			nameColor = ChatColor.valueOf(Settings.Cfg.TIER_UNIQUE_NAMES_COLOR.getString());
		} else if (tier == Settings.Cfg.TIER_SET.getInt()) {
			allowEnchant = Settings.Cfg.TIER_SET_ENCHANTS_ALLOW.getBoolean();
			maxEnchants = Settings.Cfg.TIER_SET_ENCHANTS_MAX.getInt();
			minEnchants = Settings.Cfg.TIER_SET_ENCHANTS_MIN.getInt();
			maxEnchLvl = Settings.Cfg.TIER_SET_ENCHANTS_MAX_LVL.getInt();
			maxSockets = Settings.Cfg.TIER_SET_SOCKETS_MAX.getInt();
			tierNaming = Settings.Cfg.TIER_SET_NAMING.getBoolean();
			nameColor = ChatColor.valueOf(Settings.Cfg.TIER_SET_NAMES_COLOR.getString());
		} else if (tier == Settings.Cfg.TIER_LEGENDARY.getInt()) {
			allowEnchant = Settings.Cfg.TIER_LEGENDARY_ENCHANTS_ALLOW.getBoolean();
			maxEnchants = Settings.Cfg.TIER_LEGENDARY_ENCHANTS_MAX.getInt();
			minEnchants = Settings.Cfg.TIER_LEGENDARY_ENCHANTS_MIN.getInt();
			maxEnchLvl = Settings.Cfg.TIER_LEGENDARY_ENCHANTS_MAX_LVL.getInt();
			maxSockets = Settings.Cfg.TIER_LEGENDARY_SOCKETS_MAX.getInt();
			tierNaming = Settings.Cfg.TIER_LEGENDARY_NAMING.getBoolean();
			nameColor = ChatColor.valueOf(Settings.Cfg.TIER_LEGENDARY_NAMES_COLOR.getString());
		}
		if (allowEnchant && maxEnchants > 0) {
			// nbrEnchants = minEnchants + random difference between Max and Min
			// values
			nbrEnchants = minEnchants + Plugin.Random.nextInt(maxEnchants - minEnchants);

			// get available enchantments for item.
			ArrayList<Enchantment> enchList = (ArrayList<Enchantment>) Plugin.getEnchantmentsFor(item);
			if (enchList != null && !enchList.isEmpty()) {
				int enchApplied = 0;
				// add enchants
				for (int i = 0; i < nbrEnchants && enchApplied < enchList.size(); i++) {
					Enchantment ench;
					do {
						ench = enchList.get(Plugin.Random.nextInt(enchList.size()));
					} while (item.getItemMeta().hasEnchant(ench));
					// add echantment with a random power level determined by
					// the tier's max enchant level
					item.addUnsafeEnchantment(ench, Plugin.Random.nextInt(maxEnchLvl) + 1);
					enchApplied++;
				}
			}
		}
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		if (lore == null) {
			lore = new ArrayList<String>();
		}

		// add sockets
		if (maxSockets > 0) {
			int nbrSockets = Plugin.Random.nextInt(maxSockets) + 1;
			for (int s = 0; s < nbrSockets; s++) {
				lore.add(Settings.Cfg.LORE_ITEM_SOCKET.getString());
			}
			meta.setLore(lore);
		}

		if (tierNaming) {
			// if ( tier == set ) set name + prefix + suffix else prefix +
			// suffix
			meta.setDisplayName(nameColor + (tier == Settings.Cfg.TIER_SET.getInt() ? Settings.getRandomSet() + " " : "")
					+ Settings.getRandomPrefix() + " " + Settings.getRandomSuffix());
		}
		item.setItemMeta(meta);

		return item;
	}

	public static ItemStack createItem() {
		int tier = 0;
		int tierChance = Plugin.Random.nextInt(100) + 1;
		if (tierChance <= Settings.Cfg.TIER_LEGENDARY_MAX_CHANCE.getInt()) {
			tier = Settings.Cfg.TIER_LEGENDARY.getInt();
		} else if (tierChance <= Settings.Cfg.TIER_SET_MAX_CHANCE.getInt()) {
			tier = Settings.Cfg.TIER_SET.getInt();
		} else if (tierChance <= Settings.Cfg.TIER_UNIQUE_MAX_CHANCE.getInt()) {
			tier = Settings.Cfg.TIER_UNIQUE.getInt();
		} else if (tierChance <= Settings.Cfg.TIER_RARE_MAX_CHANCE.getInt()) {
			tier = Settings.Cfg.TIER_RARE.getInt();
		} else if (tierChance <= Settings.Cfg.TIER_UNCOMMON_MAX_CHANCE.getInt()) {
			tier = Settings.Cfg.TIER_UNCOMMON.getInt();
		} else {
			tier = Settings.Cfg.TIER_COMMON.getInt();
		}
		return createItem(tier);
	}

	public static ItemStack createItem(int tier) {
		Material Material = Materials[Plugin.Random.nextInt(Materials.length)];
		ItemStack Item = new ItemStack(Material);
		return diablofy(Item, tier);
	}

	public static ItemStack createUnidentified() {
		Material material = Materials[Plugin.Random.nextInt(Materials.length)];
		ItemStack item = new ItemStack(material);
		Plugin.clearLore(item);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Settings.Cfg.DISPLAY_NAME_UNIDENTIFIED_ITEM.getString());
		meta.setLore(null);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack generateUsefulItem() {
		return generateUsefulItem(0);
		// placeholder to create generic signature
	}

	public static ItemStack generateUsefulItem(int variety) {
		int[] toolMaterials = { 1, 2, 3, 4, 6, 7, 8, 9, 11, 12, 13, 14, 16, 17, 18, 19, 21, 22, 23, 24 };
		int[] weapMaterials = { 1, 5, 6, 10, 11, 15, 16, 20, 21, 25 };
		// armor materials is 26 +r20
		if (variety == 0)
			variety += Plugin.Random.nextInt(4) + 1;
		if (variety == 1) {
			// armor
			Material Material = Materials[26 + Plugin.Random.nextInt(20)];
			ItemStack Item = new ItemStack(Material);
			return diablofy(Item, variety, Plugin.Random.nextInt(5));

		} else if (variety == 2) {
			// weapon
			Material Material = Materials[weapMaterials[Plugin.Random.nextInt(weapMaterials.length)]];
			ItemStack Item = new ItemStack(Material);
			return diablofy(Item, variety, Plugin.Random.nextInt(5));

		} else if (variety == 3) {
			// tool
			Material Material = Materials[toolMaterials[Plugin.Random.nextInt(toolMaterials.length)]];
			ItemStack Item = new ItemStack(Material);
			return diablofy(Item, variety, Plugin.Random.nextInt(5));
		} else if (variety == 4) {
			// bow
			Material Material = Materials[0];
			ItemStack Item = new ItemStack(Material);
			return diablofy(Item, variety, Plugin.Random.nextInt(5));
		} else
			return generateUsefulItem();
	}

	public static ItemStack createSocket() {
		// Redundant. Using GemAPI now.
		// return socket;
		return Gems.createGem();
	}

	public static void fillChest(Block block) {
		if (block.getType() == Material.CHEST) {
			int minItems = Settings.Cfg.CHEST_MIN_ITEMS.getInt();
			int maxItems = Settings.Cfg.CHEST_MAX_ITEMS.getInt();
			if (maxItems > 0) {
				Chest Chest = (Chest) block.getState();
				Chest.getInventory().clear();
				int nbrItems = Plugin.Random.nextInt(maxItems - minItems) + 1 + minItems;
				SortedMap<Integer, String> items = Plugin.chestItems;
				// create items
				for (int i = 0; i < nbrItems; i++) {
					// if Settings.Cfg.CHEST_FILL_RPG != TRUE or chestItems is empty
					// create common items
					if (!Settings.Cfg.CHEST_FILL_RPG.getBoolean() || items.size() == 0) {
						Chest.getInventory().addItem(createItem(0));
					} else {
						int maxRandom = (Plugin.chestItems.lastKey()).intValue();
						int random = Plugin.Random.nextInt(maxRandom) + 1;
						String type = null;
						Iterator<Integer> it = (Iterator<Integer>) items.keySet().iterator();
						// find what type was randomly selected
						while (it.hasNext() && type == null) {
							Integer key = (Integer) it.next();
							if (random < key.intValue()) {
								type = items.get(key);
							}
						}

						if (type != null) {
							if (type.equalsIgnoreCase("GEM")) {
								Chest.getInventory().addItem(Gems.createGem());
							} else if (type.equalsIgnoreCase("TOME")) {
								Chest.getInventory().addItem(createTome());
							} else if (type.equalsIgnoreCase("UNKNOWN")) {
								Chest.getInventory().addItem(createUnidentified());
							} else {
								int tier = 0;
								switch (type.toUpperCase()) {
								case "COMMON":
									tier = 0;
									break;
								case "UNCOMMON":
									tier = 1;
									break;
								case "RARE":
									tier = 2;
									break;
								case "UNIQUE":
									tier = 3;
									break;
								case "SET":
									tier = 4;
									break;
								case "LEGENDARY":
									tier = 0;
									break;
								}
								Chest.getInventory().addItem(createItem(tier));
							}
						}
					}
				}
			}
		}
	}
}