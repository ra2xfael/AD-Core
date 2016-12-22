package de.tigifan.core.bukkit.commands;

import de.apollodoener.api.file.ZipUtil;
import de.tigifan.core.bukkit.ADBukkit;
import de.tigifan.core.bukkit.util.ConfigType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

/**
 * Created by raffael on 22.12.16.
 */
public class BackupCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("ad.backup")) {
            sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("noPermission").replace("%cmd%", label));
            return true;
        }
        backup(sender);
        return false;
    }

    public static void backup(CommandSender sender) {
        String time = new SimpleDateFormat("dd-MM-yyyy-H:m").format(new Date());
        ZipUtil zipUtil = new ZipUtil();
        String serverLocation = ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getString("backup.serverlocation");
        String backupLocation = ADBukkit.getConfig(ConfigType.CONFIGURATION).getConfig().getString("backup.backuplocation") + "/" + time + ".zip";

        Bukkit.broadcastMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("backup.start").replace("%sender%", sender.getName()));
        Consumer<String> consumer = s -> Bukkit.broadcastMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("backup.success").replace("%file%", s));
        new Thread(() -> {
            try {
                zipUtil.zipDir(serverLocation, backupLocation);
            } catch (FileNotFoundException e) {
                sender.sendMessage(ADBukkit.getInstance().getPrefix() + " Die Dateien wurden nicht gefunden!");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            consumer.accept(time + ".zip");
        }).start();
    }
}