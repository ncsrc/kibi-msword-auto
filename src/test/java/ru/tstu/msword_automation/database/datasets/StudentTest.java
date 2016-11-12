package ru.tstu.msword_automation.database.datasets;

import org.junit.Test;

import static org.junit.Assert.*;

public class StudentTest
{


	@Test
	public void equals() throws Exception
	{
		Student s0 = new Student(1, "qwe", "qwe", "asd",
				"asfq", "123.123.123");
		Student s1 = new Student(1, "qwe", "qwe", "asd",
				"asfq", "123.123.123");
		Student s2 = new Student(21, "qweasd", "qwe", "asd",
				"asfq", "123.123.1223");

		assertEquals(s0, s1);
		assertNotEquals(s1, s2);
	}

}