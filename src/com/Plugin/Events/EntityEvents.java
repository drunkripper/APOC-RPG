package com.Plugin.Events;

//import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EntityEvents implements Listener {
	
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent Event) {
		//LivingEntity Entity = Event.getEntity();
	}
}