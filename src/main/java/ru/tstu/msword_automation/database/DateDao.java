package ru.tstu.msword_automation.database;


import ru.tstu.msword_automation.database.datasets.Date;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class DateDao extends AbstractDao<Date, Integer>
{

	DateDao(ConnectionPool pool)
	{
		super(pool);
	}

	@Override
	protected PreparedStatement getCreationStatement(Connection connection, Date dataset) throws SQLException // expected only 1 row in Date table, so throws exception, if table is not empty
	{
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Date VALUES(?, ?, ?)");
		statement.setInt(1, dataset.getDay());
		statement.setString(2, dataset.getMonth());
		statement.setInt(3, dataset.getYear());
		return statement;
	}


	@Override
	protected PreparedStatement getReadingByPkStatement(Connection connection, Integer pk) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("SELECT day, month, year FROM Date WHERE year = ?");
		statement.setInt(1, pk);
		return statement;
	}

	@Override
	protected PreparedStatement getReadingAllStatement(Connection connection) throws SQLException
	{
		return connection.prepareStatement("SELECT day, month, year FROM Date");
	}

	@Override
	protected PreparedStatement getUpdateStatement(Connection connection, Date dataset, Integer pk) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("UPDATE Date SET day = ?, month = ?, year = ?");
		statement.setInt(1, dataset.getDay());
		statement.setString(2, dataset.getMonth());
		statement.setInt(3, dataset.getYear());
		return statement;
	}

	@Override
	protected PreparedStatement getDeleteByPkStatement(Connection connection, Integer pk) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("DELETE FROM Date WHERE year = ?");
		statement.setInt(1, pk);
		return statement;
	}

	@Override
	protected PreparedStatement getDeleteAllStatement(Connection connection) throws SQLException
	{
		return connection.prepareStatement("DELETE FROM Date");
	}

	@Override
	protected List<Date> parseResultSet(ResultSet resultSet) throws SQLException
	{
		List<Date> dates = new ArrayList<>();
		while(resultSet.next()){
			dates.add(new Date(resultSet.getInt("day"), resultSet.getString("month"), resultSet.getInt("year")));
		}
		return dates;
	}


}























