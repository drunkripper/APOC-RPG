package com.APOCRPG.Events;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.APOCRPG.API.EffectAPI;
import com.APOCRPG.Main.Plugin;

public class CombatEventsListener implements Listener {
	public static HashMap<LivingEntity, Integer> bloodyNoses = new HashMap<LivingEntity, Integer>();

	public void onDie(EntityDeathEvent event) {
		if (bloodyNoses.containsKey(event.getEntity()))
			bloodyNoses.remove(event.getEntity());
	}

	@EventHandler
	public void onHit(EntityDamageByEntityEvent event) {

		if (event.getDamager().getType().equals(EntityType.PLAYER) && event.getEntityType().isAlive()) {
			Player playa = (Player) event.getDamager();
			LivingEntity hitMe = (LivingEntity) event.getEntity();
			HashMap<String, Integer> hershmerp;
			for (ItemStack item : hitMe.getEquipment().getArmorContents()) { 	// TODO:
																				// Effects
																				// that
																				// happen
																				// when
																				// a
																				// player
																				// is
																				// hit
																				// go
																				// here.
				hershmerp = EffectAPI.getEffectsFromItem(item);
				if (hershmerp.containsKey("Staggering"))
					playa.addPotionEffect(
							new PotionEffect(PotionEffectType.CONFUSION, 20 + 5 * hershmerp.get("Staggering"), 0));
				if (hershmerp.containsKey("Demons")
						&& (event.getCause().equals(DamageCause.FIRE) || event.getCause().equals(DamageCause.FIRE_TICK)
								|| event.getCause().equals(DamageCause.LAVA))
						&& ((hershmerp.get("Demons") - 1) * 2.5 + 10) < Plugin.Random.nextInt(100)) {
					event.setCancelled(true);
					if (hershmerp.containsKey("Warding") && !event.isCancelled())
						event.setDamage(event.getDamage() * ((9/10) - (20/1000) * (hershmerp.get("Warding") - 1)));
				}
			}
			if (playa.getItemInHand() != null) {
				ItemStack i = playa.getItemInHand();
				if (i.hasItemMeta()) {
					// System.out.println("has Meta:"
					// +i.getItemMeta().toString());
					HashMap<String, Integer> effects = EffectAPI.getEffectsFromItem(i);
					for (Entry<String, Integer> e : effects.entrySet()) {
						switch (e.getKey()) {
						case "Cleaving":
							if ((5 + 1.5 * e.getValue()) >= Plugin.Random.nextInt(100)) {
								for (Entity en : hitMe.getNearbyEntities(1.5, 1.5, 1.5))
									if (en.getType().isAlive())
										((LivingEntity) en).damage(event.getDamage());
							}
							break;
						case "Slashing":
							if ((5 + 1.5 * e.getValue()) >= Plugin.Random.nextInt(100)) {
								hitMe.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 0));
							}
						case "Debilitation":
							hitMe.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 666, e.getValue()));
							break;
						case "Crippling":
							hitMe.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 666, e.getValue()));
							break;
						case "Bloodthirst":
							if (bloodyNoses.containsKey(hitMe))
								bloodyNoses.put(hitMe, bloodyNoses.get(hitMe) + 1);
							else
								bloodyNoses.put(hitMe, 1);
							hitMe.damage((1/2) + (3/10) * (e.getValue()) * (bloodyNoses.get(hitMe)));
							break;
						case "Blinding":
							if (5 >= Plugin.Random.nextInt(100)) {
								hitMe.addPotionEffect(
										new PotionEffect(PotionEffectType.BLINDNESS, 20 + (e.getValue() - 1) * 5, 0));
							}
							break;
						case "Sacrifice":
							if ((2.5 + (e.getValue() - 1) * 1.5) >= Plugin.Random.nextInt(100)) {
								event.setDamage(event.getDamage() * 2);
								playa.damage(5);
							}
							break;
						case "Force":
							if (2.5 + (e.getValue() * .75) >= Plugin.Random.nextInt(100)){
								event.setDamage(event.getDamage()* (3/2));
							}
							break;
						case "Venom":
							hitMe.addPotionEffect(new PotionEffect(PotionEffectType.POISON, (int) ((e.getValue() - 1) * 1.5), 0));
							break;
						case "Ravaging":
							event.setDamage(event.getDamage() * ((11/10) + (25/1000) * e.getValue()));
							break;
						case "Decapitation":
							if (event.getDamage() > ((Damageable) hitMe).getHealth()) {
								hitMe.getEquipment().setItemInHand(new ItemStack(Material.SKULL));
								hitMe.getEquipment().setItemInHandDropChance(0.1f);
							}

						}

					}
				} else
					return;
			}
		}
		/**
		 * This has been commented out because we currently have no effects
		 * dealing specifically with
		 * 
		 */
		else if (event.getDamager().getType().equals(EntityType.ARROW) && event.getEntityType().isAlive()) {
			// TODO: Effects Dealing with arrows hitting entities. None at
			// present.
			Arrow arrow = (Arrow) event.getDamager();
			if (arrow.getShooter() != null && arrow.getShooter() instanceof Player) {
				LivingEntity shotMe = (LivingEntity) event.getEntity();
				Player player = (Player) arrow.getShooter();
				ItemStack itemInHand = player.getItemInHand();
				HashMap<String, Integer> effects = EffectAPI.getEffectsFromItem(itemInHand);
				for (Entry<String, Integer> e : effects.entrySet()) {
					switch (e.getKey()) {
					case "Debilitation":
						shotMe.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 666, e.getValue()));
						break;
					case "Crippling":
						shotMe.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 666, e.getValue()));
						break;
					case "Bloodthirst":
						if (bloodyNoses.containsKey(shotMe))
							bloodyNoses.put(shotMe, bloodyNoses.get(shotMe) + 1);
						else
							bloodyNoses.put(shotMe, 1);
						shotMe.damage((1/2) + (3/10) * (e.getValue()) * (bloodyNoses.get(shotMe)));

						break;
					case "Blinding":
						if (5 >= Plugin.Random.nextInt(100)) {
							shotMe.addPotionEffect(
									new PotionEffect(PotionEffectType.BLINDNESS, 20 + (e.getValue() - 1) * 5, 0));
						}
						break;
					case "Sacrifice":
						if ((2.5 + (e.getValue() - 1) * 1.5) >= Plugin.Random.nextInt(100)) {
							event.setDamage(event.getDamage() * 2);
							player.damage(5);
						}
						break;
					}

				}
			}
		}
	}

}
