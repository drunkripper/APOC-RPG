package com.APOCRPG.API;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.APOCRPG.Main.Plugin;

public class GemAPI {
	
	public static ItemStack createGem() {
		ItemStack Gem = new ItemStack(Material.EMERALD);
		ItemMeta Meta = Gem.getItemMeta();
		
		ArrayList<String> Lore = new ArrayList<String>();
		Effect effect = EffectAPI.getRandomEffect();
		int level = effect.getLevel();
		String strLvl = null;
		switch (level) {
			case 1: strLvl = "I"; break;
			case 2: strLvl = "II"; break;
			case 3: strLvl = "III"; break;
			case 4: strLvl = "IV"; break;
			case 5: strLvl = "V"; break;
			case 6: strLvl = "VI"; break;
			case 7: strLvl = "VII"; break;
			case 8: strLvl = "VIII"; break;
			case 9: strLvl = "IX"; break;
			case 10: strLvl = "X"; break;
		}
		Lore.add(Plugin.LORE_GEM_OF + effect.getEffectName() + " " + strLvl);
		Lore.add(effect.getType() + " type");
		Meta.setLore(Lore);
		Gem.setItemMeta(Meta);
		
		return Gem;
	}
	
}
