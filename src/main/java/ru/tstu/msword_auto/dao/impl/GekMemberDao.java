package ru.tstu.msword_auto.dao.impl;


import ru.tstu.msword_auto.dao.ForeignKeyReadableDao;
import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;
import ru.tstu.msword_auto.entity.GekMember;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GekMemberDao extends AbstractDao<GekMember, Integer> implements ForeignKeyReadableDao<GekMember, Integer> {

    // sql queries
    static final String SQL_CREATE = "INSERT INTO GEK_MEMBERS(HEAD_ID, GEK_MEMBER) VALUES(?, ?)";
    static final String SQL_READ_BY_PK = "SELECT MEMBER_ID, HEAD_ID, GEK_MEMBER FROM GEK_MEMBERS WHERE MEMBER_ID=?";
    static final String SQL_READ_ALL = "SELECT MEMBER_ID, HEAD_ID, GEK_MEMBER FROM GEK_MEMBERS";
    static final String SQL_UPDATE = "UPDATE GEK_MEMBERS SET HEAD_ID=?, GEK_MEMBER=? WHERE MEMBER_ID=?";
    static final String SQL_DELETE_BY_PK = "DELETE FROM GEK_MEMBERS WHERE MEMBER_ID=?";
    static final String SQL_DELETE_ALL = "DELETE FROM GEK_MEMBERS";
    static final String SQL_READ_BY_FK = "SELECT MEMBER_ID, HEAD_ID, GEK_MEMBER FROM GEK_MEMBERS WHERE HEAD_ID=?";

    // table column names
    static final String TABLE_MEMBER_ID = "MEMBER_ID";
    static final String TABLE_HEAD_ID = "HEAD_ID";
    static final String TABLE_GEK_MEMBER = "GEK_MEMBER";


    public GekMemberDao() {
		super();
	}

	@Override
	public List<GekMember> readByForeignKey(Integer fk) throws DaoSystemException, NoSuchEntityException {
    	try {
			PreparedStatement statement = this.connection.prepareStatement(SQL_READ_BY_FK);
			statement.setInt(1, fk);
			ResultSet resultSet = statement.executeQuery();

			List<GekMember> gekMembers = parseResultSet(resultSet);
			if(gekMembers.isEmpty()) {
				throw new NoSuchEntityException();
			}

			resultSet.close();
			statement.close();

			return gekMembers;
		} catch (SQLException e) {
    		throw new DaoSystemException(e);
		}

	}

	@Override
	protected PreparedStatement getCreationStatement(GekMember dataset) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SQL_CREATE);
		statement.setInt(1, dataset.getGekHeadId());
		statement.setString(2, dataset.getMember());
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
	protected PreparedStatement getUpdateStatement(GekMember dataset, Integer pk) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
		statement.setInt(1, dataset.getGekHeadId());
		statement.setString(2, dataset.getMember());
		statement.setInt(3, pk);

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
	protected List<GekMember> parseResultSet(ResultSet resultSet) throws SQLException {
		List<GekMember> list = new ArrayList<>();
		while(resultSet.next()) {
			list.add(new GekMember(
					resultSet.getInt(TABLE_MEMBER_ID),
					resultSet.getInt(TABLE_HEAD_ID),
					resultSet.getString(TABLE_GEK_MEMBER))
			);
		}

		return list;
	}

}




















