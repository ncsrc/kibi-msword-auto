package ru.tstu.msword_auto.dao;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

class ConnectionPool implements AutoCloseable
{
	private static final int DEFAULT_MIN_NUMBER_OF_CONNECTIONS = 3;
	private static final int DEFAULT_MAX_NUMBER_OF_CONNECTIONS = 10;
	private static final int DELAY_TIME = 10_000; // 10 seconds in ms	// TODO lower delay
	private String dbUser;
	private String dbPassword;
	private String jdbc_url;
	private int maxConnections;
	private int numberOfOpenedConnections;
	private List<Connection> availableConnections;
//	private List<Connection> occupiedConnections;	// TODO move here connections and return back if delay time exceeded(3-5s)
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
		// getting user-pass-etc from property file
		Properties dbInfo = new Properties();
		try{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream in = classLoader.getResourceAsStream("db.properties");

			// TODO add exceptions if some credentials not found or incorrect
			dbInfo.load(in);
			String dbName = dbInfo.getProperty("db.name");
			this.dbUser = dbInfo.getProperty("db.user");

			// sets empty password
			this.dbPassword = dbInfo.getProperty("db.password");
			if(dbPassword == null){
				dbPassword = "";
			}

			String dbHost = dbInfo.getProperty("db.host");
			String dbPort = dbInfo.getProperty("db.port");
			this.jdbc_url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?useSSL=false&characterEncoding=utf8";
			in.close();

			//			"jdbc:mysql://127.0.0.1:3306/" + DB_NAME + "?useSSL=false&characterEncoding=utf8";
		}catch(java.io.IOException e){	// TODO throw high level exception with message about occurred error. handle separate file not found
			e.printStackTrace();
			System.exit(1);
		}

		// registering initial connection
		this.maxConnections = max;
		this.availableConnections = new ArrayList<>(max);
		for(int i = 0; i < initial; i++){
			this.availableConnections.add(this.openNewConnection());
		}
		this.numberOfOpenedConnections = initial;
//		this.occupiedConnections = new ArrayList<>(max); // TODO remove ?
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


	// TODO catch exception and throw another with message
	private Connection openNewConnection() throws SQLException
	{
		return DriverManager.getConnection(jdbc_url, dbUser, dbPassword);
	}

	private boolean availableConnectionExists()
	{
		return !this.availableConnections.isEmpty();	// returns false if empty, true otherwise
	}

}






















