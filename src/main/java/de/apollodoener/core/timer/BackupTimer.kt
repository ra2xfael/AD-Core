package de.apollodoener.core.timer

import de.apollodoener.core.ApolloDoener
import de.apollodoener.core.commands.BackupCommand
import de.apollodoener.core.util.ConfigType
import org.bukkit.Bukkit

/**
 * Created by raffael on 05.02.17.
 */

class BackupTimer : HourTimerInterval {

    override fun getInterval(): Int {
        return 2 * 24
    }

    override fun run() {
        Bukkit.broadcastMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("backup.automatic"))
        Thread.sleep((24 * 60 * 60 * 1000).toLong())
        BackupCommand.backup(Bukkit.getConsoleSender())
    }
}