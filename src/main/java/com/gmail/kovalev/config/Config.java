package com.gmail.kovalev.config;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

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
        }
        return instance;
    }
}
