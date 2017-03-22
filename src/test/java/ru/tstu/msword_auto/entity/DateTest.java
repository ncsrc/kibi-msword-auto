package ru.tstu.msword_auto.entity;

import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class DateTest {

	/*
		what needs to be tested:

		from jdbc: null can be inserted, so simple constructor with only primary key(groupName) required
		in this scenario check that non-transient properties equal empty string, so as parsed data
		in other scenario check that all data properly parsed

	 */

	private String groupName;
//	private java.util.Date gosWrappedDate;
//	private java.util.Date vcrWrappedDate;
	private String gosDate;
	private String vcrDate;
	private Date defaultEntity;


	@Before
	public void setUp() throws Exception {
		groupName = "asd";
		gosDate = "2001-01-12";
		vcrDate = "2005-01-11";

//		DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
//		gosWrappedDate = parser.parse(gosDate);
//		vcrWrappedDate = parser.parse(vcrDate);

		defaultEntity = new Date(groupName, gosDate, vcrDate);
	}


	@Test
	public void whenSameDataWithFullStringConstructorThenEqualsTrue() throws Exception {
		Date second = new Date(groupName, gosDate, vcrDate);

		assertEquals(defaultEntity, second);
		assertTrue(defaultEntity.equals(second));
	}


//	@Test
//	public void whenSameDataWithFullObjectConstructorThenEqualsTrue() throws Exception {
//		Date second = new Date(groupName, gosWrappedDate, vcrWrappedDate);
//
//		assertEquals(defaultEntity, second);
//		assertTrue(defaultEntity.equals(second));
//	}

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

	@Test
	public void whenSimpleConstructorThenAllParsedDataAreEmptyString() {
		Date entity = new Date(groupName);
		String expected = "";

		assertEquals(expected, entity.getGosDay());
		assertEquals(expected, entity.getVcrDay());
		assertEquals(expected, entity.getGosMonth());
		assertEquals(expected, entity.getVcrMonth());
		assertEquals(expected, entity.getGosYear());
		assertEquals(expected, entity.getVcrYear());

		assertNotEquals(expected, entity.getGroupName());
	}

//	@Test
//	public void whenSimpleConstructorThenWrappedDatesAreNulls() {
//		Date entity = new Date(groupName);
//
//		assertNull(entity.getGosDateInLong());
//		assertNull(entity.getVcrDateInLong());
//	}

	@Test
	public void whenFullyCreatedWithStringConstructorThenDatesParsedCorrectly() throws Exception {
		String expectedGosYear = "01";
		String expectedVcrYear = "05";
		String expectedGosMonth = "января";
		String expectedVcrMonth = "января";
		String expectedGosDay = "12";
		String expectedVcrDay = "11";

		assertEquals(expectedGosYear, defaultEntity.getGosYear());
		assertEquals(expectedVcrYear, defaultEntity.getVcrYear());
		assertEquals(expectedGosMonth, defaultEntity.getGosMonth());
		assertEquals(expectedVcrMonth, defaultEntity.getVcrMonth());
		assertEquals(expectedGosDay, defaultEntity.getGosDay());
		assertEquals(expectedVcrDay, defaultEntity.getVcrDay());
	}

//	@Test
//	public void whenFullyCreatedWithObjectConstructorThenDatesParsedCorrectly() throws Exception {
//		Date defaultEntity = new Date(groupName, gosWrappedDate, vcrWrappedDate);
//		String expectedGosYear = "01";
//		String expectedVcrYear = "05";
//		String expectedGosMonth = "января";
//		String expectedVcrMonth = "января";
//		String expectedGosDay = "12";
//		String expectedVcrDay = "11";
//
//		assertEquals(expectedGosYear, defaultEntity.getGosYear());
//		assertEquals(expectedVcrYear, defaultEntity.getVcrYear());
//		assertEquals(expectedGosMonth, defaultEntity.getGosMonth());
//		assertEquals(expectedVcrMonth, defaultEntity.getVcrMonth());
//		assertEquals(expectedGosDay, defaultEntity.getGosDay());
//		assertEquals(expectedVcrDay, defaultEntity.getVcrDay());
//	}

	@Test(expected = DateFormatException.class)
	public void exceptionThrownWhenIncorrectStringDatePassed() throws Exception {
		new Date(groupName, "2003.03.12", "2004/25/01");
	}


}