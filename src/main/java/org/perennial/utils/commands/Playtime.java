package org.perennial.utils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.perennial.utils.PUtils;
import org.perennial.utils.data.PUtilsConfig;
import java.math.RoundingMode;
import java.text.DecimalFormat;

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

        if (args.length < 1) {

            statistics.save();
            DecimalFormat df = new DecimalFormat("##.##");
            df.setRoundingMode(RoundingMode.DOWN);
            float hoursPlayed = Float.parseFloat(df.format((float) statistics.getStatLong(sender.getName() + ".time-played") / 3600));

            sender.sendMessage("§b" + sender.getName() + "'s §cTime Played:§3 " + hoursPlayed + "h");
            return true;
        }

        String subject = args[0];

        statistics.save();
        DecimalFormat df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.DOWN);
        float hoursPlayed = Float.parseFloat(df.format((float) statistics.getStatLong(subject + ".time-played") / 3600));

        sender.sendMessage("§b" + subject + "'s §cTime Played:§3 " + hoursPlayed + "h");
        return true;
    }
}
