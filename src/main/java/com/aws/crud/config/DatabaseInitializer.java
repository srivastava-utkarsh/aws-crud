package com.aws.crud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class DatabaseInitializer {
    
    @Value("${spring.datasource.url}")
    private String dataSourceUrl;
    
    @Value("${spring.datasource.username}")
    private String username;
    
    @Value("${spring.datasource.password}")
    private String password;
    
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    
    @EventListener(ContextRefreshedEvent.class)
    public void initDatabase() {
        try {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(driverClassName);
            dataSource.setUrl(dataSourceUrl);
            dataSource.setUsername(username);
            dataSource.setPassword(password);

            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement()) {
                
                // Create database if not exists
                statement.execute("CREATE DATABASE IF NOT EXISTS userdb");
                
                // Use the database
                statement.execute("USE userdb");
                
            } catch (SQLException e) {
                throw new RuntimeException("Failed to initialize database", e);
            }
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
}
