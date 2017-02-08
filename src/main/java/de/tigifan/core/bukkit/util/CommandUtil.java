package de.tigifan.core.bukkit.util;

import de.tigifan.core.bukkit.ADBukkit;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Tigifan on 14.05.2016.
 */
public class CommandUtil {

  public static Player getPlayer(CommandSender sender, String playerName) {
    Player targetPlayer = null;
    try {
      targetPlayer = Bukkit.getPlayer(playerName);
    } catch (Exception ex) {
      sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("playerNotFound"));
    }
    return targetPlayer;
  }
}
