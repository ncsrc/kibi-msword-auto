package ru.tstu.msword_auto.automation;


import ru.tstu.msword_auto.entity.*;

import java.util.List;

public class TemplateData {
    private Course course;
    private Date date;
    private GekHead gekHead;
    private List<GekMember> gekMembers;
    private Student student;
    private Vcr vcr;


    public TemplateData(
            Course course,
            Date date,
            GekHead gekHead,
            List<GekMember> gekMembers,
            Student student,
            Vcr vcr
    ) {
        this.course = course;
        this.date = date;
        this.gekHead = gekHead;
        this.gekMembers = gekMembers;
        this.student = student;
        this.vcr = vcr;
    }


    public Date getDate() {
        return this.date;
    }

    public GekHead getGekHead() {
        return this.gekHead;
    }

    public List<GekMember> getGekMembers() {
        return this.gekMembers;
    }

    public String getGekMembersInString() {
        StringBuilder stringBuilder = new StringBuilder();

        for(GekMember gekMember : gekMembers) {
            if("".equals(gekMember.getMember())) {
                break;
            }

            stringBuilder.append(gekMember.getMember()).append(", ");
        }

        int length = stringBuilder.length();
        if(length != 0) {
            stringBuilder.delete(length - 2, length); // removes trailing comma and space
        }

        return stringBuilder.toString();
    }

    public Student getStudent() {
        return this.student;
    }

    public Course getCourse() {
        return this.course;
    }

    public Vcr getVcr() {
        return this.vcr;
    }


}
