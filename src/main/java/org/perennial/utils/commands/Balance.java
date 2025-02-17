package org.perennial.utils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.perennial.utils.PerennialUtilities;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PerennialUtilities.userdata;

public class Balance implements CommandExecutor {

    private final PerennialUtilities plugin;

    private final PUtilsConfig config;

    public Balance(PerennialUtilities plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("putils.balance") && !sender.isOp()) {
            sender.sendMessage("§cYou do not have permission to execute this command.");
            return true;
        }

        String subject; //Game assumes that the subject is the sender if none is specified
        if (args.length < 1) {
            subject = sender.getName();
        } else {
            subject = args[0];
        }

        double balance = userdata.getDataDouble(subject + ".stats.balance");
        sender.sendMessage("§2" + subject + "'s§a balance: $" + balance);
        return true;
    }
}
