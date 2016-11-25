package ru.tstu.msword_automation.database.datasets;

import org.junit.Test;

import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class DateTest
{


	@Test
	public void DateConstructor() throws Exception
	{
		java.util.Date d0 = new java.util.Date();
		java.util.Date d1 = new java.util.Date(d0.getTime()/100);
		Date date = new Date(1, d0, d1);
		assertEquals(d0, date.getRawGosDate());
		assertEquals(d1, date.getRawVcrDate());
	}

	@Test
	public void StringConstructor() throws Exception
	{
		String date0 = "2001-01-12";
		String date1 = "2005-01-11";
		Date d0 = new Date(1, date0, date1);

		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
		assertEquals(date0, parser.format(d0.getRawGosDate()));
		assertEquals(date1, parser.format(d0.getRawVcrDate()));
	}

	@Test
	public void CorrectBusinessLogicDateConversions() throws Exception
	{
		String date0 = "2001-01-12";
		String date1 = "2005-01-11";
		Date d0 = new Date(1, date0, date1);

		assertEquals(2001, d0.getGosYear());
		assertEquals(11, d0.getVcrDay());
		assertEquals("января", d0.getGosMonth());
	}

	@Test(expected = DateFormatException.class)
	public void ExceptionThrownWhenIncorrectStringDatePassed() throws Exception
	{
		new Date(1, "2003.03.12", "2004/25/01");
	}


	@Test
	public void equalsCorrectness() throws Exception
	{
		Date d0 = new Date(1, "2001-01-23", "2015-03-25");
		Date d1 = new Date(1, "2001-01-23", "2015-03-25");
		Date d2 = new Date(2, "2005-13-13", "2013-13-12");

		assertEquals(d0, d1);
		assertNotEquals(d1, d2);
	}

}