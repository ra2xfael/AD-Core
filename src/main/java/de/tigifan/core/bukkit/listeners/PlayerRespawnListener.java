package de.tigifan.core.bukkit.listeners;

import de.tigifan.core.bukkit.ADBukkit;
import de.tigifan.core.bukkit.commands.SpawnCommand;
import de.tigifan.core.bukkit.util.ConfigType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Created by Tigifan on 04.08.2016.
 */
public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent ev) {
        Player player = ev.getPlayer();

        String defaultSpawn = ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getString("defaultSpawn");
        if (SpawnCommand.getLocation("spawns." + defaultSpawn).getBlockY() == 0) {
            player.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("setspawn.defaultNotSet").replace("%spawn%", defaultSpawn));
            return;
        }
        ev.setRespawnLocation(SpawnCommand.getLocation("spawns." + defaultSpawn));
    }
}
