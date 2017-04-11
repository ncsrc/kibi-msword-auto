package ru.tstu.msword_auto.dao.impl;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.tstu.msword_auto.dao.exceptions.AlreadyExistingException;
import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;
import ru.tstu.msword_auto.entity.Student;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.tstu.msword_auto.dao.impl.AbstractDao.EXCEPTION_ALREADY_EXISTS;


public class StudentDaoTest {
	// mocks
	private StudentDao dao;
	private Connection connection;
	private PreparedStatement statement;

	// entity content
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

	private Student defaultEntity;
	private Student additionalEntity;


	@Before
	public void setUp() {
		connection = mock(Connection.class);
		statement = mock(PreparedStatement.class);
		dao = new StudentDao();
		dao.connection = this.connection;

		studentId = 1;
		firstNameI = "fni";
		lastNameI = "lni";
		middleNameI = "mni";
		firstNameR = "fnr";
		lastNameR = "lnr";
		middleNameR = "mnr";
		firstNameD = "fnd";
		lastNameD = "lnd";
		middleNameD = "mnd";

		defaultEntity = new Student(
				firstNameI, lastNameI, middleNameI,
				firstNameR, lastNameR, middleNameR,
				firstNameD, lastNameD, middleNameD
		);
		defaultEntity.setStudentId(studentId);

		additionalEntity = new Student(
				firstNameI, lastNameI, middleNameI,
				firstNameR, lastNameR, middleNameR,
				firstNameD, lastNameD, middleNameD
		);
		additionalEntity.setStudentId(2);

	}

	@After
	public void tearDown() throws Exception {
		verify(connection, never()).close();
	}


	@Test
	public void whenCreateFullEntityThenAllRight() throws Exception {
		when(connection.prepareStatement(StudentDao.SQL_CREATE)).thenReturn(statement);

		dao.create(defaultEntity);

		verify(connection).prepareStatement(StudentDao.SQL_CREATE);
		verify(statement).setString(1, firstNameI);
		verify(statement).setString(2, lastNameI);
		verify(statement).setString(3, middleNameI);
		verify(statement).setString(4, firstNameR);
		verify(statement).setString(5, lastNameR);
		verify(statement).setString(6, middleNameR);
		verify(statement).setString(7, firstNameD);
		verify(statement).setString(8, lastNameD);
		verify(statement).setString(9, middleNameD);
		verify(statement).executeUpdate();
		verify(statement).close();
	}

	@Test
	public void whenReadByPrimaryKeyExistingRowThenAllRight() throws Exception {
		ResultSet resultSet = mock(ResultSet.class);
		when(connection.prepareStatement(StudentDao.SQL_READ_BY_PK)).thenReturn(statement);
		when(statement.executeQuery()).thenReturn(resultSet);

		adjustResultSet(resultSet, true);

		Student entity = dao.read(studentId);

		verifyQuery(StudentDao.SQL_READ_BY_PK, resultSet, 1);
		verify(resultSet).close();
		verify(statement).close();

		assertEquals(defaultEntity, entity);
	}

	@Test
	public void whenReadAllWithMultipleRowsThenAllRight() throws Exception {
		ResultSet resultSet = mock(ResultSet.class);
		when(connection.prepareStatement(StudentDao.SQL_READ_ALL)).thenReturn(statement);
		when(statement.executeQuery()).thenReturn(resultSet);

		adjustResultSet(resultSet, false);

		List<Student> entities = dao.readAll();

		verifyQuery(StudentDao.SQL_READ_ALL, resultSet, 2);
		verify(resultSet).close();
		verify(statement).close();

		Student actual1 = entities.get(0);
		Student actual2 = entities.get(1);
		assertEquals(defaultEntity, actual1);
		assertEquals(additionalEntity, actual2);

	}

	@Test
	public void whenUpdateByPkOneRowThenAllRight() throws Exception {
		when(connection.prepareStatement(StudentDao.SQL_UPDATE)).thenReturn(statement);

		dao.update(studentId, additionalEntity);

		verify(connection).prepareStatement(StudentDao.SQL_UPDATE);
		verify(statement).setString(1, firstNameI);
		verify(statement).setString(2, lastNameI);
		verify(statement).setString(3, middleNameI);
		verify(statement).setString(4, firstNameR);
		verify(statement).setString(5, lastNameR);
		verify(statement).setString(6, middleNameR);
		verify(statement).setString(7, firstNameD);
		verify(statement).setString(8, lastNameD);
		verify(statement).setString(9, middleNameD);
		verify(statement).executeUpdate();
		verify(statement).close();

	}

	@Test
	public void whenDeleteByPkThenAllStepsPerformed() throws Exception {
		when(connection.prepareStatement(StudentDao.SQL_DELETE_BY_PK)).thenReturn(statement);

		dao.delete(studentId);

		verify(connection).prepareStatement(StudentDao.SQL_DELETE_BY_PK);
		verify(statement).setInt(1, studentId);
		verify(statement).executeUpdate();
		verify(statement).close();

	}

	@Test
	public void whenDeleteAllThenAllActionsPerformed() throws Exception {
		when(connection.prepareStatement(StudentDao.SQL_DELETE_ALL)).thenReturn(statement);

		dao.deleteAll();

		verify(connection).prepareStatement(StudentDao.SQL_DELETE_ALL);
		verify(statement).executeUpdate();
		verify(statement).close();

	}

	@Test(expected = NoSuchEntityException.class)
	public void whenReadByPrimaryKeyNonExistingRowThenException() throws Exception {
		ResultSet resultSet = mock(ResultSet.class);
		when(connection.prepareStatement(StudentDao.SQL_READ_BY_PK)).thenReturn(statement);
		when(statement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(false);

		Student entity = dao.read(studentId);

		verify(connection).prepareStatement(StudentDao.SQL_READ_BY_PK);
		verify(statement).setInt(1, studentId);
		verify(statement).executeQuery();
		verify(resultSet).next();
		verify(resultSet).close();
		verify(statement).close();

		assertNull(entity);
	}

	@Test(expected = DaoSystemException.class)
	public void whenCreateThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
		when(connection.prepareStatement(StudentDao.SQL_CREATE)).thenReturn(statement);
		when(statement.executeUpdate()).thenThrow(SQLException.class);

		dao.create(defaultEntity);
	}

	@Test(expected = DaoSystemException.class)
	public void whenReadByPkThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
		when(connection.prepareStatement(StudentDao.SQL_READ_BY_PK)).thenReturn(statement);
		when(statement.executeQuery()).thenThrow(SQLException.class);

		dao.read(studentId);
	}

	@Test(expected = DaoSystemException.class)
	public void whenReadAllThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
		when(connection.prepareStatement(StudentDao.SQL_READ_ALL)).thenReturn(statement);
		when(statement.executeQuery()).thenThrow(SQLException.class);

		dao.readAll();
	}

	@Test(expected = DaoSystemException.class)
	public void whenUpdateThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
		when(connection.prepareStatement(StudentDao.SQL_UPDATE)).thenReturn(statement);
		when(statement.executeUpdate()).thenThrow(SQLException.class);

		dao.update(studentId, defaultEntity);
	}

	@Test(expected = DaoSystemException.class)
	public void whenDeleteByPkThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
		when(connection.prepareStatement(StudentDao.SQL_DELETE_BY_PK)).thenReturn(statement);
		when(statement.executeUpdate()).thenThrow(SQLException.class);

		dao.delete(studentId);
	}

	@Test(expected = DaoSystemException.class)
	public void whenDeleteAllThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
		when(connection.prepareStatement(StudentDao.SQL_DELETE_ALL)).thenReturn(statement);
		when(statement.executeUpdate()).thenThrow(SQLException.class);

		dao.deleteAll();
	}

	@Test
	public void whenReadByPkStatementFailsThenAllResourcesClosed() throws Exception {
		ResultSet resultSet = mock(ResultSet.class);
		when(connection.prepareStatement(StudentDao.SQL_READ_BY_PK)).thenReturn(statement);
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
		when(connection.prepareStatement(StudentDao.SQL_READ_ALL)).thenReturn(statement);
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
		when(connection.prepareStatement(StudentDao.SQL_CREATE)).thenReturn(statement);
		when(statement.executeUpdate()).thenThrow(SQLException.class);

		try {
			dao.create(defaultEntity);
		} catch (DaoSystemException e) {}

		verify(statement).close();
	}

	@Test
	public void whenUpdateStatementFailsThenAllResourcesClosed() throws Exception {
		when(connection.prepareStatement(StudentDao.SQL_UPDATE)).thenReturn(statement);
		when(statement.executeUpdate()).thenThrow(SQLException.class);

		try {
			dao.update(studentId, defaultEntity);
		} catch (DaoSystemException e) {}

		verify(statement).close();
	}

	@Test
	public void whenDeleteByPkStatementFailsThenAllResourcesClosed() throws Exception {
		when(connection.prepareStatement(StudentDao.SQL_DELETE_BY_PK)).thenReturn(statement);
		when(statement.executeUpdate()).thenThrow(SQLException.class);

		try {
			dao.delete(studentId);
		} catch (DaoSystemException e) {}

		verify(statement).close();
	}

	@Test
	public void whenDeleteAllFailsThenAllResourcesClosed() throws Exception {
		when(connection.prepareStatement(StudentDao.SQL_DELETE_ALL)).thenReturn(statement);
		when(statement.executeUpdate()).thenThrow(SQLException.class);

		try {
			dao.deleteAll();
		} catch (DaoSystemException e) {}

		verify(statement).close();
	}

	@Test(expected = AlreadyExistingException.class)
	public void whenCreateAlreadyExistingThenException() throws Exception {
		when(connection.prepareStatement(StudentDao.SQL_CREATE)).thenReturn(statement);
		when(statement.executeUpdate()).thenThrow(new SQLException(EXCEPTION_ALREADY_EXISTS));
		dao.create(defaultEntity);

	}

	@Test(expected = AlreadyExistingException.class)
	public void whenUpdateAlreadyExistingThenException() throws Exception {
		when(connection.prepareStatement(StudentDao.SQL_UPDATE)).thenReturn(statement);
		when(statement.executeUpdate()).thenThrow(new SQLException(EXCEPTION_ALREADY_EXISTS));
		dao.update(studentId, defaultEntity);

	}


	private void adjustResultSet(ResultSet resultSet, boolean oneTime) throws Exception {

		if(oneTime) {
			when(resultSet.next())
					.thenReturn(true)
					.thenReturn(false);

			when(resultSet.getInt(StudentDao.TABLE_STUDENT_ID)).thenReturn(studentId);
		} else {    // pk many(at least 2)
			when(resultSet.next())
					.thenReturn(true)
					.thenReturn(true)
					.thenReturn(false);

			when(resultSet.getInt(StudentDao.TABLE_STUDENT_ID))
					.thenReturn(studentId)
					.thenReturn(2); // second id, second entity
		}

		when(resultSet.getString(StudentDao.TABLE_FIRST_NAME_I)).thenReturn(firstNameI);
		when(resultSet.getString(StudentDao.TABLE_LAST_NAME_I)).thenReturn(lastNameI);
		when(resultSet.getString(StudentDao.TABLE_MIDDLE_NAME_I)).thenReturn(middleNameI);
		when(resultSet.getString(StudentDao.TABLE_FIRST_NAME_R)).thenReturn(firstNameR);
		when(resultSet.getString(StudentDao.TABLE_LAST_NAME_R)).thenReturn(lastNameR);
		when(resultSet.getString(StudentDao.TABLE_MIDDLE_NAME_R)).thenReturn(middleNameR);
		when(resultSet.getString(StudentDao.TABLE_FIRST_NAME_D)).thenReturn(firstNameD);
		when(resultSet.getString(StudentDao.TABLE_LAST_NAME_D)).thenReturn(lastNameD);
		when(resultSet.getString(StudentDao.TABLE_MIDDLE_NAME_D)).thenReturn(middleNameD);
	}

	private void verifyQuery(String sql, ResultSet resultSet, int times) throws Exception {
		verify(connection).prepareStatement(sql);
		if(times == 1) {
			verify(statement).setInt(1, studentId);
		}
		verify(statement).executeQuery();

		int resultSetNextTimes = times + 1; // because there would be last, that returns false
		verify(resultSet, times(resultSetNextTimes)).next();
		verify(resultSet, times(times)).getInt(StudentDao.TABLE_STUDENT_ID);
		verify(resultSet, times(times)).getString(StudentDao.TABLE_FIRST_NAME_I);
		verify(resultSet, times(times)).getString(StudentDao.TABLE_LAST_NAME_I);
		verify(resultSet, times(times)).getString(StudentDao.TABLE_MIDDLE_NAME_I);
		verify(resultSet, times(times)).getString(StudentDao.TABLE_FIRST_NAME_R);
		verify(resultSet, times(times)).getString(StudentDao.TABLE_LAST_NAME_R);
		verify(resultSet, times(times)).getString(StudentDao.TABLE_MIDDLE_NAME_R);
		verify(resultSet, times(times)).getString(StudentDao.TABLE_FIRST_NAME_D);
		verify(resultSet, times(times)).getString(StudentDao.TABLE_LAST_NAME_D);
		verify(resultSet, times(times)).getString(StudentDao.TABLE_MIDDLE_NAME_D);
	}



}