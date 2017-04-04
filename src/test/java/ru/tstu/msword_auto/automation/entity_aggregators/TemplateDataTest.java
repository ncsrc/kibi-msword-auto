package ru.tstu.msword_auto.automation.entity_aggregators;

import org.junit.Test;
import ru.tstu.msword_auto.entity.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class TemplateDataTest {
    private final TemplateData data;
    private final DateParser dateParser;
    private final GekHead gekHead;
    private final List<GekMember> gekMembers;
    private final Student student;
    private final Course course;
    private final VCR vcr;


    /*
        Contract:
        fields: entity-aggregations

        constructor with entity-aggregations

        api:
        getters for entities

     */


    public TemplateDataTest() throws Exception {
        this.dateParser = new DateParser(
                new ru.tstu.msword_auto.entity.Date("gr", "2013-11-12", "2013-11-12")
        );
        this.gekHead = new GekHead("asd", "asd", "asd", "asd");
        this.gekMembers = Arrays.asList(new GekMember(1, "asd"));
        this.student = new Student("ads", "ads", "ads",
                "adss", "adss", "adss", "adsd",
                "adsd", "adsd");
        this.course = new Course(1, "xc", "asd", "asd", "asd");
        this.vcr = new VCR(1, "asd", "asd", "asd");
        Gek gek = new Gek(gekHead, gekMembers);
        StudentData studentData = new StudentData(student, course, vcr);
        this.data = new TemplateData(dateParser, gek, studentData);
    }


    @Test
    public void whenGetDateThenCorrectResult() {
        DateParser actual = this.data.getDate();
        assertEquals(this.dateParser, actual);
    }

    @Test
    public void whenGetGekHeadThenCorrectResult() {
        GekHead actual = this.data.getGekHead();
        assertEquals(this.gekHead, actual);
    }

    @Test
    public void whenGetGekMemberThenCorrectResult() {
        List<GekMember> actual = this.data.listOfGekMembers();
        assertEquals(this.gekMembers, actual);
    }

    @Test
    public void whenGetStudentThenCorrectResult() {
        Student actual = this.data.getStudent();
        assertEquals(this.student, actual);
    }

    @Test
    public void whenGetCourseThenCorrectResult() {
        Course actual = this.data.getStudentCourse();
        assertEquals(this.course, actual);
    }

    @Test
    public void whenGetVcrThenCorrectResult() {
        VCR actual = this.data.getStudentVcr();
        assertEquals(this.vcr, actual);
    }


}



























