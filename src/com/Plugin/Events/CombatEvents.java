package com.Plugin.Events;

import java.util.HashMap;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CombatEvents implements Listener {
	HashMap<String, PotionEffectType> effects = new HashMap<String,PotionEffectType>();
	public CombatEvents()
	{
		effects.put("Slowing", PotionEffectType.SLOW);
		effects.put("Fatiguing", PotionEffectType.SLOW_DIGGING);
		effects.put("Sanctifying", PotionEffectType.HEAL);
		effects.put("Desecrating", PotionEffectType.HARM);
		effects.put("Nauseating", PotionEffectType.CONFUSION);
		effects.put("Blinding", PotionEffectType.BLINDNESS);
		effects.put("Hungering", PotionEffectType.HUNGER);
		effects.put("Weakening", PotionEffectType.WEAKNESS);
		effects.put("Poisoning", PotionEffectType.POISON);
		effects.put("Withering", PotionEffectType.WITHER);

	}
	@EventHandler
	public void onHit(EntityDamageByEntityEvent event) {
		//System.out.println("EntityDamageByEntity event fired!");
		if(event.getDamager().getType().equals(EntityType.PLAYER)&&event.getEntityType().isAlive())
		{		Player p = (Player)event.getDamager();
		LivingEntity e = (LivingEntity)event.getEntity();

//		System.out.println("Player " + p.getDisplayName() +" is swinging at livingEntity" + e.getType());
			if(p.getItemInHand() !=null)
			{//	System.out.println("has "+ p.getItemInHand().toString() +" in hand");
				ItemStack i = p.getItemInHand();
				if(i.hasItemMeta())
						{
				//			System.out.println("has Meta:" +i.getItemMeta().toString());
							ItemMeta im = i.getItemMeta();
							if(im.getLore()!=null)
							{	
					//			System.out.println("has Lore(1):" + im.getLore().get(1));
								String name = im.getLore().get(0);
								Integer level = Integer.parseInt(im.getLore().get(1).substring(6));
								Integer duration = Integer.parseInt(im.getLore().get(2).split(":")[0])*20 + Integer.parseInt(im.getLore().get(2).split(":")[1]) * 60 * 20;
						
								if(name!=null)
							
								{
									e.addPotionEffect(new PotionEffect(effects.get(name),duration,level-1));
								}
							}
						}
			}
			else return;
		}	
		else return;			
	}

}
