package com.APOCRPG.API;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GemAPI {
	
	public static ItemStack createGem() {
		ItemStack Gem = new ItemStack(Material.EMERALD);
		ItemMeta Meta = Gem.getItemMeta();
		
		ArrayList<String> Lore = new ArrayList<String>();
		Effect Effect = EffectAPI.getRandomEffect();
		Lore.add("Gem of " + Effect.getEffectName());
		Lore.add(Effect.getType() + " type");
		Lore.add("------");
		Lore.add("Level " + Effect.getLevel());
		Lore.add(Effect.getTime());
		Meta.setLore(Lore);
		Gem.setItemMeta(Meta);
		
		return Gem;
	}
	
}
