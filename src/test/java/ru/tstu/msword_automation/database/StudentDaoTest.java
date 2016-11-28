package ru.tstu.msword_automation.database;


import ru.tstu.msword_automation.database.datasets.Student;

import java.sql.SQLException;

public class StudentDaoTest extends AbstractDaoTest<StudentDao, Student, Integer>
{



	@Override
	protected Student getDataset()
	{
		return new Student(1, "Иван", "Иванов", "Иванович");
	}

	@Override
	protected Student getSecondDataset()
	{
		return new Student(2, "qwe", "test", "test");
	}

	@Override
	protected StudentDao getDao() throws SQLException
	{
		return DatabaseService.getInstance().getStudentDao();
	}

	@Override
	protected Integer getPkOfFirst()
	{
		return 1;
	}

	@Override
	protected Integer getPkOfSecond()
	{
		return 2;
	}

	@Override
	protected Integer getNonExistentPk()
	{
		return -1;
	}



}