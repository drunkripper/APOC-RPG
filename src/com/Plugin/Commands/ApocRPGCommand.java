package com.Plugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Plugin.Items.ItemAPI;

public class ApocRPGCommand implements CommandExecutor{

	
	@Override
	public boolean onCommand(CommandSender Sender, Command Command, String arg2, String[] args) {
		Player Player = (Player) Sender;
		if(Command.getLabel().equals("generateitem")) {
			ItemStack item = ItemAPI.createItem();
			Player.getInventory().addItem(item);
			return true;
		} else if(Command.getLabel().equals("generateuseful")) {
			ItemStack item;
			if(args.length==0) {
				item = ItemAPI.generateUsefulItem(0);
				return true;
			} else {
				item = ItemAPI.generateUsefulItem(Integer.parseInt(args[0]));
				Player.getInventory().addItem(item);
				return true;
			}
		} else {
			System.out.println("Something went wrong");
			return false;
		}
	}
	
}
