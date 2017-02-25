package ru.tstu.msword_automation.database;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.tstu.msword_automation.database.datasets.GekMember;

import static org.junit.Assert.*;

@Ignore
public class DatabaseServiceTest
{
	private DatabaseService db;

	@Before
	public void init() throws Exception
	{
		this.db = DatabaseService.getInstance();
	}


	@Test
	public void SingleObjectWhenGetInstanceInvokedMultipleTimes() throws Exception
	{
		DatabaseService db0 = DatabaseService.getInstance();
		DatabaseService db1 = DatabaseService.getInstance();

		assertNotNull(db0);
		assertNotNull(db1);
		assertEquals(db0, db1);
	}

	@Test
	public void AssertThatInstancesOfStudentDaoCreates() throws Exception
	{
		StudentDao studentDao0 = db.getStudentDao();
		StudentDao studentDao1 = db.getStudentDao();

		assertNotNull(studentDao0);
		assertNotNull(studentDao1);
		assertNotEquals(studentDao0, studentDao1);
	}

	@Test
	public void AssertThatInstancesOfCourseDaoCreates() throws Exception
	{
		CourseDao courseDao0 = db.getCourseDao();
		CourseDao courseDao1 = db.getCourseDao();

		assertNotNull(courseDao0);
		assertNotNull(courseDao1);
		assertNotEquals(courseDao0, courseDao1);
	}

	@Test
	public void AssertThatInstancesOfDateDaoCreates() throws Exception
	{
		DateDao dateDao0 = db.getDateDao();
		DateDao dateDao1 = db.getDateDao();

		assertNotNull(dateDao0);
		assertNotNull(dateDao1);
		assertNotEquals(dateDao0, dateDao1);
	}

	@Test
	public void AssertThatInstancesOfGekDaoCreates() throws Exception
	{
		GekHeadDao gekDao0 = db.getGekHeadDao();
		GekHeadDao gekDao1 = db.getGekHeadDao();

		assertNotNull(gekDao0);
		assertNotNull(gekDao1);
		assertNotEquals(gekDao0, gekDao1);
	}

	@Test
	public void AssertThatInstancesOfVcrDaoCreates() throws Exception
	{
		VcrDao vcrDao0 = db.getVcrDao();
		VcrDao vcrDao1 = db.getVcrDao();
		assertNotNull(vcrDao0);
		assertNotNull(vcrDao1);
		assertNotEquals(vcrDao0, vcrDao1);
	}

	@Test
	public void AssertThatInstancesOfGekMemberDaoCreates() throws Exception
	{
		GekMemberDao g0 = db.getGekMemberDao();
		GekMemberDao g1 = db.getGekMemberDao();

		assertNotNull(g0);
		assertNotNull(g1);
		assertNotEquals(g0, g1);
	}


}


























