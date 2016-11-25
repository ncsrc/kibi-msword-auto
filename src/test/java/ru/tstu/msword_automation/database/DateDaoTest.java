package ru.tstu.msword_automation.database;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.tstu.msword_automation.database.datasets.Date;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class DateDaoTest
{
	private DateDao dao;
	private Date date;
	private String strDateGos;
	private String strDateVcr;

	@Before
	public void setUp() throws Exception
	{
		this.strDateGos = "2013-02-05";
		this.strDateVcr = "2012-01-13";
		this.dao = DatabaseService.getInstance().getDateDao();
		this.date = new Date(1, strDateGos, strDateVcr);
		this.dao.deleteAll();
		this.dao.create(this.date);
	}

	@Test
	public void TestInsertionInEmptyTable() throws Exception
	{
		dao.deleteAll();
		Date data = new Date(0, strDateGos, strDateVcr);
		dao.create(data);
		Date createdData = dao.readAll().get(0);
		assertEquals(data, createdData);
	}

	@Ignore // not need this after refactor
	@Test(expected = SQLException.class)
	public void ExceptionThrownWhenTryingToInsertInNonEmptyTableWithSamePrimaryKey() throws Exception
	{
		Date data = new Date(1, "2003-03-03", "2004-01-10");
		dao.create(data);
	}

	@Test
	public void SelectionByPrimaryKeyCorrectness() throws Exception
	{
		Date date = dao.readAll().get(0);
		int id =date.getId();

		Date read = dao.read(id);
		assertEquals(date, read);
	}

	@Ignore // TODO dumb test, remove
	@Test(expected = SQLException.class)
	public void SelectionThrowsExceptionWhenPrimaryKeyNotFoundInTable() throws Exception
	{
		dao.read(12);
	}

	@Test
	public void TestSelectionOfAllTable() throws Exception
	{
		Date newDate = new Date(0, "2000-11-11", "2001-09-09"); // TODO 0
		dao.create(newDate);
		List<Date> list = dao.readAll();

		Date c1 = list.get(0);
		assertEquals(this.date, c1);

		Date c2 = list.get(1);
		assertEquals(newDate, c2);
	}

	@Test
	public void TestUpdatingOldDateWithNewOne() throws Exception
	{
		int oldId = dao.readAll().get(0).getId();
		Date update = new Date(0, "2001-01-01", "2002-02-02"); // TODO 0
		dao.update(oldId, update);

		List<Date> list = dao.readAll();
		assertEquals(1, list.size());

		Date c0 = list.get(0);
		assertEquals(update, c0);
	}

	@Test
	public void ReturnsEmptyListAfterRemovalOfTheOnlyOneRow() throws Exception	// supposing there is only one row in table as it should be by default
	{
		dao.delete(dao.readAll().get(0).getId());
		List<Date> list = dao.readAll();
		assertEquals(true, list.isEmpty());
	}

	@Test
	public void ReturnsEmptyListAfterRemovalAllRows() throws Exception
	{
		dao.deleteAll();
		List<Date> list = dao.readAll();
		assertEquals(true, list.isEmpty());
	}

}


























