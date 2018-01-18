package ru.tstu.msword_auto.webapp.servlets;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;
import ru.tstu.msword_auto.dao.impl.StudentDao;
import ru.tstu.msword_auto.entity.Student;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;


public class StudentHandlerTest {
    private int studentId;

    private String firstNameI;
    private String lastNameI;
    private String middleNameI;

    private String firstNameR;
    private String lastNameR;
    private String middleNameR;

    private String firstNameD;
    private String lastNameD;
    private String middleNameD;

    private StudentHandler handler;
    private HttpServletRequest request;
    private StudentDao dao;
    private Gson gson;


    @Before
    public void setUp() throws Exception {
        studentId = 1;

        firstNameI = "fi";
        lastNameI = "li";
        middleNameI = "mi";

        firstNameR = "fr";
        lastNameR = "lr";
        middleNameR = "mr";

        firstNameD = "fd";
        lastNameD = "ld";
        middleNameD = "md";

        handler = new StudentHandler();
        request = mock(HttpServletRequest.class);
        dao = mock(StudentDao.class);
        handler.dao = dao;
        gson = new Gson();
    }


    @Test
    public void whenDoListGetsValidEntitiesThenCorrectResponse() throws Exception {
        List<Student> geks = Arrays.asList(
                new Student(
                        firstNameI, lastNameI, middleNameI,
                        firstNameR, lastNameR, middleNameR,
                        firstNameD, lastNameD, middleNameD
                ),
                new Student(
                        firstNameI, lastNameI, middleNameI,
                        firstNameR, lastNameR, middleNameR,
                        firstNameD, lastNameD, middleNameD
                )
        );
        when(dao.readAll()).thenReturn(geks);

        String expected = gson.toJson(geks);
        String actual = handler.doList(request);

        verify(dao).readAll();
        assertEquals(expected, actual);
    }

    @Test
    public void whenDoListGetsDaoSystemExceptionThenErrorDb() throws Exception {
        when(dao.readAll()).thenThrow(DaoSystemException.class);

        String expected = StudentHandler.RESPONSE_ERROR_BD;
        try {
            handler.doList(request);
        } catch (HandlingException e) {
            verify(dao).readAll();
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void whenDoCreateFullEntityThenCorrectResponse() throws Exception {
        when(request.getParameter(StudentHandler.PARAM_FIRST_NAME_I)).thenReturn(firstNameI);
        when(request.getParameter(StudentHandler.PARAM_LAST_NAME_I)).thenReturn(lastNameI);
        when(request.getParameter(StudentHandler.PARAM_MIDDLE_NAME_I)).thenReturn(middleNameI);

        when(request.getParameter(StudentHandler.PARAM_FIRST_NAME_R)).thenReturn(firstNameR);
        when(request.getParameter(StudentHandler.PARAM_LAST_NAME_R)).thenReturn(lastNameR);
        when(request.getParameter(StudentHandler.PARAM_MIDDLE_NAME_R)).thenReturn(middleNameR);

        when(request.getParameter(StudentHandler.PARAM_FIRST_NAME_D)).thenReturn(firstNameD);
        when(request.getParameter(StudentHandler.PARAM_LAST_NAME_D)).thenReturn(lastNameD);
        when(request.getParameter(StudentHandler.PARAM_MIDDLE_NAME_D)).thenReturn(middleNameD);

        Student student = new Student(
                firstNameI, lastNameI, middleNameI,
                firstNameR, lastNameR, middleNameR,
                firstNameD, lastNameD, middleNameD
        );

        String expected = gson.toJson(student);
        String actual = handler.doCreate(request);

        verify(request).getParameter(StudentHandler.PARAM_FIRST_NAME_I);
        verify(request).getParameter(StudentHandler.PARAM_LAST_NAME_I);
        verify(request).getParameter(StudentHandler.PARAM_MIDDLE_NAME_I);

        verify(request).getParameter(StudentHandler.PARAM_FIRST_NAME_R);
        verify(request).getParameter(StudentHandler.PARAM_LAST_NAME_R);
        verify(request).getParameter(StudentHandler.PARAM_MIDDLE_NAME_R);

        verify(request).getParameter(StudentHandler.PARAM_FIRST_NAME_D);
        verify(request).getParameter(StudentHandler.PARAM_LAST_NAME_D);
        verify(request).getParameter(StudentHandler.PARAM_MIDDLE_NAME_D);
        verify(dao).create(student);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoCreateWithEmptyParamsThenExceptionAndCorrectResponse() throws Exception {
        when(request.getParameter(StudentHandler.PARAM_FIRST_NAME_I)).thenReturn(null);
        when(request.getParameter(StudentHandler.PARAM_LAST_NAME_I)).thenReturn(lastNameI);
        when(request.getParameter(StudentHandler.PARAM_MIDDLE_NAME_I)).thenReturn(null);

        when(request.getParameter(StudentHandler.PARAM_FIRST_NAME_R)).thenReturn(firstNameR);
        when(request.getParameter(StudentHandler.PARAM_LAST_NAME_R)).thenReturn(null);
        when(request.getParameter(StudentHandler.PARAM_MIDDLE_NAME_R)).thenReturn(middleNameR);

        when(request.getParameter(StudentHandler.PARAM_FIRST_NAME_D)).thenReturn(null);
        when(request.getParameter(StudentHandler.PARAM_LAST_NAME_D)).thenReturn(lastNameD);
        when(request.getParameter(StudentHandler.PARAM_MIDDLE_NAME_D)).thenReturn(middleNameD);

        try {
            handler.doCreate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(StudentHandler.PARAM_FIRST_NAME_I);
            verify(request).getParameter(StudentHandler.PARAM_LAST_NAME_I);
            verify(request).getParameter(StudentHandler.PARAM_MIDDLE_NAME_I);
            // don't need other verifies, since validation will throw exception at this point

            String expected = AbstractTableHandler.RESPONSE_ERROR_EMPTY_FIELD;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }

    }

    @Test
    public void whenDoCreateThrowsDaoSystemExceptionThenErrorDb() throws Exception {
        when(request.getParameter(StudentHandler.PARAM_FIRST_NAME_I)).thenReturn(firstNameI);
        when(request.getParameter(StudentHandler.PARAM_LAST_NAME_I)).thenReturn(lastNameI);
        when(request.getParameter(StudentHandler.PARAM_MIDDLE_NAME_I)).thenReturn(middleNameI);

        when(request.getParameter(StudentHandler.PARAM_FIRST_NAME_R)).thenReturn(firstNameR);
        when(request.getParameter(StudentHandler.PARAM_LAST_NAME_R)).thenReturn(lastNameR);
        when(request.getParameter(StudentHandler.PARAM_MIDDLE_NAME_R)).thenReturn(middleNameR);

        when(request.getParameter(StudentHandler.PARAM_FIRST_NAME_D)).thenReturn(firstNameD);
        when(request.getParameter(StudentHandler.PARAM_LAST_NAME_D)).thenReturn(lastNameD);
        when(request.getParameter(StudentHandler.PARAM_MIDDLE_NAME_D)).thenReturn(middleNameD);

        Student student = new Student(
                firstNameI, lastNameI, middleNameI,
                firstNameR, lastNameR, middleNameR,
                firstNameD, lastNameD, middleNameD
        );

        doThrow(DaoSystemException.class).when(dao).create(student);

        try {
            handler.doCreate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(StudentHandler.PARAM_FIRST_NAME_I);
            verify(request).getParameter(StudentHandler.PARAM_LAST_NAME_I);
            verify(request).getParameter(StudentHandler.PARAM_MIDDLE_NAME_I);

            verify(request).getParameter(StudentHandler.PARAM_FIRST_NAME_R);
            verify(request).getParameter(StudentHandler.PARAM_LAST_NAME_R);
            verify(request).getParameter(StudentHandler.PARAM_MIDDLE_NAME_R);

            verify(request).getParameter(StudentHandler.PARAM_FIRST_NAME_D);
            verify(request).getParameter(StudentHandler.PARAM_LAST_NAME_D);
            verify(request).getParameter(StudentHandler.PARAM_MIDDLE_NAME_D);
            verify(dao).create(student);

            String expected = StudentHandler.RESPONSE_ERROR_BD;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }

    }

    @Test
    public void whenDoUpdateFullEntityThenCorrectResponse() throws Exception {
        when(request.getParameter(StudentHandler.PARAM_ID)).thenReturn(String.valueOf(studentId));
        when(request.getParameter(StudentHandler.PARAM_FIRST_NAME_I)).thenReturn(firstNameI);
        when(request.getParameter(StudentHandler.PARAM_LAST_NAME_I)).thenReturn(lastNameI);
        when(request.getParameter(StudentHandler.PARAM_MIDDLE_NAME_I)).thenReturn(middleNameI);

        when(request.getParameter(StudentHandler.PARAM_FIRST_NAME_R)).thenReturn(firstNameR);
        when(request.getParameter(StudentHandler.PARAM_LAST_NAME_R)).thenReturn(lastNameR);
        when(request.getParameter(StudentHandler.PARAM_MIDDLE_NAME_R)).thenReturn(middleNameR);

        when(request.getParameter(StudentHandler.PARAM_FIRST_NAME_D)).thenReturn(firstNameD);
        when(request.getParameter(StudentHandler.PARAM_LAST_NAME_D)).thenReturn(lastNameD);
        when(request.getParameter(StudentHandler.PARAM_MIDDLE_NAME_D)).thenReturn(middleNameD);

        Student student = new Student(
                firstNameI, lastNameI, middleNameI,
                firstNameR, lastNameR, middleNameR,
                firstNameD, lastNameD, middleNameD
        );

        String expected = StudentHandler.RESPONSE_OK;
        String actual = handler.doUpdate(request);

        verify(request).getParameter(StudentHandler.PARAM_ID);
        verify(request).getParameter(StudentHandler.PARAM_FIRST_NAME_I);
        verify(request).getParameter(StudentHandler.PARAM_LAST_NAME_I);
        verify(request).getParameter(StudentHandler.PARAM_MIDDLE_NAME_I);

        verify(request).getParameter(StudentHandler.PARAM_FIRST_NAME_R);
        verify(request).getParameter(StudentHandler.PARAM_LAST_NAME_R);
        verify(request).getParameter(StudentHandler.PARAM_MIDDLE_NAME_R);

        verify(request).getParameter(StudentHandler.PARAM_FIRST_NAME_D);
        verify(request).getParameter(StudentHandler.PARAM_LAST_NAME_D);
        verify(request).getParameter(StudentHandler.PARAM_MIDDLE_NAME_D);
        verify(dao).update(studentId, student);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoUpdateWithEmptyParamsThenExceptionAndCorrectResponse() throws Exception {
        when(request.getParameter(StudentHandler.PARAM_ID)).thenReturn(String.valueOf(studentId));
        when(request.getParameter(StudentHandler.PARAM_FIRST_NAME_I)).thenReturn(null);
        when(request.getParameter(StudentHandler.PARAM_LAST_NAME_I)).thenReturn(lastNameI);
        when(request.getParameter(StudentHandler.PARAM_MIDDLE_NAME_I)).thenReturn(null);

        when(request.getParameter(StudentHandler.PARAM_FIRST_NAME_R)).thenReturn(firstNameR);
        when(request.getParameter(StudentHandler.PARAM_LAST_NAME_R)).thenReturn(null);
        when(request.getParameter(StudentHandler.PARAM_MIDDLE_NAME_R)).thenReturn(middleNameR);

        when(request.getParameter(StudentHandler.PARAM_FIRST_NAME_D)).thenReturn(null);
        when(request.getParameter(StudentHandler.PARAM_LAST_NAME_D)).thenReturn(lastNameD);
        when(request.getParameter(StudentHandler.PARAM_MIDDLE_NAME_D)).thenReturn(middleNameD);

        try {
            handler.doUpdate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(StudentHandler.PARAM_ID);
            verify(request).getParameter(StudentHandler.PARAM_FIRST_NAME_I);
            verify(request).getParameter(StudentHandler.PARAM_LAST_NAME_I);
            verify(request).getParameter(StudentHandler.PARAM_MIDDLE_NAME_I);
            // don't need other verifies, since validation will throw exception at this point

            String expected = AbstractTableHandler.RESPONSE_ERROR_EMPTY_FIELD;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void whenDoUpdateThrowsDaoSystemExceptionThenErrorDb() throws Exception {
        when(request.getParameter(StudentHandler.PARAM_ID)).thenReturn(String.valueOf(studentId));
        when(request.getParameter(StudentHandler.PARAM_FIRST_NAME_I)).thenReturn(firstNameI);
        when(request.getParameter(StudentHandler.PARAM_LAST_NAME_I)).thenReturn(lastNameI);
        when(request.getParameter(StudentHandler.PARAM_MIDDLE_NAME_I)).thenReturn(middleNameI);

        when(request.getParameter(StudentHandler.PARAM_FIRST_NAME_R)).thenReturn(firstNameR);
        when(request.getParameter(StudentHandler.PARAM_LAST_NAME_R)).thenReturn(lastNameR);
        when(request.getParameter(StudentHandler.PARAM_MIDDLE_NAME_R)).thenReturn(middleNameR);

        when(request.getParameter(StudentHandler.PARAM_FIRST_NAME_D)).thenReturn(firstNameD);
        when(request.getParameter(StudentHandler.PARAM_LAST_NAME_D)).thenReturn(lastNameD);
        when(request.getParameter(StudentHandler.PARAM_MIDDLE_NAME_D)).thenReturn(middleNameD);

        Student student = new Student(
                firstNameI, lastNameI, middleNameI,
                firstNameR, lastNameR, middleNameR,
                firstNameD, lastNameD, middleNameD
        );

        doThrow(DaoSystemException.class).when(dao).update(studentId, student);

        try {
            handler.doUpdate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(StudentHandler.PARAM_ID);
            verify(request).getParameter(StudentHandler.PARAM_FIRST_NAME_I);
            verify(request).getParameter(StudentHandler.PARAM_LAST_NAME_I);
            verify(request).getParameter(StudentHandler.PARAM_MIDDLE_NAME_I);

            verify(request).getParameter(StudentHandler.PARAM_FIRST_NAME_R);
            verify(request).getParameter(StudentHandler.PARAM_LAST_NAME_R);
            verify(request).getParameter(StudentHandler.PARAM_MIDDLE_NAME_R);

            verify(request).getParameter(StudentHandler.PARAM_FIRST_NAME_D);
            verify(request).getParameter(StudentHandler.PARAM_LAST_NAME_D);
            verify(request).getParameter(StudentHandler.PARAM_MIDDLE_NAME_D);
            verify(dao).update(studentId, student);

            String expected = StudentHandler.RESPONSE_ERROR_BD;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }

    }

    @Test
    public void whenDoDeleteSuccessThenCorrectAnswer() throws Exception {
        when(request.getParameter(StudentHandler.PARAM_ID)).thenReturn(String.valueOf(studentId));

        String expected = StudentHandler.RESPONSE_OK;
        String actual = handler.doDelete(request);

        verify(request).getParameter(StudentHandler.PARAM_ID);
        verify(dao).delete(studentId);
        assertEquals(expected, actual);
    }

    @Test
    public void whenDoDeleteThrowsDaoSystemExceptionThenErrorDb() throws Exception {
        when(request.getParameter(StudentHandler.PARAM_ID)).thenReturn(String.valueOf(studentId));
        doThrow(DaoSystemException.class).when(dao).delete(studentId);

        try {
            handler.doDelete(request);
        } catch (HandlingException e) {
            String expected = StudentHandler.RESPONSE_ERROR_BD;
            String actual = e.getMessage();

            verify(request).getParameter(StudentHandler.PARAM_ID);
            verify(dao).delete(studentId);
            assertEquals(expected, actual);
        }

    }



}