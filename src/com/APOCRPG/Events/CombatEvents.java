package com.APOCRPG.Events;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.APOCRPG.API.EffectAPI;
import com.APOCRPG.Main.Plugin;

public class CombatEvents implements Listener {
	HashMap<String, PotionEffectType> effects = new HashMap<String,PotionEffectType>();
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onHit(EntityDamageByEntityEvent event) {
		
		if(event.getDamager().getType().equals(EntityType.PLAYER)&&event.getEntityType().isAlive())
		{		
			Player playa = (Player)event.getDamager();
			LivingEntity hitMe = (LivingEntity)event.getEntity();
			HashMap<String, Integer> hershmerp;
			for(ItemStack item:hitMe.getEquipment().getArmorContents())
			{	//TODO: Effects that happen when a player is hit go here.
				hershmerp = EffectAPI.getEffectsFromItem(item);
				if(hershmerp.containsKey("Staggering"))
					playa.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20+5*hershmerp.get("Staggering"),0 ));
				if(hershmerp.containsKey("Demons")
						&&(event.getCause().equals(DamageCause.FIRE)
							||event.getCause().equals(DamageCause.FIRE_TICK)
							||event.getCause().equals(DamageCause.LAVA)
						)&&((hershmerp.get("Demons")-1)*2.5 + 10)<Plugin.Random.nextInt(100)){
					event.setCancelled(true);
				if(hershmerp.containsKey("Warding")&&!event.isCancelled())
					event.setDamage(event.getDamage()*(0.9 - 0.025*(hershmerp.get("Warding")-1)));
				}						
			}
			if(playa.getItemInHand() !=null)
			{
				ItemStack i = playa.getItemInHand();
				if(i.hasItemMeta())
				{
					//System.out.println("has Meta:" +i.getItemMeta().toString());
					HashMap<String, Integer> effects = EffectAPI.getEffectsFromItem(i);
					for(Entry<String, Integer> e: effects.entrySet())
					{
						switch(e.getKey()) {
						case "Debilitation":
							//Proposed Handling method:
							// Create a potion effect that has no type (Does nothing)
							// On combat events that matter, check for this effect
							// modify damage as necessary.
							break;
						case "Crippling":
							//Proposed Handling method:
							// Null potion effect, 
							// Listener to handle setSpeed
							break;
						case "Bloodthirst":
							//Proposed handling:
							// Null potion effects: 
							// Bloody 1, 2, 3, 4, etc. 
							break;
						case "Blinding":
							if(5>=Plugin.Random.nextInt(100))
							{
								hitMe.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20+ (e.getValue() -1)*5, 0));
							}
							break;
						case "Sacrifice":
							if((2.5+ (e.getValue()-1)*1.5)>=Plugin.Random.nextInt(100))
							{	
								event.setDamage(event.getDamage()*2);
								playa.damage(5.0);
							}
							break;
						case "Ravaging":
							event.setDamage(event.getDamage()* (1.1+.025*e.getValue()));
							break;
						case "Decapitation":
							if(event.getDamage()>((Damageable)hitMe).getHealth())
							{
								hitMe.getEquipment().setItemInHand(new ItemStack(Material.SKULL));
								hitMe.getEquipment().setItemInHandDropChance(0.1f);
							}
							
						}
						
					}
				}
				else return;
			}
		}
		/**
		 * This has been commented out because we currently have no effects dealing specifically with 
		 * 
		 */
		else if(event.getDamager().getType().equals(EntityType.ARROW)&&event.getEntityType().isAlive())
		{
			//TODO: Effects Dealing with arrows hitting entities. None at present.
			Arrow arrow = (Arrow)event.getDamager();
			if(arrow.getShooter() != null&&arrow.getShooter().getType() == EntityType.PLAYER)
			{
				LivingEntity shotMe = (LivingEntity) event.getEntity();
				Player player = (Player) arrow.getShooter();
				ItemStack itemInHand = player.getItemInHand();
				HashMap<String, Integer> effects = EffectAPI.getEffectsFromItem(itemInHand);
				for(Entry<String, Integer> e: effects.entrySet())
				{
				switch(e.getKey()) {
				case "Debilitation":
					break;
				case "Crippling":
					break;
				case "Bloodthirst":
					break;
				case "Blinding":
					if(5>=Plugin.Random.nextInt(100))
					{
						shotMe.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20+ (e.getValue() -1)*5, 0));
					}
					break;
				case "Sacrifice":
					if((2.5+ (e.getValue()-1)*1.5)>=Plugin.Random.nextInt(100))
					{	
						event.setDamage(event.getDamage()*2);
						player.damage(5.0);
					}
					break;
				}
					
						
				}
			}
		} 
	}

}
