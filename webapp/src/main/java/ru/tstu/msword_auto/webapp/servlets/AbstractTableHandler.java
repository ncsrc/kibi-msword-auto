package ru.tstu.msword_auto.webapp.servlets;


import com.google.gson.Gson;
import ru.tstu.msword_auto.dao.DefaultDaoCRUD;
import ru.tstu.msword_auto.dao.exceptions.AlreadyExistingException;
import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// TODO clean code

abstract class AbstractTableHandler extends HttpServlet {
	private static final String PARAM_ACTION = "action";
	static final String RESPONSE_OK = "{\"Result\": \"OK\"}";
	final Gson gson = new Gson();	// from docs: Gson instances are Thread-safe so you can reuse them freely across multiple threads.
	static final String PARAM_KEY = "jtRecordKey";
	static final String RESPONSE_ERROR_BD = "Ошибка базы данных";
	static final String RESPONSE_ERROR_EMPTY_FIELD = "Укажите все поля";	// TODO to student ?
	static final String CREATE_ID = "id";
	static final String CREATE_CONTENT = "content";


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String response;
		String action = req.getParameter(PARAM_ACTION);
		if(action != null) {
			response = handleAction(action, req);
		} else {
			response = getOptionsJsonResponse(handleOptions(req));
		}

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().print(response);
	}

	// create action same for all handlers
	protected <T> int create(DefaultDaoCRUD dao, T entity, String errorMessage) throws HandlingException {
		try {
			int id = dao.create(entity);
			return id;
		} catch (DaoSystemException e) {
			throw new HandlingException(RESPONSE_ERROR_BD);
		} catch (AlreadyExistingException e) {
			throw new HandlingException(errorMessage);
		}

	}

	// update action same for all handlers
	protected <T, V> String update(DefaultDaoCRUD dao, T entity, V pk, String errorMessage) throws HandlingException {
		try {
			dao.update(pk, entity);
			return RESPONSE_OK;
		} catch (DaoSystemException e) {
			throw new HandlingException(RESPONSE_ERROR_BD);
		} catch (AlreadyExistingException e) {
			throw new HandlingException(errorMessage);
		}

	}

	private String getResultJsonResponse(String json) {
		// frontend awaits 'Records' entry in response if dao got number of rows, 'Record' otherwise
		if(json.contains("[") || json.contains("]")) {
			return "{\"Result\": \"OK\",\"Records\": " + json + "}";
		}else {
			return "{\"Result\": \"OK\",\"Record\": " + json + "}";
		}
	}

	private String getErrorJsonResponse(String message) {
		return "{\"Result\": \"ERROR\",\"Message\": \"" + message + "\"}";
	}

	private String getOptionsJsonResponse(String json) {
		return "{\"Result\": \"OK\", \"Options\": " + json + "}";
	}

	private String handleAction(String action, HttpServletRequest req) {
		String response = "";
		try {
			switch(action)
			{
				case "list":
					response = getResultJsonResponse(doList(req));
					break;
				case "create":
					response = getResultJsonResponse(doCreate(req));
					break;
				case "update":
					response = doUpdate(req); // returns only ok, so don't need to wrap in result response
					break;
				case "delete":
					response = doDelete(req); // returns only ok, so don't need to wrap in result response
					break;
				default:
					// TODO to error 404 page
			}
		} catch(HandlingException e) {
			response = getErrorJsonResponse(e.getMessage());
		}

		return response;
	}

	// this left blank and not set as abstract because only few of subclasses need to override it
	protected String handleOptions(HttpServletRequest req) { return  null; }


	abstract protected String doList(HttpServletRequest request) throws HandlingException;

	abstract protected String doCreate(HttpServletRequest request) throws HandlingException;

	abstract protected String doUpdate(HttpServletRequest request) throws HandlingException;

	abstract protected String doDelete(HttpServletRequest request) throws HandlingException;


}
