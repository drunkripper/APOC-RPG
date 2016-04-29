package com.APOCRPG.Events;

import com.APOCRPG.API.Database;
import com.APOCRPG.Entities.APlayer;
import com.APOCRPG.Main.Plugin;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class PlayerEvents implements Listener {

    private Database db = new Database();
    private Plugin plugin;

    List<String> BOSSES = plugin.getConfig().getStringList("Mobs.bosses");

    //Player kills a monster, a boss or another player
    @EventHandler
    public void onMobKill(EntityDeathEvent e) {

        //If the killer's not a player, stop
        if (!(e.getEntity().getKiller() instanceof Player)) return;

        Entity killed = e.getEntity();
        APlayer killer = (APlayer) e.getEntity().getKiller();
        int expToBeAdded = 0;

        //If it's one of the bosses from the list = 5 XP
        if (BOSSES.contains(killed.getEntityId())) {

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
    //Reassigning all skills if needed
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        APlayer p = (APlayer) e.getPlayer();
        p.addToDatabase();
    }
}