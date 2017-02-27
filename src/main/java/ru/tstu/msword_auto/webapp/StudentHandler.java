package ru.tstu.msword_auto.webapp;


import ru.tstu.msword_automation.database.DaoException;
import ru.tstu.msword_automation.database.DatabaseService;
import ru.tstu.msword_automation.database.StudentDao;
import ru.tstu.msword_automation.database.datasets.Student;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StudentHandler extends AbstractTableHandler
{
	private static final String PARAM_ID = "id";
	private static final String PARAM_FIRST_NAME = "fName";
	private static final String PARAM_LAST_NAME = "lName";
	private static final String PARAM_MIDDLE_NAME = "midName";
	private AtomicInteger currentId;


	@Override
	protected String doList(HttpServletRequest request) throws HandlingException
	{
		try{
			StudentDao dao = DatabaseService.getInstance().getStudentDao();
			List<Student> data = dao.readAll();
			return gson.toJson(data);
		}catch(SQLException e){
			// TODO logging
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override
	protected String doCreate(HttpServletRequest request) throws HandlingException
	{
		try{
			StudentDao dao = DatabaseService.getInstance().getStudentDao();

			// temporary workaround TODO fix
			// extracts all rows and increments value
			if(currentId == null){
				currentId = new AtomicInteger(dao.readAll().size());
			}
			currentId.getAndIncrement();

			// TODO move parameter extraction outside try block
			String firstName = request.getParameter(PARAM_FIRST_NAME);
			String lastName = request.getParameter(PARAM_LAST_NAME);
			String middleName = request.getParameter(PARAM_MIDDLE_NAME);

			if(firstName.isEmpty() || lastName.isEmpty() || middleName.isEmpty()){
				throw new HandlingException(RESPONSE_ERROR_EMPTY_FIELD);
			}

			Student entity = new Student(currentId.intValue(), firstName, lastName, middleName);
			dao.create(entity);
			return gson.toJson(entity);
		}catch(SQLException | DaoException e){ // TODO separate handling
			// TODO logging
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override
	protected String doUpdate(HttpServletRequest request) throws HandlingException
	{
		// TODO move parameter extraction outside try block

		try{
			// TODO move parameter extraction outside try block

			int id = Integer.parseInt(request.getParameter(PARAM_ID));
			String firstName = request.getParameter(PARAM_FIRST_NAME);
			String lastName = request.getParameter(PARAM_LAST_NAME);
			String middleName = request.getParameter(PARAM_MIDDLE_NAME);

			if(firstName.isEmpty() || lastName.isEmpty() || middleName.isEmpty()){
				throw new HandlingException(RESPONSE_ERROR_EMPTY_FIELD);
			}

			StudentDao dao = DatabaseService.getInstance().getStudentDao();
			Student entity = new Student(0, firstName, lastName, middleName); // TODO fix 0 issue
			dao.update(id, entity);
			return RESPONSE_OK;
		}catch(SQLException e){
			// TODO logging
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override
	protected String doDelete(HttpServletRequest request) throws HandlingException
	{
		try{
			// TODO move parameter extraction outside try block

			int id = Integer.parseInt(request.getParameter(PARAM_ID));
			StudentDao dao = DatabaseService.getInstance().getStudentDao();
			dao.delete(id);

			return RESPONSE_OK;
		}catch(SQLException e){
			// TODO logging
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

}

































