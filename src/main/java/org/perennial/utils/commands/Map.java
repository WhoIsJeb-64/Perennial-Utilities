package org.perennial.utils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.perennial.utils.data.PUtilsConfig;
import org.perennial.utils.PUtils;

public class Map implements CommandExecutor {

    private final PUtils plugin;

    private final PUtilsConfig config;

    public Map(PUtils plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("putils.map") && !sender.isOp()) {
            sender.sendMessage("§cYou do not have permission to execute this command.");
            return true;
        }

        String response = config.getConfigString("settings.map-command.response");
        sender.sendMessage(response);
        return true;
    }
}
