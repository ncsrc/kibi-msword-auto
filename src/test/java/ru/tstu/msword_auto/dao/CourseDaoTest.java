package ru.tstu.msword_auto.dao;


import org.junit.Before;
import org.junit.Test;
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


public class CourseDaoTest {

    // mocks
    private CourseDao dao;
    private Connection connection;
    private PreparedStatement statement;

    // entity content
    private int studentId;
    private String groupName;
    private String code;
    private String courseName;
    private String profile;
    private String qualification;

    // table column names
    private String sqlStudentId;
    private String sqlGroupName;
    private String sqlCourseCode;
    private String sqlCourseName;
    private String sqlCourseProfile;
    private String sqlQualificatgion;
    private Course defaultEntity;
    private Course additionalEntity;


    @Before
    public void setUp() {
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        dao = new CourseDao();
        dao.connection = this.connection;

        studentId = 1;
        groupName = "group";
        code = "1.1.1";
        courseName = "course";
        profile = "profile";
        qualification = "q";
        sqlStudentId = "STUDENT_ID";
        sqlGroupName = "GROUP_NAME";
        sqlCourseCode = "COURSE_CODE";
        sqlCourseName = "COURSE_NAME";
        sqlCourseProfile = "COURSE_PROFILE";
        sqlQualificatgion = "QUALIFICATION";

        defaultEntity = new Course(
                studentId,
                groupName,
                code,
                qualification,
                courseName,
                profile
        );
        additionalEntity = new Course(
                2,
                groupName,
                code,
                qualification,
                courseName,
                profile
        );

    }


    @Test
    public void whenCreateFullEntityThenAllRight() throws Exception {
        when(connection.prepareStatement(CourseDao.SQL_CREATE)).thenReturn(statement);

        dao.create(defaultEntity);

        verify(connection).prepareStatement(CourseDao.SQL_CREATE);
        verify(statement).setInt(1, studentId);
        verify(statement).setString(2, groupName);
        verify(statement).setString(3, code);
        verify(statement).setString(4, courseName);
        verify(statement).setString(5, profile);
        verify(statement).setString(6, qualification);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test
    public void whenReadByPrimaryKeyExistingRowThenAllRight() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(CourseDao.SQL_READ_BY_PK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        adjustResultSet(resultSet, true);

        Course entity = dao.read(studentId);

        verifyQuery(CourseDao.SQL_READ_BY_PK, resultSet, 1);
        verify(resultSet).close();
        verify(statement).close();

        assertEquals(defaultEntity, entity);
    }

    @Test(expected = SQLException.class)
    public void whenReadByPrimaryKeyNonExistingRowThenException() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(CourseDao.SQL_READ_BY_PK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Course entity = dao.read(studentId);

        verify(connection).prepareStatement(CourseDao.SQL_READ_BY_PK);
        verify(statement).setInt(1, studentId);
        verify(statement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).close();
        verify(statement).close();

        assertNull(entity);
    }

    @Test
    public void whenReadAllWithMultipleRowsThenAllRight() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(CourseDao.SQL_READ_ALL)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        adjustResultSet(resultSet, false);

        List<Course> entities = dao.readAll();

        verifyQuery(CourseDao.SQL_READ_ALL, resultSet, 2);
        verify(resultSet).close();
        verify(statement).close();

        Course actual1 = entities.get(0);
        Course actual2 = entities.get(1);
        assertEquals(defaultEntity, actual1);
        assertEquals(additionalEntity, actual2);

    }

    @Test
    public void whenUpdateByPkOneRowThenAllRight() throws Exception {
        when(connection.prepareStatement(CourseDao.SQL_UPDATE)).thenReturn(statement);

        dao.update(studentId, additionalEntity);

        verify(connection).prepareStatement(CourseDao.SQL_UPDATE);
        verify(statement).setString(1, groupName);
        verify(statement).setString(2, code);
        verify(statement).setString(3, courseName);
        verify(statement).setString(4, profile);
        verify(statement).setString(5, qualification);
        verify(statement).setInt(6, studentId);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test
    public void whenDeleteByPkThenAllStepsPerformed() throws Exception {
        when(connection.prepareStatement(CourseDao.SQL_DELETE_BY_PK)).thenReturn(statement);

        dao.delete(studentId);

        verify(connection).prepareStatement(CourseDao.SQL_DELETE_BY_PK);
        verify(statement).setInt(1, studentId);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test
    public void whenDeleteAllThenAllActionsPerformed() throws Exception {
        when(connection.prepareStatement(CourseDao.SQL_DELETE_ALL)).thenReturn(statement);

        dao.deleteAll();

        verify(connection).prepareStatement(CourseDao.SQL_DELETE_ALL);
        verify(statement).executeUpdate();
        verify(statement).close();

    }

    @Test
    public void whenReadByFkOneRowThenAllActionsPerformed() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(CourseDao.SQL_READ_BY_FK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        adjustResultSet(resultSet, true);

        Course entity = dao.readByForeignKey(studentId).get(0);

        verifyQuery(CourseDao.SQL_READ_BY_FK, resultSet, 1);
        verify(resultSet).close();
        verify(statement).close();

        assertEquals(defaultEntity, entity);

    }

    @Test(expected = SQLException.class)
    public void whenReadByForeignKeyNonExistingRowThenException() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(CourseDao.SQL_READ_BY_FK)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        List<Course> entities = dao.readByForeignKey(studentId);

        verify(connection).prepareStatement(CourseDao.SQL_READ_BY_FK);
        verify(statement).setInt(1, studentId);
        verify(statement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).close();
        verify(statement).close();

        assertTrue(entities.isEmpty());
    }


    private void adjustResultSet(ResultSet resultSet, boolean oneTime) throws Exception {

        // pk-fk one
        if(oneTime) {
            when(resultSet.next())
                    .thenReturn(true)
                    .thenReturn(false);

            when(resultSet.getInt(sqlStudentId)).thenReturn(studentId);
        } else {    // pk many(at least 2)
            when(resultSet.next())
                    .thenReturn(true)
                    .thenReturn(true)
                    .thenReturn(false);

            when(resultSet.getInt(sqlStudentId))
                    .thenReturn(studentId)
                    .thenReturn(2); // second id, second entity
        }

        when(resultSet.getString(sqlGroupName)).thenReturn(groupName);
        when(resultSet.getString(sqlCourseCode)).thenReturn(code);
        when(resultSet.getString(sqlCourseName)).thenReturn(courseName);
        when(resultSet.getString(sqlCourseProfile)).thenReturn(profile);
        when(resultSet.getString(sqlQualificatgion)).thenReturn(qualification);

    }

    private void verifyQuery(String sql, ResultSet resultSet, int times) throws Exception {
        verify(connection).prepareStatement(sql);
        if(times == 1) {
            verify(statement).setInt(1, studentId);
        }
        verify(statement).executeQuery();

        int resultSetNextTimes = times + 1; // because there would be last, that returns false
        verify(resultSet, times(resultSetNextTimes)).next();
        verify(resultSet, times(times)).getInt(sqlStudentId);
        verify(resultSet, times(times)).getString(sqlGroupName);
        verify(resultSet, times(times)).getString(sqlCourseCode);
        verify(resultSet, times(times)).getString(sqlCourseName);
        verify(resultSet, times(times)).getString(sqlCourseProfile);
        verify(resultSet, times(times)).getString(sqlQualificatgion);

    }

}































