package com.APOCRPG.API;

import org.bukkit.inventory.ItemStack;

import com.APOCRPG.Main.Plugin;

public class EffectAPI {
	private static final String TYPE_ARMOR = "Armor";
	private static final String TYPE_BOW = "Bow";
	private static final String TYPE_TOOL = "Tool";
	private static final String TYPE_WEAPON = "Weapon";
	private static final String TYPE_WEAPON_PASSIVE = "PassiveWeapon";
	
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
				return getRandomEffect(TYPE_ARMOR);
			case 1:
				return getRandomEffect(TYPE_ARMOR);
			case 2:
				return getRandomEffect(TYPE_WEAPON_PASSIVE);
			case 3:
				return getRandomEffect(TYPE_WEAPON);
			case 4:
				return getRandomEffect(TYPE_BOW);
			case 5:
				return getRandomEffect(TYPE_TOOL);
		}
		return null;
	}
	
	public static Effect getRandomEffect(String Type) {
		System.out.println(Type);
		switch(Type) {
			case TYPE_ARMOR:
				return new Effect(ArmorEffects[Plugin.Random.nextInt(ArmorEffects.length)], Type, Plugin.Random.nextInt(3) + 1, "0:15");
			case TYPE_WEAPON_PASSIVE:
				return new Effect(PassiveWeaponEffects[Plugin.Random.nextInt(PassiveWeaponEffects.length)], Type, Plugin.Random.nextInt(3) + 1, "0:15");
			case TYPE_WEAPON:
				return new Effect(HitWeaponEffects[Plugin.Random.nextInt(HitWeaponEffects.length)], Type, Plugin.Random.nextInt(3) + 1, "0:15");
			case TYPE_BOW:
				return new Effect(BowEffects[Plugin.Random.nextInt(BowEffects.length)], Type, Plugin.Random.nextInt(3) + 1, "0:15");
			case TYPE_TOOL:
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
	
	public static String getEffectTypeFromName ( String name ) {
		String retval = null;
		// check armor effects
		for ( int i = 0; i < ArmorEffects.length && retval == null; i++){
			String effect = ArmorEffects[i];
			if ( name.equals(effect) ){
				retval = TYPE_ARMOR;
			}
		}
		for ( int i = 0; i < HitWeaponEffects.length && retval == null; i++){
			String effect = HitWeaponEffects[i];
			if ( name.equals(effect) ){
				retval = TYPE_WEAPON;
			}
		}
		for ( int i = 0; i < PassiveWeaponEffects.length && retval == null; i++){
			String effect = PassiveWeaponEffects[i];
			if ( name.equals(effect) ){
				retval = TYPE_WEAPON_PASSIVE;
			}
		}
		for ( int i = 0; i < BowEffects.length && retval == null; i++){
			String effect = BowEffects[i];
			if ( name.equals(effect) ){
				retval = TYPE_BOW;
			}
		}
		for ( int i = 0; i < ToolEffects.length && retval == null; i++){
			String effect = ToolEffects[i];
			if ( name.equals(effect) ){
				retval = TYPE_TOOL;
			}
		}
		return retval;
	}
}
