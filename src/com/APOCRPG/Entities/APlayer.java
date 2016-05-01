package com.APOCRPG.Entities;

import com.APOCRPG.API.Database;
import com.APOCRPG.Enums.PlayerStats;
import com.APOCRPG.Enums.ProfileStats;
import com.APOCRPG.Main.Plugin;
import org.bukkit.entity.Player;

public abstract class APlayer implements Player {

    /*

    Logic behind experience points

    Level 1   Level 2    Level 3     Level 4        Levels
    |---------|----------|-----------|              Experience bar
    0        101        211         331             Experience points
       +100       +110        +120                  Needed Exp per level

    Notes:

    Each new lever start with an odd number due the roof of the previous one is even.

    */

    private static Database db = new Database();
    private Plugin plugin;

    //Public methods
    public void increaseStat(PlayerStats ps, int value) { setStat(ps, getStat(ps)+value); }

    public void reduceStat(PlayerStats ps, int value) { setStat(ps, getStat(ps)-value); }

    //TODO: Come up with a more compat design - LOWER PRIORITY
    public int getALevel() {
        int tempMinExp = 0, tempMaxExp, level = 1,
            increasePercentage = 10, currentExp = getCurrentExp();

        while (true) {
            tempMaxExp = 100*((increasePercentage/100)*level);
            if (tempMinExp <= currentExp && currentExp <= tempMaxExp) {
                return level;
            } else {
                level++;
                tempMinExp = tempMaxExp++;
            }
        }
    }

    //TODO: Compact the algorithm here as well - LOWER PRIORITY
    public int getExpRoof() {
        boolean found = false;
        int tempMinExp = 0, tempMaxExp, level = 1,
                increasePercentage = 10, currentExp = getCurrentExp();

        while (!found) {
            tempMaxExp = 100*((increasePercentage/100)*level);
            if (tempMinExp <= currentExp && currentExp <= tempMaxExp) {
                found = true;
                return tempMaxExp;
            } else {
                level++;
                tempMinExp = tempMaxExp++;
            }
        }
        return -1;
    }

    public boolean isLevelingUp (int addedExp) { if (getExpRoof()+1 >= (getExp()+addedExp)) {return true;} else {return false;} }

    public int getCurrentExp() { return db.getPlayerStat(this, PlayerStats.EXP); }

    public void addToDatabase() {
        db.addPlayer(this);
    }

    public boolean inCombat() { if (plugin.PlayersInCombat.containsKey(this)) { return true; } else { return false; } }

    public void setInCombat() { plugin.PlayersInCombat.put(this, 30*20); } //20 ticks = 1 sec

    public int getProfileStat(ProfileStats ps) { return db.getProfileStat(this, ps); }

    public int getStat(PlayerStats ps) { return db.getPlayerStat(this, ps); }

    private void setStat(PlayerStats ps, int value) {
        db.setPlayerStat(this, ps, value);
    }
}
