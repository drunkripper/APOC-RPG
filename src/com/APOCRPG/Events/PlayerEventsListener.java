package com.APOCRPG.Events;

import com.APOCRPG.API.Database;
import com.APOCRPG.Entities.APlayer;
import com.APOCRPG.Enums.PlayerStats;
import com.APOCRPG.Enums.ProfileStats;
import com.APOCRPG.Items.Items;
import com.APOCRPG.Main.Plugin;
import com.APOCRPG.Main.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static com.APOCRPG.Items.Items.createItem;

public class PlayerEventsListener implements Listener {

    //List the negative potions here
    //TODO: Fill it as you please
    private enum NegativeEffects{
        CONFUSION, HARM, HUNGER,POISON, SLOW_DIGGING, SLOW, WEAKNESS, WITHER
    }

    private Database db = new Database();
    public Plugin plugin;
    public Random r = Plugin.Random;

    private List<String> BOSSES = plugin.getConfig().getStringList("Mobs.bosses");

    //Player kills a monster, a boss or another player
    @EventHandler
    public void onMobKill(EntityDeathEvent e) {

        //If the killer's not a player, stop
        if (!(e.getEntity().getKiller() instanceof Player)) return;

        Entity killed = e.getEntity();
        APlayer killer = (APlayer) e.getEntity().getKiller();

        //If it's one of the bosses from the list = 5 XP
        if (BOSSES.contains(String.valueOf(killed.getEntityId()))) {

            killer.increaseStat(PlayerStats.STAT_POINTS, 5);
            killer.increaseStat(ProfileStats.TOTAL_STAT_POINTS, 5);

        //If it's one of the vanilla bosses = 5 XP
        } else if (killed.getType().equals(EntityType.ENDER_DRAGON) ||
                   killed.getType().equals(EntityType.WITHER)) {

            killer.increaseStat(PlayerStats.STAT_POINTS, 5);
            killer.increaseStat(ProfileStats.TOTAL_STAT_POINTS, 5);

        //If it's another player = 2 XP
        } else if (killed instanceof Player) {

            killer.increaseStat(PlayerStats.STAT_POINTS, 2);
            killer.increaseStat(ProfileStats.TOTAL_STAT_POINTS, 2);

        //If it's a mob (Monster, animal, etc) = 1 XP
        } else if (killed instanceof Monster) {

            killer.increaseStat(PlayerStats.STAT_POINTS, 1);
            killer.increaseStat(ProfileStats.TOTAL_STAT_POINTS, 1);

        }
    }

    //Player joins the game
    //Creating armor and health recovery runnable for player
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        APlayer p = (APlayer) e.getPlayer();
        p.addToDatabase();

        //Recovery
        new BukkitRunnable() {
            public void run() {
                if (p.getStat(PlayerStats.RECOVERY) > 0) return;

                //If the player's at full health we won't run any more code
                if (p.getHealth() == p.getMaxHealth()) return;

                if (!p.isOnline()) { this.cancel(); }

                //We won't heal the player:
                //    if he's been in combat in the past 30 secs,
                //    if his hunger is below 14
                if (p.inCombat() || p.getFoodLevel() < 14) return;
                //    if he's affected by any bad potions
                for(PotionEffect effects: p.getActivePotionEffects()){
                    for(NegativeEffects bad: NegativeEffects.values()){
                        if(effects.getType().getName().equalsIgnoreCase(bad.name())) return;
                    }
                }

                //Now we are healing that poor sucker
                //TODO: Not the best solution, under 100 points, you won't get healed at all
                //TODO: Need to set up a system to store health as float or something
                if (p.getHealth()+getHealthPerPoint(p) >= p.getMaxHealth()) {
                    p.setHealth(p.getMaxHealth());
                } else {
                    p.setHealth(p.getHealth()+getHealthPerPoint(p)); //There's no problem going over to double
                }
            }

            private int getHealthPerPoint(APlayer p) {
                return (p.getStat(PlayerStats.RECOVERY)/100);
            }
        }.runTaskTimer(plugin, 0, 200); //Runs every 10 seconds (20 ticks multiplied by 10)

        //Armor
        new BukkitRunnable() {
            public void run() {
                ItemStack[] armor = p.getEquipment().getArmorContents();
                List<ItemStack> updatedArmor = null;

                if (p.getStat(PlayerStats.ARMOR) > 0) return;

                //Cancel the runnable if player's not online
                if (!p.isOnline()) { this.cancel(); }

                //We won't heal the player if he's been in combat in the past 30 secs
                if (p.inCombat()) return;

                //If all the player's armor is at full health return
                for (ItemStack is : armor) {
                    if (is.getDurability()-getDurabilityPerPoint(p) <= 0) {
                        is.setDurability((short) 0);
                        updatedArmor.add(is);
                    } else {
                        is.setDurability((short) (is.getDurability()-getDurabilityPerPoint(p)));
                    }
                }
                p.getEquipment().setArmorContents((ItemStack[]) updatedArmor.toArray());
            }

            private short getDurabilityPerPoint(APlayer p) {
                return  (short) p.getStat(PlayerStats.RECOVERY);
            }
        }.runTaskTimer(plugin, 0, 200); //Runs every 10 seconds (20 ticks multiplied by 10)
    }

    //Checking for players that engage in combat
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        //If player is damaged, set him a the 30 sec combat counter
        if (e.getEntity() instanceof Player) { ((APlayer) e.getEntity()).setInCombat(); }
    }

    //Agility
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        APlayer player = (APlayer) e.getPlayer();

        float normalSpeed = 0.2f;
        float percentage = player.getStat(PlayerStats.AGILITY) / 10;
        player.setWalkSpeed(normalSpeed * percentage);

        //Backup technique
        /*Vector dir = player.getLocation().getDirection();
        Vector vec = new Vector(dir.getX() * percentage, dir.getY * percentage, dir.getZ() * percentage);
        player.setVelocity(vec);*/
    }

    //Evasion
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {

        //If the damager isn't a player, stop
        if (!(e.getDamager() instanceof Player)) return;
        APlayer damager = (APlayer) e.getDamager();

        Random r = new Random();
        float percentage = damager.getStat(PlayerStats.EVASION) / 10;
        float chance = r.nextFloat();
        if (chance <= percentage) {
            e.setDamage(0);
            //Or cancel the event, dunno
            //e.setCancelled(true);
            damager.sendMessage(Settings.Cfg.APOCRPG_SUCCESS.getString() + "Dodged"); //I personally don't like to spam the chat, but... here's the message
        }
    }

    //Luck

    //Neater version, just no way to get the location of the chest, custom dungeon chest material would come handy here
    /*@EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent e) {
        if (!(e.getInventory().getHolder() instanceof Chest ||
              e.getInventory().getHolder() instanceof DoubleChest ) ||
              CHECK FOR DUNGEON CHEST) return;
    }*/

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        //Return if the clicked block ain't a dungeon chest
        if (!(plugin.dungeonChestLocations.contains(e.getClickedBlock().getLocation()) &&
            (e.getClickedBlock() instanceof Chest || e.getClickedBlock() instanceof  DoubleChest))) return;

        InventoryHolder block = (InventoryHolder) e.getClickedBlock();
        APlayer p = (APlayer) e.getPlayer();

        if (!Settings.Cfg.CHEST_FILL_RPG.getBoolean()) return;

        Inventory inv = Bukkit.createInventory(null, block.getInventory().getSize(), "Dungeon Chest");
        List<ItemStack> items = null;

        int minItems = Settings.Cfg.CHEST_MIN_ITEMS.getInt();
        int maxItems = Settings.Cfg.CHEST_MAX_ITEMS.getInt();
        boolean doubleRarity = false;

        //If the players luck hits, we either double the max items or the rarity
        if (r.nextFloat() <= (float) (p.getStat(PlayerStats.LUCK)/100)) {
            if (r.nextBoolean() == true) {
                maxItems=(maxItems*2);
            } else {
                doubleRarity = true;
            }
        }
        //Calculating the number of items that get generated
        int randomNumberOfItems = r.nextInt(maxItems - minItems + 1) + minItems;

        if (maxItems <= 0) return;

        //Generating random items
        for (int x=0; x<=randomNumberOfItems; x++) {
            //Randomly pick item's material
            ItemStack item = new ItemStack(Items.Materials[r.nextInt(Items.Materials.length)]);
            //Getting a random tier
            int tier = pickRandomTier();
            //If luck skill hits upgrade tier
            if (doubleRarity && tier < 5) tier++;
            //Giving the item a name
            //Item has 25% chance to be unidentified item
            String itemName;
            if (r.nextFloat() <= 0.25f) {
                itemName = Settings.Cfg.DISPLAY_NAME_UNIDENTIFIED_ITEM.getString();
            } else {
                itemName = Settings.getRandomPrefix()+" "+Settings.getRandomSuffix()+item.getItemMeta().getDisplayName();
            }
            //Randomly pick socket count for the item
            int socketCount = getRandomSocketCount();
            //Not yet implemented

            //Build the item
            item = Items.diablofy(item, 0, tier);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(itemName);
            item.setItemMeta(itemMeta);
        }
    }

    private int pickRandomTier() {
        double d = Math.random() * 100;
        if ((d -= Settings.Cfg.TIER_COMMON_MAX_CHANCE.getDouble()) < 0) return 0;
        if ((d -= Settings.Cfg.TIER_UNCOMMON_MAX_CHANCE.getDouble()) < 0) return 1;
        if ((d -= Settings.Cfg.TIER_RARE_MAX_CHANCE.getDouble()) < 0) return 2;
        if ((d -= Settings.Cfg.TIER_UNIQUE_MAX_CHANCE.getDouble()) < 0) return 3;
        if ((d -= Settings.Cfg.TIER_SET_MAX_CHANCE.getDouble()) < 0) return 4;
        if ((d -= Settings.Cfg.TIER_LEGENDARY_MAX_CHANCE.getDouble()) < 0) return 5;
        return -1;
    }

    private int getRandomSocketCount() {
        if (r.nextFloat() <= 0.10f) return 1;
        if (r.nextFloat() <= 0.05f) return 2;
        if (r.nextFloat() <= 0.01f) return 3;
        return 0;
    }
}