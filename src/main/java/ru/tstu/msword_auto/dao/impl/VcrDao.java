package ru.tstu.msword_auto.dao.impl;


import ru.tstu.msword_auto.dao.ForeignKeyReadableDao;
import ru.tstu.msword_auto.dao.exceptions.AlreadyExistingException;
import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;
import ru.tstu.msword_auto.entity.Vcr;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class VcrDao extends AbstractDao<Vcr, String> implements ForeignKeyReadableDao<Vcr, Integer> {

	// sql statements
	static final String SQL_CREATE = "INSERT INTO VCRs VALUES(?, ?, ?, ?)";
	static final String SQL_READ_BY_PK = "SELECT student_id, vcr_name, vcr_head, vcr_reviewer FROM VCRs WHERE vcr_name = ?";
	static final String SQL_READ_ALL = "SELECT student_id, vcr_name, vcr_head, vcr_reviewer FROM VCRs";
	static final String SQL_UPDATE = "UPDATE VCRs SET vcr_name = ?, vcr_head = ?, vcr_reviewer = ? WHERE vcr_name = ?";
	static final String SQL_DELETE_BY_PK = "DELETE FROM VCRs WHERE vcr_name = ?";
	static final String SQL_DELETE_ALL = "DELETE FROM VCRs";
	static final String SQL_READ_BY_FK = "SELECT student_id, vcr_name, vcr_head, vcr_reviewer FROM VCRs WHERE student_id = ?";

	// column names
	static final String TABLE_STUDENT_ID = "STUDENT_ID";
	static final String TABLE_VCR_NAME = "VCR_NAME";
	static final String TABLE_VCR_HEAD = "VCR_HEAD";
	static final String TABLE_VCR_REVIEWER = "VCR_REVIEWER";

	static final String EXCEPTION_ALREADY_EXIST_ROW = "CONSTRAINT_INDEX_2 ON PUBLIC.VCRS(STUDENT_ID)";
	static final String EXCEPTION_ALREADY_EXIST_VCR = "PRIMARY_KEY_2 ON PUBLIC.VCRS(VCR_NAME)";


	public VcrDao() {
		super();
	}


	@Override
	public List<Vcr> readByForeignKey(Integer fk) throws DaoSystemException, NoSuchEntityException {
		try(PreparedStatement statement = this.connection.prepareStatement(SQL_READ_BY_FK)) {
			statement.setInt(1, fk);

			try(ResultSet resultSet = statement.executeQuery()) {
				List<Vcr> vcrs = parseResultSet(resultSet);

				if(vcrs.isEmpty()){
					throw new NoSuchEntityException();
				}

				return vcrs;
			}

		} catch (SQLException e) {
			throw new DaoSystemException(e);
		}

	}

	@Override
	public int create(Vcr dataset) throws DaoSystemException, AlreadyExistingException {
		try(PreparedStatement statement = this.getCreationStatement(dataset)) {
			statement.executeUpdate();

			try(ResultSet genKeys = statement.getGeneratedKeys()) {
				if(genKeys != null && genKeys.next()) {
					return genKeys.getInt(1);
				} else {
					return -1;	// if no key was created(e.g. if key is string)
				}

			} catch (SQLException e) {
				throw new DaoSystemException(e);
			}

		} catch (SQLException e) {
			String exceptionMessage = e.getMessage();
			if(exceptionMessage != null && exceptionMessage.contains(EXCEPTION_ALREADY_EXIST_VCR)) {
				throw new AlreadyExistingException("vcr_exists", e);
			} else if(exceptionMessage != null && exceptionMessage.contains(EXCEPTION_ALREADY_EXIST_ROW)) {
				throw new AlreadyExistingException("row_exists", e);
			}
			else {
				throw new DaoSystemException(e);
			}

		}
	}

	@Override
	protected PreparedStatement getCreationStatement(Vcr dataset) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SQL_CREATE);
		statement.setInt(1, dataset.getStudentId());
		statement.setString(2, dataset.getName());
		statement.setString(3, dataset.getHeadName());
		statement.setString(4, dataset.getReviewerName());
		return statement;
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
	protected PreparedStatement getUpdateStatement(Vcr dataset, String pk) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
		statement.setString(1, dataset.getName());
		statement.setString(2, dataset.getHeadName());
		statement.setString(3, dataset.getReviewerName());
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
	protected List<Vcr> parseResultSet(ResultSet resultSet) throws SQLException {
		List<Vcr> list = new ArrayList<>();
		while(resultSet.next()) {
			list.add(new Vcr(
					resultSet.getInt(TABLE_STUDENT_ID),
					resultSet.getString(TABLE_VCR_NAME),
					resultSet.getString(TABLE_VCR_HEAD),
					resultSet.getString(TABLE_VCR_REVIEWER)
			));
		}

		return list;
	}


}


