package ru.tstu.msword_auto.webapp.servlets;


import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;
import ru.tstu.msword_auto.dao.impl.CourseDao;
import ru.tstu.msword_auto.dao.impl.DateDao;
import ru.tstu.msword_auto.entity.*;
import ru.tstu.msword_auto.entity.Date;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


public class CourseHandler extends AbstractTableHandler {
	static final String PARAM_OPTIONS = "options";
	static final String PARAM_OPTIONS_PROFILE_VALUE = "value";
	static final String PARAM_STUDENT_ID = "studentId";
	static final String PARAM_SUBGROUP_ID = "subgroupId";
	static final String PARAM_GROUP_NAME = "groupName";
	static final String PARAM_COURSE_NAME = "courseName"; // TODO fix in tables
	static final String PARAM_STUDENT_PROFILE = "profile";
	static final String PARAM_STUDENT_QUALIFICATION = "qualification";
	static final String RESPONSE_ERROR_ALREADY_EXISTS = "У студента может быть только одно направление обучения.";

	CourseDao dao = new CourseDao();


	@Override
	protected String doList(HttpServletRequest request) throws HandlingException {
		int studentId = Integer.parseInt(request.getParameter(PARAM_STUDENT_ID));

		try {
			List<Course> courses = dao.readByForeignKey(studentId);
			return gson.toJson(courses);
		} catch (DaoSystemException e) {
			throw new HandlingException(RESPONSE_ERROR_BD);
		} catch (NoSuchEntityException e) {
			return gson.toJson(Collections.emptyList());
		}

	}

	@Override
	protected String doCreate(HttpServletRequest request) throws HandlingException {
		int studentId = Integer.parseInt(request.getParameter(PARAM_STUDENT_ID));
		int subgroupId = Integer.valueOf(request.getParameter(PARAM_SUBGROUP_ID));
		String groupName = request.getParameter(PARAM_GROUP_NAME);
		String courseName = request.getParameter(PARAM_COURSE_NAME);
		String profile = request.getParameter(PARAM_STUDENT_PROFILE);
		String qualification = request.getParameter(PARAM_STUDENT_QUALIFICATION);
		Map<String, String> validatedParams = validateParams(groupName, courseName, profile, qualification);

		Course course = new Course(
				studentId,
				subgroupId,
				validatedParams.get(PARAM_GROUP_NAME),
				validatedParams.get(PARAM_STUDENT_QUALIFICATION),
				validatedParams.get(PARAM_COURSE_NAME),
				validatedParams.get(PARAM_STUDENT_PROFILE)
		);

		create(dao, course, RESPONSE_ERROR_ALREADY_EXISTS);
		return gson.toJson(course);
	}

	@Override
	protected String doUpdate(HttpServletRequest request) throws HandlingException {
		int id = Integer.parseInt(request.getParameter(PARAM_STUDENT_ID));
		int subgroupId = Integer.valueOf(request.getParameter(PARAM_SUBGROUP_ID));
		String groupName = request.getParameter(PARAM_GROUP_NAME);
		String courseName = request.getParameter(PARAM_COURSE_NAME);
		String profile = request.getParameter(PARAM_STUDENT_PROFILE);
		String qualification = request.getParameter(PARAM_STUDENT_QUALIFICATION);
		Map<String, String> validatedParams = validateParams(groupName, courseName, profile, qualification);

		Course course = new Course();
		course.setSubgroupId(subgroupId);
		course.setGroupName(validatedParams.get(PARAM_GROUP_NAME));
		course.setCourseName(validatedParams.get(PARAM_COURSE_NAME));
		course.setProfile(validatedParams.get(PARAM_STUDENT_PROFILE));
		course.setQualification(validatedParams.get(PARAM_STUDENT_QUALIFICATION));

		return update(dao, course, id, RESPONSE_ERROR_ALREADY_EXISTS);
	}

	@Override
	protected String doDelete(HttpServletRequest request) throws HandlingException {
		int id = Integer.parseInt(request.getParameter(PARAM_STUDENT_ID));

		try {
			dao.delete(id);
			return RESPONSE_OK;
		} catch (DaoSystemException e) {
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override
	protected String handleOptions(HttpServletRequest req) {
		String request = req.getParameter(PARAM_OPTIONS);
		List<String> options = new ArrayList<>();

		if("profile".equals(request)) {
			String value = req.getParameter(PARAM_OPTIONS_PROFILE_VALUE);
			if(value.equals("Бизнес-информатика")) {
				options.add("Информационные технологии в бизнесе");
			} else if(value.equals("Торговое дело")){
				options.add("Коммерция");
				options.add("Коммерческая деятельность");
			}
		} else if("group".equals(request)) {
			String value = req.getParameter(PARAM_OPTIONS_PROFILE_VALUE);
			DateDao dateDao = new DateDao();
			try {
				for(Date date : dateDao.readByGroupName(value)) {
					options.add(String.valueOf(date.getSubgroupId()));
				}
			} catch (DaoSystemException e) {
				// return empty list
			}
		}

		return gson.toJson(options);
	}


	private Map<String, String> validateParams(String groupName, String courseName, String profile, String qualification) {
		Map<String, String> validatedParams = new HashMap<>();
		String emptyString = "";

		if(groupName == null) {
			groupName = emptyString;
		}

		if(courseName == null) {
			courseName = emptyString;
		}

		if(profile == null) {
			profile = emptyString;
		}

		if(qualification == null) {
			qualification = emptyString;
		}

		validatedParams.put(PARAM_GROUP_NAME, groupName);
		validatedParams.put(PARAM_COURSE_NAME, courseName);
		validatedParams.put(PARAM_STUDENT_PROFILE, profile);
		validatedParams.put(PARAM_STUDENT_QUALIFICATION, qualification);

		return validatedParams;
	}

}


























