package org.perennial.utils.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.perennial.utils.PUtils;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PUtils.getStats;
import static org.perennial.utils.PUtils.statistics;
import static org.perennial.utils.listeners.PlayerJoin.getStartTime;

public class PlayerQuit implements Listener {
    private PUtils plugin;
    private PUtilsConfig config;
    public static long timePlayed = 0;

    public PlayerQuit(PUtils plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @EventHandler(priority = Event.Priority.High)
    public void onPlayerQuit(PlayerQuitEvent event) {

        //Quit message
        String message = plugin.getConfig().getConfigString("settings.quit-message.value");
        message = message.replace("%player%", event.getPlayer().getName());
        event.setQuitMessage((message));

        String playerName = event.getPlayer().getName();
        long endTime = System.currentTimeMillis() / 1000;
        timePlayed = endTime - getStartTime();
        getStats().setProperty(playerName + ".time-played", timePlayed);
        statistics.save();
    }

    public static long getTimePlayed() {
        return timePlayed;
    }
}
