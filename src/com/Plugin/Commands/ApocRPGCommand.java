package com.Plugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Plugin.Items.ItemAPI;

public class ApocRPGCommand implements CommandExecutor{

	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if(arg1.getLabel().equals("generateitem")) {
		ItemStack item = ItemAPI.createItem();
		((Player)arg0).getInventory().addItem(item);
		return true;
		}
		else if(arg1.getLabel().equals("generateuseful")) {
			ItemStack item;
			if(arg3.length==0)
				item = ItemAPI.generateUsefulItem(0);
			else
				item = ItemAPI.generateUsefulItem(Integer.parseInt(arg3[0]));
			((Player)arg0).getInventory().addItem(item);
			return true;
		}
		else
		{	System.out.println("Something went wrong");
			return false;
		}
	}

	
}
