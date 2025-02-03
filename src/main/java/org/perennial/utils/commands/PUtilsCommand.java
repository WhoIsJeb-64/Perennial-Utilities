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
            sender.sendMessage("§2======= §aPerennial-Utilities v1.2 §2=======");
            sender.sendMessage("§2» §aAuthor:§7 WhoIsJeb");
            sender.sendMessage("§2» §7Use §a/putils ?§7 for a list of its commands.");
            return true;
        }

        if (args[0].equalsIgnoreCase("?")) {
            sender.sendMessage("§a/colors§7 Lists the 16 text colors and their codes.");
            sender.sendMessage("§a/discord§7 Links to the server's discord.");
            sender.sendMessage("§a/map§7 Lists to the server's dynmap.");
            sender.sendMessage("§a/playtime§7 Prints the playtime of the sender or another user.");
            sender.sendMessage("§a/stats§7 Lists some stats of the sender or another user.");
            sender.sendMessage("§a/seen§7 Says when a player was last seen online in GMT.");
            return true;
        }

        if (args[0].equalsIgnoreCase("config")) {
            if (!sender.hasPermission("putils.debug") && !sender.isOp()) {
                sender.sendMessage("§cYou do not have permission to execute this sub-command!");
                return true;
            }

            if (args[1].equalsIgnoreCase("set")) {

                String targetProperty = args[2];
                if (args[2].isEmpty()) {
                    sender.sendMessage("§cSelect a property to modify!");
                    return true;
                }
                String targetValue = args[3];
                if (args[3].isEmpty()) {
                    sender.sendMessage("§cSpecify a value to set§4 " + targetProperty + "§c to!");
                    return true;
                }

                config.setProperty(targetProperty, targetValue);
                config.reload();
                sender.sendMessage("§aValue§2 " + targetProperty + "§a set to§2 " + targetValue + "!");

                return true;
            }

            sender.sendMessage("§cInvalid or no subargument!");
            return true;
        }

        sender.sendMessage("§cInvalid argument!");

        return true;
    }
}
