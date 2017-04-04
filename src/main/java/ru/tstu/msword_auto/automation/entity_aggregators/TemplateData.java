package ru.tstu.msword_auto.automation.entity_aggregators;

import ru.tstu.msword_auto.entity.*;

import java.util.List;


// TODO consider Builder instead
/*
    Template data with: setDate(where to put wrapper?), setGekHead/Member, setStudent, setVcr
    Valid data: only student, student + something, other - defaultEntity(with empty strings)
    TODO Remove final in entity and add empty constructor ?
    TODO Remove aggregators ?
    In webapp: use setters, if some entities are missing when build() -> make default entities(by empty constructor ?)
 */

public class TemplateData {
    private final DateParser dateParser;
    private final Gek gek;
    private final StudentData studentData;


    public TemplateData(DateParser dateParser, Gek gek, StudentData studentData) {
        this.dateParser = dateParser;
        this.gek = gek;
        this.studentData = studentData;
    }

    public DateParser getDate() {
        return this.dateParser;
    }

    public GekHead getGekHead() {
        return this.gek.getGekHead();
    }

    public List<GekMember> listOfGekMembers() {
        return this.gek.getListOfGekMembers();
    }

    public String getGekMembersListInString() {
        return this.gek.getFullMembersList();
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




























