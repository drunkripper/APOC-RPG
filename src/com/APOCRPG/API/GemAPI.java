package com.APOCRPG.API;

import java.util.ArrayList;

import com.APOCRPG.Main.Settings;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.APOCRPG.Main.Plugin;

public class GemAPI {

	public static ItemStack createGem(String name, String type, String level) {
		ItemStack gem = new ItemStack(Material.EMERALD);
		ItemMeta meta = gem.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(Settings.Cfg.LORE_GEM_OF.getString() + name + " " + level);
		lore.add(type + " type");
		meta.setDisplayName(Settings.Cfg.DISPLAY_NAME_GEM.getString());
		meta.setLore(lore);
		gem.setItemMeta(meta);

		return gem;
	}

	public static ItemStack createGem() {
		Effect effect = EffectAPI.getRandomEffect();
		int level = effect.getLevel();
		String strLvl = Plugin.intToRoman(level);

		return createGem(effect.getEffectName(), effect.getType(), strLvl);
	}

	public static ItemStack createGem(String name, String level) {
		return createGem(name, "Generic", level);
	}
}
