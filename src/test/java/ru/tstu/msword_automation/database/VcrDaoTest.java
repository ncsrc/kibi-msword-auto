package ru.tstu.msword_automation.database;

import ru.tstu.msword_automation.database.datasets.VCR;

import java.sql.SQLException;


public class VcrDaoTest extends AbstractDaoTest<VcrDao, VCR, String>
{


	@Override
	protected VCR getDataset()
	{
		return new VCR(1, "test", "test", "test");
	}

	@Override
	protected VCR getSecondDataset()
	{
		return new VCR(1, "weqw", "aet", "eewq");
	}

	@Override
	protected VcrDao getDao() throws SQLException
	{
		return DatabaseService.getInstance().getVcrDao();
	}

	@Override
	protected String getPkOfFirst()
	{
		return "test";
	}

	@Override
	protected String getPkOfSecond()
	{
		return "weqw";
	}

	@Override
	protected String getNonExistentPk()
	{
		return "-1";
	}


}