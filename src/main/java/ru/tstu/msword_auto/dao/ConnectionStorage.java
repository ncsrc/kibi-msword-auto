package ru.tstu.msword_auto.dao;


import java.sql.Connection;

// wrapper that set from webapp
public class ConnectionStorage {
    private static Connection conn;


    public static void setConnection(Connection connection) {
        conn = connection;
    }

    public static Connection getConnection() {
        return conn;
    }

}
