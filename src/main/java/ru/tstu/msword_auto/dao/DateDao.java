//package ru.tstu.msword_auto.dao;
//
//
//import ru.tstu.msword_auto.entity.Date;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//// TODO remove Connection from arguments, since protected
//
//// TODO Date entity can contain empty string - check for that, and if true - set nulls
//
//public class DateDao extends AbstractDao<Date, Integer> {
//
//	public DateDao() {
//		super();
//	}
//
//	@Override
//	protected PreparedStatement getCreationStatement(Connection connection, Date dataset) throws SQLException {
//		PreparedStatement statement = connection.prepareStatement("INSERT INTO Date(date_gos, date_vcr) VALUES(?, ?)");
//		statement.setDate(1, new java.sql.Date(dataset.getGosDateInLong().getTime()));
//		statement.setDate(2, new java.sql.Date(dataset.getVcrDateInLong().getTime()));
//		return statement;
//	}
//
//
//	@Override
//	protected PreparedStatement getReadingByPkStatement(Connection connection, Integer pk) throws SQLException {
//		PreparedStatement statement = connection.prepareStatement("SELECT id, date_gos, date_vcr FROM Date WHERE id = ?");
//		statement.setInt(1, pk);
//		return statement;
//	}
//
//	@Override
//	protected PreparedStatement getReadingAllStatement(Connection connection) throws SQLException {
//		return connection.prepareStatement("SELECT id, date_gos, date_vcr FROM Date");
//	}
//
//	@Override
//	protected PreparedStatement getUpdateStatement(Connection connection, Date dataset, Integer pk) throws SQLException {
//		PreparedStatement statement = connection.prepareStatement("UPDATE Date SET date_gos = ?, date_vcr = ? WHERE id= ?");
//		statement.setDate(1, new java.sql.Date(dataset.getGosDateInLong().getTime()));
//		statement.setDate(2, new java.sql.Date(dataset.getVcrDateInLong().getTime()));
//		statement.setInt(3, pk);
//		return statement;
//	}
//
//	@Override
//	protected PreparedStatement getDeleteByPkStatement(Connection connection, Integer pk) throws SQLException {
//		PreparedStatement statement = connection.prepareStatement("DELETE FROM Date WHERE id = ?");
//		statement.setInt(1, pk);
//		return statement;
//	}
//
//	@Override
//	protected PreparedStatement getDeleteAllStatement(Connection connection) throws SQLException {
//		return connection.prepareStatement("DELETE FROM Date");
//	}
//
//	@Override
//	protected List<Date> parseResultSet(ResultSet resultSet) throws SQLException {
//		List<Date> dates = new ArrayList<>();
//		while(resultSet.next()){
//			dates.add(new Date(resultSet.getInt("id"), resultSet.getDate("date_gos"), resultSet.getDate("date_vcr")));
//		}
//		return dates;
//	}
//
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
