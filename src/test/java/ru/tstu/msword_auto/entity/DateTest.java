package ru.tstu.msword_auto.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class DateTest {
	private String groupName;
	private String gosDate;
	private String vcrDate;
	private Date defaultEntity;


	@Before
	public void setUp() throws Exception {
		groupName = "asd";
		gosDate = "2001-01-12";
		vcrDate = "2005-01-11";
		defaultEntity = new Date(groupName, gosDate, vcrDate);
	}


	@Test
	public void whenSameDataWithFullStringConstructorThenEqualsTrue() throws Exception {
		Date second = new Date(groupName, gosDate, vcrDate);

		assertEquals(defaultEntity, second);
		assertTrue(defaultEntity.equals(second));
	}

	@Test
	public void whenDifferentDataThenEqualsFalse() {
		Date other = new Date("xcz", vcrDate, gosDate);

		assertNotEquals(defaultEntity, other);
		assertFalse(defaultEntity.equals(other));
	}

	@Test
	public void whenSameDataWithSimpleConstructorThenEqualsTrue() {
		Date first = new Date(groupName);
		Date second = new Date(groupName);

		assertEquals(first, second);
		assertTrue(first.equals(second));
	}

	@Test(expected = DateFormatException.class)
	public void exceptionThrownWhenIncorrectDelimitersInDate() throws Exception {
		new Date(groupName, "2003.03.12", "2004/25/01");
	}

	@Test(expected = DateFormatException.class)
	public void exceptionThrownWhenInvalidMonthInDate() throws Exception {
		new Date(groupName, "2003-03-12", "2004-25-01");
	}

	@Test(expected = DateFormatException.class)
	public void exceptionThrownWhenInvalidDayInDate() throws Exception {
		new Date(groupName, "2003-03-32", "2004-09-01");
	}


}