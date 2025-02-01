package org.perennial.utils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.perennial.utils.PUtils;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PUtils.statistics;

public class Playtime implements CommandExecutor {

    private final PUtils plugin;

    private final PUtilsConfig config;

    public Playtime(PUtils plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("putils.stats") && !sender.isOp()) {
            sender.sendMessage("§cYou do not have permission to execute this command.");
            return true;
        }

        statistics.save();
        sender.sendMessage("§b" + sender.getName() + "'s§c Time Played:§3 " + statistics.getStatString(sender.getName() + ".time-played") + "h");
        return true;
    }
}
