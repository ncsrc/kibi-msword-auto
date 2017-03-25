package ru.tstu.msword_auto.entity;


// gek stands for "Государственная экзаменационная комиссия"
public class GekHead {
	@PrimaryKey
	private int gekId;
	private final String courseName;
	private String head = "";
	private String subhead = "";
	private String secretary = "";


	public GekHead(String courseName) {
		this.courseName = courseName;
	}

	public GekHead(String courseName, String head, String subhead, String secretary) {
		this.courseName = courseName;
		this.head = head;
		this.subhead = subhead;
		this.secretary = secretary;
	}

	public int getGekId() {
		return this.gekId;
	}

	public String getHead() {
		return this.head;
	}

	public String getSubhead() {
		return this.subhead;
	}

	public String getSecretary() {
		return this.secretary;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setGekId(int gekId) {
		this.gekId = gekId;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public void setSubhead(String subhead) {
		this.subhead = subhead;
	}

	public void setSecretary(String secretary) {
		this.secretary = secretary;
	}

	@Override
	public boolean equals(Object obj) {
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
		if(
				this.gekId == other.gekId
				&& this.courseName.equals(other.courseName)
				&& this.head.equals(other.head)
				&& this.subhead.equals(other.subhead)
				&& this.secretary.equals(other.secretary)
				)
		{

			return true;
		}


		return false;
	}

}





















