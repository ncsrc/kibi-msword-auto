package ru.tstu.msword_auto.webapp.servlets;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import ru.tstu.msword_auto.dao.exceptions.AlreadyExistingException;
import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;
import ru.tstu.msword_auto.dao.impl.VcrDao;
import ru.tstu.msword_auto.entity.Vcr;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class VcrHandlerTest {
    private String studentId;
    private String vcrName;
    private String vcrHead;
    private String vcrReviewer;
    private VcrHandler handler;
    private HttpServletRequest request;
    private VcrDao dao;
    private Gson gson;


    @Before
    public void setUp() throws Exception {
        studentId = "1";
        vcrName = "name";
        vcrHead = "head";
        vcrReviewer = "reviewer";

        handler = new VcrHandler();
        request = mock(HttpServletRequest.class);
        dao = mock(VcrDao.class);
        handler.dao = dao;
        gson = new Gson();
    }


    @Test
    public void whenDoListGetsValidEntitiesThenCorrectResponse() throws Exception {
        when(request.getParameter(VcrHandler.PARAM_STUDENT_ID)).thenReturn(studentId);
        List<Vcr> vcrs = Arrays.asList(
                new Vcr(1, vcrName, vcrHead, vcrReviewer),
                new Vcr(1, vcrName)
        );
        when(dao.readByForeignKey(Integer.parseInt(studentId))).thenReturn(vcrs);

        String expected = gson.toJson(vcrs);
        String actual = handler.doList(request);

        verify(request).getParameter(VcrHandler.PARAM_STUDENT_ID);
        verify(dao).readByForeignKey(Integer.parseInt(studentId));
        assertEquals(expected, actual);
    }

    @Test
    public void whenDoListGetsNoSuchEntityThenEmptyResponse() throws Exception {
        when(request.getParameter(VcrHandler.PARAM_STUDENT_ID)).thenReturn(studentId);
        when(dao.readByForeignKey(Integer.parseInt(studentId))).thenThrow(NoSuchEntityException.class);

        String expected = gson.toJson(Collections.EMPTY_LIST);
        String actual = handler.doList(request);

        verify(request).getParameter(VcrHandler.PARAM_STUDENT_ID);
        verify(dao).readByForeignKey(Integer.parseInt(studentId));

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoListGetsDaoSystemExceptionThenErrorDb() throws Exception {
        when(request.getParameter(VcrHandler.PARAM_STUDENT_ID)).thenReturn(studentId);
        when(dao.readByForeignKey(Integer.parseInt(studentId))).thenThrow(DaoSystemException.class);

        String expected = VcrHandler.RESPONSE_ERROR_BD;
        try {
            handler.doList(request);
        } catch (HandlingException e) {
            verify(request).getParameter(VcrHandler.PARAM_STUDENT_ID);
            verify(dao).readByForeignKey(Integer.parseInt(studentId));
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void whenDoCreateFullEntityThenCorrectResponse() throws Exception {
        when(request.getParameter(VcrHandler.PARAM_STUDENT_ID)).thenReturn(studentId);
        when(request.getParameter(VcrHandler.PARAM_NAME)).thenReturn(vcrName);
        when(request.getParameter(VcrHandler.PARAM_HEAD)).thenReturn(vcrHead);
        when(request.getParameter(VcrHandler.PARAM_REVIEWER)).thenReturn(vcrReviewer);
        Vcr vcr = new Vcr(1, vcrName, vcrHead, vcrReviewer);

        String expected = gson.toJson(vcr);
        String actual = handler.doCreate(request);

        verify(request).getParameter(VcrHandler.PARAM_STUDENT_ID);
        verify(request).getParameter(VcrHandler.PARAM_NAME);
        verify(request).getParameter(VcrHandler.PARAM_HEAD);
        verify(request).getParameter(VcrHandler.PARAM_REVIEWER);
        verify(dao).create(vcr);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoCreateWithEmptyParamsThenCorrectResponse() throws Exception {
        when(request.getParameter(VcrHandler.PARAM_STUDENT_ID)).thenReturn(studentId);
        when(request.getParameter(VcrHandler.PARAM_NAME)).thenReturn(vcrName);
        when(request.getParameter(VcrHandler.PARAM_HEAD)).thenReturn(null);
        when(request.getParameter(VcrHandler.PARAM_REVIEWER)).thenReturn(null);
        Vcr vcr = new Vcr(1, vcrName, "", "");

        String expected = gson.toJson(vcr);
        String actual = handler.doCreate(request);

        verify(request).getParameter(VcrHandler.PARAM_STUDENT_ID);
        verify(request).getParameter(VcrHandler.PARAM_NAME);
        verify(request).getParameter(VcrHandler.PARAM_HEAD);
        verify(request).getParameter(VcrHandler.PARAM_REVIEWER);
        verify(dao).create(vcr);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoCreateThrowsDaoSystemExceptionThenErrorDb() throws Exception {
        when(request.getParameter(VcrHandler.PARAM_STUDENT_ID)).thenReturn(studentId);
        when(request.getParameter(VcrHandler.PARAM_NAME)).thenReturn(vcrName);
        when(request.getParameter(VcrHandler.PARAM_HEAD)).thenReturn(null);
        when(request.getParameter(VcrHandler.PARAM_REVIEWER)).thenReturn(null);
        Vcr vcr = new Vcr(1, "", "", "");

        doThrow(DaoSystemException.class).when(dao).create(vcr);

        try {
            handler.doCreate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(VcrHandler.PARAM_STUDENT_ID);
            verify(request).getParameter(VcrHandler.PARAM_NAME);
            verify(request).getParameter(VcrHandler.PARAM_HEAD);
            verify(request).getParameter(VcrHandler.PARAM_REVIEWER);
            verify(dao).create(vcr);

            String expected = VcrHandler.RESPONSE_ERROR_BD;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }

    }

    @Test
    public void whenDoCreateWithEmptyVcrNameThenExceptionAndCorrectResponse() throws Exception {
        when(request.getParameter(VcrHandler.PARAM_STUDENT_ID)).thenReturn(studentId);
        when(request.getParameter(VcrHandler.PARAM_NAME)).thenReturn(null);
        when(request.getParameter(VcrHandler.PARAM_HEAD)).thenReturn(null);
        when(request.getParameter(VcrHandler.PARAM_REVIEWER)).thenReturn(null);

        try {
            handler.doCreate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(VcrHandler.PARAM_STUDENT_ID);
            verify(request).getParameter(VcrHandler.PARAM_NAME);
            verify(request).getParameter(VcrHandler.PARAM_HEAD);
            verify(request).getParameter(VcrHandler.PARAM_REVIEWER);

            String expected = VcrHandler.RESPONSE_ERROR_EMPTY_VCR_NAME;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void whenDoCreateExistingVcrThenException() throws Exception {
        when(request.getParameter(VcrHandler.PARAM_STUDENT_ID)).thenReturn(studentId);
        when(request.getParameter(VcrHandler.PARAM_NAME)).thenReturn(vcrName);
        when(request.getParameter(VcrHandler.PARAM_HEAD)).thenReturn(vcrHead);
        when(request.getParameter(VcrHandler.PARAM_REVIEWER)).thenReturn(vcrReviewer);

        Vcr vcr = new Vcr(Integer.valueOf(studentId), vcrName, vcrHead, vcrReviewer);
        doThrow(new AlreadyExistingException("vcr_exists")).when(dao).create(vcr);

        try {
            handler.doCreate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(VcrHandler.PARAM_STUDENT_ID);
            verify(request).getParameter(VcrHandler.PARAM_NAME);
            verify(request).getParameter(VcrHandler.PARAM_HEAD);
            verify(request).getParameter(VcrHandler.PARAM_REVIEWER);
            verify(dao).create(vcr);

            String expected = VcrHandler.RESPONSE_ERROR_VCR_NAME_ALREADY_EXIST;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void whenDoUpdateFullEntityThenCorrectResponse() throws Exception {
        when(request.getParameter(VcrHandler.PARAM_KEY)).thenReturn(vcrName);
        when(request.getParameter(VcrHandler.PARAM_NAME)).thenReturn(vcrName);
        when(request.getParameter(VcrHandler.PARAM_HEAD)).thenReturn(vcrHead);
        when(request.getParameter(VcrHandler.PARAM_REVIEWER)).thenReturn(vcrReviewer);
        Vcr vcr = new Vcr();
        vcr.setName(vcrName);
        vcr.setHead(vcrHead);
        vcr.setReviewer(vcrReviewer);

        String expected = VcrHandler.RESPONSE_OK;
        String actual = handler.doUpdate(request);

        verify(request).getParameter(VcrHandler.PARAM_KEY);
        verify(request).getParameter(VcrHandler.PARAM_NAME);
        verify(request).getParameter(VcrHandler.PARAM_HEAD);
        verify(request).getParameter(VcrHandler.PARAM_REVIEWER);
        verify(dao).update(vcrName, vcr);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoUpdateWithEmptyParamsThenCorrectResponse() throws Exception {
        when(request.getParameter(VcrHandler.PARAM_KEY)).thenReturn(vcrName);
        when(request.getParameter(VcrHandler.PARAM_NAME)).thenReturn(vcrName);
        when(request.getParameter(VcrHandler.PARAM_HEAD)).thenReturn(null);
        when(request.getParameter(VcrHandler.PARAM_REVIEWER)).thenReturn(null);
        Vcr vcr = new Vcr();
        vcr.setName(vcrName);
        vcr.setHead("");
        vcr.setReviewer("");

        String expected = VcrHandler.RESPONSE_OK;
        String actual = handler.doUpdate(request);

        verify(request).getParameter(VcrHandler.PARAM_KEY);
        verify(request).getParameter(VcrHandler.PARAM_NAME);
        verify(request).getParameter(VcrHandler.PARAM_HEAD);
        verify(request).getParameter(VcrHandler.PARAM_REVIEWER);
        verify(dao).update(vcrName, vcr);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoUpdateThrowsDaoSystemExceptionThenErrorDb() throws Exception {
        when(request.getParameter(VcrHandler.PARAM_KEY)).thenReturn(vcrName);
        when(request.getParameter(VcrHandler.PARAM_NAME)).thenReturn(vcrName);
        when(request.getParameter(VcrHandler.PARAM_HEAD)).thenReturn(null);
        when(request.getParameter(VcrHandler.PARAM_REVIEWER)).thenReturn(null);
        Vcr vcr = new Vcr();
        vcr.setName("");
        vcr.setHead("");
        vcr.setReviewer("");

        doThrow(DaoSystemException.class).when(dao).update(vcrName, vcr);

        try {
            handler.doUpdate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(VcrHandler.PARAM_KEY);
            verify(request).getParameter(VcrHandler.PARAM_NAME);
            verify(request).getParameter(VcrHandler.PARAM_HEAD);
            verify(request).getParameter(VcrHandler.PARAM_REVIEWER);
            verify(dao).update(vcrName, vcr);

            String expected = VcrHandler.RESPONSE_ERROR_BD;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }

    }

    @Test
    public void whenDoUpdateWithEmptyVcrNameThenExceptionAndCorrectResponse() throws Exception {
        when(request.getParameter(VcrHandler.PARAM_STUDENT_ID)).thenReturn(studentId);
        when(request.getParameter(VcrHandler.PARAM_NAME)).thenReturn(null);
        when(request.getParameter(VcrHandler.PARAM_HEAD)).thenReturn(null);
        when(request.getParameter(VcrHandler.PARAM_REVIEWER)).thenReturn(null);

        try {
            handler.doUpdate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(VcrHandler.PARAM_KEY);
            verify(request).getParameter(VcrHandler.PARAM_NAME);
            verify(request).getParameter(VcrHandler.PARAM_HEAD);
            verify(request).getParameter(VcrHandler.PARAM_REVIEWER);

            String expected = VcrHandler.RESPONSE_ERROR_EMPTY_VCR_NAME;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }

    }

    @Test
    public void whenDoUpdateExistingVcrThenException() throws Exception {
        when(request.getParameter(VcrHandler.PARAM_STUDENT_ID)).thenReturn(studentId);
        when(request.getParameter(VcrHandler.PARAM_NAME)).thenReturn(vcrName);
        when(request.getParameter(VcrHandler.PARAM_HEAD)).thenReturn(vcrHead);
        when(request.getParameter(VcrHandler.PARAM_REVIEWER)).thenReturn(vcrReviewer);

        Vcr vcr = new Vcr(Integer.valueOf(studentId), vcrName, vcrHead, vcrReviewer);
        doThrow(AlreadyExistingException.class).when(dao).update(vcrName, vcr);

        try {
            handler.doUpdate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(VcrHandler.PARAM_STUDENT_ID);
            verify(request).getParameter(VcrHandler.PARAM_NAME);
            verify(request).getParameter(VcrHandler.PARAM_HEAD);
            verify(request).getParameter(VcrHandler.PARAM_REVIEWER);

            String expected = VcrHandler.RESPONSE_ERROR_VCR_NAME_ALREADY_EXIST;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void whenDoDeleteSuccessThenCorrectAnswer() throws Exception {
        when(request.getParameter(VcrHandler.PARAM_NAME)).thenReturn(vcrName);

        String expected = VcrHandler.RESPONSE_OK;
        String actual = handler.doDelete(request);

        verify(request).getParameter(VcrHandler.PARAM_NAME);
        verify(dao).delete(vcrName);
        assertEquals(expected, actual);
    }

    @Test
    public void whenDoDeleteThrowsDaoSystemExceptionThenErrorDb() throws Exception {
        when(request.getParameter(VcrHandler.PARAM_NAME)).thenReturn(vcrName);
        doThrow(DaoSystemException.class).when(dao).delete(vcrName);

        try {
            handler.doDelete(request);
        } catch (HandlingException e) {
            String expected = VcrHandler.RESPONSE_ERROR_BD;
            String actual = e.getMessage();

            verify(request).getParameter(VcrHandler.PARAM_NAME);
            verify(dao).delete(vcrName);
            assertEquals(expected, actual);
        }

    }


}






























