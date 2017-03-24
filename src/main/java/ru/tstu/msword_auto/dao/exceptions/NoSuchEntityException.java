package ru.tstu.msword_auto.dao.exceptions;


/**
 * Business-level exception denoting that such entity does not exist in database.
 */
public class NoSuchEntityException extends DaoException {

    public NoSuchEntityException() {
        super();
    }

    public NoSuchEntityException(String message) {
        super(message);
    }

    public NoSuchEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchEntityException(Throwable cause) {
        super(cause);
    }

}
