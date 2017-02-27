package ru.tstu.msword_auto.dao;


import java.sql.SQLException;
import java.util.List;

public interface ForeignKeyReadableDao<T, K> {

	 List<T> readByForeignKey(K fk) throws SQLException;


}

// TODO move to abstract class