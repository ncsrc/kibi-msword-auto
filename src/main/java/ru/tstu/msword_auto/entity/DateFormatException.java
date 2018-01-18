package ru.tstu.msword_auto.entity;


public class DateFormatException extends RuntimeException
{

	public DateFormatException(){
		super();
	}

	public DateFormatException(String message){
		super(message);
	}
}
