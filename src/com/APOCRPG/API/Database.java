package com.APOCRPG.API;

import com.APOCRPG.Enums.DatabaseTables;
import com.APOCRPG.Enums.ProfileStats;
import com.APOCRPG.Main.Plugin;
import com.APOCRPG.Enums.PlayerStats;
import com.APOCRPG.Main.Settings;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.List;

public class Database {

    /* Database Layout

    Database
        |-Tables
            |-Columns

    APOC_DB
        |-Profiles
            |-UUID              INT(32)         // That's the trimmed UUID, no hyphens '-'
            |-LatestName        VARCHAR(35)
            |-usedIPv4s         DUNNO_YET       // To be added (maybe)...
        |-ProfileStats
            |-UUID              INT(32)
            |-Stat              VARCHAR(40)
            |-Value             INT(5)
        |-Players
            |-UUID              INT(32)         //Same as above
            |-Exp               INT(5)
            |-Stat_Points       INT(5)
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

	// Only need one instance of Plugin
//    private final Plugin plugin = new Plugin();
    private final String globalDatabase = Settings.Cfg.DATABASE_NAME.getString(); //Global Database name for the plugin
    private final String dbHost = Settings.Cfg.DATABASE_HOST.getString();
    private final int dbPort = Settings.Cfg.DATABASE_PORT.getInt();
    private final String uname = Settings.Cfg.DATABASE_UNAME.getString();
    private final String passwd = Settings.Cfg.DATABASE_PASSWD.getString();

    private String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + globalDatabase;

    // Public methods
    public int getStat(Player p, PlayerStats ps) {
        try {
            Connection conn = DriverManager.getConnection(url, uname, passwd);
            PreparedStatement statement = conn.prepareStatement("SELECT ? FROM players WHERE UUID=?");
            statement.setString(1, ps.toString()); statement.setInt(2, Integer.valueOf(p.getUniqueId().toString()));
            ResultSet result = statement.executeQuery();
            statement.close();
            conn.close();
            return result.getInt(0);
        } catch (Exception e) {        	
            Plugin.logger.warning(Settings.Cfg.APOCRPG_ERROR_DATABASE_CONNECTION.getString());
            Plugin.logger.warning(e.getMessage());
        }
        return -1;
    }

    public int getStat(Player p, ProfileStats ps) {
        try {
            Connection conn = DriverManager.getConnection(url, uname, passwd);
            PreparedStatement statement = conn.prepareStatement("SELECT value FROM profilestats WHERE UUID=? AND Stat=?");
            statement.setInt(1, Integer.valueOf(p.getUniqueId().toString())); statement.setString(2, ps.toString());
            ResultSet result = statement.executeQuery();
            statement.close(); conn.close();
            return result.getInt(0);
        } catch (Exception e) {
            Plugin.logger.warning(Settings.Cfg.APOCRPG_ERROR_DATABASE_CONNECTION.getString());
            Plugin.logger.warning(e.getMessage());
        }
        return -1;
    }

    public void setStat(Player p, PlayerStats ps, int value) {
        try {
            Connection conn = DriverManager.getConnection(url, uname, passwd);
            PreparedStatement statement = conn.prepareStatement("UPDATE players SET ?=? WHERE UUID=?;");
            statement.setString(1, ps.name()); statement.setInt(2, value); Integer.valueOf(p.getUniqueId().toString());
            statement.executeQuery();
            statement.close(); conn.close();
        } catch (Exception e) {
        	Plugin.logger.warning(Settings.Cfg.APOCRPG_ERROR_DATABASE_CONNECTION.getString());
        	Plugin.logger.warning(e.getMessage());
        }
    }

    public void setStat(Player p, ProfileStats ps, int value) {
        try {
            Connection conn = DriverManager.getConnection(url, uname, passwd);
            PreparedStatement statement = conn.prepareStatement("UPDATE profilestats SET value=? WHERE UUID=? AND Stat=?;");
            statement.setInt(1, value); statement.setInt(2, Integer.valueOf(p.getUniqueId().toString())); statement.setString(2, ps.name());
            statement.executeQuery();
            statement.close(); conn.close();
        } catch (Exception e) {
        	Plugin.logger.warning(Settings.Cfg.APOCRPG_ERROR_DATABASE_CONNECTION.getString());
        	Plugin.logger.warning(e.getMessage());
        }
    }

    public void addPlayer(Player p) {
        try {
            //Checking if player's already exists
            Connection conn = DriverManager.getConnection(url, uname, passwd);
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
        	Plugin.logger.warning(Settings.Cfg.APOCRPG_ERROR_DATABASE_CONNECTION.getString());
        	Plugin.logger.warning(e.getMessage());
        }
    }

    public void updatePlayer(Player p) {
        // TODO: add stuffz here
    }

    public void getPlayerProfile() {
        // TODO
    }

    public Object insecureQuery(String database, String query) {
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
        	Plugin.logger.warning(Settings.Cfg.APOCRPG_ERROR_DATABASE_CONNECTION.getString());
        	Plugin.logger.warning(e.getMessage());
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

    private boolean checkExistingDb() {
        List<String> tables = null;
        /* try {
            Connection conn = DriverManager.getConnection(url, uname, passwd);

            //Getting all the tables
            ResultSet rs = conn.getMetaData().getTables(Settings.Cfg.DATABASE_NAME.getString(),
                                                        null, "%", null);
            while (rs.next()) {
                tables.add(rs.getString(3));
            }

            rs.close();
        } catch (Exception e) {
            plugin.getLogger().warning(Settings.Cfg.APOCRPG_ERROR_DATABASE_CONNECTION.getString());
            plugin.getLogger().warning(e.getMessage());
        } */
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
