package ru.tstu.msword_auto.webapp;


import ru.tstu.msword_auto.dao.CourseDao;
import ru.tstu.msword_auto.dao.DaoException;
import ru.tstu.msword_auto.entity.Course;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseHandler extends AbstractTableHandler {
	private static final String PARAM_OPTIONS = "options";
	private static final String PARAM_STUDENT_ID = "studentId";
	private static final String PARAM_COURSE_NAME = "name";
	private static final String PARAM_STUDENT_PROFILE = "profile";	// TODO move to business-logic
	private static final String PARAM_STUDENT_QUALIFICATION = "qualification";

	private CourseDao dao;


	@Override
	protected String doList(HttpServletRequest request) throws HandlingException {
		int studentId = Integer.parseInt(request.getParameter(PARAM_STUDENT_ID));

		try{
			List<Course> courses = dao.readByForeignKey(studentId);		// know it's pk too, so TODO remove redundant functionality
			return gson.toJson(courses);
		}catch(SQLException e){
			// TODO logging
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override
	protected String doCreate(HttpServletRequest request) throws HandlingException {
		int studentId = Integer.parseInt(request.getParameter(PARAM_STUDENT_ID));
		String courseName = request.getParameter(PARAM_COURSE_NAME);
		String studentProfile = request.getParameter(PARAM_STUDENT_PROFILE);
		String studentQualification = request.getParameter(PARAM_STUDENT_QUALIFICATION);
		// TODO add empty field validation

		String courseCode = defineCode(courseName, studentQualification);

		Course entity = new Course(courseCode, studentId, studentQualification, courseName, studentProfile);
		try {
			dao.create(entity);

			return gson.toJson(entity);
		} catch(SQLException | DaoException e){ // TODO separate handling
			// TODO logging
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override	// now pk=fk, so TODO remove redundant functionality
	protected String doUpdate(HttpServletRequest request) throws HandlingException {
		int id = Integer.parseInt(request.getParameter(PARAM_STUDENT_ID));
		String course = request.getParameter(PARAM_COURSE_NAME);
		String profile = request.getParameter(PARAM_STUDENT_PROFILE);	// TODO move to business-logic
		String qualification = request.getParameter(PARAM_STUDENT_QUALIFICATION);
		String code = defineCode(course, qualification);
		Course entity = new Course(code, 0, qualification, course, profile); // TODO fix 0 issue
		// TODO add empty field validation

		try {
			dao.updateByForeignKey(id, entity);

			return RESPONSE_OK;
		} catch(SQLException e){
			// TODO logging
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override	// now pk=fk, so TODO remove redundant functionality
	protected String doDelete(HttpServletRequest request) throws HandlingException {
		int key = Integer.parseInt(request.getParameter(PARAM_STUDENT_ID));

		try {
			dao.deleteByForeignKey(key);

			return RESPONSE_OK;
		} catch(SQLException e){
			// TODO logging
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	// TODO: refactor(rename)
	@Override
	protected String handleOptions(HttpServletRequest req) {
		String request = req.getParameter(PARAM_OPTIONS);
		List<String> options = new ArrayList<>();
		if(request.equals("Бизнес-информатика")) {
			options.add("Информационные технологии в бизнесе");
		} else if(request.equals("Торговое дело")){
			options.add("Коммерция");
			options.add("Коммерческая деятельность");
		}

		return gson.toJson(options);
	}


	private String defineCode(String courseName, String studentQualification) {
		String BACHELOR_BI_CODE = "38.03.05";
		String BACHELOR_TD_CODE = "38.03.06";
		String MASTER_BI_CODE = "38.04.05";
		String MASTER_TD_CODE = "38.04.06";
		String courseCode = null;

		if(courseName.equals("Торговое дело") && studentQualification.equals("Бакалавр")){
			courseCode = BACHELOR_TD_CODE;
		}else if(courseName.equals("Торговое дело") && studentQualification.equals("Магистр")){
			courseCode = MASTER_TD_CODE;
		}else if(courseName.equals("Бизнес-информатика") && studentQualification.equals("Бакалавр")){
			courseCode = BACHELOR_BI_CODE;
		}else if(courseName.equals("Бизнес-информатика") && studentQualification.equals("Магистр")){
			courseCode = MASTER_BI_CODE;
		}

		return courseCode;
	}

}


























