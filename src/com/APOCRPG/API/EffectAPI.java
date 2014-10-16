package com.APOCRPG.API;

import org.bukkit.inventory.ItemStack;

import com.APOCRPG.Main.Plugin;

public class EffectAPI {
	
	private static String[] ArmorEffects = {
		"Warding",
		"Velocity",
		"Vaulting",
		"Staggering",
		"Demons",
		"Healing",
		"Damage",
		"Resistance",
		"Speed",
		"Taunting"
	};
	private static String[] PassiveWeaponEffects = {
		"Ravaging",
		"Decapitation"
	};
	private static String[] HitWeaponEffects = {
		"Debilitation",
		"Crippling",
		"Bloodthirst",
		"Blinding",
		"Sacrifice",
		"Slashing",
		"Cleaving",
	};
	private static String[] BowEffects = {
		"Hell",
		"Multishot",
		"Blast"
	};
	private static String[] ToolEffects = {
		"Fortune"
	};
	
	public static Effect getRandomEffect() {
		int Random = Plugin.Random.nextInt(5);
		switch (Random) {
			case 0:
				return getRandomEffect("Armor");
			case 1:
				return getRandomEffect("Armor");
			case 2:
				return getRandomEffect("PassiveWeapon");
			case 3:
				return getRandomEffect("Weapon");
			case 4:
				return getRandomEffect("Bow");
			case 5:
				return getRandomEffect("Tool");
		}
		return null;
	}
	
	public static Effect getRandomEffect(String Type) {
		System.out.println(Type);
		switch(Type) {
			case "Armor":
				return new Effect(ArmorEffects[Plugin.Random.nextInt(ArmorEffects.length)], Type, Plugin.Random.nextInt(3) + 1, "0:15");
			case "PassiveWeapon":
				return new Effect(PassiveWeaponEffects[Plugin.Random.nextInt(PassiveWeaponEffects.length)], Type, Plugin.Random.nextInt(3) + 1, "0:15");
			case "Weapon":
				return new Effect(HitWeaponEffects[Plugin.Random.nextInt(HitWeaponEffects.length)], Type, Plugin.Random.nextInt(3) + 1, "0:15");
			case "Bow":
				return new Effect(BowEffects[Plugin.Random.nextInt(BowEffects.length)], Type, Plugin.Random.nextInt(3) + 1, "0:15");
			case "Tool":
				return new Effect(ToolEffects[Plugin.Random.nextInt(ToolEffects.length)], Type, Plugin.Random.nextInt(3) + 1, "0:15");
		}
		System.out.println("Null");
		return null;
	}
	//Is this a scaffold?
	public static Effect getEffectFromGem(ItemStack Gem) {
		System.out.println("First line of lore is " + Gem.getItemMeta().getLore().get(1) + ".");
		if (Gem.getItemMeta().getLore().get(1).equals("Gem")) {
			
			return null;
		}
		return null;
	}
	
}
