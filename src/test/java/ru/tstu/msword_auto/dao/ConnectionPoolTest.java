package ru.tstu.msword_auto.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.tstu.msword_auto.dao.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

@Ignore
public class ConnectionPoolTest
{
	private ConnectionPool pool0;


	@Before
	public void init() throws Exception
	{
		this.pool0 = new ConnectionPool();
	}

	@After
	public void after() throws Exception
	{
		pool0.close();
	}


	@Test
	public void assertConnectionPoolConstructorsAreWorkingAndMakeDifferentObjects() throws Exception
	{
		ConnectionPool pool0 = new ConnectionPool();
		assertNotNull(pool0);

		ConnectionPool pool1 = new ConnectionPool(15);
		assertNotNull(pool1);
		assertNotEquals(pool0, pool1);

		ConnectionPool pool2 = new ConnectionPool(1, 20);
		assertNotNull(pool2);
		assertNotEquals(pool2, pool0);
		assertNotEquals(pool2, pool1);

		pool0.close();
		pool1.close();
		pool2.close();
	}

	@Test
	public void assertConnectionsFromDifferentPoolsAreNotTheSame() throws Exception
	{
		ConnectionPool pool1 = new ConnectionPool();
		Connection c0 = pool0.getConnection();
		Connection c1 = pool1.getConnection();

		assertNotNull(c0);
		assertNotNull(c1);

		assertNotEquals(c0, c1);
		pool1.close();
	}

	@Test
	public void gettingConnectionBelowInitialSizeOfConnectionPool() throws Exception
	{
		Connection c0 = pool0.getConnection();
		Connection c1 = pool0.getConnection();
		Connection c2 = pool0.getConnection();

		assertNotNull(c0);
		assertNotNull(c1);
		assertNotNull(c2);

		assertNotEquals(c0, c1);
	}

	@Test
	public void gettingConnectionAboveInitialSizeOfConnectionPool() throws Exception
	{
		for(int i = 0; i < 10; i++){
			assertNotNull(pool0.getConnection());
		}
	}

	@Test(expected = SQLException.class)
	public void exceptionThrownWhenAllConnectionsOccupiedAndNotPutBack() throws Exception
	{
		while(true){
			pool0.getConnection();
		}
	}

	@Test
	public void assertThatConnectionSuccessfullyPutBackToPool() throws Exception
	{
		Connection c0 = pool0.getConnection();
		pool0.putBackConnection(c0);
		Connection c1 = pool0.getConnection();

		assertNotNull(c0);
		assertEquals(c0, c1);
	}

	@Test(expected = SQLException.class)
	public void CannotGetConnectionWhenPoolClosed() throws Exception
	{
		pool0.close();
		pool0.getConnection();
	}

}





















