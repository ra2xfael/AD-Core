package de.tigifan.core.bukkit.runnable;

import de.tigifan.core.bukkit.ADBukkit;
import de.tigifan.core.bukkit.commands.BackupCommand;
import de.tigifan.core.bukkit.util.ConfigType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * Created by raffael on 22.12.16.
 */
public class BackupThread extends Thread {

    private CommandSender sender;

    public BackupThread(CommandSender sender) {
        this.sender = sender;
        this.start();
    }

    @Override
    public void run() {
        while (isInterrupted()) {
            try {
                Bukkit.broadcastMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("backup.automatic"));
                Thread.sleep( 24 * 60 * 60 * 1000);
                BackupCommand.backup(sender);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
