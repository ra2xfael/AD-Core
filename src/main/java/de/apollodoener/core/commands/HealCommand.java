package de.apollodoener.core.commands;

import de.apollodoener.core.ApolloDoener;
import de.apollodoener.core.util.ConfigType;
import de.apollodoener.core.util.CommandUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Tigifan on 14.05.2016.
 */
public class HealCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!sender.hasPermission("ad.heal")) {
      sender.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("noPermission").replace("%cmd%", label));
      return true;
    }
    switch (args.length) {
      case 0:
        if (!(sender instanceof Player)) {
          sender.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("isNoPlayer"));
          return true;
        }
        Player player = (Player) sender;
        player.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("heal.healed"));
        player.setHealth(20);
        player.setFoodLevel(20);
        break;
      case 1:
        if (!sender.hasPermission("ad.heal.other")) {
          sender.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("noPermission").replace("%cmd%", label));
          return true;
        }
        Player targetPlayer = CommandUtil.getPlayer(sender, args[0]);
        if (targetPlayer == null)
          return true;
        targetPlayer.setHealth(20);
        targetPlayer.setFoodLevel(20);
        targetPlayer.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("heal.healed"));
        sender.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("heal.healedOther").replace("%player%", targetPlayer.getName()));
        break;
    }
    return false;
  }
}
