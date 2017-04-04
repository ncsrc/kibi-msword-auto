package ru.tstu.msword_auto.automation.entity_aggregators;


import org.junit.Test;
import ru.tstu.msword_auto.entity.Course;
import ru.tstu.msword_auto.entity.Student;
import ru.tstu.msword_auto.entity.VCR;

import static org.junit.Assert.assertEquals;


public class StudentDataTest {
    private final StudentData studentData;
    private final Student student;
    private final Course course;
    private final VCR vcr;

    /*
        aggregates Student, Course, Vcr entities

        api:
        delegating methods

     */

    public StudentDataTest() {

        this.student = new Student("ads", "ads", "ads",
                "adss", "adss", "adss", "adsd",
                "adsd", "adsd");
        this.course = new Course(1, "ads", "ads", "ads", "asd");
        this.vcr = new VCR(1, "ads", "ads", "ads");
        this.studentData = new StudentData(student, course, vcr);
    }


    @Test
    public void whenGetStudentThenCorrectResult() throws Exception {
        Student actual = studentData.getStudent();
        assertEquals(this.student, actual);
    }

    @Test
    public void whenGetCourseThenCorrectResult() throws Exception {
        Course actual = studentData.getCourse();
        assertEquals(this.course, actual);
    }

    @Test
    public void whenGetVcrThenCorrectResult() throws Exception {
        VCR actual = studentData.getVcr();
        assertEquals(this.vcr, actual);
    }



}























