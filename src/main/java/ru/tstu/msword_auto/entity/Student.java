package ru.tstu.msword_auto.entity;


public class Student {
	@PrimaryKey
	private final int id;

	// Именительный падеж
	private final String firstNameI;
	private final String lastNameI;
	private final String middleNameI;

	// Родительный падеж
	private final String firstNameR;
	private final String lastNameR;
	private final String middleNameR;

	// Творительный падеж
	private final String firstNameD;
	private final String lastNameD;
	private final String middleNameD;

	private final transient String initials;


	public Student(int id, String firstNameI, String lastNameI, String middleNameI,
				   String firstNameR, String lastNameR, String middleNameR,
				   String firstNameD, String lastNameD, String middleNameD) {

		this.id = id;

		// Именительный падеж
		this.firstNameI = firstNameI;
		this.lastNameI = lastNameI;
		this.middleNameI = middleNameI;

		// Родительный падеж
		this.firstNameR = firstNameR;
		this.lastNameR = lastNameR;
		this.middleNameR = middleNameR;

		// Творительный падеж
		this.firstNameD = firstNameD;
		this.lastNameD = lastNameD;
		this.middleNameD = middleNameD;

		this.initials = this.lastNameI + " " + this.firstNameI.substring(0, 1).toUpperCase() + ". " + this.middleNameI.substring(0, 1).toUpperCase() + ".";
	}

	public int getId() {
		return this.id;
	}

	public String getFirstNameI() {
		return this.firstNameI;
	}

	public String getLastNameI() {
		return this.lastNameI;
	}

	public String getMiddleNameI() {
		return this.middleNameI;
	}

	public String getFullNameI() {
		return this.lastNameI + " " + this.firstNameI + " " + this.middleNameI;
	}

	public String getFirstNameR() {
		return firstNameR;
	}

	public String getLastNameR() {
		return lastNameR;
	}

	public String getMiddleNameR() {
		return middleNameR;
	}

	public String getFullNameR() {
		return this.lastNameR + " " + this.firstNameR + " " + this.middleNameR;
	}

	public String getFirstNameT() {
		return firstNameD;
	}

	public String getLastNameT() {
		return lastNameD;
	}

	public String getMiddleNameT() {
		return middleNameD;
	}

	public String getFullNameT() {
		return this.lastNameD + " " + this.firstNameD + " " + this.middleNameD;
	}

	public String getInitials() {
		return this.initials;
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

		Student other = (Student) obj;
		if(this.id == other.id && this.firstNameI.equals(other.firstNameI) && this.lastNameI.equals(other.lastNameI) && this.middleNameI.equals(other.middleNameI)
				&& this.firstNameR.equals(other.firstNameR) && this.lastNameR.equals(other.lastNameR) && this.middleNameR.equals(other.middleNameR)
				&& this.firstNameD.equals(other.firstNameD) && this.lastNameD.equals(other.lastNameD) && this.middleNameD.equals(other.middleNameD)){

			return true;
		}

		return false;
	}


}


















