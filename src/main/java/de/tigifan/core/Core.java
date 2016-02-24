package de.tigifan.core;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Tigifan on 24.02.2016.
 **/
public class Core extends JavaPlugin {

    public static Core instance = null;

    @Override
    public void onEnable() {
        instance = this;
        System.out.println("[AD-Core] Enabling plugin version " + this.getDescription().getVersion() + "!");

        registerEvents();
        registerCommands();

        System.out.println("[AD-Core] Plugin version " + this.getDescription().getVersion() + " enabled!");
    }

    private void registerEvents() {

    }

    private void registerCommands() {

    }
}
