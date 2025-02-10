package org.perennial.utils.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.perennial.utils.PUtils;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PUtils.statistics;

public class Seen implements CommandExecutor {

    private final PUtils plugin;

    private final PUtilsConfig config;

    public Seen(PUtils plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("putils.seen") && !sender.isOp()) {
            sender.sendMessage("§cYou do not have permission to execute this command.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§cSpecify a player's username!");
            return true;
        }

        String subject = args[0];
        Player player = Bukkit.getPlayerExact(subject);
        if (player != null) {
            sender.sendMessage("§6" + subject + "§e is currently online, retard!");
            return true;
        }

        String lastSeen = statistics.seeLastSeen(subject + ".last-seen");
        sender.sendMessage("§6" + subject + "§e was last seen " + lastSeen + " ago.");

        return true;
    }
}
