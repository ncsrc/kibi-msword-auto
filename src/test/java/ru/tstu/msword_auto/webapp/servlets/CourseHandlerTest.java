package ru.tstu.msword_auto.webapp.servlets;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import ru.tstu.msword_auto.dao.exceptions.AlreadyExistingException;
import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;
import ru.tstu.msword_auto.dao.impl.CourseDao;
import ru.tstu.msword_auto.entity.Course;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;


public class CourseHandlerTest {
    private int studentId;
    private int subgroupId;
    private String groupName;
    private String qualification;
    private String courseName;
    private String profile;
    private CourseHandler handler;
    private HttpServletRequest request;
    private CourseDao dao;
    private Gson gson;


    @Before
    public void setUp() throws Exception {
        studentId = 1;
        subgroupId = 2;
        groupName = "group";
        qualification = "Бакалавр";
        courseName = "Торговое дело";
        profile = "profile";

        handler = new CourseHandler();
        request = mock(HttpServletRequest.class);
        dao = mock(CourseDao.class);
        handler.dao = dao;
        gson = new Gson();
    }


    @Test
    public void whenDoListGetsValidEntitiesThenCorrectResponse() throws Exception {
        when(request.getParameter(CourseHandler.PARAM_STUDENT_ID)).thenReturn(String.valueOf(studentId));
        List<Course> courses = Arrays.asList(
                new Course(studentId, subgroupId, groupName, qualification, courseName, profile),
                new Course(studentId, subgroupId, groupName)
        );
        when(dao.readByForeignKey(studentId)).thenReturn(courses);

        String expected = gson.toJson(courses);
        String actual = handler.doList(request);

        verify(request).getParameter(CourseHandler.PARAM_STUDENT_ID);
        verify(dao).readByForeignKey(studentId);
        assertEquals(expected, actual);
    }

    @Test
    public void whenDoListGetsNoSuchEntityThenEmptyResponse() throws Exception {
        when(request.getParameter(CourseHandler.PARAM_STUDENT_ID)).thenReturn(String.valueOf(studentId));
        when(dao.readByForeignKey(studentId)).thenThrow(NoSuchEntityException.class);

        String expected = gson.toJson(Collections.EMPTY_LIST);
        String actual = handler.doList(request);

        verify(request).getParameter(CourseHandler.PARAM_STUDENT_ID);
        verify(dao).readByForeignKey(studentId);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoListGetsDaoSystemExceptionThenErrorDb() throws Exception {
        when(request.getParameter(CourseHandler.PARAM_STUDENT_ID)).thenReturn(String.valueOf(studentId));
        when(dao.readByForeignKey(studentId)).thenThrow(DaoSystemException.class);

        String expected = CourseHandler.RESPONSE_ERROR_BD;
        try {
            handler.doList(request);
        } catch (HandlingException e) {
            verify(request).getParameter(CourseHandler.PARAM_STUDENT_ID);
            verify(dao).readByForeignKey(studentId);
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void whenDoCreateFullEntityThenCorrectResponse() throws Exception {
        when(request.getParameter(CourseHandler.PARAM_STUDENT_ID)).thenReturn(String.valueOf(studentId));
        when(request.getParameter(CourseHandler.PARAM_SUBGROUP_ID)).thenReturn(String.valueOf(subgroupId));
        when(request.getParameter(CourseHandler.PARAM_GROUP_NAME)).thenReturn(groupName);
        when(request.getParameter(CourseHandler.PARAM_STUDENT_QUALIFICATION)).thenReturn(qualification);
        when(request.getParameter(CourseHandler.PARAM_COURSE_NAME)).thenReturn(courseName);
        when(request.getParameter(CourseHandler.PARAM_STUDENT_PROFILE)).thenReturn(profile);
        Course course = new Course(studentId, subgroupId, groupName, qualification, courseName, profile);

        String expected = gson.toJson(course);
        String actual = handler.doCreate(request);

        verify(request).getParameter(CourseHandler.PARAM_STUDENT_ID);
        verify(request).getParameter(CourseHandler.PARAM_SUBGROUP_ID);
        verify(request).getParameter(CourseHandler.PARAM_GROUP_NAME);
        verify(request).getParameter(CourseHandler.PARAM_STUDENT_QUALIFICATION);
        verify(request).getParameter(CourseHandler.PARAM_COURSE_NAME);
        verify(request).getParameter(CourseHandler.PARAM_STUDENT_PROFILE);
        verify(dao).create(course);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoCreateWithEmptyParamsThenCorrectResponse() throws Exception {
        when(request.getParameter(CourseHandler.PARAM_STUDENT_ID)).thenReturn(String.valueOf(studentId));
        when(request.getParameter(CourseHandler.PARAM_SUBGROUP_ID)).thenReturn(String.valueOf(subgroupId));
        when(request.getParameter(CourseHandler.PARAM_GROUP_NAME)).thenReturn(null);
        when(request.getParameter(CourseHandler.PARAM_STUDENT_QUALIFICATION)).thenReturn(null);
        when(request.getParameter(CourseHandler.PARAM_COURSE_NAME)).thenReturn(null);
        when(request.getParameter(CourseHandler.PARAM_STUDENT_PROFILE)).thenReturn(null);
        Course course = new Course(studentId, subgroupId, "", "", "", "");

        String expected = gson.toJson(course);
        String actual = handler.doCreate(request);

        verify(request).getParameter(CourseHandler.PARAM_STUDENT_ID);
        verify(request).getParameter(CourseHandler.PARAM_SUBGROUP_ID);
        verify(request).getParameter(CourseHandler.PARAM_GROUP_NAME);
        verify(request).getParameter(CourseHandler.PARAM_STUDENT_QUALIFICATION);
        verify(request).getParameter(CourseHandler.PARAM_COURSE_NAME);
        verify(request).getParameter(CourseHandler.PARAM_STUDENT_PROFILE);
        verify(dao).create(course);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoCreateThrowsDaoSystemExceptionThenErrorDb() throws Exception {
        when(request.getParameter(CourseHandler.PARAM_STUDENT_ID)).thenReturn(String.valueOf(studentId));
        when(request.getParameter(CourseHandler.PARAM_SUBGROUP_ID)).thenReturn(String.valueOf(subgroupId));
        when(request.getParameter(CourseHandler.PARAM_GROUP_NAME)).thenReturn(null);
        when(request.getParameter(CourseHandler.PARAM_STUDENT_QUALIFICATION)).thenReturn(null);
        when(request.getParameter(CourseHandler.PARAM_COURSE_NAME)).thenReturn(null);
        when(request.getParameter(CourseHandler.PARAM_STUDENT_PROFILE)).thenReturn(null);
        Course course = new Course(studentId, subgroupId,"", "", "", "");

        doThrow(DaoSystemException.class).when(dao).create(course);

        try {
            handler.doCreate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(CourseHandler.PARAM_STUDENT_ID);
            verify(request).getParameter(CourseHandler.PARAM_SUBGROUP_ID);
            verify(request).getParameter(CourseHandler.PARAM_GROUP_NAME);
            verify(request).getParameter(CourseHandler.PARAM_STUDENT_QUALIFICATION);
            verify(request).getParameter(CourseHandler.PARAM_COURSE_NAME);
            verify(request).getParameter(CourseHandler.PARAM_STUDENT_PROFILE);
            verify(dao).create(course);

            String expected = CourseHandler.RESPONSE_ERROR_BD;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }

    }

    @Test
    public void whenDoCreateExistingCourseThenException() throws Exception {
        when(request.getParameter(CourseHandler.PARAM_STUDENT_ID)).thenReturn(String.valueOf(studentId));
        when(request.getParameter(CourseHandler.PARAM_SUBGROUP_ID)).thenReturn(String.valueOf(subgroupId));
        when(request.getParameter(CourseHandler.PARAM_GROUP_NAME)).thenReturn(groupName);
        when(request.getParameter(CourseHandler.PARAM_STUDENT_QUALIFICATION)).thenReturn(qualification);
        when(request.getParameter(CourseHandler.PARAM_COURSE_NAME)).thenReturn(courseName);
        when(request.getParameter(CourseHandler.PARAM_STUDENT_PROFILE)).thenReturn(profile);
        Course course = new Course(studentId, subgroupId, groupName, qualification, courseName, profile);

        doThrow(AlreadyExistingException.class).when(dao).create(course);

        try {
            handler.doCreate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(CourseHandler.PARAM_STUDENT_ID);
            verify(request).getParameter(CourseHandler.PARAM_SUBGROUP_ID);
            verify(request).getParameter(CourseHandler.PARAM_GROUP_NAME);
            verify(request).getParameter(CourseHandler.PARAM_STUDENT_QUALIFICATION);
            verify(request).getParameter(CourseHandler.PARAM_COURSE_NAME);
            verify(request).getParameter(CourseHandler.PARAM_STUDENT_PROFILE);
            verify(dao).create(course);

            String expected = CourseHandler.RESPONSE_ERROR_ALREADY_EXISTS;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void whenDoUpdateFullEntityThenCorrectResponse() throws Exception {
        when(request.getParameter(CourseHandler.PARAM_STUDENT_ID)).thenReturn(String.valueOf(studentId));
        when(request.getParameter(CourseHandler.PARAM_SUBGROUP_ID)).thenReturn(String.valueOf(subgroupId));
        when(request.getParameter(CourseHandler.PARAM_GROUP_NAME)).thenReturn(groupName);
        when(request.getParameter(CourseHandler.PARAM_STUDENT_QUALIFICATION)).thenReturn(qualification);
        when(request.getParameter(CourseHandler.PARAM_COURSE_NAME)).thenReturn(courseName);
        when(request.getParameter(CourseHandler.PARAM_STUDENT_PROFILE)).thenReturn(profile);

        Course course = new Course();
        course.setSubgroupId(subgroupId);
        course.setGroupName(groupName);
        course.setQualification(qualification);
        course.setCourseName(courseName);
        course.setProfile(profile);

        String expected = CourseHandler.RESPONSE_OK;
        String actual = handler.doUpdate(request);

        verify(request).getParameter(CourseHandler.PARAM_STUDENT_ID);
        verify(request).getParameter(CourseHandler.PARAM_SUBGROUP_ID);
        verify(request).getParameter(CourseHandler.PARAM_GROUP_NAME);
        verify(request).getParameter(CourseHandler.PARAM_STUDENT_QUALIFICATION);
        verify(request).getParameter(CourseHandler.PARAM_COURSE_NAME);
        verify(request).getParameter(CourseHandler.PARAM_STUDENT_PROFILE);
        verify(dao).update(studentId, course);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoUpdateWithEmptyParamsThenCorrectResponse() throws Exception {
        when(request.getParameter(CourseHandler.PARAM_STUDENT_ID)).thenReturn(String.valueOf(studentId));
        when(request.getParameter(CourseHandler.PARAM_SUBGROUP_ID)).thenReturn(String.valueOf(subgroupId));
        when(request.getParameter(CourseHandler.PARAM_GROUP_NAME)).thenReturn(null);
        when(request.getParameter(CourseHandler.PARAM_STUDENT_QUALIFICATION)).thenReturn(null);
        when(request.getParameter(CourseHandler.PARAM_COURSE_NAME)).thenReturn(null);
        when(request.getParameter(CourseHandler.PARAM_STUDENT_PROFILE)).thenReturn(null);

        Course course = new Course();
        course.setSubgroupId(subgroupId);
        course.setGroupName("");
        course.setQualification("");
        course.setCourseName("");
        course.setProfile("");

        String expected = CourseHandler.RESPONSE_OK;
        String actual = handler.doUpdate(request);

        verify(request).getParameter(CourseHandler.PARAM_STUDENT_ID);
        verify(request).getParameter(CourseHandler.PARAM_SUBGROUP_ID);
        verify(request).getParameter(CourseHandler.PARAM_GROUP_NAME);
        verify(request).getParameter(CourseHandler.PARAM_STUDENT_QUALIFICATION);
        verify(request).getParameter(CourseHandler.PARAM_COURSE_NAME);
        verify(request).getParameter(CourseHandler.PARAM_STUDENT_PROFILE);
        verify(dao).update(studentId, course);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoUpdateThrowsDaoSystemExceptionThenErrorDb() throws Exception {
        when(request.getParameter(CourseHandler.PARAM_STUDENT_ID)).thenReturn(String.valueOf(studentId));
        when(request.getParameter(CourseHandler.PARAM_SUBGROUP_ID)).thenReturn(String.valueOf(subgroupId));
        when(request.getParameter(CourseHandler.PARAM_GROUP_NAME)).thenReturn(null);
        when(request.getParameter(CourseHandler.PARAM_STUDENT_QUALIFICATION)).thenReturn(null);
        when(request.getParameter(CourseHandler.PARAM_COURSE_NAME)).thenReturn(null);
        when(request.getParameter(CourseHandler.PARAM_STUDENT_PROFILE)).thenReturn(null);

        Course course = new Course();
        course.setGroupName(groupName);
        course.setQualification(qualification);
        course.setCourseName(courseName);
        course.setProfile(profile);

        doThrow(DaoSystemException.class).when(dao).update(studentId, course);

        try {
            handler.doUpdate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(CourseHandler.PARAM_STUDENT_ID);
            verify(request).getParameter(CourseHandler.PARAM_SUBGROUP_ID);
            verify(request).getParameter(CourseHandler.PARAM_GROUP_NAME);
            verify(request).getParameter(CourseHandler.PARAM_STUDENT_QUALIFICATION);
            verify(request).getParameter(CourseHandler.PARAM_COURSE_NAME);
            verify(request).getParameter(CourseHandler.PARAM_STUDENT_PROFILE);
            verify(dao).update(studentId, course);

            String expected = CourseHandler.RESPONSE_ERROR_BD;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }

    }

    @Test
    public void whenDoUpdateExistingCourseThenException() throws Exception {
        when(request.getParameter(CourseHandler.PARAM_STUDENT_ID)).thenReturn(String.valueOf(studentId));
        when(request.getParameter(CourseHandler.PARAM_SUBGROUP_ID)).thenReturn(String.valueOf(subgroupId));
        when(request.getParameter(CourseHandler.PARAM_GROUP_NAME)).thenReturn(groupName);
        when(request.getParameter(CourseHandler.PARAM_STUDENT_QUALIFICATION)).thenReturn(qualification);
        when(request.getParameter(CourseHandler.PARAM_COURSE_NAME)).thenReturn(courseName);
        when(request.getParameter(CourseHandler.PARAM_STUDENT_PROFILE)).thenReturn(profile);
        Course course = new Course(studentId, subgroupId, groupName, qualification, courseName, profile);

        doThrow(AlreadyExistingException.class).when(dao).update(studentId, course);

        try {
            handler.doUpdate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(CourseHandler.PARAM_STUDENT_ID);
            verify(request).getParameter(CourseHandler.PARAM_SUBGROUP_ID);
            verify(request).getParameter(CourseHandler.PARAM_GROUP_NAME);
            verify(request).getParameter(CourseHandler.PARAM_STUDENT_QUALIFICATION);
            verify(request).getParameter(CourseHandler.PARAM_COURSE_NAME);
            verify(request).getParameter(CourseHandler.PARAM_STUDENT_PROFILE);
            verify(dao).update(studentId, course);

            String expected = CourseHandler.RESPONSE_ERROR_ALREADY_EXISTS;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void whenDoDeleteSuccessThenCorrectAnswer() throws Exception {
        when(request.getParameter(CourseHandler.PARAM_STUDENT_ID)).thenReturn(String.valueOf(studentId));

        String expected = CourseHandler.RESPONSE_OK;
        String actual = handler.doDelete(request);

        verify(request).getParameter(CourseHandler.PARAM_STUDENT_ID);
        verify(dao).delete(studentId);
        assertEquals(expected, actual);
    }

    @Test
    public void whenDoDeleteThrowsDaoSystemExceptionThenErrorDb() throws Exception {
        when(request.getParameter(CourseHandler.PARAM_STUDENT_ID)).thenReturn(String.valueOf(studentId));
        doThrow(DaoSystemException.class).when(dao).delete(studentId);

        try {
            handler.doDelete(request);
        } catch (HandlingException e) {
            String expected = CourseHandler.RESPONSE_ERROR_BD;
            String actual = e.getMessage();

            verify(request).getParameter(CourseHandler.PARAM_STUDENT_ID);
            verify(dao).delete(studentId);
            assertEquals(expected, actual);
        }

    }






}