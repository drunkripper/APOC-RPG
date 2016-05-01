package com.APOCRPG.API;

import com.APOCRPG.Enums.ProfileStats;
import com.APOCRPG.Main.Plugin;
import com.APOCRPG.Enums.PlayerStats;
import org.bukkit.entity.Player;

import java.sql.*;

public class Database {

    /* Database Layout

    Database
        |-Tables
            |-Columns

    APOC_DB
        |-Profiles
            |-UUID              INT(32)         // That's the trimmed UUID, no hyphens '-'
            |-LatestName        VARCHAR(35)
            |-usedIPv4          ..              // To be added (maybe)...
        |-ProfileStats
            |-UUID              INT(32)
            |-Stat              VARCHAR(40)
            |-Value             INT(5)
        |-Players                               // I assume every profile has multiple players?
            |-UUID              INT(32)         //Player profile's UUID
            |-Exp               INT(5)
            |-StatPoints        int(5)
            |-Recovery          INT(5)
            |-Evasion           INT(5)
            |-Agility           INT(5)
            |-Luck              INT(5)
            |-Armor             INT(5)
        |-PlayerKills
            |-UUID              INT(32)
            |-EntityType        VARCHAR(35)
        |-NameChanges
            |-UUID              INT(32)
            |-lastName          VARCHAR(35)
            |-newName           VARCHAR(35)
            |-date              DATE(25)

    */

// TODO: Needs a fallback methods when the remote database is unreachable
// TODO: Deal with NoPlayerStat exception

    private final Plugin plugin = new Plugin();
    private final String globalDatabase = plugin.DATABASE_NAME; //Global Database name for the plugin

    private final String dbHost = plugin.DATABASE_HOST;
    private final int dbPort = plugin.DATABASE_PORT;
    private final String uname = plugin.DATABASE_UNAME;
    private final String passwd = plugin.DATABASE_PASSWD;

    // Public methods
    public int getPlayerStat(Player p, PlayerStats ps) {
        Connection conn;
        String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + globalDatabase;

        try {
            conn = DriverManager.getConnection(url, uname, passwd);
            PreparedStatement statement = conn.prepareStatement("SELECT ? FROM players WHERE UUID=? AND ID=?");
            statement.setString(1, ps.toString());
            statement.setInt(2, Integer.valueOf(p.getUniqueId().toString()));
            statement.setString(3, ps.toString());
            ResultSet result = statement.executeQuery();
            statement.close();
            conn.close();
            return result.getInt(0);
        } catch (Exception e) {
            plugin.getLogger().warning(Plugin.APOCRPG_ERROR + "Couldn't establish connection to remote DB.");
            plugin.getLogger().warning(e.getMessage());
        }
        return -1;
    }

    public int getProfileStat(Player p, ProfileStats ps) {
        Connection conn;
        String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + globalDatabase;

        try {
            conn = DriverManager.getConnection(url, uname, passwd);
            PreparedStatement statement = conn.prepareStatement("SELECT value FROM profilestats WHERE UUID=? AND stat=?");
            statement.setInt(1, Integer.valueOf(p.getUniqueId().toString()));
            statement.setString(2, ps.toString());
            ResultSet result = statement.executeQuery();
            statement.close();
            conn.close();
            return result.getInt(0);
        } catch (Exception e) {
            plugin.getLogger().warning(Plugin.APOCRPG_ERROR + "Couldn't establish connection to remote DB.");
            plugin.getLogger().warning(e.getMessage());
        }
        return -1;
    }

    public void setPlayerStat(Player p, PlayerStats ps, int value) {
        Connection conn;
        String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + globalDatabase;

        try {
            conn = DriverManager.getConnection(url, uname, passwd);
            PreparedStatement statement = conn.prepareStatement("UPDATE players SET value=? WHERE UUID=?;");
            statement.setInt(1, value);
            statement.setInt(2, Integer.valueOf(p.getUniqueId().toString()));
            statement.setString(2, ps.toString());
            ResultSet result = statement.executeQuery();
            statement.close();
            conn.close();
        } catch (Exception e) {
            plugin.getLogger().warning(Plugin.APOCRPG_ERROR + "Couldn't establish connection to remote DB.");
            plugin.getLogger().warning(e.getMessage());
        }
    }

    public void setProfileStat(Player p, ProfileStats ps, int value) {
        Connection conn;
        String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + globalDatabase;

        try {
            conn = DriverManager.getConnection(url, uname, passwd);
            PreparedStatement statement = conn.prepareStatement("UPDATE playerstats SET value=? WHERE UUID=?;");
            statement.setInt(1, value);
            statement.setInt(2, Integer.valueOf(p.getUniqueId().toString()));
            statement.setString(2, ps.toString());
            ResultSet result = statement.executeQuery();
            statement.close();
            conn.close();
        } catch (Exception e) {
            plugin.getLogger().warning(Plugin.APOCRPG_ERROR + "Couldn't establish connection to remote DB.");
            plugin.getLogger().warning(e.getMessage());
        }
    }

    public void addPlayer(Player p) {
        Connection conn;
        String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + globalDatabase;

        try {
            //Checking if player's already exists
            conn = DriverManager.getConnection(url, uname, passwd);
            PreparedStatement statement = conn.prepareStatement("SELECT EXISTS(SELECT 1 FROM players WHERE UUID=?)");
            statement.setInt(1, Integer.valueOf(p.getUniqueId().toString()));
            ResultSet result = statement.executeQuery();
            if (!result.wasNull()) return; statement.close(); conn.close();

            //Adds user to the database
            statement = conn.prepareStatement("INSERT INTO players (UUID, latestname, joineddate, lastseen) VALUES (?, ?)");
            statement.setInt(1, Integer.valueOf(p.getUniqueId().toString()));   //UUID
            statement.setString(2, p.getName());                                //Latest Name
            statement.close();
            conn.close();
        } catch (Exception e) {
            plugin.getLogger().warning(Plugin.APOCRPG_ERROR + "Couldn't establish connection to remote DB.");
            plugin.getLogger().warning(e.getMessage());
        }
    }

    public void updatePlayer(Player p) {
        // TODO: add stuffz here
    }

    public void getPlayerProfile() {
        // TODO
    }

    public Object nakedQuery(String database, String query) {
        Connection conn;
        String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + database;

        //Attempt to connect
        try{
            //Connection succeeded
            conn = DriverManager.getConnection(url, uname, passwd);
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            conn.close();
            return result;
        } catch(Exception e){
            plugin.getLogger().warning(Plugin.APOCRPG_ERROR + "Couldn't establish connection to remote DB.");
            plugin.getLogger().warning(e.getMessage());
        }
        return null;
    }

    // Private methods
    private boolean createDatabase() {
        return false;
    }

    private boolean createTable() {
        return false;
    }

    public void initSetup() {

    }

    // Tests connection and privileges
    public boolean testDB() {
        return false;
    }


    /* SQLite */

    public void query() {

    }

    public void makeLocalBackup() {

    }

    private void syncDatabases() {

    }
}
