package ru.tstu.msword_auto.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class StudentTest
{


	@Test
	public void equals() throws Exception
	{
		Student s0 = new Student(1, "qwe", "qwe", "asd",
				"qwee", "qwee", "asde", "qweq",
				"qweq", "asdq");

		Student s1 = new Student(1, "qwe", "qwe", "asd",
				"qwee", "qwee", "asde", "qweq",
				"qweq", "asdq");

		Student s2 = new Student(11, "qwse", "qwe", "asd",
				"qwee", "qweef", "asde", "qweq",
				"asqweq", "asdq");;

		assertEquals(s0, s1);
		assertNotEquals(s1, s2);
	}

}