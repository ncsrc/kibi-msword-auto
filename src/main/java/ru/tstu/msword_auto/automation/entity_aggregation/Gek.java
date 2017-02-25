package ru.tstu.msword_auto.automation.entity_aggregation;

import ru.tstu.msword_auto.entity.GekHead;
import ru.tstu.msword_auto.entity.GekMember;

// todo javadoc
public class Gek {
    private final GekHead gekHead;
    private final GekMember gekMember;


    public Gek(GekHead gekHead, GekMember gekMember) {
        this.gekHead = gekHead;
        this.gekMember = gekMember;
    }

    public GekHead getGekHead() {
        return gekHead;
    }

    public GekMember getGekMember() {
        return gekMember;
    }

}
