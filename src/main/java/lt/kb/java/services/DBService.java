package lt.kb.java.services;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBService {
    private static DataSource ds;
   private static HikariConfig config=new HikariConfig();
//    private static HikariDataSource ds;

    private static Properties properties;
    static {
        properties=new Properties();
        InputStream is = DBService.class.getClassLoader().
                getResourceAsStream("application.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    static{

        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.user"));
        config.setPassword(properties.getProperty("db.password"));
        ds=new HikariDataSource(config);
    }
        public static Connection createConnection() throws SQLException {
                return ds.getConnection();

        }

}
