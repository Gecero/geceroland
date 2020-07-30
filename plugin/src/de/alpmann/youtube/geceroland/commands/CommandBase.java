package de.alpmann.youtube.geceroland.commands;

import java.util.Dictionary;
import java.util.Hashtable;

import org.bukkit.command.CommandSender;

public class CommandBase {
	private String name;
	protected Dictionary<Integer, String> errorCodeLookup;
	
	public CommandBase(String name) {
		this.name = name;
		errorCodeLookup = new Hashtable<Integer, String>();
	}
	
	public int run(CommandSender sender, String[] args) {
		
		return 0;
	}
	
	public String getName() { return this.name; }
	public String getErrorMessage(int errorCode) {
		String ret = errorCodeLookup.get(errorCode);
		if(ret == null)
			return "Error code not found!";
		else
			return ret;
	}
}
