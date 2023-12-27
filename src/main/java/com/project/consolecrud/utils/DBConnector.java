package com.project.consolecrud.utils;

import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DBConnector {
    private String URL = "jdbc:mysql://localhost:3306/CRUD_APP";
    private String DRIVER = "com.mysql.cj.jdbc.Driver";
    private String USERNAME = "root";
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
