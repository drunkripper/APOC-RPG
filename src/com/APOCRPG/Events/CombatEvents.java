package com.APOCRPG.Events;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
		//TODO: Add combat effects
		if(event.getDamager().getType().equals(EntityType.PLAYER)&&event.getEntityType().isAlive())
		{		
			Player playa = (Player)event.getDamager();
			LivingEntity hitMe = (LivingEntity)event.getEntity();
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
							break;
						case "Crippling":
							break;
						case "Bloodthirst":
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
