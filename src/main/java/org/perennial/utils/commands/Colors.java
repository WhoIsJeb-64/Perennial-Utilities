package org.perennial.utils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.perennial.utils.data.PUtilsConfig;
import org.perennial.utils.PUtils;

public class Colors implements CommandExecutor {

    private final PUtils plugin;

    private final PUtilsConfig config;

    public Colors(PUtils plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("putils.colors") && !sender.isOp()) {
            sender.sendMessage("§cYou do not have permission to execute this command.");
            return true;
        }

        //The color codes and names
        sender.sendMessage("§00-Black §11-Dark Blue §22-Green §33-Dark Aqua §44-Dark Red §55-Dark Purple §66-Gold §77-Gray §88-Dark Gray §99-Blue §aa-Lime §bb-Aqua §cc-Red §dd-Light Purple §ee-Yellow §ff-White");
        return true;
    }
}
