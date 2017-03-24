package ru.tstu.msword_auto.dao.exceptions;


/**
 * General superclass for all Dao exceptions
 */
public class DaoException extends Exception {

	public DaoException(){
		super();
	}

	public DaoException(String message){
		super(message);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}


}
