package org.perennial.utils.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.perennial.utils.PUtils;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PUtils.statistics;

public class PlayerQuit implements Listener {
    private PUtils plugin;
    private PUtilsConfig config;

    public PlayerQuit(PUtils plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @EventHandler(priority = Event.Priority.Highest)
    public void onPlayerQuit(PlayerQuitEvent event) {

        String playerName = event.getPlayer().getName();

        //Quit message
        String message = plugin.getConfig().getConfigString("quit-message");
        message = message.replace("%p%", playerName);
        event.setQuitMessage((message));

        //Update player's playtime
        String key = playerName + ".time-played";
        long timePlayed = statistics.getStatLong(key);
        long sessionStart = statistics.getStatLong(playerName + ".session-start");
        long sessionEnd = System.currentTimeMillis() / 1000;
        long timeElapsed = sessionEnd - sessionStart;
        statistics.setProperty(key, timePlayed + timeElapsed);

        //Set last time they were seen
        long lastSeen = System.currentTimeMillis() / 1000;
        statistics.setProperty(playerName + ".last-seen", lastSeen);
        statistics.save();
    }
}
