package ru.tstu.msword_automation.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class ConnectionPool implements AutoCloseable
{
	private static final int DEFAULT_MIN_NUMBER_OF_CONNECTIONS = 3;
	private static final int DEFAULT_MAX_NUMBER_OF_CONNECTIONS = 10;
	private static final int DELAY_TIME = 10_000; // 10 seconds in ms
	private static final String DB_NAME = "test";
	private static final String USER = "test";
	private static final String PASSWORD = "AD127flslbl969**_";
	private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/" + DB_NAME + "?useSSL=false&characterEncoding=utf8";
	private int maxConnections;
	private int numberOfOpenedConnections;
	private List<Connection> availableConnections;
//	private List<Connection> occupiedConnections;
	private boolean closed = false;


	ConnectionPool() throws SQLException
	{
		this(DEFAULT_MIN_NUMBER_OF_CONNECTIONS, DEFAULT_MAX_NUMBER_OF_CONNECTIONS);
	}

	ConnectionPool(int max) throws SQLException
	{
		this(DEFAULT_MIN_NUMBER_OF_CONNECTIONS, max);
	}

	ConnectionPool(int initial, int max) throws SQLException
	{
		this.maxConnections = max;
		this.availableConnections = new ArrayList<>(max);
		for(int i = 0; i < initial; i++){
			this.availableConnections.add(this.openNewConnection());
		}
		this.numberOfOpenedConnections = initial;
//		this.occupiedConnections = new ArrayList<>(max);
	}


	public synchronized Connection getConnection() throws SQLException
	{
		if(this.closed){
			throw new SQLException("Connection Pool is closed");
		}

		// Enters the loop if available connection isn't present. Makes new connection if threshold hasn't met yet
		// If loops for 10 seconds, throws exceptions, because that's too long delay

		long startTime = System.currentTimeMillis();
		while(!availableConnectionExists()){
			if(this.numberOfOpenedConnections < this.maxConnections){
				this.availableConnections.add(this.openNewConnection());
				this.numberOfOpenedConnections++;
			}

			if((System.currentTimeMillis() - startTime) == DELAY_TIME){
				throw new SQLException("No available connections");
			}
		}

		int indexOfLast = availableConnections.size()-1;
		Connection connection = availableConnections.get(indexOfLast);
		availableConnections.remove(indexOfLast);
		return connection;
	}

	public synchronized void putBackConnection(Connection connection)
	{
		this.availableConnections.add(connection);
	}


	@Override
	public synchronized void close() throws SQLException
	{
		for(Connection connection : this.availableConnections){
			connection.close();
		}
		this.availableConnections.clear();
		this.closed = true;
	}


	private Connection openNewConnection() throws SQLException
	{
		return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
	}

	private boolean availableConnectionExists()
	{
		return !this.availableConnections.isEmpty();	// returns false if empty, true otherwise
	}

}






















