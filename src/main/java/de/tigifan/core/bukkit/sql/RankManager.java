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
        if(sender.hasPermission("ad.rank")) {
            return false;
        }
        if(args.length == 1) {
            switch (args[0]) {
                case "get":
                    Player player = CommandUtil.getPlayer(sender, args[1]);
                    if(player == null) {
                        sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("playerNotFound").replace("%player%", args[1]));
                        return false;
                    }

                    sender.sendMessage(ADBukkit.getConfig(ConfigType.CONFIGURATION).getMessage("rank.design").replace("%player%", player.getName()));
                    if(getSince(player.getUniqueId()) == 0) {
                        sender.sendMessage(ADBukkit.getConfig(ConfigType.CONFIGURATION).getMessage("rank.rank").replace("%rank%", getRank(player.getUniqueId())));
                        return false;
                    }
                    SimpleDateFormat dateFormatToDate = new SimpleDateFormat("YYYYMMdhms");
                    Date date = new Date(dateFormatToDate.format(getSince(player.getUniqueId())));
                    SimpleDateFormat dateFormatToTime = new SimpleDateFormat("h:m:s d.MM.YYYY");
                    String time = dateFormatToTime.format(date);
                    sender.sendMessage(ADBukkit.getConfig(ConfigType.CONFIGURATION).getMessage("rank.rank").replace("%rank%", getRank(player.getUniqueId())));
                    sender.sendMessage(ADBukkit.getConfig(ConfigType.CONFIGURATION).getMessage("rank.since").replace("%time%", time));
                    break;
                default:
                    sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("rank.syntax"));
                    break;
            }
        } else if (args.length == 3) {
            switch (args[0]) {
                case "promote":

                    break;
                default:

            }
        } else {
            sender.sendMessage(ADBukkit.getConfig(ConfigType.MESSAGES).getMessage("rank.syntax"));
            return true;
        }

        return false;
    }

    public static void createDatabase() {
        try {
            PreparedStatement preparedStatement = SQL.prepareStatement("CREATE TABLE IF NOT EXISTS rank(uuid VARCHAR(36) NOT NULL, rank VARCHAR(100) NOT NULL, since BIGINT(14))");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getRank(UUID uuid) {
        try {
            PreparedStatement preparedStatement = SQL.prepareStatement("SELECT rank FROM rank WHERE uuid = ?");
            preparedStatement.setString(1, uuid.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getString("rank");
            }
            return "None";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "None";
    }

    private static long getSince(UUID uuid) {
        try {
            PreparedStatement preparedStatement = SQL.prepareStatement("SELECT since FROM rank WHERE uuid = ?");
            preparedStatement.setString(1, uuid.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getInt("since");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void addRank(UUID uuid, String rank) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdhms");

            long date = Long.parseLong(dateFormat.format(new Date()));
            PreparedStatement preparedStatement = SQL.prepareStatement("INSERT INTO rank (uuid, rank, since) VALUES(?, ?, ?)");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, rank);
            preparedStatement.setLong(3, date);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeRank(UUID uuid) {
        try {
            PreparedStatement preparedStatement = SQL.prepareStatement("DELETE FROM rank WHERE uuid = ?");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
