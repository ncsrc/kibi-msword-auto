package ru.tstu.msword_auto.webapp.listeners;


import ru.tstu.msword_auto.automation.WordApplication;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AutomationServiceInitializer implements ServletContextListener {
    private static final String TEMPLATE_SOURCE = "template_source";
    private static final String TEMPLATE_SAVE = "template_save";


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String template_src = servletContextEvent.getServletContext().getRealPath("/templates");
        String template_save = servletContextEvent.getServletContext().getRealPath("/templates/filled");
        System.setProperty(TEMPLATE_SOURCE, template_src);
        System.setProperty(TEMPLATE_SAVE, template_save);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        WordApplication.close();
    }

}
