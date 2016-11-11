package ru.tstu.msword_automation.database;

import org.junit.Before;
import org.junit.Test;
import ru.tstu.msword_automation.database.datasets.Date;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class DateDaoTest
{
	private DateDao dao;
	private Date date;


	@Before
	public void setUp() throws Exception
	{
		this.dao = DatabaseService.getInstance().getDateDao();
		this.date = new Date(1, "апреля", 15);
		this.dao.deleteAll();
		this.dao.create(this.date);
	}

	@Test
	public void TestInsertionInEmptyTable() throws Exception
	{
		dao.deleteAll();
		Date data = new Date(5, "месяц-тест", 13);
		dao.create(data);
		Date createdData = dao.read(13);
		assertEquals(data, createdData);
	}

	@Test(expected = SQLException.class)
	public void ExceptionThrownWhenTryingToInsertInNonEmptyTableWithSamePrimaryKey() throws Exception
	{
		Date data = new Date(5, "месяц-тест", this.date.getYear());
		dao.create(data);
	}

	@Test
	public void SelectionByPrimaryKeyCorrectness() throws Exception
	{
		Date read = dao.read(this.date.getYear());
		assertEquals(this.date, read);
	}

	@Test
	public void SelectionReturnsNullIfKeyNotFound() throws Exception
	{
		Date read = dao.read(1);
		assertNull(read);
	}

	@Test
	public void TestSelectionOfAllTable() throws Exception
	{
		Date newDate = new Date(1, "from-tests", 1);
		dao.create(newDate);
		List<Date> list = dao.readAll();

		Date c1 = list.get(0);
		assertEquals(newDate, c1);

		Date c2 = list.get(1);
		assertEquals(this.date, c2);
	}

	@Test
	public void TestUpdatingOldDateWithNewOne() throws Exception
	{
		Date update = new Date(13, "from-tests", 11);
		dao.update(this.date.getYear(), update);

		List<Date> list = dao.readAll();
		assertEquals(1, list.size());

		Date c0 = list.get(0);
		assertEquals(update, c0);
	}

	@Test
	public void ReturnsEmptyListAfterRemovalOfTheOnlyOneRow() throws Exception	// supposing there is only one row in table as it should be by default
	{
		dao.delete(this.date.getYear());
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


























