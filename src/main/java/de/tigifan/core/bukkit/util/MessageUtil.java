package de.tigifan.core.bukkit.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * Created by Tigifan on 14.05.2016.
 */
public class MessageUtil {

    public static void printConsoleMessage(String msg) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&bAD-Core&7] " +  msg));
    }
}
