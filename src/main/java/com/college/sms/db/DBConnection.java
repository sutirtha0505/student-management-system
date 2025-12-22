package com.college.sms.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.college.sms.config.DBConfig;

public class DBConnection {

    // Singleton connection instance? Or just a factory for connections?
    // The guide says "Creates and manages JDBC connections".
    // Usually good to get a new connection or use a pool.
    // Since no pool library allowed, we will just create a connection per request
    // or a singleton if we want to keep it simple,
    // but single connection might time out.
    // Let's provide a method to get a connection.

    public static Connection getConnection() throws SQLException {
        try {
            // Load driver class (optional in newer JDBC but good practice for clarity)
            // Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
    }
}
