package de.tigifan.core.bukkit;

import de.tigifan.core.bukkit.commands.FeedCommand;
import de.tigifan.core.bukkit.commands.GamemodeCommand;
import de.tigifan.core.bukkit.commands.HealCommand;
import de.tigifan.core.bukkit.commands.TeleportCommand;
import de.tigifan.core.bukkit.listeners.PlayerJoinListener;
import de.tigifan.core.bukkit.listeners.PlayerLeaveListener;
import de.tigifan.core.bukkit.util.Config;
import de.tigifan.core.bukkit.util.ConfigType;
import de.tigifan.core.bukkit.util.MessageUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * Created by Tigifan on 12.04.2016.
 */
public class ADBukkit extends JavaPlugin {

    private static ADBukkit instance;

    public String Prefix = "§7[§aApolloDöner§7] ";

    private static HashMap<ConfigType, Config> configs = new HashMap<>();

    @Override
    public void onEnable() {
        MessageUtil.printConsoleMessage("&7Enabling plugin version &6" + this.getDescription().getVersion() + "&7!");
        instance = this;

        loadConfigs();

        registerEvents();
        registerCommands();

        MessageUtil.printConsoleMessage("&7Plugin version &6" + this.getDescription().getVersion() + " &7enabled!");
    }

    @Override
    public void onDisable() {
        System.out.println("[AD-Core] Plugin disabled!");
    }

    private void loadConfigs() {
        String[] files = { "config.yml", "messages.yml" };
        String[] type = { "CONFIGURATION", "MESSAGES" };
        for(int i = 0; i < 2; i++) {
            MessageUtil.printConsoleMessage("&7Loading &6" + files[i] + "&7!");
            Config config = new Config(files[i]);

            configs.put(ConfigType.valueOf(type[i]), config);
        }
    }

    public static Config getConfig(ConfigType type) {
        return configs.get(type);
    }

    private void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerLeaveListener(), this);
    }

    private void registerCommands() {
        this.getCommand("gamemode").setExecutor(new GamemodeCommand());
        this.getCommand("heal").setExecutor(new HealCommand());
        this.getCommand("feed").setExecutor(new FeedCommand());
        this.getCommand("teleport").setExecutor(new TeleportCommand());
    }

    public static ADBukkit getInstance() {
        return instance;
    }
}
