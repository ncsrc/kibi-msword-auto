package ru.tstu.msword_auto.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StudentTest {
	private int studentId;

	// Именительный падеж
	private String firstNameI;
	private String lastNameI;
	private String middleNameI;

	// Родительный падеж
	private String firstNameR;
	private String lastNameR;
	private String middleNameR;

	// Творительный падеж
	private String firstNameD;
	private String lastNameD;
	private String middleNameD;

	Student defaultEntity;


	@Before
	public void setUp() {
		studentId = 1;
		firstNameI = "fi";
		lastNameI = "li";
		middleNameI = "mi";
		firstNameR = "ri";
		lastNameR = "lr";
		middleNameR = "mr";
		firstNameD = "fd";
		lastNameD = "ld";
		middleNameD = "md";
		defaultEntity = new Student(
				firstNameI, lastNameI, middleNameI,
				firstNameR, lastNameR, middleNameR,
				firstNameD, lastNameD, middleNameD
		);
	}


	@Test
	public void whenSameDataThenEqualsTrue() throws Exception {
		Student other = new Student(
				firstNameI, lastNameI, middleNameI,
				firstNameR, lastNameR, middleNameR,
				firstNameD, lastNameD, middleNameD
		);

		assertEquals(defaultEntity, other);
		assertTrue(defaultEntity.equals(other));
	}

	@Test
	public void whenDifferentDataThenEqualsFalse() {
		Student other = new Student(
				"fd", lastNameI, "df",
				firstNameR, "fd", middleNameR,
				"f", lastNameD, "gfv"
		);

		assertNotEquals(defaultEntity, other);
		assertFalse(defaultEntity.equals(other));
	}



}