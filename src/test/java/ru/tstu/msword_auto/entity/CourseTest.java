package ru.tstu.msword_auto.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CourseTest {
	private int studentId;
	private String groupName;
	private String code;
	private String qualification;
	private String courseName;
	private String profile;
	private Course defaultEntity;


	@Before
	public void setUp() {
		studentId = 1;
		groupName = "asd";
		code = "1.1.1";
		qualification = "zxc";
		courseName = "bdf";
		profile = "dbsd";
		defaultEntity = new Course(studentId, groupName, code, qualification, courseName, profile);
	}

	@Test
	public void whenSameDataWithFullConstructorThenEqualsTrue() throws Exception {
		Course second = new Course(studentId, groupName, code, qualification, courseName, profile);

		assertEquals(defaultEntity, second);
		assertTrue(defaultEntity.equals(second));
	}

	@Test
	public void whenDifferentDataThenEqualsFalse() {
		Course other = new Course(34, groupName, "f", qualification, courseName, profile);

		assertNotEquals(defaultEntity, other);
		assertFalse(defaultEntity.equals(other));
	}

	@Test
	public void whenSameDataWithSimpleConstructorThenEqualsTrue() {
		Course first = new Course(studentId, groupName);
		Course second = new Course(studentId, groupName);

		assertEquals(first, second);
		assertTrue(first.equals(second));
	}

	@Test
	public void whenSimpleConstructorThenAllOptionalDataAreEmptyString() {
		Course entity = new Course(studentId, groupName);
		String expected = "";

		// getInfo() isn't tested, because it just combines two optional values
		assertEquals(expected, entity.getCode());
		assertEquals(expected, entity.getCourseName());
		assertEquals(expected, entity.getProfile());
		assertEquals(expected, entity.getQualification());
		assertNotEquals(expected, entity.getStudentId());
		assertNotEquals(expected, entity.getGroupName());
	}

}






























