package ru.tstu.msword_auto.dao.impl;


import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;
import ru.tstu.msword_auto.entity.GekHead;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GekHeadDao extends AbstractDao<GekHead, Integer> {

    // sql queries
    static final String SQL_CREATE = "INSERT INTO GEK_HEAD(COURSE_NAME, GEK_HEAD, GEK_SUBHEAD, GEK_SECRETARY) VALUES(?, ?, ?, ?)";

    static final String SQL_READ_BY_PK = "SELECT GEK_ID, COURSE_NAME, GEK_HEAD, GEK_SUBHEAD, GEK_SECRETARY FROM GEK_HEAD WHERE GEK_ID=?";

    static final String SQL_READ_BY_COURSENAME = "SELECT GEK_ID, COURSE_NAME, GEK_HEAD, GEK_SUBHEAD, GEK_SECRETARY FROM GEK_HEAD WHERE COURSE_NAME=?";

    static final String SQL_READ_ALL = "SELECT GEK_ID, COURSE_NAME, GEK_HEAD, GEK_SUBHEAD, GEK_SECRETARY FROM GEK_HEAD";

    static final String SQL_UPDATE = "UPDATE GEK_HEAD SET COURSE_NAME=?, GEK_HEAD=?, GEK_SUBHEAD=?, GEK_SECRETARY=? WHERE GEK_ID=?";

    static final String SQL_DELETE_BY_PK = "DELETE FROM GEK_HEAD WHERE GEK_ID=?";

    static final String SQL_DELETE_ALL = "DELETE FROM GEK_HEAD";

    // table column names
    static final String TABLE_GEK_ID = "GEK_ID";
    static final String TABLE_COURSE_NAME = "COURSE_NAME";
    static final String TABLE_GEK_HEAD = "GEK_HEAD";
    static final String TABLE_GEK_SUBHEAD = "GEK_SUBHEAD";
    static final String TABLE_GEK_SECRETARY = "GEK_SECRETARY";


	public GekHeadDao() {
		super();
	}



	public GekHead readByCourseName(String courseName) throws DaoSystemException, NoSuchEntityException {
		try {
			PreparedStatement statement = connection.prepareStatement(SQL_READ_BY_COURSENAME);
			statement.setString(1, courseName);
			ResultSet resultSet = statement.executeQuery();
			List<GekHead> entities = parseResultSet(resultSet);

			if(entities.isEmpty()) {
				throw new NoSuchEntityException();
			}

			resultSet.close();
			statement.close();

			return entities.get(0);
		} catch (SQLException e) {
			throw new DaoSystemException(e);
		}

	}


	@Override
	protected PreparedStatement getCreationStatement(GekHead dataset) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SQL_CREATE);
		statement.setString(1, dataset.getCourseName());
        statement.setString(2, dataset.getHead());
		statement.setString(3, dataset.getSubhead());
		statement.setString(4, dataset.getSecretary());
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
	protected PreparedStatement getUpdateStatement(GekHead dataset, Integer pk) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);

		statement.setString(1, dataset.getCourseName());
        statement.setString(2, dataset.getHead());
		statement.setString(3, dataset.getSubhead());
		statement.setString(4, dataset.getSecretary());
		statement.setInt(5, pk);

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
	protected List<GekHead> parseResultSet(ResultSet resultSet) throws SQLException {
		List<GekHead> list = new ArrayList<>();
		while(resultSet.next()) {
			GekHead gek = new GekHead(
                    resultSet.getString(TABLE_COURSE_NAME),
			        resultSet.getString(TABLE_GEK_HEAD),
                    resultSet.getString(TABLE_GEK_SUBHEAD),
                    resultSet.getString(TABLE_GEK_SECRETARY)
            );
			gek.setGekId(resultSet.getInt(TABLE_GEK_ID));

			list.add(gek);
		}

		return list;
	}


}























