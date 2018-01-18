package ru.tstu.msword_auto.dao.impl;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.tstu.msword_auto.dao.exceptions.AlreadyExistingException;
import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;
import ru.tstu.msword_auto.entity.GekHead;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.tstu.msword_auto.dao.impl.AbstractDao.EXCEPTION_ALREADY_EXISTS;


public class GekHeadDaoTest {

    // mocks
    private GekHeadDao dao;
    private Connection connection;
    private PreparedStatement statement;

    // entity content
    private int gekId;
    private String courseName;
    private String gekHead;
    private String gekSubhead;
    private String gekSecretary;

    // entities
    private GekHead defaultEntity;
    private GekHead additionalEntity;


    @Before
    public void setUp() {
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        dao = new GekHeadDao();
        dao.connection = this.connection;

        gekId = 1;
        courseName = "course";
        gekHead = "head";
        gekSubhead = "subhead";
        gekSecretary = "secretary";

        defaultEntity = new GekHead(courseName, gekHead, gekSubhead, gekSecretary);
        defaultEntity.setGekId(gekId);

        additionalEntity = new GekHead(courseName, gekHead, gekSubhead, gekSecretary);
        additionalEntity.setGekId(2);
    }

    @After
    public void tearDown() throws Exception {
        verify(connection, never()).close();
    }


    @Test
    public void whenCreateFullEntityThenAllRight() throws Exception {
        when(connection.prepareStatement(GekHeadDao.SQL_CREATE, Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);

        dao.create(defaultEntity);

        verify(connection).prepareStatement(GekHeadDao.SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
        verify(statement).setString(1, courseName);
        verify(statement).setString(2, gekHead);
        verify(statement).setString(3, gekSubhead);
        verify(statement).setString(4, gekSecretary);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test
    public void whenReadByPrimaryKeyExistingRowThenAllRight() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(GekHeadDao.SQL_READ_BY_PK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        adjustResultSet(resultSet, true);

        GekHead entity = dao.read(gekId);

        verifyQuery(GekHeadDao.SQL_READ_BY_PK, resultSet, 1, true);
        verify(resultSet).close();
        verify(statement).close();

        assertEquals(defaultEntity, entity);
    }

    @Test
    public void whenReadByCourseNameExistingRowThenAllRight() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(GekHeadDao.SQL_READ_BY_COURSENAME)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        adjustResultSet(resultSet, true);

        GekHead entity = dao.readByCourseName(courseName);

        verifyQuery(GekHeadDao.SQL_READ_BY_COURSENAME, resultSet, 1, false);
        verify(resultSet).close();
        verify(statement).close();

        assertEquals(defaultEntity, entity);

    }

    @Test
    public void whenReadAllWithMultipleRowsThenAllRight() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(GekHeadDao.SQL_READ_ALL)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        adjustResultSet(resultSet, false);

        List<GekHead> entities = dao.readAll();

        verifyQuery(GekHeadDao.SQL_READ_ALL, resultSet, 2, true);
        verify(resultSet).close();
        verify(statement).close();

        GekHead actual1 = entities.get(0);
        GekHead actual2 = entities.get(1);
        assertEquals(defaultEntity, actual1);
        assertEquals(additionalEntity, actual2);

    }

    @Test
    public void whenUpdateByPkOneRowThenAllRight() throws Exception {
        when(connection.prepareStatement(GekHeadDao.SQL_UPDATE)).thenReturn(statement);

        dao.update(gekId, additionalEntity);

        verify(connection).prepareStatement(GekHeadDao.SQL_UPDATE);

        verify(statement).setString(1, courseName);
        verify(statement).setString(2, gekHead);
        verify(statement).setString(3, gekSubhead);
        verify(statement).setString(4, gekSecretary);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test
    public void whenDeleteByPkThenAllStepsPerformed() throws Exception {
        when(connection.prepareStatement(GekHeadDao.SQL_DELETE_BY_PK)).thenReturn(statement);

        dao.delete(gekId);

        verify(connection).prepareStatement(GekHeadDao.SQL_DELETE_BY_PK);
        verify(statement).setInt(1, gekId);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test
    public void whenDeleteAllThenAllActionsPerformed() throws Exception {
        when(connection.prepareStatement(GekHeadDao.SQL_DELETE_ALL)).thenReturn(statement);

        dao.deleteAll();

        verify(connection).prepareStatement(GekHeadDao.SQL_DELETE_ALL);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test(expected = NoSuchEntityException.class)
    public void whenReadByPrimaryKeyNonExistingRowThenException() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(GekHeadDao.SQL_READ_BY_PK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        GekHead entity = dao.read(gekId);

        verify(connection).prepareStatement(GekHeadDao.SQL_READ_BY_PK);
        verify(statement).setInt(1, gekId);
        verify(statement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).close();
        verify(statement).close();

        assertNull(entity);
    }

    @Test(expected = NoSuchEntityException.class)
    public void whenReadByCourseNameNonExistingRowThenException() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(GekHeadDao.SQL_READ_BY_COURSENAME)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        GekHead entity = dao.readByCourseName(courseName);

        verify(connection).prepareStatement(GekHeadDao.SQL_READ_BY_COURSENAME);
        verify(statement).setInt(1, gekId);
        verify(statement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).close();
        verify(statement).close();

        assertNull(entity);
    }

    @Test(expected = DaoSystemException.class)
    public void whenCreateThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(GekHeadDao.SQL_CREATE, Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        dao.create(defaultEntity);
    }

    @Test(expected = DaoSystemException.class)
    public void whenReadByPkThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(GekHeadDao.SQL_READ_BY_PK)).thenReturn(statement);
        when(statement.executeQuery()).thenThrow(SQLException.class);

        dao.read(gekId);
    }

    @Test(expected = DaoSystemException.class)
    public void whenReadByCourseNameThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(GekHeadDao.SQL_READ_BY_COURSENAME)).thenReturn(statement);
        when(statement.executeQuery()).thenThrow(SQLException.class);

        dao.readByCourseName(courseName);
    }

    @Test(expected = DaoSystemException.class)
    public void whenReadAllThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(GekHeadDao.SQL_READ_ALL)).thenReturn(statement);
        when(statement.executeQuery()).thenThrow(SQLException.class);

        dao.readAll();
    }

    @Test(expected = DaoSystemException.class)
    public void whenUpdateThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(GekHeadDao.SQL_UPDATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        dao.update(gekId, defaultEntity);
    }

    @Test(expected = DaoSystemException.class)
    public void whenDeleteByPkThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(GekHeadDao.SQL_DELETE_BY_PK)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        dao.delete(gekId);
    }

    @Test(expected = DaoSystemException.class)
    public void whenDeleteAllThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(GekHeadDao.SQL_DELETE_ALL)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        dao.deleteAll();
    }

    @Test
    public void whenReadByPkStatementFailsThenAllResourcesClosed() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(GekHeadDao.SQL_READ_BY_PK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenThrow(SQLException.class);

        try {
            dao.read(gekId);
        } catch (DaoSystemException e) {}

        verify(resultSet).close();
        verify(statement).close();
    }

    @Test
    public void whenReadAllFailsThenAllResourcesClosed() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(GekHeadDao.SQL_READ_ALL)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenThrow(SQLException.class);

        try {
            dao.readAll();
        } catch (DaoSystemException e) {}

        verify(resultSet).close();
        verify(statement).close();
    }

    @Test
    public void whenCreateStatementFailsThenAllResourcesClosed() throws Exception {
        when(connection.prepareStatement(GekHeadDao.SQL_CREATE, Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        try {
            dao.create(defaultEntity);
        } catch (DaoSystemException e) {}

        verify(statement).close();
    }

    @Test
    public void whenUpdateStatementFailsThenAllResourcesClosed() throws Exception {
        when(connection.prepareStatement(GekHeadDao.SQL_UPDATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        try {
            dao.update(gekId, defaultEntity);
        } catch (DaoSystemException e) {}

        verify(statement).close();
    }

    @Test
    public void whenDeleteByPkStatementFailsThenAllResourcesClosed() throws Exception {
        when(connection.prepareStatement(GekHeadDao.SQL_DELETE_BY_PK)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        try {
            dao.delete(gekId);
        } catch (DaoSystemException e) {}

        verify(statement).close();
    }

    @Test
    public void whenDeleteAllFailsThenAllResourcesClosed() throws Exception {
        when(connection.prepareStatement(GekHeadDao.SQL_DELETE_ALL)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        try {
            dao.deleteAll();
        } catch (DaoSystemException e) {}

        verify(statement).close();
    }

    @Test(expected = AlreadyExistingException.class)
    public void whenCreateAlreadyExistingThenException() throws Exception {
        when(connection.prepareStatement(GekHeadDao.SQL_CREATE, Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(new SQLException(EXCEPTION_ALREADY_EXISTS));
        dao.create(defaultEntity);

        verify(connection).prepareStatement(GekHeadDao.SQL_CREATE);
        verify(statement).setString(1, courseName);
        verify(statement).setString(2, gekHead);
        verify(statement).setString(3, gekSubhead);
        verify(statement).setString(4, gekSecretary);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test(expected = AlreadyExistingException.class)
    public void whenUpdateAlreadyExistingThenException() throws Exception {
        when(connection.prepareStatement(GekHeadDao.SQL_UPDATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(new SQLException(EXCEPTION_ALREADY_EXISTS));
        dao.update(gekId, defaultEntity);

        verify(connection).prepareStatement(GekHeadDao.SQL_UPDATE);
        verify(statement).setString(1, courseName);
        verify(statement).setString(2, gekHead);
        verify(statement).setString(3, gekSubhead);
        verify(statement).setString(4, gekSecretary);
        verify(statement).executeUpdate();
        verify(statement).close();

    }


    private void adjustResultSet(ResultSet resultSet, boolean oneTime) throws Exception {

        if(oneTime) {
            when(resultSet.next())
                    .thenReturn(true)
                    .thenReturn(false);

            when(resultSet.getInt(GekHeadDao.TABLE_GEK_ID)).thenReturn(gekId);
        } else {    // pk many(at least 2)
            when(resultSet.next())
                    .thenReturn(true)
                    .thenReturn(true)
                    .thenReturn(false);

            when(resultSet.getInt(GekHeadDao.TABLE_GEK_ID))
                    .thenReturn(gekId)
                    .thenReturn(additionalEntity.getGekId()); // second id, second entity
        }

        when(resultSet.getString(GekHeadDao.TABLE_COURSE_NAME)).thenReturn(courseName);
        when(resultSet.getString(GekHeadDao.TABLE_GEK_HEAD)).thenReturn(gekHead);
        when(resultSet.getString(GekHeadDao.TABLE_GEK_SUBHEAD)).thenReturn(gekSubhead);
        when(resultSet.getString(GekHeadDao.TABLE_GEK_SECRETARY)).thenReturn(gekSecretary);

    }

    private void verifyQuery(String sql, ResultSet resultSet, int times, boolean byPrimaryKey) throws Exception {
        verify(connection).prepareStatement(sql);

        // if by pk
        if(times == 1 && byPrimaryKey) {
            verify(statement).setInt(1, gekId);
        } else if(times == 1 && !byPrimaryKey) {
            verify(statement).setString(1, courseName);
        }
        verify(statement).executeQuery();

        int resultSetNextTimes = times + 1; // because there would be last, that returns false
        verify(resultSet, times(resultSetNextTimes)).next();
        verify(resultSet, times(times)).getString(GekHeadDao.TABLE_COURSE_NAME);
        verify(resultSet, times(times)).getString(GekHeadDao.TABLE_GEK_HEAD);
        verify(resultSet, times(times)).getString(GekHeadDao.TABLE_GEK_SUBHEAD);
        verify(resultSet, times(times)).getString(GekHeadDao.TABLE_GEK_SECRETARY);

    }



}