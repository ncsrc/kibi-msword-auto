package ru.tstu.msword_automation.database;



import com.mysql.jdbc.Driver;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService
{
	private static DatabaseService self;
	private final ConnectionPool connectionPool = new ConnectionPool();


	private DatabaseService() throws SQLException
	{

	}


	public static DatabaseService getInstance() throws SQLException
	{
		if(self == null){
			DriverManager.registerDriver(new Driver());
			self = new DatabaseService();
		}
		return self;
	}

	public StudentDao getStudentDao() throws SQLException
	{
		return new StudentDao(connectionPool);
	}

	public DateDao getDateDao() throws SQLException
	{
		return new DateDao(connectionPool);
	}

	public GekDao getGekDao() throws SQLException
	{
		return new GekDao(connectionPool);
	}

	public CourseDao getCourseDao() throws SQLException
	{
		return new CourseDao(connectionPool);
	}

	public VcrDao getVcrDao() throws SQLException
	{
		return new VcrDao(connectionPool);
	}




	// TODO refactoring notes:
	/*
		1 Student has VCR and Course. DB Service should extract it and save.
		2 What needed: save(dataset), get student, get gek, get date
	 */



	/*
		What it should do:
		1 Creating and preserving connections
		2 Returning dao-objects
		3 dao-objects should return datasets

	 */


	/*
		Ways to handle connections:
			Keep ConnectionPool in DBService and give it to constructor of DAO or use function in constructor like giveConnection()
			Keep final variable of singleton ConnectionPool in every dao object(actually it possible to move it to abstract class, that's better approach)
	 */

}
