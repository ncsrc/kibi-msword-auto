package ru.tstu.msword_auto.dao;


import ru.tstu.msword_auto.entity.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// TODO remove Connection from arguments, since protected

public class StudentDao extends AbstractDao<Student, Integer> {
	private static final String SQL_CREATE = "INSERT INTO Students VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_READ_BY_PK = "SELECT student_id, first_name_i, last_name_i, middle_name_i," +
												 "first_name_r, last_name_r, middle_name_r," +
												 "first_name_t, last_name_t, middle_name_t" +
												 " FROM Students WHERE student_id = ?";

	private static final String SQL_READ_ALL = "SELECT student_id, first_name_i, last_name_i, middle_name_i," +
											   "first_name_r, last_name_r, middle_name_r," +
											   "first_name_t, last_name_t, middle_name_t FROM Students";

	private static final String SQL_UPDATE = "UPDATE Students SET first_name_i = ?, last_name_i = ?, middle_name_i = ?," +
											 "first_name_r = ?, last_name_r = ?, middle_name_r = ?," +
											 "first_name_t = ?, last_name_t = ?, middle_name_t = ?" +
											 " WHERE student_id = ?";

	private static final String SQL_DELETE = "DELETE FROM Students WHERE student_id = ?";


	public StudentDao() {
		super();
	}


	@Override
	protected PreparedStatement getCreationStatement(Connection connection, Student dataset) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SQL_CREATE);

		statement.setInt(1, dataset.getId());

		// I case
		statement.setString(2, dataset.getFirstNameI());
		statement.setString(3, dataset.getLastNameI());
		statement.setString(4, dataset.getMiddleNameI());

		// R case
		statement.setString(5, dataset.getFirstNameR());
		statement.setString(6, dataset.getLastNameR());
		statement.setString(7, dataset.getMiddleNameR());

		// T case
		statement.setString(8, dataset.getFirstNameT());
		statement.setString(9, dataset.getLastNameT());
		statement.setString(10, dataset.getMiddleNameT());

		return statement;
	}

	@Override
	protected PreparedStatement getReadingByPkStatement(Connection connection, Integer pk) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SQL_READ_BY_PK);
		statement.setInt(1, pk);

		return statement;
	}

	@Override
	protected PreparedStatement getReadingAllStatement(Connection connection) throws SQLException {
		return connection.prepareStatement(SQL_READ_ALL);
	}

	@Override
	protected PreparedStatement getUpdateStatement(Connection connection, Student dataset, Integer pk) throws SQLException {
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
		statement.setString(7, dataset.getFirstNameT());
		statement.setString(8, dataset.getLastNameT());
		statement.setString(9, dataset.getMiddleNameT());

		statement.setInt(10, pk);

		return statement;
	}

	@Override
	protected PreparedStatement getDeleteByPkStatement(Connection connection, Integer pk) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("DELETE FROM Students WHERE student_id = ?");
		statement.setInt(1, pk);

		return statement;
	}

	@Override
	protected PreparedStatement getDeleteAllStatement(Connection connection) throws SQLException {
		return connection.prepareStatement(SQL_DELETE);
	}

	@Override
	protected List<Student> parseResultSet(ResultSet resultSet) throws SQLException {
		List<Student> list = new ArrayList<>();
		while(resultSet.next()) {
			int studentId = resultSet.getInt("student_id");

			String firstNameI = resultSet.getString("first_name_i");
			String lastNameI = resultSet.getString("last_name_i");
			String middleNameI = resultSet.getString("middle_name_i");

			String firstNameR = resultSet.getString("first_name_r");
			String lastNameR = resultSet.getString("last_name_r");
			String middleNameR = resultSet.getString("middle_name_r");

			String firstNameT = resultSet.getString("first_name_t");
			String lastNameT = resultSet.getString("last_name_t");
			String middleNameT = resultSet.getString("middle_name_t");

			list.add(new Student(studentId, firstNameI, lastNameI, middleNameI,
								firstNameR, lastNameR, middleNameR,
								firstNameT, lastNameT, middleNameT));

		}

		return list;
	}
}




















