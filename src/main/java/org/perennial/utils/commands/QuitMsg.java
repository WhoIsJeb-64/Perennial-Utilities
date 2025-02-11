package org.perennial.utils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.perennial.utils.PUtils;
import org.perennial.utils.data.PUtilsConfig;

import static org.perennial.utils.PUtils.userdata;

public class QuitMsg implements CommandExecutor {

    private final PUtils plugin;

    private final PUtilsConfig config;

    public QuitMsg(PUtils plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("putils.custom-quitmsg") && !sender.isOp()) {
            sender.sendMessage("§cYou do not have permission to execute this command.");
            return true;
        }

        String subject = sender.getName();
        if (args.length < 1) {
            sender.sendMessage("§cNo arguments!");
            return true;
        }
        String key = subject + ".quit-message";

        if (args[0].equalsIgnoreCase("clear")) {

            if (userdata.getProperty(key) == null) {
                sender.sendMessage("§cYou had no custom quit message to clear!");
                return true;
            }

            userdata.setProperty(key, config.defaultQuitMessage());
            userdata.save();
            sender.sendMessage("§aCustom quit message cleared successfully!");
            return true;
        }

        if (args[0].equalsIgnoreCase("set")) {

            if (args[1].isEmpty()) {
                sender.sendMessage("§cInvalid argument!");
                return true;
            } else {
                args[1] = args[1].replaceAll("&", "§");
                String joinMessage = args[1];
                userdata.setProperty(key, joinMessage);
                userdata.save();
            }
            sender.sendMessage("§aCustom quit message set successfully!");
            return true;
        }

        if (args[0].equalsIgnoreCase("view")) {
            PUtils.userdata.reload();
            String viewMsg = userdata.getDataString(key).replace("%p%", subject);
            sender.sendMessage(userdata.getDataString(key));
            return true;
        }

        if (args[0].equalsIgnoreCase("?")) {
            sender.sendMessage("§2================ §a/quitmsg help §2================");
            sender.sendMessage("§7The §a/quitmsg§7 command allows you to set a quit message!");
            sender.sendMessage("§7For your name, use §a%p%§7, and it will appear as your username.");
            return true;
        }

        sender.sendMessage("§c/quitmsg <set|view|clear>");
        return true;
    }
}
