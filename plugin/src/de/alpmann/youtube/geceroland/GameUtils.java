package de.alpmann.youtube.geceroland;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.util.Vector;

public class GameUtils {
	public static Vector vectorWest = new Vector(-1, 0, 0);
	public static Vector vectorEast = new Vector(1, 0, 0);
	public static Vector vectorSouth = new Vector(0, 0, 1);
	public static Vector vectorNorth = new Vector(0, 0, -1);
	
	
	public static void translatePlayer(Player player, float x, float y, float z) {
		Location pos = player.getLocation();
		pos.add(x, y, z);
		player.teleport(pos);
	}
	
	public static void setPlayerRelativeBlock(Player player, float x, float y, float z, Material mat) {
		World world = player.getWorld();
		Location pos = player.getLocation();
		world.getBlockAt(pos.add(x, y, z)).setType(mat);
	}
	
	public static Entity spawnPlayerRelativeEntity(Player player, float x, float y, float z, EntityType entity) {
		World world = player.getWorld();
		Location pos = player.getLocation();
		Entity ent;
		if(entity != EntityType.PRIMED_TNT) {
			ent = world.spawnEntity(pos.add(x, y, z), entity);
		} else {
			ent = world.spawnEntity(pos.add(x, y,z ), EntityType.PRIMED_TNT);
			((TNTPrimed)ent).setFuseTicks(0);
		}
		
		return ent;
	}
}
