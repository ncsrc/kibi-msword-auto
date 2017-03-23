package ru.tstu.msword_auto.dao;

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


	// TODO in all methods change throws to try-catch. catch SQLException should close resourses, put back connections and throw some custom exception


	@Override
	public void create(T dataset) throws SQLException, DaoException {
		PreparedStatement statement = this.getCreationStatement(this.connection, dataset);
		int created = statement.executeUpdate();

		if(created > 0) {
			setLastInsertId(dataset);
		}
		statement.close();
	}

	protected abstract PreparedStatement getCreationStatement(Connection connection, T dataset) throws SQLException;


	@Override
	public T read(K pk) throws SQLException {
		PreparedStatement statement = this.getReadingByPkStatement(this.connection, pk);
		ResultSet resultSet = statement.executeQuery();
		List<T> results = this.parseResultSet(resultSet);
		resultSet.close();
		statement.close();

		if(results.isEmpty()){
			throw new SQLException("Such primary key value does not exists in table"); // TODO change to NoSuchEntityException
		}

		return results.get(0);
	}

	protected abstract PreparedStatement getReadingByPkStatement(Connection connection, K pk) throws SQLException;

	protected abstract List<T> parseResultSet(ResultSet resultSet) throws SQLException;


	@Override
	public List<T> readAll() throws SQLException {
		PreparedStatement statement = this.getReadingAllStatement(this.connection);
		ResultSet resultSet = statement.executeQuery();
		List<T> results = this.parseResultSet(resultSet);
		resultSet.close();
		statement.close();
		return results;
	}

	protected abstract PreparedStatement getReadingAllStatement(Connection connection) throws SQLException;


	@Override
	public void update(K pk, T dataset) throws SQLException {
		PreparedStatement statement = this.getUpdateStatement(this.connection, dataset, pk);
		statement.executeUpdate();
		statement.close();
	}

	protected abstract PreparedStatement getUpdateStatement(Connection connection, T dataset, K pk) throws SQLException;


	@Override
	public void delete(K pk) throws SQLException {
		PreparedStatement statement = this.getDeleteByPkStatement(this.connection, pk);
		statement.executeUpdate();
		statement.close();
	}

	protected abstract PreparedStatement getDeleteByPkStatement(Connection connection, K pk) throws SQLException;


	@Override
	public void deleteAll() throws SQLException {
		PreparedStatement statement = this.getDeleteAllStatement(this.connection);
		statement.executeUpdate();
		statement.close();
	}

	protected abstract PreparedStatement getDeleteAllStatement(Connection connection) throws SQLException;


	@Override
	public K lastInsertId() throws SQLException {
		// TODO can return null

		return this.lastInsertId;
	}


	// TODO refactor method, fix unchecked casting and null returning
	private void setLastInsertId(T dataset) throws DaoException {
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
			throw new DaoException("Can't reflectively get lastInsertId value, access denied"); // TODO think of more precise exception
		}

		this.lastInsertId = value;
	}


}
























