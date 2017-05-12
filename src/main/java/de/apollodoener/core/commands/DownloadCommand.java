package de.apollodoener.core.commands;

import de.apollodoener.core.ApolloDoener;
import de.apollodoener.core.util.ConfigType;
import de.apollodoener.core.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Tigifan on 04.08.2016.
 */
public class DownloadCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!sender.hasPermission("ad.download")) {
      sender.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("noPermission"));
      return false;
    }
    if (args.length != 1) {
      sender.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("download.syntax"));
      return false;
    }

    String pluginDirectory = ApolloDoener.getInstance().getDataFolder().getAbsolutePath() + "/..";
    try {
      sender.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("download.download"));
      String downloadedFile = download(args[0], pluginDirectory);
      sender.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("download.downloaded").replace("%file%", downloadedFile));
    } catch (FileNotFoundException ex) {
      sender.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("download.notFound").replace("%link%", args[0]));
      MessageUtil.printConsoleMessage("&6" + sender.getName() + " &7versuchte " + args[0] + " (nicht vorhanden) herunterzuladen!");
    } catch (IOException ex) {
      sender.sendMessage(ApolloDoener.getConfig(ConfigType.MESSAGES).getMessage("download.exception"));
      ex.printStackTrace();
    }
    return false;
  }

  private String download(String fileURL, String destinationDirectory) throws IOException {
    String downloadedFileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);

    // Open connection to the file
    URL url = new URL(fileURL);
    InputStream is = url.openStream();
    // Stream to the destionation file
    FileOutputStream fos = new FileOutputStream(destinationDirectory + "/" + downloadedFileName);

    // Read bytes from URL to the local file
    byte[] buffer = new byte[4096];
    int bytesRead = 0;

    System.out.print("[AD-Core] DOWNLOAD: Downloading &6" + downloadedFileName);
    while ((bytesRead = is.read(buffer)) != -1) {
      fos.write(buffer, 0, bytesRead);
    }
    MessageUtil.printConsoleMessage("DOWNLOAD: saved &6" + downloadedFileName);

    // Close destination stream
    fos.close();
    // Close URL stream
    is.close();
    return downloadedFileName;
  }
}
