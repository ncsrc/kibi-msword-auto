package ru.tstu.msword_auto.webapp.listeners;


import ru.tstu.msword_auto.automation.AutomationService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AutomationServiceInitializer implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        String jacobDll;
        if(System.getProperty("os.arch").equals("amd64")) {
            jacobDll = servletContext.getRealPath("WEB-INF/classes/jacob-1.14.3-x64.dll");
        } else {
            jacobDll = servletContext.getRealPath("WEB-INF/classes/jacob-1.14.3-x86.dll");
        }

        String templateSrc = servletContextEvent.getServletContext().getRealPath("/templates");
        String templateSave = servletContextEvent.getServletContext().getRealPath("/templates/filled");

        AutomationService.init(jacobDll, templateSrc, templateSave);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
