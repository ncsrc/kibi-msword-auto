package ru.tstu.msword_auto.dao;


import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @param <T> specifies dataset
 * @param <K> specifies type of primary key
 */
public interface Dao<T, K> {

	void create(T dataset) throws DaoSystemException;

	T read(K pk) throws DaoSystemException, NoSuchEntityException;

	List<T> readAll() throws DaoSystemException;

	void update(K pk, T dataset) throws DaoSystemException;

	void delete(K pk) throws DaoSystemException;

	void deleteAll() throws DaoSystemException;

	// TODO remove !!
	K lastInsertId();	// works only after creation of new row by dao, otherwise returns null

}


