package ru.tstu.msword_automation.database;

import org.junit.Before;
import org.junit.Test;
import ru.tstu.msword_automation.database.datasets.Course;

import java.util.List;

import static org.junit.Assert.*;

public class CourseDaoTest
{
	private CourseDao dao;
	private Course course;


	@Before
	public void init() throws Exception
	{
		this.course = new Course("11.11.11.11", "Б-И", "Б-И в сфере ИТ");
		DatabaseService db = DatabaseService.getInstance();
		this.dao = db.getCourseDao();
		this.dao.deleteAll();
		this.dao.create(this.course);
	}


	@Test
	public void TestInsertion() throws Exception
	{
		Course data = new Course("1.1.1.1", "из тестов", "спек из тестов");
		dao.create(data);
		Course createdData = dao.read("1.1.1.1");
		assertEquals(data, createdData);
	}

	@Test
	public void TestSelectionCorrectness() throws Exception
	{
		Course read = dao.read("11.11.11.11");
		assertEquals(this.course, read);
	}

	@Test
	public void SelectionReturnsNullIfKeyNotFound() throws Exception
	{
		assertNull(dao.read("1"));
	}

	@Test
	public void TestSelectionOfAllTable() throws Exception
	{
		Course newCourse = new Course("1.1.1.1", "from-tests", "spec-from-tests");
		dao.create(newCourse);
		List<Course> list = dao.readAll();

		Course c1 = list.get(0);
		assertEquals(newCourse, c1);

		Course c2 = list.get(1);
		assertEquals(this.course, c2);
	}

	@Test
	public void TestUpdatingOldCourseWithNewOne() throws Exception
	{
		Course update = new Course("1.1.1.1", "from-tests", "spec-from-tests");
		dao.update("11.11.11.11", update);

		List<Course> list = dao.readAll();
		assertEquals(1, list.size());

		Course c0 = list.get(0);
		assertEquals(update, c0);
	}

	@Test
	public void ReturnsEmptyListAfterRemovalOfTheOnlyOneRow() throws Exception	// supposing there is only one row in table as it should be by default
	{
		dao.delete("11.11.11.11");
		List<Course> list = dao.readAll();
		assertEquals(true, list.isEmpty());
	}

	@Test
	public void ReturnsEmptyListAfterRemovalAllRows() throws Exception
	{
		dao.deleteAll();
		List<Course> list = dao.readAll();
		assertEquals(true, list.isEmpty());
	}

}































