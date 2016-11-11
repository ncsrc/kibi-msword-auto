package ru.tstu.msword_automation.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

abstract class AbstractDao<T, K> implements Dao<T, K>
{
	private ConnectionPool connectionPool;


	AbstractDao(ConnectionPool connectionPool)
	{
		this.connectionPool = connectionPool;
	}


	@Override
	public void create(T dataset) throws SQLException
	{
		Connection connection = connectionPool.getConnection();
		PreparedStatement statement = this.getCreationStatement(connection, dataset);
		statement.executeUpdate();
		statement.close();
		connectionPool.putBackConnection(connection);
	}

	protected abstract PreparedStatement getCreationStatement(Connection connection, T dataset) throws SQLException;


	@Override
	public T read(K pk) throws SQLException
	{
		Connection connection = connectionPool.getConnection();
		PreparedStatement statement = this.getReadingByPkStatement(connection, pk);
		ResultSet resultSet = statement.executeQuery();
		List<T> results = this.parseResultSet(resultSet);
		resultSet.close();
		statement.close();
		connectionPool.putBackConnection(connection);

		if(results.isEmpty()){
			throw new SQLException("Such primary key value does not exists in table");
		}
		return results.get(0);
	}

	protected abstract PreparedStatement getReadingByPkStatement(Connection connection, K pk) throws SQLException;

	protected abstract List<T> parseResultSet(ResultSet resultSet) throws SQLException;


	@Override
	public List<T> readAll() throws SQLException
	{
		Connection connection = connectionPool.getConnection();
		PreparedStatement statement = this.getReadingAllStatement(connection);
		ResultSet resultSet = statement.executeQuery();
		List<T> results = this.parseResultSet(resultSet);
		resultSet.close();
		statement.close();
		connectionPool.putBackConnection(connection);
		return results;
	}

	protected abstract PreparedStatement getReadingAllStatement(Connection connection) throws SQLException;


	@Override
	public void update(K pk, T dataset) throws SQLException
	{
		Connection connection = connectionPool.getConnection();
		PreparedStatement statement = this.getUpdateStatement(connection, dataset, pk);
		statement.executeUpdate();
		statement.close();
		connectionPool.putBackConnection(connection);
	}

	protected abstract PreparedStatement getUpdateStatement(Connection connection, T dataset, K pk) throws SQLException;


	@Override
	public void delete(K pk) throws SQLException
	{
		Connection connection = connectionPool.getConnection();
		PreparedStatement statement = this.getDeleteByPkStatement(connection, pk);
		statement.executeUpdate();
		statement.close();
		connectionPool.putBackConnection(connection);
	}

	protected abstract PreparedStatement getDeleteByPkStatement(Connection connection, K pk) throws SQLException;


	@Override
	public void deleteAll() throws SQLException
	{
		Connection connection = connectionPool.getConnection();
		PreparedStatement statement = this.getDeleteAllStatement(connection);
		statement.executeUpdate();
		statement.close();
		connectionPool.putBackConnection(connection);
	}

	protected abstract PreparedStatement getDeleteAllStatement(Connection connection) throws SQLException;

}
