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

	// parsed
	private String gosDay;
	private String gosMonth;
	private String gosYear;
	private String vcrDay;
	private String vcrMonth;
	private String vcrYear;


	@Before
	public void setUp() throws Exception {
		groupName = "asd";
		gosDate = "2001-01-12";
		vcrDate = "2005-06-11";
		defaultEntity = new Date(groupName, gosDate, vcrDate);

		gosDay = "12";
		vcrDay = "11";
		gosMonth = "января";
		vcrMonth = "июня";
		gosYear = "01";
		vcrYear = "05";
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


	@Test
	public void whenGetGosDayThenEqualsCorrectly() throws Exception {
		String actual = defaultEntity.getGosDay();
		assertEquals(gosDay, actual);
	}

	@Test
	public void whenGetGosMonthThenEqualsCorrectly() throws Exception {
		String actual = defaultEntity.getGosMonth();
		assertEquals(gosMonth, actual);
	}

	@Test
	public void whenGetGosYearThenEqualsCorrectly() throws Exception {
		String actual = defaultEntity.getGosYear();
		assertEquals(gosYear, actual);
	}

	@Test
	public void whenGetVcrDayThenEqualsCorrectly() throws Exception {
		String actual = defaultEntity.getVcrDay();
		assertEquals(vcrDay, actual);
	}

	@Test
	public void whenGetVcrMonthThenEqualsCorrectly() throws Exception {
		String actual = defaultEntity.getVcrMonth();
		assertEquals(vcrMonth, actual);
	}

	@Test
	public void whenGetVcrYearThenEqualsCorrectly() throws Exception {
		String actual = defaultEntity.getVcrYear();
		assertEquals(vcrYear, actual);
	}

	@Test
	public void whenEntityWithEmptyDatesThenAllParsedDataAreEmptyString() {
		Date entity = new Date("qwe");
		String expected = "";

		assertEquals(expected, entity.getGosDay());
		assertEquals(expected, entity.getGosMonth());
		assertEquals(expected, entity.getGosYear());
		assertEquals(expected, entity.getVcrDay());
		assertEquals(expected, entity.getVcrMonth());
		assertEquals(expected, entity.getVcrYear());
	}


}