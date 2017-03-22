package ru.tstu.msword_auto.automation;


import ru.tstu.msword_auto.automation.constants.SaveFormat;
import ru.tstu.msword_auto.automation.entity_aggregation.TemplateData;
import ru.tstu.msword_auto.entity.*;

import java.util.List;

// todo javadoc
public abstract class Template implements AutoCloseable {
    protected Document doc;
	protected String filename;
	protected TemplateData data;


    private Template(TemplateData data) {
		this.data = data;
	}


	// -- Factory methods, returning concrete templates --

	// type param resolved in webapp
	public static Template newTemplate(String type, TemplateData data) {
    	if ("gos".equals(type)) {
    		return new GosTemplate(data);
		} else if ("vcr".equals(type)) {
    		return new VcrTemplate(data);
		} else {
    		throw new TemplateRuntimeException(type + " - invalid type of template");
		}
	}


	// -- Protected common methods --

	protected void fillCommonData() {
    	this.fillStudentData();
    	this.fillGekData();
	}

	protected void save() {
		doc.saveAs(AutomationService.templateSave, this.filename, SaveFormat.DOCX);
	}


	// -- Abstract methods --

	protected abstract void fillDate();

	// todo add throws TemplateException later, and checks; or remove if not needed
	public abstract void fulfillTemplate() throws TemplateException;

	// e.g. initials - Протокол ГЭК по защите ВКР.docx
	public String getFilename() {
		return this.filename;
	}


	// -- Autocloseable --

	public void close() {
		doc.close();
	}


	// -- Private methods --

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
			String templatePath = AutomationService.templateSource + "/" + TEMPLATE_NAME;

			this.doc = new BasicDocument(templatePath);
		}

		@Override
		public void fulfillTemplate() throws TemplateException {
			this.fillCommonData();
			this.fillDate();
			this.save();
		}

		@Override
		protected void fillDate() {
			ru.tstu.msword_auto.automation.entity_aggregation.Date date = data.getDate();
			String day = date.getGosDay();
			String month = date.getGosMonth();
			String year = date.getGosYear();
			doc.replace(TemplateRecord.DATE_DAY, day);
			doc.replace(TemplateRecord.DATE_MONTH, month);
			doc.replace(TemplateRecord.DATE_YEAR, year);
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
			String templatePath = AutomationService.templateSource + "/" + TEMPLATE_NAME;

			this.doc = new BasicDocument(templatePath);
		}

		@Override
		public void fulfillTemplate() throws TemplateException {
			this.fillCommonData();
			this.fillDate();

			// student/course vcr-specific data
			Student student = this.data.getStudent();
			String fullNameT = student.getFullNameD();
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

		@Override
		protected void fillDate() {
			ru.tstu.msword_auto.automation.entity_aggregation.Date date = data.getDate();
			String day = date.getVcrDay();
			String month = date.getVcrMonth();
			String year = date.getVcrYear();
			doc.replace(TemplateRecord.DATE_DAY, day);
			doc.replace(TemplateRecord.DATE_MONTH, month);
			doc.replace(TemplateRecord.DATE_YEAR, year);
		}

	}



}

























