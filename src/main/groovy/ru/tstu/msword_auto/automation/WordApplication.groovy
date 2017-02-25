package ru.tstu.msword_automation.automation

import groovy.transform.PackageScope
import org.codehaus.groovy.scriptom.ActiveXObject

/**
 * Package-local singleton representing word application for internal service usage.
 * Has only getApp() and close() methods. Considered to be used in whole lifetime of client application,
 * so close() should only be invoked while closing whole app.
 */
@PackageScope class WordApplication {
    private static final def application = new ActiveXObject("Word.Application")


    /**
     * Returns msword application object.
     * @return word app
     */
    static def getApplication() {
        return application
    }

    /**
     * Closes msword application object. Object can't be used after anymore.
     */
    static void close() {
        application.quit()
    }

}
