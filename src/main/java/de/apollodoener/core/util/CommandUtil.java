package de.apollodoener.core.util;

import de.apollodoener.core.ApolloDoener;
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
      sender.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("playerNotFound"));
    }
    return targetPlayer;
  }
}
