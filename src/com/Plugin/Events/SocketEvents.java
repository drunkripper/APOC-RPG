package com.Plugin.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.Plugin.Main.Plugin;

public class SocketEvents implements Listener {
	
	private HashMap<Player, ItemStack> SelectedSocket = new HashMap<Player, ItemStack>();
	
	public SocketEvents() {
		Plugin.Plugin.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.Plugin, new Runnable(){
			public void run() {
				for (Player Player : Plugin.Plugin.getServer().getOnlinePlayers()) {
					if (SelectedSocket.containsKey(Player)) {
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
			if (Player.getItemInHand().getItemMeta().getLore().get(0).equals("Socket")) {
				SelectedSocket.put(Player, event.getPlayer().getItemInHand());
				event.getPlayer().sendMessage("Select the item with an empty Socket and right click.");
			} else if (Player.getItemInHand().getItemMeta().getLore().get(0).equals("(Socket)")) {
				if (SelectedSocket.containsKey(event.getPlayer())) {
					SelectedSocket.put(Player, null);
					List<String> Lore = SelectedSocket.get(Player).getItemMeta().getLore();
					String Name = Lore.get(2);
					//int Level = Integer.parseInt(Lore.get(3).substring(7));
					//long Duration = Long.parseLong(Lore.get(4).split(":")[0])*20 + Long.parseLong(Lore.get(4).split(":")[1]) * 60 * 20;
					ItemMeta ItemMeta = Player.getItemInHand().getItemMeta();
					List<String> SelectedLore = ItemMeta.getLore();
					SelectedLore.set(0, Name);
					Player.getItemInHand().setItemMeta(ItemMeta);
				} else {
					event.getPlayer().sendMessage("No socket selected!");
				}
			}
		}
	}
	
}