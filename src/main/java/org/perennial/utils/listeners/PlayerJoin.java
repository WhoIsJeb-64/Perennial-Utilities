package org.perennial.utils.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.perennial.utils.PerennialUtilities;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PerennialUtilities.userdata;

public class PlayerJoin implements Listener {
    private PerennialUtilities plugin;
    private PUtilsConfig config;

    public PlayerJoin(PerennialUtilities plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @EventHandler(priority = Event.Priority.Highest)
    public void onPlayerJoin(PlayerJoinEvent event) {

        String playerName = event.getPlayer().getName();
        userdata.save();

        //Generate any data entries that don't yet exist with their default values
        userdata.write(playerName);

        //Join message
        String message = userdata.getDataString(playerName + ".join-message");
        message = message.replace("%p%", playerName);
        event.setJoinMessage((message));

        //Record play session's start for calculating playtime
        userdata.startPlaytimeCounting(playerName);

        //Require that unregistered users register
        Player player = Bukkit.getPlayer(playerName);
        if (!plugin.mustRegister(playerName)) {
            userdata.setProperty(playerName + ".must-login", true);
        }
    }
}
