package com.APOCRPG.Events;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
public class EffectPollingEvent extends Event {
	    private static final HandlerList handlers = new HandlerList();
	    private Player p;
	    public EffectPollingEvent(Player p) {
	       
	        this.p = p;
	    }
	    public String getMessage() {
	    	return "EffectPollingEvent Fired";
	    }
	    public Player getPlayer() {
	    	return p;
	    }
	    public HandlerList getHandlers() {
	        return handlers;
	    }
	 
	    public static HandlerList getHandlerList() {
	        return handlers;
	    }
	}

