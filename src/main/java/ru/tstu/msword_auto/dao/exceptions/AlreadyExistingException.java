package ru.tstu.msword_auto.dao.exceptions;


public class AlreadyExistingException extends DaoException {

    public AlreadyExistingException() {
        super();
    }

    public AlreadyExistingException(String message) {
        super(message);
    }

    public AlreadyExistingException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistingException(Throwable cause) {
        super(cause);
    }

}
