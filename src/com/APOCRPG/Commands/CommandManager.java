package com.APOCRPG.Commands;

import com.APOCRPG.Main.Plugin;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;

public class CommandManager implements CommandExecutor{

    private ArrayList<ApocCommand> cmds;
    private Plugin plugin;

    //Add your command classes here, follow the example
    public CommandManager() {
        cmds = new ArrayList<>();
        cmds.add(new ExampleCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("apocrpg")) {

            //If there are no arguments, print out all available ones
            if (args.length == 0) {
                for (ApocCommand acmd : cmds) {
                    CommandInfo info = acmd.getClass().getAnnotation(CommandInfo.class); //TODO: Fixed, (maybe), to be tested
                    sender.sendMessage(ChatColor.GOLD + "/apocrpg (" + StringUtils.join(info.aliases(), " ").trim() + ") " + info.usage() + " - " + info.description());
                }
                return true;
            }

            ApocCommand requestedCmd = null;

            //We're checking for match against every class and it's aliases
            for (ApocCommand acmd : cmds) {
                CommandInfo info = acmd.getClass().getAnnotation(CommandInfo.class);
                for (String alias : info.aliases()) {
                    if (alias.equals(args[0])) {
                        requestedCmd = acmd;
                        break;
                    }
                }
            }

            //If we don't find the command they were requesting for
            if (requestedCmd == null) {
                sender.sendMessage(plugin.APOCRPG_ERROR + ChatColor.YELLOW + "Command not found");
                return true;
            }

            //If we do find it, we'll check for permissions
            if ((requestedCmd.getClass().getAnnotation(CommandInfo.class).op() && !sender.isOp()) ||
               (!sender.hasPermission(requestedCmd.getClass().getAnnotation(CommandInfo.class).permission()))) {
                sender.sendMessage(plugin.APOCRPG_ERROR + ChatColor.RED + "Access Denied! Try adding sudo before the command.");
                return true;
            }

            //Swapping around the arguments, no big deal
            ArrayList<String> newArgs = new ArrayList<>();
            Collections.addAll(newArgs, args);
            newArgs.remove(0);
            args = newArgs.toArray(new String[newArgs.size()]);

            requestedCmd.onCommand(sender, args);
        }
        return true;
    }


}
