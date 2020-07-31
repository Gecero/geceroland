package de.alpmann.youtube.geceroland.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.alpmann.youtube.geceroland.GameUtils;

public class TNTCannon extends CommandBase {
	public TNTCannon() {
		super("tntcannon");
		errorCodeLookup.put(-2, "Invalid amount of arguments!");
		errorCodeLookup.put(-3, "Command can only be executed as Player!");
		errorCodeLookup.put(-4, "Direction is unclear!");
	}
	
	@Override
	public int run(CommandSender sender, String[] args) {
		
		if(args.length != 2)
			return -2;
		
		if(sender instanceof Player == false)
			return -3;
		
		Vector directionVector;
		float distance = Float.valueOf(args[1]);
		// see file "distance fix.xlsx"
		distance = distance + fix(distance);
		
		
		switch(args[0].toLowerCase()) {
		case "north":
			directionVector = GameUtils.vectorNorth;
			break;
		case "east":
			directionVector = GameUtils.vectorEast;
			break;
		case "south":
			directionVector = GameUtils.vectorSouth;
			break;
		case "west":
			directionVector = GameUtils.vectorWest;
			break;
		default:
			return -4;
		}
		
		Player player = (Player)sender;
		World world = player.getWorld();
		Location pos = player.getLocation(); // player position
		player.setFlying(false);
		
		// build up tnt cannon
		for(int x = -4; x <= 4; x++)
			for(int z = -4; z <= 4; z++)
				GameUtils.setPlayerRelativeBlock(player, x, -1, z, Material.WATER);
		
		GameUtils.setPlayerRelativeBlock(player, 0, -1, 0, Material.OAK_FENCE);
		
		// teleport player into the tnt cannon
		float x = (float)Math.floor(pos.getX()) + 0.5f; // center x and z at the middle 
		float z = (float)Math.floor(pos.getZ()) + 0.5f; // of the oak fence
		float y = (float)pos.getY() + 0.5f;
		
		player.teleport(new Location(world, x, y, z));
		
		
		// -----Here begins the SPAWN TNT part-----
		float directional = distance/15;
		
		// tnt's to get the player up are only primed when the value
		// of sideways tnt's is low (low distance=low sideways tnt's)
		// because theese tnt's also catapult the player up
		if(distance < 150) {
				GameUtils.spawnPlayerRelativeEntity(player, -1, -1, -1, EntityType.PRIMED_TNT);
				GameUtils.spawnPlayerRelativeEntity(player, 0, -1, -1, EntityType.PRIMED_TNT);
				GameUtils.spawnPlayerRelativeEntity(player, 1, -1, -1, EntityType.PRIMED_TNT);
				
				GameUtils.spawnPlayerRelativeEntity(player, -1, -1, 0, EntityType.PRIMED_TNT);
				GameUtils.spawnPlayerRelativeEntity(player, 1, -1, 0, EntityType.PRIMED_TNT);
			
				GameUtils.spawnPlayerRelativeEntity(player, -1, -1, 1, EntityType.PRIMED_TNT);
				GameUtils.spawnPlayerRelativeEntity(player, 0, -1, 1, EntityType.PRIMED_TNT);
				GameUtils.spawnPlayerRelativeEntity(player, 1, -1, 1, EntityType.PRIMED_TNT);
		}
		
		
		directionVector.multiply(-1f); // inverse. so when given parameter north the tnt gets placed south
		
		for(int i = 0; i < Math.ceil(directional); i++) {
			Vector tmpVector = directionVector.clone();
			
			// the height where the tnt is spawned.
			// the explosion height relative to the
			// player affects the angle
			float spawnY = -1f;
			
			// distance/300 to get it smaller and
			// times 4 because the water pool is
			// 4x4 in size
			float multiplier = distance/300*4;
			if(multiplier > 4f)
				multiplier = 4f;
			
			spawnY = (-1f) + multiplier/4f;
			if(spawnY >= -0.15f) // we need a minimum angle so the player
				spawnY = -0.15f; // doesnt just get pushed ONLY sideways
			
			tmpVector.multiply(multiplier);
			
			float spawnX = (float)tmpVector.getX();
			float spawnZ = (float)tmpVector.getZ();
			
			GameUtils.spawnPlayerRelativeEntity(player, spawnX, spawnY, spawnZ, EntityType.PRIMED_TNT);
		}
		
		
		return 0;
	}
	
	// just dont think about it, let your mind accept this function
	float fix(float distance) {
		return (float)Math.sin(0.275 * (double)distance * Math.PI) * (float)(distance / Math.PI);
	}
}


