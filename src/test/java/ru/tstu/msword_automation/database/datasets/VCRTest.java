package ru.tstu.msword_automation.database.datasets;

import org.junit.Test;

import static org.junit.Assert.*;

public class VCRTest
{

	@Test
	public void equalsCorrectness() throws Exception
	{
		VCR vcr0 = new VCR("test", "test1", "test2");
		VCR vcr1 = new VCR("test", "test1", "test2");
		VCR vcr2 = new VCR("test", "11test1", "test2asd");

		assertEquals(vcr0, vcr1);
		assertNotEquals(vcr1, vcr2);
	}

}