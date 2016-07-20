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
       +100       +110        +121                  Needed Exp per level (every level it increases by 10%)
             +10%       +10%


    Each new level starts with an odd number due the roof of the previous one is even.

    */

    private static Database db = new Database();

    //Public methods
    public void increaseStat(PlayerStats ps, int value) { setStat(ps, (getStat(ps)+value)); }

    public void increaseStat(ProfileStats ps, int value) { setStat(ps, (getStat(ps)+value)); }

    public void reduceStat(PlayerStats ps, int value) { setStat(ps, (getStat(ps)-value)); }

    public void reduceStat(ProfileStats ps, int value) { setStat(ps, (getStat(ps)-value)); }

    //TODO: Come up with a more compact design - LOWER PRIORITY
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
        int tempMinExp = 0, tempMaxExp, level = 1,
                increasePercentage = 10, currentExp = getCurrentExp();

        while (true) {
            tempMaxExp = 100*((increasePercentage/100)*level);
            if (tempMinExp <= currentExp && currentExp <= tempMaxExp) {
                return tempMaxExp;
            } else {
                level++;
                tempMinExp = tempMaxExp++;
            }
        }
    }

    public boolean isLevelingUp (int addedExp) { if (getExpRoof()+1 >= (getExp()+addedExp)) {return true;} else {return false;} }

    public int getCurrentExp() { return db.getStat(this, PlayerStats.EXP); }

    public void addToDatabase() {
        db.addPlayer(this);
    }

    public boolean inCombat() { if (Plugin.playersInCombat.containsKey(this)) { return true; } else { return false; } }

    public void setInCombat() { Plugin.playersInCombat.put(this, 30); } //30 second timer

    public int getStat(ProfileStats ps) { return db.getStat(this, ps); }

    public int getStat(PlayerStats ps) { return db.getStat(this, ps); }

    private void setStat(PlayerStats ps, int value) {
        db.setStat(this, ps, value);
    }

    private void setStat(ProfileStats ps, int value) {
        db.setStat(this, ps, value);
    }
}
