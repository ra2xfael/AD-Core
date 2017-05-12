package de.apollodoener.core.commands;

import de.apollodoener.core.ApolloDoener;
import de.apollodoener.core.util.ConfigType;
import de.apollodoener.core.util.CommandUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Tigifan on 14.05.2016.
 */
public class TeleportCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    switch (args.length) {
      default:
        sender.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("teleport.syntax"));
        break;
      case 1:
        if (sender instanceof Player) {
          Player player = (Player) sender;
          Player targetPlayer = CommandUtil.getPlayer(player, args[0]);

          if (targetPlayer == null) {
            return true;
          }
          BackCommand.setPlayer(player, player.getLocation());
          player.teleport(targetPlayer);
          targetPlayer.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("teleport.to").replace("%player%", player.getName()));
          player.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("teleport.teleported").replace("%player%", targetPlayer.getName()));
        }
        break;
      case 2:
        Player player = CommandUtil.getPlayer(sender, args[0]);
        Player targetPlayer = null;
        if (player != null) {
          targetPlayer = CommandUtil.getPlayer(player, args[1]);
        }


        if (targetPlayer == null) {
          return true;
        }

        if (targetPlayer == player) {
          player.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("teleport.isSame"));
          return true;
        }
        BackCommand.setPlayer(player, player.getLocation());

        player.teleport(targetPlayer);
        sender.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("teleport.success").replace("%from%", player.getName()).replace("%to%", targetPlayer.getName()));
        player.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("teleport.teleported").replace("%player%", targetPlayer.getName()));
        targetPlayer.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("teleport.to").replace("%player%", player.getName()));
        break;
      case 4:
        player = CommandUtil.getPlayer(sender, args[0]);

        if (player == null) {
          return true;
        }
        BackCommand.setPlayer(player, player.getLocation());

        double x = Double.parseDouble(args[1]);
        double y = Double.parseDouble(args[2]);
        double z = Double.parseDouble(args[3]);
        Location loc = new Location(player.getWorld(), x, y, z);
        player.teleport(loc);

        break;
    }

    return true;
  }
}
