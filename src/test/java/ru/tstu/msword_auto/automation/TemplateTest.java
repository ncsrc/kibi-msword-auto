package ru.tstu.msword_auto.automation;

import org.junit.*;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import ru.tstu.msword_auto.automation.entity_aggregation.Gek;
import ru.tstu.msword_auto.automation.entity_aggregation.StudentData;
import ru.tstu.msword_auto.automation.entity_aggregation.TemplateData;
import ru.tstu.msword_auto.entity.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class TemplateTest {
	private Template template;
	private TemplateData data = mock(TemplateData.class);
	private Date date = new Date(1, "2012-11-11", "2012-11-11");
	private GekHead gekHead = new GekHead("asd", "asd", "asd");
	private List<GekMember> gekMembers = Arrays.asList(
			new GekMember("asd", "asd"),
			new GekMember("asd", "qwe"));

	private String gekMembersStr = "asd, qwe";
	private Student student = new Student(1, "ads", "ads", "ads",
									   "adss", "adss", "adss", "adsd",
									   "adsd", "adsd");
	private Course course = new Course("asd", 1, "asd", "asd", "asd");
	private VCR vcr = new VCR(1, "asd", "asd", "asd");


	/*
		Template contract:
		Template itself is a abstract class, which contains only static factory methods
		and inner classes, implementing Template

		sets constants for template folders in static block from system properties, throws unchecked exception if not found

		static factory methods: newGosTemplate(TemplateData), newVcrTemplate(TemplateData)

		TemplateData resides in automation,
		but fills in webapp with entities. Throws exception if some entity missing or was not set.

		public fulfillTemplate() throws TemplateException(name ?), which fills it and saves.
		Filling conducts by extracting entities from TemplateData field, and if some data
		in some entity is missing, then exception thrown with message to client about what is missing.


		public getTemplateName(), returns filename

		public close() - closes underlying document. Make autocloseable ?

	 */


	@Before
	public void setUp() {
		when(data.getDate()).thenReturn(this.date);
		when(data.getGekHead()).thenReturn(this.gekHead);
		when(data.listOfGekMembers()).thenReturn(this.gekMembers);
		when(data.getStudent()).thenReturn(this.student);
		when(data.getStudentCourse()).thenReturn(this.course);
		when(data.getStudentVcr()).thenReturn(this.vcr);
		when(data.getGekMembersListInString()).thenReturn(this.gekMembersStr);

		System.setProperty("template_source", "templates");
		System.setProperty("template_save", "templates/filled");

		template = Template.newGosTemplate(data);
		template.close();
		template.doc = mock(Document.class);
	}

	@AfterClass
	public static void tearDownClass() {
		if(WordAppStatus.prevTestPassed) {
			WordApplication.close();
		}
		WordAppStatus.prevTestPassed = true;
	}

	@Test
	public void whenGosTplGetFilenameBeforeFillingThenCorrect() throws Exception {
		String expected = "ads A. A. - Протокол по приему гос экзамена.docx";
		String actual = template.getFilename();
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void whenGosTplGetFilenameAfterFillingThenCorrect() throws Exception {
		// assign
		String filename = "ads A. A. - Протокол по приему гос экзамена.docx";

		// act
		template.fulfillTemplate();
		String actual = template.getFilename();

		// assert
		assertEquals(filename, actual);
	}

	@Test
	public void whenFulfillGosTplWithFullDataThenSaved() throws Exception {
		String filename;
		try(Template tpl = Template.newGosTemplate(data)) {
			tpl.fulfillTemplate();
			filename = tpl.getFilename();
		}

		String path = this.getClass().getClassLoader().getResource(System.getProperty("template_save")).getPath() +
				File.separator + filename;

		boolean actual = new File(path).exists();
		assertEquals(true, actual);
	}

	@Test
	public void whenVcrTplGetFilenameBeforeFillingThenCorrect() throws Exception {

		try(Template tpl = Template.newVcrTemplate(data)) {
			String expected = "ads A. A. - Протокол по защите ВКР.docx";
			String actual = tpl.getFilename();
			assertNotNull(actual);
			assertEquals(expected, actual);
		}

	}

	@Test
	public void whenVcrTplGetFilenameAfterFillingThenCorrect() throws Exception {

		try(Template tpl = Template.newVcrTemplate(data)) {
			String filename = "ads A. A. - Протокол по защите ВКР.docx";
			tpl.fulfillTemplate();
			String actual = tpl.getFilename();
			assertEquals(filename, actual);
		}

	}

	@Test
	public void whenFulfillVcrTplWithFullDataThenSaved() throws Exception {

		String filename;
		try(Template tpl = Template.newVcrTemplate(data)) {
			tpl.fulfillTemplate();
			filename = tpl.getFilename();
		}

		String path = this.getClass().getClassLoader().getResource(System.getProperty("template_save")).getPath() +
				File.separator + filename;

		boolean actual = new File(path).exists();
		assertEquals(true, actual);
	}



	// todo
	@Ignore
	@Test
	public void whenGosTplFulfillTemplateWithFullDataThenFilled() throws Exception {
		template.fulfillTemplate();

		verify(data).getGekHead();
		verify(data).listOfGekMembers();
		verify(data).getDate();
		verify(data).getStudent();
		verify(data).getStudentCourse();
		verify(data).getStudentVcr();
	}

	// todo
	@Ignore
	@Test(expected = TemplateException.class)
	public void whenDateIsMissingThenException() throws Exception {
		when(data.getDate()).thenReturn(null);

	}


	// tests when some data are missing


}
































