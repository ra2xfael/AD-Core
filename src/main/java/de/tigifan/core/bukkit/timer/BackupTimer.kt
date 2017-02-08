package de.tigifan.core.bukkit.timer

import de.tigifan.core.bukkit.ADBukkit
import de.tigifan.core.bukkit.commands.BackupCommand
import de.tigifan.core.bukkit.util.ConfigType
import org.bukkit.Bukkit

/**
 * Created by raffael on 05.02.17.
 */

class BackupTimer : HourTimerInterval {

    override fun getInterval(): Int {
        return 2 * 24
    }

    override fun run() {
        Bukkit.broadcastMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("backup.automatic"))
        Thread.sleep((24 * 60 * 60 * 1000).toLong())
        BackupCommand.backup(Bukkit.getConsoleSender())
    }
}