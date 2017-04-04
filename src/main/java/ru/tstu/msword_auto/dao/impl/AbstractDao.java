package ru.tstu.msword_auto.dao.impl;

import ru.tstu.msword_auto.dao.Dao;
import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;
import ru.tstu.msword_auto.entity.PrimaryKey;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static ru.tstu.msword_auto.dao.ConnectionStorage.getConnection;


abstract class AbstractDao<T, K> implements Dao<T, K> {
	// protected in case you need direct access for connections
	// for implementation of custom functionality not defined in Dao
	protected Connection connection;


	AbstractDao() {
		this.connection = getConnection();
	}


	@Override
	public void create(T dataset) throws DaoSystemException {
		try(PreparedStatement statement = this.getCreationStatement(dataset)) {
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoSystemException(e);
		}
	}

	protected abstract PreparedStatement getCreationStatement(T dataset) throws SQLException;


	@Override
	public T read(K pk) throws DaoSystemException, NoSuchEntityException {
		try(PreparedStatement statement = this.getReadingByPkStatement(pk)) {

			try(ResultSet resultSet = statement.executeQuery()) {
				List<T> results = this.parseResultSet(resultSet);
				if(results.isEmpty()){
					throw new NoSuchEntityException();
				}

				return results.get(0);
			}

		} catch (SQLException e) {
			throw new DaoSystemException(e);
		}

	}

	protected abstract PreparedStatement getReadingByPkStatement(K pk) throws SQLException;

	protected abstract List<T> parseResultSet(ResultSet resultSet) throws SQLException;


	@Override
	public List<T> readAll() throws DaoSystemException {
		try(PreparedStatement statement = this.getReadingAllStatement()) {

			try(ResultSet resultSet = statement.executeQuery()) {
				return this.parseResultSet(resultSet);
			}

		} catch (SQLException e) {
			throw new DaoSystemException(e);
		}

	}

	protected abstract PreparedStatement getReadingAllStatement() throws SQLException;


	@Override
	public void update(K pk, T dataset) throws DaoSystemException {
		try(PreparedStatement statement = this.getUpdateStatement(dataset, pk)) {
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoSystemException(e);
		}

	}

	protected abstract PreparedStatement getUpdateStatement(T dataset, K pk) throws SQLException;


	@Override
	public void delete(K pk) throws DaoSystemException {
		try(PreparedStatement statement = this.getDeleteByPkStatement(pk)) {
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoSystemException(e);
		}

	}

	protected abstract PreparedStatement getDeleteByPkStatement(K pk) throws SQLException;


	@Override
	public void deleteAll() throws DaoSystemException {
		try(PreparedStatement statement = this.getDeleteAllStatement()) {
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoSystemException(e);
		}

	}

	protected abstract PreparedStatement getDeleteAllStatement() throws SQLException;


}
























