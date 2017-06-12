package ru.tstu.msword_auto.webapp.listeners;


import ru.tstu.msword_auto.dao.ConnectionStorage;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;

public class ConnectionStorageInitializer implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        Connection connection = (Connection) servletContext.getAttribute("connection");
        ConnectionStorage.setConnection(connection);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // intentionally blank
    }

}
