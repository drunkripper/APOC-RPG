package com.APOCRPG.Events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import com.APOCRPG.Items.Items;
import com.APOCRPG.Main.Plugin;
import com.APOCRPG.Main.Settings;

//Being added to PollingEventListener.java for consistency.

// Is anyone tackling this issue yet? I'm more than OK to -K

@SuppressWarnings("unused")
public class EntityEvents implements Listener {
//	@EventHandler
//	public void onBlockBreak(BlockDamageEvent eve) {
//		if (EffectAPI.getEffectsFromItem(eve.getPlayer().getItemInHand()).containsKey("Fortune")) {
//			eve.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,
//					EffectAPI.getEffectsFromItem(eve.getPlayer().getItemInHand()).get("Fortune"), 20));
//		}
//
//	}
//This needs to reference a hostiles list set in a config.
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent Event) {
		EntityType Type = Event.getEntityType();
		if ((Type == EntityType.CREEPER || Type == EntityType.ZOMBIE || Type == EntityType.PIG_ZOMBIE
				|| Type == EntityType.SKELETON || Type == EntityType.SPIDER || Type == EntityType.GHAST
				|| Type == EntityType.ENDERMAN || Type == EntityType.WITHER || Type == EntityType.ENDER_DRAGON)
				&& Plugin.Random.nextInt(100) <= 25) {
			LivingEntity Entity = Event.getEntity();
			int Amount = Plugin.Random.nextInt(2) + 1;
			for (int i = 0; i < Amount; i++) {
				int Gear = Plugin.Random.nextInt(5);
				if (Gear == 0) {
					Entity.getEquipment().setItemInHand(Items.createArmor(4));
				} else if (Gear == 1) {
					Entity.getEquipment().setHelmet(Items.createArmor(0));
				} else if (Gear == 2) {
					Entity.getEquipment().setChestplate(Items.createArmor(1));
				} else if (Gear == 3) {
					Entity.getEquipment().setLeggings(Items.createArmor(2));
				} else if (Gear == 4) {
					Entity.getEquipment().setBoots(Items.createArmor(3));
				}
			}
		}
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player po = event.getPlayer();
		EffectPollingEvent devent = new EffectPollingEvent(po);
		Bukkit.getServer().getPluginManager().callEvent(devent);
		// Bukkit.getServer().broadcastMessage(devent.getMessage());
	}

	@EventHandler
	public void onItemSwitched(PlayerItemHeldEvent event) {
		Player po = event.getPlayer();
		EffectPollingEvent devent = new EffectPollingEvent(po);
		Bukkit.getServer().getPluginManager().callEvent(devent);
		// Bukkit.getServer().broadcastMessage(devent.getMessage());
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		// get player who did the action
		Player po = (Player) event.getWhoClicked();
		// get polling event
		EffectPollingEvent devent = new EffectPollingEvent(po);
		// get item
		ItemStack item = event.getCurrentItem();
		// get lore containing "Player bound:"
		ArrayList<String> boundPlayers = (ArrayList<String>) Plugin.getLoreContaining(item, Settings.Cfg.LORE_PLAYER_BOUND.toString());
		// iterate through lore to make sure that the item belongs to the player
		for (String bp : boundPlayers) {
			if (!bp.replace(Settings.Cfg.LORE_PLAYER_BOUND.toString() + " ", "").equals(po.getName())) {
				// item belongs to another player
				po.sendMessage(Settings.Cfg.APOCRPG_ERROR.toString() + "This item does not belong to you!");
				event.setCancelled(true);
				return;
			}
		}
		// if still here, item is not owned or owned by player
		Bukkit.getServer().getPluginManager().callEvent(devent);
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		Player po = (Player) event.getPlayer();
		EffectPollingEvent devent = new EffectPollingEvent(po);
		Bukkit.getServer().getPluginManager().callEvent(devent);
	}

	@EventHandler
	public void onEnitityDeath(EntityDeathEvent event) {
		// EntityType et = event.getEntityType();
		LivingEntity entity = event.getEntity();
		if (entity instanceof Player) {
			ArrayList<ItemStack> is = (ArrayList<ItemStack>) event.getDrops();
			for (ItemStack item : is) {
				((Player) entity).sendMessage("Dropping: " + item.getItemMeta().getDisplayName());
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		/* -- this code is broken.  this writes to block metadata which affects
		 * -- all objects of the type.  so, locking against a player would lock
		 * -- ALL chests against a player.  Replace this with calls to a DB.
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		boolean isEmpty = true;
		if (block != null && block.getType().equals(Material.CHEST)) {
			if (Plugin.CHEST_LOCKABLE) {
				if (!block.getMetadata("locked").isEmpty()) {
					ArrayList<MetadataValue> meta = new ArrayList<MetadataValue>(block.getMetadata("locked"));
					for (int i = 0; meta != null && i < meta.size(); i++) {
						MetadataValue metaValue = (MetadataValue) meta.get(i);
						boolean nameFound = false;
						try {
							@SuppressWarnings("unchecked")
							ArrayList<String> names = (ArrayList<String>) metaValue.value();
							for (int j = 0; names != null && j < names.size(); j++) {
								String name = (String) names.get(j);
								Plugin.debugConsole(name);
								if (player.getName().equals(name)) {
									nameFound = true;
									if (!player.hasPermission("op")) {
										player.sendMessage(Plugin.APOCRPG_ERROR + "This chest is locked to you!");
										event.setCancelled(true);
									}
								}
							}
							if (!nameFound) {
								names.add(player.getName());
								FixedMetadataValue fmv = new FixedMetadataValue(Plugin.instance, names);
								block.setMetadata("locked", fmv);
							}
						} catch (Exception e) {
							// do nothing
						}
					}
				} else {
					ArrayList<String> names = new ArrayList<String>();
					names.add(player.getName());
					FixedMetadataValue fmv = new FixedMetadataValue(Plugin.instance, names);
					block.setMetadata("locked", fmv);
				}
			}
			if (player.hasPermission("op") && player.getItemInHand() != null) {
				ItemStack hand = player.getItemInHand();
				ItemMeta meta = hand.getItemMeta();
				if (meta != null && meta.getDisplayName() != null
						&& meta.getDisplayName().equals(Plugin.DISPLAY_NAME_UNIDENTIFIED_ITEM)) {
					Chest chest = (Chest) block.getState();
					Inventory chestInv = chest.getInventory();
					ItemStack[] contents = chestInv.getContents();
					for (int i = 0; i < contents.length && isEmpty; i++) {
						ItemStack item = (ItemStack) contents[i];
						if (item != null) {
							isEmpty = false;
						}
					}
					if (isEmpty) {
						ItemAPI.fillChest(block);
						FixedMetadataValue fmv = new FixedMetadataValue(Plugin.instance, "true");
						block.setMetadata("lockable", fmv);
					}
				}
			}
		}
		/**/
	}

	/**
	 * This method is used to prevent the player from picking up items bound to
	 * another player
	 * 
	 * @param event
	 */
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		// get item
		ItemStack item = event.getItem().getItemStack();
		// check item for lore
		if (Plugin.hasLore(item)) {
			// get player
			Player player = (Player) event.getPlayer();
			// get player name
			String playerName = player.getName();
			// create lore string as it would be if the player owns the item
			String loreString = Settings.Cfg.LORE_PLAYER_BOUND.toString() + " " + playerName;
			// default lore array list
			ArrayList<String> lore = (ArrayList<String>) item.getItemMeta().getLore();
			// iterate through lore
			for (String s : lore) {
				// if lore starts with "Player bound" but does not equal
				// player's
				// lore string, this item is owned by another player.
				if (s.startsWith(Settings.Cfg.LORE_PLAYER_BOUND.toString()) && !s.equals(loreString)) {
					// cancel event as item is owned by another player
					event.setCancelled(true);
				}
			}
		}
	}
}
