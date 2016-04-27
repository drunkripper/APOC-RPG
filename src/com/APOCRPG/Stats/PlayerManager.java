package com.APOCRPG.Stats;

import com.APOCRPG.API.Database;
import org.bukkit.entity.Player;

public class PlayerManager {

    /*

    Category                    Type                ID (To be removed, not used)

    Mob Kills                   Integer (..)        0
    Player Kills                ..                  1
    Boss Kills                  ..                  2
    Dungeons Conquered          ..                  3
    Total Chests Looted         ..                  4
    Total Commons Found         ..                  5
    Total Uncommons Found       ..                  6
    Total Magics Found          ..                  7
    Total Rares Found           ..                  8
    Total Sets Found            ..                  9
    Total Sets Owned            ..                  10
    Total Legendaries Found     ..                  11
    Total Legendaries Owned     ..                  12
    Items Repaired              ..                  13
    Items Account Bound         ..                  14
    Items Purchased             ..                  15
    Items Sold                  ..                  16
    Items Salvaged              ..                  17
    Items Identified            ..                  18
    Items Customized            ..                  19
            (Bought sockets,
             enchantments,
             renamed, etc)
    Items Socketed              ..                  20
            (Gem inserted
             into item)
    Gems Found                  ..                  21
    Gems Owned                  ..                  22
    Recovery Points             ..                  23
    Evasion Points              ..                  24
    Agility Points              ..                  25
    Luck Points                 ..                  26
    Armor Points                ..                  27
    Total Stat Points           ..                  28
    Total Skill Points.         ..                  29

     */

    private Player player;

    private Database db = new Database();

    public PlayerManager(Player p) {
        this.player = p;
    }

    // You can now cast values to every other numerical primitive.
    // Turn returned value to every numerical primitive via adding example: .doubleValue()
    public int getStat(PlayerStats ps) {
        return 0;
    }

    public void setStat(PlayerStats s, Number amount) {

    }

    public void addToDb(){

    }

}
