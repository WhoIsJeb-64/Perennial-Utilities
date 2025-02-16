package org.perennial.utils.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.perennial.utils.PerennialUtilities;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PerennialUtilities.userdata;

public class PlayerMove implements Listener {

    private PerennialUtilities plugin;
    private PUtilsConfig config;

    public PlayerMove(PerennialUtilities plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @EventHandler(priority = Event.Priority.Highest)
    public void onPlayerMove(PlayerMoveEvent event) {

        String playerName = event.getPlayer().getName();
        if (plugin.mustRegister(playerName)) {
            event.setTo(event.getFrom());
            Player player = event.getPlayer();
            player.sendMessage("§cYou need to register! §a/register <password>");
        }

        if (userdata.getDataBool(playerName + ".must-login")) {
            event.setTo(event.getFrom());
            Player player = event.getPlayer();
            player.sendMessage("§cYou need to login! §a/login <password>");
        }
    }
}
