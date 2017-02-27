package ru.tstu.msword_auto.webapp;


public class HandlingException extends Exception
{

	// don't need empty constructor, because this exception should return error messages to user
	public HandlingException(String message){
		super(message);
	}

}
