package ru.tstu.msword_auto.webapp;


import ru.tstu.msword_auto.dao.exceptions.DaoException;
import ru.tstu.msword_auto.dao.impl.VcrDao;
import ru.tstu.msword_auto.entity.Vcr;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;


// TODO add exception if vcr_name already exist

public class VcrHandler extends AbstractTableHandler {
	private static final String PARAM_STUDENT_ID = "studentId";
	private static final String PARAM_NAME = "name";
	private static final String PARAM_HEAD = "head";
	private static final String PARAM_REVIEWER = "reviewer";

	private VcrDao dao = new VcrDao();


	@Override
	protected String doList(HttpServletRequest request) throws HandlingException {
		int id = Integer.parseInt(request.getParameter(PARAM_STUDENT_ID));

		try {
			List<Vcr> table = dao.readByForeignKey(id);
			return gson.toJson(table);
		}catch(SQLException e){
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override
	protected String doCreate(HttpServletRequest request) throws HandlingException {
		int id = Integer.parseInt(request.getParameter(PARAM_STUDENT_ID));
		String vcrName = request.getParameter(PARAM_NAME);
		String vcrHead = request.getParameter(PARAM_HEAD);
		String vcrReviewer = request.getParameter(PARAM_REVIEWER);
		// TODO add empty field validation

		Vcr entity = new Vcr(id, vcrName, vcrHead, vcrReviewer);
		try{
			dao.create(entity);

			return gson.toJson(entity);
		}catch(SQLException | DaoException e){	// TODO separate handling
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override
	protected String doUpdate(HttpServletRequest request) throws HandlingException {
		String key = request.getParameter(PARAM_KEY);
		String vcrName = request.getParameter(PARAM_NAME);
		String vcrHead = request.getParameter(PARAM_HEAD);
		String vcrReviewer = request.getParameter(PARAM_REVIEWER);

		Vcr entity = new Vcr(0, vcrName, vcrHead, vcrReviewer);
		try {
			dao.update(key, entity);

			return RESPONSE_OK;
		} catch(SQLException e){
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override
	protected String doDelete(HttpServletRequest request) throws HandlingException {
		String key = request.getParameter(PARAM_NAME);

		try {
			dao.delete(key);

			return RESPONSE_OK;
		} catch(SQLException e){
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

}

























