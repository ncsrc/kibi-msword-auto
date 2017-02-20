package ru.tstu.msword_automation.automation;

import ru.tstu.msword_automation.automation.constants.ReplacementStrategy;
import ru.tstu.msword_automation.automation.constants.SaveFormat;
import ru.tstu.msword_automation.automation.constants.SaveOptions;
import ru.tstu.msword_automation.database.*;
import ru.tstu.msword_automation.database.datasets.*;

import java.io.File;
import java.sql.SQLException;
import java.util.List;


public class Template {
	private static final String TEMPLATE_GOS = "protocol_gos.docx";
	private static final String TEMPLATE_VCR = "protocol_vcr.doc";

	// todo move to enum or something
	private static final String TPL_DATE_DAY = "{Date_D}";
	private static final String TPL_DATE_MONTH = "{Date_M}";
	private static final String TPL_DATE_YEAR = "{Date_Y}";
	private static final String TPL_GEK_HEAD = "{GEK_Head}";
	private static final String TPL_GEK_SUBHEAD = "{GEK_Subhead}";
	private static final String TPL_GEK_SECRETARY = "{GEK_Secretary}";
	private static final String TPL_GEK_MEMBERS = "{GEK_Members}";
	private static final String TPL_STUDENT_NAME = "{Student_Name}";
	private static final String TPL_STUDENT_INITIALS = "{Student_Name_Initials}";
	private static final String TPL_STUDENT_COURSE_QUALIFICATION = "{Student_Qualification}";
	private static final String TPL_STUDENT_COURSE_NAME = "{Student_course}";	// code - name
	private static final String TPL_STUDENT_COURSE_SPEC = "{Course_specialization}";
	private static final String TPL_STUDENT_VCR_NAME = "{Student_VCR_Name}";
	private static final String TPL_STUDENT_VCR_HEAD = "{VCR_Head}";
	private static final String TPL_STUDENT_VCR_REVIEWER = "{VCR_Reviewer}";
	private Document doc;
	private String studentInitials;	// todo remove
	private final String templateLocation;
	private final String saveLocation;
	private String fileName;


	private Template(String templateType, String templateLocation, String saveLocation) {
		this.templateLocation = templateLocation; // make local ?
		this.saveLocation = saveLocation;
		String fullPath = templateLocation + File.separator + templateType;
		this.doc = new BasicDocument(fullPath);
	}

	static Template newGosTemplate(String templateLocation, String saveLocation) {
		//  todo implement



		return null;
	}

	static Template newVcrTemplate(String templateLocation, String saveLocation) {
		//  todo implement



		return null;
	}

	public void fillTemplate(int id) throws SQLException {

		// todo another method like getData(id) which returns class/map with data
		fillPersistent(id);
		// todo add setFileName(), remove from fill() that activity

		// todo fillNonPersistent()

		// todo to another method
		doc.saveAs(System.getProperty("template_folder"), getFilename(), SaveFormat.DOCX);

		// todo to another method
		doc.close(SaveOptions.DO_NOT_SAVE);
	}

//	public void close()
//	{
//		doc.close(SaveOptions.DO_NOT_SAVE);
//	}


	public String getFilename() {
		// todo remove magic string
		return studentInitials + " - Протокол ГЭК по защите ВКР.docx";
	}

	private void fillPersistent(int id) throws SQLException {
		fillDateData();
		fillGekData();
		fillStudentRelatedData(id);
	}

	private void fillDateData() throws SQLException {
		DateDao dao = DatabaseService.getInstance().getDateDao();
		Date date = dao.readAll().get(0); // there should be always only one row
		doc.replace(TPL_DATE_DAY, String.valueOf(date.getVcrDay()), ReplacementStrategy.REPLACE_ALL); // TODO encapsulate strategy  or set in constructor?
		doc.replace(TPL_DATE_MONTH, date.getVcrMonth(), ReplacementStrategy.REPLACE_ALL);

		String year = String.valueOf(date.getVcrYear()).substring(2, 4);
		doc.replace(TPL_DATE_YEAR, year, ReplacementStrategy.REPLACE_ALL);
	}

	private void fillStudentRelatedData(int id) throws SQLException {
		StudentDao dao = DatabaseService.getInstance().getStudentDao();
		Student student = dao.read(id);
		studentInitials = getStudentInitials(student);
		doc.replace(TPL_STUDENT_NAME, student.getFullName(), ReplacementStrategy.REPLACE_ALL);
		doc.replace(TPL_STUDENT_INITIALS, studentInitials, ReplacementStrategy.REPLACE_ALL);

		CourseDao courseDao = DatabaseService.getInstance().getCourseDao();
		Course course = courseDao.readByForeignKey(id).get(0); // 1:1
		doc.replace(TPL_STUDENT_COURSE_QUALIFICATION, course.getQualification(), ReplacementStrategy.REPLACE_ALL);
		doc.replace(TPL_STUDENT_COURSE_NAME, getCourseInfo(course), ReplacementStrategy.REPLACE_ALL);
		doc.replace(TPL_STUDENT_COURSE_SPEC, course.getProfile(), ReplacementStrategy.REPLACE_ALL);

		VcrDao vcrDao = DatabaseService.getInstance().getVcrDao();
		VCR vcr = vcrDao.readByForeignKey(id).get(0);	// 1:1
		doc.replace(TPL_STUDENT_VCR_NAME, vcr.getName(), ReplacementStrategy.REPLACE_ALL);
		doc.replace(TPL_STUDENT_VCR_HEAD, vcr.getHeadName(), ReplacementStrategy.REPLACE_ALL);
		doc.replace(TPL_STUDENT_VCR_REVIEWER, vcr.getReviewerName(), ReplacementStrategy.REPLACE_ALL);

	}

	private void fillGekData() throws SQLException {
		GekHeadDao gekHeadDao = DatabaseService.getInstance().getGekHeadDao();
		GekHead gekHead = gekHeadDao.readAll().get(0); // there should be only 1 row
		doc.replace(TPL_GEK_HEAD, gekHead.getHead(), ReplacementStrategy.REPLACE_ALL);
		doc.replace(TPL_GEK_SUBHEAD, gekHead.getSubhead(), ReplacementStrategy.REPLACE_ALL);
		doc.replace(TPL_GEK_SECRETARY, gekHead.getSecretary(), ReplacementStrategy.REPLACE_ALL);

		GekMemberDao gekMemberDao = DatabaseService.getInstance().getGekMemberDao();
		List<GekMember> gekMembers = gekMemberDao.readAll();
		doc.replace(TPL_GEK_MEMBERS, getFullMembersList(gekMembers), ReplacementStrategy.REPLACE_ALL);
		for(int i = 1; i <= 6; i++){
			doc.replace("{GEK_Member" + i + "}", gekMembers.get(i-1).getMember(), ReplacementStrategy.REPLACE_ALL);
		}

	}

	// TODO move to another class
	private String getFullMembersList(List<GekMember> gekMembers) {
		String list = gekMembers.get(0).getMember();
		for(int i = 1; i < gekMembers.size(); i++){
			list += ", ";
			list += gekMembers.get(i).getMember();
		}

		return list;
	}

	// TODO move to another class
	private String getStudentInitials(Student student) {
		return student.getLastName() + " " + student.getFirstName().substring(0, 1).toUpperCase() + ". " + student.getMiddleName().substring(0, 1).toUpperCase() + ".";
	}

	// TODO move to another module
	private String getCourseInfo(Course course) {
		return course.getCode() + " - " + course.getName();
	}



}


