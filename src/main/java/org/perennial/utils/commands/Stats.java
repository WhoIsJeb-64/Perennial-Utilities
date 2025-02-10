package org.perennial.utils.commands;

import com.earth2me.essentials.api.UserDoesNotExistException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.perennial.utils.PUtils;
import org.perennial.utils.data.PUtilsConfig;
import com.earth2me.essentials.api.Economy;

import java.text.DecimalFormat;

import static org.perennial.utils.PUtils.statistics;

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

        String subject; //Game assumes that the subject is the sender if none is specified
        if (args.length < 1) {
            subject = sender.getName();
        } else {
            subject = args[0];
        }

        double balance;
        try {
            balance = Economy.getMoney(subject);
        } catch (UserDoesNotExistException e) {
            throw new RuntimeException(e);
        }

        //Convert playtime to hours and add commas to blocks broken/placed
        long hoursPlayed = statistics.secondsToHours(subject + ".time-played");
        DecimalFormat formatter = new DecimalFormat("#,###");
        String blocksBroken = formatter.format(statistics.getStatInteger(subject + ".blocks-broken"));
        String blocksPlaced = formatter.format(statistics.getStatInteger(subject + ".blocks-placed"));

        sender.sendMessage("§6==========§e " + subject + "'s Stats: §6==========");
        sender.sendMessage("§7» §2Balance:§a $" + balance);
        sender.sendMessage("§7» §9Time Played:§3 " + hoursPlayed + "h");
        sender.sendMessage("§7» §5Blocks Broken:§d " + blocksBroken);
        sender.sendMessage("§7» §4Blocks Placed:§c " + blocksPlaced);
        return true;
    }
}
