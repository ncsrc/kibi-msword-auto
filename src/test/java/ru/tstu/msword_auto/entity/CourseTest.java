package ru.tstu.msword_auto.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CourseTest
{


	@Test
	public void CourseDatasetEqualsCorrectness() throws Exception
	{
		Course c0 = new Course("1.1.1.1", 1, "бакалавр", "test", "test-test");
		Course c1 = new Course("1.1.1.1", 1, "бакалавр", "test", "test-test");
		Course c2 = new Course("1.1.1", 2, "bakalavr","test", "tes-test");

		assertEquals(c0, c1);
		assertNotEquals(c1, c2);
	}

}