package com.APOCRPG.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.APOCRPG.API.EffectAPI;

public final class PollingEventListener implements Listener {	
	
	//Direct damage var
	double damage = 0;
	
	//Direct damage handler
	@EventHandler
    public void onDamage(EntityDamageEvent event2) {
        damage = event2.getDamage();
        //if (event2.getEntity() instanceof Player) {
        //    Player player = (Player) event2.getEntity();
        //}
	}
	//This array needs to point to a .yml for adding modpack hostiles
	private static ArrayList<EntityType> Hostiles = new ArrayList<EntityType>();

	static {
		Hostiles.add(EntityType.BLAZE);
		Hostiles.add(EntityType.CAVE_SPIDER);
		Hostiles.add(EntityType.CREEPER);
		Hostiles.add(EntityType.ENDERMAN);
		Hostiles.add(EntityType.GHAST);
		Hostiles.add(EntityType.GIANT);
		Hostiles.add(EntityType.IRON_GOLEM);
		Hostiles.add(EntityType.MAGMA_CUBE);
		Hostiles.add(EntityType.SILVERFISH);
		Hostiles.add(EntityType.SKELETON);
		Hostiles.add(EntityType.SLIME);
		Hostiles.add(EntityType.SPIDER);
		Hostiles.add(EntityType.WITCH);
		Hostiles.add(EntityType.ZOMBIE);
	}

	@EventHandler
	public void tauntTarget(EntityTargetLivingEntityEvent targ) {
		if (targ.getEntity().hasMetadata("Targeting")) {
			targ.setTarget((Player) targ.getEntity().getMetadata("Targeting"));
		}
	}

	@EventHandler
	public void poll(EffectPollingEvent event) {
		Player p = event.getPlayer();
		// System.out.println(Hand.toString());
		ItemStack[] Armors = p.getEquipment().getArmorContents();
		// for(ItemStack i:Armors)
		// System.out.println(i.toString());
		ArrayList<ItemStack> stuff = new ArrayList<>();

		for (ItemStack a : Armors) {
			if (a != null && a.getItemMeta() != null) {
				stuff.add(a);// Keep only the non null armor objects
			}
		}
		
		if (!stuff.isEmpty()) {
			for (ItemStack a : stuff) {
				// TODO Constant Effects go here
				HashMap<String, Integer> effects = EffectAPI.getEffectsFromItem(a);
				for (Entry<String, Integer> effect : effects.entrySet()) {
					switch (effect.getKey()) {
					case "Velocity":
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 666, effect.getValue()));
						break;
					case "Vaulting":
						p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 666, effect.getValue()));
						break;
					case "Healing":
						for (Entity e : p.getNearbyEntities(effect.getValue(), effect.getValue(), effect.getValue()))
							if (e.getType().equals(EntityType.PLAYER))
								((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 666, 1));
							else if (e.getType().equals(EntityType.WOLF) && ((Wolf) e).isTamed())
								((Wolf) e).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 666, 1));
						break;
					case "Damage":
						for (Entity e : p.getNearbyEntities(effect.getValue(), effect.getValue(), effect.getValue()))
							if (e.getType().equals(EntityType.PLAYER))
								((Player) e)
										.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 666, 1));
							else if (e.getType().equals(EntityType.WOLF) && ((Wolf) e).isTamed())
								((Wolf) e).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 666, 1));

						break;
					case "Resistance":
						for (Entity e : p.getNearbyEntities(effect.getValue(), effect.getValue(), effect.getValue()))
							if (e.getType().equals(EntityType.PLAYER))
								((Player) e)
										.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 666, 1));
							else if (e.getType().equals(EntityType.WOLF) && ((Wolf) e).isTamed())
								((Wolf) e)
										.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 666, 1));
						break;
					case "Speed":
						for (Entity e : p.getNearbyEntities(effect.getValue(), effect.getValue(), effect.getValue()))
							if (e.getType().equals(EntityType.PLAYER))
								((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 666, 1));
							else if (e.getType().equals(EntityType.WOLF) && ((Wolf) e).isTamed())
								((Wolf) e).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 666, 1));
						break;
					//Takes surrounding Enemies from the surrounding aura area xyz * increase 2.5/level + 5 blocks to start.
					case "Taunting":
						for (Entity e : p.getNearbyEntities(effect.getValue() * 2.5 + 4.0,
								effect.getValue() * 2.5 + 4.0, effect.getValue() * 2.5 + 4.0))
							if (Hostiles.contains(e.getType())) {
								e.setMetadata("Targeting", (MetadataValue) p);
						break;
							}
					//Takes surrounding living entities and gives the player 2.5% resistance per entity, at first level the aura is 5 blocks (level + 4)
					case "Courage":

						double resist = 0.025;
						int entcount = 0;
						for (Entity e : p.getNearbyEntities(effect.getValue() + 4.0, effect.getValue() + 4.0, effect.getValue() + 4.0)) {
							if (!e.isDead()) {
									entcount++;
							}
						}
						resist = resist * effect.getValue() * entcount;
						damage = damage - damage * resist;
						p.sendMessage("# of entities = " + entcount + "resistance = " + resist);
						break;
					}
				}
			}
		}
		/*
		 * if (p.getInventory().getItem(event.getPreviousSlot()).getItemMeta().
		 * getLore().get(1).equals("Strength")) { System.out.println(
		 * "Removing effect");
		 * 
		 * p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE); }
		 */

	}

}
