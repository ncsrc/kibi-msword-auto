package ru.tstu.msword_auto.dao;


import org.junit.Ignore;
import org.junit.Test;
import ru.tstu.msword_auto.entity.Student;


import java.sql.SQLException;
import java.util.List;


import static org.junit.Assert.assertEquals;

@Ignore
public class StudentDaoTest extends AbstractDaoTest<StudentDao, Student, Integer>
{



	@Override
	protected Student getDataset()
	{
//		return new Student(1, "Иван", "Иванов", "Иванович");

		return null;
	}

	@Override
	protected Student getSecondDataset()
	{
//		return new Student(2, "qwe", "test", "test");

		return null;
	}

	@Override
	protected StudentDao getDao() throws SQLException
	{
//		return DatabaseService.getInstance().getStudentDao();

		return null;
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


	// need to override this because of slight differ update logic of student entity
	@Test
	@Override
	public void TestUpdatingOldDateWithNewOne() throws Exception
	{
//		dao.update(this.firstSetPk, this.secondDataset);
//		List<Student> list = dao.readAll();
//		assertEquals(1, list.size());
//		Student s0 = list.get(0);
//		Student expected = new Student(firstSetPk, secondDataset.getFirstName(),
//				secondDataset.getLastName(), secondDataset.getMiddleName());
//
//		assertEquals(expected, s0);
	}

}