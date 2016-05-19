package com.APOCRPG.Enums;

import com.APOCRPG.API.Database;

import java.util.List;

public enum DatabaseTables {
    Profiles(new String[][]{
            new String[] {"uuid", "int(32)"},
            new String[] {"latestname", "varchar(35)"}
    }),
    ProfileStats(new String[][]{
            new String[]{"uuid", "INT(32)"},
            new String[]{"stat", "varchar(40)"},
            new String[]{"value", "int(5)"}
    }),
    Players(new String[][]{
            new String[]{"uuid", "INT(32)"},
            new String[]{"exp", "int(5)"},
            new String[]{"stat_points", "int(5)"},
            new String[]{"recovery", "int(5)"},
            new String[]{"evasion", "int(5)"},
            new String[]{"agility", "int(5)"},
            new String[]{"luck", "int(5)"},
            new String[]{"armor", "int(5)"}
    }),
    PlayerKills(new String[][]{
            new String[]{"uuid", "int(32)"},
            new String[]{"entitytype", "varchar(35)"}
    }),
    NameChanges(new String[][]{
            new String[]{"uuid", "int(32)"},
            new String[]{"lastname", "varchar(35)"},
            new String[]{"newname", "varchar(35)"},
            new String[]{"date", "date(25)"}
    });

    private final String[][] table;

    DatabaseTables(String[][] table) {
        this.table = table;
    }

    public String[][] getValues() { return table; }
}
