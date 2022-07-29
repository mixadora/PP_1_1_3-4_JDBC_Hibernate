package jm.task.core.jdbc.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static final String CONFIG_NAME = "db.properties";
    private static final Properties GLOBAL_CONFIG = new Properties();

    static {
        try (Reader fr = new FileReader(CONFIG_NAME)) {
            GLOBAL_CONFIG.load(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            String url = Util.getProperty("db.url");
            String user = Util.getProperty("db.user");
            String password = Util.getProperty("db.password");

            return DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getProperty(String property) {
        return GLOBAL_CONFIG.getProperty(property);
    }

}
