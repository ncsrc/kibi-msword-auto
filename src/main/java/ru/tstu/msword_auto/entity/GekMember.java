package ru.tstu.msword_auto.entity;


public class GekMember
{
	@PrimaryKey
	private final String member;
	private final String head; // foreign key


	public GekMember(String head, String member)
	{
		this.head = head;
		this.member = member;
	}

	public String getMember()
	{
		return this.member;
	}

	public String getHead()
	{
		return this.head;
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		GekMember other = (GekMember) o;

		if(this.head.equals(other.head) && this.member.equals(other.member)){
			return true;
		}

		return false;
	}

}
