package ru.tstu.msword_auto.webapp;


import ru.tstu.msword_auto.dao.DaoException;
import ru.tstu.msword_auto.dao.GekHeadDao;
import ru.tstu.msword_auto.entity.GekHead;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;


public class GekHeadHandler extends AbstractTableHandler {
	private static final String PARAM_HEAD = "head";
	private static final String PARAM_SUBHEAD = "subhead";
	private static final String PARAM_SECRETARY = "secretary";

	private GekHeadDao dao = new GekHeadDao();


	@Override
	protected String doList(HttpServletRequest request) throws HandlingException {
		try {
			List<GekHead> gek = dao.readAll();
			return gson.toJson(gek);
		} catch(SQLException e){
			// TODO logging
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override
	protected String doCreate(HttpServletRequest request) throws HandlingException {
		try {
			// TODO move parameter extraction outside try block

			String head = request.getParameter(PARAM_HEAD);
			String subHead = request.getParameter(PARAM_SUBHEAD);
			String secretary = request.getParameter(PARAM_SECRETARY);

			// todo check business-logic correctness
			if(head.isEmpty() || subHead.isEmpty() || secretary.isEmpty()){
				throw new HandlingException(RESPONSE_ERROR_EMPTY_FIELD);
			}

			GekHead entity = new GekHead(head, subHead, secretary);
			dao.create(entity);

			return gson.toJson(entity);
		}catch(SQLException | DaoException e){ // TODO separate DaoException handling
			// TODO logging
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override
	protected String doUpdate(HttpServletRequest request) throws HandlingException {
		try {
			// TODO move parameter extraction outside try block

			String key = request.getParameter(PARAM_KEY);
			String head = request.getParameter(PARAM_HEAD);
			String subHead = request.getParameter(PARAM_SUBHEAD);
			String secretary = request.getParameter(PARAM_SECRETARY);

			// TODO check correctness
			if(head.isEmpty() || subHead.isEmpty() || secretary.isEmpty()){
				throw new HandlingException(RESPONSE_ERROR_EMPTY_FIELD);
			}

			dao.update(key, new GekHead(head, subHead, secretary));
			return RESPONSE_OK;
		} catch(SQLException e){
			// TODO logging
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override
	protected String doDelete(HttpServletRequest request) throws HandlingException {
		try {
			// TODO move parameter extraction outside try block

			String head = request.getParameter(PARAM_HEAD);
			dao.delete(head);
			return RESPONSE_OK;
		} catch(SQLException e){
			// TODO logging
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}


}























