package de.alpmann.youtube.geceroland.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import de.alpmann.youtube.geceroland.GameUtils;

public class Ping extends CommandBase {
	public Ping() {
		super("ping");
	}
	
	@Override
	public int run(CommandSender sender, String[] args) {
		sender.sendMessage("Pong!");
		
		return 0;
	}
	
}


