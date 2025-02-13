package org.perennial.utils.commands;

import com.earth2me.essentials.api.UserDoesNotExistException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.perennial.utils.PUtils;
import org.perennial.utils.data.PUtilsConfig;
import com.earth2me.essentials.api.Economy;

import java.text.DecimalFormat;

import static org.perennial.utils.PUtils.userdata;

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

        //Convert playtime to hours and add commas to blocks broken/placed and messages sent
        long hoursPlayed = userdata.secondsToHours(subject + ".stats.time-played");
        DecimalFormat formatter = new DecimalFormat("#,###");
        String blocksBroken = formatter.format(userdata.getDataInt(subject + ".stats.blocks-broken"));
        String blocksPlaced = formatter.format(userdata.getDataInt(subject + ".stats.blocks-placed"));

        sender.sendMessage("§6==========§e " + subject + "'s Stats: §6==========");
        sender.sendMessage("§7» §2Balance:§a $" + balance);
        sender.sendMessage("§7» §9Time Played:§3 " + hoursPlayed + "h");
        sender.sendMessage("§7» §4Blocks Broken/Placed:§c " + blocksBroken + "§4 / §c" + blocksPlaced);
        sender.sendMessage("§7» §5Messages Sent:§d " + blocksPlaced);
        return true;
    }
}
