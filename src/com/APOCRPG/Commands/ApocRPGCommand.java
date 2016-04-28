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

import com.APOCRPG.API.EffectAPI;
import com.APOCRPG.API.GemAPI;
import com.APOCRPG.API.ItemAPI;
import com.APOCRPG.Main.Economy;
import com.APOCRPG.Main.Plugin;
import com.APOCRPG.SkillPoints.DBApi;

/**
 * 
 * @author ???, whiteknave
 *
 */

//TODO: Break up the class to seperate each command for late convinence

@SuppressWarnings("unused")
public class ApocRPGCommand implements CommandExecutor {

	@Override
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender Sender, Command Command, String label, String[] args) {
		
		ChatColor CommandColor = ChatColor.GOLD;
		ChatColor DescColor = ChatColor.BLUE;
		String arg1 = new String();
		String arg2 = new String();
		String arg3 = new String();
		String arg4 = new String();

		Player _player = (Player) Sender;
		Inventory _inventory = _player.getInventory();
		ItemStack _handItem = _player.getItemInHand();
		boolean _emptyHand = (_handItem == null || _handItem.getType().equals(Material.AIR));
		ItemMeta _handMeta = (!_emptyHand && _handItem.hasItemMeta()) ? _handItem.getItemMeta()
				: Plugin.instance.getServer().getItemFactory().getItemMeta(_handItem.getType());
		ArrayList<String> _handLore = (_handMeta != null && _handMeta.hasLore())
				? (ArrayList<String>) _handMeta.getLore() : new ArrayList<String>();
		
		if (Command.getLabel().equalsIgnoreCase("apocrpg")) {
			// if no arguments, give the user the list of commands
			if (args.length == 0) {
				_player.sendMessage(
						CommandColor + "/apocrpg buy " + DescColor + "             | Buy an enchanted item.");
				_player.sendMessage(CommandColor + "/apocrpg buy enchant " + DescColor + "  | Buy an enchantment.");
				_player.sendMessage(CommandColor + "/apocrpg buy gem" + DescColor + "         | Buy a socket gem.");
				_player.sendMessage(
						CommandColor + "/apocrpg buy item " + DescColor + "        | Buy a random RPG item.");
				_player.sendMessage(
						CommandColor + "/apocrpg buy name " + DescColor + "      | Buy a name for your item.");
				_player.sendMessage(CommandColor + "/apocrpg buy socket " + DescColor
						+ "    | Buy another socket for your item. Max allowed: " + Plugin.GEAR_SOCKETS_MAX_BUY);
				_player.sendMessage(CommandColor + "/apocrpg buy tome " + DescColor + "       | Buy an identity tome.");
				_player.sendMessage(CommandColor + "/apocrpg disenchant" + DescColor + "     | Disenchant an item.");
				_player.sendMessage(CommandColor + "/apocrpg disenchant all" + DescColor
						+ " | Disenchant all items in inventory (except for worn armor and action bar).");
				_player.sendMessage(
						CommandColor + "/apocrpg sell" + DescColor + "              | Sell a APOC-RPG item.");
				_player.sendMessage(CommandColor + "/apocrpg sell all" + DescColor
						+ "          | Sell all APOC-RPG items in inventory.");
				// _player.sendMessage(CommandColor + "/apocrpg salvage" +
				// DescColor + " | Salvage a APOC-RPG item.");
				// _player.sendMessage(CommandColor + "/apocrpg salvage all" +
				// DescColor + " | Salvage all APOC-RPG items in inventory
				// (except for worn armor and action bar).");
				_player.sendMessage(CommandColor + "/apocrpg remove gem" + DescColor
						+ "    | Remove the first gem from its socket.");
				_player.sendMessage(CommandColor + "/apocrpg remove socket" + DescColor
						+ "| Remove the first empty socket from an item.");
				_player.sendMessage(CommandColor + "/apocrpg repair" + DescColor + "          | Repair an item.");
				_player.sendMessage(
						CommandColor + "/apocrpg repair all" + DescColor + "      | Repair all items in inventory.");
				_player.sendMessage(
						CommandColor + "/apocrpg version" + DescColor + "         | Show version information.");
			} else {
				// determine if the player has the proper permissions.
				if (!Plugin.hasPermission(_player, args)) {
					_player.sendMessage(Plugin.APOCRPG_ERROR + "You do not have permission to do that!");
					return true;
				}

				// parse the first four (4) arguments. The first two arguments
				// are command arguements, the third and fourth arguments
				// are optional data
				for (int i = 0; i < args.length; i++) {
					if (i == 0)
						arg1 = ((String) args[i]).toLowerCase();
					else if (i == 1)
						arg2 = ((String) args[i]).toLowerCase();
					else if (i == 2)
						arg3 = (String) args[i];
					else if (i == 3)
						arg4 = (String) args[i];
				}

				if (arg1.equalsIgnoreCase("version")) {
					// display Plugin version information
					_player.sendMessage(CommandColor + "APOC RPG Plugin | Version " + Plugin.VERSION);
					_player.sendMessage(CommandColor + "Programmed by the Apocalyptic Gaming Network");
					_player.sendMessage(CommandColor + "http://apocgaming.org");
					_player.sendMessage(CommandColor + "https://github.com/Zilacon/APOC-RPG");
				} else if (arg1.equalsIgnoreCase("levelup")) {
					if(arg2.equalsIgnoreCase("evasion")) {
						DBApi.addAbility(_player, 1.0, "evasion");
					} else if(arg2.equalsIgnoreCase("armor")) {
						DBApi.addAbility(_player, 1.0, "armor");
					}else if(arg2.equalsIgnoreCase("luck")) {
						DBApi.addAbility(_player, 1.0, "luck");
					}else if(arg2.equalsIgnoreCase("recovery")) {
						DBApi.addAbility(_player, 1.0, "recovery");
					}
				}
				// check for command permissions
				// else if ( !_player.hasPermission("apocrpg."+arg1+((arg2 !=
				// null && !arg2.trim().equals(""))?"."+arg2:""))){
				// _player.sendMessage(Plugin.APOCRPG_ERROR_NO_PERMISSION);
				// return true;
				// }
				// do buy routine
				else if (arg1.equalsIgnoreCase("buy")) {
					// if additional
					if (args.length > 1) {
						if (arg2.equalsIgnoreCase("enchant")) {
							// if nothing in hand, send error message and return
							if (_emptyHand) {
								_player.sendMessage(Plugin.APOCRPG_ERROR_EMPTY_HAND);
								return true;
							}

							// get list of enchantments available for this
							// ItemStack
							ArrayList<Enchantment> enchList = (ArrayList<Enchantment>) Plugin
									.getEnchantmentsFor(_handItem);
							if (enchList == null || enchList.isEmpty()) {
								_player.sendMessage(
										Plugin.APOCRPG_ERROR + "No enchantments are available for that item!");
							} else if (arg3 == null || arg3.equals("") || arg3.equalsIgnoreCase("list")) {
								_player.sendMessage(ChatColor.BLUE + "Enchantments available for this item:");
								for (int i = 0; i < enchList.size(); i++) {
									Enchantment e = (Enchantment) enchList.get(i);
									// output the number (left padded) and
									// enchantment name to the user.
									_player.sendMessage(
											String.format(ChatColor.BLUE + "%1$" + 4 + "s", String.valueOf(i + 1))
													+ " - " + e.getName());
								}
							} else {
								int enchNbr = -1;
								ArrayList<Enchantment> buyEnchants = new ArrayList<Enchantment>();

								// if all enchantments requested, use the list
								// of available enchantments
								if (arg3.equalsIgnoreCase("all")) {
									buyEnchants = enchList;
								} else {
									Enchantment ench = null;
									// try to find the specific enchantment
									// requested
									// see if arg3 is the number of the
									// Enchantment or the name.
									try {
										enchNbr = Integer.parseInt(arg3);
										// if we didn't blow up, it's a number
										ench = enchList.get(enchNbr - 1);
									} catch (Exception e) {
										// just need to catch the exception.
										Plugin.debug("DEBUG: " + e.getLocalizedMessage());
									}

									// if ench is null, try to match arg3 to
									// names of enchantments
									if (ench == null) {
										for (Enchantment e : enchList) {
											if (e.getName().equalsIgnoreCase(arg3)) {
												ench = e;
												break;
											}
										}
									}

									// if ench is still null.. unknown
									// enchantment
									// or enchantment is not available for this
									// ItemStack
									if (ench == null) {
										_player.sendMessage(Plugin.APOCRPG_ERROR
												+ "That enchantment is not recognized for this item!");
										return true;
									} else {
										buyEnchants.add(ench);
									}
								}
								int powerLvl = -1;
								// parse the requested level for the enchantment
								try {
									powerLvl = Integer.valueOf(arg4);
								} catch (NumberFormatException npe) {
									powerLvl = Plugin.romanToInt(arg4);
								} catch (Exception e) {
									// some other exception happened
								}

								if (powerLvl < 1 || (powerLvl > 10 && !_player.hasPermission("op"))) {
									_player.sendMessage(Plugin.APOCRPG_ERROR + "Invalid enchantment level!");
									return true;
								}

								// calculate cost for enchantment(s)
								double cost = 0;
								ArrayList<Enchantment> applyEnchants = new ArrayList<Enchantment>();

								// iterate through requested enchantment(s)
								for (Enchantment e : buyEnchants) {
									// get current power level for this
									// enchantment
									int currLevel = _handItem.getEnchantmentLevel(e);
									if (currLevel < powerLvl) {
										do {
											switch (currLevel) {
											case 0:
												cost += Plugin.COST_ENCHANT_LVL_1;
												break;
											case 1:
												cost += Plugin.COST_ENCHANT_LVL_2;
												break;
											case 2:
												cost += Plugin.COST_ENCHANT_LVL_3;
												break;
											case 3:
												cost += Plugin.COST_ENCHANT_LVL_4;
												break;
											case 4:
												cost += Plugin.COST_ENCHANT_LVL_5;
												break;
											case 5:
												cost += Plugin.COST_ENCHANT_LVL_6;
												break;
											case 6:
												cost += Plugin.COST_ENCHANT_LVL_7;
												break;
											case 7:
												cost += Plugin.COST_ENCHANT_LVL_8;
												break;
											case 8:
												cost += Plugin.COST_ENCHANT_LVL_9;
												break;
											default:
												cost += Plugin.COST_ENCHANT_LVL_10;
												break;
											}
											currLevel++;
											applyEnchants.add(e);
										} while (currLevel < powerLvl);
									} else if (currLevel > powerLvl && buyEnchants.size() == 1) {
										_player.sendMessage(
												Plugin.APOCRPG_ERROR + "This item exceeds that enchantment level.");
										return true;
									} else if (currLevel == powerLvl && buyEnchants.size() == 1) {
										_player.sendMessage(
												Plugin.APOCRPG_ERROR + "This item already has that enchantment level.");
										return true;
									}
								}

								if (applyEnchants.isEmpty()) {
									_player.sendMessage(Plugin.APOCRPG_ERROR
											+ "There are no more enchantments available for this item.");
								} else if (!Economy.hasMoney(_player, cost)) {
									_player.sendMessage(
											Plugin.APOCRPG_ERROR_NO_MONEY + "You need $" + Economy.format(cost) + "!");
								} else {
									Economy.removeMoney(_player, cost);
									for (Enchantment e : buyEnchants) {
										if (!_handItem.containsEnchantment(e)
												|| _handItem.getEnchantmentLevel(e) < powerLvl) {
											_handItem.addUnsafeEnchantment(e, powerLvl);
										}
									}
									// add player bound
									Plugin.addLoreText(_handItem, Plugin.LORE_PLAYER_BOUND, _player.getName());
									_player.sendMessage(buyEnchants.size() + " enchantment"
											+ (buyEnchants.size() > 1 ? "s" : "") + " added for: $" + cost);
								}

							}
						} else if (arg2.equalsIgnoreCase("gem")) {
							if ( _player.hasPermission("op") && !arg3.isEmpty() ) {
								String type = EffectAPI.getEffectTypeFromName(arg3);
								if ( type == null ) {
									_player.sendMessage(Plugin.APOCRPG_ERROR + "Invalid effect!");
								} else {
									if ( arg4 == null || "".equals(arg4)) {
										arg4 = "I";
									} else if ( !arg4.equalsIgnoreCase("I") && !arg4.equalsIgnoreCase("II") && !arg4.equalsIgnoreCase("III")) {
										// see if an int was passed
										try {
											int i = Integer.parseInt(arg4);
											if ( i < 1 || i > 3 ) {
												arg4 = "I";
											} else {
												arg4 = Plugin.intToRoman(i);
											}
										} catch ( Exception e ) {
											arg4 = "I";
										}
									}
									_inventory.addItem(GemAPI.createGem(arg3, type, arg4));
								}
							} else { 
								if (Economy.hasMoney(_player, Plugin.COST_BUY_GEM)) {
									Economy.removeMoney(_player, Plugin.COST_BUY_GEM);
									_inventory.addItem(GemAPI.createGem());
								} else {
									_player.sendMessage(Plugin.APOCRPG_ERROR_NO_MONEY + "You need $"
											+ Economy.format(Plugin.COST_BUY_GEM) + "!");
								}
							}
						} else if (arg2.equalsIgnoreCase("item")) {
							if (Economy.hasMoney(_player, Plugin.COST_BUY_GEAR)) {
								Economy.removeMoney(_player, Plugin.COST_BUY_GEAR);
								ItemStack item = ItemAPI.createItem();
								Plugin.addLoreText(item, Plugin.LORE_PLAYER_BOUND, _player.getName());
								_inventory.addItem(item);
							} else {
								_player.sendMessage(Plugin.APOCRPG_ERROR_NO_MONEY + "You need $"
										+ Economy.format(Plugin.COST_BUY_GEAR) + "!");
							}
						} else if (arg2.equalsIgnoreCase("name")) {
							String name = "";
							for (int i = 2; i < args.length; i++) {
								if ( name.length() > 0 ) {
									name += " ";
								}
								name += args[i];
							}
							if ( name.equals("") || name.equals(Plugin.LORE_UNKNOWN_ITEM)) {
								_player.sendMessage(Plugin.APOCRPG_ERROR + "Invalid item name!");
							} else if (_emptyHand) {
								_player.sendMessage(Plugin.APOCRPG_ERROR_EMPTY_HAND);
							} else if (!Economy.hasMoney(_player, Plugin.COST_BUY_NAME)) {
								_player.sendMessage(Plugin.APOCRPG_ERROR_NO_MONEY + "You need $"
										+ Economy.format(Plugin.COST_BUY_NAME) + "!");
							} else {
								if (_handMeta.getDisplayName() != null
										&& (_handMeta.getDisplayName().equals(Plugin.DISPLAY_NAME_GEM) || _handMeta
												.getDisplayName().equals(Plugin.DISPLAY_NAME_TOME)
										|| _handMeta.getDisplayName().equals(Plugin.DISPLAY_NAME_UNIDENTIFIED_ITEM))) {
									_player.sendMessage(Plugin.APOCRPG_ERROR + "This item cannot be renamed!");
									return true;
								}
								Economy.removeMoney(_player, Plugin.COST_BUY_NAME);
								_handMeta.setDisplayName(name);
								Plugin.addLoreText(_handMeta, Plugin.LORE_PLAYER_BOUND, _player.getName());
								_handItem.setItemMeta(_handMeta);
							}
						} else if (arg2.equalsIgnoreCase("socket")) {
							int currentSockets = 0;
							double cost = 0;
							if (_emptyHand) {
								_player.sendMessage(Plugin.APOCRPG_ERROR_EMPTY_HAND);
							} else {
								// check to see if the player currently has any
								// gems or empty sockets on item
								for (String l : _handLore) {
									if (l.equals(Plugin.LORE_ITEM_SOCKET) || l.startsWith(Plugin.LORE_GEM_OF)) {
										currentSockets++;
									}
								}

								if (currentSockets >= Plugin.GEAR_SOCKETS_MAX_BUY) {
									_player.sendMessage(
											Plugin.APOCRPG_ERROR + "No more sockets available for this item!");
								} else {
									// determine cost. if currentSockets = 1,
									// the cost will be for the first socket
									switch (currentSockets) {
									case 0:
										cost = Plugin.COST_SOCKET_1;
										break;
									case 1:
										cost = Plugin.COST_SOCKET_2;
										break;
									case 2:
										cost = Plugin.COST_SOCKET_3;
										break;
									}

									if (cost > 0) {
										if (!Economy.hasMoney(_player, cost)) {
											_player.sendMessage(Plugin.APOCRPG_ERROR_NO_MONEY + "You need $"
													+ Economy.format(cost) + "!");
										} else {
											Economy.removeMoney(_player, cost);
											_handItem = addItemSocket(_handItem);
											Plugin.addLoreText(_handItem, Plugin.LORE_PLAYER_BOUND, _player.getName());
											_player.sendMessage("You have added a socket for $" + Economy.format(cost)
													+ " economy.");
										}
									}
								}
								Economy.removeMoney(_player, Plugin.COST_SOCKET_1);
							}
						} else if (arg2.equalsIgnoreCase("tome")) {
							if (Economy.hasMoney(_player, Plugin.COST_BUY_TOME)) {
								Economy.removeMoney(_player, Plugin.COST_BUY_TOME);
								_inventory.addItem(ItemAPI.createTome());
							} else {
								_player.sendMessage(Plugin.APOCRPG_ERROR_NO_MONEY + "You need $"
										+ Economy.format(Plugin.COST_BUY_TOME) + "!");
							}
						} else if (arg2.equalsIgnoreCase("unknown")) {
							if (Economy.hasMoney(_player, Plugin.COST_BUY_GEAR)) {
								Economy.removeMoney(_player, Plugin.COST_BUY_GEAR);
								ItemStack item = ItemAPI.createUnidentified();
								_inventory.addItem(item);
							} else {
								_player.sendMessage(Plugin.APOCRPG_ERROR_NO_MONEY + "You need $"
										+ Economy.format(Plugin.COST_BUY_GEAR) + "!");
							}
						}
					} else {
						_player.sendMessage(
								CommandColor + "/apocrpg buy " + DescColor + "             | Buy an enchanted item.");
						_player.sendMessage(
								CommandColor + "/apocrpg buy enchant " + DescColor + "  | Buy an enchantment.");
						_player.sendMessage(
								CommandColor + "/apocrpg buy gem" + DescColor + "         | Buy a socket gem.");
						_player.sendMessage(
								CommandColor + "/apocrpg buy item " + DescColor + "        | Buy a random RPG item.");
						_player.sendMessage(
								CommandColor + "/apocrpg buy name " + DescColor + "      | Buy a name for your item.");
						_player.sendMessage(CommandColor + "/apocrpg buy socket " + DescColor
								+ "    | Buy another socket for your item. Max allowed: "
								+ Plugin.GEAR_SOCKETS_MAX_BUY);
						_player.sendMessage(
								CommandColor + "/apocrpg buy tome " + DescColor + "       | Buy an identity tome.");
					}
				} else if (arg1.equalsIgnoreCase("disenchant")) {
					double cost = 0;
					double totalXP = 0;

					List<ItemStack> items = new ArrayList<ItemStack>();
					ItemStack tmpItem = null;
					int levels = 0;

					// if arg2 check item in hand
					if (arg2 == null || arg2.trim().equalsIgnoreCase("")) {
						if (_handItem != null && !_handItem.getEnchantments().isEmpty()) {
							items.add(_handItem);
						}
					} else if (arg2.equalsIgnoreCase("all")) {
						// ignore the first 9 slots [0-8] as we don't want to
						// disenchant items in the action bar
						for (int i = 9; i < _inventory.getSize(); i++) {
							tmpItem = _inventory.getItem(i);
							if (tmpItem != null && !tmpItem.getEnchantments().isEmpty()) {
								items.add(tmpItem);
							}
						}
					} else {
						_player.sendMessage(Plugin.APOCRPG_ERROR + "Unknown command : '" + arg1 + " " + arg2 + "'");
						return true;
					}

					// determine the total levels to be removed from all objects
					for (ItemStack stack : items) {
						Map<Enchantment, Integer> map = stack.getEnchantments();
						Iterator<Enchantment> it = (Iterator<Enchantment>) map.keySet().iterator();
						while (it.hasNext()) {
							Enchantment ench = (Enchantment) it.next();
							levels += ((Integer) map.get(ench)).intValue();
						}
					}

					cost = levels * Plugin.COST_DISENCHANT;

					if (cost == 0) {
						_player.sendMessage("You have nothing to disenchant.");
					} else if (Economy.hasMoney(_player, cost)) {
						Economy.removeMoney(_player, cost);

						for (ItemStack stack : items) {
							Map<Enchantment, Integer> map = stack.getEnchantments();
							Iterator<Enchantment> it = (Iterator<Enchantment>) map.keySet().iterator();
							while (it.hasNext()) {
								Enchantment ench = (Enchantment) it.next();
								stack.removeEnchantment(ench);
							}
						}

						totalXP += (levels * Plugin.EXP_DISENCHANT);
						if (totalXP > 0) {
							_player.giveExp((int) totalXP);
						}
						_player.sendMessage("You have disenchanted " + items.size() + " _handItem"
								+ (items.size() > 1 ? "s" : "") + " for " + Economy.format(cost)
								+ " economy and received " + (int) totalXP + " XP.");

					} else {
						_player.sendMessage(Plugin.APOCRPG_ERROR_NO_MONEY + "You need " + Economy.format(cost) + "!");
					}
				} else if (arg1.equalsIgnoreCase("reload")) {
					Plugin.loadConfig();
				} else if (arg1.equalsIgnoreCase("remove")) {
					if (arg2 == null || (!arg2.equalsIgnoreCase("gem") && !arg2.equalsIgnoreCase("socket"))) {
						_player.sendMessage(Plugin.APOCRPG_ERROR + "Invalid command!");
					} else if (arg2.equalsIgnoreCase("gem")) {
						// see if item has "GEM OF ..." lore
						if (_emptyHand) {
							_player.sendMessage(Plugin.APOCRPG_ERROR_EMPTY_HAND);
						} else if (!Plugin.containsLoreText(_handItem, Plugin.LORE_GEM_OF)) {
							_player.sendMessage(Plugin.APOCRPG_ERROR + "This item has no gems to remove!");
						} else if (!Economy.hasMoney(_player, Plugin.COST_REMOVE_GEM)) {
							_player.sendMessage(Plugin.APOCRPG_ERROR_NO_MONEY + "You need "
									+ Economy.format(Plugin.COST_REMOVE_GEM) + "!");
						} else {
							String tmp = null;
							boolean gemFound = false;
							// iterate through lore to find first gem
							for (int i = 0; i < _handLore.size() && !gemFound; i++) {
								tmp = _handLore.get(i);
								if (tmp.startsWith(Plugin.LORE_GEM_OF)) {
									// recreate gem from lore
									String gemLevel = tmp.substring(tmp.lastIndexOf(' ')).trim();
									String gemName = tmp.replace(Plugin.LORE_GEM_OF, "").replace(gemLevel, "").trim();
									String gemType = EffectAPI.getEffectTypeFromName(gemName);
									ItemStack gem = GemAPI.createGem(gemName, gemType, gemLevel);
									// give gem to player
									_inventory.addItem(gem);
									gemFound = true;
								}
								_handLore.remove(i);
								_handMeta.setLore(_handLore);
								_handItem.setItemMeta(_handMeta);
							}
							// now add empty socket back to item
							_handItem = addItemSocket(_handItem);
							// add player bound
							Plugin.addLoreText(_handItem, Plugin.LORE_PLAYER_BOUND, _player.getName());
							// remove money
							Economy.removeMoney(_player, Plugin.COST_REMOVE_GEM);
							_player.updateInventory();
							_player.sendMessage(
									"You have removed a gem for $" + Economy.format(Plugin.COST_REMOVE_GEM) + ".");
						}
					} else if (arg2.equalsIgnoreCase("socket")) {
						if (_emptyHand || !Plugin.containsLoreText(_handItem, Plugin.LORE_ITEM_SOCKET)) {
							_player.sendMessage(Plugin.APOCRPG_ERROR + "There are no empty sockets to remove!");
						} else if (!Economy.hasMoney(_player, Plugin.COST_REMOVE_SOCKET)) {
							_player.sendMessage(Plugin.APOCRPG_ERROR_NO_MONEY + "You need "
									+ Economy.format(Plugin.COST_REMOVE_SOCKET) + "!");
						} else {
							String tmp = null;
							// iterate through lore to find first empty socket
							for (int i = 0; i < _handLore.size(); i++) {
								tmp = _handLore.get(i);
								if (tmp.equals(Plugin.LORE_ITEM_SOCKET)) {
									Economy.removeMoney(_player, Plugin.COST_REMOVE_SOCKET);
									_handLore.remove(i);
									_handMeta.setLore(_handLore);
									_handItem.setItemMeta(_handMeta);
									break;
								}
							}
							// add player bound ore
							Plugin.addLoreText(_handItem, Plugin.LORE_PLAYER_BOUND, _player.getName());
							_player.sendMessage("You have removed a socket for $"
									+ Economy.format(Plugin.COST_REMOVE_SOCKET) + ".");
						}
					}
				} else if (arg1.equalsIgnoreCase("repair")) {
					double cost = 0;

					List<ItemStack> items = new ArrayList<ItemStack>();
					ItemStack tmpItem = null;

					if (arg2 == null || arg2.trim().equalsIgnoreCase("")) {
						if (!_emptyHand && _handItem.getDurability() > 0) {
							items.add(_handItem);
						}
					} else if (arg2.equalsIgnoreCase("all")) {
						ItemStack[] armor = _player.getPlayer().getInventory().getArmorContents();

						for (int i = 0; i < _inventory.getSize(); i++) {
							tmpItem = _inventory.getItem(i);
							if (tmpItem != null && tmpItem.getDurability() > 0) {
								items.add(tmpItem);
							}
						}
						for (int j = 0; j < armor.length; j++) {
							tmpItem = armor[j];
							if (tmpItem != null && tmpItem.getDurability() > 0) {
								items.add(tmpItem);
							}
						}
					} else {
						_player.sendMessage(Plugin.APOCRPG_ERROR + "Unknown command : '" + arg1 + " " + arg2 + "'");
					}
					cost = items.size() * Plugin.COST_REPAIR;

					if (cost == 0) {
						_player.sendMessage("You have nothing to repair.");
					} else if (Economy.hasMoney(_player, cost)) {
						Economy.removeMoney(_player, cost);
						for (int j = 0; j < items.size(); j++) {
							tmpItem = items.get(j);
							tmpItem.setDurability(Short.parseShort("0"));

							Plugin.addLoreText(tmpItem, Plugin.LORE_PLAYER_BOUND, _player.getName());
							Plugin.addLoreText(tmpItem, Plugin.LORE_REPAIRED);
						}
						_player.sendMessage("You have repaired your item" + (items.size() > 1 ? "s" : "") + " for "
								+ Economy.format(cost) + " economy.");

					} else {
						_player.sendMessage(Plugin.APOCRPG_ERROR_NO_MONEY + "You need " + Economy.format(cost) + "!");
					}
				} else if (arg1.equalsIgnoreCase("sell")) {
					ArrayList<ItemStack> sold = new ArrayList<ItemStack>();
					ItemStack tmpItem = null;
					// if not 'sell all' make sure hand isn't empty
					if ((arg2 == null || arg2.equals("")) && _emptyHand) {
						_player.sendMessage(Plugin.APOCRPG_ERROR_EMPTY_HAND);
						return true;
					} else if ((arg2 == null || arg2.equals("")) && !_emptyHand) {
						if (Plugin.containsLoreText(_handItem, Plugin.LORE_REPAIRED) && Plugin.GEAR_NO_SALE_ON_REPAIR) {
							_player.sendMessage(Plugin.APOCRPG_ERROR + "You cannot sell repaired items!");
							return true;
						} else if (_handItem.getType().getMaxDurability() < 1) {
							_player.sendMessage(Plugin.APOCRPG_ERROR + "You cannot sell that item! No durability");
							Plugin.debugConsole("Item: " + _handItem.getTypeId() + ", type: "
									+ _handItem.getType().toString() + "");
							return true;
						} else {
							sold.add(_handItem);
						}
					} else if (arg2.equalsIgnoreCase("all")) {
						// do not iterate through task bar. only kept inventory
						for (int i = 9; i < _inventory.getSize(); i++) {
							tmpItem = _inventory.getItem(i);
							// only continue if item is not null, has max
							// durability > 0 and max stack size = 1
							// and exclude repaired items if selling repaired
							// items is disallowed
							if (tmpItem != null && tmpItem.getType().getMaxDurability() > 0
									&& tmpItem.getMaxStackSize() == 1
									&& !(Plugin.containsLoreText(tmpItem, Plugin.LORE_REPAIRED)
											&& Plugin.GEAR_NO_SALE_ON_REPAIR)) {
								sold.add(tmpItem);
							}
						}

					} else {
						_player.sendMessage(Plugin.APOCRPG_ERROR + "Unknown command!");
						return true;
					}

					if (!sold.isEmpty()) {
						double payment = 0;
						double rate = 0;
						// iterate through sold items
						for (ItemStack item : sold) {
							// get current durability
							double durability = (double) item.getType().getMaxDurability()
									- (double) item.getDurability();
							// get material type to determine pay rate
							String type = item.getType().toString();
							if (type.startsWith("WOOD")) {
								rate = Plugin.GEAR_WOOD_RATE;
							} else if (type.startsWith("LEATHER")) {
								rate = Plugin.GEAR_LEATHER_RATE;
							} else if (type.startsWith("STONE")) {
								rate = Plugin.GEAR_STONE_RATE;
							} else if (type.startsWith("IRON")) {
								rate = Plugin.GEAR_IRON_RATE;
							} else if (type.startsWith("GOLD")) {
								rate = Plugin.GEAR_GOLD_RATE;
							} else if (type.startsWith("CHAIN")) {
								rate = Plugin.GEAR_CHAIN_RATE;
							} else if (type.startsWith("DIAMOND")) {
								rate = Plugin.GEAR_DIAMOND_RATE;
							} else {
								rate = Plugin.GEAR_FORGE_RATE;
							}
							payment += (rate * durability);

							// remove item
							_inventory.remove(item);
						}
						Economy.addMoney(_player, payment);
						_player.sendMessage("You have just received $" + Economy.format(payment) + " for your item"
								+ (sold.size() > 1 ? "s" : "") + "!");
						_player.updateInventory();
					} else {
						_player.sendMessage(Plugin.APOCRPG_ERROR + "You have nothing worth selling!");
					}
				} else if (arg1.equalsIgnoreCase("spawn")) {
					if (arg2.equalsIgnoreCase("chest")) {
						ItemStack chest = new ItemStack(Material.CHEST);
						_inventory.addItem(chest);
					} else if (arg2.equalsIgnoreCase("item")) {
						int tier = Plugin.Random.nextInt(6);
						if (arg3 != null && !arg3.isEmpty() && (arg3.equals("0") || arg3.equals("1") || arg3.equals("2")
								|| arg3.equals("3") || arg3.equals("4") || arg3.equals("5"))) {
							tier = Integer.parseInt(arg3);
						}
						ItemStack item = ItemAPI.createItem(tier);
						Plugin.addLoreText(item, Plugin.LORE_PLAYER_BOUND, _player.getName());
						_inventory.addItem(item);
					} else {
						System.out.println(Plugin.APOCRPG_ERROR + "Unknown command sent.");
						return false;
					}
				}
			}
			return true;
		} else {
			System.out.println(Plugin.APOCRPG_ERROR + "Unknown command sent.");
			return false;
		}
		
	}
	/*
	 * private ArrayList<String> addItemSocket ( ArrayList<String> lore ) {
	 * ArrayList<String> retval = new ArrayList<String>(); boolean socketAdded =
	 * false;
	 * 
	 * for ( int i = 0; lore != null && i < lore.size(); i++){ String text =
	 * lore.get(i); // if we haven't added socket yet, and if text starts with
	 * '(Socket)' // or 'Player bound:' add the new socket if ( !socketAdded &&
	 * ( text.equals(Plugin.LORE_ITEM_SOCKET) ||
	 * text.startsWith(Plugin.LORE_PLAYER_BOUND))) { retval.add(retval.size(),
	 * Plugin.LORE_ITEM_SOCKET); socketAdded = true; } // now add existing lore
	 * retval.add(retval.size(), text); } // if socket hasn't been added yet,
	 * it's not player bound and doesn't // have any empty sockets, so add
	 * socket at end of lore list if ( !socketAdded ){ retval.add(retval.size(),
	 * Plugin.LORE_ITEM_SOCKET); } return retval; }
	 */

	private ItemStack addItemSocket(ItemStack item) {
		// make sure item has lore
		if (item != null && !item.getType().equals(Material.AIR)) {
			ItemMeta meta = (item != null && item.hasItemMeta()) ? item.getItemMeta()
					: Plugin.instance.getServer().getItemFactory().getItemMeta(item.getType());
			// get item meta and lore
			ArrayList<String> lore = (meta.hasLore() ? (ArrayList<String>) meta.getLore() : new ArrayList<String>());
			ArrayList<String> newLore = new ArrayList<String>();
			boolean socketAdded = false;
			// iterate through lore
			for (int i = 0; lore != null && i < lore.size(); i++) {
				String text = lore.get(i);
				// if we haven't added socket yet, and if text starts with
				// '(Socket)'
				// or 'Player bound:' add the new socket
				if (!socketAdded
						&& (text.equals(Plugin.LORE_ITEM_SOCKET) || text.startsWith(Plugin.LORE_PLAYER_BOUND))) {
					newLore.add(newLore.size(), Plugin.LORE_ITEM_SOCKET);
					socketAdded = true;
				}
				// now add existing lore
				newLore.add(newLore.size(), text);
			}
			// if socket hasn't been added yet, it's not player bound and
			// doesn't
			// have any empty sockets, so add socket at end of lore list
			if (!socketAdded) {
				newLore.add(newLore.size(), Plugin.LORE_ITEM_SOCKET);
			}

			meta.setLore(newLore);
			item.setItemMeta(meta);

		}
		return item;
	}

}
