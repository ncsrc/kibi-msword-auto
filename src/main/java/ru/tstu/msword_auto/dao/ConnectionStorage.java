package ru.tstu.msword_auto.dao;

import javax.servlet.ServletContext;
import java.sql.Connection;

// wrapper that set from webapp
// todo javadoc
class ConnectionStorage {
    private static Connection connection;


    public static void setConnection(ServletContext servletContext) {
        connection = (Connection) servletContext.getAttribute("connection");
    }

    public static Connection getConnection() {
        return connection;
    }

}
