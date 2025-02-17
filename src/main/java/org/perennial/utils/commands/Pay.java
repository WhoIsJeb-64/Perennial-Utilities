package org.perennial.utils.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.perennial.utils.PerennialUtilities;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PerennialUtilities.userdata;

public class Pay implements CommandExecutor {

    private final PerennialUtilities plugin;

    private final PUtilsConfig config;

    public Pay(PerennialUtilities plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("putils.pay") && !sender.isOp()) {
            sender.sendMessage("§cYou do not have permission to execute this command.");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("§c/pay <player> <amount>");
            return true;
        }
        String subject = args[0];
        String user = sender.getName();
        double amount = Double.parseDouble(args[1]);
        if (amount > userdata.getDataDouble(user + ".stats.balance") && amount < 0) {
            sender.sendMessage("§cInvalid pay amount!");
            return true;
        }
        if (user.equalsIgnoreCase(subject)) {
            sender.sendMessage("§aSuccessfully paid §2" + subject + " §a$" + amount + ".");
            return true;
        }
        amount = userdata.roundToHundredths(amount);

        double newSubjectBal = userdata.modifyDataDouble(subject + ".stats.balance", amount);
        double newSenderBal = userdata.modifyDataDouble(subject + ".stats.balance", (amount * -1));

        userdata.setProperty(subject + ".stats.balance", newSubjectBal);
        userdata.setProperty(user + ".stats.balance", newSenderBal);

        sender.sendMessage("§aSuccessfully paid §2" + subject + " §a$" + amount + ".");
        Player player = Bukkit.getPlayerExact(subject);
        if (player != null) {
            player.sendMessage("§2" + user + " §ahas paid you $" + amount + ".");
        }
        return true;
    }
}
