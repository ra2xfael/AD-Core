package de.tigifan.core.bukkit.listeners;

import de.tigifan.core.bukkit.commands.BackCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Tigifan on 02.06.2016.
 */
public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent ev) {
        BackCommand.setPlayer(ev.getEntity(), ev.getEntity().getLocation());
    }
}
