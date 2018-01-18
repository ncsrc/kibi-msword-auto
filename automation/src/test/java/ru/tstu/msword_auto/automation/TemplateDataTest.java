package ru.tstu.msword_auto.automation;

import org.junit.Test;
import ru.tstu.msword_auto.entity.GekMember;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class TemplateDataTest {



    @Test
    public void whenGetGekMembersInStringWithFullListThenCorrectString() throws Exception {
        List<GekMember> gekMember = Arrays.asList(
                new GekMember(1, "first"),
                new GekMember(2, "second"),
                new GekMember(3, "third"),
                new GekMember(4, "fourth"),
                new GekMember(5, "fifth"),
                new GekMember(6, "sixth")
        );

        TemplateData data = new TemplateData(
                null,
                null,
                null,
                gekMember,
                null,
                null
        );

        String expected = "first, second, third, fourth, fifth, sixth";
        String actual = data.getGekMembersInString();

        assertEquals(expected, actual);
    }

    @Test
    public void whenGetGekMembersInStringWithListOfDefaultsThenCorrectString() throws Exception {
        List<GekMember> gekMember = Arrays.asList(
                new GekMember(), new GekMember(),
                new GekMember(), new GekMember(),
                new GekMember(), new GekMember()
        );

        TemplateData data = new TemplateData(
                null,
                null,
                null,
                gekMember,
                null,
                null
        );

        String expected = "";
        String actual = data.getGekMembersInString();

        assertEquals(expected, actual);
    }

    @Test
    public void whenGetGekMembersInStringWithHalfListThenCorrectString() throws Exception {
        List<GekMember> gekMember = Arrays.asList(
                new GekMember(1, "first"),
                new GekMember(2, "second"),
                new GekMember(3, "third"),
                new GekMember(), new GekMember(), new GekMember()
        );

        TemplateData data = new TemplateData(
                null,
                null,
                null,
                gekMember,
                null,
                null
        );

        String expected = "first, second, third";
        String actual = data.getGekMembersInString();

        assertEquals(expected, actual);
    }


}