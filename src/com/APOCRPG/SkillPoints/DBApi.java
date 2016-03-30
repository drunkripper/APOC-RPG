package com.APOCRPG.SkillPoints;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.entity.Player;

import com.APOCRPG.Main.Plugin;

public class DBApi {
    public static void executeQuery(String query){
    	String databaseHost = Plugin.Settings.getString("server_ip");
    	int port = Plugin.Settings.getInt("server_port");
    	String username = Plugin.Settings.getString("server_user");
    	String password = Plugin.Settings.getString("server_password");
    	
        Connection conn;
        String url = "jdbc:mysql://" + databaseHost + ":" + port + "/Skill";
       
        //Attempt to connect
        try{
          //Connection succeeded
          conn = DriverManager.getConnection(url, username, password);
          PreparedStatement statement = conn.prepareStatement(query);
          statement.executeQuery();
          conn.close();
        } catch(Exception e){
          //Couldn't connect to the database
        }
      }
    

    
    public static boolean checkPlayerExist(Player p) {
    	String databaseHost = Plugin.Settings.getString("server_ip");
    	int port = Plugin.Settings.getInt("server_port");
    	String username = Plugin.Settings.getString("server_user");
    	String password = Plugin.Settings.getString("server_password");
    	
        Connection conn;
        String url = "jdbc:mysql://" + databaseHost + ":" + port + "/Skill";
       
        //Attempt to connect
        try{
          //Connection succeeded
          conn = DriverManager.getConnection(url, username, password);
          PreparedStatement sql = conn.prepareStatement("SELECT * FROM `Skill` WHERE player=?");
          sql.setString(1, p.getName());
          ResultSet rs = sql.executeQuery();
          boolean containsPlayer = rs.next();
          sql.close();
          conn.close();
          
          return containsPlayer;
        }catch(Exception e) {
        	e.printStackTrace();
        	return false;
        }
    }
    
    public static void addXp(Player p, double amt) {
    	String databaseHost = Plugin.Settings.getString("server_ip");
    	int port = Plugin.Settings.getInt("server_port");
    	String username = Plugin.Settings.getString("server_user");
    	String password = Plugin.Settings.getString("server_password");
    	
        Connection conn;
        String url = "jdbc:mysql://" + databaseHost + ":" + port + "/Skill";
       
        //Attempt to connect
        try{
          //Connection succeeded
          conn = DriverManager.getConnection(url, username, password);
          
          PreparedStatement stat = conn.prepareStatement("SELECT * FROM `Skill` WHERE player=?");
          stat.setString(1, p.getName());
          ResultSet rs = stat.executeQuery();
          PreparedStatement statement = conn.prepareStatement("UPDATE `Skill` SET current_xp=? WHERE player=?;");
          statement.setDouble(1, rs.getDouble("current_xp") + amt);
          statement.setString(2, p.getName());
          statement.executeUpdate();
          statement.close();
          conn.close();
        } catch(Exception e){
          //Couldn't connect to the database
        }
    }
}
