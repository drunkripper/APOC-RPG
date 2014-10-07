package com.APOCRPG.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.APOCRPG.API.ItemAPI;
import com.APOCRPG.Main.Plugin;

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
	
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			ItemStack item = player.getItemInHand();
			if ( item.getType().equals(Material.ENCHANTED_BOOK) && Plugin.containsLoreText(item, Plugin.LORE_TOME)) {
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
						ItemStack clone = ItemAPI.diablofy(unidItem.clone());
						clone.setDurability(invItem.getDurability());
						Plugin.addLoreText(clone, Plugin.LORE_PLAYER_BOUND, player.getName());
						inv.remove(unidItem);
						player.setItemInHand(clone);
						return ;
					}
				} else {
					player.sendMessage(Plugin.APOCRPG_ERROR+"You have nothing to identify!");
				}
				// if we are still here, we haven't found any unidentified items
				// check worn armor
				
			}
			/*
			if ( item != null && item.hasItemMeta() ) {
				List<String> lore1 = item.getItemMeta().getLore();
				if ( lore1 != null && lore1.get(0) != null && lore1.get(0).equals("Socket")) {
					SelectedSocket.put(player, event.getPlayer().getItemInHand());
					SelectedSlot.put(player, event.getPlayer().getInventory().getHeldItemSlot());
					event.getPlayer().sendMessage("Select the item with an empty Socket and right click.");
				} else if (lore1 != null && lore1.get(0) != null && lore1.get(0).equals("(Socket)")) {
					if (SelectedSocket.containsKey(event.getPlayer())) {
						ItemMeta Meta = SelectedSocket.get(player).getItemMeta();
						ItemStack Used = new ItemStack(Material.COAL);
						ItemMeta itemMeta = Used.getItemMeta();
						List<String> lore = new ArrayList<String>();
						lore.add("Used socket");
						itemMeta.setLore(lore);
						Used.setItemMeta(itemMeta);
						event.getPlayer().getInventory().setItem(SelectedSlot.get(player), Used);
						SelectedSocket.put(player, null);
						if (Meta != null) {
							List<String> Lore = Meta.getLore();
							String Name = Lore.get(2);
							//int Level = Integer.parseInt(Lore.get(3).substring(7));
							//long Duration = Long.parseLong(Lore.get(4).split(":")[0])*20 + Long.parseLong(Lore.get(4).split(":")[1]) * 60 * 20;
							ItemMeta ItemMeta = item.getItemMeta();
							List<String> SelectedLore = ItemMeta.getLore();
							SelectedLore.set(0, Name);
							SelectedLore.add(Lore.get(3));
							SelectedLore.add(Lore.get(4));
							ItemMeta.setLore(SelectedLore);
							ItemMeta.setItemMeta(ItemMeta);
						}
					} else {
						event.getPlayer().sendMessage("No socket selected!");
					}
				}
			}
			*/
		}
	}
	
}
