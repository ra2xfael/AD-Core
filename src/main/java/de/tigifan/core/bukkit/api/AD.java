package de.tigifan.core.bukkit.api;

import de.tigifan.core.bukkit.ADBukkit;
import de.tigifan.core.bukkit.util.ConfigType;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by Tigifan on 02.08.2016.
 */
public class AD {

    public static ADBukkit getMain() {
        return ADBukkit.getInstance();
    }

    public static String getPrefix() {
        return getMain().Prefix;
    }

    public static FileConfiguration getConfig(ConfigType type) {
        return ADBukkit.getConfig(type).getConfig();
    }
}
