package de.apollodoener.core.sql;

import de.apollodoener.core.ApolloDoener;
import de.apollodoener.core.util.ConfigType;
import de.apollodoener.core.util.MessageUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Tigifan on 30.06.2016.
 */
public class SQL {

  private static boolean useDatabase = ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().getBoolean("mysql.use");
  private static String host = ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().getString("mysql.host");
  private static String port = ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().getString("mysql.port");
  private static String database = ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().getString("mysql.database");
  private static String user = ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().getString("mysql.user");
  private static String password = ApolloDoener.getConfig(ConfigType.CONFIGURATION).getConfig().getString("mysql.password");

  private static Connection con;

  private SQL() {
  }

  public static void connect() {
    if (useDatabase && !isConnected()) {
      try {
        con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
        MessageUtil.printConsoleMessage("&7MySQL connected!");
      } catch (SQLException e) {
        MessageUtil.printConsoleMessage("&cMySQL couldn't connect!");
        e.printStackTrace();
        MessageUtil.printConsoleMessage("&c&m---------------------------------------");
        MessageUtil.printConsoleMessage("&4DISABLING AD-CORE VERSION " + ApolloDoener.getInstance().getDescription().getVersion() + "!");
        MessageUtil.printConsoleMessage("&c&m---------------------------------------");
        ApolloDoener.getInstance().getServer().getPluginManager().disablePlugin(ApolloDoener.getInstance());
      }
    }
  }

  public static void disconnect() {
    if (isConnected()) {
      try {
        con.close();
        MessageUtil.printConsoleMessage("&7MySQL disconnected!");
      } catch (SQLException e) {
        MessageUtil.printConsoleMessage("&cMySQL couldn't disconnect!");
        e.printStackTrace();
      }
    }
  }

  public static boolean isConnected() {
    return con != null;
  }

  public static Connection getConnection() {
    return con;
  }

  public static PreparedStatement prepareStatement(String query) throws SQLException {
    if (isConnected()) {
      return con.prepareStatement(query);
    }
    SQL.connect();
    throw new SQLException();
  }
}
