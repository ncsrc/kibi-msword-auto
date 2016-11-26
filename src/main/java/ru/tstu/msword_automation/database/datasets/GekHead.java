package ru.tstu.msword_automation.database.datasets;


public class GekHead    // gek stands for "Государственная экзаменационная комиссия"
{
	private final String head;
	private final String subhead;
	private final String secretary;



	public GekHead(String head, String subhead, String secretary)
	{
		this.head = head;
		this.subhead = subhead;
		this.secretary = secretary;
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

		GekHead other = (GekHead) obj;
		if(this.head.equals(other.head) && this.subhead.equals(other.subhead) && this.secretary.equals(other.secretary)){
			return true;
		}

		return false;
	}

}





















