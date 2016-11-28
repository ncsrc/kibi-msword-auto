package ru.tstu.msword_automation.database.datasets;



public class Student
{
	@PrimaryKey
	private final int id;
	private final String fName;
	private final String lName;
	private final String midName;


	public Student(int id, String firstName, String lastName, String middleName)
	{
		this.id = id;
		this.fName = firstName;
		this.lName = lastName;
		this.midName = middleName;
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

	public String getFullName()
	{
		return this.lName + " " + this.fName + " " + this.midName;
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
				&& this.midName.equals(other.midName)){

			return true;
		}

		return false;
	}
}


















