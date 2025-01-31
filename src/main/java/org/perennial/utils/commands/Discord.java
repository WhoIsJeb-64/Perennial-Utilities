package org.perennial.utils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.perennial.utils.data.PUtilsConfig;
import org.perennial.utils.PUtils;

public class Discord implements CommandExecutor {

    private final PUtils plugin;

    private final PUtilsConfig config;

    public Discord(PUtils plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("putils.discord") && !sender.isOp()) {
            sender.sendMessage("§cYou do not have permission to execute this command.");
            return true;
        }

        String response = config.getConfigString("settings.discord-command.response.value");
        sender.sendMessage(response);
        return true;
    }
}
