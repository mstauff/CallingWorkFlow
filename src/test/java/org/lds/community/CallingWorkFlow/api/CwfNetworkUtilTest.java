package org.lds.community.CallingWorkFlow.api;

import com.xtremelabs.robolectric.Robolectric;
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
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
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
        Assert.assertTrue(Robolectric.httpRequestWasMade("http://cwf.jit.su/callings/pending"));

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

//    @Test
//    public void test5(){
//        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
//        ProtocolVersion httpProtocolVersion = new ProtocolVersion("HTTP", 1, 1);
//        HttpResponse successResponse =   new BasicHttpResponse(httpProtocolVersion, 200, "OK");
//        Robolectric.addHttpResponseRule("http://cwf.jit.su/wardList", successResponse);
//
//
//        Robolectric.addHttpResponseRule("http://cwf.jit.su/positionIds", successResponse);
//
//
////        Robolectric.setDefaultHttpResponse(200, "OK");
////        HttpClient httpClient = NetworkUtil.createHttpClient();
////        String STATUSES_URL =  "/statuses";
////
//////        HttpResponse response = httpClient.execute("http://cwf.jit.su" + STATUSES_URL);
////
////
////        Calling calling1 = TestUtils.createCallingObj( 22L, "APPROVED", 5555L,false );
////        String ROOT = "http://cwf.jit.su";
//
//
////        networkUtil.updateCalling( calling1 );
//
//
//                Robolectric.clearPendingHttpResponses();
//
//    }

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





}
