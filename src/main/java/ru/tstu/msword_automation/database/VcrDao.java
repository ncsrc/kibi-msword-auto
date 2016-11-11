package ru.tstu.msword_automation.database;


import ru.tstu.msword_automation.database.datasets.VCR;

import java.sql.SQLException;
import java.util.List;

class VcrDao implements Dao<VCR, String>
{
	private final ConnectionPool connectionPool;

	VcrDao(ConnectionPool connectionPool)
	{
		this.connectionPool = connectionPool;
	}

	@Override
	public void create(VCR dataset) throws SQLException
	{

	}

	@Override
	public VCR read(String pk) throws SQLException
	{
		return null;
	}

	@Override
	public List<VCR> readAll() throws SQLException
	{
		return null;
	}

	@Override
	public void update(String pk, VCR dataset) throws SQLException
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
