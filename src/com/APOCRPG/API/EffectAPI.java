package com.APOCRPG.API;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.inventory.ItemStack;

import com.APOCRPG.Main.Plugin;

public class EffectAPI {
	private static final String TYPE_ARMOR = "Armor";
	private static final String TYPE_BOW = "Bow";
	private static final String TYPE_TOOL = "Tool";
	private static final String TYPE_WEAPON = "Weapon";
	private static final String TYPE_WEAPON_PASSIVE = "PassiveWeapon";
	
	private static ArrayList<String> ArmorEffects = new ArrayList<String>();
	{
		ArmorEffects.add("Warding");
		ArmorEffects.add("Velocity");
		ArmorEffects.add("Vaulting");
		ArmorEffects.add("Staggering");
		ArmorEffects.add("Demons");
		ArmorEffects.add("Healing");
		ArmorEffects.add("Damage");
		ArmorEffects.add("Resistance");
		ArmorEffects.add("Speed");
		ArmorEffects.add("Taunting");
	};
	private static ArrayList<String> PassiveWeaponEffects = new ArrayList<String>();
	{
		PassiveWeaponEffects.add("Ravaging");
		PassiveWeaponEffects.add("Decapitation");
	};
	private static ArrayList<String> HitWeaponEffects = new ArrayList<String>();
	{
		HitWeaponEffects.add("Debilitation");
		HitWeaponEffects.add("Crippling");
		HitWeaponEffects.add("Bloodthirst");
		HitWeaponEffects.add("Blinding");
		HitWeaponEffects.add("Sacrifice");
		HitWeaponEffects.add("Slashing");
		HitWeaponEffects.add("Cleaving");
	};
	private static ArrayList<String> BowEffects = new ArrayList<String>();
	{
		BowEffects.add("Hell");
		BowEffects.add("Multishot");
		BowEffects.add("Blast");
	};
	private static ArrayList<String> ToolEffects = new ArrayList<String>();
	{
		ToolEffects.add("Fortune");
	};
	private static HashMap<String, String> TypeLookup = new HashMap<String, String>();
	{
		for(String ef:ArmorEffects)
			TypeLookup.put(ef, TYPE_ARMOR);
		for(String ef:PassiveWeaponEffects)
			TypeLookup.put(ef, TYPE_WEAPON_PASSIVE);
		for(String ef:HitWeaponEffects)
			TypeLookup.put(ef, TYPE_WEAPON);
		for(String ef:BowEffects)
			TypeLookup.put(ef, TYPE_BOW);
		for(String ef:ToolEffects)
			TypeLookup.put(ef, TYPE_TOOL);
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
				return new Effect(ArmorEffects.get(Plugin.Random.nextInt(ArmorEffects.size())), Type, Plugin.Random.nextInt(3) + 1, "0:15");
			case TYPE_WEAPON_PASSIVE:
				return new Effect(PassiveWeaponEffects.get(Plugin.Random.nextInt(PassiveWeaponEffects.size())), Type, Plugin.Random.nextInt(3) + 1, "0:15");
			case TYPE_WEAPON:
				return new Effect(HitWeaponEffects.get(Plugin.Random.nextInt(HitWeaponEffects.size())), Type, Plugin.Random.nextInt(3) + 1, "0:15");
			case TYPE_BOW:
				return new Effect(BowEffects.get(Plugin.Random.nextInt(BowEffects.size())), Type, Plugin.Random.nextInt(3) + 1, "0:15");
			case TYPE_TOOL:
				return new Effect(ToolEffects.get(Plugin.Random.nextInt(ToolEffects.size())), Type, Plugin.Random.nextInt(3) + 1, "0:15");
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
		//LookupTable
		if(TypeLookup.containsKey(name))
			retval = TypeLookup.get(name);
		return retval;
	}
}
