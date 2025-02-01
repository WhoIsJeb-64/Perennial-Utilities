package org.perennial.utils.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.perennial.utils.PUtils;
import org.perennial.utils.data.PUtilsConfig;

public class PlayerKick implements Listener {
    private PUtils plugin;
    private PUtilsConfig config;

    // Constructor to link the plugin instance
    public PlayerKick(PUtils plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @EventHandler(priority = Event.Priority.High)
    public void onPlayerKick(PlayerKickEvent event) {

        //Kick message
        String message = plugin.getConfig().getConfigString("settings.kick-message");
        message = message.replace("%player%", event.getPlayer().getName());
        event.setLeaveMessage((message));
    }
}
