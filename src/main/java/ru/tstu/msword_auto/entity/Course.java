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

	public Course(int studentId, String groupName, String qualification, String courseName, String profile) {
		this.courseName = courseName;
		this.groupName = groupName;
		this.studentId = studentId;
		this.qualification = qualification;
		this.code = defineCode();
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


	private String defineCode() {
		String BACHELOR_BI_CODE = "38.03.05";
		String BACHELOR_TD_CODE = "38.03.06";
		String MASTER_BI_CODE = "38.04.05";
		String MASTER_TD_CODE = "38.04.06";

		if("Торговое дело".equals(courseName) && "Бакалавр".equals(qualification)) {
			return BACHELOR_TD_CODE;
		} else if("Торговое дело".equals(courseName) && "Магистр".equals(qualification)) {
			return MASTER_TD_CODE;
		} else if("Бизнес-информатика".equals(courseName) && "Бакалавр".equals(qualification)) {
			return BACHELOR_BI_CODE;
		} else if("Бизнес-информатика".equals(courseName) && "Магистр".equals(qualification)) {
			return MASTER_BI_CODE;
		}

		return "";
	}

}
























