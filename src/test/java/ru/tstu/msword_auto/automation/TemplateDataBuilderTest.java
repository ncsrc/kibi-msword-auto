package ru.tstu.msword_auto.automation;

import org.junit.Before;
import org.junit.Test;
import ru.tstu.msword_auto.entity.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class TemplateDataBuilderTest {
    private Course course;
    private Date date;
    private GekHead gekHead;
    private List<GekMember> gekMembers;
    private Student student;
    private Vcr vcr;
    private Course emptyCourse;
    private Date emptyDate;
    private GekHead emptyGekHead;
    private List<GekMember> emptyGekMembers;
    private Vcr emptyVcr;
    private TemplateDataBuilder builder;


    @Before
    public void setUp() throws Exception {
        this.course = new Course(1, 1, "group", "q", "cs", "pr");
        this.date = new Date(1, 1, "gr", "2001-01-01", "2001-01-01");
        this.gekHead = new GekHead("cs", "sa", "asd", "s");
        this.gekMembers = Arrays.asList(
                new GekMember(0, 0, "qwe"),
                new GekMember(1, 1, "ads"),
                new GekMember(2, 2, "asd"),
                new GekMember(3, 3, "asd"),
                new GekMember(4, 4, "asd"),
                new GekMember(5, 5, "asd")
        );
        this.student = new Student(
                "qwe",
                "qwe",
                "asd",
                "fsd",
                "scd",
                "csd",
                "asdcx",
                "asdcx",
                "asd"
        );
        this.vcr = new Vcr(1, "name", "head", "rev");

        this.emptyCourse = new Course();
        this.emptyDate = new Date();
        this.emptyGekHead = new GekHead();
        this.emptyGekMembers = Arrays.asList(
                new GekMember(), new GekMember(),
                new GekMember(), new GekMember(),
                new GekMember(), new GekMember()
        );
        this.emptyVcr = new Vcr();

        this.builder = new TemplateDataBuilder();
    }


    @Test
    public void whenAllSettersThenBuildObjectWithSameFields() throws Exception {
        builder.setCourse(course)
                .setDate(date)
                .setGekHead(gekHead)
                .setGekMembers(gekMembers)
                .setStudent(student)
                .setVcr(vcr);

        TemplateData result = builder.build();


        assertEquals(course, result.getCourse());
        assertEquals(date, result.getDate());
        assertEquals(gekHead, result.getGekHead());
        assertEquals(gekMembers, result.getGekMembers());
        assertEquals(student, result.getStudent());
        assertEquals(vcr, result.getVcr());
    }

    @Test
    public void whenOnlyStudentSetterThenSameStudentAndEmptyOthers() throws Exception {
        builder.setStudent(student);
        TemplateData data = builder.build();

        assertEquals(student, data.getStudent());
        assertEquals(emptyCourse, data.getCourse());
        assertEquals(emptyDate, data.getDate());
        assertEquals(emptyGekHead, data.getGekHead());
        assertEquals(emptyGekMembers, data.getGekMembers());
        assertEquals(emptyVcr, data.getVcr());
    }

    @Test
    public void whenOnlyCourseSetterThenSameCourseAndEmptyOthers() throws Exception {
        builder.setStudent(student)
                .setCourse(course);

        TemplateData data = builder.build();

        assertEquals(student, data.getStudent());
        assertEquals(course, data.getCourse());
        assertEquals(emptyDate, data.getDate());
        assertEquals(emptyGekHead, data.getGekHead());
        assertEquals(emptyGekMembers, data.getGekMembers());
        assertEquals(emptyVcr, data.getVcr());
    }

    @Test
    public void whenOnlyDateSetterThenSameDateAndEmptyOthers() throws Exception {
        builder.setStudent(student)
                .setDate(date);

        TemplateData data = builder.build();

        assertEquals(student, data.getStudent());
        assertEquals(emptyCourse, data.getCourse());
        assertEquals(date, data.getDate());
        assertEquals(emptyGekHead, data.getGekHead());
        assertEquals(emptyGekMembers, data.getGekMembers());
        assertEquals(emptyVcr, data.getVcr());
    }

    @Test
    public void whenOnlyGekHeadSetterThenSameGekHeadAndEmptyOthers() throws Exception {
        builder.setStudent(student)
                .setGekHead(gekHead);

        TemplateData data = builder.build();

        assertEquals(student, data.getStudent());
        assertEquals(emptyCourse, data.getCourse());
        assertEquals(emptyDate, data.getDate());
        assertEquals(gekHead, data.getGekHead());
        assertEquals(emptyGekMembers, data.getGekMembers());
        assertEquals(emptyVcr, data.getVcr());
    }

    @Test
    public void whenOnlyGekMemberListSetterThenSameGekMemberListAndEmptyOthers() throws Exception {
        builder.setStudent(student)
                .setGekMembers(gekMembers);

        TemplateData data = builder.build();

        assertEquals(student, data.getStudent());
        assertEquals(emptyCourse, data.getCourse());
        assertEquals(emptyDate, data.getDate());
        assertEquals(emptyGekHead, data.getGekHead());
        assertEquals(gekMembers, data.getGekMembers());
        assertEquals(emptyVcr, data.getVcr());
    }

    @Test
    public void whenOnlyVcrSetterThenSameVcrAndEmptyOthers() throws Exception {
        builder.setStudent(student)
                .setVcr(vcr);

        TemplateData data = builder.build();

        assertEquals(student, data.getStudent());
        assertEquals(emptyCourse, data.getCourse());
        assertEquals(emptyDate, data.getDate());
        assertEquals(emptyGekHead, data.getGekHead());
        assertEquals(emptyGekMembers, data.getGekMembers());
        assertEquals(vcr, data.getVcr());
    }

    @Test
    public void whenNotFullGekMembersListThenGapsAutomaticallyCreated() throws Exception {
        List<GekMember> gekMembers = new ArrayList<>();
        gekMembers.add(new GekMember(0, "asd"));
        gekMembers.add(new GekMember(1, "qwe"));

        List<GekMember> expected = Arrays.asList(
                new GekMember(0, "asd"),
                new GekMember(1, "qwe"),
                new GekMember(), new GekMember(),
                new GekMember(), new GekMember()
        );
        int expectedSize = 6;

        builder.setStudent(student).setGekMembers(gekMembers);
        TemplateData data = builder.build();
        List<GekMember> actual = data.getGekMembers();

        assertEquals(expectedSize, actual.size());
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalStateException.class)
    public void whenStudentIsMissingThenExceptionThrown() throws Exception {
        builder.setCourse(course)
                .setGekMembers(gekMembers)
                .setGekHead(gekHead)
                .setDate(date)
                .setVcr(vcr);

        builder.build();
    }



}