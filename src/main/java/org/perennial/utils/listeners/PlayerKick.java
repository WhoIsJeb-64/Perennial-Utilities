package org.perennial.utils.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.perennial.utils.PUtils;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PUtils.getStats;
import static org.perennial.utils.PUtils.statistics;
import static org.perennial.utils.listeners.PlayerJoin.getStartTime;

public class PlayerKick implements Listener {
    private PUtils plugin;
    private PUtilsConfig config;
    long startTime = getStartTime();

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

        String playerName = event.getPlayer().getName();
        long endTime = System.currentTimeMillis() / 1000;
        long gainedTime = endTime - startTime;
        statistics.setProperty(playerName + ".time-played", Long.sum(getStats().getStatLong(playerName + ".time-played"), gainedTime));
        statistics.save();
    }
}
