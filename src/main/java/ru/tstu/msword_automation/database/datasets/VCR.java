package ru.tstu.msword_automation.database.datasets;


public class VCR	// stands for "Выпускная квалификационная работа"
{
	private final String name;
	private final String head;
	private final String reviewer;


	public VCR(String name, String head, String reviewer)
	{
		this.name = name;
		this.head = head;
		this.reviewer = reviewer;
	}

	public String getName()
	{
		return this.name;
	}

	public String getHeadName()
	{
		return this.head;
	}

	public String getReviewerName()
	{
		return this.reviewer;
	}


}
