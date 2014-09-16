package com.modcrafting.diablodrops.tier;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import com.modcrafting.diablodrops.DiabloDrops;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class TierUtil {

    private TierUtil() {}

    public static Tier randomTier(Collection<Tier> collection) {
        Validate.notNull(collection, "Collection<Tier> cannot be null");
        Tier[] array = collection.toArray(new Tier[collection.size()]);
        return array[RandomUtils.nextInt(array.length)];
    }

    @Deprecated
    public static MythicDropsTier randomTierWithChance(Collection<MythicDropsTier> values, String worldName) {
        Validate.notNull(values, "Collection<Tier> cannot be null");
        return randomTierWithChance(values);
    }

    public static MythicDropsTier randomTierWithChance(Collection<MythicDropsTier> values) {
        Validate.notNull(values, "Collection<Tier> cannot be null");

        double totalWeight = 0;
        
        List<MythicDropsTier> v = new ArrayList<>(values);
        Collections.shuffle(v);
        for (MythicDropsTier t : v) {
            totalWeight += t.getSpawnChance();
        }

        double chosenWeight = DiabloDrops.getInstance().getSingleRandom().nextDouble() * totalWeight;

        double currentWeight = 0;

        for (MythicDropsTier t : v) {
            currentWeight += t.getSpawnChance();

            if (currentWeight >= chosenWeight) {
                return t;
            }
        }

        return null;
    }

    @Deprecated
    public static MythicDropsTier randomTierWithIdentifyChance(Collection<MythicDropsTier> values, String worldName) {
        Validate.notNull(values, "Collection<Tier> cannot be null");
        return randomTierWithIdentifyChance(values);
    }

    public static MythicDropsTier randomTierWithIdentifyChance(Collection<MythicDropsTier> values) {
        Validate.notNull(values, "Collection<Tier> cannot be null");
        double totalWeight = 0;
        List<MythicDropsTier> v = new ArrayList<>(values);
        Collections.shuffle(v);
        for (MythicDropsTier t : v) {
            totalWeight += t.getIdentifyChance();
        }

        double chosenWeight = DiabloDrops.getInstance().getSingleRandom().nextDouble() * totalWeight;

        double currentWeight = 0;

        for (MythicDropsTier t : v) {
            currentWeight += t.getIdentifyChance();

            if (currentWeight >= chosenWeight) {
                return t;
            }
        }

        return null;
    }

    public static Collection<MythicDropsTier> getTiersFromStrings(Collection<String> strings) {
        Validate.notNull(strings, "Collection<String> cannot be null");
        Set<MythicDropsTier> tiers = new LinkedHashSet<>();
        for (String s : strings) {
            MythicDropsTier t = getTier(s);
            if (t != null) {
                tiers.add(t);
            }
        }
        return tiers;
    }

    public static MythicDropsTier getTier(String name) {
        Validate.notNull(name, "String cannot be null");
        MythicDropsTier tier = TierMap.getInstance().get(name.toLowerCase());
        if (tier != null) {
            return tier;
        }
        for (MythicDropsTier t : TierMap.getInstance().values()) {
            if (t.getName().equalsIgnoreCase(name)) {
                return t;
            }
            if (t.getDisplayName().equalsIgnoreCase(name)) {
                return t;
            }
        }
        return null;
    }

    public static List<String> getStringsFromTiers(Collection<Tier> collection) {
        Validate.notNull(collection, "Collection<Tier> cannot be null");
        List<String> col = new ArrayList<>();
        for (Tier t : collection) {
            col.add(t.getName());
        }
        return col;
    }

    public static MythicDropsTier getTierFromItemStack(ItemStack itemStack) {
        return getTierFromItemStack(itemStack, TierMap.getInstance().values());
    }

    public static MythicDropsTier getTierFromItemStack(ItemStack itemStack, Collection<MythicDropsTier> tiers) {
        Validate.notNull(itemStack);
        Validate.notNull(tiers);
        if (!itemStack.hasItemMeta()) {
            return null;
        }
        if (!itemStack.getItemMeta().hasDisplayName()) {
            return null;
        }
        String displayName = itemStack.getItemMeta().getDisplayName();
        ChatColor initColor = findColor(displayName);
        String colors = ChatColor.getLastColors(displayName);
        ChatColor
                endColor =
                ChatColor.getLastColors(displayName).contains(String.valueOf(ChatColor.COLOR_CHAR)) ?
                        ChatColor.getByChar(colors.substring(1, 2)) : null;
        if (initColor == null || endColor == null || initColor == endColor) {
            return null;
        }
        for (MythicDropsTier t : tiers) {
            if (t.getDisplayColor() == initColor && t.getIdentificationColor() == endColor) {
                return t;
            }
        }
        return null;
    }

    private static ChatColor findColor(final String s) {
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == (char) 167 && (i + 1) < c.length) {
                return ChatColor.getByChar(c[i + 1]);
            }
        }
        return null;
    }

    @Deprecated
    public static Collection<Tier> skewTierCollectionToRarer(Collection<Tier> values,
                                                             int numberToKeep) {
        return values;
    }

    public static Tier randomTierWithChance(Map<Tier, Double> chanceMap) {
        Validate.notNull(chanceMap, "Map<Tier, Double> cannot be null");
        double totalWeight = 0;
        List<Tier> keys = new ArrayList<>(chanceMap.keySet());
        Collections.shuffle(keys);
        for (Tier t : keys) {
            totalWeight += chanceMap.get(t);
        }

        double chosenWeight = DiabloDrops.getInstance().getSingleRandom().nextDouble() * totalWeight;

        double currentWeight = 0;

        for (Tier t : keys) {
            currentWeight += chanceMap.get(t);

            if (currentWeight >= chosenWeight) {
                return t;
            }
        }
        return null;
    }

}
