package ru.tstu.msword_auto.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class VcrTest {
	private int studentId;
	private String vcrName;
	private String vcrHead;
	private String vcrReviewer;
	private Vcr defaultEntity;


	@Before
	public void setUp() {
		studentId = 1;
		vcrName = "name";
		vcrHead = "head";
		vcrReviewer = "rev";
		defaultEntity = new Vcr(studentId, vcrName, vcrHead, vcrReviewer);
	}

	@Test
	public void whenSameDataWithFullConstructorThenEqualsTrue() throws Exception {
		Vcr second = new Vcr(studentId, vcrName, vcrHead, vcrReviewer);

		assertEquals(defaultEntity, second);
		assertTrue(defaultEntity.equals(second));
	}

	@Test
	public void whenDifferentDataThenEqualsFalse() {
		Vcr other = new Vcr(2, vcrName, "dsf", "w");

		assertNotEquals(defaultEntity, other);
		assertFalse(defaultEntity.equals(other));
	}

	@Test
	public void whenSameDataWithSimpleConstructorThenEqualsTrue() {
		Vcr first = new Vcr(studentId, vcrName);
		Vcr second = new Vcr(studentId, vcrName);

		assertEquals(first, second);
		assertTrue(first.equals(second));
	}

	@Test
	public void whenSimpleConstructorThenAllOptionalDataAreEmptyString() {
		Vcr entity = new Vcr(studentId, vcrName);
		String expected = "";

		assertEquals(expected, entity.getHeadName());
		assertEquals(expected, entity.getReviewerName());
		assertNotEquals(expected, entity.getStudentId());
		assertNotEquals(expected, entity.getName());
	}



}