package com.modcrafting.diablodrops.items;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.modcrafting.diablodrops.DiabloDrops;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class SocketGemUtil {

    private SocketGemUtil() {
        // do nothing;
    }

    public static SocketGem getSocketGemFromItemStack(ItemStack itemStack) {
        SocketGem sg;
        if (!DiabloDrops.getInstance().getSockettingSettings().getSocketGemMaterials().contains
                (itemStack.getType())) {
            return null;
        }
        if (!itemStack.hasItemMeta() || !itemStack.getItemMeta().hasDisplayName()) {
            return null;
        }
        String
                replacedArgs =
                ChatColor.stripColor(DiabloDrops.getInstance()
                                .getSockettingSettings()
                                .getSocketGemName().replace("%socketgem%", "").replace('&', '\u00A7').replace("\u00A7\u00A7", "&"));
        String type = ChatColor.stripColor(
                itemStack.getItemMeta().getDisplayName().replace(replacedArgs, ""));
        if (type == null) {
            return null;
        }
        sg = DiabloDrops.getInstance().getSockettingSettings().getSocketGemMap().get(type);
        if (sg == null) {
            sg = SocketGemUtil.getSocketGemFromName(type);
        }
        return sg;
    }

    public static SocketGem getSocketGemFromName(String name) {
        for (SocketGem sg : DiabloDrops.getInstance().getSockettingSettings().getSocketGemMap()
                .values()) {
            if (sg.getName().equalsIgnoreCase(name) || sg.getName().equalsIgnoreCase(name.replace("_", " "))) {
                return sg;
            }
        }
        return null;
    }

    public static SocketGem getRandomSocketGemWithChance() {
        Map<String, SocketGem>
                socketGemMap =
                DiabloDrops.getInstance().getSockettingSettings().getSocketGemMap
                        ();
        if (socketGemMap == null || socketGemMap.isEmpty()) {
            return null;
        }
        double totalWeight = 0;
        for (SocketGem sg : socketGemMap.values()) {
            totalWeight += sg.getChance();
        }

        double chosenWeight = DiabloDrops.getInstance().getSingleRandom().nextDouble() * totalWeight;

        double currentWeight = 0;

        List<SocketGem> l = new ArrayList<>(socketGemMap.values());
        Collections.shuffle(l);

        for (SocketGem sg : socketGemMap.values()) {
        	currentWeight += sg.getChance();

            if (currentWeight >= chosenWeight) {
                return sg;
            }
        }
        return null;
    }

    public static Material getRandomSocketGemMaterial() {
        List<Material> materialDatas = DiabloDrops.getInstance().getSockettingSettings()
                .getSocketGemMaterials();
        if (materialDatas == null || materialDatas.isEmpty()) {
            return null;
        }
        return materialDatas.get(RandomUtils.nextInt(materialDatas.size()));
    }

}
