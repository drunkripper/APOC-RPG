package com.modcrafting.diablodrops;

import org.bukkit.Material;

import com.modcrafting.diablodrops.items.SocketGem;

import java.util.List;
import java.util.Map;

public interface SockettingSettings {

    @Deprecated
    boolean isEnabled();

    String getSocketGemName();

    List<String> getSocketGemLore();

    String getSockettedItemString();

    List<String> getSockettedItemLore();

    boolean isUseAttackerItemInHand();

    boolean isUseAttackerArmorEquipped();

    boolean isUseDefenderItemInHand();

    boolean isUseDefenderArmorEquipped();

    boolean isPreventMultipleChangesFromSockets();

    List<Material> getSocketGemMaterials();

    Map<String, SocketGem> getSocketGemMap();

    List<String> getSocketGemPrefixes();

    List<String> getSocketGemSuffixes();

    boolean isCanDropSocketGemsOnItems();
}
