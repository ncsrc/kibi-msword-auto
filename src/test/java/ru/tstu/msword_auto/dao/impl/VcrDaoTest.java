package ru.tstu.msword_auto.dao.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;
import ru.tstu.msword_auto.entity.VCR;


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


public class VcrDaoTest {

	// mocks
	private VcrDao dao;
	private Connection connection;
	private PreparedStatement statement;

	// entity content
	private int studentId;
	private String vcrName;
	private String vcrHead;
	private String vcrReviewer;

	// entities
	private VCR defaultEntity;
	private VCR additionalEntity;


	@Before
	public void setUp() {
		connection = mock(Connection.class);
		statement = mock(PreparedStatement.class);
		dao = new VcrDao();
		dao.connection = this.connection;

		studentId = 1;
		vcrName = "name";
		vcrHead = "head";
		vcrReviewer = "reviewer";

		defaultEntity = new VCR(studentId, vcrName, vcrHead, vcrReviewer);
		additionalEntity = new VCR(2, vcrName, vcrHead, vcrReviewer);

	}

	@After
	public void tearDown() throws Exception {
		verify(connection, never()).close();
	}


	@Test
	public void whenCreateFullEntityThenAllRight() throws Exception {
		when(connection.prepareStatement(VcrDao.SQL_CREATE)).thenReturn(statement);

		dao.create(defaultEntity);

		verify(connection).prepareStatement(VcrDao.SQL_CREATE);
		verify(statement).setInt(1, studentId);
		verify(statement).setString(2, vcrName);
		verify(statement).setString(3, vcrHead);
		verify(statement).setString(4, vcrReviewer);
		verify(statement).executeUpdate();
		verify(statement).close();

	}

	@Test
	public void whenReadByPrimaryKeyExistingRowThenAllRight() throws Exception {
		ResultSet resultSet = mock(ResultSet.class);
		when(connection.prepareStatement(VcrDao.SQL_READ_BY_PK)).thenReturn(statement);
		when(statement.executeQuery()).thenReturn(resultSet);

		adjustResultSet(resultSet, true);

		VCR entity = dao.read(vcrName);

		verifyQuery(VcrDao.SQL_READ_BY_PK, resultSet, 1, true);
		verify(resultSet).close();
		verify(statement).close();

		assertEquals(defaultEntity, entity);
	}

	@Test
	public void whenReadAllWithMultipleRowsThenAllRight() throws Exception {
		ResultSet resultSet = mock(ResultSet.class);
		when(connection.prepareStatement(VcrDao.SQL_READ_ALL)).thenReturn(statement);
		when(statement.executeQuery()).thenReturn(resultSet);

		adjustResultSet(resultSet, false);

		List<VCR> entities = dao.readAll();

		verifyQuery(VcrDao.SQL_READ_ALL, resultSet, 2, true);
		verify(resultSet).close();
		verify(statement).close();

		VCR actual1 = entities.get(0);
		VCR actual2 = entities.get(1);
		assertEquals(defaultEntity, actual1);
		assertEquals(additionalEntity, actual2);

	}

	@Test
	public void whenUpdateByPkOneRowThenAllRight() throws Exception {
		when(connection.prepareStatement(VcrDao.SQL_UPDATE)).thenReturn(statement);

		dao.update(vcrName, additionalEntity);

		verify(connection).prepareStatement(VcrDao.SQL_UPDATE);
		verify(statement).setString(1, vcrName);
		verify(statement).setString(2, vcrHead);
		verify(statement).setString(3, vcrReviewer);
		verify(statement).setString(4, vcrName);
		verify(statement).executeUpdate();
		verify(statement).close();

	}

	@Test
	public void whenDeleteByPkThenAllStepsPerformed() throws Exception {
		when(connection.prepareStatement(VcrDao.SQL_DELETE_BY_PK)).thenReturn(statement);

		dao.delete(vcrName);

		verify(connection).prepareStatement(VcrDao.SQL_DELETE_BY_PK);
		verify(statement).setString(1, vcrName);
		verify(statement).executeUpdate();
		verify(statement).close();

	}

	@Test
	public void whenDeleteAllThenAllActionsPerformed() throws Exception {
		when(connection.prepareStatement(VcrDao.SQL_DELETE_ALL)).thenReturn(statement);

		dao.deleteAll();

		verify(connection).prepareStatement(VcrDao.SQL_DELETE_ALL);
		verify(statement).executeUpdate();
		verify(statement).close();

	}

	@Test
	public void whenReadByFkOneRowThenAllActionsPerformed() throws Exception {
		ResultSet resultSet = mock(ResultSet.class);
		when(connection.prepareStatement(VcrDao.SQL_READ_BY_FK)).thenReturn(statement);
		when(statement.executeQuery()).thenReturn(resultSet);

		adjustResultSet(resultSet, true);

		VCR entity = dao.readByForeignKey(studentId).get(0);

		verifyQuery(VcrDao.SQL_READ_BY_FK, resultSet, 1, false);
		verify(resultSet).close();
		verify(statement).close();

		assertEquals(defaultEntity, entity);

	}

	@Test(expected = NoSuchEntityException.class)
	public void whenReadByPrimaryKeyNonExistingRowThenException() throws Exception {
		ResultSet resultSet = mock(ResultSet.class);
		when(connection.prepareStatement(VcrDao.SQL_READ_BY_PK)).thenReturn(statement);
		when(statement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(false);

		VCR entity = dao.read(vcrName);

		verify(connection).prepareStatement(VcrDao.SQL_READ_BY_PK);
		verify(statement).setString(1, vcrName);
		verify(statement).executeQuery();
		verify(resultSet).next();
		verify(resultSet).close();
		verify(statement).close();

		assertNull(entity);
	}

	@Test(expected = NoSuchEntityException.class)
	public void whenReadByForeignKeyNonExistingRowThenException() throws Exception {
		ResultSet resultSet = mock(ResultSet.class);
		when(connection.prepareStatement(VcrDao.SQL_READ_BY_FK)).thenReturn(statement);
		when(statement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(false);

		List<VCR> entities = dao.readByForeignKey(studentId);

		verify(connection).prepareStatement(VcrDao.SQL_READ_BY_FK);
		verify(statement).setInt(1, studentId);
		verify(statement).executeQuery();
		verify(resultSet).next();
		verify(resultSet).close();
		verify(statement).close();

		assertTrue(entities.isEmpty());
	}

	@Test(expected = DaoSystemException.class)
	public void whenCreateThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
		when(connection.prepareStatement(VcrDao.SQL_CREATE)).thenReturn(statement);
		when(statement.executeUpdate()).thenThrow(SQLException.class);

		dao.create(defaultEntity);
	}

	@Test(expected = DaoSystemException.class)
	public void whenReadByPkThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
		when(connection.prepareStatement(VcrDao.SQL_READ_BY_PK)).thenReturn(statement);
		when(statement.executeQuery()).thenThrow(SQLException.class);

		dao.read(vcrName);
	}

	@Test(expected = DaoSystemException.class)
	public void whenReadByFkThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
		when(connection.prepareStatement(VcrDao.SQL_READ_BY_FK)).thenReturn(statement);
		when(statement.executeQuery()).thenThrow(SQLException.class);

		dao.readByForeignKey(studentId);
	}

	@Test(expected = DaoSystemException.class)
	public void whenReadAllThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
		when(connection.prepareStatement(VcrDao.SQL_READ_ALL)).thenReturn(statement);
		when(statement.executeQuery()).thenThrow(SQLException.class);

		dao.readAll();
	}

	@Test(expected = DaoSystemException.class)
	public void whenUpdateThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
		when(connection.prepareStatement(VcrDao.SQL_UPDATE)).thenReturn(statement);
		when(statement.executeUpdate()).thenThrow(SQLException.class);

		dao.update(vcrName, defaultEntity);
	}

	@Test(expected = DaoSystemException.class)
	public void whenDeleteByPkThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
		when(connection.prepareStatement(VcrDao.SQL_DELETE_BY_PK)).thenReturn(statement);
		when(statement.executeUpdate()).thenThrow(SQLException.class);

		dao.delete(vcrName);
	}

	@Test(expected = DaoSystemException.class)
	public void whenDeleteAllThrowsSqlExceptionThenThrownDaoSystemException() throws Exception {
		when(connection.prepareStatement(VcrDao.SQL_DELETE_ALL)).thenReturn(statement);
		when(statement.executeUpdate()).thenThrow(SQLException.class);

		dao.deleteAll();
	}

	@Test
	public void whenReadByPkStatementFailsThenAllResourcesClosed() throws Exception {
		ResultSet resultSet = mock(ResultSet.class);
		when(connection.prepareStatement(VcrDao.SQL_READ_BY_PK)).thenReturn(statement);
		when(statement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenThrow(SQLException.class);

		try {
			dao.read(vcrName);
		} catch (DaoSystemException e) {}

		verify(resultSet).close();
		verify(statement).close();
	}

	@Test
	public void whenReadAllFailsThenAllResourcesClosed() throws Exception {
		ResultSet resultSet = mock(ResultSet.class);
		when(connection.prepareStatement(VcrDao.SQL_READ_ALL)).thenReturn(statement);
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
		when(connection.prepareStatement(VcrDao.SQL_READ_BY_FK)).thenReturn(statement);
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
		when(connection.prepareStatement(VcrDao.SQL_CREATE)).thenReturn(statement);
		when(statement.executeUpdate()).thenThrow(SQLException.class);

		try {
			dao.create(defaultEntity);
		} catch (DaoSystemException e) {}

		verify(statement).close();
	}

	@Test
	public void whenUpdateStatementFailsThenAllResourcesClosed() throws Exception {
		when(connection.prepareStatement(VcrDao.SQL_UPDATE)).thenReturn(statement);
		when(statement.executeUpdate()).thenThrow(SQLException.class);

		try {
			dao.update(vcrName, defaultEntity);
		} catch (DaoSystemException e) {}

		verify(statement).close();
	}

	@Test
	public void whenDeleteByPkStatementFailsThenAllResourcesClosed() throws Exception {
		when(connection.prepareStatement(VcrDao.SQL_DELETE_BY_PK)).thenReturn(statement);
		when(statement.executeUpdate()).thenThrow(SQLException.class);

		try {
			dao.delete(vcrName);
		} catch (DaoSystemException e) {}

		verify(statement).close();
	}

	@Test
	public void whenDeleteAllFailsThenAllResourcesClosed() throws Exception {
		when(connection.prepareStatement(VcrDao.SQL_DELETE_ALL)).thenReturn(statement);
		when(statement.executeUpdate()).thenThrow(SQLException.class);

		try {
			dao.deleteAll();
		} catch (DaoSystemException e) {}

		verify(statement).close();
	}



	private void adjustResultSet(ResultSet resultSet, boolean oneTime) throws Exception {

		if(oneTime) {
			when(resultSet.next())
					.thenReturn(true)
					.thenReturn(false);

			when(resultSet.getInt(VcrDao.TABLE_STUDENT_ID)).thenReturn(1);
		} else {    // pk many(at least 2)
			when(resultSet.next())
					.thenReturn(true)
					.thenReturn(true)
					.thenReturn(false);

			when(resultSet.getInt(VcrDao.TABLE_STUDENT_ID))
					.thenReturn(1)
					.thenReturn(2); // second id, second entity
		}

		when(resultSet.getString(VcrDao.TABLE_VCR_NAME)).thenReturn(vcrName);
		when(resultSet.getString(VcrDao.TABLE_VCR_HEAD)).thenReturn(vcrHead);
		when(resultSet.getString(VcrDao.TABLE_VCR_REVIEWER)).thenReturn(vcrReviewer);

	}

	private void verifyQuery(String sql, ResultSet resultSet, int times, boolean byPrimaryKey) throws Exception {
		verify(connection).prepareStatement(sql);

		// if by pk
		if(times == 1 && byPrimaryKey) {
			verify(statement).setString(1, vcrName);
		} else if(times == 1 && !byPrimaryKey) {
			verify(statement).setInt(1, studentId);
		}
		verify(statement).executeQuery();

		int resultSetNextTimes = times + 1; // because there would be last, that returns false
		verify(resultSet, times(resultSetNextTimes)).next();
		verify(resultSet, times(times)).getInt(VcrDao.TABLE_STUDENT_ID);
		verify(resultSet, times(times)).getString(VcrDao.TABLE_VCR_NAME);
		verify(resultSet, times(times)).getString(VcrDao.TABLE_VCR_HEAD);
		verify(resultSet, times(times)).getString(VcrDao.TABLE_VCR_REVIEWER);

	}



}






















