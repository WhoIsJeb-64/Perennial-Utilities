package org.perennial.utils.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.perennial.utils.PUtils;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PUtils.getStats;
import static org.perennial.utils.PUtils.statistics;

public class BlockBreak  implements Listener {
    private PUtils plugin;
    private PUtilsConfig config;

    public BlockBreak(PUtils plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @EventHandler(priority = Event.Priority.Highest)
    public void onBlockBreak(BlockBreakEvent event) {

        String playerName = event.getPlayer().getName();
        statistics.setProperty(playerName + ".blocks-broken", Integer.sum(getStats().getStatInteger(playerName + ".blocks-broken"), 1));
        statistics.save();
    }
}
