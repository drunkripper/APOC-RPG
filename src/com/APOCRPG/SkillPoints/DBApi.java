package com.APOCRPG.SkillPoints;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.bukkit.entity.Player;

import com.APOCRPG.Main.Plugin;

public class DBApi {
	
	public static void executeQuery(String query){
    	String databaseHost = Plugin.instance.getConfig().getString("server_ip");
    	int port = Plugin.instance.getConfig().getInt("server_port");
    	String username = Plugin.instance.getConfig().getString("server_user");
    	String password = Plugin.instance.getConfig().getString("server_password");
    	
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
    	String databaseHost = Plugin.instance.getConfig().getString("server_ip");
    	int port = Plugin.instance.getConfig().getInt("server_port");
    	String username = Plugin.instance.getConfig().getString("server_user");
    	String password = Plugin.instance.getConfig().getString("server_password");
    	
        Connection conn;
        String url = "jdbc:mysql://" + databaseHost + ":" + port + "/Skill";
       
        //Attempt to connect
        try{
          //Connection succeeded
          conn = DriverManager.getConnection(url, username, password);
          PreparedStatement sql = conn.prepareStatement("SELECT * FROM `Skill` WHERE player_name=?");
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
    	String databaseHost = Plugin.instance.getConfig().getString("server_ip");
    	int port = Plugin.instance.getConfig().getInt("server_port");
    	String username = Plugin.instance.getConfig().getString("server_user");
    	String password = Plugin.instance.getConfig().getString("server_password");
    	
        Connection conn;
        String url = "jdbc:mysql://" + databaseHost + ":" + port + "/Skill";
       
        //Attempt to connect
        try{
          //Connection succeeded
          conn = DriverManager.getConnection(url, username, password);
          
          PreparedStatement stat = conn.prepareStatement("SELECT * FROM `Skill` WHERE player_name=?");
          stat.setString(1, p.getName());
          ResultSet rs = stat.executeQuery();
          PreparedStatement statement = conn.prepareStatement("UPDATE `Skill` SET current_xp=? WHERE player_name=?;");
          statement.setDouble(1, rs.getDouble("current_xp") + amt);
          statement.setString(2, p.getName());
          statement.executeUpdate();
          statement.close();
          conn.close();
        } catch(Exception e){
          //Couldn't connect to the database
        }
    }
    
    public static void addLog(String mob, String player, Timestamp ts) {
    	String databaseHost = Plugin.instance.getConfig().getString("server_ip");
    	int port = Plugin.instance.getConfig().getInt("server_port");
    	String username = Plugin.instance.getConfig().getString("server_user");
    	String password = Plugin.instance.getConfig().getString("server_password");
    	
        Connection conn;
        String url = "jdbc:mysql://" + databaseHost + ":" + port + "/Skill";
       
        //Attempt to connect
        try{
          //Connection succeeded
          conn = DriverManager.getConnection(url, username, password);
          
          PreparedStatement stet = conn.prepareStatement("INSERT INTO Logs (player_name,killed,time) VALUES (?,?,?);");
          stet.setString(1, player);
          stet.setString(2, mob);
          stet.setTimestamp(3, ts);
          stet.close();
          conn.close();
        } catch(Exception e) {
        	e.printStackTrace();
        }
    }
    
    public static String grabData(String tn, String pname, String fld) {
    	String databaseHost = Plugin.instance.getConfig().getString("server_ip");
    	int port = Plugin.instance.getConfig().getInt("server_port");
    	String username = Plugin.instance.getConfig().getString("server_user");
    	String password = Plugin.instance.getConfig().getString("server_password");
    	
        Connection conn;
        String url = "jdbc:mysql://" + databaseHost + ":" + port + "/Skill";
       
        //Attempt to connect
        try{
          //Connection succeeded
          conn = DriverManager.getConnection(url, username, password);
          
          PreparedStatement stet = conn.prepareStatement("SELECT " + fld + " FROM " + tn + " WHERE player_name = \"" + pname + "\";");
          ResultSet rs = stet.executeQuery();
          String sr = rs.getString(1);
          return sr;
        }catch(Exception e){
        	return "Poo";
        }
    }
    
    public static void addSkillp(Player p, double amt) {
    	String databaseHost = Plugin.instance.getConfig().getString("server_ip");
    	int port = Plugin.instance.getConfig().getInt("server_port");
    	String username = Plugin.instance.getConfig().getString("server_user");
    	String password = Plugin.instance.getConfig().getString("server_password");
    	
        Connection conn;
        String url = "jdbc:mysql://" + databaseHost + ":" + port + "/Skill";
       
        //Attempt to connect
        try{
          //Connection succeeded
          conn = DriverManager.getConnection(url, username, password);
          
          PreparedStatement stat = conn.prepareStatement("SELECT * FROM `Skill` WHERE player_name=?");
          stat.setString(1, p.getName());
          ResultSet rs = stat.executeQuery();
          PreparedStatement statement = conn.prepareStatement("UPDATE `Skill` SET skill_points=? WHERE player_name=?;");
          statement.setDouble(1, rs.getDouble("skill_pointss") + amt);
          statement.setString(2, p.getName());
          statement.executeUpdate();
          statement.close();
          conn.close();
        } catch(Exception e){
          //Couldn't connect to the database
        }
    }
    
    public static void addAbility(Player p, double amt, String ability) {
    	String databaseHost = Plugin.instance.getConfig().getString("server_ip");
    	int port = Plugin.instance.getConfig().getInt("server_port");
    	String username = Plugin.instance.getConfig().getString("server_user");
    	String password = Plugin.instance.getConfig().getString("server_password");
    	
        Connection conn;
        String url = "jdbc:mysql://" + databaseHost + ":" + port + "/Skill";
       
        //Attempt to connect
        try{
          //Connection succeeded
          conn = DriverManager.getConnection(url, username, password);
          
          PreparedStatement stat = conn.prepareStatement("SELECT * FROM `Skill` WHERE player_name=?");
          stat.setString(1, p.getName());
          ResultSet rs = stat.executeQuery();
          PreparedStatement statement = conn.prepareStatement("UPDATE `Skill` SET ?=? WHERE player_name=?;");
          statement.setString(1, ability);
          statement.setDouble(2, rs.getDouble(ability) + amt);
          statement.setString(3, p.getName());
          statement.executeUpdate();
          statement.close();
          conn.close();
        } catch(Exception e){
          //Couldn't connect to the database
        }
    }
}
