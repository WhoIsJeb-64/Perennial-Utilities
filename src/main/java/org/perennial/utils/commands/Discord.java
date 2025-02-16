package org.perennial.utils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.perennial.utils.PerennialUtilities;
import org.perennial.utils.data.PUtilsConfig;

public class Discord implements CommandExecutor {

    private final PerennialUtilities plugin;

    private final PUtilsConfig config;

    public Discord(PerennialUtilities plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("putils.discord") && !sender.isOp()) {
            sender.sendMessage("§cYou do not have permission to execute this command.");
            return true;
        }

        //Invite to server's discord
        String response = config.getConfigString("/discord-response");
        sender.sendMessage(response);
        return true;
    }
}
