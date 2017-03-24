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

// TODO change template methods to lambdas(executor class, composite in daos)

abstract class AbstractDao<T, K> implements Dao<T, K> {
	// protected in case you need direct access for connections
	// for implementation of custom functionality not defined in Dao
	protected Connection connection;
	protected K lastInsertId; // this assigned in create() method after successful sql-statement execution

	AbstractDao() {
		this.connection = getConnection();
	}


	@Override
	public void create(T dataset) throws DaoSystemException {
		try {
			PreparedStatement statement = this.getCreationStatement(dataset);
			int created = statement.executeUpdate();

			if(created > 0) {
				setLastInsertId(dataset);
			}
			statement.close();
		} catch (SQLException e) {
			throw new DaoSystemException(e);
		}
	}

	protected abstract PreparedStatement getCreationStatement(T dataset) throws SQLException;


	@Override
	public T read(K pk) throws DaoSystemException, NoSuchEntityException {
		try {
			PreparedStatement statement = this.getReadingByPkStatement(pk);
			ResultSet resultSet = statement.executeQuery();
			List<T> results = this.parseResultSet(resultSet);
			resultSet.close();
			statement.close();

			if(results.isEmpty()){
				throw new NoSuchEntityException();
			}

			return results.get(0);
		} catch (SQLException e) {
			throw new DaoSystemException(e);
		}

	}

	protected abstract PreparedStatement getReadingByPkStatement(K pk) throws SQLException;

	protected abstract List<T> parseResultSet(ResultSet resultSet) throws SQLException;


	@Override
	public List<T> readAll() throws DaoSystemException {
		try {
			PreparedStatement statement = this.getReadingAllStatement();
			ResultSet resultSet = statement.executeQuery();
			List<T> results = this.parseResultSet(resultSet);
			resultSet.close();
			statement.close();
			return results;
		} catch (SQLException e) {
			throw new DaoSystemException(e);
		}

	}

	protected abstract PreparedStatement getReadingAllStatement() throws SQLException;


	@Override
	public void update(K pk, T dataset) throws DaoSystemException {
		try {
			PreparedStatement statement = this.getUpdateStatement(dataset, pk);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			throw new DaoSystemException(e);
		}

	}

	protected abstract PreparedStatement getUpdateStatement(T dataset, K pk) throws SQLException;


	@Override
	public void delete(K pk) throws DaoSystemException {
		try {
			PreparedStatement statement = this.getDeleteByPkStatement(pk);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			throw new DaoSystemException(e);
		}

	}

	protected abstract PreparedStatement getDeleteByPkStatement(K pk) throws SQLException;


	@Override
	public void deleteAll() throws DaoSystemException {
		try {
			PreparedStatement statement = this.getDeleteAllStatement();
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			throw new DaoSystemException(e);
		}

	}

	protected abstract PreparedStatement getDeleteAllStatement() throws SQLException;


	@Override
	public K lastInsertId() {
		// TODO can return null

		return this.lastInsertId;
	}


	// TODO remove !!!
	// TODO refactor method, fix unchecked casting and null returning
	private void setLastInsertId(T dataset) throws DaoSystemException {
		Class clazz = dataset.getClass();
		Field[] fields = clazz.getDeclaredFields();

		Field target = null;
		for(Field field : fields) {
			Annotation pk = field.getAnnotation(PrimaryKey.class);
			if(pk != null){
				target = field;
				break;
			}
		}

		K value;
		try {
			target.setAccessible(true);
			value = (K) target.get(dataset); // TODO fix unchecked casting
		} catch(IllegalAccessException e) {
			throw new DaoSystemException("Can't reflectively get lastInsertId value, access denied"); // TODO think of more precise exception
		}

		this.lastInsertId = value;
	}


}
























