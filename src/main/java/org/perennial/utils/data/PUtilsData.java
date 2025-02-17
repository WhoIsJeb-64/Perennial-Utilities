package org.perennial.utils.data;

import org.bukkit.util.config.Configuration;
import org.perennial.utils.PerennialUtilities;
import java.io.File;

import static org.perennial.utils.PerennialUtilities.userdata;

public class PUtilsData extends Configuration {

    private PerennialUtilities plugin;
    private PUtilsConfig config;

    public PUtilsData(PerennialUtilities plugin, File statsFile) {
        super(statsFile);
        this.plugin = plugin;
        this.reload();
        this.config = plugin.getConfig();
    }

    public void reload() {
        this.load();
        //this.write();
        this.save();
    }



    public void write(String playerName) {
        userdata.generateDataEntry(playerName + ".join-message", config.defaultJoinMessage());
        userdata.generateDataEntry(playerName + ".quit-message", config.defaultQuitMessage());
        userdata.generateDataEntry(playerName + ".password", null);
        userdata.generateDataEntry(playerName + ".must-login", true);
        userdata.generateDataEntry(playerName + ".stats.last-seen", 0);
        userdata.generateDataEntry(playerName + ".stats.time-played", 0);
        userdata.generateDataEntry(playerName + ".stats.blocks-broken", 0);
        userdata.generateDataEntry(playerName + ".stats.blocks-placed", 0);
        userdata.generateDataEntry(playerName + ".stats.messages-sent", 0);
    }

    public void generateDataEntry(String key, Object defaultValue) {
        if (this.getProperty(key) == null) {
            this.setProperty(key, defaultValue);
        }
        final Object value = this.getProperty(key);
        this.removeProperty(key);
        this.setProperty(key, value);
    }

    //Retrieving statistics

    public String getDataString(String key) {
        return String.valueOf(this.getProperty(key));
    }

    public Integer getDataInt(String key) {
        return Integer.valueOf(getDataString(key));
    }

    public Long getDataLong(String key) {
        return Long.valueOf(getDataString(key));
    }

    public boolean getDataBool(String key) {
        return Boolean.valueOf(getDataString(key));
    }

    public double getDataDouble(String key) {
        return Double.valueOf(getDataString(key));
    }

    public long getLastSeen(String playerName) {
        long now = System.currentTimeMillis() / 1000;
        long lastSeen = getDataLong(playerName + ".stats.last-seen");
        return (now - lastSeen) / 3600;
    }

    //Incrementing statistics

    public void modifyDataInt(String key, Integer amount) {
        int moddedInt = Integer.sum(getDataInt(key), amount);
        setProperty(key, moddedInt);
    }

    public double modifyDataDouble(String key, Double amount) {
        return Double.sum(getDataDouble(key), amount);
    }

    //Playtime stuff

    public void startPlaytimeCounting(String playerName) {
        long sessionStart = System.currentTimeMillis() / 1000;
        userdata.setProperty(playerName + ".stats.session-start", sessionStart);
        save();
    }

    public void updatePlaytime(String playerName) {
        long timePlayed = getDataLong(playerName + ".stats.time-played");
        long sessionStart = getDataLong(playerName + ".stats.session-start");
        long sessionEnd = System.currentTimeMillis() / 1000;
        long timeElapsed = sessionEnd - sessionStart;
        setProperty(playerName + ".stats.time-played", timePlayed + timeElapsed);
        save();
    }

    //Calculations

    public long secondsToHours(String key) {
        return getDataLong(key) / 3600;
    }

    public double roundToHundredths(Double input) {
        return Math.floor(input * 100) / 100;
    }

    //Other stuff

    public void setLastSeen(String playerName) {
        long lastSeen = System.currentTimeMillis() / 1000;
        setProperty(playerName + ".stats.last-seen", lastSeen);
        save();
    }
}
