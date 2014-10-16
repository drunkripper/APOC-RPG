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

	@Override
	public boolean onCommand(CommandSender Sender, Command Command, String label, String[] args) {
		ChatColor CommandColor = ChatColor.GOLD;
		ChatColor DescColor = ChatColor.BLUE;
		String arg1 = new String();
		String arg2 = new String();
		String arg3 = new String();
		
		Player _player = (Player) Sender;
		
		if (Command.getLabel().equalsIgnoreCase("apocrpg")) {
			if (args.length == 0) {
				_player.sendMessage(CommandColor + "/apocrpg buy " + DescColor + "             | Buy an enchanted item.");
				_player.sendMessage(CommandColor + "/apocrpg buy enchant " + DescColor + "  | Buy an enchantment.");
				_player.sendMessage(CommandColor + "/apocrpg buy gem" + DescColor + "        | Buy a socket gem.");
				_player.sendMessage(CommandColor + "/apocrpg buy item " + DescColor + "       | Buy a random RPG item.");
				_player.sendMessage(CommandColor + "/apocrpg buy name " + DescColor + "      | Buy a name for your item.");
				_player.sendMessage(CommandColor + "/apocrpg buy tome " + DescColor + "      | Buy an identity tome.");
				_player.sendMessage(CommandColor + "/apocrpg disenchant" + DescColor + "     | Disenchant an item.");
				_player.sendMessage(CommandColor + "/apocrpg disenchant all" + DescColor + " | Disenchant all items in inventory (except for worn armor).");
				_player.sendMessage(CommandColor + "/apocrpg sell" + DescColor + "             | Sell a APOC-RPG item.");
				_player.sendMessage(CommandColor + "/apocrpg sell all" + DescColor + "         | Sell all APOC-RPG items in inventory.");
				_player.sendMessage(CommandColor + "/apocrpg repair" + DescColor + "          | Repair an item.");
				_player.sendMessage(CommandColor + "/apocrpg repair all" + DescColor + "      | Repair all items in inventory.");
				_player.sendMessage(CommandColor + "/apocrpg version" + DescColor + "      | Show version information.");
			} else {
				for ( int i = 0; i < args.length; i++){
					if ( i == 0 )
						arg1 = ((String)args[i]).toLowerCase();
					else if ( i == 1 )
						arg2 = ((String)args[i]).toLowerCase();
					else if ( i == 2 )
						arg3 = args[i];
				}
				
				if (arg1.equalsIgnoreCase("version")) {
					_player.sendMessage(CommandColor + "APOC RPG Plugin | Version "+Plugin.VERSION);
					_player.sendMessage(CommandColor + "Programmed by the Apocalyptic Gaming Network");
					_player.sendMessage(CommandColor + "http://apocgaming.org");
					_player.sendMessage(CommandColor + "https://github.com/Zilacon/APOC-RPG");
				}
				else if ( !_player.hasPermission("apocrpg."+arg1+((arg2 != null && !arg2.trim().equals(""))?"."+arg2:""))){
					_player.sendMessage(Plugin.APOCRPG_ERROR_NO_PERMISSION);
					return true;
				}
				else if (arg1.equalsIgnoreCase("buy")) {
					Inventory Inventory = _player.getInventory();
					if (args.length > 1) {
						if (arg2.equalsIgnoreCase("enchant")) {
							_player.sendMessage(Plugin.APOCRPG_ERROR+"This command is still under development.");
							/*
							if (arg3.equalsIgnoreCase("list")){
								ItemStack item = _player.getItemInHand();
								if ( item == null || item.getType().name().equalsIgnoreCase("air") ) {
									_player.sendMessage(Plugin.APOCRPG_ERROR+"No item being held!");
								} else {
								}
							}
							*/
						} else if (arg2.equalsIgnoreCase("gem")) {
							if (Economy.hasMoney(_player, Plugin.COST_BUY_GEM)) {
								Economy.removeMoney(_player, Plugin.COST_BUY_GEM);
								//Inventory.addItem(ItemAPI.createSocket());
								Inventory.addItem(GemAPI.createGem());
							} else {
								_player.sendMessage(Plugin.APOCRPG_ERROR_NO_MONEY);
							}
						} else if (arg2.equalsIgnoreCase("item")) {
							if (Economy.hasMoney(_player, Plugin.COST_BUY_GEAR)) {
								Economy.removeMoney(_player, Plugin.COST_BUY_GEAR);
								ItemStack item = ItemAPI.createItem();
								Plugin.addLoreText(item, Plugin.LORE_PLAYER_BOUND, _player.getName());
								Inventory.addItem(item);
							}
						} else if (arg2.equalsIgnoreCase("name")) {
							ItemStack item = _player.getItemInHand();
							if ( arg3 != null) {
								arg3 = arg3.replaceAll("_", " ").trim();
							}
							if ( arg3 == null || arg3.equals(Plugin.LORE_UNKNOWN_ITEM)){
								_player.sendMessage(Plugin.APOCRPG_ERROR+"Invalid item name!");
							} else if (item == null || item.getType().equals(Material.AIR)) {
								_player.sendMessage(Plugin.APOCRPG_ERROR_EMPTY_HAND);
							} else if ( !Economy.hasMoney(_player, Plugin.COST_BUY_NAME)) {
								_player.sendMessage(Plugin.APOCRPG_ERROR_NO_MONEY);
							} else {
								Economy.removeMoney(_player, Plugin.COST_BUY_NAME);
								ItemMeta meta = item.getItemMeta();
								meta.setDisplayName(arg3.replaceAll("_"," ").trim());
								Plugin.addLoreText(meta, Plugin.LORE_PLAYER_BOUND, _player.getName() );
								item.setItemMeta(meta);
							}
						} else if (arg2.equalsIgnoreCase("tome")) {
							if (Economy.hasMoney(_player, Plugin.COST_BUY_TOME)) {
								Economy.removeMoney(_player, Plugin.COST_BUY_TOME);
								Inventory.addItem(ItemAPI.createTome());
							} else {
								_player.sendMessage(Plugin.APOCRPG_ERROR_NO_MONEY);
							}
						} else if (arg2.equalsIgnoreCase("unknown")) {
							if (Economy.hasMoney(_player, Plugin.COST_BUY_GEAR)) {
								Economy.removeMoney(_player, Plugin.COST_BUY_GEAR);
								ItemStack item = ItemAPI.createItem();
								if ( item.getItemMeta().hasEnchants() ) {
									Map<Enchantment, Integer> enchants = item.getEnchantments();
									Iterator<Enchantment> it = (Iterator<Enchantment>)enchants.keySet().iterator();
									while (it.hasNext()){
										Enchantment _ench = (Enchantment)it.next();
										item.removeEnchantment(_ench);
									}
								}
								Plugin.clearLore(item);
								ItemMeta meta = item.getItemMeta();
								meta.setDisplayName("Unidentified Item");
								meta.setLore(null);
								item.setItemMeta(meta);
								Inventory.addItem(item);
							} else {
								_player.sendMessage(Plugin.APOCRPG_ERROR_NO_MONEY);
							}
						}
					} else {
						_player.sendMessage(CommandColor + "/apocrpg buy enchant " + DescColor + "  | Buy an enchantment.");
						_player.sendMessage(CommandColor + "/apocrpg buy gem" + DescColor + "        | Buy a socket gem.");
						_player.sendMessage(CommandColor + "/apocrpg buy item " + DescColor + "       | Buy a random RPG item.");
						_player.sendMessage(CommandColor + "/apocrpg buy name " + DescColor + "      | Buy a name for your item.");
						_player.sendMessage(CommandColor + "/apocrpg buy tome " + DescColor + "      | Buy an identity tome.");
					}
				} else if (arg1.equalsIgnoreCase("disenchant")) {
					double cost = 0;
					double totalXP = 0;
					
					ItemStack item = null;
					List<ItemStack> items = new ArrayList<ItemStack>();
						
					if ( arg2 == null || arg2.trim().equalsIgnoreCase("")) {
						item = _player.getItemInHand();
						if ( item != null && !item.getEnchantments().isEmpty() ) {
							items.add(item);
						}
					} else if ( arg2.equalsIgnoreCase("all")){
						Inventory inventory = _player.getInventory();
						
						// ignore the first 9 slots [0-8] as we don't want to
						// disenchant items in the action bar
						for ( int i = 9; i < inventory.getSize(); i++){
							item = inventory.getItem(i);
							if ( item != null && !item.getEnchantments().isEmpty() ) {
								items.add(item);
							}
						}
					} else {
						_player.sendMessage(Plugin.APOCRPG_ERROR + "Unknown command : '"+arg1+" "+arg2+"'");
						return true;
					}
					cost = items.size() * Plugin.COST_DISENCHANT;
					
					if ( cost == 0 ) {
						_player.sendMessage("You have nothing to disenchant.");
					} else if ( Economy.hasMoney(_player, cost)) {
						Economy.removeMoney(_player, cost);
						for ( int j = 0; j < items.size(); j++){
							item = items.get(j);
							Map<Enchantment, Integer> enchantments = item.getEnchantments();
							if ( enchantments != null && ! enchantments.isEmpty()){
								Iterator<Enchantment> it = (Iterator<Enchantment>)enchantments.keySet().iterator();
								while (it.hasNext()){
									Enchantment ench = (Enchantment)it.next();
									int levels = ((Integer)enchantments.get(ench)).intValue();
									totalXP += (levels * Plugin.EXP_DISENCHANT);
									item.removeEnchantment(ench);
								}
							}
						}
						if ( totalXP > 0 ){
							_player.giveExp((int)totalXP);
						}
						_player.sendMessage("You have disenchanted "+items.size()+" item"+(items.size() > 1 ? "s" : "")+" for " + Economy.format(cost) + " economy and received "+(int)totalXP+" XP.");
							
					} else {
						_player.sendMessage(Plugin.APOCRPG_ERROR_NO_MONEY+"You need " + Economy.format(cost) + " economy.");
					}
				} else if (arg1.equalsIgnoreCase("reload")) {
					Plugin.loadConfig();
				} else if (arg1.equalsIgnoreCase("repair")) {
					double cost = 0;
					ItemStack item = null;
					
					List<ItemStack> items = new ArrayList<ItemStack>();
						
					if ( arg2 == null || arg2.trim().equalsIgnoreCase("")) {
						item = _player.getItemInHand();
						if ( item != null && item.getDurability() > 0 ) {
							items.add(item);
						}
					} else if ( arg2.equalsIgnoreCase("all")){
						Inventory inventory = _player.getInventory();
						ItemStack[] armor = _player.getPlayer().getInventory().getArmorContents();
						
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
						_player.sendMessage(Plugin.APOCRPG_ERROR + "Unknown command : '"+arg1+" "+arg2+"'");
					}
					cost = items.size() * Plugin.COST_REPAIR;
					
					if ( cost == 0 ) {
						_player.sendMessage("You have nothing to repair.");
					} else if ( Economy.hasMoney(_player, cost)) {
						Economy.removeMoney(_player, cost);
						for ( int j = 0; j < items.size(); j++){
							item = items.get(j);
							item.setDurability(Short.parseShort("0"));
							
							Plugin.debugPlayerMsg(_player, "Adding lore to item.getItemMeta().getDisplayName(): "+item.getItemMeta().getDisplayName());
							Plugin.debugPlayerMsg(_player, "Adding lore to item.getTypeId(): "+item.getTypeId());
							Plugin.debugPlayerMsg(_player, "Adding lore to item.getData().getItemType().ordinal(): "+item.getData().getItemType().ordinal());
							Plugin.debugPlayerMsg(_player, "Adding lore to item.getType().toString(): "+item.getType().toString());
							Plugin.debugPlayerMsg(_player, "Adding lore to item.getType().name()(): "+item.getType().name());
							Plugin.addLoreText(item, Plugin.LORE_PLAYER_BOUND, _player.getName());
							Plugin.addLoreText(item, Plugin.LORE_REPAIRED );
							
							ArrayList<String> lore = (ArrayList<String>)item.getItemMeta().getLore();
							for ( int i = 0; lore != null && !lore.isEmpty() && i < lore.size(); i++){
								Plugin.debugPlayerMsg(_player,"lore["+i+"] : "+lore.get(i));
							}
						}
						_player.sendMessage("You have repaired your item"+(items.size() > 1 ? "s" : "")+" for " + Economy.format(cost) + " economy.");
							
					} else {
						_player.sendMessage(Plugin.APOCRPG_ERROR_NO_MONEY+"You need " + Economy.format(cost) + " economy.");
					}
				}
			}
			return true;
		} else {
			System.out.println(Plugin.APOCRPG_ERROR+"Unknown command sent.");
			return false;
		}
	}
}
