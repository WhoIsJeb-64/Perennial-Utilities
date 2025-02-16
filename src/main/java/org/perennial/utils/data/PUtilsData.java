package org.perennial.utils.data;

import org.bukkit.util.config.Configuration;
import org.perennial.utils.PerennialUtilities;

import java.io.File;
import java.text.SimpleDateFormat;

public class PUtilsData extends Configuration {

    private PerennialUtilities plugin;

    public PUtilsData(PerennialUtilities plugin, File statsFile) {
        super(statsFile);
        this.plugin = plugin;
        this.reload();
    }

    public void reload() {
        this.load();
        this.write();
        this.save();
    }

    public void write() {
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

    //Incrementing statistics

    public Integer incrementDataInt(String key) {
        return Integer.sum(this.getDataInt(key), 1);
    }

    public void incrementPlaytime() {

    }

    //Doing other stuff to/with statistics

    public String seeLastSeen(String key) {
        long seenAgo = (System.currentTimeMillis() / 1000) - getDataLong(key);
        String DATE_FORMAT = "D 'days,' H 'hours, &' m 'minutes'";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(seenAgo);
    }

    public long secondsToHours(String key) {
        return getDataLong(key) / 3600;
    }
}
