package com.gmail.kovalev.config;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Sergey Kovalev
 * Класс для чтения конфигурации приложения из .yml файла.
 */
public final class Config {
    private static Config instance;
    public Map<String, Map<String, String>> config;

    private Config() {
        this.config = loadConfig();
    }

    /**
     * Метод возвращающий ключ-значение параметров приложения.
     *
     * @return ключ-значения.
     */
    private static Map<String, Map<String, String>> loadConfig() {
        try (InputStream inputStream = new FileInputStream("src/main/resources/properties.yml")) {
            Yaml yaml = new Yaml();
            return yaml.load(inputStream);
        } catch (IOException e) {
            try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("properties.yml")) {
                Yaml yaml = new Yaml();
                return yaml.load(inputStream);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
            instance.initDB(instance.config.get("application").get("reinitialize DB"));
        }
        return instance;
    }

    private void initDB(String isReinitialize) {
        if (isReinitialize.equalsIgnoreCase("true")) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try (Connection connection = DriverManager.getConnection(
                    Config.getInstance().config.get("db").get("url"),
                    Config.getInstance().config.get("db").get("login"),
                    Config.getInstance().config.get("db").get("password"))) {
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("table_for_db.sql");
                String sql = null;
                if (inputStream != null) {
                    sql = new BufferedReader(new InputStreamReader(inputStream))
                            .lines().collect(Collectors.joining());
                }
                Statement stmt = connection.createStatement();
                stmt.execute(sql);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
