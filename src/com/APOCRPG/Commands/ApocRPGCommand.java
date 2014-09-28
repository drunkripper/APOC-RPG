package com.APOCRPG.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.APOCRPG.API.ItemAPI;
import com.APOCRPG.Main.Economy;
import com.APOCRPG.Main.Plugin;

public class ApocRPGCommand implements CommandExecutor {

	int gearCost = Plugin.Settings.getInt("Command-Settings.cost-for-gear");
	int gemCost = Plugin.Settings.getInt("Command-Settings.cost-for-gem");
	int tomeCost = Plugin.Settings.getInt("Command-Settings.cost-for-tome");
	int nameCost = Plugin.Settings.getInt("Command-Settings.cost-for-name");
	int loreCost = Plugin.Settings.getInt("Command-Settings.cost-for-lore");
	int enchantCost = Plugin.Settings.getInt("Command-Settings.cost-to-enchant");
	int disenchantCost = Plugin.Settings.getInt("Command-Settings.cost-to-disenchant");
	int salvageCost = Plugin.Settings.getInt("Command-Settings.cost-to-salvage");
	int repairCost = Plugin.Settings.getInt("Command-Settings.cost-to-repair");

	@Override
	public boolean onCommand(CommandSender Sender, Command Command, String label, String[] args) {
		Player Player = (Player) Sender;
		if (Command.getLabel().equals("apocrpg")) {
			if (args.length == 0) {
				ChatColor CommandColor = ChatColor.GOLD;
				ChatColor DescColor = ChatColor.BLUE;
				Player.sendMessage(CommandColor + "/apocrpg buy " + DescColor + "           | Buy an enchanted item.");
				Player.sendMessage(CommandColor + "/apocrpg buy enchant " + DescColor + "| Buy a enchantment.");
				Player.sendMessage(CommandColor + "/apocrpg buy item " + DescColor + "      | Buy a random RPG item.");
				Player.sendMessage(CommandColor + "/apocrpg buy name " + DescColor + "     | Buy an enchanted item with a set name.");
				Player.sendMessage(CommandColor + "/apocrpg buy book " + DescColor + "     | Buy an identity tome.");
				Player.sendMessage(CommandColor + "/apocrpg buy gem" + DescColor + "       | Buy a socket gem.");
				Player.sendMessage(CommandColor + "/apocrpg sell" + DescColor + "            | Sell a APOC-RPG item.");
				Player.sendMessage(CommandColor + "/apocrpg sell all" + DescColor + "        | Sell all APOC-RPG items in inventory.");
				Player.sendMessage(CommandColor + "/apocrpg repair" + DescColor + "         | Repair a item.");
				Player.sendMessage(CommandColor + "/apocrpg repair all" + DescColor + "         | Repair all items in inventory.");
			} else {
				String arg1 = args[0];
				if (arg1.equals("buy")) {
					Inventory Inventory = Player.getInventory();
					if (args.length > 1) {
						String arg2 = args[1];
						
						if (arg2.equals("enchant")) {
							
						} else if (arg2.equals("item")) {
							
						} else if (arg2.equals("name")) {
							if (Economy.hasMoney(Player, gearCost)) {
								Economy.removeMoney(Player, gearCost);
								ItemStack Item = ItemAPI.createItem();
								ItemMeta meta = Item.getItemMeta();
								meta.setDisplayName(args[2]);
								Item.setItemMeta(meta);
								Inventory.addItem(Item);
							} else {
								Player.sendMessage(ChatColor.RED + "[APOC-RPG] Not enough money!");
							}
						} else if (arg2.equals("book")) {
							
						} else if (arg2.equals("gem")) {
							if (Economy.hasMoney(Player, gemCost)) {
								Economy.removeMoney(Player, gemCost);
								Inventory.addItem(ItemAPI.createSocket());
							} else {
								Player.sendMessage(ChatColor.RED + "[APOC-RPG] Not enough money!");
							}
						}
					} else if (Economy.hasMoney(Player, gearCost)) {
						Economy.removeMoney(Player, gearCost);
						Inventory.addItem(ItemAPI.createItem());
					} else {
						Player.sendMessage(ChatColor.RED + "[APOC-RPG] Not enough money!");
					}
				} else if (arg1.equals("repair")) {
					
				}
			}
			return true;
		} else {
			System.out.println("[APOC-RPG] Unkown command sent.");
			return false;
		}
	}
}
