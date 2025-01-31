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

    private void reload() {
        this.load();
        //this.write();
        this.save();
    }
}
