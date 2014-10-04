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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.APOCRPG.Main.Plugin;

public class SocketEvents implements Listener {
	
	private HashMap<Player, ItemStack> SelectedSocket = new HashMap<Player, ItemStack>();
	private HashMap<Player, Integer> SelectedSlot = new HashMap<Player, Integer>();
	
	public SocketEvents() {
		Plugin.Plugin.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.Plugin, new Runnable(){
			public void run() {
				for (Player Player : Plugin.Plugin.getServer().getOnlinePlayers()) {
					if (SelectedSocket.containsKey(Player) && SelectedSocket.get(Player) != null) {
						SelectedSocket.put(Player, null);
						Player.sendMessage("Socket unselected!");
					}
				}
			}
		}, 1200l, 1200l);
	}
	
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent event) {
		Player Player = event.getPlayer();
		if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			if ( Player.getItemInHand() != null && Player.getItemInHand().hasItemMeta() ) {
				List<String> lore1 = Player.getItemInHand().getItemMeta().getLore();
				if ( lore1 != null && lore1.get(0) != null && lore1.get(0).equals("Socket")) {
					SelectedSocket.put(Player, event.getPlayer().getItemInHand());
					SelectedSlot.put(Player, event.getPlayer().getInventory().getHeldItemSlot());
					event.getPlayer().sendMessage("Select the item with an empty Socket and right click.");
				} else if (lore1 != null && lore1.get(0) != null && lore1.get(0).equals("(Socket)")) {
					if (SelectedSocket.containsKey(event.getPlayer())) {
						ItemMeta Meta = SelectedSocket.get(Player).getItemMeta();
						ItemStack Used = new ItemStack(Material.COAL);
						ItemMeta itemMeta = Used.getItemMeta();
						ArrayList<String> lore = new ArrayList<String>();
						lore.add("Used socket");
						itemMeta.setLore(lore);
						Used.setItemMeta(itemMeta);
						event.getPlayer().getInventory().setItem(SelectedSlot.get(Player), Used);
						SelectedSocket.put(Player, null);
						if (Meta != null) {
							List<String> Lore = Meta.getLore();
							String Name = Lore.get(2);
							//int Level = Integer.parseInt(Lore.get(3).substring(7));
							//long Duration = Long.parseLong(Lore.get(4).split(":")[0])*20 + Long.parseLong(Lore.get(4).split(":")[1]) * 60 * 20;
							ItemMeta ItemMeta = Player.getItemInHand().getItemMeta();
							List<String> SelectedLore = ItemMeta.getLore();
							SelectedLore.set(0, Name);
							SelectedLore.add(Lore.get(3));
							SelectedLore.add(Lore.get(4));
							ItemMeta.setLore(SelectedLore);
							Player.getItemInHand().setItemMeta(ItemMeta);
						}
					} else {
						event.getPlayer().sendMessage("No socket selected!");
					}
				}
			}
		}
	}
	
}
