package ru.tstu.msword_auto.automation.entity_aggregation;

import org.junit.Test;
import ru.tstu.msword_auto.entity.GekHead;
import ru.tstu.msword_auto.entity.GekMember;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class GekTest {
    private final static String MEMBERS_LIST = "asd, qwe, zxc";
    private final Gek gek;
    private final GekHead gekHead;
    private final List<GekMember> gekMembers;


    /*
        aggregates GekHead and GekMember

        api:
        delegating methods

     */

    public GekTest() {
        this.gekHead = new GekHead("ads", "ads", "ads");
        this.gekMembers = Arrays.asList(
                new GekMember("asd", "asd"),
                new GekMember("asd", "qwe"),
                new GekMember("asd", "zxc"));

        this.gek = new Gek(gekHead, gekMembers);
    }


    @Test
    public void whenGetGekHeadThenCorrectData() throws Exception {
        GekHead actual = this.gek.getGekHead();
        assertEquals(this.gekHead, actual);
    }

    @Test
    public void whenGetGekMemberThenCorrectData() throws Exception {
        List<GekMember> actual = this.gek.getListOfGekMembers();
        assertEquals(this.gekMembers, actual);
    }

    @Test
    public void whenGetGekMembersInStringThenCorrectRepresentation() throws Exception {
        String actual = gek.getFullMembersList();
        assertEquals(MEMBERS_LIST, actual);
    }



}






















