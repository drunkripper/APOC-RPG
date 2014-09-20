package com.Plugin.Events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.Plugin.Items.ItemAPI;
import com.Plugin.Main.Plugin;

public class EntityEvents implements Listener {
	
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent Event) {
		if (Plugin.Random.nextInt(100) <= 25) {
		LivingEntity Entity = Event.getEntity();
		
			int Amount = Plugin.Random.nextInt(2) + 1;
			for (int i=0; i<Amount; i++) {
				int Gear = Plugin.Random.nextInt(5);
				if (Gear == 0) {
					Entity.getEquipment().setItemInHand(ItemAPI.createArmor(4));
				} else if (Gear == 1) {
					Entity.getEquipment().setHelmet(ItemAPI.createArmor(0));
				} else if (Gear == 2) {
					Entity.getEquipment().setChestplate(ItemAPI.createArmor(1));
				} else if (Gear == 3) {
					Entity.getEquipment().setLeggings(ItemAPI.createArmor(2));
				} else if (Gear == 4) {
					Entity.getEquipment().setBoots(ItemAPI.createArmor(3));
				}
			}
		}
	}
	
	@EventHandler
	public void onItemSwitched(PlayerItemHeldEvent event) {
		ItemStack Hand = event.getPlayer().getItemInHand();
		if (Hand != null) {
			ItemMeta Meta = Hand.getItemMeta();
			try {
				String Effect = Meta.getLore().get(1);
				if (Effect != null) {
					System.out.println(Meta.getDisplayName() + "'s Effect is " + Effect);
					if (Effect.equals("Strength")) {
						event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200000, 1000));
					}
				} else {
					if (event.getPlayer().getInventory().getItem(event.getPreviousSlot()).getItemMeta().getLore().get(1).equals("Strength")) {
						event.getPlayer().removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					}
				}
			} catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
				System.out.println("Item has no Effect");
			}
		}
	}
}