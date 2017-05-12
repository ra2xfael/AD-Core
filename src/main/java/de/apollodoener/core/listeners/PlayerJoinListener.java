package de.apollodoener.core.listeners;

import de.apollodoener.core.ApolloDoener;
import de.apollodoener.core.util.ConfigType;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Tigifan on 14.05.2016.
 */
public class PlayerJoinListener implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    event.setJoinMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("join").replace("%player%", player.getName()));

    String mainSpawn = ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().getString("defaultSpawn");

    if (ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().getBoolean("spawnOnJoin") && ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().getDouble("spawns." + mainSpawn + ".Y") != 0d) {
      String spawn = ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().getString("defaultSpawn");

      World world = Bukkit.getWorld(ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().getString("spawns." + spawn + ".World"));
      double x = ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().getDouble("spawns." + spawn + ".X");
      double y = ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().getDouble("spawns." + spawn + ".Y");
      double z = ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().getDouble("spawns." + spawn + ".Z");
      float yaw = (float) ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().getDouble("spawns." + spawn + ".Yaw");
      float pitch = (float) ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().getDouble("spawns." + spawn + ".Pitch");

      Location location = new Location(world, x, y, z, yaw, pitch);

      player.teleport(location);
    }
    boolean hasPermission = event.getPlayer().hasPermission("ad.gamemode");
    boolean isEnabled = ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().getBoolean("gamemodeOnJoin");
    if (hasPermission && isEnabled) {
      Bukkit.getScheduler().scheduleSyncDelayedTask(ApolloDoener.getInstance(), () -> player.setGameMode(GameMode.CREATIVE), 1L);
    }
  }
}
