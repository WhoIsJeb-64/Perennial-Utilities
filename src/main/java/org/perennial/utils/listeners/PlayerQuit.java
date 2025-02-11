package org.perennial.utils.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.perennial.utils.PUtils;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PUtils.userdata;

public class PlayerQuit implements Listener {
    private PUtils plugin;
    private PUtilsConfig config;

    public PlayerQuit(PUtils plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @EventHandler(priority = Event.Priority.Highest)
    public void onPlayerQuit(PlayerQuitEvent event) {

        //Quit message
        String playerName = event.getPlayer().getName();
        String defaultQuitMessage = plugin.getConfig().getConfigString("quit-message");
        String message = userdata.getDataString(playerName + ".quit-message");
        message = message.replace("%p%", playerName);
        event.setQuitMessage((message));

        //Update player's playtime
        String key = playerName + ".stats.time-played";
        long timePlayed = userdata.getDataLong(key);
        long sessionStart = userdata.getDataLong(playerName + ".stats.session-start");
        long sessionEnd = System.currentTimeMillis() / 1000;
        long timeElapsed = sessionEnd - sessionStart;
        userdata.setProperty(key, timePlayed + timeElapsed);

        //Set last time they were seen
        long lastSeen = System.currentTimeMillis() / 1000;
        userdata.setProperty(playerName + ".stats.last-seen", lastSeen);
        userdata.save();
    }
}
