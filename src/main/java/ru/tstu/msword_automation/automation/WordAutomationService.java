package ru.tstu.msword_automation.automation;


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

        templateSrcFolder = templateSourceFolder;
        templateDstFolder = templateDestinationFolder;
        initialized = true;
    }

    /**
     * Simply returns new empty gos template.
     * @return gos template
     */
    public Template getGosTemplate() {
        return Template.newGosTemplate();
    }

    /**
     * Simply returns new empty vcr template
     * @return vcr template
     */
    public Template getVcrTemplate() {
        return Template.newVcrTemplate();
    }


    // This two methods are for internal usage.
    // Template class use them to identify .doc files location
    String getTemplateSourceFolder() {
        return templateSrcFolder;
    }

    String getTemplateDestinationFolder() {
        return templateDstFolder;
    }


}




























