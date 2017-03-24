package ru.tstu.msword_auto.dao.impl;


import ru.tstu.msword_auto.entity.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class StudentDao extends AbstractDao<Student, Integer> {

	// sql queries
	static final String SQL_CREATE = "INSERT INTO Students VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	static final String SQL_READ_BY_PK = "SELECT student_id, first_name_i, last_name_i, middle_name_i," +
												 "first_name_r, last_name_r, middle_name_r," +
												 "first_name_t, last_name_t, middle_name_t" +
												 " FROM Students WHERE student_id = ?";

	static final String SQL_READ_ALL = "SELECT student_id, first_name_i, last_name_i, middle_name_i," +
											   "first_name_r, last_name_r, middle_name_r," +
											   "first_name_t, last_name_t, middle_name_t FROM Students";

	static final String SQL_UPDATE = "UPDATE Students SET first_name_i = ?, last_name_i = ?, middle_name_i = ?," +
											 "first_name_r = ?, last_name_r = ?, middle_name_r = ?," +
											 "first_name_t = ?, last_name_t = ?, middle_name_t = ?" +
											 " WHERE student_id = ?";

	static final String SQL_DELETE_BY_PK = "DELETE FROM Students WHERE student_id = ?";

	static final String SQL_DELETE_ALL = "DELETE FROM Students";

	// table names
	static final String TABLE_STUDENT_ID = "STUDENT_ID";
	static final String TABLE_FIRST_NAME_I = "FIRST_NAME_I";
	static final String TABLE_LAST_NAME_I = "LAST_NAME_I";
	static final String TABLE_MIDDLE_NAME_I = "MIDDLE_NAME_I";
	static final String TABLE_FIRST_NAME_R = "FIRST_NAME_R";
	static final String TABLE_LAST_NAME_R = "LAST_NAME_R";
	static final String TABLE_MIDDLE_NAME_R = "MIDDLE_NAME_R";
	static final String TABLE_FIRST_NAME_D = "FIRST_NAME_T";
	static final String TABLE_LAST_NAME_D = "LAST_NAME_T";
	static final String TABLE_MIDDLE_NAME_D = "MIDDLE_NAME_T";


	public StudentDao() {
		super();
	}


	@Override
	protected PreparedStatement getCreationStatement(Student dataset) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SQL_CREATE);

		// I case
		statement.setString(1, dataset.getFirstNameI());
		statement.setString(2, dataset.getLastNameI());
		statement.setString(3, dataset.getMiddleNameI());

		// R case
		statement.setString(4, dataset.getFirstNameR());
		statement.setString(5, dataset.getLastNameR());
		statement.setString(6, dataset.getMiddleNameR());

		// D case
		statement.setString(7, dataset.getFirstNameD());
		statement.setString(8, dataset.getLastNameD());
		statement.setString(9, dataset.getMiddleNameD());


		return statement;
	}

	@Override
	protected PreparedStatement getReadingByPkStatement(Integer pk) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SQL_READ_BY_PK);
		statement.setInt(1, pk);

		return statement;
	}

	@Override
	protected PreparedStatement getReadingAllStatement() throws SQLException {
		return connection.prepareStatement(SQL_READ_ALL);
	}

	@Override
	protected PreparedStatement getUpdateStatement(Student dataset, Integer pk) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);

		// I case
		statement.setString(1, dataset.getFirstNameI());
		statement.setString(2, dataset.getLastNameI());
		statement.setString(3, dataset.getMiddleNameI());

		// R case
		statement.setString(4, dataset.getFirstNameR());
		statement.setString(5, dataset.getLastNameR());
		statement.setString(6, dataset.getMiddleNameR());

		// T case
		statement.setString(7, dataset.getFirstNameD());
		statement.setString(8, dataset.getLastNameD());
		statement.setString(9, dataset.getMiddleNameD());

		statement.setInt(10, pk);

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
	protected List<Student> parseResultSet(ResultSet resultSet) throws SQLException {
		List<Student> list = new ArrayList<>();
		while(resultSet.next()) {
			int studentId = resultSet.getInt(TABLE_STUDENT_ID);

			String firstNameI = resultSet.getString(TABLE_FIRST_NAME_I);
			String lastNameI = resultSet.getString(TABLE_LAST_NAME_I);
			String middleNameI = resultSet.getString(TABLE_MIDDLE_NAME_I);

			String firstNameR = resultSet.getString(TABLE_FIRST_NAME_R);
			String lastNameR = resultSet.getString(TABLE_LAST_NAME_R);
			String middleNameR = resultSet.getString(TABLE_MIDDLE_NAME_R);

			String firstNameT = resultSet.getString(TABLE_FIRST_NAME_D);
			String lastNameT = resultSet.getString(TABLE_LAST_NAME_D);
			String middleNameT = resultSet.getString(TABLE_MIDDLE_NAME_D);

			Student entity = new Student(firstNameI, lastNameI, middleNameI,
					firstNameR, lastNameR, middleNameR,
					firstNameT, lastNameT, middleNameT);
			entity.setStudentId(studentId);

			list.add(entity);
		}

		return list;
	}
}




















