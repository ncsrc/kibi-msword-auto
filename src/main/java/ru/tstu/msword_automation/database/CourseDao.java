package ru.tstu.msword_automation.database;


import ru.tstu.msword_automation.database.datasets.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class CourseDao implements Dao<Course, String>
{
	private final ConnectionPool connectionPool;


	CourseDao(ConnectionPool pool)
	{
		this.connectionPool = pool;
	}

	@Override
	public void create(Course dataset) throws SQLException
	{
		Connection connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Courses(course_code, course_name, course_specialization) VALUES(?, ?, ?)");
		statement.setString(1, dataset.getCode());
		statement.setString(2, dataset.getName());
		statement.setString(3, dataset.getSpec());
		statement.executeUpdate();
		statement.close();
		connectionPool.putBackConnection(connection);
	}

	// returns null if row not found
	@Override
	public Course read(String pk) throws SQLException
	{
		Connection connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement("SELECT course_code, course_name, course_specialization FROM Courses WHERE course_code = ?");

		statement.setString(1, pk);
		ResultSet result = statement.executeQuery();
		Course course = null;
		if(result.isBeforeFirst()){
			result.next();
			course = new Course(result.getString("course_code"),
					result.getString("course_name"), result.getString("course_specialization"));
		}

		result.close();
		statement.close();
		connectionPool.putBackConnection(connection);

		return course;
	}

	// returns empty list if no results
	@Override
	public List<Course> readAll() throws SQLException
	{
		Connection connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement("SELECT course_code, course_name, course_specialization FROM Courses");
		ResultSet result = statement.executeQuery();

		List<Course> courses = new ArrayList<>();
		while(result.next()){
			courses.add(new Course(result.getString("course_code"), result.getString("course_name"),
					result.getString("course_specialization")));
		}

		statement.close();
		connectionPool.putBackConnection(connection);
		return courses;
	}

	@Override
	public void update(String pk, Course dataset) throws SQLException
	{
		Connection connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement("UPDATE Courses SET course_code = ?, course_name = ?, course_specialization = ? WHERE course_code = ?");
		statement.setString(1, dataset.getCode());
		statement.setString(2, dataset.getName());
		statement.setString(3, dataset.getSpec());
		statement.setString(4, pk);
		statement.executeUpdate();
		statement.close();
		connectionPool.putBackConnection(connection);
	}

	@Override
	public void delete(String pk) throws SQLException
	{
		Connection connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement("DELETE FROM Courses WHERE course_code = ?");
		statement.setString(1, pk);
		statement.executeUpdate();
		statement.close();
		connectionPool.putBackConnection(connection);
	}

	@Override
	public void deleteAll() throws SQLException
	{
		Connection connection = connectionPool.getConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate("DELETE FROM Courses");
		statement.close();
		connectionPool.putBackConnection(connection);
	}


}


























