package org.perennial.utils.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.perennial.utils.PUtils;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PUtils.getStats;
import static org.perennial.utils.PUtils.statistics;

public class BlockPlace implements Listener {
    private PUtils plugin;
    private PUtilsConfig config;
    public static int blocksPlaced = 0;

    public BlockPlace(PUtils plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @EventHandler(priority = Event.Priority.Highest)
    public void onBlockPlace(BlockPlaceEvent event) {

        //Increment the player´s blocks-placed count
        String playerName = event.getPlayer().getName();
        blocksPlaced = blocksPlaced + 1;
        getStats().setProperty(playerName + ".blocks-placed", blocksPlaced);
        statistics.save();
    }

    public static Integer getBlocksPlaced() {
        return blocksPlaced;
    }
}
