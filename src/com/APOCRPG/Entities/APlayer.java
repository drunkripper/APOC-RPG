package com.APOCRPG.Entities;

import com.APOCRPG.API.Database;
import com.APOCRPG.Enums.PlayerStats;
import org.bukkit.entity.Player;

public abstract class APlayer implements Player {

    private static Database db = new Database();

    //Public methods
    public void increaseStat(PlayerStats ps, int value) { setStat(ps, getStat(ps)+value); }

    public void reduceStat(PlayerStats ps, int value) { setStat(ps, getStat(ps)-value); }

    //TODO: Come up with a better algorithm
    public int getALevel() {
        boolean found = false;
        int tempMinExp = 0, tempMaxExp, level = 1,
            increasePercentage = 10, currentExp = getCurrentExp();

        while (!found) {
            tempMaxExp = 100*((increasePercentage/100)*level);
            if (tempMinExp < currentExp && currentExp < tempMaxExp) {
                found = true;
                return level;
            } else {
                level++;
                tempMinExp = tempMaxExp++;
            }
        }
        return -1;
    }

    //TODO: Better algorithm here as well
    public int getExpRoof() {
        boolean found = false;
        int tempMinExp = 0, tempMaxExp, level = 1,
                increasePercentage = 10, currentExp = getCurrentExp();

        while (!found) {
            tempMaxExp = 100*((increasePercentage/100)*level);
            if (tempMinExp < currentExp && currentExp < tempMaxExp) {
                found = true;
                return tempMaxExp;
            } else {
                level++;
                tempMinExp = tempMaxExp++;
            }
        }
        return -1;
    }

    public boolean isLevelingUp (int addedExp) {
        if (getExpRoof() >= (getExp()+addedExp)) {return true;} else {return false;}
    }

    public int getCurrentExp() { return db.getPlayerStat(this, PlayerStats.EXP); }

    public void addToDatabase() {
        db.addPlayer(this);
    }

    public void updateDatabase() {
        // TODO
    }

    public void getProfile() {

    }

    //Private methods
    private int getStat(PlayerStats ps) {
        return db.getPlayerStat(this, ps);
    }

    private void setStat(PlayerStats ps, int value) {
        db.setPlayerStat(this, ps, value);
    }
}
