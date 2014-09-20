package com.Plugin.Events;

import java.util.ArrayList;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
//import org.bukkit.event.inventory.InventoryOpenEvent;
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
/*		//This is checking for item effects granted to the player
		Player p = event.getPlayer();
		ItemStack Hand = p.getItemInHand();
		ItemStack[] Armors = p.getEquipment().getArmorContents();
		ArrayList<ItemStack> stuff = new ArrayList<>(); 
		ArrayList<ItemMeta> metas = new ArrayList<>();
		
		if(Hand!=null)
			stuff.add(Hand);
		for(ItemStack a:Armors)
			if(a!=null)
				stuff.add(a);//Keep only the non null armor objects
		if(!stuff.isEmpty())
			{for(ItemStack a:stuff)
				metas.add(a.getItemMeta());//All Metas now live here
		
		
			try {
				
				for(ItemMeta Meta:metas)
				{
				String Effect = Meta.getLore().get(1);
				System.out.println(Meta.getDisplayName() + "'s Effect is " + Effect);
					if(Effect.endsWith("ing"))
					{
						//Unimplemented...
					}
					else
					{
						switch(Effect) {
						case "Speed":
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 1));
							break;
						case "Haste":
							p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 600, 1));
							break;
						case "Strength":
							p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 1));
							break;
						case "Jumpfulness":
							p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 600, 1));
							break;
						case "Regeneration":
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 1));
							break;
						case "Resistance":
							p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 1));
							break;
						case "Fire Resistance":
							p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 600, 1));
							break;
						case "Scuba":
							p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 600, 1));
							break;
						case "Invisibility":
							p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 600, 1));
							break;
						case "Night Vision":
							p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 600, 1));
							break;
						case "Health":
							p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 600, 1));
							break;
						case "Absorption":
							p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 600, 1));
							break;
						case "Saturation":
							p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 600, 1));
							break;
						}
					}
				}
				
					/*if (p.getInventory().getItem(event.getPreviousSlot()).getItemMeta().getLore().get(1).equals("Strength")) {
						System.out.println("Removing effect");
						
						p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					}
			
			} catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
				System.out.println("Item has no Effect");
			}
		}*/
	}
}