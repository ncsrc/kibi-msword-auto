package ru.tstu.msword_auto.webapp.servlets;

import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;
import ru.tstu.msword_auto.dao.impl.DateDao;
import ru.tstu.msword_auto.entity.Date;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DateHandler extends AbstractTableHandler {
	static final String PARAM_DATE_ID = "dateId";
	static final String PARAM_GROUP_ID = "subgroupId";
	static final String PARAM_GROUP = "groupName";
	static final String PARAM_DATE_GOS = "gosDate";
	static final String PARAM_DATE_VCR = "vcrDate";
	static final String RESPONSE_ERROR_EMPTY_GROUP_NAME = "Укажите группу.";
	static final String RESPONSE_ERROR_GROUP_NAME_ALREADY_EXISTS = "Даты по данной группе уже указаны.";

	DateDao dao = new DateDao();


	@Override
	protected String doList(HttpServletRequest request) throws HandlingException {
		try {
			List<Date> dates = dao.readAll();
			return gson.toJson(dates);
		} catch (DaoSystemException e) {
			throw new HandlingException(RESPONSE_ERROR_BD);
		}
	}

	@Override
	protected String doCreate(HttpServletRequest request) throws HandlingException {
		String groupName = request.getParameter(PARAM_GROUP);
		String dateGos = request.getParameter(PARAM_DATE_GOS);
		String dateVcr = request.getParameter(PARAM_DATE_VCR);
		Map<String, String> validatedParams = validateParameters(groupName, dateGos, dateVcr);

		int newGroupId = 0;
		try {
			int lastGroupId = dao.readByGroupName(groupName).size();
			newGroupId = lastGroupId + 1;
		} catch (DaoSystemException e) {
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

		Date date = new Date();
		date.setSubgroupId(newGroupId);
		date.setGroupName(validatedParams.get(PARAM_GROUP));
		date.setGosDate(validatedParams.get(PARAM_DATE_GOS));
		date.setVcrDate(validatedParams.get(PARAM_DATE_VCR));

		int insertedId = create(dao, date, RESPONSE_ERROR_GROUP_NAME_ALREADY_EXISTS);
		date.setDateId(insertedId);
		return gson.toJson(date);
	}

	@Override
	protected String doUpdate(HttpServletRequest request) throws HandlingException {
		int key = Integer.valueOf(request.getParameter(PARAM_DATE_ID));
		int groupId = 0;
		String group = request.getParameter(PARAM_GROUP);
		String dateGos = request.getParameter(PARAM_DATE_GOS);
		String dateVcr = request.getParameter(PARAM_DATE_VCR);
		Map<String, String> validatedParams = validateParameters(group, dateGos, dateVcr);

		boolean sameGroup = false;
		// TODO set manually, if different group -> check and update id
		try {
			groupId = dao.readByGroupName(group).size();
			String groupName = dao.read(key).getGroupName();
			if(group.equals(groupName)) {
				sameGroup = true;
			}
		} catch (DaoSystemException e) {
			throw new HandlingException(RESPONSE_ERROR_BD);
		} catch (NoSuchEntityException e) {
			/*NOP*/
		}

		if(!sameGroup) {
			groupId++;
		}

		Date date = new Date();
		date.setSubgroupId(groupId);
		date.setGroupName(validatedParams.get(PARAM_GROUP));
		date.setGosDate(validatedParams.get(PARAM_DATE_GOS));
		date.setVcrDate(validatedParams.get(PARAM_DATE_VCR));

		return update(dao, date, key, RESPONSE_ERROR_GROUP_NAME_ALREADY_EXISTS);
	}

	@Override
	protected String doDelete(HttpServletRequest request) throws HandlingException {
		int key = Integer.valueOf(request.getParameter(PARAM_DATE_ID));

		try {
			dao.delete(key);
			return RESPONSE_OK;
		} catch (DaoSystemException e) {
			throw new HandlingException(RESPONSE_ERROR_BD);
		}
	}


	private Map<String, String> validateParameters(String groupName, String gosDate, String vcrName) throws HandlingException {
		Map<String, String> validatedParams = new HashMap<>();
		String emptyString = "";

		if(groupName == null || groupName.isEmpty()) {
			throw new HandlingException(RESPONSE_ERROR_EMPTY_GROUP_NAME);
		}

		if(gosDate == null) {
			gosDate = emptyString;
		}

		if(vcrName == null) {
			vcrName = emptyString;
		}

		validatedParams.put(PARAM_GROUP, groupName);
		validatedParams.put(PARAM_DATE_GOS, gosDate);
		validatedParams.put(PARAM_DATE_VCR, vcrName);

		return validatedParams;
	}


}
