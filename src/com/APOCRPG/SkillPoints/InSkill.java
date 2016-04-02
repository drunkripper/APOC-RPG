package com.APOCRPG.SkillPoints;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class InSkill implements Listener {
	//Class for the diff abilities
	
	ArrayList<Player> evasion = new ArrayList<Player>();
	
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
	
	//Evasion
	@EventHandler
	public void onEdE(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			if(evasion.contains((Player)e.getEntity())) {
				Random rand = new Random();
				int x = rand.nextInt(100);
				Player p = (Player) e.getEntity();
				double echance = Double.parseDouble(DBApi.grabData("Skill", p.getName(), "evasion"))/10;
				if (x <= echance) {
					e.setDamage(0.0);
					p.sendMessage("DODGED");
				}
			}
		}
	}
	
	
	
}
