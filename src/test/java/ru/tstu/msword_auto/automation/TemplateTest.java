package ru.tstu.msword_automation.automation;

import org.junit.*;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;


// todo refactor with mocking


public class TemplateTest
{
	private Template template;


	@Before
	public void setUp()
	{
		// TODO add records to bd

		System.setProperty("template_folder", "C:\\Users\\user\\Desktop\\msword_automation\\automation\\src\\main\\resourses\\templates");
//		template = new Template();
	}

	@After
	public void close()
	{
//		template.close();
	}


//	@Test
//	public void TestSingletonCreation()
//	{
//		Template t1 = Template.getInstance();
//		assertEquals(template, t1);
//	}

	@Test
	public void CheckIfFilledTemplateWasSaved() throws Exception    // does not checks internal changes in template
	{
		template.fillTemplate(1);
		File filledTemplate = new File(System.getProperty("template_folder") + File.separator + "fyhgf G. F. - Протокол ГЭК по защите ВКР.docx");
		assertEquals(true, filledTemplate.exists());
	}


}




