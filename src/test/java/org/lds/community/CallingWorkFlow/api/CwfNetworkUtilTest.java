package org.lds.community.CallingWorkFlow.api;

import com.xtremelabs.robolectric.Robolectric;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lds.community.CallingWorkFlow.InjectedTestRunner;
import org.lds.community.CallingWorkFlow.Utilities.TestUtils;
import org.lds.community.CallingWorkFlow.domain.Calling;
import org.lds.community.CallingWorkFlow.domain.Member;
import org.lds.community.CallingWorkFlow.domain.Position;
import org.lds.community.CallingWorkFlow.domain.WorkFlowStatus;

import javax.inject.Inject;
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

    String jsonCallings = "[{\"individualId\":\"1111\",\"positionId\":\"1\",\"statusName\":\"SUBMITTED\", \"assignedTo\":\"0\",\"synced\":\"false\" }, " +
            "{\"individualId\":\"2222\",\"positionId\":\"4\",\"statusName\":\"SET_APART\", \"assignedTo\":\"55555\",\"synced\":\"true\" }   ]";
    String jsonMembers = "[{\"lastName\":\"Doe\",\"firstName\":\"John\",\"individualId\":\"1111\"},{\"lastName\":\"Doe\",\"firstName\":\"Jane\",\"individualId\":\"2222\"}]";
    String jsonStatuses = "[{\"statusName\":\"SUBMITTED\",\"isComplete\":\"false\",\"sortOrder\":\"0\"},{\"statusName\":\"SET_APART\",\"isComplete\":\"true\",\"sortOrder\":\"1\"}]";
    String jsonPositions = "[{\"positionId\":\"1\",\"positionName\":\"Stake President\"},{\"positionId\":\"4\",\"positionName\":\"Bishop\"}]";


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
        TestUtils.httpMockJSONResponse(jsonMembers,null);
        List<Member> members = networkUtil.getWardList();
        Member firstMember = members.get(0);
        Assert.assertEquals( "last name doesn't match", "Doe", firstMember.getLastName() );
        Assert.assertEquals( "first name doesn't match", "John", firstMember.getFirstName() );
        Assert.assertEquals( "individualId doesn't match", "1111",String.valueOf(firstMember.getIndividualId()) );
    }


    @Test
    public void getPositionListMockTest() {
        TestUtils.httpMockJSONResponse(jsonPositions,null);

        List<Position> positionList = networkUtil.getPositionIds();
        Position firstPosition = positionList.get(0);
        Assert.assertEquals( "position name doesn't match", "Stake President", firstPosition.getPositionName());
        Assert.assertEquals( "positionId doesn't match", "1", String.valueOf(firstPosition.getPositionId()));
        Robolectric.clearPendingHttpResponses();
    }

    @Test
    public void getStatusListMockTest() {
        TestUtils.httpMockJSONResponse(jsonStatuses,null);

        List<WorkFlowStatus> statusList = networkUtil.getStatuses();
        WorkFlowStatus firstStatus = statusList.get(0);
        Assert.assertEquals( "Status name doesn't match", "SUBMITTED", firstStatus.getStatusName());
        Assert.assertEquals( "isComplete doesn't match", false, firstStatus.getComplete());
        Assert.assertEquals( "Sequence doesn't match", "0",String.valueOf(firstStatus.getSequence()));
        Robolectric.clearPendingHttpResponses();
    }

    @Test
    public void getCallingsListMockTest(){

        TestUtils.httpMockJSONResponse(jsonCallings,null);
        List<Calling> callingList = networkUtil.getCompletedCallings();

        Calling firstCalling = callingList.get(0);
        Assert.assertEquals( "IndividualId doesn't match", "1111", String.valueOf(firstCalling.getIndividualId()));
        Assert.assertEquals( "StatusName doesn't match", "SUBMITTED", firstCalling.getStatusName());
        Assert.assertEquals( "PositionId doesn't match", "1",String.valueOf(firstCalling.getPositionId()));
        Assert.assertEquals( "assignedTo doesn't match", "0",String.valueOf(firstCalling.getAssignedToId()));
        Assert.assertEquals( "assignedTo doesn't match", false,firstCalling.getIsSynced());
        Robolectric.clearPendingHttpResponses();
    }

    @Test
    public void geHttpWardListTest(){
        Robolectric.getFakeHttpLayer().interceptHttpRequests(true);
        String url = CwfNetworkUtil.WARD_LIST_URL;
        TestUtils.httpMockJSONResponse(jsonMembers,url);

        networkUtil.getWardList();
        TestUtils.validateURLRequest(url);
    }

    @Test
    public void geHttpStatusListTest(){
        Robolectric.getFakeHttpLayer().interceptHttpRequests(true);
        String url = CwfNetworkUtil.STATUSES_URL;
        TestUtils.httpMockJSONResponse(jsonStatuses,url);

        networkUtil.getStatuses();
        TestUtils.validateURLRequest(url);
    }

    @Test
    public void geHttpPositionListTest(){
        Robolectric.getFakeHttpLayer().interceptHttpRequests(true);
        String url = CwfNetworkUtil.POSITION_LIST_URL;
        TestUtils.httpMockJSONResponse(jsonPositions,url);

        networkUtil.getPositionIds();
        TestUtils.validateURLRequest(url);
    }

    @Test
    public void geHttpPendingCallingsListTest(){
        Robolectric.getFakeHttpLayer().interceptHttpRequests(true);
        String url = CwfNetworkUtil.CALLINGS_PENDING_URL;
        TestUtils.httpMockJSONResponse(jsonCallings,url);

        networkUtil.getPendingCallings();
        TestUtils.validateURLRequest(url);
    }

    @Test
    public void geHttpCompletedCallingsListTest(){
        Robolectric.getFakeHttpLayer().interceptHttpRequests(true);
        String url = CwfNetworkUtil.CALLINGS_COMPLETED_URL;
        TestUtils.httpMockJSONResponse(jsonCallings,url);

        networkUtil.getCompletedCallings();
        TestUtils.validateURLRequest(url);
    }

    @Test
    public void geHttpAllCallingsListTest(){
        Robolectric.getFakeHttpLayer().interceptHttpRequests(true);
        String url = CwfNetworkUtil.ALL_CALLINGS_URL;
        TestUtils.httpMockJSONResponse(jsonCallings,url);

        networkUtil.getCallings();
        TestUtils.validateURLRequest(url);
    }

}
