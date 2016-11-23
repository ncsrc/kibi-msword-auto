package ru.tstu.msword_automation.database;


import ru.tstu.msword_automation.database.datasets.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDao extends AbstractDao<Course, String>
{

	CourseDao(ConnectionPool pool)
	{
		super(pool);
	}


	@Override
	protected PreparedStatement getCreationStatement(Connection connection, Course dataset) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Courses(course_code, course_name, course_specialization) VALUES(?, ?, ?)");
		statement.setString(1, dataset.getCode());
		statement.setString(2, dataset.getName());
		statement.setString(3, dataset.getSpec());
		return statement;
	}

	@Override
	protected PreparedStatement getReadingByPkStatement(Connection connection, String pk) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("SELECT course_code, course_name, course_specialization FROM Courses WHERE course_code = ?");
		statement.setString(1, pk);
		return statement;
	}

	@Override
	protected List<Course> parseResultSet(ResultSet resultSet) throws SQLException
	{
		List<Course> courses = new ArrayList<>();
		while(resultSet.next()){
			courses.add(new Course(resultSet.getString("course_code"), resultSet.getString("course_name"),
					resultSet.getString("course_specialization")));
		}
		return courses;
	}

	@Override
	protected PreparedStatement getReadingAllStatement(Connection connection) throws SQLException
	{
		return connection.prepareStatement("SELECT course_code, course_name, course_specialization FROM Courses");
	}

	@Override
	protected PreparedStatement getUpdateStatement(Connection connection, Course dataset, String pk) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("UPDATE Courses SET course_code = ?, course_name = ?, course_specialization = ? WHERE course_code = ?");
		statement.setString(1, dataset.getCode());
		statement.setString(2, dataset.getName());
		statement.setString(3, dataset.getSpec());
		statement.setString(4, pk);
		return statement;
	}

	@Override
	protected PreparedStatement getDeleteByPkStatement(Connection connection, String pk) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("DELETE FROM Courses WHERE course_code = ?");
		statement.setString(1, pk);
		return statement;
	}

	@Override
	protected PreparedStatement getDeleteAllStatement(Connection connection) throws SQLException
	{
		return connection.prepareStatement("DELETE FROM Courses");
	}

}


























