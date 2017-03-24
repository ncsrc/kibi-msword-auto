package ru.tstu.msword_auto.dao.impl;


import ru.tstu.msword_auto.entity.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DateDao extends AbstractDao<Date, String> {

	// parsed dates map keys
	private static final String KEY_GOS_DATE = "gos_date";
	private static final String KEY_VCR_DATE = "vcr_date";

    // sql queries
    static final String SQL_CREATE = "INSERT INTO Date(GROUP_NAME, DATE_GOS, DATE_VCR) VALUES(?, ?, ?)";

    static final String SQL_READ_BY_PK = "SELECT GROUP_NAME, DATE_GOS, DATE_VCR FROM DATE WHERE GROUP_NAME=?";

    static final String SQL_READ_ALL = "SELECT GROUP_NAME, DATE_GOS, DATE_VCR FROM DATE";

    static final String SQL_UPDATE = "UPDATE DATE SET GROUP_NAME=?, DATE_GOS=?, DATE_VCR=? WHERE GROUP_NAME=?";

    static final String SQL_DELETE_BY_PK = "DELETE FROM DATE WHERE GROUP_NAME=?";

    static final String SQL_DELETE_ALL = "DELETE FROM DATE";

    // table column names
	static final String TABLE_GROUP_NAME = "GROUP_NAME";
	static final String TABLE_DATE_GOS = "DATE_GOS";
	static final String TABLE_DATE_VCR = "DATE_VCR";


	public DateDao() {
		super();
	}

	@Override
	protected PreparedStatement getCreationStatement(Date dataset) throws SQLException {
		return prepareUpdateStatement(SQL_CREATE, dataset);
	}


	@Override
	protected PreparedStatement getReadingByPkStatement(String pk) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SQL_READ_BY_PK);
		statement.setString(1, pk);
		return statement;
	}

	@Override
	protected PreparedStatement getReadingAllStatement() throws SQLException {
		return connection.prepareStatement(SQL_READ_ALL);
	}

	@Override
	protected PreparedStatement getUpdateStatement(Date dataset, String pk) throws SQLException {
		PreparedStatement statement = prepareUpdateStatement(SQL_UPDATE, dataset);
		statement.setString(4, pk);
		return statement;
	}

	@Override
	protected PreparedStatement getDeleteByPkStatement(String pk) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_PK);
		statement.setString(1, pk);
		return statement;
	}

	@Override
	protected PreparedStatement getDeleteAllStatement() throws SQLException {
		return connection.prepareStatement(SQL_DELETE_ALL);
	}

	@Override
	protected List<Date> parseResultSet(ResultSet resultSet) throws SQLException {
		List<Date> dates = new ArrayList<>();
		while(resultSet.next()) {
			String groupName = resultSet.getString(TABLE_GROUP_NAME);
			String gosDate = parseDateToString(resultSet.getDate(TABLE_DATE_GOS));
			String vcrDate = parseDateToString(resultSet.getDate(TABLE_DATE_VCR));

			Date entity = new Date(groupName, gosDate, vcrDate);

			dates.add(entity);
		}

		return dates;
	}


	// this code is similar to create and update actions
	private PreparedStatement prepareUpdateStatement(String sql, Date dataset) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);

		Map<String, java.sql.Date> parsed = parseDatesToSqlQuery(dataset);

		statement.setString(1, dataset.getGroupName());
		statement.setDate(2, parsed.get(KEY_GOS_DATE));
		statement.setDate(3, parsed.get(KEY_VCR_DATE));

		return statement;
	}

	private String parseDateToString(java.sql.Date date) {
		if(date == null) {
			return "";
		}

		return date.toString();
	}

	private Map<String, java.sql.Date> parseDatesToSqlQuery(Date dataset) {
        String gosDate = dataset.getGosDate();
        String vcrDate = dataset.getVcrDate();
        Map<String, java.sql.Date> parsed = new HashMap<>();

		parsed.put(KEY_GOS_DATE, parseSingleDate(gosDate));
		parsed.put(KEY_VCR_DATE, parseSingleDate(vcrDate));

        return parsed;
    }

    private java.sql.Date parseSingleDate(String date) {
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date parsedDate = null;

		try {
			parsedDate = new java.sql.Date(parser.parse(date).getTime());
		} catch (ParseException e) {
			// intentionally blank, since in dataset can't be date in wrong format
			// it can be only empty string, and in that case null should be returned
		}

		return parsedDate;
	}


}























