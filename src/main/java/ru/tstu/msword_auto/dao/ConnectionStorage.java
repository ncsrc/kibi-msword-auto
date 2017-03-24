package ru.tstu.msword_auto.dao;

import javax.servlet.ServletContext;
import java.sql.Connection;

// wrapper that set from webapp
// todo javadoc
public class ConnectionStorage {
    private static Connection connection;

    // TODO Connection instead of ServletContext

    public static void setConnection(ServletContext servletContext) {
        connection = (Connection) servletContext.getAttribute("connection");
    }

    public static Connection getConnection() {
        return connection;
    }

}
