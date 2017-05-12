package de.apollodoener.core.listeners;

import de.apollodoener.core.util.ConfigType;
import de.apollodoener.core.ApolloDoener;
import de.apollodoener.core.commands.BackCommand;
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

    ev.setDeathMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("death.player").replace("%player%", player.getName()));
    player.spigot().respawn();
  }
}
