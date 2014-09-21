package com.Plugin.Main;

import com.Plugin.Commands.ApocRPGCommand;
import com.Plugin.Events.ChunkEvents;
import com.Plugin.Events.CombatEvents;
import com.Plugin.Events.EntityEvents;
import com.Plugin.Events.SocketEvents;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Plugin extends JavaPlugin {
	
	public static Random Random = new Random();
	public static Plugin Plugin = null;
	public static Settings Settings = null;
	public static File LandRuins = null;
	
	public static ChunkEvents ChunkListener = new ChunkEvents();
	public static EntityEvents EntityListener = new EntityEvents();
	public static CombatEvents CombatListener = new CombatEvents();
	public static SocketEvents SocketListener = new SocketEvents();
	public void onEnable() {
		
		Plugin = this;
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				Player[] ps = Plugin.getServer().getOnlinePlayers();
				for(Player p: ps) {
				ItemStack Hand = p.getItemInHand();
				System.out.println(Hand.toString());
				ItemStack[] Armors = p.getEquipment().getArmorContents();
				for(ItemStack i:Armors)
					System.out.println(i.toString());
				ArrayList<ItemStack> stuff = new ArrayList<>(); 
				ArrayList<ItemMeta> metas = new ArrayList<>();
				
				if(Hand!=null)
					stuff.add(Hand);
				for(ItemStack a:Armors)
					if(a!=null)
						stuff.add(a);//Keep only the non null armor objects
				if(!stuff.isEmpty())
					{for(ItemStack a:stuff)
						if(a.getItemMeta()!=null&&a.getItemMeta().getLore()!=null&&a.getItemMeta().getLore().get(1)!=null)
						{
							System.out.println(a.toString());
							metas.add(a.getItemMeta());//All Metas now live here
							System.out.println(metas.get(0));
						}
				
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
									p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 680, 1));
									break;
								case "Haste":
									p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 680, 1));
									break;
								case "Strength":
									p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 680, 1));
									break;
								case "Jumpfulness":
									p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 680, 1));
									break;
								case "Regeneration":
									p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 680, 1));
									break;
								case "Resistance":
									p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 680, 1));
									break;
								case "Fire Resistance":
									p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 680, 1));
									break;
								case "Scuba":
									p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 680, 1));
									break;
								case "Invisibility":
									p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 680, 1));
									break;
								case "Night Vision":
									p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 680, 1));
									break;
								case "Health":
									p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 680, 1));
									break;
								case "Absorption":
									p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 680, 1));
									break;
								case "Saturation":
									p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 680, 1));
									break;
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
			}}, 600l, 600l);
			
		saveDefaultConfig();
		Settings = new Settings(getConfig());
		LandRuins = new File(getDataFolder(), "/LandRuins");
		if (!LandRuins.exists()) {
			LandRuins.mkdir();
		}
		
		getServer().getPluginManager().registerEvents(ChunkListener, this);
		getServer().getPluginManager().registerEvents(EntityListener, this);
		getServer().getPluginManager().registerEvents(CombatListener, this);
		getServer().getPluginManager().registerEvents(SocketListener, this);
		getCommand("generateitem").setExecutor(new ApocRPGCommand());
		getCommand("generateuseful").setExecutor(new ApocRPGCommand());
	}

	public void onDisable() {
		
	}
	
}