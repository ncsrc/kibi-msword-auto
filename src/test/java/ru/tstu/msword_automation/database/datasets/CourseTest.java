package ru.tstu.msword_automation.database.datasets;

import org.junit.Test;

import static org.junit.Assert.*;

public class CourseTest
{


	@Test
	public void CourseDatasetEqualsCorrectness() throws Exception
	{
		Course c0 = new Course("1.1.1.1", "test", "test-test");
		Course c1 = new Course("1.1.1.1", "test", "test-test");
		Course c2 = new Course("1.1.1", "test", "tes-test");

		assertEquals(c0, c1);
		assertNotEquals(c1, c2);
	}

}