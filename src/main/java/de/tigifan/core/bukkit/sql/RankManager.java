package de.tigifan.core.bukkit.sql;

import de.tigifan.core.bukkit.ADBukkit;
import de.tigifan.core.bukkit.util.CommandUtil;
import de.tigifan.core.bukkit.util.ConfigType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Tigifan on 30.06.2016.
 */
public class RankManager implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!sender.hasPermission("ad.rank")) {
      sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("noPermission"));
      return false;
    }

    if (args.length == 2) {
      switch (args[0]) {
        case "get":
          Player player = CommandUtil.getPlayer(sender, args[1]);
          if (player == null) {
            sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("playerNotFound").replace("%player%", args[1]));
            return false;
          }

          sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("rank.design").replace("%player%", player.getName()));
          if (getSince(player.getUniqueId()) == 0) {
            sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("rank.rank").replace("%rank%", getRank(player.getUniqueId())));
            return false;
          }
          long millis = getSince(player.getUniqueId());
          SimpleDateFormat dateFormatToTime = new SimpleDateFormat("dd.MM.YYYY");

          String time = dateFormatToTime.format(new Date(millis));
          sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("rank.rank").replace("%rank%", getRank(player.getUniqueId())));
          sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("rank.since").replace("%time%", time));

          break;
        default:
          sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("rank.syntax"));
          break;
      }
    } else if ((args.length == 3) && args[0].equalsIgnoreCase("promote")) {
      Player player = CommandUtil.getPlayer(sender, args[1]);
      if (player == null) {
        sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("playerNotFound").replace("%player%", args[2]));
        return false;
      }
      String rank = args[2];
      if (addRank(player.getUniqueId(), rank))
        sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("rank.promoted").replace("%player%", player.getName()));
      else
        sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("sqlException"));
    } else {
      sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("rank.syntax"));
      return true;
    }

    return false;
  }

  public static void createDatabase() {
    if (SQL.isConnected()) {
      try {
        PreparedStatement preparedStatement = SQL.prepareStatement("CREATE TABLE IF NOT EXISTS rank(uuid VARCHAR(36) NOT NULL, rank VARCHAR(100) NOT NULL, since BIGINT(30))");
        preparedStatement.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  private static String getRank(UUID uuid) {
    if (SQL.isConnected()) {

      try {
        PreparedStatement preparedStatement = SQL.prepareStatement("SELECT rank FROM rank WHERE uuid = ?");
        preparedStatement.setString(1, uuid.toString());

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
          return resultSet.getString("rank");
        }
        return "None";
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return "None";
  }

  private static long getSince(UUID uuid) {
    if (SQL.isConnected()) {
      try {
        PreparedStatement preparedStatement = SQL.prepareStatement("SELECT since FROM rank WHERE uuid = ?");
        preparedStatement.setString(1, uuid.toString());

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
          return resultSet.getLong("since");
        }
        return 0;
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return 0;
  }

  private static boolean addRank(UUID uuid, String rank) {
    if (SQL.isConnected()) {
      try {
        long date = System.currentTimeMillis();
        PreparedStatement preparedStatement = SQL.prepareStatement("INSERT INTO rank (uuid, rank, since) VALUES(?, ?, ?)");
        preparedStatement.setString(1, uuid.toString());
        preparedStatement.setString(2, rank);
        preparedStatement.setLong(3, date);
        preparedStatement.executeUpdate();
        return true;
      } catch (SQLException e) {
        e.printStackTrace();
        return false;
      }
    }
    return false;
  }

  private static void removeRank(UUID uuid) {
    if (SQL.isConnected()) {
      try {
        PreparedStatement preparedStatement = SQL.prepareStatement("DELETE FROM rank WHERE uuid = ?");
        preparedStatement.setString(1, uuid.toString());
        preparedStatement.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
