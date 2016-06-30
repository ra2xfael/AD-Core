package de.tigifan.core.bukkit.util;

import de.tigifan.core.bukkit.ADBukkit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Tigifan on 19.04.2016.
 */
public class Config {

    private YamlConfiguration configFile = new YamlConfiguration();
    private File file;
    private boolean loaded = false;
    private Plugin plugin;
    private String fileName;

    public Config(String fileName) {
        this.plugin = ADBukkit.getInstance();
        this.fileName = fileName;
        loadConfig();
    }

    public YamlConfiguration getConfig() {
        return configFile;
    }

    public File getConfigFile() {
        if (!loaded) {
            loadConfig();
        }
        return file;
    }

    private void loadConfig() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin(this.plugin.getName()).getDataFolder(), fileName);
        if (file.exists()) {
            configFile = new YamlConfiguration();
            try {
                configFile.load(file);
            } catch (InvalidConfigurationException | IOException e) {
                e.printStackTrace();
            }
            loaded = true;
            MessageUtil.printConsoleMessage(fileName + " loaded!");
        } else {
            MessageUtil.printConsoleMessage(fileName + " created!");
            try {
                Bukkit.getServer().getPluginManager().getPlugin(plugin.getName()).getDataFolder().mkdir();
                InputStream jarURL = Config.class.getResourceAsStream("/" + fileName);
                copyFile(jarURL, file);
                configFile = new YamlConfiguration();
                configFile.load(file);
                loaded = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void copyFile(InputStream in, File out) throws Exception {
        InputStream fis = in;
        FileOutputStream fos = new FileOutputStream(out);
        try {
            byte[] buf = new byte[1024];
            int i = 0;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    public String getMessage(String path) {
        return ADBukkit.getInstance().Prefix + ChatColor.translateAlternateColorCodes('&', this.configFile.getString(path));
    }
}