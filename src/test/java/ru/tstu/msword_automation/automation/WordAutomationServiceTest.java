package ru.tstu.msword_automation.automation;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class WordAutomationServiceTest {
    private static final String templateSrcFolder = "templates";
    private static final String templateDstFolder = "templates/filled";


    @Test
    public void whenCalledGetInstanceAfterInitializationThenSuccessfullyReturnsInstance() throws Exception {
        WordAutomationService.initializeService(templateSrcFolder, templateDstFolder);
        WordAutomationService service = WordAutomationService.getInstance();

        assertNotNull(service);
    }

    @Test(expected = WordAutomationServiceSystemException.class)
    public void whenCalledGetInstanceBeforeInitializationThenThrowsException() throws Exception {
        WordAutomationService.getInstance();
        // todo run multiple times after first test
    }

    @Test
    public void whenGetGosTemplateThenReturnsCorrectTemplate() throws Exception {
        WordAutomationService.initializeService(templateSrcFolder, templateDstFolder);
        Template gosTemplate = WordAutomationService.getInstance().getGosTemplate();
        String templateActualName = gosTemplate.getFilename();
        String templateExpectedName = "protocol_gos";
        assertThat(templateActualName, is(templateExpectedName));
    }

    @Test
    public void getVcrTemplate() throws Exception {
        WordAutomationService.initializeService(templateSrcFolder, templateDstFolder);
        Template vcrTemplate = WordAutomationService.getInstance().getVcrTemplate();
        String templateActualName = vcrTemplate.getFilename();
        String templateExpectedName = "protocol_vcr";
        assertThat(templateActualName, is(templateExpectedName));
    }

}































