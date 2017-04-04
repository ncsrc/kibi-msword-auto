package ru.tstu.msword_auto.automation.entity_aggregators;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class DateParserTest {
    private DateParser dateParser;
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
        dateParser = new DateParser(entity);
        gosDay = "12";
        gosMonth = "марта";
        gosYear = "01";
        vcrDay = "22";
        vcrMonth = "августа";
        vcrYear = "02";
    }


    @Test
    public void whenGetEntityDateThenEqualsCorrectly() throws Exception {
        ru.tstu.msword_auto.entity.Date entity = dateParser.getEntityDate();
        assertEquals(this.entity, entity);
    }

    @Test
    public void whenGetGosDayThenEqualsCorrectly() throws Exception {
        String actual = dateParser.getGosDay();
        assertEquals(gosDay, actual);
    }

    @Test
    public void whenGetGosMonthThenEqualsCorrectly() throws Exception {
        String actual = dateParser.getGosMonth();
        assertEquals(gosMonth, actual);
    }

    @Test
    public void whenGetGosYearThenEqualsCorrectly() throws Exception {
        String actual = dateParser.getGosYear();
        assertEquals(gosYear, actual);
    }

    @Test
    public void whenGetVcrDayThenEqualsCorrectly() throws Exception {
        String actual = dateParser.getVcrDay();
        assertEquals(vcrDay, actual);
    }

    @Test
    public void whenGetVcrMonthThenEqualsCorrectly() throws Exception {
        String actual = dateParser.getVcrMonth();
        assertEquals(vcrMonth, actual);
    }

    @Test
    public void whenGetVcrYearThenEqualsCorrectly() throws Exception {
        String actual = dateParser.getVcrYear();
        assertEquals(vcrYear, actual);
    }

    @Test
    public void whenEntityWithEmptyDatesThenAllParsedDataAreEmptyString() {
        dateParser = new DateParser(new ru.tstu.msword_auto.entity.Date("group"));
        String expected = "";

        assertEquals(expected, dateParser.getGosDay());
        assertEquals(expected, dateParser.getGosMonth());
        assertEquals(expected, dateParser.getGosYear());
        assertEquals(expected, dateParser.getVcrDay());
        assertEquals(expected, dateParser.getVcrMonth());
        assertEquals(expected, dateParser.getVcrYear());
    }

}























