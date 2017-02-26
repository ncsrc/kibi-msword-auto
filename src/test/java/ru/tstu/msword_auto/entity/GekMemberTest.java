package ru.tstu.msword_auto.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


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