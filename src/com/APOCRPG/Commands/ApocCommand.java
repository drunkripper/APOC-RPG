package com.APOCRPG.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(description = "", usage = "", aliases = "", permissions = {})
public abstract class ApocCommand {

    public abstract void onCommand(CommandSender sender, String[] args);
}
