package ru.tstu.msword_automation.automation;


import com.jacob.com.LibraryLoader;

/**
 * This is a service for filling ms word templates.
 * Should be initialized before usage. Initialization required for
 * specifying the locations where original templates reside and where
 * to save them after filling.
 * By itself this service is kind of a "factory" for creation of brand new .doc templates.
 */
public class WordAutomationService {
    private static boolean initialized;
    private static WordAutomationService self;
    private static String templateSrcFolder;
    private static String templateDstFolder;


    private WordAutomationService() {

    }

    /**
     * Returns singleton instance of service. Note that service must be initialized first,
     * before using this method. Otherwise it throws unchecked exception.
     * @return instance of service.
     */
    public static WordAutomationService getInstance() throws WordAutomationServiceSystemException {
        if(!initialized) {
            throw new WordAutomationServiceSystemException("Word automation service was not initialized.");
        }

        if(self == null) {
            self = new WordAutomationService();
        }

        return self;
    }

    /**
     * Performs initialization of service. Initialization consists only of specification in which folder
     * .docx templates reside and where to save filled templates for subsequent sending.
     * @param templateSourceFolder folder where .docx templates reside
     * @param templateDestinationFolder folder where to save filled templates
     * @throws WordAutomationServiceSystemException thrown when client calls initialization more than once
     */
    public static void initializeService(String templateSourceFolder, String templateDestinationFolder) throws WordAutomationServiceSystemException {
        if(initialized) {
            throw new WordAutomationServiceSystemException("Word automation service was already initialized. Cannot initialize it twice.");
        }

        System.setProperty(LibraryLoader.JACOB_DLL_PATH, getJacobDllPath());
        templateSrcFolder = templateSourceFolder;
        templateDstFolder = templateDestinationFolder;
        initialized = true;
    }

    /**
     * Simply returns new empty gos template.
     * @return gos template
     */
    public Template getGosTemplate() {
        return Template.newGosTemplate(templateSrcFolder, templateDstFolder);
    }

    /**
     * Simply returns new empty vcr template
     * @return vcr template
     */
    public Template getVcrTemplate() {
        return Template.newVcrTemplate(templateSrcFolder, templateDstFolder);
    }


    private static String getJacobDllPath() {
        ClassLoader classLoader = WordAutomationService.class.getClassLoader();
        String cpArch = System.getProperty("os.arch");
        if(cpArch.equals("amd64")) {
            return classLoader.getResource("jacob-1.14.3-x64.dll").getPath();
        } else {
            return classLoader.getResource("jacob-1.14.3-x86.dll").getPath();
        }
    }

}




























