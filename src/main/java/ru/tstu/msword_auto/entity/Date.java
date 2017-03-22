package ru.tstu.msword_auto.entity;


import java.util.HashMap;
import java.util.Map;


public class Date {
	/** Key which points on day of map with parsed data */
	private static final transient String KEY_DAY = "day";

	/** Key which points on month of map with parsed data */
	private static final transient String KEY_MONTH = "month";

	/** Key which points on year of map with parsed data*/
	private static final transient String KEY_YEAR = "year";


	/** Entity value of GROUP_NAME from database table, primary key */
	@PrimaryKey
	private final String groupName;

	/** Entity value of DATE_GOS from database table,
	 *  cannot be null, so needs contain at least empty string for correct sending to frontend
	 */
	private String gosDate = "";

	/** Entity value of DATE_VCR from database table,
	 *  cannot be null, so needs contain at least empty string for correct sending to frontend
	 */
	private String vcrDate = "";

	// TODO move to automation Template ?
	/** Map containing parsed gos date in a way which satisfy document requirements. Contains day, month and year. */
	private transient Map<String, String> parsedGosDate;

	// TODO move to automation Template ?
	/** Map containing parsed vcr date in a way which satisfy document requirements. Contains day, month and year. */
	private transient Map<String, String> parsedVcrDate;


	/**
	 * Full constructor with all entity values.
	 * @param groupName GROUP_NAME in db table
	 * @param gosDate DATE_GOS in db table
	 * @param vcrDate DATE_VCR in db table
	 * @throws DateFormatException thrown when parameters contain invalid date format. Required: yyyy-MM-dd
	 */
	public Date(String groupName, String gosDate, String vcrDate) throws DateFormatException {
		this.groupName = groupName;

		// TODO add regex validation
		// regex: ([0-9][0-9][0-9][0-9])-(0[1-9]|1[0-2])-(([0-2][0-9])|(3[0-1]))

		parsedGosDate = parseDate(gosDate);
		parsedVcrDate = parseDate(vcrDate);

		this.gosDate = gosDate;
		this.vcrDate = vcrDate;
	}

	// do i really need that ? // TODO check usage
//	public Date(String groupName, java.util.Date gosDate, java.util.Date vcrDate) {
//		this.groupName = groupName;
//		this.gosDateInLong = gosDate;
//		this.vcrDateInLong = vcrDate;
//
//		// getting parsed gos date
//		Calendar dateParser = Calendar.getInstance();
//		dateParser.setTime(gosDate);
//		parsedGosDate = parseDate(dateParser);
//
//		// getting parsed vcr date
//		dateParser.setTime(vcrDate);
//		parseDate(dateParser);
//		parsedVcrDate = parseDate(dateParser);
//
//		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
//		this.gosDate = parser.format(gosDate);
//		this.vcrDate = parser.format(vcrDate);
//	}

	/**
	 * Simple constructor, all entity values will be equal to empty strings
	 * @param groupName GROUP_NAME in db table
	 */
	public Date(String groupName) {
		this.groupName = groupName;

		parsedGosDate = new HashMap<>();
		fillParsedDateWithEmptyStrings(parsedGosDate);
		parsedVcrDate = new HashMap<>();
		fillParsedDateWithEmptyStrings(parsedVcrDate);
	}

	/**
	 * Returns information about day of gos date in a way which document requires
	 * @return day of gos date
	 */
	public String getGosDay() {
		return parsedGosDate.get(KEY_DAY);
	}

	/**
	 * Returns information about day of vcr date in a way which document requires
	 * @return day of vcr date
	 */
	public String getVcrDay() {
		return parsedVcrDate.get(KEY_DAY);
	}

	/**
	 * Returns information about month of gos date in a way which document requires
	 * @return month of gos date
	 */
	public String getGosMonth() {
		return parsedGosDate.get(KEY_MONTH);
	}

	/**
	 * Returns information about month of vcr date in a way which document requires
	 * @return month of vcr date
	 */
	public String getVcrMonth() {
		return parsedVcrDate.get(KEY_MONTH);
	}

	/**
	 * Returns information about year of gos date in a way which document requires
	 * @return year of gos date
	 */
	public String getGosYear() {
		return parsedGosDate.get(KEY_YEAR);
	}

	/**
	 * Returns information about year of vcr date in a way which document requires
	 * @return year of vcr date
	 */
	public String getVcrYear() {
		return parsedVcrDate.get(KEY_YEAR);
	}

	/**
	 * @return value of GROUP_NAME
	 */
	public String getGroupName() {
		return this.groupName;
	}

	/** Compares just by entity values */
	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}

		if(obj == null) {
			return false;
		}

		if(this.getClass() != obj.getClass()) {
			return false;
		}

		Date other = (Date) obj;
		if(groupName.equals(other.groupName) && gosDate.equals(other.gosDate) && vcrDate.equals(other.vcrDate)) {
			return true;
		}

		return false;
	}


	private Map<String, String> parseDate(String date) {
		Map<String, String> parsedData = new HashMap<>();
		String day = date.substring(8);
		String month = parseMonth(date.substring(5, 7));
		String year = date.substring(2, 4);

		parsedData.put(KEY_DAY, day);
		parsedData.put(KEY_MONTH, month);
		parsedData.put(KEY_YEAR, year);

		return parsedData;
	}

	private String parseMonth(String month) {
		switch(month)
		{
			case "01":
				return "января";
			case "02":
				return "февраля";
			case "03":
				return "марта";
			case "04":
				return "апреля";
			case "05":
				return "мая";
			case "06":
				return "июня";
			case "07":
				return "июля";
			case "08":
				return "августа";
			case "09":
				return "сентября";
			case "10":
				return "октября";
			case "11":
				return "ноября";
			case "12":
				return "декабря";
			default:
				return "";
		}
	}

	private void fillParsedDateWithEmptyStrings(Map<String, String> parsedDate) {
		parsedDate.put(KEY_DAY, "");
		parsedDate.put(KEY_MONTH, "");
		parsedDate.put(KEY_YEAR, "");
	}

}


















