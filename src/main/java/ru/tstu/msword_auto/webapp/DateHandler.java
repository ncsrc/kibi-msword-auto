package ru.tstu.msword_auto.webapp;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

// TODO add exception in creating second row
// TODO add default value and restrict creation

public class DateHandler extends AbstractTableHandler
{
	private static final String PARAM_ID = "id";
	private static final String PARAM_DATE_GOS = "gosDate";
	private static final String PARAM_DATE_VCR = "vcrDate";
	private static final String RESPONSE_ERROR_DATE_FORMAT = "Неправильный формат даты. Должен быть: yyyy-mm-dd";
	private static final String RESPONSE_ERROR_EMPTY_DATE = "Укажите обе даты";


	@Override
	protected String doList(HttpServletRequest request) throws HandlingException
	{
		// can't move dao extraction to field because of multithreading issues of servlets
		try{
			DateDao dao = DatabaseService.getInstance().getDateDao();
			List<Date> dates = dao.readAll();
			return gson.toJson(dates);
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

			DateDao dao = DatabaseService.getInstance().getDateDao();

			String dateGos = request.getParameter(PARAM_DATE_GOS);
			String dateVcr = request.getParameter(PARAM_DATE_VCR);
			if(dateGos.isEmpty() || dateVcr.isEmpty()){
				throw new HandlingException(RESPONSE_ERROR_EMPTY_DATE);
			}

			Date dateEntity = new Date(0, dateGos, dateVcr); // TODO fix 0 issue
			dao.create(dateEntity);

			dateEntity = new Date(dao.lastInsertId(), dateGos, dateVcr); // TODO fix necessity of making new instance
			return gson.toJson(dateEntity);
		}catch(SQLException | DaoException e){	// TODO separate DaoException handling
			// TODO logging
			throw new HandlingException(RESPONSE_ERROR_BD);
		}catch(DateFormatException e){
			// TODO logging
			throw new HandlingException(RESPONSE_ERROR_DATE_FORMAT);
		}
	}

	@Override
	protected String doUpdate(HttpServletRequest request) throws HandlingException
	{
		try{
			// TODO move parameter extraction outside try block

			DateDao dao = DatabaseService.getInstance().getDateDao();
			int id = Integer.parseInt(request.getParameter(PARAM_ID));

			String dateGos = request.getParameter(PARAM_DATE_GOS);
			String dateVcr = request.getParameter(PARAM_DATE_VCR);
			if(dateGos.isEmpty() || dateVcr.isEmpty()){
				throw new HandlingException(RESPONSE_ERROR_EMPTY_DATE);
			}

			dao.update(id, new Date(0, dateGos, dateVcr)); // TODO 0 issue
			return RESPONSE_OK;
		}catch(SQLException e){
			// TODO logging
			throw new HandlingException(RESPONSE_ERROR_BD);
		}catch(DateFormatException e){
			throw new HandlingException(RESPONSE_ERROR_DATE_FORMAT);
		}
	}

	@Override
	protected String doDelete(HttpServletRequest request) throws HandlingException
	{
		try{
			// TODO move parameter extraction outside try block

			DateDao dao = DatabaseService.getInstance().getDateDao();
			int id = Integer.parseInt(request.getParameter(PARAM_ID));
			dao.delete(id);
			return RESPONSE_OK;
		}catch(SQLException e){
			// TODO logging
			throw new HandlingException(RESPONSE_ERROR_BD);
		}
	}


}
