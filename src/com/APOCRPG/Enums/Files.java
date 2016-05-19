package com.APOCRPG.Enums;

import com.APOCRPG.Main.Settings;

import java.io.File;

public enum Files {
    MainFolder(new File(Settings.Cfg.MAIN_FOLDER.getString())),
    DungeonChestLocations(new File(Folders.Dungeons_Biome + "DungeonChestLocations.yml"));

    private final File path;

    Files(File path) {
        this.path = path;
    }

    public File getValue() {
        return path;
    }
}