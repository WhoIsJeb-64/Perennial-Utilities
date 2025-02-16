package org.perennial.utils.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.perennial.utils.PerennialUtilities;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PerennialUtilities.userdata;

public class PlayerKick implements Listener {
    private PerennialUtilities plugin;
    private PUtilsConfig config;

    // Constructor to link the plugin instance
    public PlayerKick(PerennialUtilities plugin) {
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
        String key = playerName + ".stats.time-played";
        long timePlayed = userdata.getDataLong(key);
        long sessionStart = userdata.getDataLong(playerName + ".stats.session-start");
        long sessionEnd = System.currentTimeMillis() / 1000;
        long timeElapsed = sessionEnd - sessionStart;
        userdata.setProperty(key, timePlayed + timeElapsed);
        userdata.save();

    }
}
