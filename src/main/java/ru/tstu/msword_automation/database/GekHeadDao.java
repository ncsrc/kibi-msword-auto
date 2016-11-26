package ru.tstu.msword_automation.database;


import ru.tstu.msword_automation.database.datasets.GekHead;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GekHeadDao extends AbstractDao<GekHead, String>
{

	GekHeadDao(ConnectionPool pool)
	{
		super(pool);
	}


	@Override
	protected PreparedStatement getCreationStatement(Connection connection, GekHead dataset) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Gek_Head VALUES(?, ?, ?)");
		statement.setString(1, dataset.getHead());
		statement.setString(2, dataset.getSubhead());
		statement.setString(3, dataset.getSecretary());
		return statement;
	}

	@Override
	protected PreparedStatement getReadingByPkStatement(Connection connection, String pk) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("SELECT gek_head, gek_subhead, gek_secretary FROM Gek_Head WHERE gek_head = ?");
		statement.setString(1, pk);

		return statement;
	}

	@Override
	protected PreparedStatement getReadingAllStatement(Connection connection) throws SQLException
	{
		return connection.prepareStatement("SELECT gek_head, gek_subhead, gek_secretary FROM Gek_Head");
	}

	@Override
	protected PreparedStatement getUpdateStatement(Connection connection, GekHead dataset, String pk) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("UPDATE Gek_Head SET gek_head = ?, gek_subhead = ?, gek_secretary = ? WHERE gek_head = ?");
		statement.setString(1, dataset.getHead());
		statement.setString(2, dataset.getSubhead());
		statement.setString(3, dataset.getSecretary());
		statement.setString(4, pk);

		return statement;
	}

	@Override
	protected PreparedStatement getDeleteByPkStatement(Connection connection, String pk) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("DELETE FROM Gek_Head WHERE gek_head = ?");
		statement.setString(1, pk);

		return statement;
	}

	@Override
	protected PreparedStatement getDeleteAllStatement(Connection connection) throws SQLException
	{
		return connection.prepareStatement("DELETE FROM Gek_Head");
	}

	@Override
	protected List<GekHead> parseResultSet(ResultSet resultSet) throws SQLException
	{
		List<GekHead> list = new ArrayList<>();
		while(resultSet.next()){
			GekHead gek = new GekHead(resultSet.getString("gek_head"), resultSet.getString("gek_subhead"), resultSet.getString("gek_secretary"));
			list.add(gek);
		}

		return list;
	}


}























