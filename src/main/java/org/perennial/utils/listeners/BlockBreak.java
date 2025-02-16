package org.perennial.utils.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.perennial.utils.PerennialUtilities;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PerennialUtilities.userdata;

public class BlockBreak  implements Listener {
    private PerennialUtilities plugin;
    private PUtilsConfig config;

    public BlockBreak(PerennialUtilities plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @EventHandler(priority = Event.Priority.Highest)
    public void onBlockBreak(BlockBreakEvent event) {

        String playerName = event.getPlayer().getName();
        String key = playerName + ".stats.blocks-broken";
        userdata.setProperty(key, userdata.incrementDataInt(key));
        userdata.save();
    }
}
