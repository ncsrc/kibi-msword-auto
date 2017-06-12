package ru.tstu.msword_auto.dao.impl;


import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;
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


public class DateDao extends AbstractDao<Date, Integer> {

	// parsed dates map keys
	private static final String KEY_GOS_DATE = "gos_date";
	private static final String KEY_VCR_DATE = "vcr_date";

    // sql queries
    static final String SQL_CREATE = "INSERT INTO Date(group_id, GROUP_NAME, DATE_GOS, DATE_VCR) VALUES(?, ?, ?, ?)";

    static final String SQL_READ_BY_PK = "SELECT date_id, group_id, GROUP_NAME, DATE_GOS, DATE_VCR FROM DATE WHERE date_id=?";

    static final String SQL_READ_BY_GROUP_NAME = "SELECT date_id, group_id, GROUP_NAME, DATE_GOS, DATE_VCR FROM DATE WHERE GROUP_NAME=?";

	static final String SQL_READ_BY_GROUP_NAME_AND_GROUP_ID = "SELECT date_id, group_id, GROUP_NAME, DATE_GOS, DATE_VCR FROM DATE WHERE GROUP_NAME=? AND GROUP_ID=?";

    static final String SQL_READ_ALL = "SELECT date_id, group_id, GROUP_NAME, DATE_GOS, DATE_VCR FROM DATE";

    static final String SQL_UPDATE = "UPDATE DATE SET group_id=?, GROUP_NAME=?, DATE_GOS=?, DATE_VCR=? WHERE date_id=?";

    static final String SQL_DELETE_BY_PK = "DELETE FROM DATE WHERE date_id=?";

    static final String SQL_DELETE_ALL = "DELETE FROM DATE";

    // table column names
	static final String TABLE_DATE_ID = "date_id";
	static final String TABLE_GROUP_ID = "group_id";
	static final String TABLE_GROUP_NAME = "GROUP_NAME";
	static final String TABLE_DATE_GOS = "DATE_GOS";
	static final String TABLE_DATE_VCR = "DATE_VCR";


	public DateDao() {
		super();
	}


	// TODO add test
	public List<Date> readByGroupName(String groupName) throws DaoSystemException {
		try(PreparedStatement statement = connection.prepareStatement(SQL_READ_BY_GROUP_NAME)) {
			statement.setString(1, groupName);

			try(ResultSet resultSet = statement.executeQuery()) {
				return this.parseResultSet(resultSet);
			} catch (SQLException e) {
				throw new DaoSystemException(e);
			}

		} catch (SQLException e) {
			throw new DaoSystemException(e);
		}
	}

	// TODO add test
	public Date readByGroupNameAndGroupId(String groupName, int groupId) throws DaoSystemException, NoSuchEntityException {
		try(PreparedStatement statement = connection.prepareStatement(SQL_READ_BY_GROUP_NAME_AND_GROUP_ID)) {
			statement.setString(1, groupName);
			statement.setInt(2, groupId);

			try(ResultSet resultSet = statement.executeQuery()) {
				List<Date> results = this.parseResultSet(resultSet);
				if(results.isEmpty()){
					throw new NoSuchEntityException();
				}

				return results.get(0);
			} catch (SQLException e) {
				throw new DaoSystemException(e);
			}

		} catch (SQLException e) {
			throw new DaoSystemException(e);
		}
	}

	@Override
	protected PreparedStatement getCreationStatement(Date dataset) throws SQLException {
		return prepareUpdateStatement(SQL_CREATE, dataset);
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
	protected PreparedStatement getUpdateStatement(Date dataset, Integer pk) throws SQLException {
		PreparedStatement statement = prepareUpdateStatement(SQL_UPDATE, dataset);
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
	protected List<Date> parseResultSet(ResultSet resultSet) throws SQLException {
		List<Date> dates = new ArrayList<>();
		while(resultSet.next()) {
			int dateId = resultSet.getInt(TABLE_DATE_ID);
			int groupId = resultSet.getInt(TABLE_GROUP_ID);
			String groupName = resultSet.getString(TABLE_GROUP_NAME);
			String gosDate = parseDateToString(resultSet.getDate(TABLE_DATE_GOS));
			String vcrDate = parseDateToString(resultSet.getDate(TABLE_DATE_VCR));

			Date entity = new Date(dateId, groupId, groupName, gosDate, vcrDate);

			dates.add(entity);
		}

		return dates;
	}


	// this code is similar to create and update actions
	private PreparedStatement prepareUpdateStatement(String sql, Date dataset) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);

		Map<String, java.sql.Date> parsed = parseDatesToSqlQuery(dataset);

		statement.setInt(1, dataset.getSubgroupId());
		statement.setString(2, dataset.getGroupName());
		statement.setDate(3, parsed.get(KEY_GOS_DATE));
		statement.setDate(4, parsed.get(KEY_VCR_DATE));

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























