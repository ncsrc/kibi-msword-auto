package ru.tstu.msword_auto.dao.impl;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.tstu.msword_auto.dao.exceptions.AlreadyExistingException;
import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;
import ru.tstu.msword_auto.entity.GekMember;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.tstu.msword_auto.dao.impl.AbstractDao.EXCEPTION_ALREADY_EXISTS;


public class GekMemberDaoTest {

    // mocks
    private GekMemberDao dao;
    private Connection connection;
    private PreparedStatement statement;

    // entity content
    private int memberId;
    private int headId;
    private String gekMember;

    // entities
    private GekMember defaultEntity;
    private GekMember additionalEntity;


    @Before
    public void setUp() {
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        dao = new GekMemberDao();
        dao.connection = this.connection;

        memberId = 1;
        headId = 2;
        gekMember = "name";

        defaultEntity = new GekMember(memberId, headId, gekMember);
        additionalEntity = new GekMember(2, headId, gekMember);

    }

    @After
    public void tearDown() throws Exception {
        verify(connection, never()).close();
    }


    @Test
    public void whenCreateFullEntityThenAllRight() throws Exception {
        when(connection.prepareStatement(GekMemberDao.SQL_CREATE)).thenReturn(statement);

        dao.create(defaultEntity);

        verify(connection).prepareStatement(GekMemberDao.SQL_CREATE);
        verify(statement).setInt(1, headId);
        verify(statement).setString(2, gekMember);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test
    public void whenReadByPrimaryKeyExistingRowThenAllRight() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(GekMemberDao.SQL_READ_BY_PK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        adjustResultSet(resultSet, true);

        GekMember entity = dao.read(memberId);

        verifyQuery(GekMemberDao.SQL_READ_BY_PK, resultSet, 1, true);
        verify(resultSet).close();
        verify(statement).close();

        assertEquals(defaultEntity, entity);
    }

    @Test
    public void whenReadAllWithMultipleRowsThenAllRight() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(GekMemberDao.SQL_READ_ALL)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        adjustResultSet(resultSet, false);

        List<GekMember> entities = dao.readAll();

        verifyQuery(GekMemberDao.SQL_READ_ALL, resultSet, 2, true);
        verify(resultSet).close();
        verify(statement).close();

        GekMember actual1 = entities.get(0);
        GekMember actual2 = entities.get(1);
        assertEquals(defaultEntity, actual1);
        assertEquals(additionalEntity, actual2);

    }

    @Test
    public void whenUpdateByPkOneRowThenAllRight() throws Exception {
        when(connection.prepareStatement(GekMemberDao.SQL_UPDATE)).thenReturn(statement);

        dao.update(memberId, additionalEntity);

        verify(connection).prepareStatement(GekMemberDao.SQL_UPDATE);

        verify(statement).setInt(1, headId);
        verify(statement).setString(2, gekMember);
        verify(statement).setInt(3, memberId);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test
    public void whenDeleteByPkThenAllStepsPerformed() throws Exception {
        when(connection.prepareStatement(GekMemberDao.SQL_DELETE_BY_PK)).thenReturn(statement);

        dao.delete(memberId);

        verify(connection).prepareStatement(GekMemberDao.SQL_DELETE_BY_PK);
        verify(statement).setInt(1, memberId);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test
    public void whenDeleteAllThenAllActionsPerformed() throws Exception {
        when(connection.prepareStatement(GekMemberDao.SQL_DELETE_ALL)).thenReturn(statement);

        dao.deleteAll();

        verify(connection).prepareStatement(GekMemberDao.SQL_DELETE_ALL);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test
    public void whenReadByFkOneRowThenAllActionsPerformed() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(GekMemberDao.SQL_READ_BY_FK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        adjustResultSet(resultSet, true);

        GekMember entity = dao.readByForeignKey(headId).get(0);

        verifyQuery(GekMemberDao.SQL_READ_BY_FK, resultSet, 1, false);
        verify(resultSet).close();
        verify(statement).close();

        assertEquals(defaultEntity, entity);

    }

    @Test(expected = NoSuchEntityException.class)
    public void whenReadByPrimaryKeyNonExistingRowThenException() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(GekMemberDao.SQL_READ_BY_PK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        GekMember entity = dao.read(memberId);

        verify(connection).prepareStatement(GekMemberDao.SQL_READ_BY_PK);
        verify(statement).setInt(1, memberId);
        verify(statement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).close();
        verify(statement).close();

        assertNull(entity);
    }

    @Test(expected = NoSuchEntityException.class)
    public void whenReadByForeignKeyNonExistingRowThenException() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(GekMemberDao.SQL_READ_BY_FK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        List<GekMember> entities = dao.readByForeignKey(headId);

        verify(connection).prepareStatement(GekMemberDao.SQL_READ_BY_FK);
        verify(statement).setInt(1, headId);
        verify(statement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).close();
        verify(statement).close();

        assertTrue(entities.isEmpty());
    }

    @Test(expected = DaoSystemException.class)
    public void whenCreateThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(GekMemberDao.SQL_CREATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        dao.create(defaultEntity);
    }

    @Test(expected = DaoSystemException.class)
    public void whenReadByPkThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(GekMemberDao.SQL_READ_BY_PK)).thenReturn(statement);
        when(statement.executeQuery()).thenThrow(SQLException.class);

        dao.read(memberId);
    }

    @Test(expected = DaoSystemException.class)
    public void whenReadByFkThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(GekMemberDao.SQL_READ_BY_FK)).thenReturn(statement);
        when(statement.executeQuery()).thenThrow(SQLException.class);

        dao.readByForeignKey(headId);
    }

    @Test(expected = DaoSystemException.class)
    public void whenReadAllThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(GekMemberDao.SQL_READ_ALL)).thenReturn(statement);
        when(statement.executeQuery()).thenThrow(SQLException.class);

        dao.readAll();
    }

    @Test(expected = DaoSystemException.class)
    public void whenUpdateThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(GekMemberDao.SQL_UPDATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        dao.update(memberId, defaultEntity);
    }

    @Test(expected = DaoSystemException.class)
    public void whenDeleteByPkThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(GekMemberDao.SQL_DELETE_BY_PK)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        dao.delete(memberId);
    }

    @Test(expected = DaoSystemException.class)
    public void whenDeleteAllThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(GekMemberDao.SQL_DELETE_ALL)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        dao.deleteAll();
    }

    @Test
    public void whenReadByPkStatementFailsThenAllResourcesClosed() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(GekMemberDao.SQL_READ_BY_PK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenThrow(SQLException.class);

        try {
            dao.read(memberId);
        } catch (DaoSystemException e) {}

        verify(resultSet).close();
        verify(statement).close();
    }

    @Test
    public void whenReadAllFailsThenAllResourcesClosed() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(GekMemberDao.SQL_READ_ALL)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenThrow(SQLException.class);

        try {
            dao.readAll();
        } catch (DaoSystemException e) {}

        verify(resultSet).close();
        verify(statement).close();
    }

    @Test
    public void whenReadByFkStatementFailsThenAllResourcesClosed() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(GekMemberDao.SQL_READ_BY_FK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenThrow(SQLException.class);

        try {
            dao.readByForeignKey(memberId);
        } catch (DaoSystemException e) {}

        verify(resultSet).close();
        verify(statement).close();
    }

    @Test
    public void whenCreateStatementFailsThenAllResourcesClosed() throws Exception {
        when(connection.prepareStatement(GekMemberDao.SQL_CREATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        try {
            dao.create(defaultEntity);
        } catch (DaoSystemException e) {}

        verify(statement).close();
    }

    @Test
    public void whenUpdateStatementFailsThenAllResourcesClosed() throws Exception {
        when(connection.prepareStatement(GekMemberDao.SQL_UPDATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        try {
            dao.update(memberId, defaultEntity);
        } catch (DaoSystemException e) {}

        verify(statement).close();
    }

    @Test
    public void whenDeleteByPkStatementFailsThenAllResourcesClosed() throws Exception {
        when(connection.prepareStatement(GekMemberDao.SQL_DELETE_BY_PK)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        try {
            dao.delete(memberId);
        } catch (DaoSystemException e) {}

        verify(statement).close();
    }

    @Test
    public void whenDeleteAllFailsThenAllResourcesClosed() throws Exception {
        when(connection.prepareStatement(GekMemberDao.SQL_DELETE_ALL)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        try {
            dao.deleteAll();
        } catch (DaoSystemException e) {}

        verify(statement).close();
    }

    @Test(expected = AlreadyExistingException.class)
    public void whenCreateAlreadyExistingThenException() throws Exception {
        when(connection.prepareStatement(GekMemberDao.SQL_CREATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(new SQLException(EXCEPTION_ALREADY_EXISTS));
        dao.create(defaultEntity);

        verify(connection).prepareStatement(GekMemberDao.SQL_CREATE);
        verify(statement).setInt(1, headId);
        verify(statement).setString(2, gekMember);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test(expected = AlreadyExistingException.class)
    public void whenUpdateAlreadyExistingThenException() throws Exception {
        when(connection.prepareStatement(GekMemberDao.SQL_UPDATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(new SQLException(EXCEPTION_ALREADY_EXISTS));
        dao.update(memberId, defaultEntity);

        verify(connection).prepareStatement(GekMemberDao.SQL_UPDATE);
        verify(statement).setInt(1, headId);
        verify(statement).setString(2, gekMember);
        verify(statement).setInt(3, memberId);
        verify(statement).executeUpdate();
        verify(statement).close();

    }


    private void adjustResultSet(ResultSet resultSet, boolean oneTime) throws Exception {

        if(oneTime) {
            when(resultSet.next())
                    .thenReturn(true)
                    .thenReturn(false);

            when(resultSet.getInt(GekMemberDao.TABLE_MEMBER_ID)).thenReturn(memberId);
        } else {    // pk many(at least 2)
            when(resultSet.next())
                    .thenReturn(true)
                    .thenReturn(true)
                    .thenReturn(false);

            when(resultSet.getInt(GekMemberDao.TABLE_MEMBER_ID))
                    .thenReturn(memberId)
                    .thenReturn(2); // second id, second entity
        }

        when(resultSet.getInt(GekMemberDao.TABLE_HEAD_ID)).thenReturn(headId);
        when(resultSet.getString(GekMemberDao.TABLE_GEK_MEMBER)).thenReturn(gekMember);

    }

    private void verifyQuery(String sql, ResultSet resultSet, int times, boolean byPrimaryKey) throws Exception {
        verify(connection).prepareStatement(sql);

        // if by pk
        if(times == 1 && byPrimaryKey) {
            verify(statement).setInt(1, memberId);
        } else if(times == 1 && !byPrimaryKey) {
            verify(statement).setInt(1, headId);
        }
        verify(statement).executeQuery();

        int resultSetNextTimes = times + 1; // because there would be last, that returns false
        verify(resultSet, times(resultSetNextTimes)).next();
        verify(resultSet, times(times)).getInt(GekMemberDao.TABLE_MEMBER_ID);
        verify(resultSet, times(times)).getInt(GekMemberDao.TABLE_HEAD_ID);
        verify(resultSet, times(times)).getString(GekMemberDao.TABLE_GEK_MEMBER);

    }






}























