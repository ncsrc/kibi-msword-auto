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
	private final String firstNameT;
	private final String lastNameT;
	private final String middleNameT;

	private final transient String initials;


	public Student(int id, String firstNameI, String lastNameI, String middleNameI,
				   String firstNameR, String lastNameR, String middleNameR,
				   String firstNameT, String lastNameT, String middleNameT) {

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
		this.firstNameT = firstNameT;
		this.lastNameT = lastNameT;
		this.middleNameT = middleNameT;

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
		return firstNameT;
	}

	public String getLastNameT() {
		return lastNameT;
	}

	public String getMiddleNameT() {
		return middleNameT;
	}

	public String getFullNameT() {
		return this.lastNameT + " " + this.firstNameT + " " + this.middleNameT;
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
				&& this.firstNameT.equals(other.firstNameT) && this.lastNameT.equals(other.lastNameT) && this.middleNameT.equals(other.middleNameT)){

			return true;
		}

		return false;
	}


}


















