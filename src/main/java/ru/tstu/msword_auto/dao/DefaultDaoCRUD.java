package ru.tstu.msword_auto.dao;


import ru.tstu.msword_auto.dao.exceptions.AlreadyExistingException;
import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;

import java.util.List;

/**
 *
 * @param <T> specifies dataset
 * @param <K> specifies type of primary key
 */
public interface DefaultDaoCRUD<T, K> {

	int create(T dataset) throws DaoSystemException, AlreadyExistingException;

	T read(K pk) throws DaoSystemException, NoSuchEntityException;

	List<T> readAll() throws DaoSystemException;

	void update(K pk, T dataset) throws DaoSystemException, AlreadyExistingException;

	void delete(K pk) throws DaoSystemException;

	void deleteAll() throws DaoSystemException;

}


