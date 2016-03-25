package com.APOCRPG.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.APOCRPG.API.EffectAPI;

public class MiningEvents implements Listener {
	@EventHandler
	public void onBlockBreak(BlockDamageEvent eve) {
		if (EffectAPI.getEffectsFromItem(eve.getPlayer().getItemInHand()).containsKey("Breaking")) {
			eve.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, EffectAPI.getEffectsFromItem(eve.getPlayer().getItemInHand()).get("Breaking"), 20));
		}

	}
		
}
