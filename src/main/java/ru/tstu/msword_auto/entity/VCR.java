package ru.tstu.msword_auto.entity;


public class VCR	// stands for "Выпускная квалификационная работа"
{
	private final int student_id; // foreign key

	@PrimaryKey
	private final String name;
	private final String head;
	private final String reviewer;


	public VCR(int student_id, String name, String head, String reviewer)
	{
		this.student_id = student_id;
		this.name = name;
		this.head = head;
		this.reviewer = reviewer;
	}

	public int getStudentId()
	{
		return this.student_id;
	}

	public String getName()
	{
		return this.name;
	}

	public String getHeadName()
	{
		return this.head;
	}

	public String getReviewerName()
	{
		return this.reviewer;
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

		VCR other = (VCR) obj;
		if(this.name.equals(other.name) && this.head.equals(other.head) && this.reviewer.equals(other.reviewer)){
			return true;
		}

		return false;
	}

}























