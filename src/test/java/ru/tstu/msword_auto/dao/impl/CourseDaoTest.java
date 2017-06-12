package ru.tstu.msword_auto.dao.impl;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.tstu.msword_auto.dao.exceptions.AlreadyExistingException;
import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;
import ru.tstu.msword_auto.entity.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static ru.tstu.msword_auto.dao.impl.CourseDao.*;


public class CourseDaoTest {

    // mocks
    private CourseDao dao;
    private Connection connection;
    private PreparedStatement statement;

    // entity content
    private int studentId;
    private int subgroupId;
    private String groupName;
    private String code;
    private String courseName;
    private String profile;
    private String qualification;

    private Course defaultEntity;
    private Course additionalEntity;


    @Before
    public void setUp() {
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        dao = new CourseDao();
        dao.connection = this.connection;

        studentId = 1;
        subgroupId = 2;
        groupName = "group";
        code = "38.03.06";
        courseName = "Торговое дело";
        profile = "qqwe";
        qualification = "Бакалавр";

        defaultEntity = new Course(
                studentId,
                subgroupId,
                groupName,
                qualification,
                courseName,
                profile
        );
        additionalEntity = new Course(
                2,
                subgroupId,
                groupName,
                qualification,
                courseName,
                profile
        );

    }

    @After
    public void tearDown() throws Exception {
        verify(connection, never()).close();
    }


    @Test
    public void whenCreateFullEntityThenAllRight() throws Exception {
        when(connection.prepareStatement(SQL_CREATE)).thenReturn(statement);

        dao.create(defaultEntity);

        verify(connection).prepareStatement(SQL_CREATE);
        verify(statement).setInt(1, studentId);
        verify(statement).setInt(2, subgroupId);
        verify(statement).setString(3, groupName);
        verify(statement).setString(4, code);
        verify(statement).setString(5, courseName);
        verify(statement).setString(6, profile);
        verify(statement).setString(7, qualification);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test
    public void whenReadByPrimaryKeyExistingRowThenAllRight() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(SQL_READ_BY_PK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        adjustResultSet(resultSet, true);

        Course entity = dao.read(studentId);

        verifyQuery(SQL_READ_BY_PK, resultSet, 1);
        verify(resultSet).close();
        verify(statement).close();

        assertEquals(defaultEntity, entity);
    }

    @Test
    public void whenReadAllWithMultipleRowsThenAllRight() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(SQL_READ_ALL)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        adjustResultSet(resultSet, false);

        List<Course> entities = dao.readAll();

        verifyQuery(SQL_READ_ALL, resultSet, 2);
        verify(resultSet).close();
        verify(statement).close();

        Course actual1 = entities.get(0);
        Course actual2 = entities.get(1);
        assertEquals(defaultEntity, actual1);
        assertEquals(additionalEntity, actual2);

    }

    @Test
    public void whenUpdateByPkOneRowThenAllRight() throws Exception {
        when(connection.prepareStatement(SQL_UPDATE)).thenReturn(statement);

        dao.update(studentId, additionalEntity);

        verify(connection).prepareStatement(SQL_UPDATE);
        verify(statement).setInt(1, additionalEntity.getSubgroupId());
        verify(statement).setString(2, additionalEntity.getGroupName());
        verify(statement).setString(3, code);
        verify(statement).setString(4, courseName);
        verify(statement).setString(5, profile);
        verify(statement).setString(6, qualification);
        verify(statement).setInt(7, studentId);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test
    public void whenDeleteByPkThenAllStepsPerformed() throws Exception {
        when(connection.prepareStatement(SQL_DELETE_BY_PK)).thenReturn(statement);

        dao.delete(studentId);

        verify(connection).prepareStatement(SQL_DELETE_BY_PK);
        verify(statement).setInt(1, studentId);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test
    public void whenDeleteAllThenAllActionsPerformed() throws Exception {
        when(connection.prepareStatement(SQL_DELETE_ALL)).thenReturn(statement);

        dao.deleteAll();

        verify(connection).prepareStatement(SQL_DELETE_ALL);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test
    public void whenReadByFkOneRowThenAllActionsPerformed() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(SQL_READ_BY_FK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        adjustResultSet(resultSet, true);

        Course entity = dao.readByForeignKey(studentId).get(0);

        verifyQuery(SQL_READ_BY_FK, resultSet, 1);
        verify(resultSet).close();
        verify(statement).close();

        assertEquals(defaultEntity, entity);

    }

    @Test(expected = NoSuchEntityException.class)
    public void whenReadByForeignKeyNonExistingRowThenException() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(SQL_READ_BY_FK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        List<Course> entities = dao.readByForeignKey(studentId);

        verify(connection).prepareStatement(SQL_READ_BY_FK);
        verify(statement).setInt(1, studentId);
        verify(statement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).close();
        verify(statement).close();

        assertTrue(entities.isEmpty());
    }

    @Test(expected = NoSuchEntityException.class)
    public void whenReadByPrimaryKeyNonExistingRowThenException() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(SQL_READ_BY_PK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Course entity = dao.read(studentId);

        verify(connection).prepareStatement(SQL_READ_BY_PK);
        verify(statement).setInt(1, studentId);
        verify(statement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).close();
        verify(statement).close();

        assertNull(entity);
    }

    @Test(expected = DaoSystemException.class)
    public void whenCreateThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(CourseDao.SQL_CREATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        dao.create(defaultEntity);
    }

    @Test(expected = DaoSystemException.class)
    public void whenReadByPkThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(CourseDao.SQL_READ_BY_PK)).thenReturn(statement);
        when(statement.executeQuery()).thenThrow(SQLException.class);

        dao.read(studentId);
    }

    @Test(expected = DaoSystemException.class)
    public void whenReadByFkThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(CourseDao.SQL_READ_BY_FK)).thenReturn(statement);
        when(statement.executeQuery()).thenThrow(SQLException.class);

        dao.readByForeignKey(studentId);
    }

    @Test(expected = DaoSystemException.class)
    public void whenReadAllThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(CourseDao.SQL_READ_ALL)).thenReturn(statement);
        when(statement.executeQuery()).thenThrow(SQLException.class);

        dao.readAll();
    }

    @Test(expected = DaoSystemException.class)
    public void whenUpdateThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(CourseDao.SQL_UPDATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        dao.update(studentId, defaultEntity);
    }

    @Test(expected = DaoSystemException.class)
    public void whenDeleteByPkThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(CourseDao.SQL_DELETE_BY_PK)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        dao.delete(studentId);
    }

    @Test(expected = DaoSystemException.class)
    public void whenDeleteAllThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
        when(connection.prepareStatement(CourseDao.SQL_DELETE_ALL)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        dao.deleteAll();
    }

    @Test
    public void whenReadByPkStatementFailsThenAllResourcesClosed() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(CourseDao.SQL_READ_BY_PK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenThrow(SQLException.class);

        try {
            dao.read(studentId);
        } catch (DaoSystemException e) {}

        verify(resultSet).close();
        verify(statement).close();
    }

    @Test
    public void whenReadAllFailsThenAllResourcesClosed() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(CourseDao.SQL_READ_ALL)).thenReturn(statement);
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
        when(connection.prepareStatement(CourseDao.SQL_READ_BY_FK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenThrow(SQLException.class);

        try {
            dao.readByForeignKey(studentId);
        } catch (DaoSystemException e) {}

        verify(resultSet).close();
        verify(statement).close();
    }

    @Test
    public void whenCreateStatementFailsThenAllResourcesClosed() throws Exception {
        when(connection.prepareStatement(CourseDao.SQL_CREATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        try {
            dao.create(defaultEntity);
        } catch (DaoSystemException e) {}

        verify(statement).close();
    }

    @Test
    public void whenUpdateStatementFailsThenAllResourcesClosed() throws Exception {
        when(connection.prepareStatement(CourseDao.SQL_UPDATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        try {
            dao.update(studentId, defaultEntity);
        } catch (DaoSystemException e) {}

        verify(statement).close();
    }

    @Test
    public void whenDeleteByPkStatementFailsThenAllResourcesClosed() throws Exception {
        when(connection.prepareStatement(CourseDao.SQL_DELETE_BY_PK)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        try {
            dao.delete(studentId);
        } catch (DaoSystemException e) {}

        verify(statement).close();
    }

    @Test
    public void whenDeleteAllFailsThenAllResourcesClosed() throws Exception {
        when(connection.prepareStatement(CourseDao.SQL_DELETE_ALL)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(SQLException.class);

        try {
            dao.deleteAll();
        } catch (DaoSystemException e) {}

        verify(statement).close();
    }

    @Test(expected = AlreadyExistingException.class)
    public void whenCreateAlreadyExistingThenException() throws Exception {
        when(connection.prepareStatement(SQL_CREATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(new SQLException(EXCEPTION_ALREADY_EXISTS));
        dao.create(defaultEntity);

        verify(connection).prepareStatement(SQL_CREATE);
        verify(statement).setInt(1, studentId);
        verify(statement).setString(2, groupName);
        verify(statement).setString(3, code);
        verify(statement).setString(4, courseName);
        verify(statement).setString(5, profile);
        verify(statement).setString(6, qualification);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test(expected = AlreadyExistingException.class)
    public void whenUpdateAlreadyExistingThenException() throws Exception {
        when(connection.prepareStatement(SQL_UPDATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(new SQLException(EXCEPTION_ALREADY_EXISTS));
        dao.update(studentId, defaultEntity);

        verify(connection).prepareStatement(SQL_UPDATE);
        verify(statement).setString(1, groupName);
        verify(statement).setString(2, code);
        verify(statement).setString(3, courseName);
        verify(statement).setString(4, profile);
        verify(statement).setString(5, qualification);
        verify(statement).setInt(6, studentId);
        verify(statement).executeUpdate();
        verify(statement).close();

    }


    private void adjustResultSet(ResultSet resultSet, boolean oneTime) throws Exception {

        // pk-fk one
        if(oneTime) {
            when(resultSet.next())
                    .thenReturn(true)
                    .thenReturn(false);

            when(resultSet.getInt(TABLE_STUDENT_ID)).thenReturn(studentId);
        } else {    // pk many(at least 2)
            when(resultSet.next())
                    .thenReturn(true)
                    .thenReturn(true)
                    .thenReturn(false);

            when(resultSet.getInt(TABLE_STUDENT_ID))
                    .thenReturn(studentId)
                    .thenReturn(2); // second id, second entity
        }

        when(resultSet.getInt(TABLE_SUBGROUP_ID)).thenReturn(subgroupId);
        when(resultSet.getString(TABLE_GROUP_NAME)).thenReturn(groupName);
        when(resultSet.getString(TABLE_COURSE_CODE)).thenReturn(code);
        when(resultSet.getString(TABLE_COURSE_NAME)).thenReturn(courseName);
        when(resultSet.getString(TABLE_COURSE_PROFILE)).thenReturn(profile);
        when(resultSet.getString(TABLE_QUALIFICATION)).thenReturn(qualification);

    }

    private void verifyQuery(String sql, ResultSet resultSet, int times) throws Exception {
        verify(connection).prepareStatement(sql);
        if(times == 1) {
            verify(statement).setInt(1, studentId);
        }
        verify(statement).executeQuery();

        int resultSetNextTimes = times + 1; // because there would be last, that returns false
        verify(resultSet, times(resultSetNextTimes)).next();
        verify(resultSet, times(times)).getInt(TABLE_STUDENT_ID);
        verify(resultSet, times(times)).getInt(TABLE_SUBGROUP_ID);
        verify(resultSet, times(times)).getString(TABLE_GROUP_NAME);
        verify(resultSet, times(times)).getString(TABLE_COURSE_NAME);
        verify(resultSet, times(times)).getString(TABLE_COURSE_PROFILE);
        verify(resultSet, times(times)).getString(TABLE_QUALIFICATION);

    }

}































