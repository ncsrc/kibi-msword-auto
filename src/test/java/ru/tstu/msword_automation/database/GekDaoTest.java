package ru.tstu.msword_automation.database;


import ru.tstu.msword_automation.database.datasets.GEK;

import java.sql.SQLException;


public class GekDaoTest extends AbstractDaoTest<GekDao, GEK, String>
{



	@Override
	protected GEK getDataset()
	{
		return new GEK("qwe", "asd", "zxc", "1", "2", "3"
						, "4", "5", "6");
	}

	@Override
	protected String getPkOfFirst()
	{
		return "qwe";
	}

	@Override
	protected GEK getSecondDataset()
	{
		return new GEK("test", "test", "test",
				"test", "test", "test", "test", "test", "test");
	}

	@Override
	protected String getPkOfSecond()
	{
		return "test";
	}

	@Override
	protected GekDao getDao() throws SQLException
	{
		return DatabaseService.getInstance().getGekDao();	// do not instantiate directly, because ConnectionPool overflow may occur then
	}

	@Override
	protected String getNonExistentPk()
	{
		return "asd";
	}

	//	private GekDao dao;
//	private GEK gek;
//
//
//	@Before
//	public void setUp() throws Exception
//	{
//		this.dao = DatabaseService.getInstance().getGekDao();
//		this.gek = new GEK("qwe", "asd", "zxc", "1", "2", "3"
//							, "4", "5", "6");
//		this.dao.deleteAll();
//		this.dao.create(this.gek);
//	}
//
//
//	@Test
//	public void TestInsertionInEmptyTable() throws Exception
//	{
//		dao.deleteAll();
//		dao.create(this.gek);
//		GEK read = dao.read(this.gek.getHead());
//		assertEquals(this.gek, read);
//	}
//
//	@Test(expected = SQLException.class)
//	public void ExceptionThrownWhenTryingToInsertInNonEmptyTableWithSamePrimaryKey() throws Exception
//	{
//		dao.create(this.gek);
//	}
//
//	@Test
//	public void SelectionByPrimaryKeyCorrectness() throws Exception
//	{
//		GEK read = dao.read(this.gek.getHead());
//		assertEquals(this.gek, read);
//	}
//
//	@Test(expected = SQLException.class)
//	public void SelectionThrowsExceptionWhenPrimaryKeyNotFoundInTable() throws Exception
//	{
//		dao.read("1");
//	}
//
//	@Test
//	public void TestSelectionOfAllTable() throws Exception
//	{
//		GEK newGEK = new GEK("test", "test", "test",
//				"test", "test", "test", "test", "test", "test");
//		dao.create(newGEK);
//		List<GEK> list = dao.readAll();
//
//		GEK g1 = list.get(0);
//		assertEquals(newGEK, g1);
//
//		GEK g2 = list.get(1);
//		assertEquals(this.gek, g2);
//	}
//
//	@Test
//	public void TestUpdatingOldDateWithNewOne() throws Exception
//	{
//		GEK newGEK = new GEK("test", "test", "test",
//				"test", "test", "test", "test", "test", "test");
//		dao.update(this.gek.getHead(), newGEK);
//
//		List<GEK> list = dao.readAll();
//		assertEquals(1, list.size());
//
//		GEK g0 = list.get(0);
//		assertEquals(newGEK, g0);
//	}
//
//	@Test
//	public void ReturnsEmptyListAfterRemovalOfTheOnlyOneRow() throws Exception	// supposing there is only one row in table as it should be by default
//	{
//		dao.delete(this.gek.getHead());
//		List<GEK> list = dao.readAll();
//		assertEquals(true, list.isEmpty());
//	}
//
//	@Test
//	public void ReturnsEmptyListAfterRemovalAllRows() throws Exception
//	{
//		dao.deleteAll();
//		List<GEK> list = dao.readAll();
//		assertEquals(true, list.isEmpty());
//	}



}