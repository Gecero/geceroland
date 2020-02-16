package de.alpmann.youtube.geceroland;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_15_R1.ChunkConverterPalette.Direction;

public class Main extends JavaPlugin {
	
	Vector vectorWest = new Vector(-1, 0, 0);
	Vector vectorEast = new Vector(1, 0, 0);
	Vector vectorSouth = new Vector(0, 0, 1);
	Vector vectorNorth = new Vector(0, 0, -1);
	
	@Override
	public void onEnable() {
		// startup
		// reloads
		// plugin reloads
		registerCommands();
	}
	
	void registerCommands() {
		
	}
	
	@Override
	public void onDisable() {
		// shutdown
		// reloads
		// plugin reloads
	}
	
	void setPlayerRelativeBlock(Player player, float x, float y, float z, Material mat) {
		World world = player.getWorld();
		Location pos = player.getLocation();
		world.getBlockAt(pos.add(x, y, z)).setType(mat);
	}
	
	void spawnPlayerRelativeEntity(Player player, float x, float y, float z, EntityType entity) {
		World world = player.getWorld();
		Location pos = player.getLocation();
		if(entity != EntityType.PRIMED_TNT) {
			world.spawnEntity(pos.add(x, y, z), entity);
		} else {
			Entity tnt = world.spawnEntity(pos.add(x, y,z ), EntityType.PRIMED_TNT);
			((TNTPrimed)tnt).setFuseTicks(0);
		}
	}
	
	void translatePlayer(Player player, float x, float y, float z) {
		Location pos = player.getLocation();
		pos.add(x, y, z);
		player.teleport(pos);
	}
	
	float randomRange(float min, float max) {
	    Random rand = new Random();
	    return rand.nextFloat() * (max - min) + min;
	}
	
	float fix(float distance) {
		return (float)Math.sin(0.275 * (double)distance * Math.PI) * (float)(distance / Math.PI);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("tntcannon")) {
				if(args.length != 2) {
					sender.sendMessage("amount of arguments wrong");
					return false;
				}
			
			if(sender instanceof Player == false) {
				sender.sendMessage("can only be executed as player");
				return false;
			}
			
			Direction direction;
			Vector directionVector;
			float distance = Float.valueOf(args[1]);
			// see "distance fix.xlsx"
			distance = distance + fix(distance);
			
			
			switch(args[0].toLowerCase()) {
			case "north":
				direction = Direction.NORTH;
				directionVector = vectorNorth;
				break;
			case "east":
				direction = Direction.EAST;
				directionVector = vectorEast;
				break;
			case "south":
				direction = Direction.SOUTH;
				directionVector = vectorSouth;
				break;
			case "west":
				direction = Direction.WEST;
				directionVector = vectorWest;
				break;
			default:
				sender.sendMessage("direction is not understandable");
				return false;
			}
			
			
			// important stuff:
			// Player.getLocation();
			// World.getBlockAt();
			// 1 tnt ~ 20 blocks
			
			Player player = Bukkit.getPlayer(sender.getName());
			World world = player.getWorld();
			Location pos = player.getLocation(); // player position
			player.setFlying(false);
			
			// build up tnt cannon
			for(int x = -4; x <= 4; x++)
				for(int z = -4; z <= 4; z++)
					setPlayerRelativeBlock(player, x, -1, z, Material.WATER);
			
			setPlayerRelativeBlock(player, 0, -1, 0, Material.OAK_FENCE);
			
			// teleport player into the tnt cannon
			float x = (float)Math.floor(pos.getX()) + 0.5f; // center x and z at the middle 
			float z = (float)Math.floor(pos.getZ()) + 0.5f; // of the oak fence
			float y = (float)pos.getY() + 0.5f;
			
			player.teleport(new Location(world, x, y, z));
			
			
			// spawn tnt
			float up = distance/20;
			float directional = distance/15;
			up /= 8; // 1 up loop spawns 8 tnt
			up = (float) Math.pow(up, 0.25f); // up value is too much in general
			
			if(distance < 150) {
				for(int i = 0; i < Math.round(up); i++) {
					spawnPlayerRelativeEntity(player, -1, -1, -1, EntityType.PRIMED_TNT);
					spawnPlayerRelativeEntity(player, 0, -1, -1, EntityType.PRIMED_TNT);
					spawnPlayerRelativeEntity(player, 1, -1, -1, EntityType.PRIMED_TNT);
					
					spawnPlayerRelativeEntity(player, -1, -1, 0, EntityType.PRIMED_TNT);
					spawnPlayerRelativeEntity(player, 1, -1, 0, EntityType.PRIMED_TNT);
				
					spawnPlayerRelativeEntity(player, -1, -1, 1, EntityType.PRIMED_TNT);
					spawnPlayerRelativeEntity(player, 0, -1, 1, EntityType.PRIMED_TNT);
					spawnPlayerRelativeEntity(player, 1, -1, 1, EntityType.PRIMED_TNT);
				}
			}
			
			
			directionVector.multiply(-1f); // inverse. so when moving north the tnt gets placed south
			
			for(int i = 0; i < Math.ceil(directional); i++) {
				Vector tmpVector = directionVector.clone();
				float spawnY = (float)0;
				
				float multiplier = distance/300*4;
				if(multiplier > 4) {
					multiplier = 4;
					spawnY = -1f + (4-multiplier);
					if(spawnY >= -0.1f) // we need some angle
						spawnY = -0.1f; // at least
				}
				tmpVector.multiply(multiplier);
				
				float spawnX = (float)tmpVector.getX();
				float spawnZ = (float)tmpVector.getZ();
				
				spawnPlayerRelativeEntity(player, spawnX, spawnY, spawnZ, EntityType.PRIMED_TNT);
			}
			
			
			return true;
		}
		return false;
	}
}
