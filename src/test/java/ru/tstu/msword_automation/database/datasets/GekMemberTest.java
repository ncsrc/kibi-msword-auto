package ru.tstu.msword_automation.database.datasets;

import org.junit.Test;

import static org.junit.Assert.*;


public class GekMemberTest
{


	@Test
	public void equalsCorrectness() throws Exception
	{
		GekMember g0 = new GekMember("test", "qwz");
		GekMember g1 = new GekMember("test", "qwz");
		GekMember g2 = new GekMember("zxc", "qwz");

		assertEquals(g0, g1);
		assertNotEquals(g1, g2);
	}

}