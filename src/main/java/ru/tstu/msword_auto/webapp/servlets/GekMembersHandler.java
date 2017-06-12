package ru.tstu.msword_auto.webapp.servlets;


import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;
import ru.tstu.msword_auto.dao.impl.GekMemberDao;
import ru.tstu.msword_auto.entity.GekMember;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;


public class GekMembersHandler extends AbstractTableHandler {
	static final String PARAM_MEMBER_ID = "gekMemberId";
	static final String PARAM_HEAD_ID = "gekHeadId";
	static final String PARAM_MEMBER = "member";
	static final String RESPONSE_ERROR_PARENT_TABLE_IS_EMPTY = "Укажите сначала данные в родительской таблице";	// TODO, fix message
	static final String RESPONSE_ERROR_MEMBER_ALREADY_EXISTS = "Один из членов ГЭК с таким именем уже есть в таблице";

	GekMemberDao dao = new GekMemberDao();


	// TODO new test to no such entity, fix other tests
	@Override
	protected String doList(HttpServletRequest request) throws HandlingException {
		int id = Integer.valueOf(request.getParameter(PARAM_HEAD_ID));

		try {
			List<GekMember> gekMembers = dao.readByForeignKey(id);
			return gson.toJson(gekMembers);
		} catch (DaoSystemException e) {
			throw new HandlingException(RESPONSE_ERROR_BD);
		} catch (NoSuchEntityException e) {
			return gson.toJson(Collections.emptyList());
		}

	}

	@Override
	protected String doCreate(HttpServletRequest request) throws HandlingException {
//		int idPrev = Integer.valueOf(request.getParameter(PARAM_MEMBER_ID));
//		int idNew = idPrev + 1;

		int headId = Integer.valueOf(request.getParameter(PARAM_HEAD_ID));
		String member = request.getParameter(PARAM_MEMBER);
		String validatedMember = validateMember(member);

		GekMember gekMember = new GekMember(headId, validatedMember);

		int generatedId = create(dao, gekMember, RESPONSE_ERROR_MEMBER_ALREADY_EXISTS);
		gekMember.setGekMemberId(generatedId);
		return gson.toJson(gekMember);
	}

	@Override
	protected String doUpdate(HttpServletRequest request) throws HandlingException {
		int id = Integer.valueOf(request.getParameter(PARAM_MEMBER_ID));
		int headId = Integer.valueOf(request.getParameter(PARAM_HEAD_ID));
		String member = request.getParameter(PARAM_MEMBER);
		String validatedMember = validateMember(member);

		GekMember gekMember = new GekMember(headId, validatedMember);

		return update(dao, gekMember, id, RESPONSE_ERROR_MEMBER_ALREADY_EXISTS);

	}

	@Override
	protected String doDelete(HttpServletRequest request) throws HandlingException {
		int id = Integer.valueOf(request.getParameter(PARAM_MEMBER_ID));

		try {
			dao.delete(id);
			return RESPONSE_OK;
		} catch (DaoSystemException e) {
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}


	private String validateMember(String member) {
		if(member == null) {
			return "";
		} else {
			return member;
		}
	}

}






















