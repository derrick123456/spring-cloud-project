package com.test.demo.user.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
@Configuration
public class DataSourceConfig {
    /**
     * 创建datasource对象
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    @Primary
    public DataSource dataSource() {
        DataSource datasource = DataSourceBuilder.create().type(HikariDataSource.class).build();

        return datasource;
    }
}
