package org.perennial.utils.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.perennial.utils.PUtils;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PUtils.userdata;

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

        userdata.save();

        //Join message
        String playerName = event.getPlayer().getName();
        String message = userdata.getDataString(playerName + ".join-message");
        message = message.replace("%p%", playerName);
        event.setJoinMessage((message));

        userdata.generateDataEntry(playerName + ".stats.time-played", 0);
        userdata.generateDataEntry(playerName + ".stats.blocks-broken", 0);
        userdata.generateDataEntry(playerName + ".stats.blocks-placed", 0);
        userdata.generateDataEntry(playerName + ".stats.messages-sent", 0);
        userdata.generateDataEntry(playerName + ".join-message", config.defaultJoinMessage());
        userdata.generateDataEntry(playerName + ".join-message", config.defaultQuitMessage());

        sessionStart = System.currentTimeMillis() / 1000;
        userdata.setProperty(playerName + ".stats.session-start", sessionStart);
    }
}
