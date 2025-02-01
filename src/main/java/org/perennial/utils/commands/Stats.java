package org.perennial.utils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.perennial.utils.PUtils;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PUtils.statistics;
import static org.perennial.utils.listeners.BlockBreak.getBlocksBroken;
import static org.perennial.utils.listeners.BlockPlace.getBlocksPlaced;
import static org.perennial.utils.listeners.PlayerQuit.getTimePlayed;

public class Stats implements CommandExecutor {

    private final PUtils plugin;

    private final PUtilsConfig config;

    public Stats(PUtils plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("putils.stats") && !sender.isOp()) {
            sender.sendMessage("§cYou do not have permission to execute this command.");
            return true;
        }

        statistics.save();
        long getTimePlayed = getTimePlayed() / 3600;
        int getBlocksBroken = getBlocksBroken();
        int getBlocksPlaced = getBlocksPlaced();
        sender.sendMessage("§3=======§b " + sender.getName() + "'s Stats: §3=======");
        sender.sendMessage("§4» §cTime Played:§3 " + getTimePlayed + "h");
        sender.sendMessage("§4» §cBlocks Broken:§3 " + getBlocksBroken());
        sender.sendMessage("§4» §cBlocks Placed:§3 " + getBlocksPlaced());
        return true;
    }
}
