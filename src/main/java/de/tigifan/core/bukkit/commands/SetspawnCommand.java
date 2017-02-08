package de.tigifan.core.bukkit.commands;

import de.tigifan.core.bukkit.ADBukkit;
import de.tigifan.core.bukkit.util.ConfigType;
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
      sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("isNoPlayer"));
      return true;
    }
    Player player = (Player) sender;
    if (!player.hasPermission("ad.setspawn")) {
      player.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("noPermission").replace("%cmd%", label));
      return true;
    }
    switch (args.length) {
      case 1:
        Location location = player.getLocation();
        String defaultSpawn = ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getString("defaultSpawn");
        String spawn = args[0];
        if (!args[0].equalsIgnoreCase(defaultSpawn) && ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getDouble("spawns." + defaultSpawn + ".Y") == 0) {
          player.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("setspawn.defaultNotSet").replace("%spawn%", defaultSpawn));
        }
        ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().set("spawns." + spawn + ".World", location.getWorld().getName());
        ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().set("spawns." + spawn + ".X", location.getX());
        ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().set("spawns." + spawn + ".Y", location.getY());
        ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().set("spawns." + spawn + ".Z", location.getZ());
        ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().set("spawns." + spawn + ".Yaw", location.getYaw());
        ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().set("spawns." + spawn + ".Pitch", location.getPitch());
        try {
          ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().save(ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfigFile());
          player.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("setspawn.success").replace("%spawn%", spawn));
        } catch (IOException e) {
          e.printStackTrace();
        }
        break;
      default:
        player.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("setspawn.syntax"));
    }
    return false;
  }
}
