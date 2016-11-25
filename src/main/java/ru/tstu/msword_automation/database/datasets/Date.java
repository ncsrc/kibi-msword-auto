package ru.tstu.msword_automation.database.datasets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

// TODO refactor
public class Date
{
	private final int id;
	private final String gosDate;
	private final String vcrDate;
	private final transient java.util.Date gosDateWrapper;
	private final transient java.util.Date vcrDateWrapper;
	private final transient Calendar gosParser = Calendar.getInstance();
	private final transient Calendar vcrParser = Calendar.getInstance();
	// TODO fix primary key

	public Date(int id, String gosDate, String vcrDate) throws DateFormatException
	{
		this.id = id;
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
		try{
			this.gosDateWrapper = parser.parse(gosDate);
			this.vcrDateWrapper = parser.parse(vcrDate);
		}catch(ParseException e){
			throw new DateFormatException("Date format should be like yyyy-MM-dd");
		}

		gosParser.setTime(this.gosDateWrapper);
		vcrParser.setTime(this.vcrDateWrapper);

		this.gosDate = gosDate;
		this.vcrDate = vcrDate;
	}

	public Date(int id, java.util.Date gosDate, java.util.Date vcrDate)
	{
		this.id = id;
		this.gosDateWrapper = gosDate;
		this.vcrDateWrapper = vcrDate;

		gosParser.setTime(this.gosDateWrapper);
		vcrParser.setTime(this.vcrDateWrapper);

		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
		this.gosDate = parser.format(gosDate);
		this.vcrDate = parser.format(vcrDate);
	}

	public int getGosDay()
	{
		return gosParser.get(Calendar.DAY_OF_MONTH);
	}

	public int getVcrDay()
	{
		return vcrParser.get(Calendar.DAY_OF_MONTH);
	}

	public String getGosMonth()
	{
		return parseMonth(gosParser);
	}

	public String getVcrMonth()
	{
		return parseMonth(vcrParser);
	}

	public int getGosYear()
	{
		return gosParser.get(Calendar.YEAR);
	}

	public int getVcrYear()
	{
		return vcrParser.get(Calendar.YEAR);
	}

	public int getId()
	{
		return this.id;
	}

	public java.util.Date getRawGosDate()
	{
		return this.gosDateWrapper;
	}

	public java.util.Date getRawVcrDate()
	{
		return this.vcrDateWrapper;
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
		if(this.gosDateWrapper.equals(other.gosDateWrapper) && this.vcrDateWrapper.equals(other.vcrDateWrapper)){
			return true;
		}

		return false;
	}


	private String parseMonth(Calendar parser)
	{
		int month = parser.get(Calendar.MONTH);
		switch(month)
		{
			case 0:
				return "января";
			case 1:
				return "февраля";
			case 2:
				return "марта";
			case 3:
				return "апреля";
			case 4:
				return "мая";
			case 5:
				return "июня";
			case 6:
				return "июля";
			case 7:
				return "августа";
			case 8:
				return "сентября";
			case 9:
				return "октября";
			case 10:
				return "ноября";
			case 11:
				return "декабря";
			default:
				return "";
		}
	}

}


















