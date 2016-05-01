package com.APOCRPG.SkillPoints;

import com.APOCRPG.Main.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class InSkill implements Listener {
	//Class for the diff abilities
		
	//Add player to the db
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(DBApi.checkPlayerExist(p) == true) {
			p.sendMessage("Welcome Back, " + p.getName() + " your skills are awesome!");
		} else {
			String q = "INSERT INTO Skill VALUES ('" + p.getName() + "','0','100',0','0','0','0','0','0');";
			DBApi.executeQuery(q);
			p.sendMessage("Nice, a new player.. Zilacon, see what you can make of him.. xD");
		}
	}
	
	@EventHandler
	public void onEdE(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
				Random rand = new Random();
				int x = rand.nextInt(100);
				Player p = (Player) e.getEntity();
				//Evasion
				if(Double.parseDouble(DBApi.grabData("Skill", p.getName(), "evasion")) != 0.0) {
				double echance = Double.parseDouble(DBApi.grabData("Skill", p.getName(), "evasion"))*0.1;
				if (x <= echance) {
					e.setDamage(0.0);
					p.sendMessage("DODGED");
					
				}
			}
		}
	}
	
	int recCheck = 0;
	int armCheck = 0;
	
	@EventHandler
	public void onEd(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			Plugin plugin = new Plugin();
			//Recovery
			if(Double.parseDouble(DBApi.grabData("Skill", p.getName(), "recovery")) != 0.0) {
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
					public void run() {
						while(p.getHealth() != p.getMaxHealth()) {
							long t = System.currentTimeMillis();
							long end = t + 10000;
							double points = Double.parseDouble(DBApi.grabData("Skill", p.getName(), "recovery"));
							double rec = points * 0.01;
							while (System.currentTimeMillis() < end) {
								if(p.getHealth() == p.getMaxHealth()) {
									break;
								} else {
									p.setHealth(p.getHealth() + rec);
								}
							}
						}
					}					
				}, 600);
			}
			
			//Armor
			if (p.getInventory().getArmorContents() != null) {
				ItemStack[] aCts = p.getInventory().getArmorContents();
				ItemStack head = aCts[0];
				ItemStack chest = aCts[1];
				ItemStack leg = aCts[2];
				ItemStack booty = aCts[3];
				if(Double.parseDouble(DBApi.grabData("Skill", p.getName(), "armor")) != 0.0) {
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							long t = System.currentTimeMillis();
							long end = t + 10000;
							short points = Short.parseShort(DBApi.grabData("Skill", p.getName(), "armor"));
							short armo = (short) (points * 1);
							while (System.currentTimeMillis() < end) {
								head.setDurability((short) (head.getDurability() + armo));
								chest.setDurability((short) (chest.getDurability() + armo));
								leg.setDurability((short) (leg.getDurability() + armo));
								booty.setDurability((short) (booty.getDurability() + armo));
							}
						}
					}, 600);
				}
			}
		}
	}
}
