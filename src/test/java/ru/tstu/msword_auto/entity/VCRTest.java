package ru.tstu.msword_auto.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class VCRTest {
	private int studentId;
	private String vcrName;
	private String vcrHead;
	private String vcrReviewer;
	private VCR defaultEntity;


	@Before
	public void setUp() {
		studentId = 1;
		vcrName = "name";
		vcrHead = "head";
		vcrReviewer = "rev";
		defaultEntity = new VCR(studentId, vcrName, vcrHead, vcrReviewer);
	}

	@Test
	public void whenSameDataWithFullConstructorThenEqualsTrue() throws Exception {
		VCR second = new VCR(studentId, vcrName, vcrHead, vcrReviewer);

		assertEquals(defaultEntity, second);
		assertTrue(defaultEntity.equals(second));
	}

	@Test
	public void whenDifferentDataThenEqualsFalse() {
		VCR other = new VCR(2, vcrName, "dsf", "w");

		assertNotEquals(defaultEntity, other);
		assertFalse(defaultEntity.equals(other));
	}

	@Test
	public void whenSameDataWithSimpleConstructorThenEqualsTrue() {
		VCR first = new VCR(studentId, vcrName);
		VCR second = new VCR(studentId, vcrName);

		assertEquals(first, second);
		assertTrue(first.equals(second));
	}

	@Test
	public void whenSimpleConstructorThenAllOptionalDataAreEmptyString() {
		VCR entity = new VCR(studentId, vcrName);
		String expected = "";

		assertEquals(expected, entity.getHeadName());
		assertEquals(expected, entity.getReviewerName());
		assertNotEquals(expected, entity.getStudentId());
		assertNotEquals(expected, entity.getName());
	}



}