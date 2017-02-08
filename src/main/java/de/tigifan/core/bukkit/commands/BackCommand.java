package de.tigifan.core.bukkit.commands;

import de.tigifan.core.bukkit.ADBukkit;
import de.tigifan.core.bukkit.util.CommandUtil;
import de.tigifan.core.bukkit.util.ConfigType;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by Tigifan on 15.05.2016.
 */
public class BackCommand implements CommandExecutor {

  private static HashMap<Player, Location> backLocations = new HashMap<>();

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("isNoPlayer"));
      return true;
    }
    Player player = (Player) sender;
    if (!player.hasPermission("ad.back")) {
      player.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("noPermission").replace("%cmd%", label));
      return true;
    }
    switch (args.length) {
      case 0:
        player.teleport(backLocations.get(player));
        player.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("back.success"));
        break;
      case 1:
        if (!sender.hasPermission("ad.back.other")) {
          sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("noPermission").replace("%cmd%", label));
          return true;
        }
        Player targetPlayer = CommandUtil.getPlayer(sender, args[0]);
        if (targetPlayer == null)
          return true;

        player.teleport(backLocations.get(targetPlayer));
        sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("back.backOther").replace("%player%", targetPlayer.getName()));
        break;
    }
    return false;
  }

  public static void setPlayer(Player player, Location location) {
    if (backLocations.containsKey(player)) {
      backLocations.remove(player);
    }
    backLocations.put(player, location);
  }
}
