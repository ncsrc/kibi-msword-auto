package ru.tstu.msword_automation.database.datasets;



public class Student
{
	@PrimaryKey
	private final int id;
	private final String fName;
	private final String lName;
	private final String midName;
	private final String initials;	// TODO move this field and method to upstream module
	private final String qualification;
	private final String courseCode;


	public Student(int id, String firstName, String lastName, String middleName, String qualification,
				   String courseCode)
	{
		this.id = id;
		this.fName = firstName;
		this.lName = lastName;
		this.midName = middleName;
		this.initials = this.fName.charAt(0) + "." + this.midName.charAt(0) +". " + this.lName;
		this.qualification = qualification;
		this.courseCode = courseCode;
	}

	public int getId()
	{
		return this.id;
	}

	public String getFirstName()
	{
		return this.fName;
	}

	public String getLastName()
	{
		return this.lName;
	}

	public String getMiddleName()
	{
		return this.midName;
	}

	public String getCourseCode()
	{
		return this.courseCode;
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

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj){
			return true;
		}

		if(obj == null){
			return false;
		}

		if(this.getClass() != obj.getClass()){
			return false;
		}

		Student other = (Student) obj;
		if(this.id == other.id && this.fName.equals(other.fName) && this.lName.equals(other.lName)
				&& this.midName.equals(other.midName) && this.qualification.equals(other.qualification)
				&& this.courseCode.equals(other.courseCode)){

			return true;
		}

		return false;
	}
}


















