package ru.tstu.msword_auto.automation.entity_aggregation;

import ru.tstu.msword_auto.entity.*;

// todo javadoc
public class TemplateData {
    private final Date date;
    private final Gek gek;
    private final StudentData studentData;


    public TemplateData(Date date, Gek gek, StudentData studentData) {
        this.date = date;
        this.gek = gek;
        this.studentData = studentData;
    }

    public Date getDate() {
        return this.date;
    }

    public GekHead getGekHead() {
        return this.gek.getGekHead();
    }

    public GekMember getGekMember() {
        return this.gek.getGekMember();
    }

    public Student getStudent() {
        return this.studentData.getStudent();
    }

    public Course getStudentCourse() {
        return this.studentData.getCourse();
    }

    public VCR getStudentVcr() {
        return this.studentData.getVcr();
    }


}




























