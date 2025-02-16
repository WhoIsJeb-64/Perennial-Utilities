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
    public static long sessionStart;

    public PlayerJoin(PerennialUtilities plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @EventHandler(priority = Event.Priority.Highest)
    public void onPlayerJoin(PlayerJoinEvent event) {

        String playerName = event.getPlayer().getName();
        userdata.save();

        //Generate any data entries that don't yet exist with their default values
        userdata.generateDataEntry(playerName + ".stats.time-played", 0);
        userdata.generateDataEntry(playerName + ".stats.blocks-broken", 0);
        userdata.generateDataEntry(playerName + ".stats.blocks-placed", 0);
        userdata.generateDataEntry(playerName + ".stats.messages-sent", 0);
        userdata.generateDataEntry(playerName + ".join-message", config.defaultJoinMessage());
        userdata.generateDataEntry(playerName + ".quit-message", config.defaultQuitMessage());
        userdata.generateDataEntry(playerName + ".password", null);

        //Join message
        String message = userdata.getDataString(playerName + ".join-message");
        message = message.replace("%p%", playerName);
        event.setJoinMessage((message));

        //Record play session's start for calculating playtime
        sessionStart = System.currentTimeMillis() / 1000;
        userdata.setProperty(playerName + ".stats.session-start", sessionStart);

        //Require that unregistered users register
        Player player = Bukkit.getPlayer(playerName);
        if (plugin.mustRegister(playerName)) {
            player.sendMessage("§cYou must register! §a/register <password>");
        } else {
            userdata.setProperty(playerName + ".must-login", true);
            player.sendMessage("§cYou must login! §a/login <password>");
        }
    }
}
