package com.APOCRPG.Main;

import org.bukkit.entity.Player;

public class Economy {
	
	private static net.milkbowl.vault.economy.Economy Economy = Plugin.Plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class).getProvider();
	
	public static void addMoney(Player Player, double amount) {
		addMoney(Player.getName(), amount);
	}
	
	public static void addMoney(String Player, double amount) {
		Economy.depositPlayer(Player, amount);
	}
	
	public static void removeMoney(Player Player, double amount) {
		removeMoney(Player.getName(), amount);
	}
	
	public static void removeMoney(String Player, double amount) {
		Economy.withdrawPlayer(Player, amount);
	}
	
	public String format(double amount) {
		return Economy.format(amount);
	}
	
	public static boolean hasMoney(Player Player, double amount) {
		return hasMoney(Player.getName(), amount);
	}
	
	public static boolean hasMoney(String Player, double amount) {
		return Economy.has(Player, amount);
	}
	
}
