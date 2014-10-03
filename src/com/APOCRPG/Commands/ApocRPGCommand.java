package com.APOCRPG.Commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.APOCRPG.API.GemAPI;
import com.APOCRPG.API.ItemAPI;
import com.APOCRPG.Main.Economy;
import com.APOCRPG.Main.Plugin;

public class ApocRPGCommand implements CommandExecutor {

	double gearCost = Plugin.Settings.getDouble("Command-Settings.cost-for-gear");
	double gemCost = Plugin.Settings.getDouble("Command-Settings.cost-for-gem");
	double tomeCost = Plugin.Settings.getDouble("Command-Settings.cost-for-tome");
	double nameCost = Plugin.Settings.getDouble("Command-Settings.cost-for-name");
	double loreCost = Plugin.Settings.getDouble("Command-Settings.cost-for-lore");
	double enchantCost = Plugin.Settings.getDouble("Command-Settings.cost-to-enchant");
	double disenchantCost = Plugin.Settings.getDouble("Command-Settings.cost-to-disenchant");
	double salvageCost = Plugin.Settings.getDouble("Command-Settings.cost-to-salvage");
	double repairCost = Plugin.Settings.getDouble("Command-Settings.cost-to-repair");

	@Override
	public boolean onCommand(CommandSender Sender, Command Command, String label, String[] args) {
		String arg1 = new String();
		String arg2 = new String();
		String arg3 = new String();
		
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
				for ( int i = 0; i < args.length; i++){
					if ( i == 0 )
						arg1 = args[i];
					else if ( i == 1 )
						arg2 = args[i];
					else if ( i == 2 )
						arg3 = args[i];
				}
				if (arg1.equals("buy")) {
					Inventory Inventory = Player.getInventory();
					if (args.length > 1) {
						
						if (arg2.equals("enchant")) {
							if (arg3.equalsIgnoreCase("list")){
								ItemStack item = Player.getItemInHand();
								if ( item == null ) {
									Player.sendMessage(ChatColor.RED + "[APOC-RPG] No item being held!");
								} else {
									Player.sendMessage(ChatColor.RED + "[APOC-RPG] Item durability: " + item.getDurability());
								}
							}
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
								//Inventory.addItem(ItemAPI.createSocket());
								Inventory.addItem(GemAPI.createGem());
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
					double curDurability = 0;
					double maxDurability = 0;
					double pctDmg = 0;
					double cost = 0;
					double totalCost = 0;
					Inventory inventory = Player.getInventory();
					int invSize = inventory.getSize();
					ItemStack item = null;
					ArrayList<ItemStack> items = new ArrayList<ItemStack>();
						
					if ( arg2 == null || arg2.equals("")) {
						item = Player.getItemInHand();
						if ( item != null && item.getDurability() > 0 ) {
							items.add(item);
							curDurability = (double)item.getDurability();
							maxDurability = (double)Material.getMaterial(item.getTypeId()).getMaxDurability();
							pctDmg = (curDurability / maxDurability);
							if ( pctDmg > 0.0 ) {
								cost = repairCost * pctDmg;
								totalCost += cost;
							}							
						}
					} else if ( arg2.equalsIgnoreCase("all")){
						for ( int i = 0; i < invSize; i++){
							item = inventory.getItem(i);
							if ( item != null && item.getDurability() > 0 ) {
								items.add(item);
								curDurability = (double)item.getDurability();
								maxDurability = (double)Material.getMaterial(item.getTypeId()).getMaxDurability();
								pctDmg = (curDurability / maxDurability);
								if ( pctDmg > 0.0 ) {
									cost = repairCost * pctDmg;
									totalCost += cost;
								}							
							}
						}
					}
					if ( totalCost == 0 ) {
						Player.sendMessage("You have nothing to repair.");
					} else if ( Economy.hasMoney(Player, totalCost)) {
						StringBuffer itemNames = new StringBuffer();
						Economy.removeMoney(Player, totalCost);
						for ( int j = 0; j < items.size(); j++){
							item = items.get(j);
							item.setDurability(Short.parseShort("0"));
							if ( j == 0 ){
								itemNames.append(item.getItemMeta().getDisplayName());
							} else {
								itemNames.append(", "+item.getItemMeta().getDisplayName());
							}
						}
						Player.sendMessage("You have repaired your " + itemNames.toString() + " for " + Economy.format(cost) + " economy.");
					} else {
						Player.sendMessage("You don't have enough money! You need " + Economy.format(cost) + " economy.");
					}
				}
			}
			return true;
		} else {
			System.out.println("[APOC-RPG] Unkown command sent.");
			return false;
		}
	}
}
