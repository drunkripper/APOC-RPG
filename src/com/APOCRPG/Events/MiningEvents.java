package com.APOCRPG.Events;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.APOCRPG.API.EffectAPI;
import com.APOCRPG.Main.Plugin;


public class MiningEvents implements Listener {
	@EventHandler
	public void onBlockBreak(BlockDamageEvent eve) {
		Player plyr = eve.getPlayer();
		if (plyr.getItemInHand() !=null){
			ItemStack itm = plyr.getItemInHand();
			if (itm.hasItemMeta()) {
				HashMap<String, Integer> effects = EffectAPI.getEffectsFromItem(itm);
				for (Entry<String, Integer> e : effects.entrySet()) {
					switch (e.getKey()) {
					case "Breaking":
						if (2.5 + (e.getValue() * .75) >= Plugin.Random.nextInt(100)) {
							((Player)e).addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 1));
						}
					break;
					case "Demolition":
					//something clever	
					break;
					case "Smelting":
					//something else just as great	
					break;
					}					
				}
			}
		}
	}		
}
