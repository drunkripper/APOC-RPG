package com.modcrafting.diablodrops.items;

import org.apache.commons.lang.WordUtils;
import org.bukkit.enchantments.Enchantment;

import java.util.List;
import java.util.Map;

public final class SocketGem {

    private final String name;
    private final GemType gemType;
    private final List<SocketEffect> socketEffects;
    private final double chance;
    private final String prefix;
    private final String suffix;
    private final List<String> lore;
    private final Map<Enchantment, Integer> enchantments;
    private final String description;

    public SocketGem(String name, GemType gemType, List<SocketEffect> socketEffects, double chance,
                     String prefix, String suffix, List<String> lore,
                     Map<Enchantment, Integer> enchantments) {
        this(name, gemType, socketEffects, chance, prefix, suffix, lore, enchantments, "");
    }

    public SocketGem(
            String name, GemType gemType, List<SocketEffect> socketEffects, double chance,
            String prefix,
            String suffix, List<String> lore, Map<Enchantment, Integer> enchantments,
            String description) {
        this.name = name;
        this.gemType = gemType;
        this.socketEffects = socketEffects;
        this.chance = chance;
        this.prefix = prefix;
        this.suffix = suffix;
        this.lore = lore;
        this.enchantments = enchantments;
        this.description = description;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public String getName() {
        return name;
    }

    public String getPresentableType() {
        return WordUtils.capitalize(getGemType().getName());
    }

    public GemType getGemType() {
        return gemType;
    }

    public List<SocketEffect> getSocketEffects() {
        return socketEffects;
    }

    public double getChance() {
        return chance;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public List<String> getLore() {
        return lore;
    }

    public String getDescription() {
        return description;
    }

}
