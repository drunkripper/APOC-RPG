package com.APOCRPG.SkillPoints;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.APOCRPG.Main.Plugin;

import net.md_5.bungee.api.ChatColor;

public class SkillGet implements Listener{
	
	Date dt = new Date();
	Plugin pl = new Plugin();
	String[] bmobs = pl.getConfig().getString("mobsr").split(",");
	
	@EventHandler
	public void onEntitydeath(EntityDeathEvent e) {
		Timestamp ts = new Timestamp(dt.getTime());
		Player p = e.getEntity().getKiller();
		double xpRoof = Double.parseDouble(DBApi.grabData("Skill", p.getName(), "xp_roof"));
		
		if (p != null) {
			//Player kills Player
			if (e.getEntity() instanceof Player) {
				DBApi.addXp(p, 2.0);
				DBApi.addLog(e.getEntity().getName(), p.getName(), ts);
				p.sendMessage(ChatColor.GREEN + "2 xp Gained!");
				String cxp = DBApi.grabData("Skill", p.getName(), "current_xp");
				p.sendMessage(ChatColor.GOLD + "Your current xp: " + cxp);
				double dcxp = Double.parseDouble(cxp);

				//Check if he levels up
				if(xpRoof == dcxp) {
					p.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + "YOU HAVE LEVELED UP!");
					p.playSound(p.getLocation(), Sound.EXPLODE, 30.0f, 1.0f);
					DBApi.addSkillp(p, 5.0);
					p.sendMessage(ChatColor.GREEN + "Your current STAT POINTS are: " + DBApi.grabData("Skill", p.getName(), "skill_points"));
					double nxpf = xpRoof + (10/100.0 * xpRoof);
					String queri = "UPDATE `Skill` SET xp_roof=" + nxpf + " WHERE player_name=" + p.getName() + ";";
					DBApi.executeQuery(queri);
					p.sendMessage(ChatColor.DARK_PURPLE + "You must get " + nxpf + " more xp to level up again.");
					
				}
			}
			
			//Player kills Boss mob
			for(int i = 0; i <= bmobs.length; i++) {
				if (bmobs[i].equalsIgnoreCase(e.getEntity().getName())) {
					p.sendMessage(ChatColor.GREEN + "5 xp Gained!");
					DBApi.addXp(p, 5.0);
					DBApi.addLog(e.getEntity().getName(), p.getName(), ts);
					String cxp = DBApi.grabData("Skill", p.getName(), "current_xp");
					p.sendMessage(ChatColor.GOLD + "Your current xp: " + cxp);
					double dcxp = Double.parseDouble(cxp);
					
					//Check if he levels up
					if(xpRoof == dcxp) {
						p.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + "YOU HAVE LEVELED UP!");
						p.playSound(p.getLocation(), Sound.EXPLODE, 30.0f, 1.0f);
						DBApi.addSkillp(p, 5.0);
						p.sendMessage(ChatColor.GREEN + "Your current STAT POINTS are: " + DBApi.grabData("Skill", p.getName(), "skill_points"));
						double nxpf = xpRoof + (10/100.0 * xpRoof);
						String queri = "UPDATE `Skill` SET xp_roof=" + nxpf + " WHERE player_name=" + p.getName() + ";";
						DBApi.executeQuery(queri);
						p.sendMessage(ChatColor.DARK_PURPLE + "You must get " + nxpf + " more xp to level up again.");
						
					}
				}
			}
			//Other Mob
			if (!Arrays.asList(bmobs).contains(e.getEntity().getName())) {
				p.sendMessage(ChatColor.GREEN + "Killed a mob, 1 xp gained.");
				DBApi.addXp(p, 1.0);
				DBApi.addLog(e.getEntity().getName(), p.getName(), ts);
				String cxp = DBApi.grabData("Skill", p.getName(), "current_xp");
				p.sendMessage(ChatColor.GOLD + "Your current xp: " + cxp);
				double dcxp = Double.parseDouble(cxp);
				
				//Check if he levels up
				if(xpRoof == dcxp) {
					p.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + "YOU HAVE LEVELED UP!");
					p.playSound(p.getLocation(), Sound.EXPLODE, 30.0f, 1.0f);
					DBApi.addSkillp(p, 5.0);
					p.sendMessage(ChatColor.GREEN + "Your current STAT POINTS are: " + DBApi.grabData("Skill", p.getName(), "skill_points"));
					double nxpf = xpRoof + (10/100.0 * xpRoof);
					String queri = "UPDATE `Skill` SET xp_roof=" + nxpf + " WHERE player_name=" + p.getName() + ";";
					DBApi.executeQuery(queri);
					p.sendMessage(ChatColor.DARK_PURPLE + "You must get " + nxpf + " more xp to level up again.");
					
				}
			}
		}		
	}
}
