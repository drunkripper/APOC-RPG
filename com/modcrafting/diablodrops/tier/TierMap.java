package com.modcrafting.diablodrops.tier;

import org.apache.commons.lang.math.RandomUtils;

import com.modcrafting.diablodrops.DiabloDrops;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("serial")
public final class TierMap extends ConcurrentHashMap<String, MythicDropsTier> {

    private static final TierMap _INSTANCE = new TierMap();

    private TierMap() {
        // do nothing
    }

    /**
     * Gets the instance of CustomItemMap running on the server.
     *
     * @return instance running on the server
     */
    public static TierMap getInstance() {
        return _INSTANCE;
    }

    @Deprecated
    public MythicDropsTier getRandomWithChance(String worldName) {
        return getRandomWithChance();
    }

    /**
     * Gets a random {@link Tier} out of the ones loaded on the server using chance. Returns null if none found.
     *
     * @return random Tier
     */
    public MythicDropsTier getRandomWithChance() {
        double totalWeight = 0;
        List<MythicDropsTier> v = new ArrayList<>(values());
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

    /**
     * Gets a random {@link Tier} out of the ones loaded on the server.
     *
     * @return random Tier
     */
    public MythicDropsTier getRandom() {
        MythicDropsTier[] valueArray = values().toArray(new MythicDropsTier[values().size()]);
        return valueArray[RandomUtils.nextInt(values().size())];
    }

    @Deprecated
    public MythicDropsTier getRandomWithIdentifyChance(String worldName) {
        return getRandomWithIdentifyChance();
    }

    public MythicDropsTier getRandomWithIdentifyChance() {
        double totalWeight = 0;
        List<MythicDropsTier> v = new ArrayList<>(values());
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

}
