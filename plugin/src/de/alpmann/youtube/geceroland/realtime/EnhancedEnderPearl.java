package de.alpmann.youtube.geceroland.realtime;

import java.util.List;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class EnhancedEnderPearl extends RealtimeBase {
	
	@Override
	public void update(Server server) {
		
		List<World> worlds = server.getWorlds();
		for(int i = 0; i < worlds.size(); i++) {
			World world = worlds.get(i);
			List<Entity> entities = world.getEntities();
			
			for(int j = 0; j < entities.size(); j++) {
				Entity entity = entities.get(j);
				if(entity.getType() != EntityType.ENDER_PEARL)
					continue;
				
				EnderPearl pearl = (EnderPearl)entity;
				Player shooter = (Player)pearl.getShooter();
				
				if(shooter.isSneaking() &&
				   shooter.getVehicle().getType() == EntityType.ENDER_PEARL) {
					// if player is leaving "vehicle"
					pearl.remove();
				}
				
				if(pearl.isOnGround() == false &&
				   shooter.hasPermission("enderpearlride") &&
				   shooter.isInsideVehicle() == false)
					pearl.addPassenger(shooter);
				
			} // entity loop
		} // world loop
		
	} // void update
	
}
