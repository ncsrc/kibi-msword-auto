package ru.tstu.msword_automation.database;


import ru.tstu.msword_automation.database.datasets.GEK;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GekDao extends AbstractDao<GEK, String>
{

	GekDao(ConnectionPool pool)
	{
		super(pool);
	}


	@Override
	protected PreparedStatement getCreationStatement(Connection connection, GEK dataset) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("INSERT INTO GEK VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
		statement.setString(1, dataset.getHead());
		statement.setString(2, dataset.getSubhead());
		statement.setString(3, dataset.getSecretary());
		statement.setString(4, dataset.getFirstMember());
		statement.setString(5, dataset.getSecondMember());
		statement.setString(6, dataset.getThirdMember());
		statement.setString(7, dataset.getFourthMember());
		statement.setString(8, dataset.getFifthMember());
		statement.setString(9, dataset.getSixthMember());
		return statement;
	}

	@Override
	protected PreparedStatement getReadingByPkStatement(Connection connection, String pk) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("SELECT gek_head, gek_subhead, gek_secretary, gek_member1, gek_member2, gek_member3," +
				"  gek_member4, gek_member5, gek_member6 FROM GEK WHERE gek_head = ?");
		statement.setString(1, pk);

		return statement;
	}

	@Override
	protected PreparedStatement getReadingAllStatement(Connection connection) throws SQLException
	{
		return connection.prepareStatement("SELECT gek_head, gek_subhead, gek_secretary, gek_member1, gek_member2, gek_member3," +
				"  gek_member4, gek_member5, gek_member6 FROM GEK");
	}

	@Override
	protected PreparedStatement getUpdateStatement(Connection connection, GEK dataset, String pk) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("UPDATE GEK SET gek_head = ?, gek_subhead = ?, gek_secretary = ?, gek_member1 = ?," +
				"  gek_member2 = ?, gek_member3 = ?, gek_member4 = ?, gek_member5 = ?, gek_member6 = ?" +
				" WHERE gek_head = ?");
		statement.setString(1, dataset.getHead());
		statement.setString(2, dataset.getSubhead());
		statement.setString(3, dataset.getSecretary());
		statement.setString(4, dataset.getFirstMember());
		statement.setString(5, dataset.getSecondMember());
		statement.setString(6, dataset.getThirdMember());
		statement.setString(7, dataset.getFourthMember());
		statement.setString(8, dataset.getFifthMember());
		statement.setString(9, dataset.getSixthMember());
		statement.setString(10, pk);

		return statement;
	}

	@Override
	protected PreparedStatement getDeleteByPkStatement(Connection connection, String pk) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("DELETE FROM GEK WHERE gek_head = ?");
		statement.setString(1, pk);

		return statement;
	}

	@Override
	protected PreparedStatement getDeleteAllStatement(Connection connection) throws SQLException
	{
		return connection.prepareStatement("DELETE FROM GEK");
	}

	@Override
	protected List<GEK> parseResultSet(ResultSet resultSet) throws SQLException
	{
		List<GEK> list = new ArrayList<>();
		while(resultSet.next()){
			GEK gek = new GEK(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
					resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
					resultSet.getString(7), resultSet.getString(8), resultSet.getString(9));
			list.add(gek);
		}

		return list;
	}


}























