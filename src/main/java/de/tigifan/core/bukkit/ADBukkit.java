package de.tigifan.core.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Tigifan on 12.04.2016.
 */
public class ADBukkit extends JavaPlugin {

    private static ADBukkit instance;

    @Override
    public void onEnable() {
        System.out.println("[AD-Core] Enabling plugin version " + this.getDescription().getVersion() + "!");
        instance = this;

        System.out.println("[AD-Core] Plugin version " + this.getDescription().getVersion() + " enabled!");
    }

    @Override
    public void onDisable() {

    }

    public static ADBukkit getInstance() {
        return instance;
    }
}
