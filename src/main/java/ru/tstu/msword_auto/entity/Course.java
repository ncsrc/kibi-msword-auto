package ru.tstu.msword_auto.entity;


public class Course {
	@PrimaryKey
	private final int studentId; // also foreign key
	private final String groupName;
	private String code = "";
	private String qualification = "";
	private String courseName = "";
	private String profile = "";


	public Course(int studentId, String groupName) {
		this.studentId = studentId;
		this.groupName = groupName;
	}

	public Course(int studentId, String groupName, String code, String qualification, String courseName, String profile) {
		this.courseName = courseName;
		this.groupName = groupName;
		this.studentId = studentId;
		this.qualification = qualification;
		this.code = code;
		this.profile = profile;
	}


	public void setCode(String code) {
		this.code = code;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public int getStudentId() {
		return this.studentId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public String getCode() {
		return this.code;
	}

	public String getCourseName() {
		return this.courseName;
	}

	public String getInfo() {
		return this.code + " – " + this.courseName;
	}

	public String getProfile() {
		return this.profile;
	}

	public String getQualification() {
		return this.qualification;
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

		Course other = (Course) obj;
		if(this.code.equals(other.code) && this.studentId == other.studentId
				&& this.courseName.equals(other.courseName) && this.profile.equals(other.profile)
				&& this.qualification.equals(other.qualification) && this.groupName.equals(other.groupName)){

			return true;
		}

		return false;
	}

}
























