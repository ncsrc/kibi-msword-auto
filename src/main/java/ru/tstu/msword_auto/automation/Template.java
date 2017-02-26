package ru.tstu.msword_auto.automation;


import com.jacob.com.LibraryLoader;
import ru.tstu.msword_auto.automation.constants.SaveOptions;
import ru.tstu.msword_auto.automation.entity_aggregation.TemplateData;
import ru.tstu.msword_auto.entity.*;

import java.util.List;

public abstract class Template implements AutoCloseable {
//	private static final String TEMPLATE_GOS = "protocol_gos.docx";
    private static final String TEMPLATE_VCR = "protocol_vcr.doc";
    private static final String FOLDER_SOURCE;
    private static final String FOLDER_SAVE;
//	protected final TemplateData data;
    protected Document doc;


    private Template() {

	}

	public static Template newGosTemplate() {
		return new GosTemplate();
	}

	public static Template newVcrTemplate() {

		return null;
	}


	public abstract void fulfillTemplate(TemplateData data) throws TemplateException;

	// e.g. initials - Протокол ГЭК по защите ВКР.docx
	public abstract String getFilename();


	public void close() {
		doc.close(SaveOptions.DO_NOT_SAVE);
	}


	private static class GosTemplate extends Template {
		private static final String TEMPLATE_NAME = "protocol_gos.docx";
		private String filename;

		public GosTemplate() {
			String templateLocation = this.getClass().getClassLoader().getResource(TEMPLATE_NAME).getPath();
			this.doc = new BasicDocument(templateLocation);
		}

		@Override
		public void fulfillTemplate(TemplateData data) throws TemplateException {
			Student studentInfo = data.getStudent();
			String fullName = studentInfo.getFullName();
			doc.replace(TemplateRecord.STUDENT_NAME.getValue(), fullName);

			Date dateInfo = data.getDate();
			String day = String.valueOf(dateInfo.getGosDay());
			String month = dateInfo.getGosMonth();
			String year = String.valueOf(dateInfo.getGosYear());
			doc.replace(TemplateRecord.DATE_DAY.getValue(), day);
			doc.replace(TemplateRecord.DATE_MONTH.getValue(), month);
			doc.replace(TemplateRecord.DATE_YEAR.getValue(), year);

			Course course = data.getStudentCourse();
			String courseInfo = course.getInfo();
			String profile = course.getProfile();
			doc.replace(TemplateRecord.STUDENT_COURSE_NAME.getValue(), courseInfo);
			doc.replace(TemplateRecord.STUDENT_COURSE_SPEC.getValue(), profile);

			GekHead headInfo = data.getGekHead();
			String head = headInfo.getHead();
			String subHead = headInfo.getSubhead();
			String secretary = headInfo.getSecretary();
			doc.replace(TemplateRecord.GEK_HEAD.getValue(), head);
			doc.replace(TemplateRecord.GEK_SUBHEAD.getValue(), subHead);
			doc.replace(TemplateRecord.GEK_SECRETARY.getValue(), secretary);

			List<GekMember> members = data.listOfGekMembers();
			for(int i = 1; i <= members.size(); i++) {
				doc.replace("{GEK_Member" + i + "}", members.get(i-1).getMember());
			}

		}

		@Override
		public String getFilename() {
			return this.filename;
		}
	}


	static {
		// jacob dll initialization
		ClassLoader classLoader = Template.class.getClassLoader();
		String jacobDll;
		if(System.getProperty("os.arch").equals("amd64")) {
			jacobDll = classLoader.getResource("jacob-1.14.3-x64.dll").getPath();
		} else {
			jacobDll = classLoader.getResource("jacob-1.14.3-x86.dll").getPath();
		}
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, jacobDll);

		// folders initialization
		FOLDER_SOURCE = System.getProperty("template_source");
		FOLDER_SAVE = System.getProperty("template_save");
	}


}

























