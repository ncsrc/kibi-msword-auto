package ru.tstu.msword_auto.webapp.servlets;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import ru.tstu.msword_auto.dao.exceptions.AlreadyExistingException;
import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.impl.DateDao;
import ru.tstu.msword_auto.entity.Date;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;


public class DateHandlerTest {
    private int dateId;
    private int groupId;
    private String groupName;
    private String gosDate;
    private String vcrDate;
    private DateHandler handler;
    private HttpServletRequest request;
    private DateDao dao;
    private Gson gson;


    @Before
    public void setUp() throws Exception {
        dateId = 1;
        groupId = 1;
        groupName = "group";
        gosDate = "2011-01-01";
        vcrDate = "2012-01-01";

        handler = new DateHandler();
        request = mock(HttpServletRequest.class);
        dao = mock(DateDao.class);
        handler.dao = dao;
        gson = new Gson();
    }


    @Test
    public void whenDoListGetsValidEntitiesThenCorrectResponse() throws Exception {
        List<Date> dates = Arrays.asList(
                new Date(dateId, groupId, groupName, gosDate, vcrDate),
                new Date(groupName)
        );
        when(dao.readAll()).thenReturn(dates);

        String expected = gson.toJson(dates);
        String actual = handler.doList(request);

        verify(dao).readAll();
        assertEquals(expected, actual);
    }

    @Test
    public void whenDoListGetsDaoSystemExceptionThenErrorDb() throws Exception {
        when(dao.readAll()).thenThrow(DaoSystemException.class);

        String expected = CourseHandler.RESPONSE_ERROR_BD;
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
        when(request.getParameter(DateHandler.PARAM_GROUP)).thenReturn(groupName);
        when(request.getParameter(DateHandler.PARAM_DATE_GOS)).thenReturn(gosDate);
        when(request.getParameter(DateHandler.PARAM_DATE_VCR)).thenReturn(vcrDate);
        Date date = new Date();
        date.setSubgroupId(groupId);
        date.setGroupName(groupName);
        date.setGosDate(gosDate);
        date.setVcrDate(vcrDate);

        String expected = gson.toJson(date);
        String actual = handler.doCreate(request);

        verify(request).getParameter(DateHandler.PARAM_GROUP);
        verify(request).getParameter(DateHandler.PARAM_DATE_GOS);
        verify(request).getParameter(DateHandler.PARAM_DATE_VCR);
        verify(dao).readByGroupName(groupName);
        verify(dao).create(date);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoCreateWithEmptyParamsThenCorrectResponse() throws Exception {
        when(request.getParameter(DateHandler.PARAM_GROUP_ID)).thenReturn(String.valueOf(groupId));
        when(request.getParameter(DateHandler.PARAM_GROUP)).thenReturn(groupName);
        when(request.getParameter(DateHandler.PARAM_DATE_GOS)).thenReturn(null);
        when(request.getParameter(DateHandler.PARAM_DATE_VCR)).thenReturn(null);
        Date date = new Date(groupName);
        date.setSubgroupId(groupId);

        String expected = gson.toJson(date);
        String actual = handler.doCreate(request);

        verify(request).getParameter(DateHandler.PARAM_GROUP);
        verify(request).getParameter(DateHandler.PARAM_DATE_GOS);
        verify(request).getParameter(DateHandler.PARAM_DATE_VCR);
        verify(dao).readByGroupName(groupName);
        verify(dao).create(date);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoCreateThrowsDaoSystemExceptionThenErrorDb() throws Exception {
        when(request.getParameter(DateHandler.PARAM_GROUP)).thenReturn(groupName);
        when(request.getParameter(DateHandler.PARAM_DATE_GOS)).thenReturn(null);
        when(request.getParameter(DateHandler.PARAM_DATE_VCR)).thenReturn(null);
        Date date = new Date(groupName);

        doThrow(DaoSystemException.class).when(dao).create(date);

        try {
            handler.doCreate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(DateHandler.PARAM_GROUP);
            verify(request).getParameter(DateHandler.PARAM_DATE_GOS);
            verify(request).getParameter(DateHandler.PARAM_DATE_VCR);
            verify(dao).create(date);

            String expected = DateHandler.RESPONSE_ERROR_BD;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }

    }

    @Test
    public void whenDoCreateWithEmptyGroupThenExceptionAndCorrectResponse() throws Exception {
        when(request.getParameter(DateHandler.PARAM_GROUP)).thenReturn(null);
        when(request.getParameter(DateHandler.PARAM_DATE_GOS)).thenReturn(null);
        when(request.getParameter(DateHandler.PARAM_DATE_VCR)).thenReturn(null);

        try {
            handler.doCreate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(DateHandler.PARAM_GROUP);
            verify(request).getParameter(DateHandler.PARAM_DATE_GOS);
            verify(request).getParameter(DateHandler.PARAM_DATE_VCR);

            String expected = DateHandler.RESPONSE_ERROR_EMPTY_GROUP_NAME;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void whenDoCreateExistingDateThenException() throws Exception {
        when(request.getParameter(DateHandler.PARAM_GROUP)).thenReturn(groupName);
        when(request.getParameter(DateHandler.PARAM_DATE_GOS)).thenReturn(gosDate);
        when(request.getParameter(DateHandler.PARAM_DATE_VCR)).thenReturn(vcrDate);
        Date date = new Date();
        date.setGroupName(groupName);
        date.setGosDate(gosDate);
        date.setVcrDate(vcrDate);

        doThrow(AlreadyExistingException.class).when(dao).create(date);

        try {
            handler.doCreate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(DateHandler.PARAM_GROUP);
            verify(request).getParameter(DateHandler.PARAM_DATE_GOS);
            verify(request).getParameter(DateHandler.PARAM_DATE_VCR);
            verify(dao).create(date);

            String expected = DateHandler.RESPONSE_ERROR_GROUP_NAME_ALREADY_EXISTS;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }
    }

    // TODO test with different group name
    @Test
    public void whenDoUpdateFullEntityThenCorrectResponse() throws Exception {
        when(request.getParameter(DateHandler.PARAM_DATE_ID)).thenReturn(String.valueOf(dateId));
        when(request.getParameter(DateHandler.PARAM_GROUP)).thenReturn(groupName);
        when(request.getParameter(DateHandler.PARAM_DATE_GOS)).thenReturn(gosDate);
        when(request.getParameter(DateHandler.PARAM_DATE_VCR)).thenReturn(vcrDate);
        Date date = new Date();
        date.setGroupName(groupName);
        date.setGosDate(gosDate);
        date.setVcrDate(vcrDate);

        when(dao.read(dateId)).thenReturn(date);

        String expected = DateHandler.RESPONSE_OK;
        String actual = handler.doUpdate(request);

        verify(request).getParameter(DateHandler.PARAM_DATE_ID);
        verify(request).getParameter(DateHandler.PARAM_GROUP);
        verify(request).getParameter(DateHandler.PARAM_DATE_GOS);
        verify(request).getParameter(DateHandler.PARAM_DATE_VCR);
        verify(dao).update(dateId, date);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoUpdateWithEmptyParamsThenCorrectResponse() throws Exception {
        when(request.getParameter(DateHandler.PARAM_DATE_ID)).thenReturn(String.valueOf(dateId));
        when(request.getParameter(DateHandler.PARAM_GROUP)).thenReturn(groupName);
        when(request.getParameter(DateHandler.PARAM_DATE_GOS)).thenReturn(null);
        when(request.getParameter(DateHandler.PARAM_DATE_VCR)).thenReturn(null);
        Date date = new Date(groupName);

        when(dao.read(dateId)).thenReturn(date);
        String expected = DateHandler.RESPONSE_OK;
        String actual = handler.doUpdate(request);

        verify(request).getParameter(DateHandler.PARAM_DATE_ID);
        verify(request).getParameter(DateHandler.PARAM_GROUP);
        verify(request).getParameter(DateHandler.PARAM_DATE_GOS);
        verify(request).getParameter(DateHandler.PARAM_DATE_VCR);
        verify(dao).update(dateId, date);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoUpdateThrowsDaoSystemExceptionThenErrorDb() throws Exception {
        when(request.getParameter(DateHandler.PARAM_DATE_ID)).thenReturn(String.valueOf(dateId));
        when(request.getParameter(DateHandler.PARAM_GROUP)).thenReturn(groupName);
        when(request.getParameter(DateHandler.PARAM_DATE_GOS)).thenReturn(null);
        when(request.getParameter(DateHandler.PARAM_DATE_VCR)).thenReturn(null);
        Date date = new Date();
        date.setDateId(dateId);
        date.setSubgroupId(groupId);
        date.setGroupName(groupName);
        date.setGosDate(gosDate);
        date.setVcrDate(vcrDate);

        when(dao.read(dateId)).thenReturn(date);
        doThrow(DaoSystemException.class).when(dao).update(dateId, date);

        try {
            handler.doUpdate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(DateHandler.PARAM_DATE_ID);
            verify(request).getParameter(DateHandler.PARAM_GROUP);
            verify(request).getParameter(DateHandler.PARAM_DATE_GOS);
            verify(request).getParameter(DateHandler.PARAM_DATE_VCR);
            verify(dao).update(dateId, date);

            String expected = DateHandler.RESPONSE_ERROR_BD;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }

    }

    @Test
    public void whenDoDeleteSuccessThenCorrectAnswer() throws Exception {
        when(request.getParameter(DateHandler.PARAM_DATE_ID)).thenReturn(String.valueOf(dateId));

        String expected = DateHandler.RESPONSE_OK;
        String actual = handler.doDelete(request);

        verify(request).getParameter(DateHandler.PARAM_DATE_ID);
        verify(dao).delete(dateId);
        assertEquals(expected, actual);
    }

    @Test
    public void whenDoDeleteThrowsDaoSystemExceptionThenErrorDb() throws Exception {
        when(request.getParameter(DateHandler.PARAM_DATE_ID)).thenReturn(String.valueOf(dateId));
        doThrow(DaoSystemException.class).when(dao).delete(dateId);

        try {
            handler.doDelete(request);
        } catch (HandlingException e) {
            String expected = DateHandler.RESPONSE_ERROR_BD;
            String actual = e.getMessage();

            verify(request).getParameter(DateHandler.PARAM_DATE_ID);
            verify(dao).delete(dateId);
            assertEquals(expected, actual);
        }

    }




}