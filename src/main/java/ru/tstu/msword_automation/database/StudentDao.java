package ru.tstu.msword_automation.database;


import ru.tstu.msword_automation.database.datasets.Student;

import java.sql.SQLException;
import java.util.List;

class StudentDao implements Dao<Student, Integer>
{
	private final ConnectionPool connectionPool;


	StudentDao(ConnectionPool pool)
	{
		this.connectionPool = pool;
	}

	@Override
	public void create(Student dataset) throws SQLException
	{

	}

	@Override
	public Student read(Integer pk) throws SQLException
	{
		return null;
	}

	@Override
	public List<Student> readAll() throws SQLException
	{
		return null;
	}

	@Override
	public void update(Integer pk, Student dataset) throws SQLException
	{

	}

	@Override
	public void delete(Integer pk) throws SQLException
	{

	}

	@Override
	public void deleteAll() throws SQLException
	{

	}

}
