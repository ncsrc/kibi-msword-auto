package ru.tstu.msword_auto.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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