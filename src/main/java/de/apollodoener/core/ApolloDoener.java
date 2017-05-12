package de.apollodoener.core;

import de.apollodoener.api.ADPlugin;
import de.apollodoener.core.commands.*;
import de.apollodoener.core.listeners.*;
import de.apollodoener.core.timer.BackupTimer;
import de.apollodoener.core.timer.HourTimer;
import de.apollodoener.core.util.ConfigType;
import de.apollodoener.core.sql.RankManager;
import de.apollodoener.core.sql.SQL;
import de.apollodoener.core.util.Config;
import de.apollodoener.core.util.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * Created by Tigifan on 12.04.2016.
 */
public class ApolloDoener extends JavaPlugin implements ADPlugin {

  private static ApolloDoener instance;

  private String prefix = "§7[§aApolloDöner§7] ";
  private HourTimer timer = new HourTimer();

  private static HashMap<ConfigType, Config> configs = new HashMap<>();

  //private Thread backupThread = new BackupThread(Bukkit.getConsoleSender());

  @Override
  public void onEnable() {
    MessageUtil.printConsoleMessage("&7Enabling plugin version &6" + this.getDescription().getVersion() + "&7!");
    instance = this;

    loadConfigs();
    initalize();

    registerEvents();
    registerCommands();
    startTimer();

    SQL.connect();
    RankManager.createDatabase();

    MessageUtil.printConsoleMessage("&7Plugin version &6" + this.getDescription().getVersion() + " &7enabled!");
  }

  @Override
  public void onDisable() {
    //backupThread.interrupt();
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

  private void startTimer() {
    timer.registerTimer(new BackupTimer());

    timer.start();
  }

  public static ApolloDoener getInstance() {
    return instance;
  }

  private void initalize() {
    prefix = ChatColor.translateAlternateColorCodes('&', ApolloDoener.getConfig(ConfigType.MESSAGES).getConfig().getString("Prefix"));
  }

  @NotNull
  @Override
  public String getPrefix() {
    return prefix;
  }

  @Override
  public void setPrefix(String s) {
    this.prefix = s;
  }
}
