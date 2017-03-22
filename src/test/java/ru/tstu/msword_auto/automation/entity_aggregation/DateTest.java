package ru.tstu.msword_auto.automation.entity_aggregation;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class DateTest {
    private Date date;
    private ru.tstu.msword_auto.entity.Date entity;
    private String gosDay;
    private String gosMonth;
    private String gosYear;
    private String vcrDay;
    private String vcrMonth;
    private String vcrYear;


    @Before
    public void setUp() {
        entity = new ru.tstu.msword_auto.entity.Date("group", "2001-03-12", "2002-08-22");
        date = new Date(entity);
        gosDay = "12";
        gosMonth = "марта";
        gosYear = "01";
        vcrDay = "22";
        vcrMonth = "августа";
        vcrYear = "02";
    }


    @Test
    public void whenGetEntityDateThenEqualsCorrectly() throws Exception {
        ru.tstu.msword_auto.entity.Date entity = date.getEntityDate();
        assertEquals(this.entity, entity);
    }

    @Test
    public void whenGetGosDayThenEqualsCorrectly() throws Exception {
        String actual = date.getGosDay();
        assertEquals(gosDay, actual);
    }

    @Test
    public void whenGetGosMonthThenEqualsCorrectly() throws Exception {
        String actual = date.getGosMonth();
        assertEquals(gosMonth, actual);
    }

    @Test
    public void whenGetGosYearThenEqualsCorrectly() throws Exception {
        String actual = date.getGosYear();
        assertEquals(gosYear, actual);
    }

    @Test
    public void whenGetVcrDayThenEqualsCorrectly() throws Exception {
        String actual = date.getVcrDay();
        assertEquals(vcrDay, actual);
    }

    @Test
    public void whenGetVcrMonthThenEqualsCorrectly() throws Exception {
        String actual = date.getVcrMonth();
        assertEquals(vcrMonth, actual);
    }

    @Test
    public void whenGetVcrYearThenEqualsCorrectly() throws Exception {
        String actual = date.getVcrYear();
        assertEquals(vcrYear, actual);
    }

    @Test
    public void whenEntityWithEmptyDatesThenAllParsedDataAreEmptyString() {
        date = new Date(new ru.tstu.msword_auto.entity.Date("group"));
        String expected = "";

        assertEquals(expected, date.getGosDay());
        assertEquals(expected, date.getGosMonth());
        assertEquals(expected, date.getGosYear());
        assertEquals(expected, date.getVcrDay());
        assertEquals(expected, date.getVcrMonth());
        assertEquals(expected, date.getVcrYear());
    }

}























