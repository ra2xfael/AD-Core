package de.apollodoener.core.commands;

import de.apollodoener.core.util.ConfigType;
import de.apollodoener.core.ApolloDoener;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Created by raffael on 07.12.16.
 */
public class PlayerHeadCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("isNoPlayer"));
      return false;
    }
    if (!sender.hasPermission("ad.ph")) {
      sender.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("noPermission"));
      return false;
    }
    Player player = (Player) sender;
    if (args.length != 1) {
      player.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("playerhead.syntax"));
      return false;
    }
    String ownerName = args[0];
    ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
    SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
    skullMeta.setOwner(ownerName);
    skullMeta.setDisplayName("§6" + ownerName + "'s §7Kopf");
    skull.setItemMeta(skullMeta);
    player.getInventory().addItem(skull);
    player.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("playerhead.success").replace("%player%", ownerName));

    return true;
  }
}
