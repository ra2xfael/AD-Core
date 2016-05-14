package de.tigifan.core.bukkit.listeners;

import de.tigifan.core.bukkit.ADBukkit;
import de.tigifan.core.bukkit.util.ConfigType;
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
    }
}
