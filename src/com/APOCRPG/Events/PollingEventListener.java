package com.APOCRPG.Events;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import com.APOCRPG.API.EffectAPI;
public final class PollingEventListener implements Listener{
	@EventHandler
	public void poll(EffectPollingEvent event) {
		Player p = event.getPlayer();
	   	ItemStack Hand = p.getItemInHand();
		//System.out.println(Hand.toString());
		ItemStack[] Armors = p.getEquipment().getArmorContents();
		//for(ItemStack i:Armors)
				//System.out.println(i.toString());
		ArrayList<ItemStack> stuff = new ArrayList<>(); 
		
		if(Hand!=null) {
			stuff.add(Hand);
		}
		for(ItemStack a:Armors) {
			if(a!=null&&a.getItemMeta()!=null) {
				stuff.add(a);//Keep only the non null armor objects
			}
		}
		
		if(!stuff.isEmpty()) 
		{
			for(ItemStack a:stuff) 
			{
				//TODO Constant Effects go here
				HashMap<String, Integer> effects = EffectAPI.getEffectsFromItem(a);
				for(Entry<String, Integer> effect : effects.entrySet())
				{
					switch(effect.getKey()) 
					{
					case "Fortune":
						break;
					}
				}
			}
		}
				/*if (p.getInventory().getItem(event.getPreviousSlot()).getItemMeta().getLore().get(1).equals("Strength")) {
					System.out.println("Removing effect");
					
					p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
				}*/
			
	} 
}
	

