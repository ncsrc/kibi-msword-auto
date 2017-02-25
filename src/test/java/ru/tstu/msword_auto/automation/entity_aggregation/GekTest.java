package ru.tstu.msword_auto.automation.entity_aggregation;

import org.junit.Test;
import ru.tstu.msword_auto.entity.GekHead;
import ru.tstu.msword_auto.entity.GekMember;

import static org.junit.Assert.assertEquals;


public class GekTest {
    private final Gek gek;
    private final GekHead gekHead;
    private final GekMember gekMember;


    /*
        aggregates GekHead and GekMember

        api:
        delegating methods

     */

    public GekTest() {
        this.gekHead = new GekHead("ads", "ads", "ads");
        this.gekMember = new GekMember("ads", "ads");
        this.gek = new Gek(gekHead, gekMember);
    }


    @Test
    public void whenGetGekHeadThenCorrectData() throws Exception {
        GekHead actual = this.gek.getGekHead();
        assertEquals(this.gekHead, actual);
    }

    @Test
    public void whenGetGekMemberThenCorrectData() throws Exception {
        GekMember actual = this.gek.getGekMember();
        assertEquals(this.gekMember, actual);
    }



}






















