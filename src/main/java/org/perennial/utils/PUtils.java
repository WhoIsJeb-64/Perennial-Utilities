package org.perennial.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.perennial.utils.commands.*;
import org.perennial.utils.data.PUtilsConfig;
import org.perennial.utils.data.PUtilsStats;
import org.perennial.utils.listeners.*;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PUtils extends JavaPlugin {
    private JavaPlugin plugin;
    private Logger log;
    private String pluginName;
    private PluginDescriptionFile pdf;

    public static PUtilsConfig configuration;
    public static PUtilsStats statistics;


    @Override
    public void onEnable() {
        plugin = this;
        log = this.getServer().getLogger();
        pdf = this.getDescription();
        pluginName = pdf.getName();
        log.info("[" + pluginName + "] Is Loading, Version: " + pdf.getVersion());

        //Create config.yml and stats.yml
        configuration = new PUtilsConfig(this, new File(getDataFolder(), "config.yml"));
        statistics = new PUtilsStats(this, new File(getDataFolder(), "stats.yml"));

        //Register commands
        getCommand("discord").setExecutor(new Discord(this));
        getCommand("map").setExecutor(new Map(this));
        getCommand("colors").setExecutor(new Colors(this));
        getCommand("stats").setExecutor(new Stats(this));
        getCommand("playtime").setExecutor(new Playtime(this));
        //getCommand("seen").setExecutor(new Seen(this));
        getCommand("putils").setExecutor(new PUtilsCommand(this));

        //Register listeners
        final PlayerJoin joinlistener = new PlayerJoin(this);
        final PlayerQuit quitlistener = new PlayerQuit(this);
        final PlayerKick kicklistener = new PlayerKick(this);
        final BlockBreak breaklistener = new BlockBreak(this);
        final BlockPlace placelistener = new BlockPlace(this);
        getServer().getPluginManager().registerEvents(joinlistener, this);
        getServer().getPluginManager().registerEvents(quitlistener, this);
        getServer().getPluginManager().registerEvents(kicklistener, this);
        getServer().getPluginManager().registerEvents(breaklistener, this);
        getServer().getPluginManager().registerEvents(placelistener, this);

        log.info("[" + pluginName + "] Is Loaded, Version: " + pdf.getVersion());
    }

    @Override
    public void onDisable() {
        log.info("[" + pluginName + "] Is Unloading, Version: " + pdf.getVersion());

        configuration.save();
        statistics.save();

        log.info("[" + pluginName + "] Is Unloaded, Version: " + pdf.getVersion());
    }

    public void logger(Level level, String message) {
        Bukkit.getLogger().log(level, "[" + plugin.getDescription().getName() + "] " + message);
    }

    public PUtilsConfig getConfig() {
        return configuration;
    }

    public static PUtilsStats getStats() {
        return statistics;
    }
}
