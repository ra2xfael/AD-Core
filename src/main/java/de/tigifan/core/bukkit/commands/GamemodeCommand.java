package de.tigifan.core.bukkit.commands;

import de.tigifan.core.bukkit.ADBukkit;
import de.tigifan.core.bukkit.util.ConfigType;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Tigifan on 14.05.2016.
 */
public class GamemodeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("ad.gamemode")) {
            sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("noPermission").replace("%cmd%", label));
            return true;
        }
        switch (args.length) {
            case 0:
                sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("gamemode.syntax"));
                return true;
            case 1:
                if(!(sender instanceof Player)) {
                    sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("isNoPlayer"));
                    return true;
                }
                Player player = (Player) sender;
                if(getGameMode(player, args[0]) != null) {
                    player.setGameMode(getGameMode(player, args[0]));
                    player.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("gamemode.changedMode"));
                }
                break;
            case 2:
                if(sender.hasPermission("ad.gamemode.other")) {
                    sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("noPermission").replace("%cmd%", label));
                    return true;
                }
                Player targetPlayer = getPlayer(sender, args[1]);
                if(targetPlayer == null) {
                    return true;
                }

                if(getGameMode(sender, args[0]) == null) {
                    return true;
                }

                targetPlayer.setGameMode(getGameMode(sender, args[0]));
                targetPlayer.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("gamemode.changedMode"));
                sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("gamemode.changedModeOther").replace("%player%", targetPlayer.getName()));
        }
        return false;
    }

    private GameMode getGameMode(CommandSender sender, String mode) {
        try {
            int gamemodeInt = Integer.parseInt(mode);
            GameMode gameMode = null;
            switch (gamemodeInt) {
                case 0:
                    gameMode = GameMode.SURVIVAL;
                    break;
                case 1:
                    gameMode = GameMode.CREATIVE;
                    break;
                case 2:
                    gameMode = GameMode.ADVENTURE;
                    break;
                case 3:
                    gameMode = GameMode.SPECTATOR;
                    break;
                default:
                    sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("gamemode.modeNotFound").replace("%mode%", mode));
            }
            if(gameMode != null)
                return gameMode;

        } catch (Exception ex) {
            GameMode gameMode;
            try {
                gameMode = GameMode.valueOf(mode);
                return gameMode;
            } catch (Exception ex2) {
                sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("gamemode.modeNotFound").replace("%mode%", mode));
            }
        }
        return null;
    }

    private Player getPlayer(CommandSender sender, String playerName) {
        Player targetPlayer = null;
        try {
             targetPlayer = Bukkit.getPlayer(playerName);
        } catch (Exception ex) {
            sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("playerNotFound"));
        }
        return targetPlayer;
    }
}
