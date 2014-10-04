package com.APOCRPG.Commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
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
	double disenchantXP = Plugin.Settings.getDouble("Command-Settings.disenchant-exp");
	double salvageCost = Plugin.Settings.getDouble("Command-Settings.cost-to-salvage");
	double repairCost = Plugin.Settings.getDouble("Command-Settings.cost-to-repair");
	
	@Override
	public boolean onCommand(CommandSender Sender, Command Command, String label, String[] args) {
		String arg1 = new String();
		String arg2 = new String();
		String arg3 = new String();
		
		Player Player = (Player) Sender;
		if (Command.getLabel().equalsIgnoreCase("apocrpg")) {
			if (args.length == 0) {
				ChatColor CommandColor = ChatColor.GOLD;
				ChatColor DescColor = ChatColor.BLUE;
				Player.sendMessage(CommandColor + "/apocrpg buy " + DescColor + "             | Buy an enchanted item.");
				Player.sendMessage(CommandColor + "/apocrpg buy enchant " + DescColor + "  | Buy an enchantment.");
				Player.sendMessage(CommandColor + "/apocrpg buy item " + DescColor + "       | Buy a random RPG item.");
				Player.sendMessage(CommandColor + "/apocrpg buy name " + DescColor + "      | Buy an enchanted item with a set name.");
				Player.sendMessage(CommandColor + "/apocrpg buy book " + DescColor + "      | Buy an identity tome.");
				Player.sendMessage(CommandColor + "/apocrpg buy gem" + DescColor + "        | Buy a socket gem.");
				Player.sendMessage(CommandColor + "/apocrpg sell" + DescColor + "             | Sell a APOC-RPG item.");
				Player.sendMessage(CommandColor + "/apocrpg sell all" + DescColor + "         | Sell all APOC-RPG items in inventory.");
				Player.sendMessage(CommandColor + "/apocrpg repair" + DescColor + "          | Repair an item.");
				Player.sendMessage(CommandColor + "/apocrpg repair all" + DescColor + "      | Repair all items in inventory.");
				Player.sendMessage(CommandColor + "/apocrpg disenchant" + DescColor + "     | Disenchant an item.");
				Player.sendMessage(CommandColor + "/apocrpg disenchant all" + DescColor + " | Disenchant all items in inventory (except for worn armor).");
			} else {
				for ( int i = 0; i < args.length; i++){
					if ( i == 0 )
						arg1 = args[i];
					else if ( i == 1 )
						arg2 = args[i];
					else if ( i == 2 )
						arg3 = args[i];
				}
				if (arg1.equalsIgnoreCase("buy")) {
					Inventory Inventory = Player.getInventory();
					if (args.length > 1) {
						
						if (arg2.equalsIgnoreCase("enchant")) {
							Player.sendMessage(ChatColor.RED + "[APOC-RPG] This command is still under development.");
							/*
							if (arg3.equalsIgnoreCase("list")){
								ItemStack item = Player.getItemInHand();
								if ( item == null || item.getType().name().equalsIgnoreCase("air") ) {
									Player.sendMessage(ChatColor.RED + "[APOC-RPG] No item being held!");
								} else {
								}
							}
							*/
						} else if (arg2.equalsIgnoreCase("item")) {
							if (Economy.hasMoney(Player, gearCost)) {
								Economy.removeMoney(Player, gearCost);
								ItemStack Item = ItemAPI.createItem();
								Inventory.addItem(Item);
							} else {
								Player.sendMessage(ChatColor.RED + "[APOC-RPG] Not enough money!");
							}
						} else if (arg2.equalsIgnoreCase("name")) {
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
						} else if (arg2.equalsIgnoreCase("book")) {
							
						} else if (arg2.equalsIgnoreCase("gem")) {
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
				} else if (arg1.equalsIgnoreCase("disenchant")) {
					double cost = 0;
					double totalXP = 0;
					
					ItemStack item = null;
					ArrayList<ItemStack> items = new ArrayList<ItemStack>();
						
					if ( arg2 == null || arg2.trim().equalsIgnoreCase("")) {
						item = Player.getItemInHand();
						if ( item != null && !item.getEnchantments().isEmpty() ) {
							items.add(item);
						}
					} else if ( arg2.equalsIgnoreCase("all")){
						Inventory inventory = Player.getInventory();
						
						// ignore the first 9 slots [0-8] as we don't want to
						// disenchant items in the action bar
						for ( int i = 9; i < inventory.getSize(); i++){
							item = inventory.getItem(i);
							if ( item != null && !item.getEnchantments().isEmpty() ) {
								items.add(item);
							}
						}
					} else {
						Player.sendMessage(ChatColor.RED + "Error: Unknown command : '"+arg1+" "+arg2+"'");
						return true;
					}
					cost = items.size() * disenchantCost;
					
					if ( cost == 0 ) {
						Player.sendMessage("You have nothing to disenchant.");
					} else if ( Economy.hasMoney(Player, cost)) {
						Economy.removeMoney(Player, cost);
						for ( int j = 0; j < items.size(); j++){
							item = items.get(j);
							Map enchantments = item.getEnchantments();
							if ( enchantments != null && ! enchantments.isEmpty()){
								Iterator it = enchantments.keySet().iterator();
								while (it.hasNext()){
									Enchantment ench = (Enchantment)it.next();
									int levels = ((Integer)enchantments.get(ench)).intValue();
									totalXP += (levels * disenchantXP);
									item.removeEnchantment(ench);
								}
							}
						}
						if ( totalXP > 0 ){
							Player.giveExp((int)totalXP);
						}
						Player.sendMessage("You have disenchanted "+items.size()+" item"+(items.size() > 1 ? "s" : "")+" for " + Economy.format(cost) + " economy and received "+(int)totalXP+" XP.");
							
					} else {
						Player.sendMessage("You don't have enough money! You need " + Economy.format(cost) + " economy.");
					}
				} else if (arg1.equalsIgnoreCase("repair")) {
					double cost = 0;
					ItemStack item = null;
					ArrayList<ItemStack> items = new ArrayList<ItemStack>();
						
					if ( arg2 == null || arg2.trim().equalsIgnoreCase("")) {
						item = Player.getItemInHand();
						if ( item != null && item.getDurability() > 0 ) {
							items.add(item);
						}
					} else if ( arg2.equalsIgnoreCase("all")){
						Inventory inventory = Player.getInventory();
						ItemStack[] armor = Player.getPlayer().getInventory().getArmorContents();
						
						for ( int i = 0; i < inventory.getSize(); i++){
							item = inventory.getItem(i);
							if ( item != null && item.getDurability() > 0 ) {
								items.add(item);
							}
						}
						for ( int j = 0; j < armor.length; j++ ){
							item = armor[j];
							if ( item != null && item.getDurability() > 0 ) {
								items.add(item);
							}
						}
					} else {
						Player.sendMessage(ChatColor.RED + "Error: Unknown command : '"+arg1+" "+arg2+"'");
					}
					cost = items.size() * repairCost;
					
					if ( cost == 0 ) {
						Player.sendMessage("You have nothing to repair.");
					} else if ( Economy.hasMoney(Player, cost)) {
						Economy.removeMoney(Player, cost);
						for ( int j = 0; j < items.size(); j++){
							item = items.get(j);
							item.setDurability(Short.parseShort("0"));
							/*
							 * fix this section where it won't throw errors
							ItemMeta meta = item.getItemMeta();
							List<String> lore = meta.getLore();
							if ( lore == null || !lore.contains("Used Item")) {
								lore.add(lore.size(),"Used Item");
								meta.setLore(lore);
								item.setItemMeta(meta);
							}
							*/
						}
						Player.sendMessage("You have repaired your item"+(items.size() > 1 ? "s" : "")+" for " + Economy.format(cost) + " economy.");
							
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
