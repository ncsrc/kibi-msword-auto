package ru.tstu.msword_auto.entity;


public class GekMember {
	@PrimaryKey
	private int gekMemberId;
	private final int gekHeadId; // foreign key
	private final String member;


	public GekMember(int gekHeadId, String member) {
		this.gekHeadId = gekHeadId;
		this.member = member;
	}

	public GekMember(int gekMemberId, int gekHeadId , String member) {
		this.gekMemberId = gekMemberId;
		this.gekHeadId = gekHeadId;
		this.member = member;
	}

	public void setGekMemberId(int gekMemberId) {
		this.gekMemberId = gekMemberId;
	}

	public int getGekMemberId() {
		return this.gekMemberId;
	}

	public int getGekHeadId() {
		return this.gekHeadId;
	}

	public String getMember() {
		return this.member;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		GekMember other = (GekMember) o;

		if(this.gekMemberId == other.gekMemberId && this.gekHeadId == other.gekHeadId && this.member.equals(other.member)) {
			return true;
		}

		return false;
	}

}
