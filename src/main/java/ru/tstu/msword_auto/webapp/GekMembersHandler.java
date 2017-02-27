package ru.tstu.msword_auto.webapp;


import ru.tstu.msword_automation.database.DaoException;
import ru.tstu.msword_automation.database.DatabaseService;
import ru.tstu.msword_automation.database.GekMemberDao;
import ru.tstu.msword_automation.database.datasets.GekHead;
import ru.tstu.msword_automation.database.datasets.GekMember;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;


// TODO add validation that input is proper(Фамилия И.О.)

public class GekMembersHandler extends AbstractTableHandler
{
	private static final String PARAM_MEMBER = "member";
	private static final String RESPONSE_ERROR_PARENT_TABLE_IS_EMPTY = "Укажите сначала данные в родительской таблице";
	private static final String RESPONSE_ERROR_DUPLICATE_ENTRY = "Один из членов ГЭК с таким именем уже есть в таблице";


	@Override
	protected String doList(HttpServletRequest request) throws HandlingException
	{
		try{
			GekMemberDao dao = DatabaseService.getInstance().getGekMemberDao();
			List<GekMember> gekMembers = dao.readAll();
			return gson.toJson(gekMembers);
		}catch(SQLException e){
			// TODO logging
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override
	protected String doCreate(HttpServletRequest request) throws HandlingException
	{
		try{
			// TODO move parameter extraction outside try block

			GekMemberDao dao = DatabaseService.getInstance().getGekMemberDao();
			String head;
			String member = request.getParameter(PARAM_MEMBER);

			// reads parent table to check if it's empty, throws error message to user if it is,
			// otherwise gets its necessary data
			// TODO think of more proper way
			List<GekHead> parentTable = DatabaseService.getInstance().getGekHeadDao().readAll();
			if(parentTable.isEmpty()){
				throw new HandlingException(RESPONSE_ERROR_PARENT_TABLE_IS_EMPTY);
			}
			head = parentTable.get(0).getHead();

			if(member.isEmpty()){
				throw new HandlingException(RESPONSE_ERROR_EMPTY_FIELD);
			}

			GekMember newMember = new GekMember(head, member);
			dao.create(newMember);

			return gson.toJson(newMember);
		}catch(SQLException | DaoException e){ // TODO separate exception handling
			// TODO logging
			if(e.getMessage().contains("Duplicate entry")){
				throw new HandlingException(RESPONSE_ERROR_DUPLICATE_ENTRY);
			}

			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

	@Override
	protected String doUpdate(HttpServletRequest request) throws HandlingException
	{
		try{
			// TODO move parameter extraction outside try block

			GekMemberDao dao = DatabaseService.getInstance().getGekMemberDao();
			String key = request.getParameter(PARAM_KEY);
			String member = request.getParameter(PARAM_MEMBER);

			if(member.isEmpty()){
				throw new HandlingException(RESPONSE_ERROR_EMPTY_FIELD);
			}

			GekMember entity = new GekMember("", member); // TODO fix "" issue
			dao.update(key, entity);

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

			GekMemberDao dao = DatabaseService.getInstance().getGekMemberDao();
			String member = request.getParameter(PARAM_MEMBER);
			dao.delete(member);

			return RESPONSE_OK;
		}catch(SQLException e){
			// TODO logging
			throw new HandlingException(RESPONSE_ERROR_BD);
		}

	}

}






















