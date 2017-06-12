package ru.tstu.msword_auto.webapp;


import ru.tstu.msword_auto.dao.exceptions.DaoException;
import ru.tstu.msword_auto.entity.Course;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CourseHandler extends AbstractTableHandler {
	private static final String PARAM_OPTIONS = "options";
	private static final String PARAM_STUDENT_ID = "studentId";
	private static final String PARAM_COURSE_NAME = "name";
	private static final String PARAM_STUDENT_PROFILE = "profile";
	private static final String PARAM_STUDENT_QUALIFICATION = "qualification";

	private CourseDao dao = new CourseDao();


	@Override
	protected String doList(HttpServletRequest request) throws HandlingException {
		int studentId = Integer.parseInt(request.getParameter(PARAM_STUDENT_ID));

		try{
			List<Course> courses = dao.readByForeignKey(studentId);
			return gson.toJson(courses);
		}catch(SQLException e){
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
		} catch(SQLException | DaoException e){
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override	// now pk=fk, so TODO use pk instead
	protected String doUpdate(HttpServletRequest request) throws HandlingException {
		int id = Integer.parseInt(request.getParameter(PARAM_STUDENT_ID));
		String course = request.getParameter(PARAM_COURSE_NAME);
		String profile = request.getParameter(PARAM_STUDENT_PROFILE);
		String qualification = request.getParameter(PARAM_STUDENT_QUALIFICATION);
		String code = defineCode(course, qualification);
		Course entity = new Course(code, 0, qualification, course, profile);
		// TODO add empty field validation

		try {
			// TODO change to usual
			dao.updateByForeignKey(id, entity);

			return RESPONSE_OK;
		} catch(SQLException e){
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override	// now pk=fk
	protected String doDelete(HttpServletRequest request) throws HandlingException {
		int key = Integer.parseInt(request.getParameter(PARAM_STUDENT_ID));

		try {
			// TODO change to usual
			dao.deleteByForeignKey(key);

			return RESPONSE_OK;
		} catch(SQLException e){
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


}


























