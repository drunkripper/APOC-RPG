package com.Plugin.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInventoryEvent;
//import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import com.Plugin.Items.ItemAPI;
import com.Plugin.Main.Plugin;

public class EntityEvents implements Listener {
	
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent Event) {
		EntityType Type = Event.getEntityType();
		if ((Type == EntityType.CREEPER || Type == EntityType.ZOMBIE || Type == EntityType.PIG_ZOMBIE || Type == EntityType.SKELETON || Type == EntityType.SPIDER || Type == EntityType.GHAST || Type == EntityType.ENDERMAN || Type == EntityType.WITHER || Type == EntityType.ENDER_DRAGON) && Plugin.Random.nextInt(100) <= 25) {
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
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player po = event.getPlayer();
		EffectPollingEvent devent = new EffectPollingEvent(po);
		Bukkit.getServer().getPluginManager().callEvent(devent);
		//Bukkit.getServer().broadcastMessage(devent.getMessage());
	}
	@EventHandler
	public void onItemSwitched(PlayerItemHeldEvent event) {
		Player po = event.getPlayer();
		EffectPollingEvent devent = new EffectPollingEvent(po);
		Bukkit.getServer().getPluginManager().callEvent(devent);
		//Bukkit.getServer().broadcastMessage(devent.getMessage());
	}
	@EventHandler
	public void onInventoryClick(InventoryInteractEvent event) {
		Player po= (Player) event.getWhoClicked();
		EffectPollingEvent devent = new EffectPollingEvent(po);
		Bukkit.getServer().getPluginManager().callEvent(devent);
	}
}