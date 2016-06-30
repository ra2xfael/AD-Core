package de.tigifan.core.bukkit.listeners;

import de.tigifan.core.bukkit.ADBukkit;
import de.tigifan.core.bukkit.util.ConfigType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Tigifan on 14.05.2016.
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("join").replace("%player%", event.getPlayer().getName()));

        String mainSpawn = ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getString("defaultSpawn");
        if (ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getBoolean("spawnOnJoin") && ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getDouble("spawns." + mainSpawn + ".Y") != 0d) {
            String spawn = ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getString("defaultSpawn");

            World world = Bukkit.getWorld(ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getString("spawns." + spawn + ".World"));
            double x = ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getDouble("spawns." + spawn + ".X");
            double y = ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getDouble("spawns." + spawn + ".Y");
            double z = ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getDouble("spawns." + spawn + ".Z");
            float yaw = (float) ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getDouble("spawns." + spawn + ".Yaw");
            float pitch = (float) ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getDouble("spawns." + spawn + ".Pitch");

            Location location = new Location(world, x, y, z, yaw, pitch);

            event.getPlayer().teleport(location);
        }
    }
}
