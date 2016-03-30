package com.APOCRPG.SkillPoints;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class SkillGet implements Listener{
	@EventHandler
	public void onEntitydeath(EntityDeathEvent e) {
		Player p = e.getEntity().getKiller();
		if (p != null) {
			if (e.getEntity() instanceof Player) {
				DBApi.addXp(p, 2.0);
			}
		}		
	}
}
