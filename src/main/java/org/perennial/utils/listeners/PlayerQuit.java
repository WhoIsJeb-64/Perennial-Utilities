package org.perennial.utils.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.perennial.utils.PUtils;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PUtils.statistics;
import static org.perennial.utils.listeners.PlayerJoin.getStartTime;

public class PlayerQuit implements Listener {
    private PUtils plugin;
    private PUtilsConfig config;
    long startTime = getStartTime();
    public static long lastSeen = System.currentTimeMillis();

    public PlayerQuit(PUtils plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @EventHandler(priority = Event.Priority.Highest)
    public void onPlayerQuit(PlayerQuitEvent event) {

        //Quit message
        String message = plugin.getConfig().getConfigString("settings.quit-message");
        message = message.replace("%p%", event.getPlayer().getName());
        event.setQuitMessage((message));

        //Add length of play session to playtime and set last-seen
        String playerName = event.getPlayer().getName();
        long endTime = System.currentTimeMillis() / 1000L;
        long gainedTime = endTime - startTime;
        long timePlayed = statistics.getStatLong(playerName + ".time-played");
        statistics.setProperty(playerName + ".time-played", timePlayed + gainedTime);
        statistics.setProperty(playerName + ".last-seen", lastSeen);
        statistics.save();
    }

    public static long getLastSeen() {
        return lastSeen;
    }
}
