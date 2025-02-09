package org.perennial.utils.data;

import org.bukkit.util.config.Configuration;
import org.perennial.utils.PUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PUtilsStats extends Configuration {

    private PUtils plugin;

    public PUtilsStats(PUtils plugin, File statsFile) {
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

    public void generateStatEntry(String key, Object defaultValue) {
        if (this.getProperty(key) == null) {
            this.setProperty(key, defaultValue);
        }
        final Object value = this.getProperty(key);
        this.removeProperty(key);
        this.setProperty(key, value);
    }

    //Retrieving statistics

    public String getStatString(String key) {
        return String.valueOf(this.getProperty(key));
    }

    public Integer getStatInteger(String key) {
        return Integer.valueOf(getStatString(key));
    }

    public Long getStatLong(String key) {
        return Long.valueOf(getStatString(key));
    }

    //Incrementing statistics

    public Integer incrementStatInt(String key) {
        return Integer.sum(this.getStatInteger(key), 1);
    }

    //Doing other stuff to/with statistics

    public String dateFormatLong(String key) {
        Date date = new java.util.Date(this.getStatLong(key));
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }
}
