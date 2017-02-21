package ru.tstu.msword_automation.automation;

import com.jacob.com.LibraryLoader;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class WordAutomationServiceTest {
    private static final String templateSrcFolder = "templates";
    private static final String templateDstFolder = "templates/filled";
    private static WordAutomationService service;


    @BeforeClass
    public static void setUp() throws Exception {
        WordAutomationService.initializeService(templateSrcFolder, templateDstFolder);
        service = WordAutomationService.getInstance();
    }


    @Test
    public void whenCalledGetInstanceAfterInitializationThenSuccessfullyReturnsInstance() throws Exception {
        WordAutomationService service_ = WordAutomationService.getInstance();
        assertNotNull(service_);
        assertEquals(service_, service);
    }

    @Test(expected = WordAutomationServiceSystemException.class)
    public void whenInitializedTwiceThenThrowsException() throws Exception {
        WordAutomationService.initializeService(templateSrcFolder, templateDstFolder);

    }

    @Test
    public void whenGetGosTemplateThenReturnsCorrectTemplate() throws Exception {
        Template gosTemplate = service.getGosTemplate();
        String templateActualName = gosTemplate.getFilename();
        String templateExpectedName = "protocol_gos";
        assertThat(templateActualName, is(templateExpectedName));
    }

    @Test
    public void whenGetVcrTemplateThenReturnsCorrectTemplate() throws Exception {
        Template vcrTemplate = service.getVcrTemplate();
        String templateActualName = vcrTemplate.getFilename();
        String templateExpectedName = "protocol_vcr";
        assertThat(templateActualName, is(templateExpectedName));
    }

    @Test
    public void whenGetGosTemplateCalledTwiceThenItIsDifferentObjects() throws Exception {
        Template firstTpl = service.getGosTemplate();
        Template secondTpl = service.getGosTemplate();
        assertNotEquals(firstTpl, secondTpl);
    }

    @Test
    public void whenGetVcrTemplateCalledTwiceThenItIsDifferentObjects() throws Exception {
        Template firstTpl = service.getVcrTemplate();
        Template secondTpl = service.getVcrTemplate();
        assertNotEquals(firstTpl, secondTpl);
    }

    @Test
    public void jacobDllPropertyIsSetUp() throws Exception {
        String dllPath = System.getProperty(LibraryLoader.JACOB_DLL_PATH);
        String expected = WordAutomationServiceTest.class.getClassLoader().getResource("jacob-1.14.3-x64.dll").getPath();
        assertEquals(expected, dllPath);
    }

}































