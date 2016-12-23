package de.tigifan.core.bukkit;

import de.apollodoener.api.ADPlugin;
import de.tigifan.core.bukkit.commands.*;
import de.tigifan.core.bukkit.listeners.*;
import de.tigifan.core.bukkit.runnable.BackupThread;
import de.tigifan.core.bukkit.sql.RankManager;
import de.tigifan.core.bukkit.sql.SQL;
import de.tigifan.core.bukkit.util.Config;
import de.tigifan.core.bukkit.util.ConfigType;
import de.tigifan.core.bukkit.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * Created by Tigifan on 12.04.2016.
 */
public class ADBukkit extends JavaPlugin implements ADPlugin {

    private static ADBukkit instance;

    private String prefix = "§7[§aApolloDöner§7] ";

    private static HashMap<ConfigType, Config> configs = new HashMap<>();

    private Thread backupThread = new BackupThread(Bukkit.getConsoleSender());

    @Override
    public void onEnable() {
        MessageUtil.printConsoleMessage("&7Enabling plugin version &6" + this.getDescription().getVersion() + "&7!");
        instance = this;

        loadConfigs();
        initalize();

        registerEvents();
        registerCommands();

        SQL.connect();
        RankManager.createDatabase();

        MessageUtil.printConsoleMessage("&7Plugin version &6" + this.getDescription().getVersion() + " &7enabled!");
    }

    @Override
    public void onDisable() {
        backupThread.interrupt();
        SQL.disconnect();
        MessageUtil.printConsoleMessage("&7Plugin disabled!");
    }

    private void loadConfigs() {
        String[] files = {"config.yml", "messages.yml"};
        String[] type = {"CONFIGURATION", "MESSAGES"};
        for (int i = 0; i < 2; i++) {
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
        this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerRespawnListener(), this);
        this.getServer().getPluginManager().registerEvents(new SignChangeListener(), this);
    }

    private void registerCommands() {
        this.getCommand("gamemode").setExecutor(new GamemodeCommand());
        this.getCommand("heal").setExecutor(new HealCommand());
        this.getCommand("feed").setExecutor(new FeedCommand());
        this.getCommand("teleport").setExecutor(new TeleportCommand());
        this.getCommand("setspawn").setExecutor(new SetspawnCommand());
        this.getCommand("fly").setExecutor(new FlyCommand());
        this.getCommand("spawn").setExecutor(new SpawnCommand());
        this.getCommand("back").setExecutor(new BackCommand());
        this.getCommand("rank").setExecutor(new RankManager());
        this.getCommand("filedownload").setExecutor(new DownloadCommand());
        this.getCommand("playerhead").setExecutor(new PlayerHeadCommand());
        this.getCommand("backup").setExecutor(new BackupCommand());
    }

    public static ADBukkit getInstance() {
        return instance;
    }

    private void initalize() {
        prefix = ChatColor.translateAlternateColorCodes('&', ADBukkit.getConfig(ConfigType.MESSAGES).getConfig().getString("Prefix"));
    }

    @NotNull
    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public void setPrefix(String s) { this.prefix = s; }
}
