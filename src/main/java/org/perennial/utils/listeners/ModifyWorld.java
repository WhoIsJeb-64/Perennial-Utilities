package org.perennial.utils.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.perennial.utils.PerennialUtilities;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PerennialUtilities.userdata;

public class ModifyWorld implements Listener {

    private PerennialUtilities plugin;
    private PUtilsConfig config;

    public ModifyWorld(PerennialUtilities plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @EventHandler(priority = Event.Priority.Highest)
    public void onBlockBreak(BlockBreakEvent event) {

        String playerName = event.getPlayer().getName();
        String key = playerName + ".stats.blocks-broken";
        userdata.setProperty(key, userdata.incrementDataInt(key, 1));
        userdata.save();
    }

    @EventHandler(priority = Event.Priority.Highest)
    public void onBlockPlace(BlockPlaceEvent event) {

        String playerName = event.getPlayer().getName();
        String key = playerName + ".stats.blocks-placed";
        userdata.setProperty(key, userdata.incrementDataInt(key, 1));
        userdata.save();
    }
}
