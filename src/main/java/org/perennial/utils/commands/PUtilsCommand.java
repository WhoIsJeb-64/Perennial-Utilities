package org.perennial.utils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.perennial.utils.PUtils;
import org.perennial.utils.data.PUtilsConfig;

public class PUtilsCommand implements CommandExecutor {

    private final PUtils plugin;

    private final PUtilsConfig config;

    public PUtilsCommand(PUtils plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§2========== §aPerennial-Utilities v1.2.0 §2==========");
            sender.sendMessage("§2» §aAuthor:§7 WhoIsJeb");
            sender.sendMessage("§2» §7Use §a/putils ?§7 for a list of its commands.");
            return true;
        }

        if (args[0].equalsIgnoreCase("?")) {
            sender.sendMessage("§2=============== §aPUtils Commands §2===============");
            if (sender.hasPermission("putils.colors") || sender.isOp()) {
                sender.sendMessage("§a/colors§7 Lists the 16 text colors and their codes.");
            }
            if (sender.hasPermission("putils.discord") || sender.isOp()) {
                sender.sendMessage("§a/discord§7 Links to the server's discord.");
            }
            if (sender.hasPermission("putils.map") || sender.isOp()) {
                sender.sendMessage("§a/map§7 Lists to the server's dynmap.");
            }
            if (sender.hasPermission("putils.stats") || sender.isOp()) {
                sender.sendMessage("§a/playtime§7 Prints the playtime of the sender or another user.");
                sender.sendMessage("§a/stats§7 Lists some stats of the sender or another user.");
            }
            if (sender.hasPermission("putils.seen") || sender.isOp()) {
                sender.sendMessage("§a/seen§7 Says when a player was last seen online in GMT.");
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("config")) {
            if (!sender.hasPermission("putils.debug") && !sender.isOp()) {
                sender.sendMessage("§cYou do not have permission to execute this sub-command!");
                return true;
            }

            if (args[1].equalsIgnoreCase("set")) {

                if (args[2].isEmpty() || args[3].isEmpty()) {
                    sender.sendMessage("§c/putils config set <property> <value>");
                    return true;
                }
                String targetProperty = args[2];
                String targetValue = args[3];

                if (config.getConfigOption(targetProperty) == null) {
                    sender.sendMessage("§cProperty§4 " + targetProperty + "§c does not exist!");
                    return true;
                }

                config.setProperty(targetProperty, targetValue);
                PUtils.configuration.save();
                sender.sendMessage("§aValue§2 " + targetProperty + "§a set to§2 " + targetValue + "§a!");
                return true;
            }

            if (args[1].equalsIgnoreCase("reload")) {
                PUtils.configuration.reload();
                sender.sendMessage("§aPerennial-Utilities config reloaded successfully!");
                return true;
            }

            sender.sendMessage("§cNot enough arguments!");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("putils.debug") && !sender.isOp()) {
                sender.sendMessage("§cYou do not have permission to execute this sub-command!");
                return true;
            }

            PUtils.configuration.reload();
            PUtils.statistics.reload();
            sender.sendMessage("§aPerennial-Utilities reloaded successfully!");

            return true;
        }

        sender.sendMessage("§cInvalid argument!");
        return true;
    }
}
