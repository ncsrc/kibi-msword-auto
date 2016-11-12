package ru.tstu.msword_automation.database;


import org.junit.Before;
import org.junit.Test;
import ru.tstu.msword_automation.database.datasets.GEK;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 * @param <T> specifies Dao
 * @param <D> specifies Dataset
 * @param <K> specifies Primary Key for Dao
 */
public abstract class AbstractDaoTest<T extends Dao<D, K>, D, K>
{
	private T dao;
	private D dataset;
	private K firstSetPk;
	private D secondDataset;
	private K secondSetPk;


	@Before
	public void setUp() throws Exception
	{
		this.dao = getDao();
		this.dataset = getDataset();
		this.secondDataset = getSecondDataset();
		this.dao.deleteAll();
		this.dao.create(this.dataset);
		this.firstSetPk = getPkOfFirst();
	}

	protected abstract D getDataset();
	protected abstract D getSecondDataset();
	protected abstract T getDao();
	protected abstract K getPkOfFirst();
	protected abstract K getPkOfSecond();


	@Test
	public void TestInsertionInEmptyTable() throws Exception
	{
		dao.deleteAll();
		dao.create(this.dataset);
		D read = dao.read(this.firstSetPk);
		assertEquals(this.dataset, read);
	}

	@Test(expected = SQLException.class)
	public void ExceptionThrownWhenTryingToInsertInNonEmptyTableWithSamePrimaryKey() throws Exception
	{
		dao.create(this.dataset);
	}

	@Test
	public void SelectionByPrimaryKeyCorrectness() throws Exception
	{
		D read = dao.read(this.firstSetPk);
		assertEquals(this.dataset, read);
	}

	@Test(expected = SQLException.class)
	public void SelectionThrowsExceptionWhenPrimaryKeyNotFoundInTable() throws Exception
	{
		dao.read(getNonExistentPk());
	}

	protected abstract K getNonExistentPk();


	@Test
	public void TestSelectionOfAllTable() throws Exception
	{
		dao.create(this.secondDataset);
		List<D> list = dao.readAll();
		assertEquals(true, list.size() == 2);

		D d0 = list.get(0);
		assertEquals(true, checkIfEquals(d0));

		D d1 = list.get(1);
		assertEquals(true, checkIfEquals(d1));
	}

	@Test
	public void TestUpdatingOldDateWithNewOne() throws Exception
	{
		dao.update(this.secondSetPk, this.secondDataset);
		List<D> list = dao.readAll();
		assertEquals(1, list.size());
		D d0 = list.get(0);
		assertEquals(this.secondDataset, d0);
	}

	@Test
	public void ReturnsEmptyListAfterRemovalOfTheOnlyOneRow() throws Exception	// supposing there is only one row in table as it should be by default
	{
		dao.delete(this.firstSetPk);
		List<D> list = dao.readAll();
		assertEquals(true, list.isEmpty());
	}

	@Test
	public void ReturnsEmptyListAfterRemovalAllRows() throws Exception
	{
		dao.deleteAll();
		List<D> list = dao.readAll();
		assertEquals(true, list.isEmpty());
	}


	private boolean checkIfEquals(D dataset)
	{
		if(dataset.equals(this.dataset) || dataset.equals(this.secondDataset)){
			return true;
		}

		return false;
	}

}






















