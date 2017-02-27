package ru.tstu.msword_auto.dao;


import ru.tstu.msword_auto.entity.GekMember;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// TODO remove Connection from arguments, since protected

public class GekMemberDao extends AbstractDao<GekMember, String> implements ForeignKeyReadableDao<GekMember, String> {

	public GekMemberDao() {
		super();
	}

	@Override
	public List<GekMember> readByForeignKey(String fk) throws SQLException {
		PreparedStatement statement = this.connection.prepareStatement("SELECT gek_head, gek_member FROM Gek_Members WHERE gek_head = ?");
		statement.setString(1, fk);
		ResultSet resultSet = statement.executeQuery();

		List<GekMember> gekMembers = new ArrayList<>();
		while(resultSet.next()){
			gekMembers.add(new GekMember(resultSet.getString("gek_head"), resultSet.getString("gek_member")));
		}

		resultSet.close();
		statement.close();

		return gekMembers;
	}

	@Override
	protected PreparedStatement getCreationStatement(Connection connection, GekMember dataset) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Gek_Members VALUES(?, ?)");
		statement.setString(1, dataset.getHead());
		statement.setString(2, dataset.getMember());
		return statement;
	}

	@Override
	protected PreparedStatement getReadingByPkStatement(Connection connection, String pk) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT gek_head, gek_member FROM Gek_Members WHERE gek_member = ?");
		statement.setString(1, pk);

		return statement;
	}

	@Override
	protected PreparedStatement getReadingAllStatement(Connection connection) throws SQLException {
		return connection.prepareStatement("SELECT gek_head, gek_member FROM Gek_Members");
	}

	@Override
	protected PreparedStatement getUpdateStatement(Connection connection, GekMember dataset, String pk) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("UPDATE Gek_Members SET gek_member = ? WHERE gek_member = ?");
		statement.setString(1, dataset.getMember());
		statement.setString(2, pk);

		return statement;
	}

	@Override
	protected PreparedStatement getDeleteByPkStatement(Connection connection, String pk) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("DELETE FROM Gek_Members WHERE gek_member = ?");
		statement.setString(1, pk);

		return statement;
	}

	@Override
	protected PreparedStatement getDeleteAllStatement(Connection connection) throws SQLException {
		return connection.prepareStatement("DELETE FROM Gek_Members");
	}

	@Override
	protected List<GekMember> parseResultSet(ResultSet resultSet) throws SQLException {
		List<GekMember> list = new ArrayList<>();
		while(resultSet.next()) {
			list.add(new GekMember(resultSet.getString("gek_head"), resultSet.getString("gek_member")));
		}

		return list;
	}

}




















