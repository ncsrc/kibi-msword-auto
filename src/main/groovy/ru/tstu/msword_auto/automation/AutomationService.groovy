package ru.tstu.msword_auto.automation

import com.jacob.com.LibraryLoader
import org.codehaus.groovy.scriptom.ActiveXObject

/**
 * Package-local singleton representing word application for internal service usage.
 * Has only getApp() and close() methods. Considered to be used in whole lifetime of client application,
 * so close() should only be invoked while closing whole app.
 */
class AutomationService {
    public static String templateSource
    public static String templateSave


    static void init(String jacobDllPath, String templateSourcePath, String templateSavePath) {
        System.setProperty(LibraryLoader.JACOB_DLL_PATH, jacobDllPath)
        templateSource = templateSourcePath
        templateSave = templateSavePath
    }

}
