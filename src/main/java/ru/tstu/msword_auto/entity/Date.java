package ru.tstu.msword_auto.entity;


import java.util.regex.Pattern;


public class Date {

	/** Entity value of GROUP_NAME from database table, primary key */
	@PrimaryKey
	private String groupName = "";

	/** Entity value of DATE_GOS from database table,
	 *  cannot be null, so needs contain at least empty string for correct sending to frontend
	 */
	private String gosDate = "";

	/** Entity value of DATE_VCR from database table,
	 *  cannot be null, so needs contain at least empty string for correct sending to frontend
	 */
	private String vcrDate = "";

	/** Parses dates for templates */
	private transient DateParser parser;


	/** Default constructor with empty strings */
	public Date() {
		this.parser = new DateParser();
	}

	/**
	 * Simple constructor, all entity values will be equal to empty strings
	 * @param groupName GROUP_NAME in db table
	 */
	public Date(String groupName) {
		this.groupName = groupName;
		this.parser = new DateParser();
	}

	/**
	 * Full constructor with all entity values.
	 * @param groupName GROUP_NAME in db table
	 * @param gosDate DATE_GOS in db table
	 * @param vcrDate DATE_VCR in db table
	 * @throws DateFormatException thrown when parameters contain invalid date format. Required: yyyy-MM-dd
	 */
	public Date(String groupName, String gosDate, String vcrDate) throws DateFormatException {
		this.groupName = groupName;

		if(!datesAreValid(gosDate, vcrDate)) {
			throw new DateFormatException("Invalid date format. Requited: yyyy-MM-dd");
		}

		this.gosDate = gosDate;
		this.vcrDate = vcrDate;
		this.parser = new DateParser();
	}


	/**
	 * @return gos date entity value
	 */
	public String getGosDate() {
		return this.gosDate;
	}

	/**
	 * @return vcr date entity value
	 */
	public String getVcrDate() {
		return this.vcrDate;
	}

	/**
	 * @return value of GROUP_NAME
	 */
	public String getGroupName() {
		return this.groupName;
	}

	/**
	 * Returns information about day of gos date in a way which document requires
	 * @return day of gos date
	 */
	public String getGosDay() {
		return parser.getGosDay();
	}

	/**
	 * Returns information about day of vcr date in a way which document requires
	 * @return day of vcr date
	 */
	public String getVcrDay() {
		return parser.getVcrDay();
	}

	/**
	 * Returns information about month of gos date in a way which document requires
	 * @return month of gos date
	 */
	public String getGosMonth() {
		return parser.getGosMonth();
	}

	/**
	 * Returns information about month of vcr date in a way which document requires
	 * @return month of vcr date
	 */
	public String getVcrMonth() {
		return parser.getVcrMonth();
	}

	/**
	 * Returns information about year of gos date in a way which document requires
	 * @return year of gos date
	 */
	public String getGosYear() {
		return parser.getGosYear();
	}

	/**
	 * Returns information about year of vcr date in a way which document requires
	 * @return year of vcr date
	 */
	public String getVcrYear() {
		return parser.getVcrYear();
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


	private boolean datesAreValid(String gosDate, String vcrDate) {
		String regex = "([0-9][0-9][0-9][0-9])-(0[1-9]|1[0-2])-(([0-2][0-9])|(3[0-1]))";
		if(Pattern.matches(regex, gosDate) && Pattern.matches(regex, vcrDate)) {
			return true;
		} else if(Pattern.matches(regex, gosDate) && "".equals(vcrDate)) {
			return true;
		} else if("".equals(gosDate) && Pattern.matches(regex, vcrDate)) {
			return true;
		} else if("".equals(gosDate) && "".equals(vcrDate)) {
			return true;
		}

		return false;
	}


	// parser for representation for templates
	// wrapper that parses date entity in a way that automation requires
	private class DateParser {


		public DateParser() {}


		public String getGosDay() {
			if("".equals(gosDate)) {
				return "";
			} else {
				return gosDate.substring(8);
			}

		}

		public String getVcrDay() {
			if("".equals(vcrDate)) {
				return "";
			} else {
				return vcrDate.substring(8);
			}
		}

		public String getGosMonth() {
			if("".equals(gosDate)) {
				return "";
			} else {
				return parseMonth(gosDate.substring(5, 7));
			}
		}

		public String getVcrMonth() {
			if("".equals(vcrDate)) {
				return "";
			} else {
				return parseMonth(vcrDate.substring(5, 7));
			}
		}

		public String getGosYear() {
			if("".equals(gosDate)) {
				return "";
			} else {
				return gosDate.substring(2, 4);
			}
		}

		public String getVcrYear() {
			if("".equals(vcrDate)) {
				return "";
			} else {
				return vcrDate.substring(2, 4);
			}
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

	}



}


















