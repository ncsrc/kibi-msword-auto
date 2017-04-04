package ru.tstu.msword_auto.dao.impl;

import ru.tstu.msword_auto.dao.ForeignKeyReadableDao;
import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;
import ru.tstu.msword_auto.entity.Course;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CourseDao extends AbstractDao<Course, Integer> implements ForeignKeyReadableDao<Course, Integer> {

	// sql queries
	static final String SQL_CREATE = "INSERT INTO COURSES(STUDENT_ID, GROUP_NAME, COURSE_CODE, COURSE_NAME, COURSE_PROFILE, QUALIFICATION) " +
							   		 "VALUES(?, ?, ?, ?, ?, ?);";

	static final String SQL_READ_BY_PK = "SELECT STUDENT_ID, GROUP_NAME, COURSE_CODE, COURSE_NAME, COURSE_PROFILE, QUALIFICATION " +
										 "FROM COURSES WHERE STUDENT_ID = ?";

	static final String SQL_READ_ALL = "SELECT STUDENT_ID, GROUP_NAME, COURSE_CODE, COURSE_NAME, COURSE_PROFILE, QUALIFICATION FROM COURSES";

	static final String SQL_UPDATE = "UPDATE COURSES SET GROUP_NAME = ?, COURSE_CODE = ?, COURSE_NAME = ?, COURSE_PROFILE = ?, QUALIFICATION = ? " +
									 "WHERE STUDENT_ID = ?";

	static final String SQL_DELETE_BY_PK = "DELETE FROM COURSES WHERE STUDENT_ID = ?";

	static final String SQL_DELETE_ALL = "DELETE FROM COURSES";

	static final String SQL_READ_BY_FK = "SELECT STUDENT_ID, GROUP_NAME, COURSE_CODE, COURSE_NAME, COURSE_PROFILE, QUALIFICATION " +
										 "FROM COURSES WHERE STUDENT_ID = ?";

	// table column names
	static final String TABLE_STUDENT_ID = "STUDENT_ID";
	static final String TABLE_GROUP_NAME = "GROUP_NAME";
	static final String TABLE_COURSE_CODE = "COURSE_CODE";
	static final String TABLE_COURSE_NAME = "COURSE_NAME";
	static final String TABLE_COURSE_PROFILE = "COURSE_PROFILE";
	static final String TABLE_QUALIFICATION = "QUALIFICATION";


	public CourseDao() {
		super();
	}


	@Override
	protected PreparedStatement getCreationStatement(Course dataset) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SQL_CREATE);
		statement.setInt(1, dataset.getStudentId());
		statement.setString(2, dataset.getGroupName());
		statement.setString(3, dataset.getCode());
		statement.setString(4, dataset.getCourseName());
		statement.setString(5, dataset.getProfile());
		statement.setString(6, dataset.getQualification());

		return statement;
	}

	@Override
	protected PreparedStatement getReadingByPkStatement(Integer pk) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SQL_READ_BY_PK);
		statement.setInt(1, pk);
		return statement;
	}

	@Override
	protected List<Course> parseResultSet(ResultSet resultSet) throws SQLException {
		List<Course> courses = new ArrayList<>();

		while(resultSet.next()){
			courses.add(new Course(
					resultSet.getInt(TABLE_STUDENT_ID),
					resultSet.getString(TABLE_GROUP_NAME),
					resultSet.getString(TABLE_QUALIFICATION),
					resultSet.getString(TABLE_COURSE_NAME),
					resultSet.getString(TABLE_COURSE_PROFILE)
					));

		}

		return courses;
	}

	@Override
	protected PreparedStatement getReadingAllStatement() throws SQLException {
		return connection.prepareStatement(SQL_READ_ALL);
	}

	@Override
	protected PreparedStatement getUpdateStatement(Course dataset, Integer pk) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
		statement.setString(1, dataset.getGroupName());
		statement.setString(2, dataset.getCode());
		statement.setString(3, dataset.getCourseName());
		statement.setString(4, dataset.getProfile());
		statement.setString(5, dataset.getQualification());
		statement.setInt(6, pk);

		return statement;
	}

	@Override
	protected PreparedStatement getDeleteByPkStatement(Integer pk) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_PK);
		statement.setInt(1, pk);
		return statement;
	}

	@Override
	protected PreparedStatement getDeleteAllStatement() throws SQLException {
		return connection.prepareStatement(SQL_DELETE_ALL);
	}

	@Override
	public List<Course> readByForeignKey(Integer fk) throws DaoSystemException, NoSuchEntityException {
		try(PreparedStatement statement = this.connection.prepareStatement(SQL_READ_BY_FK)) {
			statement.setInt(1, fk);

			try(ResultSet resultSet = statement.executeQuery()) {
				List<Course> courses = parseResultSet(resultSet);

				if(courses.isEmpty()){
					throw new NoSuchEntityException();
				}

				return courses;
			}

		} catch (SQLException e) {
			throw new DaoSystemException(e);
		}

	}


}


























