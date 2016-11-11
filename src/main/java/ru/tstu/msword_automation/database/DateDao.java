package ru.tstu.msword_automation.database;


import ru.tstu.msword_automation.database.datasets.Date;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class DateDao implements Dao<Date, Integer>
{
	private final ConnectionPool connectionPool;


	DateDao(ConnectionPool pool)
	{
		this.connectionPool = pool;
	}

	@Override
	public void create(Date dataset) throws SQLException	// expected only 1 row in Date table, so throws exception, if table is not empty
	{
		Connection connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Date VALUES(?, ?, ?)");
		statement.setInt(1, dataset.getDay());
		statement.setString(2, dataset.getMonth());
		statement.setInt(3, dataset.getYear());
		statement.executeUpdate();
		statement.close();
		connectionPool.putBackConnection(connection);
	}

	@Override
	public Date read(Integer pk) throws SQLException
	{
		Connection connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement("SELECT day, month, year FROM Date WHERE year = ?");
		statement.setInt(1, pk);
		ResultSet result = statement.executeQuery();
		Date date = null;
		if(result.isBeforeFirst()){
			result.next();
			date = new Date(result.getInt("day"), result.getString("month"), result.getInt("year"));
		}
		result.close();
		statement.close();
		connectionPool.putBackConnection(connection);
		return date;
	}

	@Override
	public List<Date> readAll() throws SQLException
	{
		Connection connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement("SELECT day, month, year FROM Date");
		ResultSet result = statement.executeQuery();
		List<Date> dates = new ArrayList<>();
		while(result.next()){
			dates.add(new Date(result.getInt("day"), result.getString("month"), result.getInt("year")));
		}

		result.close();
		statement.close();
		connectionPool.putBackConnection(connection);
		return dates;
	}

	@Override
	public void update(Integer pk, Date dataset) throws SQLException
	{
		Connection connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement("UPDATE Date SET day = ?, month = ?, year = ?");
		statement.setInt(1, dataset.getDay());
		statement.setString(2, dataset.getMonth());
		statement.setInt(3, dataset.getYear());
		statement.executeUpdate();
		statement.close();
		connectionPool.putBackConnection(connection);
	}

	@Override
	public void delete(Integer pk) throws SQLException
	{
		Connection connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement("DELETE FROM Date WHERE year = ?");
		statement.setInt(1, pk);
		statement.executeUpdate();
		statement.close();
		connectionPool.putBackConnection(connection);
	}

	@Override
	public void deleteAll() throws SQLException
	{
		Connection connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement("DELETE FROM Date");
		statement.executeUpdate();
		statement.close();
		connectionPool.putBackConnection(connection);
	}


}























