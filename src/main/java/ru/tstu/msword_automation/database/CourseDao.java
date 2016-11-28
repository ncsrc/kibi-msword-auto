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
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Courses(student_id, course_code, course_name, course_profile, qualification) VALUES(?, ?, ?, ?, ?)");
		statement.setInt(1, dataset.getStudentId());
		statement.setString(2, dataset.getCode());
		statement.setString(3, dataset.getName());
		statement.setString(4, dataset.getProfile());
		statement.setString(5, dataset.getQualification());

		return statement;
	}

	@Override
	protected PreparedStatement getReadingByPkStatement(Connection connection, String pk) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("SELECT student_id, course_code, course_name, course_profile, qualification FROM Courses WHERE course_code = ?");
		statement.setString(1, pk);
		return statement;
	}

	@Override
	protected List<Course> parseResultSet(ResultSet resultSet) throws SQLException
	{
		List<Course> courses = new ArrayList<>();
		while(resultSet.next()){
			courses.add(new Course(resultSet.getString("course_code"), resultSet.getInt("student_id"),
					resultSet.getString("qualification"), resultSet.getString("course_name"), resultSet.getString("course_profile")));
		}

		return courses;
	}

	@Override
	protected PreparedStatement getReadingAllStatement(Connection connection) throws SQLException
	{
		return connection.prepareStatement("SELECT student_id, course_code, course_name, course_profile, qualification FROM Courses");
	}

	@Override
	protected PreparedStatement getUpdateStatement(Connection connection, Course dataset, String pk) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("UPDATE Courses SET student_id = ?, course_code = ?, course_name = ?, course_profile = ?, qualification = ? WHERE course_code = ?");
		statement.setInt(1, dataset.getStudentId());
		statement.setString(2, dataset.getCode());
		statement.setString(3, dataset.getName());
		statement.setString(4, dataset.getProfile());
		statement.setString(5, dataset.getQualification());
		statement.setString(6, pk);

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


























