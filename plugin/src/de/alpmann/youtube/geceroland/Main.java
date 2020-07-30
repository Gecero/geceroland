package de.alpmann.youtube.geceroland;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.alpmann.youtube.geceroland.commands.*;
import de.alpmann.youtube.geceroland.realtime.*;

public class Main extends JavaPlugin {
	
	Server server;
	
	@Override
	public void onEnable() {
		// startup
		// reloads
		// plugin reloads
		server = (Server)this.getServer();
		
		CommandManager.init();
		CommandManager.register(new TNTCannon());
		
		RealtimeManager.init();
		RealtimeManager.register(new EnhancedEnderPearl());
		server.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				RealtimeManager.update(server);
			}
		}, 0L, 1L);
		
	}
	
	@Override
	public void onDisable() {
		RealtimeManager.getData().clear();
		CommandManager.getData().clear();
		// shutdown
		// reloads
		// plugin reloads
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		int result = CommandManager.callCommand(label, sender, args);
		
		if(result == -1)
			sender.sendMessage("ERROR: Command not Found!");
		else if(result != 0)
			sender.sendMessage("'" + label + "' exited with code " + String.valueOf(result) + " (" + CommandManager.getCommand(label).getErrorMessage(result) + ")");
		
		return true;
	}
}
