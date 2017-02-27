package ru.tstu.msword_auto.dao;


import org.junit.Ignore;
import ru.tstu.msword_auto.entity.GekHead;


import java.sql.SQLException;

@Ignore
public class GekHeadDaoTest extends AbstractDaoTest<GekHeadDao, GekHead, String>
{



	@Override
	protected GekHead getDataset()
	{
		return new GekHead("qwe", "asd", "zxc");
	}

	@Override
	protected String getPkOfFirst()
	{
		return "qwe";
	}

	@Override
	protected GekHead getSecondDataset()
	{
		return new GekHead("test", "test", "test");
	}

	@Override
	protected String getPkOfSecond()
	{
		return "test";
	}

	@Override
	protected GekHeadDao getDao() throws SQLException
	{
//		return DatabaseService.getInstance().getGekHeadDao();	// do not instantiate directly, because ConnectionPool overflow may occur then

		return null;
	}

	@Override
	protected String getNonExistentPk()
	{
		return "asd";
	}


}