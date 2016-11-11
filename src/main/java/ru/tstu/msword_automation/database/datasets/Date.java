package ru.tstu.msword_automation.database.datasets;

// TODO look for date types in mysql
public class Date
{
	private final int day;
	private final String month;
	private final int year;


	public Date(int day, String month, int year)
	{
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public int getDay()
	{
		return this.day;
	}

	public String getMonth()
	{
		return this.month;
	}

	public int getYear()
	{
		return this.year;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj == this){
			return true;
		}

		if(obj == null){
			return false;
		}

		if(this.getClass() != obj.getClass()){
			return false;
		}

		Date other = (Date) obj;
		if(this.year == other.year && this.month.equals(other.month) && this.day == other.day){
			return true;
		}

		return false;
	}

}


















