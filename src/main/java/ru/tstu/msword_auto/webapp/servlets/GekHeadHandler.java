package ru.tstu.msword_auto.webapp.servlets;


import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.impl.GekHeadDao;
import ru.tstu.msword_auto.entity.GekHead;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GekHeadHandler extends AbstractTableHandler {
	static final String PARAM_ID = "gekId";
	static final String PARAM_COURSE_NAME = "courseName";
	static final String PARAM_HEAD = "head";
	static final String PARAM_SUBHEAD = "subhead";
	static final String PARAM_SECRETARY = "secretary";
	static final String RESPONSE_ERROR_EMPTY_COURSE_NAME = "Укажите образовательную программу.";
	static final String RESPONSE_ERROR_COURSE_NAME_ALREADY_EXIST = "Члены ГЭК по данной образовательной программе уже указаны.";

	GekHeadDao dao = new GekHeadDao();


	@Override
	protected String doList(HttpServletRequest request) throws HandlingException {
		try {
			List<GekHead> gek = dao.readAll();
			return gson.toJson(gek);
		} catch (DaoSystemException e) {
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override
	protected String doCreate(HttpServletRequest request) throws HandlingException {
//		int idPrev = Integer.valueOf(request.getParameter(PARAM_ID));
//		int idNew = idPrev + 1;

		String courseName = request.getParameter(PARAM_COURSE_NAME);
		String head = request.getParameter(PARAM_HEAD);
		String subHead = request.getParameter(PARAM_SUBHEAD);
		String secretary = request.getParameter(PARAM_SECRETARY);
		Map<String, String> validatedParams = validateParams(courseName, head, subHead, secretary);

		GekHead gekHead = new GekHead(
				courseName,
				validatedParams.get(PARAM_HEAD),
				validatedParams.get(PARAM_SUBHEAD),
				validatedParams.get(PARAM_SECRETARY)
		);

		int generatedId = create(dao, gekHead, RESPONSE_ERROR_COURSE_NAME_ALREADY_EXIST);
		gekHead.setGekId(generatedId);
		return gson.toJson(gekHead);
	}

	@Override
	protected String doUpdate(HttpServletRequest request) throws HandlingException {
		int id = Integer.valueOf(request.getParameter(PARAM_ID));
		String courseName = request.getParameter(PARAM_COURSE_NAME);
		String head = request.getParameter(PARAM_HEAD);
		String subHead = request.getParameter(PARAM_SUBHEAD);
		String secretary = request.getParameter(PARAM_SECRETARY);
		Map<String, String> validatedParams = validateParams(courseName, head, subHead, secretary);

		GekHead gekHead = new GekHead(
				courseName,
				validatedParams.get(PARAM_HEAD),
				validatedParams.get(PARAM_SUBHEAD),
				validatedParams.get(PARAM_SECRETARY)
		);

		return update(dao, gekHead, id, RESPONSE_ERROR_COURSE_NAME_ALREADY_EXIST);
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


	private Map<String, String> validateParams(String courseName, String head, String subHead, String secretary) throws HandlingException {
		Map<String, String> validatedParams = new HashMap<>();

		if(courseName == null || courseName.isEmpty()) {
			throw new HandlingException(RESPONSE_ERROR_EMPTY_COURSE_NAME);
		}

		String emptyString = "";
		if(head == null) {
			head = emptyString;
		}

		if(subHead == null) {
			subHead = emptyString;
		}

		if(secretary == null) {
			secretary = emptyString;
		}

		validatedParams.put(PARAM_HEAD, head);
		validatedParams.put(PARAM_SUBHEAD, subHead);
		validatedParams.put(PARAM_SECRETARY, secretary);

		return validatedParams;
	}

}























