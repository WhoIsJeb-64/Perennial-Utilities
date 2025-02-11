package org.perennial.utils.data;

import org.bukkit.util.config.Configuration;
import org.perennial.utils.PUtils;

import java.io.File;

/**
 * A custom configuration class for managing plugin configuration files in a Bukkit environment.
 * Extends the {@link Configuration} class to provide additional utility methods for
 * reading and writing configuration options with defaults.
 */
public class PUtilsConfig extends Configuration {
    private final int configVersion = 3;


    private PUtils plugin;

    /**
     * Constructs a new TemplateConfig instance.
     *
     * @param plugin     The plugin instance associated with this configuration.
     * @param configFile The configuration file to be managed.
     */
    public PUtilsConfig(PUtils plugin, File configFile) {
        super(configFile);
        this.plugin = plugin;
        this.reload();
    }

    /**
     * Reloads the configuration by loading the file, writing defaults, and saving changes.
     */
    public void reload() {
        this.load();
        this.write();
        this.save();
    }

    /**
     * Writes default configuration options to the file.
     * Ensures that default options are added when the file is loaded.
     */
    public void write() {
        // Convert old configuration keys to new keys if necessary
        if (this.getString("config-version") == null || Integer.valueOf(this.getString("config-version")) < configVersion) {
            this.plugin.logger(java.util.logging.Level.INFO, "Converting config to new version (" + configVersion + ")...");
            convertToNewConfig();
            this.setProperty("config-version", configVersion); // This should be handled by the conversion method but just in case
        }

        // Main options
        generateConfigOption("config-version", configVersion);

        // Command Output options
        generateConfigOption("/discord-response", "§2Our Discord: §bhttps://discord.gg/[Invite]>");
        generateConfigOption("/map-response", "§2Our Dynmap: §bhttp://[IP:Port]]/");

        //Custom join/quit/kick messages
        generateConfigOption("join-message", "§2%p% §ajoined the game.");
        generateConfigOption("quit-message", "§4%p% §cleft the game.");
        generateConfigOption("kick-message", "§6%p% §ewas kicked from the game.");
    }

    private void convertToNewConfig() {
        // Convert old configuration keys to new keys

        // Convert from old config version 0 to new config version 3
        if(this.getString("config-version") == null || Integer.valueOf(this.getString("config-version")) < 3) {
            convertToNewAddress("/discord-response", "settings.discord-command.response", true);
            convertToNewAddress("/map-response", "settings.map-command.response", true);
            convertToNewAddress("join-message", "settings.join-message", true);
            convertToNewAddress("quit-message", "settings.quit-message", true);
            convertToNewAddress("kick-message", "settings.kick-message", true);
        }
    }

    /**
     * Converts an old configuration key to a new one.
     * If the old key exists and the new key does not, the old key's value is copied to the new key,
     * and the old key is removed.
     *
     * @param newKey The new configuration key.
     * @param oldKey The old configuration key.
     * @param log    Whether to log the conversion process.
     * @return True if the conversion was performed, false otherwise.
     */
    private boolean convertToNewAddress(String newKey, String oldKey, boolean log) {
        if (this.getString(newKey) != null) {
            return false;
        }
        if (this.getString(oldKey) == null) {
            return false;
        }
        if (log) {
            plugin.logger(java.util.logging.Level.INFO, "Converting Config: " + oldKey + " to " + newKey);
        }
        Object value = this.getProperty(oldKey);
        this.setProperty(newKey, value);
        this.removeProperty(oldKey);
        return true;
    }

    /**
     * Adds a default value for a configuration key if it is not already set.
     *
     * @param key          The configuration key.
     * @param defaultValue The default value to set.
     */
    public void generateConfigOption(String key, Object defaultValue) {
        if (this.getProperty(key) == null) {
            this.setProperty(key, defaultValue);
        }
        final Object value = this.getProperty(key);
        this.removeProperty(key);
        this.setProperty(key, value);
    }

    //Getters Start
    public Object getConfigOption(String key) {
        return this.getProperty(key);
    }

    public String getConfigString(String key) {
        return String.valueOf(getConfigOption(key));
    }

    public Integer getConfigInteger(String key) {
        return Integer.valueOf(getConfigString(key));
    }

    public Long getConfigLong(String key) {
        return Long.valueOf(getConfigString(key));
    }

    public Double getConfigDouble(String key) {
        return Double.valueOf(getConfigString(key));
    }

    public Boolean getConfigBoolean(String key) {
        return Boolean.valueOf(getConfigString(key));
    }

    public String defaultJoinMessage() {
        return plugin.getConfig().getConfigString("join-message");
    }

    public String defaultQuitMessage() {
        return plugin.getConfig().getConfigString("quit-message");
    }

    //Getters End
}
