package ru.tstu.msword_auto.automation;

import org.junit.*;
import org.junit.Test;
import ru.tstu.msword_auto.entity.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class TemplateTest {
	private Template template;
	private TemplateData data = mock(TemplateData.class);
	private Date date = new Date(1, 1,"group", "2012-11-11", "2012-11-11");
	private GekHead gekHead = new GekHead("asd", "asd", "asd", "asd");
	private List<GekMember> gekMembers = Arrays.asList(
			new GekMember(1, "asd"),
			new GekMember(2, "qwe"));

	private String gekMembersStr = "asd, qwe";
	private Student student = new Student("ads", "ads", "ads",
									   "adss", "adss", "adss", "adsd",
									   "adsd", "adsd");
	private Course course = new Course(1, 1, "asd");
	private Vcr vcr = new Vcr(1, "asd", "asd", "asd");


	/*
		Template contract:
		Template itself is a abstract class, which contains only static factory methods
		and inner classes, implementing Template

		sets options for template folders in static block from system properties, throws unchecked exception if not found

		static factory methods: newGosTemplate(TemplateData), newVcrTemplate(TemplateData)

		TemplateData resides in automation,
		but fills in webapp with entities. Throws exception if some entity missing or was not set.

		public fillTemplate() throws TemplateException(name ?), which fills it and saves.
		Filling conducts by extracting entities from TemplateData field, and if some data
		in some entity is missing, then exception thrown with message to client about what is missing.


		public getTemplateName(), returns filename

		public close() - closes underlying document. Make autocloseable ?

	 */


	@Before
	public void setUp() throws Exception {
		when(data.getDate()).thenReturn(this.date);
		when(data.getGekHead()).thenReturn(this.gekHead);
		when(data.getGekMembers()).thenReturn(this.gekMembers);
		when(data.getStudent()).thenReturn(this.student);
		when(data.getCourse()).thenReturn(this.course);
		when(data.getVcr()).thenReturn(this.vcr);
		when(data.getGekMembersInString()).thenReturn(this.gekMembersStr);

		String templatePath = this.getClass().getClassLoader().getResource("templates").getPath();
		String templateSavePath = this.getClass().getClassLoader().getResource("templates/filled").getPath();
		String jacobDllPath = this.getClass().getClassLoader().getResource("jacob-1.14.3-x64.dll").getPath();
		jacobDllPath = jacobDllPath.substring(1); // to remove "/"
		AutomationService.init(jacobDllPath, templatePath, templateSavePath);

		template = Template.newTemplate("gos", data);
	}

	@After
	public void tearDown() {
		template.close();
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
		template.fillTemplate();
		String actual = template.getFilename();

		// assert
		assertEquals(filename, actual);
	}

	@Test
	public void whenFulfillGosTplWithFullDataThenSaved() throws Exception {
		template.fillTemplate();
		String filename = template.getFilename();

		String path = AutomationService.templateSave + File.separator + filename;

		boolean actual = new File(path).exists();
		assertEquals(true, actual);
	}

	@Test
	public void whenVcrTplGetFilenameBeforeFillingThenCorrect() throws Exception {

		try(Template tpl = Template.newTemplate("vcr", data)) {
			String expected = "ads A. A. - Протокол по защите ВКР.docx";
			String actual = tpl.getFilename();
			assertNotNull(actual);
			assertEquals(expected, actual);
		}

	}

	@Test
	public void whenVcrTplGetFilenameAfterFillingThenCorrect() throws Exception {

		try(Template tpl = Template.newTemplate("vcr", data)) {
			String filename = "ads A. A. - Протокол по защите ВКР.docx";
			tpl.fillTemplate();
			String actual = tpl.getFilename();
			assertEquals(filename, actual);
		}

	}

	@Test
	public void whenFulfillVcrTplWithFullDataThenSaved() throws Exception {

		String filename;
		try(Template tpl = Template.newTemplate("vcr", data)) {
			tpl.fillTemplate();
			filename = tpl.getFilename();
		}

		String path = AutomationService.templateSave + File.separator + filename;

		boolean actual = new File(path).exists();
		assertEquals(true, actual);
	}

	@Test
	public void whenGosTplFulfillTemplateWithFullDataThenFilled() throws Exception {
		template.fillTemplate();

		verify(data).getGekHead();
		verify(data).getGekMembers();
		verify(data).getDate();
		verify(data, times(2)).getStudent();    // 1 in constructor
		verify(data).getCourse();
	}

    @Test
    public void whenVcrTplFulfillTemplateWithFullDataThenFilled() throws Exception {
        template.close();
	    template = Template.newTemplate("vcr", data);
	    template.fillTemplate();

        verify(data).getGekHead();
        verify(data).getGekMembers();
        verify(data).getDate();
        verify(data, times(4)).getStudent();    // 1 in constructor in setUp, actual 3
        verify(data, times(2)).getCourse();
        verify(data).getVcr();
    }

	@Test(expected = IllegalTypeException.class)
	public void whenInvalidTypePassedThenException() throws Exception {
		Template.newTemplate("wrong_type", data);

	}



}
































