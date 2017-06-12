package ru.tstu.msword_auto.webapp;


import ru.tstu.msword_auto.dao.exceptions.DaoException;
import ru.tstu.msword_auto.dao.impl.StudentDao;
import ru.tstu.msword_auto.entity.Student;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StudentHandler extends AbstractTableHandler {
	private static final String PARAM_ID = "id";

	private static final String PARAM_FIRST_NAME_I = "firstNameI";
	private static final String PARAM_LAST_NAME_I = "lastNameI";
	private static final String PARAM_MIDDLE_NAME_I = "middleNameI";

	private static final String PARAM_FIRST_NAME_R = "firstNameR";
	private static final String PARAM_LAST_NAME_R = "lastNameR";
	private static final String PARAM_MIDDLE_NAME_R = "middleNameR";

	private static final String PARAM_FIRST_NAME_D = "firstNameD";
	private static final String PARAM_LAST_NAME_D = "lastNameD";
	private static final String PARAM_MIDDLE_NAME_D = "middleNameD";

	private AtomicInteger currentId;
	private StudentDao dao = new StudentDao();


	@Override
	protected String doList(HttpServletRequest request) throws HandlingException {
		try {
			List<Student> data = dao.readAll();
			return gson.toJson(data);
		} catch(SQLException e){
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override
	protected String doCreate(HttpServletRequest request) throws HandlingException {
		try {
			// temporary workaround TODO fix
			// extracts all rows and increments value
			if(currentId == null){
				currentId = new AtomicInteger(dao.readAll().size());
			}
			currentId.getAndIncrement();

			// TODO move parameter extraction outside try block
			String firstNameI = request.getParameter(PARAM_FIRST_NAME_I);
			String lastNameI = request.getParameter(PARAM_LAST_NAME_I);
			String middleNameI = request.getParameter(PARAM_MIDDLE_NAME_I);

			String firstNameR = request.getParameter(PARAM_FIRST_NAME_R);
			String lastNameR = request.getParameter(PARAM_LAST_NAME_R);
			String middleNameR = request.getParameter(PARAM_MIDDLE_NAME_R);

			String firstNameD = request.getParameter(PARAM_FIRST_NAME_D);
			String lastNameD = request.getParameter(PARAM_LAST_NAME_D);
			String middleNameD = request.getParameter(PARAM_MIDDLE_NAME_D);

			if(firstNameI.isEmpty() || lastNameI.isEmpty() || middleNameI.isEmpty()
			   || firstNameR.isEmpty() || lastNameR.isEmpty() || middleNameR.isEmpty()
			   || firstNameD.isEmpty() || lastNameD.isEmpty() || middleNameD.isEmpty()){

				throw new HandlingException(RESPONSE_ERROR_EMPTY_FIELD);
			}

			Student entity = new Student(currentId.intValue(), firstNameI, lastNameI, middleNameI,
					 					 firstNameR, lastNameR, middleNameR,
					 					 firstNameD, lastNameD, middleNameD);

			dao.create(entity);
			return gson.toJson(entity);
		} catch(SQLException | DaoException e){ // TODO separate handling
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override
	protected String doUpdate(HttpServletRequest request) throws HandlingException {

		try {
			// TODO move parameter extraction outside try block

			int id = Integer.parseInt(request.getParameter(PARAM_ID));

			String firstNameI = request.getParameter(PARAM_FIRST_NAME_I);
			String lastNameI = request.getParameter(PARAM_LAST_NAME_I);
			String middleNameI = request.getParameter(PARAM_MIDDLE_NAME_I);

			String firstNameR = request.getParameter(PARAM_FIRST_NAME_R);
			String lastNameR = request.getParameter(PARAM_LAST_NAME_R);
			String middleNameR = request.getParameter(PARAM_MIDDLE_NAME_R);

			String firstNameD = request.getParameter(PARAM_FIRST_NAME_D);
			String lastNameD = request.getParameter(PARAM_LAST_NAME_D);
			String middleNameD = request.getParameter(PARAM_FIRST_NAME_D);

			if(firstNameI.isEmpty() || lastNameI.isEmpty() || middleNameI.isEmpty()
				|| firstNameR.isEmpty() || lastNameR.isEmpty() || middleNameR.isEmpty()
				|| firstNameD.isEmpty() || lastNameD.isEmpty() || middleNameD.isEmpty()) {

				throw new HandlingException(RESPONSE_ERROR_EMPTY_FIELD);
			}


			Student entity = new Student(0, firstNameI, lastNameI, middleNameI,
										 firstNameR, lastNameR, middleNameR,
										 firstNameD, lastNameD, middleNameD);

			dao.update(id, entity);
			return RESPONSE_OK;
		}catch(SQLException e){
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override
	protected String doDelete(HttpServletRequest request) throws HandlingException {
		try {
			// TODO move parameter extraction outside try block

			int id = Integer.parseInt(request.getParameter(PARAM_ID));
			dao.delete(id);

			return RESPONSE_OK;
		} catch(SQLException e){
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

}

































