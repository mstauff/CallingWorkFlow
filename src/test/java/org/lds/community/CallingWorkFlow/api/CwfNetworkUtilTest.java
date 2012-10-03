package org.lds.community.CallingWorkFlow.api;

import com.xtremelabs.robolectric.Robolectric;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lds.community.CallingWorkFlow.InjectedTestRunner;
import org.lds.community.CallingWorkFlow.Utilities.TestUtils;
import org.lds.community.CallingWorkFlow.domain.Calling;
import org.lds.community.CallingWorkFlow.domain.Member;
import org.lds.community.CallingWorkFlow.domain.Position;
import org.lds.community.CallingWorkFlow.domain.WorkFlowStatus;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: AlexC
 * Date: 8/27/12
 * Time: 2:41 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(InjectedTestRunner.class)
public class CwfNetworkUtilTest{

    @Inject
    CwfNetworkUtil networkUtil;

    @Before
    public void setup(){
         networkUtil = new CwfNetworkUtil();

    }

    // todo other test is to not use  getFakeHttpLayer and use Robolectric to mock the rest calls
    // test the json coming back from the server


    @Test
    public void testUpdateCallingTest() throws Exception {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        Calling calling1 = TestUtils.createCallingObj( 22L, "APPROVED", 5555L,false );

        networkUtil.updateCalling( calling1 );
        List<Calling> callingListFromDB = networkUtil.getPendingCallings();

        Calling resultCalling=  TestUtils.getCallingObjectFromList(callingListFromDB,calling1.getIndividualId(),calling1.getPositionId());
        Assert.assertTrue("Calling wasn't saved", TestUtils.isCallingFoundOnList(callingListFromDB, calling1.getIndividualId(), calling1.getPositionId()));

        TestUtils.assertEntityEquals(calling1,resultCalling, "");
        networkUtil.deleteCalling(calling1);

    }

    @Test
    public void testCallingChangeStatusTest(){
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        networkUtil = new CwfNetworkUtil();

        List<WorkFlowStatus> wfList=networkUtil.getStatuses();
        List<Member> mList=networkUtil.getWardList();
        List<Calling> cList=networkUtil.getCallings();

        if (!cList.isEmpty()){
            Calling calling1 = TestUtils.createCallingObj( cList.get(0).getPositionId(), wfList.get(0).getStatusName(), mList.get(0).getIndividualId(),false );
            networkUtil.updateCalling(calling1);

            List<Calling> callingPendingList2= networkUtil.getPendingCallings();
            Calling resultCallingModify=  TestUtils.getCallingObjectFromList(callingPendingList2, calling1.getIndividualId(), calling1.getPositionId());

            TestUtils.assertEntityEquals(calling1,resultCallingModify,"");

            // MODIFY CALLINGS STATUS
            resultCallingModify.setStatusName( getStatusFromList(wfList,true).getStatusName());
            networkUtil.updateCalling(resultCallingModify);

            // GET RESULTS
            List<Calling> callingCompletedList= networkUtil.getPendingCallings();
            Calling resultCallingCompleted=  TestUtils.getCallingObjectFromList(callingCompletedList, resultCallingModify.getIndividualId(), resultCallingModify.getPositionId());

            TestUtils.assertEntityEquals(resultCallingModify,resultCallingCompleted,"");

            networkUtil.deleteCalling( resultCallingCompleted );
        }
    }

    @Test
    public void deleteCallingTest(){

        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        Calling calling1 = TestUtils.createCallingObj(22L, "APPROVED", 5555L,false );
        Calling calling2 = TestUtils.createCallingObj(23L, "APPROVED", 5554L,false );
        networkUtil.updateCalling( calling1 );
        networkUtil.updateCalling( calling2 );

        networkUtil.deleteCalling( calling1 );

        List<Calling> callingPendingList= networkUtil.getPendingCallings();
        Calling resultCallingDeleted=  TestUtils.getCallingObjectFromList(callingPendingList, calling1.getIndividualId(), calling1.getPositionId());
        Assert.assertTrue("The callings was not deleted",resultCallingDeleted==null);

        networkUtil.deleteCalling( calling2 );
    }

    @Test
    public void getWorkFlowStatusTest(){
        try {
            Robolectric.getFakeHttpLayer().interceptHttpRequests(false);

            List<WorkFlowStatus> wfList= networkUtil.getStatuses();
            Assert.assertTrue( "Status list was empty",wfList.size()>=0);

        } catch (Exception name) {
            Assert.assertFalse("Exception found:  " + name,false);
        }
    }

    @Test
    public void getPositionIdsTest(){
        try {
            Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
            List<Position> pList= networkUtil.getPositionIds();
            Assert.assertTrue( "The position list was empty",pList.size()>=0);
        } catch (Exception name) {
            Assert.assertFalse("Exception found:  " + name,false);
        }
    }

    @Test
    public void getMemberListTest(){
        try {
            Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
            List<Member> wardList= networkUtil.getWardList();
            Assert.assertTrue( "Ward member list was empty",wardList.size()>=0);
        } catch (Exception name) {
            Assert.assertFalse("Exception found:  " + name,false);
        }
    }

    @Test
    public void getMemberListMockTest() {
        String json = "[{\"lastName\":\"Doe\",\"firstName\":\"John\",\"individualId\":\"1111\"},{\"lastName\":\"Doe\",\"firstName\":\"Jane\",\"individualId\":\"2222\"}]";
        httpMockStuff(json);

        List<Member> members = networkUtil.getWardList();
        Member firstMember = members.get(0);
        Assert.assertEquals( "last name doesn't match", "Doe", firstMember.getLastName() );
        Assert.assertEquals( "first name doesn't match", "John", firstMember.getFirstName() );
        Assert.assertEquals( "individualId doesn't match", "1111",String.valueOf(firstMember.getIndividualId()) );
    }


    @Test
    public void getPositionListMockTest() {
        String json = "[{\"positionId\":\"1\",\"positionName\":\"Stake President\"},{\"positionId\":\"4\",\"positionName\":\"Bishop\"}]";
        httpMockStuff(json);

        List<Position> positionList = networkUtil.getPositionIds();
        Position firstPosition = positionList.get(0);
        Assert.assertEquals( "position name doesn't match", "Stake President", firstPosition.getPositionName());
        Assert.assertEquals( "positionId doesn't match", "1", String.valueOf(firstPosition.getPositionId()));

    }

    @Test
    public void getStatusListMockTest() {
        String json = "[{\"statusName\":\"SUBMITTED\",\"isComplete\":\"false\",\"sortOrder\":\"0\"},{\"statusName\":\"SET_APART\",\"isComplete\":\"true\",\"sortOrder\":\"2\"}]";
        httpMockStuff(json);

        List<WorkFlowStatus> statusList = networkUtil.getStatuses();
        WorkFlowStatus firstStatus = statusList.get(0);
        Assert.assertEquals( "Status name doesn't match", "SUBMITTED", firstStatus.getStatusName());
        Assert.assertEquals( "isComplete doesn't match", false, firstStatus.getComplete());
        Assert.assertEquals( "Sequence doesn't match", "0",String.valueOf(firstStatus.getSequence()));

    }

//    @Test
//    public void getCallingsListMockTest(){
//        String json = "[{\"individualId\":\"1111L\",\"positionId\":\"1L\",\"statusName\":\"SUBMITTED\", \"assignedTo\":\"0L\",\"synced\":\"false\" }, " +
//                       "{\"individualId\":\"2222L\",\"positionId\":\"4L\",\"statusName\":\"SET_APART\", \"assignedTo\":\"55555L\",\"synced\":\"true\" }   ]";
//        httpMockStuff(json);
//
//        List<Calling> callingList = networkUtil.getCompletedCallings();
//        Calling firstCalling = callingList.get(0);
//
//        Assert.assertEquals( "IndividualId doesn't match", "1111", String.valueOf(firstCalling.getIndividualId()));
//        Assert.assertEquals( "IndividualId doesn't match", "4", String.valueOf(firstCalling.getPositionId()));

//        Assert.assertEquals( "isComplete doesn't match", false, firstStatus.getComplete());
//        Assert.assertEquals( "Sequence doesn't match", "0",String.valueOf(firstStatus.getSequence()));

//        setIndividualId(individualId);
//        setPositionId(positionId);
//        setStatusName(statusName);
//        setAssignedTo(assignedTo);
//        setIsSynced(synced);


//    }

    //------------------------------------------------------------------------------------------------------------------
    // UTIL METHODS
    private WorkFlowStatus getStatusFromList(List<WorkFlowStatus> statuses, Boolean isCompleted){
        WorkFlowStatus status=new WorkFlowStatus();
        for(WorkFlowStatus wf: statuses){
            if (wf.getComplete()==isCompleted){
               status= wf;
               break;
            }
        }
        return status;
    }

    private void httpMockStuff(String json){
        Robolectric.getFakeHttpLayer().interceptHttpRequests(true);
        HttpResponse res = new DefaultHttpResponseFactory().newHttpResponse(HttpVersion.HTTP_1_1, 200, null);
        BasicHttpEntity entity1 = new BasicHttpEntity();
        entity1.setContentType("application/json");
        entity1.setContent( new ByteArrayInputStream( json.getBytes() ));
        res.setEntity(entity1);
        Robolectric.addPendingHttpResponse(res );

    }

}
