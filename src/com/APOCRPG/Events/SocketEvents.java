package com.APOCRPG.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import com.APOCRPG.API.ItemAPI;
import com.APOCRPG.Main.Plugin;
import com.APOCRPG.items.IdentifyTome;

public class SocketEvents implements Listener {
	
	private HashMap<Player, ItemStack> SelectedSocket = new HashMap<Player, ItemStack>();
	private HashMap<Player, Integer> SelectedSlot = new HashMap<Player, Integer>();
	
	public SocketEvents() {
		Plugin.Plugin.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.Plugin, new Runnable(){
			public void run() {
				for (Player player : Plugin.Plugin.getServer().getOnlinePlayers()) {
					if (SelectedSocket.containsKey(player) && SelectedSocket.get(player) != null) {
						SelectedSocket.put(player, null);
						player.sendMessage("Socket unselected!");
					}
				}
			}
		}, 1200l, 1200l);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getItemInHand();
		ItemMeta itemMeta = (item != null && item.hasItemMeta()) ? item.getItemMeta() :  Plugin.Plugin.getServer().getItemFactory().getItemMeta(item.getType());
		List<String> itemLore = (itemMeta != null && itemMeta.hasLore())? item.getItemMeta().getLore() : new ArrayList<String>() ;
		
		if ( item != null && !item.getData().equals(Material.AIR) 
		&& (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) 
		{
			if ( item.getType().equals(Material.WRITTEN_BOOK) && Plugin.containsLoreText(itemLore, Plugin.LORE_TOME)) {
				BookMeta bm = (BookMeta)item.getItemMeta();
				if ( bm == null || !bm.hasAuthor() || !bm.hasTitle() && bm.getTitle().contains("Identify Tome"))
					{ return; }
				
				Inventory inv = player.getInventory();
				ItemStack invItem = null;
				ItemStack unidItem = null;
				for ( int i = 0; i < inv.getSize() && unidItem == null; i++){
					invItem = inv.getItem(i);
					if ( invItem != null && !invItem.getType().equals(Material.AIR)) {
						ItemMeta invMeta = invItem.getItemMeta();
						if ( item != null && invMeta.getDisplayName() != null
						&& invMeta.getDisplayName().equals(Plugin.LORE_UNKNOWN_ITEM) && invMeta.getLore()==null) 
						{
							unidItem = invItem;
						}
					}
				}
				if ( unidItem != null && !unidItem.getType().equals(Material.AIR)) {
					ItemMeta unidMeta = unidItem.getItemMeta();
					if ( ( unidMeta != null && unidMeta.getDisplayName() != null) 
					&& (unidMeta.getDisplayName().equals(Plugin.LORE_UNKNOWN_ITEM) && unidMeta.getLore()==null))
					{
						inv.remove(unidItem);
						ItemStack clone = ItemAPI.diablofy(unidItem.clone());
						clone.setDurability(invItem.getDurability());
						Plugin.addLoreText(clone, Plugin.LORE_PLAYER_BOUND, player.getName());
						player.setItemInHand(clone);
						event.setUseItemInHand(Result.DENY);
						event.setCancelled(true);
						player.updateInventory();
						player.closeInventory();
						return ;
					}
				} else {
					player.sendMessage(Plugin.APOCRPG_ERROR+"You have nothing to identify!");
				}
				// if we are still here, we haven't found any unidentified items
				// check worn armor
				
			}
			else {
				if ( item.getType().equals(Material.EMERALD) && Plugin.containsLoreText(itemLore, Plugin.LORE_GEM_OF)) {
					SelectedSocket.put(player, event.getPlayer().getItemInHand());
					SelectedSlot.put(player, event.getPlayer().getInventory().getHeldItemSlot());
					event.getPlayer().sendMessage("Select the item with an empty Socket and right click.");
				} else if (itemLore != null && ( Plugin.containsLoreText(itemLore, Plugin.LORE_ITEM_SOCKET) || Plugin.containsLoreText(itemLore, Plugin.LORE_GEM_OF))) {
					Plugin.debugPlayerMsg(player, "Player has selected item for socket");
					boolean socketFound = false;
					
					if (SelectedSocket.containsKey(event.getPlayer())) {
						Plugin.debugPlayerMsg(player, "Player has selected socket");
						// get gem lore information for effect, type, and level
						String gemEffect = null;
						String gemType = null;
						String gemStrLvl = null;
						int gemIntLvl = 0;
						String itemStrLvl = null;
						int itemIntLvl = 0;
						int newLevel = 0;
						
						ItemMeta gemMeta = (SelectedSocket.get(player)).getItemMeta();
						List<String> gemLore = (gemMeta != null && gemMeta.hasLore()) ? gemMeta.getLore() : new ArrayList<String>();
						for ( String s : gemLore) {
							if ( s.startsWith(Plugin.LORE_GEM_OF) ) {
								Plugin.debugPlayerMsg(player, s);
								gemEffect = s.substring(0, s.lastIndexOf(" ")).replaceAll(Plugin.LORE_GEM_OF, "");
								gemStrLvl = s.substring(s.lastIndexOf(" ")+1);
								gemIntLvl = Plugin.romanToInt(gemStrLvl);
								Plugin.debugPlayerMsg(player, "Gem: "+gemEffect+"; Level: "+gemStrLvl);
							} else if ( s.endsWith("Type")) {
								gemType = s;
							}
						}
						
						//List<String> newLore = new ArrayList<String>();

						for ( int i = 0; i < itemLore.size() && !socketFound; i++ ) {
							String s = itemLore.get(i);
							// check to see if the item already has the same gem socketed
							if ( s.startsWith(Plugin.LORE_GEM_OF + gemEffect )) {
								Plugin.debugPlayerMsg(player, "Item: "+s);
								itemStrLvl = s.substring(s.lastIndexOf(" ")+1);
								itemIntLvl = Plugin.romanToInt(itemStrLvl);
								
								if ( gemIntLvl > 0 && itemIntLvl > 0 ) {
									newLevel = gemIntLvl + itemIntLvl;
								}
								// if there is a match, make sure the gem wouldn't push the item over the max level
								if ( newLevel > 0 && newLevel <= 10 ) {
									itemLore.remove(i);
									itemLore.add(i, Plugin.LORE_GEM_OF + gemEffect + " " + Plugin.intToRoman( newLevel ));
									socketFound = true;
								}
							} else if ( s.equals(Plugin.LORE_ITEM_SOCKET)) {
								itemLore.remove(i);
								itemLore.add(i, Plugin.LORE_GEM_OF + gemEffect + " " + gemStrLvl );
								socketFound = true;
							}
						}
						if ( socketFound ) {
							itemMeta.setLore(itemLore);
							item.setItemMeta(itemMeta);
							Plugin.debugPlayerMsg(player, "Socket found. Removing gem. ");
							player.getInventory().remove(SelectedSocket.get(player));
							SelectedSocket.remove(player);
							player.updateInventory();
							player.closeInventory();
						} else {
							player.sendMessage(Plugin.APOCRPG_ERROR_SOCKET);
						}
						
						/*
						ItemStack coal = new ItemStack(Material.COAL);
						ItemMeta coalMeta = coal.getItemMeta();
						List<String> coalLore = new ArrayList<String>();
						coalLore.add("Used socket");
						coalMeta.setLore(coalLore);
						coal.setItemMeta(coalMeta);
						event.getPlayer().getInventory().setItem(SelectedSlot.get(player), coal);
						*/
					} else {
						event.getPlayer().sendMessage("No socket selected!");
					}
				} else if ( itemLore == null ) {
					Plugin.debugPlayerMsg(player, "Selected Item has no lore!");
				}
			}
		} else {
			Plugin.debugPlayerMsg(player, "event.getEventName() = "+event.getEventName());
			Plugin.debugPlayerMsg(player, "event.getAction().name = "+event.getAction().name());
		}
	}
	
}
