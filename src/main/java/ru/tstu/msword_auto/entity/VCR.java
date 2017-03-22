package ru.tstu.msword_auto.entity;


// stands for "Выпускная квалификационная работа"
public class VCR {
	private final int studentId; // foreign key

	@PrimaryKey
	private final String name;
	private String head = "";
	private String reviewer = "";


	public VCR(int studentId, String name) {
		this.studentId = studentId;
		this.name = name;
	}

	public VCR(int studentId, String name, String head, String reviewer) {
		this.studentId = studentId;
		this.name = name;
		this.head = head;
		this.reviewer = reviewer;
	}

	public int getStudentId() {
		return this.studentId;
	}

	public String getName() {
		return this.name;
	}

	public String getHeadName() {
		return this.head;
	}

	public String getReviewerName() {
		return this.reviewer;
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

		VCR other = (VCR) obj;
		if(
				this.studentId == other.studentId
				&& this.name.equals(other.name)
				&& this.head.equals(other.head)
				&& this.reviewer.equals(other.reviewer)
				)
		{
			return true;
		}


		return false;
	}

}























