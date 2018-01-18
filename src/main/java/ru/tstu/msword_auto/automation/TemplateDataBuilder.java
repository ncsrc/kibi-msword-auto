package ru.tstu.msword_auto.automation;


import ru.tstu.msword_auto.entity.*;

import java.util.ArrayList;
import java.util.List;


public class TemplateDataBuilder {
    private Course course;
    private Date date;
    private GekHead gekHead;
    private List<GekMember> gekMembers;
    private Student student;
    private Vcr vcr;


    public TemplateDataBuilder() {
        // sets default entities
        this.course = new Course();
        this.date = new Date();
        this.gekHead = new GekHead();
        this.gekMembers = getEmptyGekMembersList();
        this.vcr = new Vcr();
    }


    public TemplateDataBuilder setCourse(Course course) {
        this.course = course;
        return this;
    }

    public TemplateDataBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    public TemplateDataBuilder setGekHead(GekHead gekHead) {
        this.gekHead = gekHead;
        return this;
    }

    public TemplateDataBuilder setGekMembers(List<GekMember> gekMembers) {
        int requiredSize = 6;
        int size = gekMembers.size();

        if(size < requiredSize) {
            int numberOfGaps = requiredSize - size;

            for(int i = 0; i < numberOfGaps; i++) {
                gekMembers.add(new GekMember());    // adds default/empty gek member
            }
        }

        this.gekMembers = gekMembers;
        return this;
    }

    public TemplateDataBuilder setStudent(Student student) {
        this.student = student;
        return this;
    }

    public TemplateDataBuilder setVcr(Vcr vcr) {
        this.vcr = vcr;
        return this;
    }

    public TemplateData build() throws IllegalStateException {
        if(student == null) {
            throw new IllegalStateException("Can't build without student data");
        }

        return new TemplateData(course, date, gekHead, gekMembers, student, vcr);
    }


    private List<GekMember> getEmptyGekMembersList() {
        List<GekMember> members = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            members.add(new GekMember());
        }

        return members;
    }


}
