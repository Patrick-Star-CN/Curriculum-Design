package team.star.healthcodesystem.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import static team.star.healthcodesystem.util.YamlReader.readConfFromYaml;

/**
 * 数据库配置类
 * @author Patrick_Star
 * @version 1.1
 */
public class DatabaseConnector {
    private static final HikariDataSource DATA_SOURCE;

    static  {
        Map<String, String> mysqlConfig = (Map<String, String>) readConfFromYaml("config.yaml").get("mysql");
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(mysqlConfig.get("driver"));
        config.setJdbcUrl(mysqlConfig.get("url"));
        config.setUsername(mysqlConfig.get("user"));
        config.setPassword(mysqlConfig.get("password"));
        config.setConnectionTimeout(30000);
        config.setMaximumPoolSize(20);
        config.setIdleTimeout(600000);
        DATA_SOURCE = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return DATA_SOURCE.getConnection();
    }
}

