package ru.tstu.msword_auto.webapp.servlets;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import ru.tstu.msword_auto.dao.exceptions.AlreadyExistingException;
import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;
import ru.tstu.msword_auto.dao.impl.GekMemberDao;
import ru.tstu.msword_auto.entity.GekMember;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;


public class GekMembersHandlerTest {
    private int memberId;
    private int headId;
    private String gekMember;
    private GekMembersHandler handler;
    private HttpServletRequest request;
    private GekMemberDao dao;
    private Gson gson;


    @Before
    public void setUp() throws Exception {
        memberId = 0;
        headId = 1;
        gekMember = "member";

        handler = new GekMembersHandler();
        request = mock(HttpServletRequest.class);
        dao = mock(GekMemberDao.class);
        handler.dao = dao;
        gson = new Gson();
    }


    @Test
    public void whenDoListGetsValidEntitiesThenCorrectResponse() throws Exception {
        when(request.getParameter(GekMembersHandler.PARAM_HEAD_ID)).thenReturn(String.valueOf(headId));
        List<GekMember> geks = Arrays.asList(
                new GekMember(memberId, headId, gekMember),
                new GekMember(headId, gekMember)
        );
        when(dao.readByForeignKey(headId)).thenReturn(geks);

        String expected = gson.toJson(geks);
        String actual = handler.doList(request);

        verify(request).getParameter(GekMembersHandler.PARAM_HEAD_ID);
        verify(dao).readByForeignKey(headId);
        assertEquals(expected, actual);
    }

    @Test
    public void whenDoListGetsNoSuchEntityThenEmptyResponse() throws Exception {
        when(request.getParameter(GekMembersHandler.PARAM_HEAD_ID)).thenReturn(String.valueOf(headId));
        when(dao.readByForeignKey(headId)).thenThrow(NoSuchEntityException.class);

        String expected = gson.toJson(Collections.EMPTY_LIST);
        String actual = handler.doList(request);

        verify(request).getParameter(GekMembersHandler.PARAM_HEAD_ID);
        verify(dao).readByForeignKey(headId);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoListGetsDaoSystemExceptionThenErrorDb() throws Exception {
        when(request.getParameter(GekMembersHandler.PARAM_HEAD_ID)).thenReturn(String.valueOf(headId));
        when(dao.readByForeignKey(headId)).thenThrow(DaoSystemException.class);

        String expected = GekHeadHandler.RESPONSE_ERROR_BD;
        try {
            handler.doList(request);
        } catch (HandlingException e) {
            verify(dao).readByForeignKey(headId);
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void whenDoCreateFullEntityThenCorrectResponse() throws Exception {
        when(request.getParameter(GekMembersHandler.PARAM_HEAD_ID)).thenReturn(String.valueOf(headId));
        when(request.getParameter(GekMembersHandler.PARAM_MEMBER)).thenReturn(gekMember);
        GekMember gek = new GekMember(headId, gekMember);

        String expected = gson.toJson(gek);
        String actual = handler.doCreate(request);

        verify(request).getParameter(GekMembersHandler.PARAM_HEAD_ID);
        verify(request).getParameter(GekMembersHandler.PARAM_MEMBER);
        verify(dao).create(gek);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoCreateWithEmptyParamsThenCorrectResponse() throws Exception {
        when(request.getParameter(GekMembersHandler.PARAM_HEAD_ID)).thenReturn(String.valueOf(headId));
        when(request.getParameter(GekMembersHandler.PARAM_MEMBER)).thenReturn(null);
        GekMember gek = new GekMember(headId, "");

        String expected = gson.toJson(gek);
        String actual = handler.doCreate(request);

        verify(request).getParameter(GekMembersHandler.PARAM_HEAD_ID);
        verify(request).getParameter(GekMembersHandler.PARAM_MEMBER);
        verify(dao).create(gek);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoCreateThrowsDaoSystemExceptionThenErrorDb() throws Exception {
        when(request.getParameter(GekMembersHandler.PARAM_HEAD_ID)).thenReturn(String.valueOf(headId));
        when(request.getParameter(GekMembersHandler.PARAM_MEMBER)).thenReturn(null);
        GekMember gek = new GekMember();
        gek.setGekMemberId(memberId);
        gek.setGekHeadId(headId);

        doThrow(DaoSystemException.class).when(dao).create(gek);

        try {
            handler.doCreate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(GekMembersHandler.PARAM_HEAD_ID);
            verify(request).getParameter(GekMembersHandler.PARAM_MEMBER);
            verify(dao).create(gek);

            String expected = GekMembersHandler.RESPONSE_ERROR_BD;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }

    }

    @Test
    public void whenDoCreateExistingCourseNameThenException() throws Exception {
        when(request.getParameter(GekMembersHandler.PARAM_HEAD_ID)).thenReturn(String.valueOf(headId));
        when(request.getParameter(GekMembersHandler.PARAM_MEMBER)).thenReturn(gekMember);
        GekMember gek = new GekMember(headId, gekMember);

        doThrow(AlreadyExistingException.class).when(dao).create(gek);

        try {
            handler.doCreate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(GekMembersHandler.PARAM_HEAD_ID);
            verify(request).getParameter(GekMembersHandler.PARAM_MEMBER);
            verify(dao).create(gek);

            String expected = GekMembersHandler.RESPONSE_ERROR_MEMBER_ALREADY_EXISTS;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void whenDoUpdateFullEntityThenCorrectResponse() throws Exception {
        when(request.getParameter(GekMembersHandler.PARAM_MEMBER_ID)).thenReturn(String.valueOf(memberId));
        when(request.getParameter(GekMembersHandler.PARAM_HEAD_ID)).thenReturn(String.valueOf(headId));
        when(request.getParameter(GekMembersHandler.PARAM_MEMBER)).thenReturn(gekMember);
        GekMember gek = new GekMember(headId, gekMember);

        String expected = GekMembersHandler.RESPONSE_OK;
        String actual = handler.doUpdate(request);

        verify(request).getParameter(GekMembersHandler.PARAM_MEMBER_ID);
        verify(request).getParameter(GekMembersHandler.PARAM_HEAD_ID);
        verify(request).getParameter(GekMembersHandler.PARAM_MEMBER);
        verify(dao).update(memberId, gek);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoUpdateWithEmptyParamsThenCorrectResponse() throws Exception {
        when(request.getParameter(GekMembersHandler.PARAM_MEMBER_ID)).thenReturn(String.valueOf(memberId));
        when(request.getParameter(GekMembersHandler.PARAM_HEAD_ID)).thenReturn(String.valueOf(headId));
        when(request.getParameter(GekMembersHandler.PARAM_MEMBER)).thenReturn(null);
        GekMember gek = new GekMember();
        gek.setGekHeadId(headId);

        String expected = GekMembersHandler.RESPONSE_OK;
        String actual = handler.doUpdate(request);

        verify(request).getParameter(GekMembersHandler.PARAM_MEMBER_ID);
        verify(request).getParameter(GekMembersHandler.PARAM_HEAD_ID);
        verify(request).getParameter(GekMembersHandler.PARAM_MEMBER);
        verify(dao).update(memberId, gek);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDoUpdateThrowsDaoSystemExceptionThenErrorDb() throws Exception {
        when(request.getParameter(GekMembersHandler.PARAM_MEMBER_ID)).thenReturn(String.valueOf(memberId));
        when(request.getParameter(GekMembersHandler.PARAM_HEAD_ID)).thenReturn(String.valueOf(headId));
        when(request.getParameter(GekMembersHandler.PARAM_MEMBER)).thenReturn(null);
        GekMember gek = new GekMember();
        gek.setGekHeadId(headId);

        doThrow(DaoSystemException.class).when(dao).update(memberId, gek);

        try {
            handler.doUpdate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(GekMembersHandler.PARAM_MEMBER_ID);
            verify(request).getParameter(GekMembersHandler.PARAM_HEAD_ID);
            verify(request).getParameter(GekMembersHandler.PARAM_MEMBER);
            verify(dao).update(memberId, gek);

            String expected = GekMembersHandler.RESPONSE_ERROR_BD;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }

    }

    @Test
    public void whenDoUpdateExistingCourseNameThenException() throws Exception {
        when(request.getParameter(GekMembersHandler.PARAM_MEMBER_ID)).thenReturn(String.valueOf(memberId));
        when(request.getParameter(GekMembersHandler.PARAM_HEAD_ID)).thenReturn(String.valueOf(headId));
        when(request.getParameter(GekMembersHandler.PARAM_MEMBER)).thenReturn(gekMember);
        GekMember gek = new GekMember(headId, gekMember);
        doThrow(AlreadyExistingException.class).when(dao).update(memberId, gek);

        try {
            handler.doUpdate(request);
        } catch (HandlingException e) {
            verify(request).getParameter(GekMembersHandler.PARAM_MEMBER_ID);
            verify(request).getParameter(GekMembersHandler.PARAM_HEAD_ID);
            verify(request).getParameter(GekMembersHandler.PARAM_MEMBER);
            verify(dao).update(memberId, gek);

            String expected = GekMembersHandler.RESPONSE_ERROR_MEMBER_ALREADY_EXISTS;
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void whenDoDeleteSuccessThenCorrectAnswer() throws Exception {
        when(request.getParameter(GekMembersHandler.PARAM_MEMBER_ID)).thenReturn(String.valueOf(memberId));

        String expected = GekMembersHandler.RESPONSE_OK;
        String actual = handler.doDelete(request);

        verify(request).getParameter(GekMembersHandler.PARAM_MEMBER_ID);
        verify(dao).delete(memberId);
        assertEquals(expected, actual);
    }

    @Test
    public void whenDoDeleteThrowsDaoSystemExceptionThenErrorDb() throws Exception {
        when(request.getParameter(GekMembersHandler.PARAM_MEMBER_ID)).thenReturn(String.valueOf(memberId));
        doThrow(DaoSystemException.class).when(dao).delete(memberId);

        try {
            handler.doDelete(request);
        } catch (HandlingException e) {
            String expected = GekMembersHandler.RESPONSE_ERROR_BD;
            String actual = e.getMessage();

            verify(request).getParameter(GekMembersHandler.PARAM_MEMBER_ID);
            verify(dao).delete(memberId);
            assertEquals(expected, actual);
        }

    }




}