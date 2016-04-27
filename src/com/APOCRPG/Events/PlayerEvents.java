package com.APOCRPG.Events;

import com.APOCRPG.API.Database;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEvents implements Listener {

    private Database db = new Database();

    //Player kills a monster, a boss or another player
    @EventHandler
    public void onMobKill(EntityDeathEvent e) {
        Entity killed = e.getEntity();
        Entity killer = e.getEntity().getKiller();

        if (!(killed instanceof Monster)) return;


    }

    //Player joins the game
    //Adds player to the DB and updates it
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        db.addPlayer(p);
    }
}