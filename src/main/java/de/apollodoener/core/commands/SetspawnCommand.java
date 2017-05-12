package de.apollodoener.core.commands;

import de.apollodoener.core.ApolloDoener;
import de.apollodoener.core.util.ConfigType;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

/**
 * Created by Tigifan on 14.05.2016.
 */
public class SetspawnCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("isNoPlayer"));
      return true;
    }
    Player player = (Player) sender;
    if (!player.hasPermission("ad.setspawn")) {
      player.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("noPermission").replace("%cmd%", label));
      return true;
    }
    switch (args.length) {
      case 1:
        Location location = player.getLocation();
        String defaultSpawn = ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().getString("defaultSpawn");
        String spawn = args[0];
        if (!args[0].equalsIgnoreCase(defaultSpawn) && ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().getDouble("spawns." + defaultSpawn + ".Y") == 0) {
          player.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("setspawn.defaultNotSet").replace("%spawn%", defaultSpawn));
        }
        ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().set("spawns." + spawn + ".World", location.getWorld().getName());
        ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().set("spawns." + spawn + ".X", location.getX());
        ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().set("spawns." + spawn + ".Y", location.getY());
        ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().set("spawns." + spawn + ".Z", location.getZ());
        ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().set("spawns." + spawn + ".Yaw", location.getYaw());
        ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().set("spawns." + spawn + ".Pitch", location.getPitch());
        try {
          ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().save(ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfigFile());
          player.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("setspawn.success").replace("%spawn%", spawn));
        } catch (IOException e) {
          e.printStackTrace();
        }
        break;
      default:
        player.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("setspawn.syntax"));
    }
    return false;
  }
}
