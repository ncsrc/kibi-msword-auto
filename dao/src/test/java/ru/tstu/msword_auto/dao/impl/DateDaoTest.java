package ru.tstu.msword_auto.dao.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.tstu.msword_auto.dao.exceptions.AlreadyExistingException;
import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;
import ru.tstu.msword_auto.entity.Date;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.tstu.msword_auto.dao.impl.AbstractDao.EXCEPTION_ALREADY_EXISTS;


public class DateDaoTest {

    // mocks
    private DateDao dao;
    private Connection connection;
    private PreparedStatement statement;

    // entity content
    private int dateId;
    private int groupId;
    private String groupName;
    private String gosDate;
    private String vcrDate;

    // entities
    private Date defaultEntity;
    private Date additionalEntity;


    @Before
    public void setUp() {
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        dao = new DateDao();
        dao.connection = this.connection;

        dateId = 1;
        groupId = 2;
        groupName = "name";
        gosDate = "2001-01-02";
        vcrDate = "2003-03-04";

        defaultEntity = new Date(dateId, groupId, groupName, gosDate, vcrDate);
        additionalEntity = new Date(3, groupId, groupName, gosDate, vcrDate);

    }

    @After
    public void tearDown() throws Exception {
        verify(connection, never()).close();
    }


    @Test
    public void whenCreateFullEntityThenAllRight() throws Exception {
        when(connection.prepareStatement(DateDao.SQL_CREATE)).thenReturn(statement);
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date parsedGos = new java.sql.Date(parser.parse(defaultEntity.getGosDate()).getTime());
        java.sql.Date parsedVcr = new java.sql.Date(parser.parse(defaultEntity.getVcrDate()).getTime());

        dao.create(defaultEntity);

        verify(connection).prepareStatement(DateDao.SQL_CREATE);
        verify(statement).setInt(1, groupId);
        verify(statement).setString(2, groupName);
        verify(statement).setDate(3, parsedGos);
        verify(statement).setDate(4, parsedVcr);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test
    public void whenReadByGroupNameExistingRowsThenAllRight() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(DateDao.SQL_READ_BY_GROUP_NAME)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        adjustResultSet(resultSet, false);

        List<Date> entities = dao.readByGroupName(groupName);

        verifyQuery(DateDao.SQL_READ_BY_GROUP_NAME, resultSet, 2, true);
        verify(resultSet).close();
        verify(statement).close();

        Date actual1 = entities.get(0);
        Date actual2 = entities.get(1);
        assertEquals(defaultEntity, actual1);
        assertEquals(additionalEntity, actual2);

    }

    @Test
    public void whenReadByGroupNameAndGroupIdExistingRowThenAllRight() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(DateDao.SQL_READ_BY_GROUP_NAME_AND_GROUP_ID)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getInt(DateDao.TABLE_DATE_ID)).thenReturn(dateId);

        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date parsedGos = new java.sql.Date(parser.parse(gosDate).getTime());
        java.sql.Date parsedVcr = new java.sql.Date(parser.parse(vcrDate).getTime());

        when(resultSet.getInt(DateDao.TABLE_GROUP_ID)).thenReturn(groupId);
        when(resultSet.getString(DateDao.TABLE_GROUP_NAME)).thenReturn(groupName);
        when(resultSet.getDate(DateDao.TABLE_DATE_GOS)).thenReturn(parsedGos);
        when(resultSet.getDate(DateDao.TABLE_DATE_VCR)).thenReturn(parsedVcr);

        Date entity = dao.readByGroupNameAndGroupId(groupName, groupId);

        verify(statement).setString(1, groupName);
        verify(statement).setInt(2, groupId);
        verify(statement).executeQuery();
        verify(resultSet, times(2)).next();
        verify(resultSet).getInt(DateDao.TABLE_DATE_ID);
        verify(resultSet).getInt(DateDao.TABLE_GROUP_ID);
        verify(resultSet).getString(DateDao.TABLE_GROUP_NAME);
        verify(resultSet).getDate(DateDao.TABLE_DATE_GOS);
        verify(resultSet).getDate(DateDao.TABLE_DATE_VCR);
        verify(resultSet).close();
        verify(statement).close();

        assertEquals(defaultEntity, entity);
    }

    @Test
    public void whenReadByPrimaryKeyExistingRowThenAllRight() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(DateDao.SQL_READ_BY_PK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        adjustResultSet(resultSet, true);

        Date entity = dao.read(dateId);

        verifyQuery(DateDao.SQL_READ_BY_PK, resultSet, 1, true);
        verify(resultSet).close();
        verify(statement).close();

        assertEquals(defaultEntity, entity);
    }

    @Test
    public void whenReadByPrimaryKeyRowWithNullDatesThenEntityWithEmptyStrings() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        Date emptyEntity = new Date(groupName);
        when(connection.prepareStatement(DateDao.SQL_READ_BY_PK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);
        when(resultSet.getString(DateDao.TABLE_GROUP_NAME)).thenReturn(groupName);
        when(resultSet.getDate(DateDao.TABLE_DATE_GOS)).thenReturn(null);
        when(resultSet.getDate(DateDao.TABLE_DATE_VCR)).thenReturn(null);

        Date entity = dao.read(dateId);

        verifyQuery(DateDao.SQL_READ_BY_PK, resultSet, 1, true);
        verify(resultSet).close();
        verify(statement).close();

        assertEquals(emptyEntity, entity);
    }

    @Test
    public void whenReadAllWithMultipleRowsThenAllRight() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(DateDao.SQL_READ_ALL)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        adjustResultSet(resultSet, false);

        List<Date> entities = dao.readAll();

        verifyQuery(DateDao.SQL_READ_ALL, resultSet, 2, true);
        verify(resultSet).close();
        verify(statement).close();

        Date actual1 = entities.get(0);
        Date actual2 = entities.get(1);
        assertEquals(defaultEntity, actual1);
        assertEquals(additionalEntity, actual2);

    }

    @Test
    public void whenUpdateByPkOneRowThenAllRight() throws Exception {
        when(connection.prepareStatement(DateDao.SQL_UPDATE)).thenReturn(statement);
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date parsedGos = new java.sql.Date(parser.parse(defaultEntity.getGosDate()).getTime());
        java.sql.Date parsedVcr = new java.sql.Date(parser.parse(defaultEntity.getVcrDate()).getTime());

        dao.update(dateId, additionalEntity);

        verify(connection).prepareStatement(DateDao.SQL_UPDATE);
        verify(statement).setInt(1, groupId);
        verify(statement).setString(2, groupName);
        verify(statement).setDate(3, parsedGos);
        verify(statement).setDate(4, parsedVcr);
        verify(statement).setInt(5, dateId);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test
    public void whenDeleteByPkThenAllStepsPerformed() throws Exception {
        when(connection.prepareStatement(DateDao.SQL_DELETE_BY_PK)).thenReturn(statement);

        dao.delete(dateId);

        verify(connection).prepareStatement(DateDao.SQL_DELETE_BY_PK);
        verify(statement).setInt(1, dateId);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test
    public void whenDeleteAllThenAllActionsPerformed() throws Exception {
        when(connection.prepareStatement(DateDao.SQL_DELETE_ALL)).thenReturn(statement);

        dao.deleteAll();

        verify(connection).prepareStatement(DateDao.SQL_DELETE_ALL);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test
    public void whenDatesInEntityAreEmptyStringsThenSqlDateAreNullInCreate() throws Exception {
        when(connection.prepareStatement(DateDao.SQL_CREATE)).thenReturn(statement);

        Date entityEmpty = new Date(groupName);

        dao.create(entityEmpty);

        verify(connection).prepareStatement(DateDao.SQL_CREATE);
        verify(statement).setInt(1, 0);
        verify(statement).setString(2, groupName);
        verify(statement).setDate(3, null);
        verify(statement).setDate(4, null);
        verify(statement).executeUpdate();
        verify(statement).close();
    }

    @Test
    public void whenDatesInEntityAreEmptyStringsThenSqlDateAreNullInUpdate() throws  Exception {
        when(connection.prepareStatement(DateDao.SQL_UPDATE)).thenReturn(statement);

        Date entityEmpty = new Date(groupName);

        dao.update(dateId, entityEmpty);

        verify(connection).prepareStatement(DateDao.SQL_UPDATE);
        verify(statement).setInt(1, 0);
        verify(statement).setString(2, groupName);
        verify(statement).setDate(3, null);
        verify(statement).setDate(4, null);
        verify(statement).setInt(5, dateId);
        verify(statement).executeUpdate();
        verify(statement).close();

    }


    @Test(expected = NoSuchEntityException.class)
    public void whenReadByPrimaryKeyNonExistingRowThenException() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(DateDao.SQL_READ_BY_PK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Date entity = dao.read(dateId);

        verify(connection).prepareStatement(DateDao.SQL_READ_BY_PK);
        verify(statement).setString(1, groupName);
        verify(statement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).close();
        verify(statement).close();

        assertNull(entity);
    }

    @Test(expected = NoSuchEntityException.class)
    public void whenReadByGroupNameAndGroupIdNonExistingRowThenException() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(DateDao.SQL_READ_BY_GROUP_NAME_AND_GROUP_ID)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Date entity = dao.readByGroupNameAndGroupId(groupName, groupId);

        verify(connection).prepareStatement(DateDao.SQL_READ_BY_GROUP_NAME_AND_GROUP_ID);
        verify(statement).setString(1, groupName);
        verify(statement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).close();
        verify(statement).close();

        assertNull(entity);
    }

    @Test(expected = DaoSystemException.class)
    public void whenCreateThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(DateDao.SQL_CREATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        dao.create(defaultEntity);
    }

    @Test(expected = DaoSystemException.class)
    public void whenReadByPkThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(DateDao.SQL_READ_BY_PK)).thenReturn(statement);
        when(statement.executeQuery()).thenThrow(SQLException.class);

        dao.read(dateId);
    }

    @Test(expected = DaoSystemException.class)
    public void whenReadAllThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(DateDao.SQL_READ_ALL)).thenReturn(statement);
        when(statement.executeQuery()).thenThrow(SQLException.class);

        dao.readAll();
    }

    @Test(expected = DaoSystemException.class)
    public void whenUpdateThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(DateDao.SQL_UPDATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        dao.update(dateId, defaultEntity);
    }

    @Test(expected = DaoSystemException.class)
    public void whenDeleteByPkThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(DateDao.SQL_DELETE_BY_PK)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        dao.delete(dateId);
    }

    @Test(expected = DaoSystemException.class)
    public void whenDeleteAllThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(DateDao.SQL_DELETE_ALL)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        dao.deleteAll();
    }

    @Test
    public void whenReadByPkStatementFailsThenAllResourcesClosed() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(DateDao.SQL_READ_BY_PK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenThrow(SQLException.class);

        try {
            dao.read(dateId);
        } catch (DaoSystemException e) {}

        verify(resultSet).close();
        verify(statement).close();
    }

    @Test
    public void whenReadAllFailsThenAllResourcesClosed() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(DateDao.SQL_READ_ALL)).thenReturn(statement);
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
        when(connection.prepareStatement(DateDao.SQL_CREATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        try {
            dao.create(defaultEntity);
        } catch (DaoSystemException e) {}

        verify(statement).close();
    }

    @Test
    public void whenUpdateStatementFailsThenAllResourcesClosed() throws Exception {
        when(connection.prepareStatement(DateDao.SQL_UPDATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        try {
            dao.update(dateId, defaultEntity);
        } catch (DaoSystemException e) {}

        verify(statement).close();
    }

    @Test
    public void whenDeleteByPkStatementFailsThenAllResourcesClosed() throws Exception {
        when(connection.prepareStatement(DateDao.SQL_DELETE_BY_PK)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        try {
            dao.delete(dateId);
        } catch (DaoSystemException e) {}

        verify(statement).close();
    }

    @Test
    public void whenDeleteAllFailsThenAllResourcesClosed() throws Exception {
        when(connection.prepareStatement(DateDao.SQL_DELETE_ALL)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        try {
            dao.deleteAll();
        } catch (DaoSystemException e) {}

        verify(statement).close();
    }

    @Test(expected = AlreadyExistingException.class)
    public void whenCreateAlreadyExistingThenException() throws Exception {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date parsedGos = new java.sql.Date(parser.parse(defaultEntity.getGosDate()).getTime());
        java.sql.Date parsedVcr = new java.sql.Date(parser.parse(defaultEntity.getVcrDate()).getTime());

        when(connection.prepareStatement(DateDao.SQL_CREATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(new SQLException(EXCEPTION_ALREADY_EXISTS));
        dao.create(defaultEntity);

        verify(connection).prepareStatement(DateDao.SQL_CREATE);
        verify(connection).prepareStatement(DateDao.SQL_CREATE);
        verify(statement).setString(1, groupName);
        verify(statement).setDate(2, parsedGos);
        verify(statement).setDate(3, parsedVcr);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test(expected = AlreadyExistingException.class)
    public void whenUpdateAlreadyExistingThenException() throws Exception {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date parsedGos = new java.sql.Date(parser.parse(defaultEntity.getGosDate()).getTime());
        java.sql.Date parsedVcr = new java.sql.Date(parser.parse(defaultEntity.getVcrDate()).getTime());
        when(connection.prepareStatement(DateDao.SQL_UPDATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(new SQLException(EXCEPTION_ALREADY_EXISTS));
        dao.update(dateId, defaultEntity);

        verify(connection).prepareStatement(DateDao.SQL_UPDATE);
        verify(statement).setString(1, "asd");
        verify(statement).setDate(2, parsedGos);
        verify(statement).setDate(3, parsedVcr);
        verify(statement).executeUpdate();
        verify(statement).close();

    }


    private void adjustResultSet(ResultSet resultSet, boolean oneTime) throws Exception {

        if(oneTime) {
            when(resultSet.next())
                    .thenReturn(true)
                    .thenReturn(false);

            when(resultSet.getInt(DateDao.TABLE_DATE_ID)).thenReturn(dateId);
        } else {    // pk many(at least 2)
            when(resultSet.next())
                    .thenReturn(true)
                    .thenReturn(true)
                    .thenReturn(false);

            when(resultSet.getInt(DateDao.TABLE_DATE_ID))
                    .thenReturn(dateId)
                    .thenReturn(additionalEntity.getDateId()); // second id, second entity
        }

        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date parsedGos = new java.sql.Date(parser.parse(gosDate).getTime());
        java.sql.Date parsedVcr = new java.sql.Date(parser.parse(vcrDate).getTime());

        when(resultSet.getInt(DateDao.TABLE_GROUP_ID)).thenReturn(groupId);
        when(resultSet.getString(DateDao.TABLE_GROUP_NAME)).thenReturn(groupName);
        when(resultSet.getDate(DateDao.TABLE_DATE_GOS)).thenReturn(parsedGos);
        when(resultSet.getDate(DateDao.TABLE_DATE_VCR)).thenReturn(parsedVcr);
    }

    private void verifyQuery(String sql, ResultSet resultSet, int times, boolean byPrimaryKey) throws Exception {
        verify(connection).prepareStatement(sql);

        // if by pk
        if(times == 1 && byPrimaryKey) {
            verify(statement).setInt(1, dateId);
        } else if(times == 1 && !byPrimaryKey) {
            // intentionally blank, does not have foreign key
        }
        verify(statement).executeQuery();

        int resultSetNextTimes = times + 1; // because there would be last, that returns false
        verify(resultSet, times(resultSetNextTimes)).next();
        verify(resultSet, times(times)).getInt(DateDao.TABLE_DATE_ID);
        verify(resultSet, times(times)).getInt(DateDao.TABLE_GROUP_ID);
        verify(resultSet, times(times)).getString(DateDao.TABLE_GROUP_NAME);
        verify(resultSet, times(times)).getDate(DateDao.TABLE_DATE_GOS);
        verify(resultSet, times(times)).getDate(DateDao.TABLE_DATE_VCR);

    }





}


























