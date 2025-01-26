package org.perennial.utils;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PUtilsListener implements Listener {
    private PUtils plugin;
    private PUtilsConfig config;

    // Constructor to link the plugin instance
    public PUtilsListener(PUtils plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    // Handle player join event
    @EventHandler(priority = Event.Priority.Normal)
    public void onPlayerJoin(PlayerJoinEvent event) {
        //
    }
}
