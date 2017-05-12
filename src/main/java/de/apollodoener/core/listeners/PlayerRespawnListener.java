package de.apollodoener.core.listeners;

import de.apollodoener.core.ApolloDoener;
import de.apollodoener.core.util.ConfigType;
import de.apollodoener.core.commands.SpawnCommand;
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

    String defaultSpawn = ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().getString("defaultSpawn");
    if (SpawnCommand.getLocation("spawns." + defaultSpawn).getBlockY() == 0) {
      player.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("setspawn.defaultNotSet").replace("%spawn%", defaultSpawn));
      return;
    }
    ev.setRespawnLocation(SpawnCommand.getLocation("spawns." + defaultSpawn));
  }
}
