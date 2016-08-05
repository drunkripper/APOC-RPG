package com.APOCRPG.SkillPoints;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.APOCRPG.Main.Plugin;
import com.APOCRPG.Main.Settings;

import net.md_5.bungee.api.ChatColor;

public class SkillGet implements Listener{
	
	Date dt = new Date();
	
	// Boss MOBs
	String[] bmobs = Plugin.instance.getConfig().getString("Mobs.bosses").split(",");	
	
	@EventHandler
	public void onEntitydeath(EntityDeathEvent e) {
		Timestamp ts = new Timestamp(dt.getTime());
		Player killer = e.getEntity().getKiller();
		Entity killed = e.getEntity();
		double xpRoof = Double.parseDouble(DBApi.grabData("Skill", killer.getName(), "xp_roof"));
		
		if (killer != null) {
			//Player kills Player
			if (killed instanceof Player) {
				Player killedPlayer = (Player) e.getEntity();
				DBApi.addXp(killer, 2.0);
				DBApi.addLog(killedPlayer.getName(), killer.getName(), ts);
				killer.sendMessage(ChatColor.GREEN + "2 xp Gained!");
				String cxp = DBApi.grabData("Skill", killer.getName(), "current_xp");
				killer.sendMessage(ChatColor.GOLD + "Your current xp: " + cxp);
				double dcxp = Double.parseDouble(cxp);

				//Check if he levels up
				if(xpRoof == dcxp) {
					killer.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + "YOU HAVE LEVELED UP!");
					killer.playSound(killer.getLocation(), Sound.EXPLODE, 30.0f, 1.0f);
					DBApi.addSkillp(killer, 5.0);
					killer.sendMessage(ChatColor.GREEN + "Your current STAT POINTS are: " + DBApi.grabData("Skill", killer.getName(), "skill_points"));
					double nxpf = xpRoof + (10/100.0 * xpRoof);
					String queri = "UPDATE `Skill` SET xp_roof=" + nxpf + " WHERE player_name=" + killer.getName() + ";";
					DBApi.executeQuery(queri);
					killer.sendMessage(ChatColor.DARK_PURPLE + "You must get " + nxpf + " more xp to level up again.");
					
				}
			}			
			else
			{
				LivingEntity killedMob = e.getEntity();
				
				//Player kills Boss mob
				for(int i = 0; i <= bmobs.length; i++) {
					if (bmobs[i].equalsIgnoreCase(killedMob.getCustomName())) {
						killer.sendMessage(ChatColor.GREEN + "5 xp Gained!");
						DBApi.addXp(killer, 5.0);
						DBApi.addLog(killedMob.getCustomName(), killer.getName(), ts);
						String cxp = DBApi.grabData("Skill", killer.getName(), "current_xp");
						killer.sendMessage(ChatColor.GOLD + "Your current xp: " + cxp);
						double dcxp = Double.parseDouble(cxp);
						
						//Check if he levels up
						if(xpRoof == dcxp) {
							killer.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + "YOU HAVE LEVELED UP!");
							killer.playSound(killer.getLocation(), Sound.EXPLODE, 30.0f, 1.0f);
							DBApi.addSkillp(killer, 5.0);
							killer.sendMessage(ChatColor.GREEN + "Your current STAT POINTS are: " + DBApi.grabData("Skill", killer.getName(), "skill_points"));
							double nxpf = xpRoof + (10/100.0 * xpRoof);
							String queri = "UPDATE `Skill` SET xp_roof=" + nxpf + " WHERE player_name=" + killer.getName() + ";";
							DBApi.executeQuery(queri);
							killer.sendMessage(ChatColor.DARK_PURPLE + "You must get " + nxpf + " more xp to level up again.");
							
						}
					}
				}
				//Other Mob
				if (!Arrays.asList(bmobs).contains(killedMob.getCustomName())) {
					killer.sendMessage(ChatColor.GREEN + "Killed a mob, 1 xp gained.");
					DBApi.addXp(killer, 1.0);
					DBApi.addLog(killedMob.getCustomName(), killer.getName(), ts);
					String cxp = DBApi.grabData("Skill", killer.getName(), "current_xp");
					killer.sendMessage(ChatColor.GOLD + "Your current xp: " + cxp);
					double dcxp = Double.parseDouble(cxp);
					
					//Check if he levels up
					if(xpRoof == dcxp) {
						killer.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + "YOU HAVE LEVELED UP!");
						killer.playSound(killer.getLocation(), Sound.EXPLODE, 30.0f, 1.0f);
						DBApi.addSkillp(killer, 5.0);
						killer.sendMessage(ChatColor.GREEN + "Your current STAT POINTS are: " + DBApi.grabData("Skill", killer.getName(), "skill_points"));
						double nxpf = xpRoof + (10/100.0 * xpRoof);
						String queri = "UPDATE `Skill` SET xp_roof=" + nxpf + " WHERE player_name=" + killer.getName() + ";";
						DBApi.executeQuery(queri);
						killer.sendMessage(ChatColor.DARK_PURPLE + "You must get " + nxpf + " more xp to level up again.");
						
					}
				}
			}
		}		
	}
}
