package ru.tstu.msword_auto.dao;


import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;

import java.util.List;


public interface ForeignKeyReadableDao<T, K> {

	 List<T> readByForeignKey(K fk) throws DaoSystemException, NoSuchEntityException;


}
