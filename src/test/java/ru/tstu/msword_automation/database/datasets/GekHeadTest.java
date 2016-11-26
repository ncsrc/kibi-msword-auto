package ru.tstu.msword_automation.database.datasets;

import org.junit.Test;

import static org.junit.Assert.*;

public class GekHeadTest
{

	@Test
	public void equalsCorrectness() throws Exception
	{
		GekHead g0 = new GekHead("qwe", "asd", "zxc");
		GekHead g1 = new GekHead("qwe", "asd", "zxc");
		GekHead g2 = new GekHead("qweasdas", "asd", "zxc");

		assertEquals(g0, g1);
		assertNotEquals(g1, g2);
	}

}