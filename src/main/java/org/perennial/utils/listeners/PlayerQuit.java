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
    public static long lastSeen = System.currentTimeMillis();

    public PlayerQuit(PUtils plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @EventHandler(priority = Event.Priority.Highest)
    public void onPlayerQuit(PlayerQuitEvent event) {

        //Quit message
        String message = plugin.getConfig().getConfigString("quit-message");
        message = message.replace("%p%", event.getPlayer().getName());
        event.setQuitMessage((message));

        String playerName = event.getPlayer().getName();
        statistics.iteratePlaytime(playerName + ".time-played");
    }

    public static long getLastSeen() {
        return lastSeen;
    }
}
