package ru.tstu.msword_automation.automation;

// TODO probably do not need this anymore after refactoring to service. remove.

/**
 * Unchecked exception which signals that client code forgot to specify template folder
 * where all .docs should reside. Setting this property is required.
 */
public class TempFolderException extends RuntimeException
{

	public TempFolderException(String message)
	{
		super(message);
	}

	public TempFolderException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public TempFolderException(Throwable cause)
	{
		super(cause);
	}

	protected TempFolderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
