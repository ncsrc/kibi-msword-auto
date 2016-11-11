package ru.tstu.msword_automation.database;


import ru.tstu.msword_automation.database.datasets.GEK;

import java.sql.SQLException;
import java.util.List;

class GekDao implements Dao<GEK, String>
{
	private final ConnectionPool connectionPool;


	GekDao(ConnectionPool pool)
	{
		this.connectionPool = pool;
	}

	@Override
	public void create(GEK dataset) throws SQLException
	{

	}

	@Override
	public GEK read(String pk) throws SQLException
	{
		return null;
	}

	@Override
	public List<GEK> readAll() throws SQLException
	{
		return null;
	}

	@Override
	public void update(String pk, GEK dataset) throws SQLException
	{

	}

	@Override
	public void delete(String pk) throws SQLException
	{

	}

	@Override
	public void deleteAll() throws SQLException
	{

	}


}
