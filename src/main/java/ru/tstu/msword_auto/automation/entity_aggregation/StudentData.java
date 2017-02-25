package ru.tstu.msword_auto.automation.entity_aggregation;


import ru.tstu.msword_auto.entity.Course;
import ru.tstu.msword_auto.entity.Student;
import ru.tstu.msword_auto.entity.VCR;

public class StudentData {
    private final Student student;
    private final Course course;
    private final VCR vcr;


    public StudentData(Student student, Course course, VCR vcr) {
        this.student = student;
        this.course = course;
        this.vcr = vcr;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public VCR getVcr() {
        return vcr;
    }

}
