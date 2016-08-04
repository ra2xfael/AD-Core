package de.tigifan.core.bukkit.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * Created by Tigifan on 04.08.2016.
 */
public class SignChangeListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent ev) {

        for(int i = 0; i < 3; i++) {
            System.out.println(ev.getLine(i));
            ev.setLine(i, ChatColor.translateAlternateColorCodes('&', ev.getLine(i)));
        }
    }
}
