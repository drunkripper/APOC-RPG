package com.Plugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Plugin.Items.ItemAPI;
import com.Plugin.Main.Plugin;

public class ApocRPGCommand implements CommandExecutor {

	
	@Override
	public boolean onCommand(CommandSender Sender, Command Command, String arg2, String[] args) {
		Player Player = (Player) Sender;
		if (Command.getLabel().equals("generateitem")) {
			ItemStack item = ItemAPI.createItem();
			if (args.length==0) {
				Player.getInventory().addItem(item);
			} else {
				Player TargetPlayer = Plugin.Plugin.getServer().getPlayer(args[0]);
				TargetPlayer.getInventory().addItem(item);
			}
			return true;
		} else if (Command.getLabel().equals("generateuseful")) {
			ItemStack item;
			if(args.length==0) {
				item = ItemAPI.generateUsefulItem(0);
			} else {
				item = ItemAPI.generateUsefulItem(Integer.parseInt(args[0]));
			}
			Player.getInventory().addItem(item);
			return true;
		} else if (Command.getLabel().equals("generatesocket")) {
			Player.getInventory().addItem(ItemAPI.createSocket());
			return true;
		} else {
			System.out.println("Unkown command sent.");
			return false;
		}
	}
	
}
