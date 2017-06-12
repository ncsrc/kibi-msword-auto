package ru.tstu.msword_auto.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CourseTest {
	private int studentId;
	private int subgroupId;
	private String groupName;
	private String code;
	private String qualification;
	private String courseName;
	private String profile;
	private Course defaultEntity;


	@Before
	public void setUp() {
		studentId = 1;
		subgroupId = 2;
		groupName = "asd";
		code = "38.03.05";
		qualification = "Бакалавр";
		courseName = "Бизнес-информатика";
		profile = "dbsd";
		defaultEntity = new Course(studentId, subgroupId, groupName, qualification, courseName, profile);
	}

	@Test
	public void whenSameDataWithFullConstructorThenEqualsTrue() throws Exception {
		Course second = new Course(studentId, subgroupId, groupName, qualification, courseName, profile);

		assertEquals(defaultEntity, second);
		assertTrue(defaultEntity.equals(second));
	}

	@Test
	public void whenDifferentDataThenEqualsFalse() {
		Course other = new Course(34, 12, groupName, "f", courseName, profile);

		assertNotEquals(defaultEntity, other);
		assertFalse(defaultEntity.equals(other));
	}

	@Test
	public void whenSameDataWithSimpleConstructorThenEqualsTrue() {
		Course first = new Course(studentId, subgroupId, groupName);
		Course second = new Course(studentId, subgroupId, groupName);

		assertEquals(first, second);
		assertTrue(first.equals(second));
	}

	@Test
	public void whenSimpleConstructorThenAllOptionalDataAreEmptyString() {
		Course entity = new Course(studentId, subgroupId, groupName);
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






























