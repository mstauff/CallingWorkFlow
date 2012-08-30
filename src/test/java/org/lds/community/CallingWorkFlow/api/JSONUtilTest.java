package org.lds.community.CallingWorkFlow.api;

import android.content.ContentValues;
import android.database.Cursor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lds.community.CallingWorkFlow.domain.Calling;
import org.lds.community.CallingWorkFlow.domain.Member;
import org.lds.community.CallingWorkFlow.domain.Position;
import org.lds.community.CallingWorkFlow.domain.WorkFlowStatus;
import java.util.List;

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
      // no setup is necessary
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testParseMember() throws Exception {
        Member member1=new Member();
        Member member2=new Member();
        
        member1=createMember("Joe","Jones",123456789L) ;
        try {
            member2= JSONUtil.parseMember(new JSONObject("{" +JSONUtil.MEMBER_FIRST + ":Joe;" + JSONUtil.MEMBER_LAST + ":Jones ;" + JSONUtil.MEMBER_IND_ID + ":123456789"+ "}"));
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Assert.assertEquals(member1.getFirstName(), member2.getFirstName());
        Assert.assertEquals(member1.getLastName(), member2.getLastName());
        Assert.assertEquals(member1.getIndividualId(), member2.getIndividualId());

    }

    @Test
    public void testParseMemberList() throws Exception {

        Member member1=createMember("Joe","Jones",1L);
        Member member2=createMember("Joe","Gonzales",2L);
        Member member3=createMember("Joe","Peterson",3L);

        JSONArray listSource=new JSONArray();
        listSource.put(new JSONObject("{" + JSONUtil.MEMBER_FIRST + ":Joe;" + JSONUtil.MEMBER_LAST + ":Jones ;" + JSONUtil.MEMBER_IND_ID + ":1" + "}"));
        listSource.put(new JSONObject("{" + JSONUtil.MEMBER_FIRST + ":Joe;" + JSONUtil.MEMBER_LAST + ":Gonzales ;" + JSONUtil.MEMBER_IND_ID + ":2" + "}"));
        listSource.put(new JSONObject("{" + JSONUtil.MEMBER_FIRST + ":Joe;" + JSONUtil.MEMBER_LAST + ":Peterson ;" + JSONUtil.MEMBER_IND_ID + ":3" + "}"));

        List<Member> listResult= JSONUtil.parseMemberList(listSource);

        Assert.assertEquals(listSource.length(),listResult.size());
        Assert.assertEquals(member1.getLastName(),listResult.get(0).getLastName());
        Assert.assertEquals(member2.getIndividualId(),listResult.get(1).getIndividualId());
        Assert.assertEquals(member3.getFirstName(),listResult.get(2).getFirstName());

    }

    @Test
    public void testParseCallings() throws Exception {
        Calling calling1=createCalling(4L,"ACTIVE",1L);
        Calling calling2=createCalling(4L,"ACTIVE",2L);
        Calling calling3=createCalling(4L,"ACTIVE",3L);
        JSONArray listSource=new JSONArray();
        listSource.put(new JSONObject("{" + JSONUtil.CALLING_POS_ID + ":4;" + JSONUtil.CALLING_STATUS_NAME + ":ACTIVE ;" + JSONUtil.MEMBER_IND_ID + ":1" + "}"));
        listSource.put(new JSONObject("{" + JSONUtil.CALLING_POS_ID + ":54;" + JSONUtil.CALLING_STATUS_NAME + ":ACTIVE ;" + JSONUtil.MEMBER_IND_ID + ":2" + "}"));
        listSource.put(new JSONObject("{" + JSONUtil.CALLING_POS_ID + ":1;" + JSONUtil.CALLING_STATUS_NAME + ":ACTIVE ;" + JSONUtil.MEMBER_IND_ID + ":3" + "}"));
 
        List<Calling> listResult= JSONUtil.parseCallings(listSource);

        Assert.assertEquals(listSource.length(),listResult.size());
        Assert.assertEquals(calling1.getPositionId(), listResult.get(0).getPositionId());
        Assert.assertEquals(calling2.getIndividualId(), listResult.get(1).getIndividualId());
        Assert.assertEquals(calling3.getStatusName(), listResult.get(2).getStatusName());

    }

    @Test
    public void testParseCalling() throws Exception {
 
        Calling calling1=createCalling(54L,"ACTIVE",1234567L);
        Calling calling2=JSONUtil.parseCalling(new JSONObject("{" + JSONUtil.CALLING_POS_ID + ":54;" + JSONUtil.CALLING_IND_ID + ":1234567;" + JSONUtil.CALLING_STATUS_NAME + ":ACTIVE" + "}"));
        Assert.assertEquals(calling1.getPositionId(), calling2.getPositionId());
        Assert.assertEquals(calling1.getIndividualId(), calling2.getIndividualId());
        Assert.assertEquals(calling1.getStatusName(), calling2.getStatusName());

    }

    @Test
    public void testParsePosition() throws Exception {
        Position position1=createPosition(4L,"Bishop");
        Position position2=JSONUtil.parsePosition(new JSONObject("{" + JSONUtil.CALLING_POS_ID + ":4;" + JSONUtil.POSITION_NAME + ":Bishop" + "}"));
        Assert.assertEquals(position1.getPositionId(), position2.getPositionId());
        Assert.assertEquals(position1.getPositionName(), position2.getPositionName());

    }

    @Test
    public void testParsePositionIds() throws Exception {
        Position position1=createPosition(4L,"Bishop");
        Position position2=createPosition(1L,"Stake President");
        Position position3=createPosition(133L,"High priest Group Leader");

        JSONArray listSource=new JSONArray();
        listSource.put(new JSONObject("{" + JSONUtil.POSITION_ID + ":4;" + JSONUtil.POSITION_NAME + ":Bishop"  + "}"));
        listSource.put(new JSONObject("{" + JSONUtil.POSITION_ID + ":1;" + JSONUtil.POSITION_NAME + ":Stake President"  + "}"));
        listSource.put(new JSONObject("{" + JSONUtil.POSITION_ID + ":133;" + JSONUtil.POSITION_NAME + ":High priest Group Leader"  + "}"));

        List<Position> listResult= JSONUtil.parsePositionIds(listSource);

        Assert.assertEquals(listSource.length(),listResult.size());
        Assert.assertEquals(position1.getPositionId(), listResult.get(0).getPositionId());
        Assert.assertEquals(position2.getPositionName(), listResult.get(1).getPositionName());
        Assert.assertEquals(position3.getPositionName(), listResult.get(2).getPositionName());

    }

    @Test
    public void testParseStatus() throws Exception {
        WorkFlowStatus status1=createStatus(1,"ACTIVE",null,null,1);
        WorkFlowStatus status2=JSONUtil.parseStatus(new JSONObject("{" + JSONUtil.STATUS_COMPLETE + ":1;" + JSONUtil.STATUS_NAME + ":ACTIVE;" + JSONUtil.STATUS_ORDER + ":1" + "}"));

        Assert.assertEquals(status1.getComplete(), status2.getComplete());
        Assert.assertEquals(status1.getSequence(), status2.getSequence());
        Assert.assertEquals(status1.getStatusName(), status2.getStatusName());
    }

    @Test
    public void testParseStatuses() throws Exception {
        WorkFlowStatus status1=createStatus(1,"ACTIVE",null,null,1);
        WorkFlowStatus status2=createStatus(0,"NON_ACTIVE",null,null,2);
        WorkFlowStatus status3=createStatus(1,"PASSIVE",null,null,3);

        JSONArray listSource=new JSONArray();
        listSource.put(new JSONObject("{" + JSONUtil.STATUS_COMPLETE + ":1;" + JSONUtil.STATUS_NAME + ":ACTIVE;"  + JSONUtil.STATUS_ORDER + ":1" + "}"));
        listSource.put(new JSONObject("{" + JSONUtil.STATUS_COMPLETE + ":0;" + JSONUtil.STATUS_NAME + ":NON_ACTIVE;"  + JSONUtil.STATUS_ORDER + ":2" + "}"));
        listSource.put(new JSONObject("{" + JSONUtil.STATUS_COMPLETE + ":1;" + JSONUtil.STATUS_NAME + ":PASSIVE;"  + JSONUtil.STATUS_ORDER + ":3" + "}"));

        List<WorkFlowStatus> listResult= JSONUtil.parseStatuses(listSource);

        Assert.assertEquals(listSource.length(),listResult.size());
        Assert.assertEquals(status1.getComplete(), listResult.get(0).getComplete());
        Assert.assertEquals(status2.getSequence(), listResult.get(1).getSequence());
        Assert.assertEquals(status3.getStatusName(), listResult.get(2).getStatusName());

    }

    // HELPER METHODS
    private Member createMember(String firstName, String lastName, Long individualId){
        Member memberObj=new Member();
        memberObj.setFirstName(firstName);
        memberObj.setLastName(lastName);
        memberObj.setIndividualId(individualId);
        return memberObj;

    }

    private Calling createCalling(Long positionId, String statusName, Long individualId){
        Calling callingObj=new Calling();
        callingObj.setIndividualId(individualId);
        callingObj.setPositionId(positionId);
        callingObj.setStatusName(statusName);

        return callingObj;
    }
    
    private Position createPosition(Long positionId, String positionName){
        Position positionObj=new Position();
        positionObj.setPositionId(positionId);
        positionObj.setPositionName(positionName);

        return positionObj;
    }

    private WorkFlowStatus createStatus(int complete, String statusName, ContentValues cValues, Cursor cursor, int sequence){
        WorkFlowStatus statusObj=new WorkFlowStatus();
        statusObj.setStatusName(statusName);
        statusObj.setComplete(complete);
//        statusObj.setContent(cValues);
//        statusObj.setContent(cursor);
        statusObj.setSequence(sequence);
        
        return statusObj;
    }

}
