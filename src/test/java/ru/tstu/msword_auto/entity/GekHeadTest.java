package ru.tstu.msword_auto.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class GekHeadTest {
	private int gekId;
	private String courseName;
	private String head;
	private String subhead;
	private String secretary;
	private GekHead defaultEntity;


	@Before
	public void setUp() {
		gekId = 1;
		courseName = "bdf";
		head = "asd";
		subhead = "asfd";
		secretary = "zxc";
		defaultEntity = new GekHead(gekId, courseName, head, subhead, secretary);
	}

	@Test
	public void whenSameDataWithFullConstructorThenEqualsTrue() throws Exception {
		GekHead other = new GekHead(gekId, courseName, head, subhead, secretary);

		assertEquals(defaultEntity, other);
		assertTrue(defaultEntity.equals(other));
	}

	@Test
	public void whenDifferentDataThenEqualsFalse() {
		GekHead other = new GekHead(gekId, "cvb", head, "cv", secretary);

		assertNotEquals(defaultEntity, other);
		assertFalse(defaultEntity.equals(other));
	}

	@Test
	public void whenSameDataWithSimpleConstructorThenEqualsTrue() {
		GekHead first = new GekHead(courseName);
		GekHead second = new GekHead(courseName);

		assertEquals(first, second);
		assertTrue(first.equals(second));
	}

	@Test
	public void whenSimpleConstructorThenAllOptionalDataAreEmptyString() {
		GekHead entity = new GekHead(courseName);
		String expected = "";

		assertEquals(expected, entity.getHead());
		assertEquals(expected, entity.getSubhead());
		assertEquals(expected, entity.getSecretary());
		assertNotEquals(expected, entity.getCourseName());
	}


}















