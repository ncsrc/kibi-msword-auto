package ru.tstu.msword_auto.webapp;

import ru.tstu.msword_auto.dao.exceptions.DaoException;
import ru.tstu.msword_auto.dao.DateDao;
import ru.tstu.msword_auto.entity.Date;
import ru.tstu.msword_auto.entity.DateFormatException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;


public class DateHandler extends AbstractTableHandler {
	private static final String PARAM_ID = "id";
	private static final String PARAM_DATE_GOS = "gosDate";
	private static final String PARAM_DATE_VCR = "vcrDate";
	private static final String RESPONSE_ERROR_DATE_FORMAT = "Неправильный формат даты. Должен быть: yyyy-mm-dd";

	// TODO remove
	private static final String RESPONSE_ERROR_EMPTY_DATE = "Укажите обе даты";

	private DateDao dao = new DateDao();


	@Override
	protected String doList(HttpServletRequest request) throws HandlingException {
		try {
			List<Date> dates = dao.readAll();
			return gson.toJson(dates);
		} catch(SQLException e){
			throw new HandlingException(RESPONSE_ERROR_BD);
		}
	}

	@Override
	protected String doCreate(HttpServletRequest request) throws HandlingException {
		try {
			// TODO move parameter extraction outside try block

			String dateGos = request.getParameter(PARAM_DATE_GOS);
			String dateVcr = request.getParameter(PARAM_DATE_VCR);

			// vcr date may be unknown. remove?
//			if(dateGos.isEmpty() || dateVcr.isEmpty()){
//				throw new HandlingException(RESPONSE_ERROR_EMPTY_DATE);
//			}

			Date dateEntity = new Date(0, dateGos, dateVcr);
			dao.create(dateEntity);

			dateEntity = new Date(dao.lastInsertId(), dateGos, dateVcr); // TODO fix necessity of making new instance
			return gson.toJson(dateEntity);
		} catch(SQLException | DaoException e){	// TODO separate DaoException handling
			throw new HandlingException(RESPONSE_ERROR_BD);
		} catch(DateFormatException e){
			throw new HandlingException(RESPONSE_ERROR_DATE_FORMAT);
		}
	}

	@Override
	protected String doUpdate(HttpServletRequest request) throws HandlingException {
		try {
			// TODO move parameter extraction outside try block

			int id = Integer.parseInt(request.getParameter(PARAM_ID));

			String dateGos = request.getParameter(PARAM_DATE_GOS);
			String dateVcr = request.getParameter(PARAM_DATE_VCR);

			// TODO remove, they can be empty
			if(dateGos.isEmpty() || dateVcr.isEmpty()){
				throw new HandlingException(RESPONSE_ERROR_EMPTY_DATE);
			}

			dao.update(id, new Date(0, dateGos, dateVcr));
			return RESPONSE_OK;
		} catch(SQLException e){
			throw new HandlingException(RESPONSE_ERROR_BD);
		} catch(DateFormatException e){
			throw new HandlingException(RESPONSE_ERROR_DATE_FORMAT);
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
