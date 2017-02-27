package ru.tstu.msword_auto.dao;


import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import ru.tstu.msword_automation.database.datasets.GekHead;
import ru.tstu.msword_automation.database.datasets.GekMember;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Ignore
public class GekMemberDaoTest extends AbstractDaoTest<GekMemberDao, GekMember, String>
{

	// need this initialization because of dependency on foreign key
	@BeforeClass
	public static void init() throws Exception
	{
		GekHeadDao headDao = DatabaseService.getInstance().getGekHeadDao();
		headDao.deleteAll();
		headDao.create(new GekHead("test", "test", "test"));
	}


	@Override
	protected GekMember getDataset()
	{
		return new GekMember("test", "member");
	}

	@Override
	protected GekMember getSecondDataset()
	{
		return new GekMember("test", "qsdzxc");
	}

	@Override
	protected GekMemberDao getDao() throws SQLException
	{
		return DatabaseService.getInstance().getGekMemberDao();
	}

	@Override
	protected String getPkOfFirst()
	{
		return "member";
	}

	@Override
	protected String getPkOfSecond()
	{
		return "qsdzxc";	// TODO move to constants
	}

	@Override
	protected String getNonExistentPk()
	{
		return "123";
	}

	@Test
	public void CorrectReadByForeignKey() throws Exception
	{
	 	List<GekMember> list = dao.readByForeignKey("test");
		assertEquals(dataset, list.get(0));

	}


}























