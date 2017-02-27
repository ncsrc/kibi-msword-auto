package ru.tstu.msword_auto.dao;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.tstu.msword_auto.entity.VCR;


import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Ignore
public class VcrDaoTest extends AbstractDaoTest<VcrDao, VCR, String>
{

	@Before
	public void init() throws Exception
	{
		// TODO remove this, add insertion to all tables in abstract class
		// TODO OR include dependent objects and initialize them in constructor
//		DatabaseService.getInstance().getStudentDao().create(new Student(1, "test", "test",
//				"test", "test", "test"));
	}


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
//		return DatabaseService.getInstance().getVcrDao();

		return null;
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


	@Test
	public void CorrectReadByForeignKey() throws Exception
	{
		List<VCR> list = dao.readByForeignKey(1);
		assertEquals(dataset, list.get(0));
//		assertEquals(secondDataset, list.get(1));
	}


}






















