package ru.tstu.msword_auto.entity;


import java.util.regex.Pattern;


public class Date {
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
	}

	/**
	 * Simple constructor, all entity values will be equal to empty strings
	 * @param groupName GROUP_NAME in db table
	 */
	public Date(String groupName) {
		this.groupName = groupName;
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


}


















