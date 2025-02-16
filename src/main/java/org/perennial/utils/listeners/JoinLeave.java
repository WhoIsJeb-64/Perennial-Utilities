package org.perennial.utils.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.perennial.utils.PerennialUtilities;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PerennialUtilities.userdata;

public class JoinLeave implements Listener {

    private PerennialUtilities plugin;
    private PUtilsConfig config;

    public JoinLeave(PerennialUtilities plugin) {
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

        userdata.startPlaytimeCounting(playerName);

        //Require that unregistered users register
        Player player = Bukkit.getPlayer(playerName);
        if (!plugin.mustRegister(playerName)) {
            userdata.setProperty(playerName + ".must-login", true);
        }
    }

    @EventHandler(priority = Event.Priority.Highest)
    public void onPlayerQuit(PlayerQuitEvent event) {

        //Quit message
        String playerName = event.getPlayer().getName();
        String defaultQuitMessage = plugin.getConfig().getConfigString("quit-message");
        String message = userdata.getDataString(playerName + ".quit-message");
        message = message.replace("%p%", playerName);
        event.setQuitMessage((message));

        userdata.updatePlaytime(playerName);
        userdata.setLastSeen(playerName);
    }

    @EventHandler(priority = Event.Priority.High)
    public void onPlayerKick(PlayerKickEvent event) {

        String playerName = event.getPlayer().getName();

        //Kick message
        String message = plugin.getConfig().getConfigString("kick-message");
        message = message.replace("%p%", event.getPlayer().getName());
        event.setLeaveMessage((message));

        userdata.updatePlaytime(playerName);
        userdata.setLastSeen(playerName);
    }
}
