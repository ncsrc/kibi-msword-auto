package ru.tstu.msword_automation.database;


import java.sql.SQLException;
import java.util.List;

/**
 *
 * @param <T> specifies dataset
 * @param <K> specifies type of primary key
 */
public interface Dao<T, K>
{

	void create(T dataset) throws SQLException, DaoException;

	T read(K pk) throws SQLException;

	List<T> readAll() throws SQLException;

	void update(K pk, T dataset) throws SQLException;

	void delete(K pk) throws SQLException;

	void deleteAll() throws SQLException;

	K lastInsertId() throws SQLException;

}


