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
    public static long sessionStart;

    public PlayerJoin(PUtils plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @EventHandler(priority = Event.Priority.Highest)
    public void onPlayerJoin(PlayerJoinEvent event) {

        //Join message
        String playerName = event.getPlayer().getName();
        String message = plugin.getConfig().getConfigString("join-message");
        message = message.replace("%p%", playerName);
        event.setJoinMessage((message));

        statistics.generateStatEntry(playerName + ".time-played", 0);
        statistics.generateStatEntry(playerName + ".blocks-broken", 0);
        statistics.generateStatEntry(playerName + ".blocks-placed", 0);

        sessionStart = System.currentTimeMillis() / 1000;
        statistics.setProperty(playerName + ".session-start", sessionStart);
    }
}
