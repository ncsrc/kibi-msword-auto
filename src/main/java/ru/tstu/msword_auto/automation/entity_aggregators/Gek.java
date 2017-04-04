package ru.tstu.msword_auto.automation.entity_aggregators;

import ru.tstu.msword_auto.entity.GekHead;
import ru.tstu.msword_auto.entity.GekMember;

import java.util.List;


public class Gek {
    private final GekHead gekHead;
    private final List<GekMember> gekMembers;


    public Gek(GekHead gekHead, List<GekMember> gekMembers) {
        this.gekHead = gekHead;
        this.gekMembers = gekMembers;
    }

    public GekHead getGekHead() {
        return gekHead;
    }

    public List<GekMember> getListOfGekMembers() {
        return gekMembers;
    }

	public String getFullMembersList() {
		String list = gekMembers.get(0).getMember();
		for(int i = 1; i < gekMembers.size(); i++){
			list += ", ";
			list += gekMembers.get(i).getMember();
		}

		return list;
	}

}
