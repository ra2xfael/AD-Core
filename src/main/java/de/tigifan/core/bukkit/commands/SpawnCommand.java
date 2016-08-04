package de.tigifan.core.bukkit.commands;

import de.tigifan.core.bukkit.ADBukkit;
import de.tigifan.core.bukkit.util.ConfigType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Tigifan on 15.05.2016.
 */
public class SpawnCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("isNoPlayer"));
            return true;
        }
        if (!sender.hasPermission("ad.spawn")) {
            sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("noPermission").replace("%cmd%", label));
            return true;
        }
        Player player = (Player) sender;
        switch (args.length) {
            case 0:
                String defaultSpawn = ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getString("defaultSpawn");
                if (getLocation("spawns." + defaultSpawn).getBlockY() == 0) {
                    player.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("setspawn.defaultNotSet").replace("%spawn%", defaultSpawn));
                    return true;
                }
                player.teleport(getLocation("spawns." + defaultSpawn));
                player.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("spawn.successful.default"));
                break;
            case 1:
                String spawn = args[0];
                if (getLocation("spawns." + spawn) == null) {
                    player.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("spawn.notFound").replace("%spawn%", spawn));
                    return true;
                }
                player.teleport(getLocation("spawns." + spawn));
                player.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("spawn.successful.specially").replace("%spawn%", spawn));
                break;
            default:
                player.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("spawn.syntax"));
        }
        return false;
    }

    public static Location getLocation(String path) {
        World world = Bukkit.getWorld(ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getString(path + ".World"));
        double x = ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getDouble(path + ".X");
        double y = ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getDouble(path + ".Y");
        double z = ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getDouble(path + ".Z");
        float yaw = (float) ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getDouble(path + ".Yaw");
        float pitch = (float) ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getDouble(path + ".Pitch");

        return new Location(world, x, y, z, yaw, pitch);
    }
}
