package com.project.consolecrud.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DBConnector {

    @Value("${spring.datasource.url}")
    private String URL = "jdbc:mysql://localhost:3306/APPLICATION";
    @Value("${spring.datasource.driver-class-name}")
    private String DRIVER = "com.mysql.cj.jdbc.Driver";
    @Value("${spring.datasource.username}")
    private String USERNAME = "root";
    @Value("${spring.datasource.password}")
    private String PASSWORD = "password";


    public Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        connection.setAutoCommit(false);
        return connection;
    }
}
