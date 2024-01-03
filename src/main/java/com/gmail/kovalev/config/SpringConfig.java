package com.gmail.kovalev.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.util.Objects;
import java.util.Properties;

@Getter
@Configuration
@ComponentScan("com.gmail.kovalev")
@PropertySource("classpath:properties.yml")
public class SpringConfig {

    @Value("${application.page_size}")
    private int pageSize;

    @Value("${application.cache_type}")
    private String cacheType;

    @Value("${application.cache_size}")
    private int cacheSize;

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
}
