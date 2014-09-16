package com.modcrafting.diablodrops.items;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import com.modcrafting.diablodrops.DiabloDrops;

public final class SocketItem extends MythicItemStack {

    public SocketItem(Material material, SocketGem socketGem) {
    	super(material, 1, (short) 0, DiabloDrops.getInstance().getSockettingSettings().getSocketGemName().replace("%socketgem%", socketGem.getName()), getLore(DiabloDrops.getInstance().getSockettingSettings().getSocketGemLore(), socketGem));
    }

    @Deprecated
    public SocketItem(MaterialData materialData, SocketGem socketGem) {
    	super(materialData.getItemType(), 1, (short) 0, DiabloDrops.getInstance().getSockettingSettings().getSocketGemName().replace("%socketgem%", socketGem.getName()), getLore(DiabloDrops.getInstance().getSockettingSettings().getSocketGemLore(), socketGem));
    }
    
    private static List<String> getLore(List<String> Lore, SocketGem socketGem) {
    	int i = 0;
    	for (String string : Lore) {
    		i++;
    		Lore.set(i, string.replace("%socketgem%", socketGem.getName()));
    		Lore.set(i, string.replace("%type%", socketGem.getPresentableType()));
    	}
    	return Lore;
    }

}
