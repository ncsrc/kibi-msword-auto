package ru.tstu.msword_auto.automation.entity_aggregation;

import org.junit.Test;
import ru.tstu.msword_auto.entity.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class TemplateDataTest {
    private final TemplateData data;
    private final Date date;
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
        this.date = new Date(1, "2013-11-12", "2013-11-12");
        this.gekHead = new GekHead("asd", "asd", "asd");
        this.gekMembers = Arrays.asList(new GekMember("asd", "asd"));
        this.student = new Student(1, "ads", "ads", "ads",
                "adss", "adss", "adss", "adsd",
                "adsd", "adsd");
        this.course = new Course("asd", 1, "asd", "asd", "asd");
        this.vcr = new VCR(1, "asd", "asd", "asd");
        Gek gek = new Gek(gekHead, gekMembers);
        StudentData studentData = new StudentData(student, course, vcr);
        this.data = new TemplateData(date, gek, studentData);
    }


    @Test
    public void whenGetDateThenCorrectResult() {
        Date actual = this.data.getDate();
        assertEquals(this.date, actual);
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



























