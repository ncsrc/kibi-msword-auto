package ru.tstu.msword_auto.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;


public class GekMemberTest {
	private int memberId;
	private int headId;
	private String member;
	private GekMember defaultEntity;


	@Before
	public void setUp() {
		memberId = 1;
		headId = 1;
		member = "qwe";
		defaultEntity = new GekMember(memberId, headId, member);
	}


	@Test
	public void whenSameDataWithFullConstructorThenEqualsTrue() throws Exception {
		GekMember other = new GekMember(memberId, headId, member);

		assertEquals(defaultEntity, other);
		assertTrue(defaultEntity.equals(other));
	}

	@Test
	public void whenSameDataWithSimpleConstructorThenEqualsTrue() {
		GekMember first = new GekMember(headId, member);
		GekMember second = new GekMember(headId, member);

		assertEquals(first, second);
		assertTrue(first.equals(second));
	}

	@Test
	public void whenDifferentDataThenEqualsFalse() {
		GekMember other = new GekMember(memberId,2, "sd");

		assertNotEquals(defaultEntity, other);
		assertFalse(defaultEntity.equals(other));
	}


}