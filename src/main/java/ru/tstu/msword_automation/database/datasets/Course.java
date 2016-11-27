package ru.tstu.msword_automation.database.datasets;


public class Course
{
	@PrimaryKey
	private final String code;
	private final String name;
	private final String spec;


	public Course(String code, String name, String spec)
	{
		this.name = name;
		this.code = code;
		this.spec = spec;
	}


	public String getCode()
	{
		return this.code;
	}

	public String getName()
	{
		return this.name;
	}

//	public String getInfo()	// TODO move this to business-logic module
//	{
//		return this.code + " – " + this.name;
//	}

	public String getSpec()
	{
		return this.spec;
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
		if(this.code.equals(other.code) && this.name.equals(other.name) && this.spec.equals(other.spec)){
			return true;
		}

		return false;
	}

}
























