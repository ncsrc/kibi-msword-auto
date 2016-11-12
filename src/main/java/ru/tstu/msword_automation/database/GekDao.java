package ru.tstu.msword_automation.database;


import ru.tstu.msword_automation.database.datasets.GEK;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

class GekDao extends AbstractDao<GEK, String>
{

	GekDao(ConnectionPool pool)
	{
		super(pool);
	}


	@Override
	protected PreparedStatement getCreationStatement(Connection connection, GEK dataset) throws SQLException
	{


		return null;
	}

	@Override
	protected PreparedStatement getReadingByPkStatement(Connection connection, String pk) throws SQLException{
		return null;
	}

	@Override
	protected PreparedStatement getReadingAllStatement(Connection connection) throws SQLException{
		return null;
	}

	@Override
	protected PreparedStatement getUpdateStatement(Connection connection, GEK dataset, String pk) throws SQLException{
		return null;
	}

	@Override
	protected PreparedStatement getDeleteByPkStatement(Connection connection, String pk) throws SQLException{
		return null;
	}

	@Override
	protected PreparedStatement getDeleteAllStatement(Connection connection) throws SQLException{
		return null;
	}

	@Override
	protected List<GEK> parseResultSet(ResultSet resultSet) throws SQLException{
		return null;
	}
	

}
