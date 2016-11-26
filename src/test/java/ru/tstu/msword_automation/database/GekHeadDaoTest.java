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


}