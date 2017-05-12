package de.apollodoener.core.listeners;

import de.apollodoener.core.ApolloDoener;
import de.apollodoener.core.util.ConfigType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Tigifan on 14.05.2016.
 */
public class PlayerLeaveListener implements Listener {

  @EventHandler
  public void onLeave(PlayerQuitEvent event) {
    event.setQuitMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("leave").replace("%player%", event.getPlayer().getName()));
  }
}
