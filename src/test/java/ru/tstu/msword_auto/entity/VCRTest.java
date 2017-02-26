package ru.tstu.msword_auto.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class VCRTest
{

	@Test
	public void equalsCorrectness() throws Exception
	{
		VCR vcr0 = new VCR(1, "test", "test1", "test2");
		VCR vcr1 = new VCR(1, "test", "test1", "test2");
		VCR vcr2 = new VCR(2, "test", "11test1", "test2asd");

		assertEquals(vcr0, vcr1);
		assertNotEquals(vcr1, vcr2);
	}

}