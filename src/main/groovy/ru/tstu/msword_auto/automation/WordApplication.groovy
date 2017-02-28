package ru.tstu.msword_auto.automation

import com.jacob.com.LibraryLoader
import org.codehaus.groovy.scriptom.ActiveXObject

/**
 * Package-local singleton representing word application for internal service usage.
 * Has only getApp() and close() methods. Considered to be used in whole lifetime of client application,
 * so close() should only be invoked while closing whole app.
 */
class WordApplication {
    private static def application


    /**
     * Returns msword application object.
     * @return word app
     */
    static def getApplication() {
        return application
    }

    static void init() {
        application = new ActiveXObject("Word.Application")
    }

    /**
     * Closes msword application object. Object can't be used after anymore.
     */
    static void close() {
        application.quit()
    }


    static void initDll(String path) {
        System.setProperty(LibraryLoader.JACOB_DLL_PATH, path)
    }

}
