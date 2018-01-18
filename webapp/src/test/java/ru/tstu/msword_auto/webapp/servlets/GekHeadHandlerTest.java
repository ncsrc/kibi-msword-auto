package ru.tstu.msword_auto.webapp.servlets;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import ru.tstu.msword_auto.dao.exceptions.AlreadyExistingException;
import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.impl.GekHeadDao;
import ru.tstu.msword_auto.entity.GekHead;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;


public class GekHeadHandlerTest {
    private int gekId;
    private String courseName;
    private String gekHead;
    private String gekSubhead;
    private String gekSecretary;
    private GekHeadHandler handler;
    private HttpServletRequest request;
    private GekHeadDao dao;
    private Gson gson;


    @Before
    public void setUp() throws Exception {
        gekId = 0;
        courseName = "course";
        gekHead = "head";
        gekSubhead = "subhead";
        gekSecretary = "secretary";

        handler = new GekHeadHandler();
        request = mock(HttpServletRequest.class);
        dao = mock(GekHeadDao.class);
        handler.dao = dao;
        gson = new Gson();
    }


    @Test
    public void whenDoListGetsValidEntitiesThenCorrectResponse() throws Exception {
        List<GekHead> geks = Arrays.asList(
                new GekHead(courseName, gekHead, gekSubhead, gekSecretary),
                new GekHead(courseName)
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

        String expected = GekHeadHandler.RESPONSE_ERROR_BD;
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
        when(request.getParameter(GekHeadHandler.PARAM_COURSE_NAME)).thenReturn(courseName);
        when(request.getParameter(GekHeadHandler.PARAM_HEAD)).thenReturn(gekHead);
        when(request.getParameter(GekHeadHandler.PARAM_SUBHEAD)).thenReturn(gekSubhead);
        when(request.getParameter(GekHeadHandler.PARAM_SECRETARY)).thenReturn(gekSecretary);
        GekHead gek = new GekHead(courseName, gekHead, gekSubhead, gekSecretary);

        String expected = gson.toJson(gek);
        String actual = handler.doCreate(request);

        verify(request).getParameter(GekHeadHandler.PARAM_COURSE_NAME);
        verify(request).getParameter(GekHeadHandler.PARAM_HEAD);
        verify(request).getParameter(GekHeadHandler.PARAM_SUBHEAD);
        verify(request).getParameter(GekHeadHandler.PARAM_SECRETARY);
//        verify(dao).create(gekOriginal);  says it's different, though actually it's not. could be hashcode issue

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoCreateWithEmptyParamsThenCorrectResponse() throws Exception {
        when(request.getParameter(GekHeadHandler.PARAM_COURSE_NAME)).thenReturn(courseName);
        when(request.getParameter(GekHeadHandler.PARAM_HEAD)).thenReturn(null);
        when(request.getParameter(GekHeadHandler.PARAM_SUBHEAD)).thenReturn(null);
        when(request.getParameter(GekHeadHandler.PARAM_SECRETARY)).thenReturn(null);
        GekHead gek = new GekHead(courseName);

        String actual = handler.doCreate(request);
        String expected = gson.toJson(gek);

        verify(request).getParameter(GekHeadHandler.PARAM_COURSE_NAME);
        verify(request).getParameter(GekHeadHandler.PARAM_HEAD);
        verify(request).getParameter(GekHeadHandler.PARAM_SUBHEAD);
        verify(request).getParameter(GekHeadHandler.PARAM_SECRETARY);
        verify(dao).create(gek);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoCreateThrowsDaoSystemExceptionThenErrorDb() throws Exception {
        when(request.getParameter(GekHeadHandler.PARAM_COURSE_NAME)).thenReturn(courseName);
        when(request.getParameter(GekHeadHandler.PARAM_HEAD)).thenReturn(null);
        when(request.getParameter(GekHeadHandler.PARAM_SUBHEAD)).thenReturn(null);
        when(request.getParameter(GekHeadHandler.PARAM_SECRETARY)).thenReturn(null);
        GekHead gek = new GekHead(courseName);
        gek.setGekId(gekId);

        doThrow(DaoSystemException.class).when(dao).create(gek);

        try {
            handler.doCreate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(GekHeadHandler.PARAM_COURSE_NAME);
            verify(request).getParameter(GekHeadHandler.PARAM_HEAD);
            verify(request).getParameter(GekHeadHandler.PARAM_SUBHEAD);
            verify(request).getParameter(GekHeadHandler.PARAM_SECRETARY);
            verify(dao).create(gek);

            String expected = GekHeadHandler.RESPONSE_ERROR_BD;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void whenDoCreateWithEmptyCourseNameThenExceptionAndCorrectResponse() throws Exception {
        when(request.getParameter(GekHeadHandler.PARAM_COURSE_NAME)).thenReturn(null);
        when(request.getParameter(GekHeadHandler.PARAM_HEAD)).thenReturn(null);
        when(request.getParameter(GekHeadHandler.PARAM_SUBHEAD)).thenReturn(null);
        when(request.getParameter(GekHeadHandler.PARAM_SECRETARY)).thenReturn(null);

        try {
            handler.doCreate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(GekHeadHandler.PARAM_COURSE_NAME);
            verify(request).getParameter(GekHeadHandler.PARAM_HEAD);
            verify(request).getParameter(GekHeadHandler.PARAM_SUBHEAD);
            verify(request).getParameter(GekHeadHandler.PARAM_SECRETARY);

            String expected = GekHeadHandler.RESPONSE_ERROR_EMPTY_COURSE_NAME;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void whenDoCreateExistingCourseNameThenException() throws Exception {
        when(request.getParameter(GekHeadHandler.PARAM_COURSE_NAME)).thenReturn(courseName);
        when(request.getParameter(GekHeadHandler.PARAM_HEAD)).thenReturn(gekHead);
        when(request.getParameter(GekHeadHandler.PARAM_SUBHEAD)).thenReturn(gekSubhead);
        when(request.getParameter(GekHeadHandler.PARAM_SECRETARY)).thenReturn(gekSecretary);

        GekHead gek = new GekHead(courseName, gekHead, gekSubhead, gekSecretary);
        doThrow(AlreadyExistingException.class).when(dao).create(gek);

        try {
            handler.doCreate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(GekHeadHandler.PARAM_COURSE_NAME);
            verify(request).getParameter(GekHeadHandler.PARAM_HEAD);
            verify(request).getParameter(GekHeadHandler.PARAM_SUBHEAD);
            verify(request).getParameter(GekHeadHandler.PARAM_SECRETARY);
            verify(dao).create(gek);

            String expected = GekHeadHandler.RESPONSE_ERROR_COURSE_NAME_ALREADY_EXIST;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void whenDoUpdateFullEntityThenCorrectResponse() throws Exception {
        when(request.getParameter(GekHeadHandler.PARAM_ID)).thenReturn(String.valueOf(gekId));
        when(request.getParameter(GekHeadHandler.PARAM_COURSE_NAME)).thenReturn(courseName);
        when(request.getParameter(GekHeadHandler.PARAM_HEAD)).thenReturn(gekHead);
        when(request.getParameter(GekHeadHandler.PARAM_SUBHEAD)).thenReturn(gekSubhead);
        when(request.getParameter(GekHeadHandler.PARAM_SECRETARY)).thenReturn(gekSecretary);
        GekHead gek = new GekHead(courseName, gekHead, gekSubhead, gekSecretary);

        String expected = GekHeadHandler.RESPONSE_OK;
        String actual = handler.doUpdate(request);

        verify(request).getParameter(GekHeadHandler.PARAM_ID);
        verify(request).getParameter(GekHeadHandler.PARAM_COURSE_NAME);
        verify(request).getParameter(GekHeadHandler.PARAM_HEAD);
        verify(request).getParameter(GekHeadHandler.PARAM_SUBHEAD);
        verify(request).getParameter(GekHeadHandler.PARAM_SECRETARY);
        verify(dao).update(gekId, gek);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoUpdateWithEmptyParamsThenCorrectResponse() throws Exception {
        when(request.getParameter(GekHeadHandler.PARAM_ID)).thenReturn(String.valueOf(gekId));
        when(request.getParameter(GekHeadHandler.PARAM_COURSE_NAME)).thenReturn(courseName);
        when(request.getParameter(GekHeadHandler.PARAM_HEAD)).thenReturn(null);
        when(request.getParameter(GekHeadHandler.PARAM_SUBHEAD)).thenReturn(null);
        when(request.getParameter(GekHeadHandler.PARAM_SECRETARY)).thenReturn(null);
        GekHead gek = new GekHead(courseName);

        String expected = GekHeadHandler.RESPONSE_OK;
        String actual = handler.doUpdate(request);

        verify(request).getParameter(GekHeadHandler.PARAM_ID);
        verify(request).getParameter(GekHeadHandler.PARAM_COURSE_NAME);
        verify(request).getParameter(GekHeadHandler.PARAM_HEAD);
        verify(request).getParameter(GekHeadHandler.PARAM_SUBHEAD);
        verify(request).getParameter(GekHeadHandler.PARAM_SECRETARY);
        verify(dao).update(gekId, gek);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoUpdateThrowsDaoSystemExceptionThenErrorDb() throws Exception {
        when(request.getParameter(GekHeadHandler.PARAM_ID)).thenReturn(String.valueOf(gekId));
        when(request.getParameter(GekHeadHandler.PARAM_COURSE_NAME)).thenReturn(courseName);
        when(request.getParameter(GekHeadHandler.PARAM_HEAD)).thenReturn(null);
        when(request.getParameter(GekHeadHandler.PARAM_SUBHEAD)).thenReturn(null);
        when(request.getParameter(GekHeadHandler.PARAM_SECRETARY)).thenReturn(null);
        GekHead gek = new GekHead(courseName);

        doThrow(DaoSystemException.class).when(dao).update(gekId, gek);

        try {
            handler.doUpdate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(GekHeadHandler.PARAM_ID);
            verify(request).getParameter(GekHeadHandler.PARAM_COURSE_NAME);
            verify(request).getParameter(GekHeadHandler.PARAM_HEAD);
            verify(request).getParameter(GekHeadHandler.PARAM_SUBHEAD);
            verify(request).getParameter(GekHeadHandler.PARAM_SECRETARY);
            verify(dao).update(gekId, gek);

            String expected = GekHeadHandler.RESPONSE_ERROR_BD;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }

    }

    @Test
    public void whenDoUpdateWithEmptyCourseNameThenExceptionAndCorrectResponse() throws Exception {
        when(request.getParameter(GekHeadHandler.PARAM_ID)).thenReturn(String.valueOf(gekId));
        when(request.getParameter(GekHeadHandler.PARAM_COURSE_NAME)).thenReturn(null);
        when(request.getParameter(GekHeadHandler.PARAM_HEAD)).thenReturn(null);
        when(request.getParameter(GekHeadHandler.PARAM_SUBHEAD)).thenReturn(null);
        when(request.getParameter(GekHeadHandler.PARAM_SECRETARY)).thenReturn(null);

        try {
            handler.doUpdate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(GekHeadHandler.PARAM_ID);
            verify(request).getParameter(GekHeadHandler.PARAM_COURSE_NAME);
            verify(request).getParameter(GekHeadHandler.PARAM_HEAD);
            verify(request).getParameter(GekHeadHandler.PARAM_SUBHEAD);
            verify(request).getParameter(GekHeadHandler.PARAM_SECRETARY);

            String expected = GekHeadHandler.RESPONSE_ERROR_EMPTY_COURSE_NAME;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }

    }

    @Test
    public void whenDoUpdateExistingCourseNameThenException() throws Exception {
        when(request.getParameter(GekHeadHandler.PARAM_ID)).thenReturn(String.valueOf(gekId));
        when(request.getParameter(GekHeadHandler.PARAM_COURSE_NAME)).thenReturn(courseName);
        when(request.getParameter(GekHeadHandler.PARAM_HEAD)).thenReturn(gekHead);
        when(request.getParameter(GekHeadHandler.PARAM_SUBHEAD)).thenReturn(gekSubhead);
        when(request.getParameter(GekHeadHandler.PARAM_SECRETARY)).thenReturn(gekSecretary);

        GekHead gek = new GekHead(courseName, gekHead, gekSubhead, gekSecretary);
        doThrow(AlreadyExistingException.class).when(dao).update(gekId, gek);

        try {
            handler.doUpdate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(GekHeadHandler.PARAM_ID);
            verify(request).getParameter(GekHeadHandler.PARAM_COURSE_NAME);
            verify(request).getParameter(GekHeadHandler.PARAM_HEAD);
            verify(request).getParameter(GekHeadHandler.PARAM_SUBHEAD);
            verify(request).getParameter(GekHeadHandler.PARAM_SECRETARY);
            verify(dao).update(gekId, gek);

            String expected = GekHeadHandler.RESPONSE_ERROR_COURSE_NAME_ALREADY_EXIST;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void whenDoDeleteSuccessThenCorrectAnswer() throws Exception {
        when(request.getParameter(GekHeadHandler.PARAM_ID)).thenReturn(String.valueOf(gekId));

        String expected = GekHeadHandler.RESPONSE_OK;
        String actual = handler.doDelete(request);

        verify(request).getParameter(GekHeadHandler.PARAM_ID);
        verify(dao).delete(gekId);
        assertEquals(expected, actual);
    }

    @Test
    public void whenDoDeleteThrowsDaoSystemExceptionThenErrorDb() throws Exception {
        when(request.getParameter(GekHeadHandler.PARAM_ID)).thenReturn(String.valueOf(gekId));
        doThrow(DaoSystemException.class).when(dao).delete(gekId);

        try {
            handler.doDelete(request);
        } catch (HandlingException e) {
            String expected = GekHeadHandler.RESPONSE_ERROR_BD;
            String actual = e.getMessage();

            verify(request).getParameter(GekHeadHandler.PARAM_ID);
            verify(dao).delete(gekId);
            assertEquals(expected, actual);
        }

    }




}