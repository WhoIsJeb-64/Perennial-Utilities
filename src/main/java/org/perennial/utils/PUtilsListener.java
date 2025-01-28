package org.perennial.utils;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PUtilsListener implements Listener {
    private PUtils plugin;
    private PUtilsConfig config;

    // Constructor to link the plugin instance
    public PUtilsListener(PUtils plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    // Handle player join event
    @EventHandler(priority = Event.Priority.Highest)
    public void onPlayerJoin(PlayerJoinEvent event) {

        //Join message
        String message = plugin.getConfig().getConfigString("settings.join-message.value");
        message = message.replace("%player%", event.getPlayer().getName());
        event.setJoinMessage((message));
    }

    @EventHandler(priority = Event.Priority.High)
    public void onPlayerQuit(PlayerQuitEvent event) {

        //Quit message
        String message = plugin.getConfig().getConfigString("settings.quit-message.value");
        message = message.replace("%player%", event.getPlayer().getName());
        event.setQuitMessage((message));
    }

    @EventHandler(priority = Event.Priority.High)
    public void onPlayerKick(PlayerKickEvent event) {

        //Kick message
        String message = plugin.getConfig().getConfigString("settings.kick-message.value");
        message = message.replace("%player%", event.getPlayer().getName());
        event.setLeaveMessage((message));
    }
}
