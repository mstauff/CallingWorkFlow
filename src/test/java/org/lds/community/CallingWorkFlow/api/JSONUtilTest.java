package org.lds.community.CallingWorkFlow.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lds.community.CallingWorkFlow.domain.Member;

/**
 * Created with IntelliJ IDEA.
 * User: AlexC
 * Date: 8/27/12
 * Time: 2:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class JSONUtilTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testParseMember() throws Exception {
        Member member1=new Member();
        Member member2=new Member();
        member1.setFirstName("alex");
        member1.setLastName("Carrasco");
        member1.setIndividualId(1234567L);

        try {
            member2= JSONUtil.parseMember(new JSONObject("{" +JSONUtil.MEMBER_FIRST + ":alex;" + JSONUtil.MEMBER_LAST + ":Carrasco ;" + JSONUtil.MEMBER_IND_ID + ":123456789"+ "}"));
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Assert.assertEquals(member1.getLastName(), member2.getLastName());

    }

    @Test
    public void testParseMemberList() throws Exception {

    }

    @Test
    public void testParseCallings() throws Exception {

    }

    @Test
    public void testParseCalling() throws Exception {

    }

    @Test
    public void testParsePosition() throws Exception {

    }

    @Test
    public void testParsePositionIds() throws Exception {

    }

    @Test
    public void testParseStatus() throws Exception {

    }

    @Test
    public void testParseStatuses() throws Exception {

    }
}
