package ru.tstu.msword_auto.webapp.servlets;


import ru.tstu.msword_auto.dao.exceptions.AlreadyExistingException;
import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;
import ru.tstu.msword_auto.dao.impl.VcrDao;
import ru.tstu.msword_auto.entity.Vcr;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VcrHandler extends AbstractTableHandler {
	static final String PARAM_STUDENT_ID = "studentId";
	static final String PARAM_NAME = "name";
	static final String PARAM_HEAD = "head";
	static final String PARAM_REVIEWER = "reviewer";
	static final String RESPONSE_ERROR_EMPTY_VCR_NAME = "Укажите тему ВКР.";
	static final String RESPONSE_ERROR_VCR_NAME_ALREADY_EXIST = "Введенная тема ВКР уже есть у кого-то из студентов.";
	static final String RESPONSE_ERROR_ROW_EXISTS = "У студента может быть только одна ВКР.";

	VcrDao dao = new VcrDao();


	@Override
	protected String doList(HttpServletRequest request) throws HandlingException {
		int id = Integer.parseInt(request.getParameter(PARAM_STUDENT_ID));

		try {
			List<Vcr> table = dao.readByForeignKey(id);
			return gson.toJson(table);
		} catch (DaoSystemException e) {
			throw new HandlingException(RESPONSE_ERROR_BD);
		} catch (NoSuchEntityException e) {
			return gson.toJson(Collections.EMPTY_LIST);
		}

	}

	@Override
	protected String doCreate(HttpServletRequest request) throws HandlingException {
		int id = Integer.parseInt(request.getParameter(PARAM_STUDENT_ID));
		String vcrName = request.getParameter(PARAM_NAME);
		String vcrHead = request.getParameter(PARAM_HEAD);
		String vcrReviewer = request.getParameter(PARAM_REVIEWER);
		Map<String, String> validatedParams = validateParameters(vcrName, vcrHead, vcrReviewer);

		Vcr entity = new Vcr(
				id,
				validatedParams.get(PARAM_NAME),
				validatedParams.get(PARAM_HEAD),
				validatedParams.get(PARAM_REVIEWER)
		);

		try {
			dao.create(entity);
		} catch (DaoSystemException e) {
			throw new HandlingException(RESPONSE_ERROR_BD);
		} catch (AlreadyExistingException e) {
			String errorMessage = e.getMessage();
			if(errorMessage.contains("vcr_exists")) {
				throw new HandlingException(RESPONSE_ERROR_VCR_NAME_ALREADY_EXIST);
			} else {
				throw new HandlingException(RESPONSE_ERROR_ROW_EXISTS);
			}
		}
		return gson.toJson(entity);
	}

	@Override
	protected String doUpdate(HttpServletRequest request) throws HandlingException {

		// TODO validate ?
		String key = request.getParameter(PARAM_KEY);

		String vcrName = request.getParameter(PARAM_NAME);
		String vcrHead = request.getParameter(PARAM_HEAD);
		String vcrReviewer = request.getParameter(PARAM_REVIEWER);

		Map<String, String> validatedParams = validateParameters(vcrName, vcrHead, vcrReviewer);

		Vcr entity = new Vcr();
		entity.setName(validatedParams.get(PARAM_NAME));
		entity.setHead(validatedParams.get(PARAM_HEAD));
		entity.setReviewer(validatedParams.get(PARAM_REVIEWER));

		return update(dao, entity, key, RESPONSE_ERROR_VCR_NAME_ALREADY_EXIST);
	}

	@Override
	protected String doDelete(HttpServletRequest request) throws HandlingException {

		// TODO validate ?
		String key = request.getParameter(PARAM_NAME);

		try {
			dao.delete(key);
			return RESPONSE_OK;
		} catch (DaoSystemException e) {
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}


	private Map<String, String> validateParameters(String vcrName, String vcrHead, String vcrReviewer) throws HandlingException {
		Map<String, String> results = new HashMap<>();
		if(vcrName == null || vcrName.isEmpty()) {
			throw new HandlingException(RESPONSE_ERROR_EMPTY_VCR_NAME);	// vcr should be specified and can't be avoided
		}

		if(vcrHead == null) {
			vcrHead = "";
		}

		if(vcrReviewer == null) {
			vcrReviewer = "";
		}

		results.put(PARAM_NAME, vcrName);
		results.put(PARAM_HEAD, vcrHead);
		results.put(PARAM_REVIEWER, vcrReviewer);

		return results;
	}


}

























