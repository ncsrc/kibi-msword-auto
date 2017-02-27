package ru.tstu.msword_auto.dao;

import ru.tstu.msword_auto.entity.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// TODO remove Connection from arguments, since protected

public class CourseDao extends AbstractDao<Course, Integer> implements ForeignKeyReadableDao<Course, Integer> {

	public CourseDao() {
		super();
	}


	@Override
	protected PreparedStatement getCreationStatement(Connection connection, Course dataset) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Courses(student_id, course_code, course_name, course_profile, qualification) VALUES(?, ?, ?, ?, ?)");
		statement.setInt(1, dataset.getStudentId());
		statement.setString(2, dataset.getCode());
		statement.setString(3, dataset.getName());
		statement.setString(4, dataset.getProfile());
		statement.setString(5, dataset.getQualification());

		return statement;
	}

	@Override	// TODO changed pk, test
	protected PreparedStatement getReadingByPkStatement(Connection connection, Integer pk) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT student_id, course_code, course_name, course_profile, qualification FROM Courses WHERE student_id = ?");
		statement.setInt(1, pk);
		return statement;
	}

	@Override
	protected List<Course> parseResultSet(ResultSet resultSet) throws SQLException {
		List<Course> courses = new ArrayList<>();
		while(resultSet.next()){
			courses.add(new Course(resultSet.getString("course_code"), resultSet.getInt("student_id"),
					resultSet.getString("qualification"), resultSet.getString("course_name"), resultSet.getString("course_profile")));
		}

		return courses;
	}

	@Override
	protected PreparedStatement getReadingAllStatement(Connection connection) throws SQLException {
		return connection.prepareStatement("SELECT student_id, course_code, course_name, course_profile, qualification FROM Courses");
	}

	@Override	// TODO changed pk, test
	protected PreparedStatement getUpdateStatement(Connection connection, Course dataset, Integer pk) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("UPDATE Courses SET course_code = ?, course_name = ?, course_profile = ?, qualification = ? WHERE student_id = ?");
		statement.setString(1, dataset.getCode());
		statement.setString(2, dataset.getName());
		statement.setString(3, dataset.getProfile());
		statement.setString(4, dataset.getQualification());
		statement.setInt(5, pk);

		return statement;
	}

	@Override	// TODO changed pk, test
	protected PreparedStatement getDeleteByPkStatement(Connection connection, Integer pk) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("DELETE FROM Courses WHERE student_id = ?");
		statement.setInt(1, pk);
		return statement;
	}

	@Override
	protected PreparedStatement getDeleteAllStatement(Connection connection) throws SQLException {
		return connection.prepareStatement("DELETE FROM Courses");
	}

	@Override
	public List<Course> readByForeignKey(Integer fk) throws SQLException {
		PreparedStatement statement = this.connection.prepareStatement("SELECT student_id, course_code, course_name, course_profile, qualification FROM Courses WHERE student_id = ?");
		statement.setInt(1, fk);

		ResultSet resultSet = statement.executeQuery();
		List<Course> courses = new ArrayList<>();
		while(resultSet.next()){
			courses.add(new Course(resultSet.getString("course_code"), resultSet.getInt("student_id"), resultSet.getString("qualification"),	// TODO fix 0 issue
					resultSet.getString("course_name"), resultSet.getString("course_profile")));
		}

		resultSet.close();
		statement.close();
		return courses;
	}

	// TODO add test
	public void updateByForeignKey(int fk, Course dataset) throws SQLException {
		PreparedStatement statement = this.connection.prepareStatement("UPDATE Courses SET course_code = ?, course_name = ?, course_profile = ?, qualification = ? WHERE student_id = ?");
		statement.setString(1, dataset.getCode());
		statement.setString(2, dataset.getName());
		statement.setString(3, dataset.getProfile());
		statement.setString(4, dataset.getQualification());
		statement.setInt(5, fk);
		statement.executeUpdate();

		statement.close();
	}

	// TODO add test
	public void deleteByForeignKey(int fk) throws SQLException {
		PreparedStatement statement = this.connection.prepareStatement("DELETE FROM Courses WHERE student_id = ?");
		statement.setInt(1, fk);
		statement.executeUpdate();

		statement.close();
	}

}


























