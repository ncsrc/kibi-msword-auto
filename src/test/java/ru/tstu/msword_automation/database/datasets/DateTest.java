package ru.tstu.msword_automation.database.datasets;

import org.junit.Test;

import static org.junit.Assert.*;

public class DateTest
{

	@Test
	public void equalsCorrectness() throws Exception
	{
		Date d0 = new Date(1, "апреля", 11);
		Date d1 = new Date(1, "апреля", 11);
		Date d2 = new Date(1, "апреля", 111);

		assertEquals(d0, d1);
		assertNotEquals(d1, d2);
	}

}