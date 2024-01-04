package com.gmail.kovalev.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

@Getter
@Configuration
@ComponentScan("com.gmail.kovalev")
@PropertySource("classpath:properties.yml")
public class SpringConfig {

    private final ApplicationContext context;

    @Value("${application.page_size}")
    private int pageSize;

    @Value("${application.cache_type}")
    private String cacheType;

    @Value("${application.cache_size}")
    private int cacheSize;

    @Value("${application.reinitialize_DB}")
    private boolean isReinitialize;

    public SpringConfig(ApplicationContext context) {
        this.context = context;
    }

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        PropertySourcesPlaceholderConfigurer configure = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new ClassPathResource("properties.yml"));
        Properties yamlObj = Objects.requireNonNull(yamlPropertiesFactoryBean.getObject(), "Yaml not found");
        configure.setProperties(yamlObj);
        return configure;
    }

    @Bean
    public HikariDataSource dataSource(@Value("${db.driver}") String driver,
                                       @Value("${db.url}") String url,
                                       @Value("${db.login}") String login,
                                       @Value("${db.password}") String password) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(login);
        dataSource.setPassword(password);
        return dataSource;
    }

    @PostConstruct
    private void init() {
        initDB(isReinitialize);
    }

    private void initDB(boolean isReinitialize) {
        HikariDataSource dataSource = context.getBean("dataSource", HikariDataSource.class);
        if (isReinitialize) {
            try (Connection connection = dataSource.getConnection()) {
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
