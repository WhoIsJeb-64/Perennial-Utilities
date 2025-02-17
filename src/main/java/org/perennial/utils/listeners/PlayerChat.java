package org.perennial.utils.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.perennial.utils.PerennialUtilities;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PerennialUtilities.userdata;

public class PlayerChat implements Listener {

    private PerennialUtilities plugin;
    private PUtilsConfig config;

    public PlayerChat(PerennialUtilities plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @EventHandler(priority = Event.Priority.Highest)
    public void onPlayerChat(PlayerChatEvent event) {

        String playerName = event.getPlayer().getName();
        String key = playerName + ".stats.messages-sent";
        userdata.modifyDataInt(key, 1);
        userdata.save();
    }
}
