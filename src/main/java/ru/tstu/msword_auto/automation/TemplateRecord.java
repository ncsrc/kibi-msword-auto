package ru.tstu.msword_auto.automation;

// todo javadoc
enum TemplateRecord {
    DATE_DAY("{Date_D}"),
    DATE_MONTH("{Date_M}"),
    DATE_YEAR("{Date_Y}"),
    GEK_HEAD("{GEK_Head}"),
    GEK_SUBHEAD("{GEK_Subhead}"),
    GEK_SECRETARY("{GEK_Secretary}"),
    GEK_MEMBERS("{GEK_Members}"),
    STUDENT_NAME("{Student_Name}"),
    STUDENT_INITIALS("{Student_Name_Initials}"),
    STUDENT_COURSE_QUALIFICATION("{Student_Qualification}"),
    STUDENT_COURSE_NAME("{Student_course}"),
    STUDENT_COURSE_SPEC("{Course_specialization}"),
    STUDENT_VCR_NAME("{Student_VCR_Name}"),
    STUDENT_VCR_HEAD("{VCR_Head}"),
    STUDENT_VCR_REVIEWER("{VCR_Reviewer}");


    private String value;


    TemplateRecord(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
