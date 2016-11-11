package ru.tstu.msword_automation.database.datasets;


public class Student
{
	private final String fName;
	private final String lName;
	private final String midName;
	private final String initials;
	private final String qualification;
	private final String courseCode;


	public Student(String firstName, String lastName, String middleName, String qualification,
				   String courseCode)
	{
		this.fName = firstName;
		this.lName = lastName;
		this.midName = middleName;
		this.qualification = qualification;
		this.courseCode = courseCode;
		this.initials = this.fName.charAt(0) + "." + this.midName.charAt(0) +". " + this.lName;
	}

	public String getFullName()
	{
		return this.lName + " " + this.fName + " " + this.midName;
	}

	public String getInitials()
	{
		return this.initials;
	}

	public String getQualification()
	{
		return this.qualification;
	}


}


















