package org.perennial.utils.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.perennial.utils.PerennialUtilities;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PerennialUtilities.userdata;

public class PlayerQuit implements Listener {
    private PerennialUtilities plugin;
    private PUtilsConfig config;

    public PlayerQuit(PerennialUtilities plugin) {
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
        userdata.updatePlaytime(playerName);

        //Set last time they were seen
        long lastSeen = System.currentTimeMillis() / 1000;
        userdata.setProperty(playerName + ".stats.last-seen", lastSeen);
        userdata.save();
    }
}
