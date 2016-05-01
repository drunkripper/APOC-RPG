package com.APOCRPG.Events;

import com.APOCRPG.API.Database;
import com.APOCRPG.Entities.APlayer;
import com.APOCRPG.Enums.PlayerStats;
import com.APOCRPG.Main.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.Random;

public class PlayerEvents implements Listener {

    //List the negative potions here
    private enum NegativeEffects{
        CONFUSION, HARM, HUNGER,POISON, SLOW_DIGGING, SLOW, WEAKNESS, WITHER;
    }

    private Database db = new Database();
    private Plugin plugin;

    private List<String> BOSSES = plugin.getConfig().getStringList("Mobs.bosses");

    //Player kills a monster, a boss or another player
    @EventHandler
    public void onMobKill(EntityDeathEvent e) {

        //If the killer's not a player, stop
        if (!(e.getEntity().getKiller() instanceof Player)) return;

        Entity killed = e.getEntity();
        APlayer killer = (APlayer) e.getEntity().getKiller();
        int expToBeAdded = 0;

        //If it's one of the bosses from the list = 5 XP
        if (BOSSES.contains(String.valueOf(killed.getEntityId()))) {

            expToBeAdded += 5;

        //If it's one of the vanilla bosses = 5 XP
        } else if (killed.getType().equals(EntityType.ENDER_DRAGON) ||
                   killed.getType().equals(EntityType.WITHER)) {

            expToBeAdded += 5;

        //If it's another player = 2 XP
        } else if (killed instanceof Player) {

            expToBeAdded += 2;

        //If it's a mob (Monster, animal, etc) = 1 XP
        } else if (killed instanceof Monster) {

            //TODO: Player killed some other entity type

        }

        //Checks if player levels up after the kill
        if (killer.isLevelingUp(expToBeAdded)) {
            //killer.increaseStat(PlayerStats.);
            //TODO: What happens here, do we add the points to the profile or the player? dunno
        }
    }

    //Player joins the game
    //Adds player to the DB and updates it
    //Re-assigning healing
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        APlayer p = (APlayer) e.getPlayer();
        p.addToDatabase();

        if (p.getStat(PlayerStats.RECOVERY) > 0) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {

                //We won't heal the player:
                //    if he's been in combat in 30 secs,
                //    if he's hungry
                if (p.inCombat() || p.getFoodLevel() < 16) return;
                //    if he's affected by any bad potions
                for(PotionEffect effects: p.getActivePotionEffects()){
                    for(NegativeEffects bad: NegativeEffects.values()){
                        if(effects.getType().getName().equalsIgnoreCase(bad.name())){
                            return;
                        }
                    }
                }

                //Now we are healing that poor sucker
                while (p.getHealth() != p.getMaxHealth()) {

                    //TODO: Come up with a solution: the health is made of 20 decimals, but we're dealing with floats here

                }

            }, 200); //runs forever for every 200 ticks e 10 seconds
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        //If player is damaged, set him a the 30 sec combat counter
        if (e.getEntity() instanceof Player) { ((APlayer) e.getEntity()).setInCombat(); }
    }

    // SKILLS

    //Agility
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        APlayer player = (APlayer) e.getPlayer();

        float normalSpeed = 0.2f;
        float percentage = player.getStat(PlayerStats.AGILITY) / 10;
        player.setWalkSpeed(normalSpeed * percentage);

        //Backup technique
        /*Vector dir = player.getLocation().getDirection();
        Vector vec = new Vector(dir.getX() * percentage, dir.getY * percentage, dir.getZ() * percentage);
        player.setVelocity(vec);*/
    }

    //Evasion
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {

        //If the damager is not a player, stop
        if (!(e.getDamager() instanceof Player)) return;
        APlayer damager = (APlayer) e.getDamager();

        Random r = new Random();
        float percentage = damager.getStat(PlayerStats.EVASION) / 10;
        float chance = r.nextFloat();
        if (chance <= percentage) {
            e.setDamage(0);
            //Or cancel the event, dunno
            //e.setCancelled(true);
            damager.sendMessage(ChatColor.GREEN + "Dodged"); //I don't like to spam the chat, but... here's the message
        }
    }

    //Luck

    //Armor
}