package ru.tstu.msword_automation.database.datasets;


public class Course
{
	@PrimaryKey
	private final String code;
	private final int studentId; // foreign key
	private final String qualification;
	private final String name;
	private final String profile;


	public Course(String code, int studentId, String qualification, String name, String profile)
	{
		this.name = name;
		this.studentId = studentId;
		this.qualification = qualification;
		this.code = code;
		this.profile = profile;
	}


	public String getCode()
	{
		return this.code;
	}

	public int getStudentId()
	{
		return this.studentId;
	}

	public String getName()
	{
		return this.name;
	}

//	public String getInfo()	// TODO move this to business-logic module
//	{
//		return this.code + " – " + this.name;
//	}

	public String getProfile()
	{
		return this.profile;
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

		Course other = (Course) obj;
		if(this.code.equals(other.code) && this.studentId == other.studentId
				&& this.name.equals(other.name) && this.profile.equals(other.profile)
				&& this.qualification.equals(other.qualification)){

			return true;
		}

		return false;
	}

}
























