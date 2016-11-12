package ru.tstu.msword_automation.database.datasets;

import org.junit.Test;

import static org.junit.Assert.*;

public class GEKTest
{

	@Test
	public void equalsCorrectness() throws Exception
	{
		GEK g0 = new GEK("qwe", "asd", "zxc", "1", "2", "3"
				, "4", "5", "6");
		GEK g1 = new GEK("qwe", "asd", "zxc", "1", "2", "3"
				, "4", "5", "6");
		GEK g2 = new GEK("qweasdas", "asd", "zxc", "1", "2", "3"
				, "4", "5231", "6");

		assertEquals(g0, g1);
		assertNotEquals(g1, g2);
	}

}