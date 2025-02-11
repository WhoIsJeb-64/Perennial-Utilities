package org.perennial.utils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.perennial.utils.PUtils;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PUtils.userdata;

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

        String subject; //Game assumes that the subject is the sender if none is specified
        if (args.length < 1) {
            subject = sender.getName();
        } else {
            subject = args[0];
        }

        long hoursPlayed = userdata.secondsToHours(subject + ".stats.time-played");

        sender.sendMessage("§9" + subject + "'s §9Time Played:§3 " + hoursPlayed + "h");
        return true;
    }
}
