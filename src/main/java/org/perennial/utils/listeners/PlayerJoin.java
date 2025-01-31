package org.perennial.utils.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.perennial.utils.PUtils;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PUtils.statistics;

public class PlayerJoin implements Listener {
    private PUtils plugin;
    private PUtilsConfig config;
    public static long startTime = System.currentTimeMillis() / 1000;

    public PlayerJoin(PUtils plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @EventHandler(priority = Event.Priority.Highest)
    public void onPlayerJoin(PlayerJoinEvent event) {

        //Join message
        String playerName = event.getPlayer().getName();
        String message = plugin.getConfig().getConfigString("settings.join-message.value");
        message = message.replace("%player%", playerName);
        event.setJoinMessage((message));

        statistics.save();
    }

    public static long getStartTime() {
        return startTime;
    }
}
