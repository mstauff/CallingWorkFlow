package org.lds.community.CallingWorkFlow.api;

import com.xtremelabs.robolectric.Robolectric;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lds.community.CallingWorkFlow.InjectedTestRunner;
import org.lds.community.CallingWorkFlow.Utilities.TestUtils;
import org.lds.community.CallingWorkFlow.domain.Calling;
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

    TestUtils initDb=new TestUtils();


    @Before
    public void setup(){

        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
//        initDb.initializeDb();
    }

    @Test
    public void testUpdateCalling() throws Exception {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        networkUtil = new CwfNetworkUtil();
        Calling calling1 = TestUtils.createCallingObj( 22L, "APPROVED", 5555L );
        Calling calling2 = TestUtils.createCallingObj( 23L, "APPROVED", 5554L );


        networkUtil.updateCalling( calling1 );
        List<Calling> callings = networkUtil.getPendingCallings();
        Assert.assertTrue("Calling wasn't saved", TestUtils.foundCallingFromList(callings, calling1.getIndividualId(), calling1.getPositionId()));
        Calling resultCalling=  TestUtils.getCallingObjectFromList(callings,calling1.getIndividualId(),calling1.getPositionId());
        TestUtils.assertEntityEquals(calling1,resultCalling);

        networkUtil.deleteCalling( calling1 );
        networkUtil.deleteCalling( calling2 );

    }

    @Test
    public void testCallingStatus(){
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        networkUtil = new CwfNetworkUtil();


        Calling calling1 = TestUtils.createCallingObj( 22L, "SET_APART", 1L );
        Calling calling2 = TestUtils.createCallingObj( 23L, "SET_APART", 2L );
        Calling calling3 = TestUtils.createCallingObj( 24L, "SET_APART", 3L );
        Calling calling4 = TestUtils.createCallingObj( 25L, "PENDING", 4L );
        Calling calling5 = TestUtils.createCallingObj( 26L, "PENDING", 5L );

        networkUtil.updateCalling( calling1 );
        networkUtil.updateCalling( calling2 );
        networkUtil.updateCalling( calling3 );
        networkUtil.updateCalling( calling4 );
        networkUtil.updateCalling( calling5 );

        List<Calling> callingList= networkUtil.getCompletedCallings();
        Assert.assertEquals("Did not return 3 SET_APART callings",3, TestUtils.getCallingStatusCountFromList(callingList, "SET_APART"));
        Assert.assertEquals("Did not return 2 PENDING callings",2, TestUtils.getCallingStatusCountFromList(callingList,"PENDING"));

        for(Calling c:callingList){
            networkUtil.deleteCalling( c );
        }
    }

   @Test
    public void getWorkFlowStatus(){
       List<WorkFlowStatus> wfList= networkUtil.getStatuses();
       Assert.assertTrue( "",wfList.size()>=0);
       //todo need to do something here
   }

    @Test
    public void getPositionIds(){
        List<Position> pList= networkUtil.getPositionIds();
        Assert.assertTrue( "",pList.size()>=0);
        //todo need to do something here

    }
}
