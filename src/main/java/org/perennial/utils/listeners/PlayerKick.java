package org.perennial.utils.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.perennial.utils.PUtils;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PUtils.statistics;

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

        String playerName = event.getPlayer().getName();

        //Kick message
        String message = plugin.getConfig().getConfigString("kick-message");
        message = message.replace("%p%", event.getPlayer().getName());
        event.setLeaveMessage((message));

        //Update player's playtime
        String key = playerName + ".time-played";
        long timePlayed = statistics.getStatLong(key);
        long sessionStart = statistics.getStatLong(playerName + ".session-start");
        long sessionEnd = System.currentTimeMillis() / 1000;
        long timeElapsed = sessionEnd - sessionStart;
        statistics.setProperty(key, timePlayed + timeElapsed);
        statistics.save();

    }
}
