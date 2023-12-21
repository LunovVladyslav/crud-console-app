package com.project.consolecrud.utils;

import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DBConnector {
    private String URL = "jdbc:mysql://localhost:3306/testCrud";
    private static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private String USERNAME = "root";
    private String PASSWORD = "password";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}
