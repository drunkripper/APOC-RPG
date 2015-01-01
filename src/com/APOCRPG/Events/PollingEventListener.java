package com.APOCRPG.Events;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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
		ArrayList<ItemMeta> metas = new ArrayList<>();
		
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
				if(a.getItemMeta()!=null 
				&&a.getItemMeta().getLore()!=null
				&&(!a.getItemMeta().getLore().get(0).equals("Socket"))
				&&a.getItemMeta().getLore().size()>1
				&&a.getItemMeta().getLore().get(1)!=null
				)
				{
					//System.out.println(a.toString());
					if(a.getItemMeta()!=null)
						metas.add(a.getItemMeta());//All Metas now live here
					//System.out.println(metas.get(0));
				}
			}
			try 
			{
				
				for(ItemMeta Meta:metas)
				{
					String Effect = Meta.getLore().get(0);
					int level = 0;
					if ( Meta.getLore().size() > 1 )
					{
						try {
							level = Integer.parseInt(Meta.getLore().get(1).substring(6));
						} catch (Exception e){
							;
						}
						if ( Effect != null && !Effect.trim().equals("") && level > 0 ){
							//System.out.println(Meta.getDisplayName() + "'s Effect is " + Effect);
							
								switch(Effect) {
								case "Speed":
									p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 800, level));
									break;
								case "Haste":
									p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 800, level));
									break;
								case "Strength":
									p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 800, level));
									break;
								case "Jumpfulness":
									p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 800, level));
									break;
								case "Regeneration":
									p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 800, level));
									break;
								case "Resistance":
									p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 800, level));
									break;
								case "Fire Resistance":
									p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 800, level));
									break;
								case "Scuba":
									p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 800, level));
									break;
								case "Invisibility":
									p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 800, level));
									break;
								case "Night Vision":
									p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 800, level));
									break;
								case "Health":
									p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 800, level));
									break;
								case "Absorption":
									p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 800, level));
									break;
								case "Saturation":
									p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 800, level));
									break;
								
							}
						}
					}
				}
				/*if (p.getInventory().getItem(event.getPreviousSlot()).getItemMeta().getLore().get(1).equals("Strength")) {
					System.out.println("Removing effect");
					
					p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
				}*/
			
			} catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
				System.out.println("Item has no Effect");
			}
		}
	}
}
