package com.APOCRPG.Events;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

import com.APOCRPG.API.EffectAPI;
import com.APOCRPG.Main.Plugin;

public class ProjectileEventsListener implements Listener {
	public void onShoot(EntityShootBowEvent event) {
		HashMap<String, Integer> effects = EffectAPI.getEffectsFromItem(event.getBow());
		if (effects != null) {
			for (Entry<String, Integer> e : effects.entrySet()) {
				if ((2.5 + (e.getValue() - 1) * 1.5) > Plugin.Random.nextInt(100))
					switch (e.getKey()) {
					case "Hell":
						event.setProjectile(event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(),
								EntityType.FIREBALL));
						break;
					case "Multishot":
						event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.ARROW)
								.setVelocity(event.getProjectile().getVelocity());
						break;
					case "Blast": // Cone-based area of effect
						for (Entity n : event.getEntity().getNearbyEntities(10, 3, 10)) {
							if (n instanceof LivingEntity) {
								Location origin = event.getEntity().getLocation();
								Location offset = event.getEntity().getLocation()
										.add(event.getEntity().getLocation().getDirection());
								if (n.getLocation().distance(offset) > n.getLocation().distance(origin))
									n.setVelocity(event.getEntity().getLocation().getDirection().multiply(2));
							}
						}
						break;
					}
			}
		}
	}

}
