package de.tigifan.core.bukkit.commands;

import de.tigifan.core.bukkit.ADBukkit;
import de.tigifan.core.bukkit.util.CommandUtil;
import de.tigifan.core.bukkit.util.ConfigType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Tigifan on 15.05.2016.
 */
public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("ad.fly")) {
            sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("noPermission").replace("%cmd%", label));
            return true;
        }
        switch (args.length) {
            case 0:
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("isNoPlayer"));
                    return true;
                }
                Player player = (Player) sender;
                player.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("fly.flight"));
                player.setAllowFlight(!player.getAllowFlight());
                break;
            case 1:
                if (!sender.hasPermission("ad.fly.other")) {
                    sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("noPermission").replace("%cmd%", label));
                    return true;
                }
                Player targetPlayer = CommandUtil.getPlayer(sender, args[0]);
                if (targetPlayer == null)
                    return true;
                targetPlayer.setAllowFlight(!targetPlayer.getAllowFlight());
                targetPlayer.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("fly.flight"));
                sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("fly.flightOther").replace("%player%", targetPlayer.getName()));
                break;
        }
        return false;
    }
}
