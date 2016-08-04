package de.tigifan.core.bukkit.listeners;

import de.tigifan.core.bukkit.ADBukkit;
import de.tigifan.core.bukkit.commands.BackCommand;
import de.tigifan.core.bukkit.commands.SpawnCommand;
import de.tigifan.core.bukkit.util.ConfigType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Tigifan on 02.06.2016.
 */
public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent ev) {
        Player player = ev.getEntity();
        BackCommand.setPlayer(player, player.getLocation());

        ev.setDeathMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("death.player").replace("%player%", player.getName()));
        player.spigot().respawn();
    }
}
