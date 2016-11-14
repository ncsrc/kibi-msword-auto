package ru.tstu.msword_automation.database;


import ru.tstu.msword_automation.database.datasets.VCR;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class VcrDao extends AbstractDao<VCR, String>
{


	VcrDao(ConnectionPool connectionPool)
	{
		super(connectionPool);
	}


	@Override
	protected PreparedStatement getCreationStatement(Connection connection, VCR dataset) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("INSERT INTO VCRs VALUES(?, ?, ?, ?)");
		statement.setInt(1, dataset.getStudentId());
		statement.setString(2, dataset.getName());
		statement.setString(3, dataset.getHeadName());
		statement.setString(4, dataset.getReviewerName());
		return statement;
	}

	@Override
	protected PreparedStatement getReadingByPkStatement(Connection connection, String pk) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("SELECT student_id, vcr_name, vcr_head, vcr_reviewer FROM VCRs WHERE vcr_name = ?");
		statement.setString(1, pk);
		return statement;
	}

	@Override
	protected PreparedStatement getReadingAllStatement(Connection connection) throws SQLException
	{
		return connection.prepareStatement("SELECT student_id, vcr_name, vcr_head, vcr_reviewer FROM VCRs");
	}

	@Override
	protected PreparedStatement getUpdateStatement(Connection connection, VCR dataset, String pk) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("UPDATE VCRs SET student_id = ?, vcr_name = ?, vcr_head = ?, vcr_reviewer = ? WHERE vcr_name = ?");
		statement.setInt(1, dataset.getStudentId());
		statement.setString(2, dataset.getName());
		statement.setString(3, dataset.getHeadName());
		statement.setString(4, dataset.getReviewerName());
		statement.setString(5, pk);
		return statement;
	}

	@Override
	protected PreparedStatement getDeleteByPkStatement(Connection connection, String pk) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement("DELETE FROM VCRs WHERE vcr_name = ?");
		statement.setString(1, pk);
		return statement;
	}

	@Override
	protected PreparedStatement getDeleteAllStatement(Connection connection) throws SQLException
	{
		return connection.prepareStatement("DELETE FROM VCRs");
	}

	@Override
	protected List<VCR> parseResultSet(ResultSet resultSet) throws SQLException
	{
		List<VCR> list = new ArrayList<>();
		while(resultSet.next()){
			list.add(new VCR(resultSet.getInt("student_id"), resultSet.getString("vcr_name"),
					resultSet.getString("vcr_head"), resultSet.getString("vcr_reviewer")));
		}

		return list;
	}


}



// TODO add interface ForeignKeyReadable - some tables can be read only by fk