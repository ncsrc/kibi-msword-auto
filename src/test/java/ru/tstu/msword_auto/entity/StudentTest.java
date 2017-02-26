package ru.tstu.msword_auto.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class StudentTest
{


	@Test
	public void equals() throws Exception
	{
		Student s0 = new Student(1, "qwe", "qwe", "asd");
		Student s1 = new Student(1, "qwe", "qwe", "asd");
		Student s2 = new Student(21, "qweasd", "qwe", "asd");

		assertEquals(s0, s1);
		assertNotEquals(s1, s2);
	}

}