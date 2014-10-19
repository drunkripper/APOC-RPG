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
/**
 * 
 * @author ???, whiteknave
 *
 */
public class ApocRPGCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender Sender, Command Command, String label, String[] args) {
		ChatColor CommandColor = ChatColor.GOLD;
		ChatColor DescColor = ChatColor.BLUE;
		String arg1 = new String();
		String arg2 = new String();
		String arg3 = new String();
		String arg4 = new String();
		
		Player _player = (Player) Sender;
		ItemStack _item = _player.getItemInHand();
		
		if (Command.getLabel().equalsIgnoreCase("apocrpg")) {
			// if no arguments, give the user the list of commands
			if (args.length == 0) {
				_player.sendMessage(CommandColor + "/apocrpg buy " + DescColor + "             | Buy an enchanted _item.");
				_player.sendMessage(CommandColor + "/apocrpg buy enchant " + DescColor + "  | Buy an enchantment.");
				_player.sendMessage(CommandColor + "/apocrpg buy gem" + DescColor + "        | Buy a socket gem.");
				_player.sendMessage(CommandColor + "/apocrpg buy item " + DescColor + "       | Buy a random RPG _item.");
				_player.sendMessage(CommandColor + "/apocrpg buy name " + DescColor + "      | Buy a name for your _item.");
				_player.sendMessage(CommandColor + "/apocrpg buy tome " + DescColor + "      | Buy an identity tome.");
				_player.sendMessage(CommandColor + "/apocrpg disenchant" + DescColor + "     | Disenchant an _item.");
				_player.sendMessage(CommandColor + "/apocrpg disenchant all" + DescColor + " | Disenchant all items in inventory (except for worn armor).");
				_player.sendMessage(CommandColor + "/apocrpg sell" + DescColor + "             | Sell a APOC-RPG _item.");
				_player.sendMessage(CommandColor + "/apocrpg sell all" + DescColor + "         | Sell all APOC-RPG items in inventory.");
				_player.sendMessage(CommandColor + "/apocrpg repair" + DescColor + "          | Repair an _item.");
				_player.sendMessage(CommandColor + "/apocrpg repair all" + DescColor + "      | Repair all items in inventory.");
				_player.sendMessage(CommandColor + "/apocrpg version" + DescColor + "      | Show version information.");
			} else {
				// determine if the player has the proper permissions.
				if ( !Plugin.hasPermission(_player, args) ){
					_player.sendMessage(Plugin.APOCRPG_ERROR+"You do not have permission to do that!");
					return true;
				}
				
				// parse the first four (4) arguments.  The first two arguments
				// are command arguements, the third and fourth arguments
				// are optional data
				for ( int i = 0; i < args.length; i++){
					if ( i == 0 )
						arg1 = ((String)args[i]).toLowerCase();
					else if ( i == 1 )
						arg2 = ((String)args[i]).toLowerCase();
					else if ( i == 2 )
						arg3 = (String)args[i];
					else if ( i == 3 )
						arg4 = (String)args[i];
				}
				
				if (arg1.equalsIgnoreCase("version")) {
					// display Plugin version information
					_player.sendMessage(CommandColor + "APOC RPG Plugin | Version "+Plugin.VERSION);
					_player.sendMessage(CommandColor + "Programmed by the Apocalyptic Gaming Network");
					_player.sendMessage(CommandColor + "http://apocgaming.org");
					_player.sendMessage(CommandColor + "https://github.com/Zilacon/APOC-RPG");
				}
				// check for command permissions
//				else if ( !_player.hasPermission("apocrpg."+arg1+((arg2 != null && !arg2.trim().equals(""))?"."+arg2:""))){
//					_player.sendMessage(Plugin.APOCRPG_ERROR_NO_PERMISSION);
//					return true;
//				}
				// do buy routine
				else if (arg1.equalsIgnoreCase("buy")) {
					// get player inventory
					Inventory Inventory = _player.getInventory();
					// if additional
					if (args.length > 1) {
						if (arg2.equalsIgnoreCase("enchant")) {
							// if nothing in hand, send error message and return
							if ( _item == null || _item.getType().name().equalsIgnoreCase("air") ) {
								_player.sendMessage(Plugin.APOCRPG_ERROR+"No _item being held!");
								return true;
							}
							
							// get list of enchantments available for this ItemStack
							ArrayList<Enchantment> enchList = (ArrayList<Enchantment>)Plugin.getEnchantmentsFor(_item);
							if ( arg3 == null || arg3.equalsIgnoreCase("list")){
								for ( int i = 0; i < enchList.size(); i++){
									Enchantment e = (Enchantment)enchList.get(i);
									// output the number (left padded) and enchantment name to the user.
									_player.sendMessage(String.format("%1$" + 4 + "s", String.valueOf(i+1)) + " " + CommandColor + e.getName());
								}
							}
							else {
								int enchNbr = -1;
								ArrayList<Enchantment> buyEnchants = new ArrayList<Enchantment>();
								
								// if all enchantments requested, use the list of available enchantments
								if ( arg3.equalsIgnoreCase("all")){
									buyEnchants = enchList;
								} else {
									Enchantment ench = null;
									// try to find the specific enchantment requested
									// see if arg3 is the number of the Enchantment or the name.
									try {
										enchNbr = Integer.parseInt(arg3);
										// if we didn't blow up, it's a number
										ench = enchList.get( enchNbr - 1 );
									} catch ( Exception e ) {
										// just need to catch the exception.
									}
									
									// if ench is null, try to match arg3 to names of enchantments
									if ( ench == null ) {
										for ( Enchantment e : enchList ) {
											if ( e.getName().equals(arg3)) {
												ench = e;
												break;
											}
										}
									}
									
									// if ench is still null.. unknown enchantment
									// or enchantment is not available for this ItemStack
									if ( ench == null ) {
										_player.sendMessage(Plugin.APOCRPG_ERROR+"That enchantment is not recognized for this _item!");
										return true;
									} else {
										buyEnchants.add(ench);
									}
								}
								int powerLvl = -1;
								// parse the requested level for the enchantment
								try{
									powerLvl = Integer.valueOf(arg4);
								} catch (NumberFormatException npe){
									powerLvl = Plugin.romanToInt(arg4);
								} catch (Exception e){
									// some other exception happened
								}
								
								if ( powerLvl < 1 || ( powerLvl > 10 && !_player.hasPermission("op"))) {
									_player.sendMessage(Plugin.APOCRPG_ERROR+"Invalid enchantment level!");
									return true;
								}
								
								// calculate cost for enchantment(s)
								double cost = 0;
								// iterate through requested enchantment(s)
								for ( Enchantment e : buyEnchants ) {
									// get current power level for this enchantment
									int currLevel = _item.getEnchantmentLevel(e);
									if ( currLevel > powerLvl ) {
										_player.sendMessage(Plugin.APOCRPG_ERROR+"This _item already exceeds that enchantment level for " + e.getName()+"!");
									} else if ( currLevel == powerLvl ){
										_player.sendMessage(Plugin.APOCRPG_ERROR+"This _item already has that enchantment level!");
										return true;
									} else {
										do {
											switch ( currLevel ) {
												case 0 : cost += Plugin.COST_ENCHANT_LVL_1; break;
												case 1 : cost += Plugin.COST_ENCHANT_LVL_2; break;
												case 2 : cost += Plugin.COST_ENCHANT_LVL_3; break;
												case 3 : cost += Plugin.COST_ENCHANT_LVL_4; break;
												case 4 : cost += Plugin.COST_ENCHANT_LVL_5; break;
												case 5 : cost += Plugin.COST_ENCHANT_LVL_6; break;
												case 6 : cost += Plugin.COST_ENCHANT_LVL_7; break;
												case 7 : cost += Plugin.COST_ENCHANT_LVL_8; break;
												case 8 : cost += Plugin.COST_ENCHANT_LVL_9; break;
												default : cost += Plugin.COST_ENCHANT_LVL_10; break;
											}
											currLevel ++;
										} while ( currLevel < powerLvl );
									}
								}
								
								// yay! player has money.  add the enchantments
								if (Economy.hasMoney(_player, cost )) {
									Economy.removeMoney(_player, cost );
									for ( Enchantment e : buyEnchants ) {
										if ( !_item.containsEnchantment(e) || _item.getEnchantmentLevel(e) < powerLvl  ) {
											_item.addUnsafeEnchantment(e, powerLvl);
										}	
									}
									// add player bound
									Plugin.addLoreText(_item, Plugin.LORE_PLAYER_BOUND, _player.getName());
									_player.sendMessage("Enchantment" + (buyEnchants.size() > 1 ? "s" : "") + " added for: $"+cost);
								} else {
									_player.sendMessage(Plugin.APOCRPG_ERROR_NO_MONEY);
								}
								
							}
						} else if (arg2.equalsIgnoreCase("gem")) {
							if (Economy.hasMoney(_player, Plugin.COST_BUY_GEM)) {
								Economy.removeMoney(_player, Plugin.COST_BUY_GEM);
								if ( !arg3.isEmpty() && !arg4.isEmpty()) {
									Inventory.addItem(GemAPI.createGem(arg3, arg4));
								} else {
									Inventory.addItem(GemAPI.createGem());
								}
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
							if ( arg3 != null) {
								arg3 = arg3.replaceAll("_", " ").trim();
							}
							if ( arg3 == null || arg3.equals(Plugin.LORE_UNKNOWN_ITEM)){
								_player.sendMessage(Plugin.APOCRPG_ERROR+"Invalid _item name!");
							} else if (_item == null || _item.getType().equals(Material.AIR)) {
								_player.sendMessage(Plugin.APOCRPG_ERROR_EMPTY_HAND);
							} else if ( !Economy.hasMoney(_player, Plugin.COST_BUY_NAME)) {
								_player.sendMessage(Plugin.APOCRPG_ERROR_NO_MONEY);
							} else {
								Economy.removeMoney(_player, Plugin.COST_BUY_NAME);
								ItemMeta meta = _item.getItemMeta();
								meta.setDisplayName(arg3.replaceAll("_"," ").trim());
								Plugin.addLoreText(meta, Plugin.LORE_PLAYER_BOUND, _player.getName() );
								_item.setItemMeta(meta);
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
						_player.sendMessage(CommandColor + "/apocrpg buy _item " + DescColor + "       | Buy a random RPG _item.");
						_player.sendMessage(CommandColor + "/apocrpg buy name " + DescColor + "      | Buy a name for your _item.");
						_player.sendMessage(CommandColor + "/apocrpg buy tome " + DescColor + "      | Buy an identity tome.");
					}
				} else if (arg1.equalsIgnoreCase("disenchant")) {
					double cost = 0;
					double totalXP = 0;
					
					List<ItemStack> items = new ArrayList<ItemStack>();
					int levels = 0;
					
					// if arg2 check _item in hand
					if ( arg2 == null || arg2.trim().equalsIgnoreCase("")) {
						if ( _item != null && !_item.getEnchantments().isEmpty() ) {
							items.add(_item);
						}
					} else if ( arg2.equalsIgnoreCase("all")){
						Inventory inventory = _player.getInventory();
						
						// ignore the first 9 slots [0-8] as we don't want to
						// disenchant items in the action bar
						for ( int i = 9; i < inventory.getSize(); i++){
							_item = inventory.getItem(i);
							if ( _item != null && !_item.getEnchantments().isEmpty() ) {
								items.add(_item);
							}
						}
					} else {
						_player.sendMessage(Plugin.APOCRPG_ERROR + "Unknown command : '"+arg1+" "+arg2+"'");
						return true;
					}
					
					// determine the total levels to be removed from all objects
					for ( ItemStack stack : items ){
						Map<Enchantment,Integer> map = stack.getEnchantments();
						Iterator<Enchantment> it = (Iterator<Enchantment>)map.keySet().iterator();
						while (it.hasNext()){
							Enchantment ench = (Enchantment)it.next();
							levels += ((Integer)map.get(ench)).intValue();
						}
					}
					
					cost = levels * Plugin.COST_DISENCHANT;
					
					if ( cost == 0 ) {
						_player.sendMessage("You have nothing to disenchant.");
					} else if ( Economy.hasMoney(_player, cost)) {
						Economy.removeMoney(_player, cost);
						
						for ( ItemStack stack : items ){
							Map<Enchantment,Integer> map = stack.getEnchantments();
							Iterator<Enchantment> it = (Iterator<Enchantment>)map.keySet().iterator();
							while (it.hasNext()){
								Enchantment ench = (Enchantment)it.next();
								stack.removeEnchantment(ench);
							}
						}
						
						totalXP += (levels * Plugin.EXP_DISENCHANT);
						if ( totalXP > 0 ){
							_player.giveExp((int)totalXP);
						}
						_player.sendMessage("You have disenchanted "+items.size()+" _item"+(items.size() > 1 ? "s" : "")+" for " + Economy.format(cost) + " economy and received "+(int)totalXP+" XP.");
							
					} else {
						_player.sendMessage(Plugin.APOCRPG_ERROR_NO_MONEY+"You need " + Economy.format(cost) + " economy.");
					}
				} else if (arg1.equalsIgnoreCase("reload")) {
					Plugin.loadConfig();
				} else if (arg1.equalsIgnoreCase("remove")) {
					if ( arg2 == null || ( !arg2.equalsIgnoreCase("gem") && !arg2.equalsIgnoreCase("socket"))) {
						_player.sendMessage(Plugin.APOCRPG_ERROR+"Invalid command!");
					} else if ( arg2.equalsIgnoreCase("gem")) {
						_player.sendMessage(Plugin.APOCRPG_ERROR+"This command is not implemented yet.");
						/*
						// see if item has "GEM OF ..." lore
						if (Plugin.containsLoreText( _item, Plugin.LORE_GEM_OF)){
							// get all lore
							ArrayList<String> lore =  (ArrayList<String>)_item.getItemMeta().getLore();
							String tmp = null;
							//iterate through lore to find first gem
							for ( int i = 0; i < lore.size(); i++ ){
								tmp = lore.get(i);
								if ( temp.equals(Plugin.LORE_GEM_OF)) {
									
								}
							}
						}
						*/
					} else if ( arg2.equalsIgnoreCase("socket")) {
						if ( _item == null || _item.getType().equals(Material.AIR) || !Plugin.containsLoreText(_item, Plugin.LORE_ITEM_SOCKET) ){
							_player.sendMessage(Plugin.APOCRPG_ERROR+"There are no empty sockets to remove!");
						} else if ( !Economy.hasMoney(_player, Plugin.COST_REMOVE_SOCKET ) ) {
							_player.sendMessage(Plugin.APOCRPG_ERROR_NO_MONEY+"You need " + Economy.format(Plugin.COST_REMOVE_SOCKET) + " economy.");
						} else {
							// get all lore
							ItemMeta meta = _item.getItemMeta();
							ArrayList<String> lore =  (ArrayList<String>)meta.getLore();
							String tmp = null;
							//iterate through lore to find first empty socket
							for ( int i = 0; i < lore.size(); i++ ){
								tmp = lore.get(i);
								if ( tmp.equals(Plugin.LORE_ITEM_SOCKET)) {
									Economy.removeMoney(_player, Plugin.COST_REMOVE_SOCKET);
									lore.remove(i);
									meta.setLore(lore);
									_item.setItemMeta(meta);
									break;
								}
							}
							
						}
					}
				} else if (arg1.equalsIgnoreCase("repair")) {
					double cost = 0;
					
					List<ItemStack> items = new ArrayList<ItemStack>();
						
					if ( arg2 == null || arg2.trim().equalsIgnoreCase("")) {
						if ( _item != null && _item.getDurability() > 0 ) {
							items.add(_item);
						}
					} else if ( arg2.equalsIgnoreCase("all")){
						Inventory inventory = _player.getInventory();
						ItemStack[] armor = _player.getPlayer().getInventory().getArmorContents();
						
						for ( int i = 0; i < inventory.getSize(); i++){
							_item = inventory.getItem(i);
							if ( _item != null && _item.getDurability() > 0 ) {
								items.add(_item);
							}
						}
						for ( int j = 0; j < armor.length; j++ ){
							_item = armor[j];
							if ( _item != null && _item.getDurability() > 0 ) {
								items.add(_item);
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
							_item = items.get(j);
							_item.setDurability(Short.parseShort("0"));
							
							Plugin.addLoreText(_item, Plugin.LORE_PLAYER_BOUND, _player.getName());
							Plugin.addLoreText(_item, Plugin.LORE_REPAIRED );
							
							ArrayList<String> lore = (ArrayList<String>)_item.getItemMeta().getLore();
							for ( int i = 0; lore != null && !lore.isEmpty() && i < lore.size(); i++){
								Plugin.debugPlayerMsg(_player,"lore["+i+"] : "+lore.get(i));
							}
						}
						_player.sendMessage("You have repaired your _item"+(items.size() > 1 ? "s" : "")+" for " + Economy.format(cost) + " economy.");
							
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
