package com.APOCRPG.SkillPoints;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.APOCRPG.Main.Plugin;

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
		}
	}
}
