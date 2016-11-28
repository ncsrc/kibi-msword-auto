package ru.tstu.msword_automation.database;


import ru.tstu.msword_automation.database.datasets.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDao extends AbstractDao<Student, Integer>
{


	StudentDao(ConnectionPool pool)
	{
		super(pool);
	}


	@Override
	protected PreparedStatement getCreationStatement(Connection connection, Student dataset) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Students VALUES(?, ?, ?, ?)");
		statement.setInt(1, dataset.getId());
		statement.setString(2, dataset.getFirstName());
		statement.setString(3, dataset.getLastName());
		statement.setString(4, dataset.getMiddleName());

		return statement;
	}

	@Override
	protected PreparedStatement getReadingByPkStatement(Connection connection, Integer pk) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("SELECT student_id, first_name, last_name, middle_name FROM Students WHERE student_id = ?");
		statement.setInt(1, pk);

		return statement;
	}

	@Override
	protected PreparedStatement getReadingAllStatement(Connection connection) throws SQLException
	{
		return connection.prepareStatement("SELECT student_id, first_name, last_name, middle_name FROM Students");
	}

	@Override
	protected PreparedStatement getUpdateStatement(Connection connection, Student dataset, Integer pk) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("UPDATE Students SET first_name = ?, last_name = ?, middle_name = ? WHERE student_id = ?");
		statement.setString(1, dataset.getFirstName());
		statement.setString(2, dataset.getLastName());
		statement.setString(3, dataset.getMiddleName());
		statement.setInt(4, pk);

		return statement;
	}

	@Override
	protected PreparedStatement getDeleteByPkStatement(Connection connection, Integer pk) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("DELETE FROM Students WHERE student_id = ?");
		statement.setInt(1, pk);

		return statement;
	}

	@Override
	protected PreparedStatement getDeleteAllStatement(Connection connection) throws SQLException
	{
		return connection.prepareStatement("DELETE FROM Students");
	}

	@Override
	protected List<Student> parseResultSet(ResultSet resultSet) throws SQLException
	{
		List<Student> list = new ArrayList<>();
		while(resultSet.next()){
			list.add(new Student(resultSet.getInt("student_id"), resultSet.getString("first_name"), resultSet.getString("last_name"),
					resultSet.getString("middle_name")));
		}

		return list;
	}
}




















