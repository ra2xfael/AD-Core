package de.tigifan.core.bukkit.listeners;

import de.tigifan.core.bukkit.ADBukkit;
import de.tigifan.core.bukkit.util.ConfigType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Tigifan on 14.05.2016.
 */
public class PlayerLeaveListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        event.setQuitMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("leave").replace("%player%", event.getPlayer().getName()));
    }
}
