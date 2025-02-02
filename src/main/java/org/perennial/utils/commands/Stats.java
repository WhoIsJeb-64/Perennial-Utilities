package org.perennial.utils.commands;

import com.earth2me.essentials.api.UserDoesNotExistException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.perennial.utils.PUtils;
import org.perennial.utils.data.PUtilsConfig;
import com.earth2me.essentials.api.Economy;
import java.math.RoundingMode;
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

        if (args.length < 1) {

            statistics.save();
            DecimalFormat df = new DecimalFormat("##.##");
            df.setRoundingMode(RoundingMode.DOWN);
            float hoursPlayed = Float.parseFloat(df.format((float) statistics.getStatLong(sender.getName() + ".time-played") / 3600));

            double balance;
            try {
                balance = Economy.getMoney(sender.getName());
            } catch (UserDoesNotExistException e) {
                throw new RuntimeException(e);
            }
            sender.sendMessage("§6========§e " + sender.getName() + "'s Stats: §6========");
            sender.sendMessage("§7» §2Balance:§a $" + balance);
            sender.sendMessage("§7» §9Time Played:§3 " + hoursPlayed + "h");
            sender.sendMessage("§7» §5Blocks Broken:§d " + statistics.getStatString(sender.getName() + ".blocks-broken"));
            sender.sendMessage("§7» §4Blocks Placed:§c " + statistics.getStatString(sender.getName() + ".blocks-placed"));
            return true;
        }

        String subject = args[0];

        statistics.save();
        DecimalFormat df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.DOWN);
        float hoursPlayed = Float.parseFloat(df.format((float) statistics.getStatLong(subject + ".time-played") / 3600L));

        double balance;
        try {
            balance = Economy.getMoney(subject);
        } catch (UserDoesNotExistException e) {
            throw new RuntimeException(e);
        }

        sender.sendMessage("§6========§e " + subject + "'s Stats: §6========");
        sender.sendMessage("§7» §2Balance:§a $" + balance);
        sender.sendMessage("§7» §9Time Played:§3 " + hoursPlayed + "h");
        sender.sendMessage("§7» §5Blocks Broken:§d " + statistics.getStatString(subject + ".blocks-broken"));
        sender.sendMessage("§7» §4Blocks Placed:§c " + statistics.getStatString(subject + ".blocks-placed"));
        return true;
    }
}
