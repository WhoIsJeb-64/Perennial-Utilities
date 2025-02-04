package org.perennial.utils.data;

import org.bukkit.util.config.Configuration;
import org.perennial.utils.PUtils;
import java.io.File;

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

    public Object getStatEntry(String key) {
        return this.getProperty(key);
    }

    public String getStatString(String key) {
        return String.valueOf(getStatEntry(key));
    }

    public Integer getStatInteger(String key) {
        return Integer.valueOf(getStatString(key));
    }

    public Long getStatLong(String key) {
        return Long.valueOf(getStatString(key));
    }
}
