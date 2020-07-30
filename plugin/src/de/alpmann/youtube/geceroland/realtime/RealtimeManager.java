package de.alpmann.youtube.geceroland.realtime;

import java.util.ArrayList;

import org.bukkit.Server;

public class RealtimeManager {
	static ArrayList<RealtimeBase> realtimeThings;
	
	static public void init() {
		realtimeThings = new ArrayList<RealtimeBase>();
	}
	
	static public void update(Server server) {
		for(int i = 0; i < realtimeThings.size(); i++) {
			realtimeThings.get(i).update(server);
		}
	}
	
	static public void register(RealtimeBase thing) {
		realtimeThings.add(thing);
	}
	
	static public ArrayList<RealtimeBase> getData() {
		return realtimeThings;
	}
}
