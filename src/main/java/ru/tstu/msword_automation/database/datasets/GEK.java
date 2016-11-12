package ru.tstu.msword_automation.database.datasets;


public class GEK	// stands for "Государственная экзаменационная комиссия"
{
	private final String head;
	private final String subhead;
	private final String secretary;
	private final String first;
	private final String second;
	private final String third;
	private final String fourth;
	private final String fifth;
	private final String sixth;


	public GEK(String head, String subhead, String secretary, String first, String second, String third,
				String fourth, String fifth, String sixth)	// too many args, think about some better way
	{
		this.head = head;
		this.subhead = subhead;
		this.secretary = secretary;
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
		this.fifth = fifth;
		this.sixth = sixth;
	}

	public String getHead()
	{
		return this.head;
	}

	public String getSubhead()
	{
		return this.subhead;
	}

	public String getSecretary()
	{
		return this.secretary;
	}

	public String getFirstMember()
	{
		return this.first;
	}

	public String getSecondMember()
	{
		return this.second;
	}

	public String getThirdMember()
	{
		return this.third;
	}

	public String getFourthMember()
	{
		return this.fourth;
	}

	public String getFifthMember()
	{
		return this.fifth;
	}

	public String getSixthMember()
	{
		return this.sixth;
	}

	public String getFullMemberList()
	{
		return getFirstMember() + ", " + getSecondMember() + ", " +
				getThirdMember() + ", " + getFourthMember() + ", " +
				getFifthMember() + ", " + getSixthMember();
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

		GEK other = (GEK) obj;
		if(this.head.equals(other.head) && this.subhead.equals(other.subhead) && this.secretary.equals(other.secretary)
				&& this.first.equals(other.first) && this.second.equals(other.second) && this.third.equals(other.third)
				&& this.fourth.equals(other.fourth) && this.fifth.equals(other.fifth) && this.sixth.equals(other.sixth)){

			return true;
		}

		return false;
	}

}





















