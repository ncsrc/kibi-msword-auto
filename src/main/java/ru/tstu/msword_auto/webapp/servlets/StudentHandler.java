package ru.tstu.msword_auto.webapp.servlets;


import ru.tstu.msword_auto.dao.exceptions.AlreadyExistingException;
import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.impl.StudentDao;
import ru.tstu.msword_auto.entity.Student;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class StudentHandler extends AbstractTableHandler {
	static final String PARAM_ID = "studentId";

	static final String PARAM_FIRST_NAME_I = "firstNameI";
	static final String PARAM_LAST_NAME_I = "lastNameI";
	static final String PARAM_MIDDLE_NAME_I = "middleNameI";

	static final String PARAM_FIRST_NAME_R = "firstNameR";
	static final String PARAM_LAST_NAME_R = "lastNameR";
	static final String PARAM_MIDDLE_NAME_R = "middleNameR";

	static final String PARAM_FIRST_NAME_D = "firstNameD";
	static final String PARAM_LAST_NAME_D = "lastNameD";
	static final String PARAM_MIDDLE_NAME_D = "middleNameD";

	StudentDao dao = new StudentDao();


	@Override
	protected String doList(HttpServletRequest request) throws HandlingException {
		try {
			List<Student> students = dao.readAll();
			return gson.toJson(students);
		} catch (DaoSystemException e) {
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override
	protected String doCreate(HttpServletRequest request) throws HandlingException {
//		int idPrev = Integer.valueOf(request.getParameter(PARAM_ID));
//		int idNew = idPrev + 1; // autoincrements in database

		String firstNameI = request.getParameter(PARAM_FIRST_NAME_I);
		String lastNameI = request.getParameter(PARAM_LAST_NAME_I);
		String middleNameI = request.getParameter(PARAM_MIDDLE_NAME_I);
		validateStudentFio(firstNameI, lastNameI, middleNameI);

		String firstNameR = request.getParameter(PARAM_FIRST_NAME_R);
		String lastNameR = request.getParameter(PARAM_LAST_NAME_R);
		String middleNameR = request.getParameter(PARAM_MIDDLE_NAME_R);
		validateStudentFio(firstNameR, lastNameR, middleNameR);

		String firstNameD = request.getParameter(PARAM_FIRST_NAME_D);
		String lastNameD = request.getParameter(PARAM_LAST_NAME_D);
		String middleNameD = request.getParameter(PARAM_MIDDLE_NAME_D);
		validateStudentFio(firstNameD, lastNameD, middleNameD);

		Student student = new Student(
				firstNameI, lastNameI, middleNameI,
				firstNameR, lastNameR, middleNameR,
				firstNameD, lastNameD, middleNameD
		);

		try {
			int generatedId = dao.create(student);
			student.setStudentId(generatedId);

			return gson.toJson(student);
		} catch (DaoSystemException | AlreadyExistingException e) {	// AlreadyExistingException can't happen either way
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override
	protected String doUpdate(HttpServletRequest request) throws HandlingException {
		int id = Integer.valueOf(request.getParameter(PARAM_ID));

		String firstNameI = request.getParameter(PARAM_FIRST_NAME_I);
		String lastNameI = request.getParameter(PARAM_LAST_NAME_I);
		String middleNameI = request.getParameter(PARAM_MIDDLE_NAME_I);
		validateStudentFio(firstNameI, lastNameI, middleNameI);

		String firstNameR = request.getParameter(PARAM_FIRST_NAME_R);
		String lastNameR = request.getParameter(PARAM_LAST_NAME_R);
		String middleNameR = request.getParameter(PARAM_MIDDLE_NAME_R);
		validateStudentFio(firstNameR, lastNameR, middleNameR);

		String firstNameD = request.getParameter(PARAM_FIRST_NAME_D);
		String lastNameD = request.getParameter(PARAM_LAST_NAME_D);
		String middleNameD = request.getParameter(PARAM_MIDDLE_NAME_D);
		validateStudentFio(firstNameD, lastNameD, middleNameD);

		Student student = new Student(
				firstNameI, lastNameI, middleNameI,
				firstNameR, lastNameR, middleNameR,
				firstNameD, lastNameD, middleNameD
		);

		try {
			dao.update(id, student);
			return RESPONSE_OK;
		} catch (DaoSystemException | AlreadyExistingException e) { // AlreadyExistingException can't happen
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override
	protected String doDelete(HttpServletRequest request) throws HandlingException {
		int id = Integer.valueOf(request.getParameter(PARAM_ID));

		try {
			dao.delete(id);
			return RESPONSE_OK;
		} catch (DaoSystemException e) {
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}


	private void validateStudentFio(String firstName, String lastName, String middleName) throws HandlingException {
		if(firstName == null || lastName == null || middleName == null) {
			throw new HandlingException(RESPONSE_ERROR_EMPTY_FIELD);
		}

		if(firstName.isEmpty() || lastName.isEmpty() || middleName.isEmpty()) {
			throw new HandlingException(RESPONSE_ERROR_EMPTY_FIELD);
		}

	}


}

































