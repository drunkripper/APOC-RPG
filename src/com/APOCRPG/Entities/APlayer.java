package com.APOCRPG.Entities;

import com.APOCRPG.API.Database;
import com.APOCRPG.Enums.PlayerStats;

public abstract class APlayer implements org.bukkit.entity.Player {

    private static Database db = new Database();

    private int getStat(PlayerStats ps) {
        return db.getPlayerStat(this, ps);
    }

    private void setStat(PlayerStats ps, int value) {
        db.setPlayerStat(this, ps, value);
    }

    public void increaseStat(PlayerStats ps, int value) { setStat(ps, getStat(ps)+value); }

    public void reduceStat() {

    }

    public void levelUp() {
        //TODO
    }

    public void addToDatabase() {
        db.addPlayer(this);
    }

    public void updateDatabase() {
        // TODO
    }

    public void getProfile() {

    }
}
