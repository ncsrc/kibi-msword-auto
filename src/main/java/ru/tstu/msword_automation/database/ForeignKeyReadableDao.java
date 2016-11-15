package ru.tstu.msword_automation.database;


import java.sql.SQLException;

public interface ForeignKeyReadableDao<T, K>
{

	 T readByForeignKey(K fk) throws SQLException;


}
