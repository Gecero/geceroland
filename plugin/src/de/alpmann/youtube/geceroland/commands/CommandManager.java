package de.alpmann.youtube.geceroland.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class CommandManager {
	static ArrayList<CommandBase> commands;
	
	static public void init() {
		commands = new ArrayList<CommandBase>();
	}
	
	static public void register(CommandBase cmd) {
		commands.add(cmd);
		Bukkit.getLogger().info("Registered Command: " + cmd.getName());
	}
	
	static public int callCommand(String commandName, CommandSender sender, String[] args) {
		commandName = commandName.toLowerCase();
		
		CommandBase cmd = getCommand(commandName);
		if(cmd != null)
			return cmd.run(sender, args);
		
		return -1;
	}
	
	static public CommandBase getCommand(String commandName) {
		for(int i = 0; i < commands.size(); i++)
			if(commands.get(i).getName().equalsIgnoreCase(commandName))
				return commands.get(i);
		
		return null;
	}
	
	static public ArrayList<CommandBase> getData() {
		return commands;
	}
}
