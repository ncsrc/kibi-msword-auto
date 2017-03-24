package ru.tstu.msword_auto.dao.exceptions;


/**
 * Exception for all system problems that could occur by using database.
 * E.g. - connection lost, db removed, timeout exceeded, etc
 */
public class DaoSystemException extends DaoException {

    public DaoSystemException() {
        super();
    }

    public DaoSystemException(String message) {
        super(message);
    }

    public DaoSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoSystemException(Throwable cause) {
        super(cause);
    }

}
