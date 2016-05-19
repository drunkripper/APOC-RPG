package com.APOCRPG.Enums;

import com.APOCRPG.Main.Plugin;
import com.APOCRPG.Main.Settings;

import java.io.File;

public enum Folders {
    MainFolder(new File(Settings.Cfg.MAIN_FOLDER.getString())),
    Dungeons(new File(MainFolder.getValue() + "/Dungeons")),
    Dungeons_Biome(new File(MainFolder.getValue() + "/Dungeons/Biome")),
    LandRuins(new File(MainFolder.getValue() + "/LandRuins")); //Dunno about that, not my line

    private final File path;

    Folders(File path) {
        this.path = path;
    }

    public File getValue() {
        return path;
    }
}