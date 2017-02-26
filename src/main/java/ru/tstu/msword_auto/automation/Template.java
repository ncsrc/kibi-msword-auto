package ru.tstu.msword_auto.automation;


import com.jacob.com.LibraryLoader;
import ru.tstu.msword_auto.automation.constants.SaveFormat;
import ru.tstu.msword_auto.automation.constants.SaveOptions;
import ru.tstu.msword_auto.automation.entity_aggregation.TemplateData;
import ru.tstu.msword_auto.entity.*;

import java.util.List;

public abstract class Template implements AutoCloseable {
    private static final String FOLDER_SOURCE;
    private static final String FOLDER_SAVE;
    protected Document doc;
	protected String filename;
	protected TemplateData data;


    private Template(TemplateData data) {
		this.data = data;
	}

	// -- Static factory methods, returning concrete templates --

	public static Template newGosTemplate(TemplateData data) {
		return new GosTemplate(data);
	}

	public static Template newVcrTemplate(TemplateData data) {
		return new VcrTemplate(data);
	}


	// -- Protected common methods --

	protected void fillCommonData() {
    	this.fillDate();
    	this.fillStudentData();
    	this.fillGekData();
	}

	protected void save() {
		String saveLocation = this.getClass().getClassLoader().getResource(FOLDER_SAVE).getPath();
		doc.saveAs(saveLocation, this.filename, SaveFormat.DOCX);
	}


	// -- Abstract methods --

	// todo add throws TemplateException later, and checks
	public abstract void fulfillTemplate() throws TemplateException;

	// e.g. initials - Протокол ГЭК по защите ВКР.docx
	public String getFilename() {
		return this.filename;
	}


	// -- Autocloseable --

	public void close() {
		doc.close(SaveOptions.DO_NOT_SAVE);
	}


	// -- Private methods --

	private void fillDate() {
		Date dateInfo = data.getDate();
		String day = String.valueOf(dateInfo.getGosDay());
		String month = dateInfo.getGosMonth();
		String year = String.valueOf(dateInfo.getGosYear()).substring(2);
		doc.replace(TemplateRecord.DATE_DAY, day);
		doc.replace(TemplateRecord.DATE_MONTH, month);
		doc.replace(TemplateRecord.DATE_YEAR, year);
	}

	// fills common: fio in I case and in R case; course info and course profile
	private void fillStudentData() {
    	Student student = data.getStudent();
		String fullNameI = student.getFullNameI();
		doc.replace(TemplateRecord.STUDENT_NAME_I, fullNameI);

		String fullNameR = student.getFullNameR();
		doc.replace(TemplateRecord.STUDENT_NAME_R, fullNameR);

		Course course = data.getStudentCourse();
		String courseInfo = course.getInfo();
		String profile = course.getProfile();
		doc.replace(TemplateRecord.STUDENT_COURSE_NAME, courseInfo);
		doc.replace(TemplateRecord.STUDENT_COURSE_SPEC, profile);
	}

	private void fillGekData() {
		GekHead headInfo = data.getGekHead();
		String head = headInfo.getHead();
		String subHead = headInfo.getSubhead();
		String secretary = headInfo.getSecretary();
		doc.replace(TemplateRecord.GEK_HEAD, head);
		doc.replace(TemplateRecord.GEK_SUBHEAD, subHead);
		doc.replace(TemplateRecord.GEK_SECRETARY, secretary);

		List<GekMember> members = data.listOfGekMembers();
		for(int i = 1; i <= members.size(); i++) {
			doc.replace("{GEK_Member" + i + "}", members.get(i-1).getMember());
		}

		String fullMembersList = data.getGekMembersListInString();
		doc.replace(TemplateRecord.GEK_MEMBERS, fullMembersList);
}




	// -- Inner implementations --

	private static class GosTemplate extends Template {
		private static final String TEMPLATE_NAME = "protocol_gos.docx";


		public GosTemplate(TemplateData data) {
			super(data);

			// set filename
			Student studentInfo = data.getStudent();
			this.filename = studentInfo.getInitials() + " - Протокол по приему гос экзамена.docx";

			// open document
			String templatePath = FOLDER_SOURCE + "/" + TEMPLATE_NAME;
			String templateLocation = this.getClass().getClassLoader().getResource(templatePath).getPath();
			this.doc = new BasicDocument(templateLocation);
		}

		@Override
		public void fulfillTemplate() throws TemplateException {
			this.fillCommonData();
			this.save();
		}

	}


	private static class VcrTemplate extends Template {
		private static final String TEMPLATE_NAME = "protocol_vcr.doc";

		public VcrTemplate(TemplateData data) {
			super(data);

			// set filename
			Student studentInfo = data.getStudent();
			this.filename = studentInfo.getInitials() + " - Протокол по защите ВКР.docx";

			// open document
			String templatePath = FOLDER_SOURCE + "/" + TEMPLATE_NAME;
			String templateLocation = this.getClass().getClassLoader().getResource(templatePath).getPath();
			this.doc = new BasicDocument(templateLocation);
		}

		@Override
		public void fulfillTemplate() throws TemplateException {
			this.fillCommonData();

			// student/course vcr-specific data
			Student student = this.data.getStudent();
			String fullNameT = student.getFullNameT();
			String initials = student.getInitials();
			Course course = this.data.getStudentCourse();
			String qualification = course.getQualification();
			this.doc.replace(TemplateRecord.STUDENT_NAME_T, fullNameT);
			this.doc.replace(TemplateRecord.STUDENT_INITIALS, initials);
			this.doc.replace(TemplateRecord.STUDENT_COURSE_QUALIFICATION, qualification);

			// vcr data
			VCR vcr = this.data.getStudentVcr();
			String vcrName = vcr.getName();
			String vcrHead = vcr.getHeadName();
			String vcrReviewer = vcr.getReviewerName();
			this.doc.replace(TemplateRecord.STUDENT_VCR_NAME, vcrName);
			this.doc.replace(TemplateRecord.STUDENT_VCR_HEAD, vcrHead);
			this.doc.replace(TemplateRecord.STUDENT_VCR_REVIEWER, vcrReviewer);

			this.save();
		}

	}


	// -- Static init block --

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

























